package com.piraeus.component.mci;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.tiplus2.customisation.entity.PIRRMABANKS;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails.ErrorType;
import com.misys.tiplus2.foundations.lang.logging.Loggers;
import com.piraeus.component.PaneRouterCallbacks;
import com.piraeus.component.RouterCallbacks;

public class FindTGTAddressee {
	
	/*
	 * @author Dimitris Dimitriou 
	 */

	
	private static final Logger LOG = LoggerFactory.getLogger(FindTGTAddressee.class);
	private static final String  	VALIDIY_CHECK_BOX = "authBICValidity";
	private static final String  	DESTINATION_BIC_CODE = "destBICCode";
	private static final String 	PRODUCT_TYPE_CODE = "PCO" ;
	
	
	public static void onValidate( 	RouterCallbacks routerCallbacks,  ValidationDetails validationDetails) {
		
		Loggers.method().enter(LOG);
		
		// Διαβάζουμε το product
		String 	pCode 	= routerCallbacks.getEventFieldAsText( PRODUCT_TYPE_CODE, "s", "");
		String 	destBIC = (String)routerCallbacks.getPaneCallbacks().getCustomField(DESTINATION_BIC_CODE);
		String	errorText ="";
		
		errorText = findTGTRelation(routerCallbacks.getPaneCallbacks());
		
		if ( StringUtils.isNotBlank( errorText) )
			validationDetails.addError(ErrorType.Other, errorText);

		Loggers.method().exit(LOG);
	}
	
	private static String findTGTRelation( PaneRouterCallbacks  paneCallbacks ) {
		
		String destBIC = (String)paneCallbacks.getCustomField( DESTINATION_BIC_CODE );
		String transferMethod = paneCallbacks.getEventFieldAsText( "TFM", "s", "");
		String	sentToSWCODE ="", advisingName ="";
		String 	errorText ="";
		
		//TFM	Transfer method	3
		//TFMD	Transfer method description	Telex via SWIFT
		if ( !transferMethod.equals("4") ) {
			return errorText;
		}
		
		// AVLX Available with type (A = Advising)
		String availableWithType = paneCallbacks.getEventFieldAsText("AVLX", "s", "");
		
		if (availableWithType.equals("A")) {
			
			// Advising Bank
			sentToSWCODE = paneCallbacks.getEventFieldAsText("ADV", "p", "s");
			sentToSWCODE = sentToSWCODE.replace(" ", "").trim();
			advisingName = (String)paneCallbacks.getEventFieldAsText("ADV", "p", "f").trim();
			LOG.debug("BICKeyCheck ( senToSWCODE = {}, Advising Bank {})" , sentToSWCODE,advisingName);

		}
		
		if ( StringUtils.isBlank(sentToSWCODE ) ) {
			//validationDetails.addError( ErrorType.Other, "Invalid Advising Bank"+advisingName );
			errorText ="Invalid Advising Bank : "+advisingName ;
			return errorText;
		}
		
		String queryString ="";
		if ( StringUtils.isNotBlank( sentToSWCODE ) ) {
			paneCallbacks.setCustomField(DESTINATION_BIC_CODE , String.format( "%s", sentToSWCODE ));

			queryString = "SWCODE = '" + sentToSWCODE.substring(0, 8) +"' and IE_TYPE ='I' and SW_CAT='7' and (SW_TYPE ='700' or SW_TYPE='ALL')";
			LOG.debug(" BICKeyCheck-onValidate  sentToSWCODE {}, queryString {} ", sentToSWCODE , queryString );

			PIRRMABANKS entity = paneCallbacks.createQuery_PIRRMABanks( "PIRRMABANKS", queryString );
			if ( null != entity) {
				paneCallbacks.setCustomField( VALIDIY_CHECK_BOX , Boolean.TRUE);
			}
			else {
				paneCallbacks.setCustomField( VALIDIY_CHECK_BOX , Boolean.FALSE);
				errorText = String.format( "%s, %s %s", "Δεν έχουμε κλείδα για την τράπεζα ", sentToSWCODE ,advisingName);
				//validationDetails.addError(ErrorType.Other,  String.format( "%s, %s %s", "Δεν έχουμε κλείδα για την τράπεζα ",sentToSWCODE ,advisingName));
				return errorText;
			}
		}
		
		return "";
		
	}		

	
}
