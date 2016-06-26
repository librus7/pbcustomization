// Bickeys Commit 57 

package com.piraeus.component.mci;

import java.sql.Ref;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.piraeus.ti.WSAdapter;
import com.misys.tiplus2.customisation.entity.PIRRMABANKS;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails.ErrorType;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails.WarningType;
import com.misys.tiplus2.foundations.lang.logging.Loggers;
import com.piraeus.component.EventExtensionRouterBase;
import com.piraeus.component.PaneRouterCallbacks;
import com.piraeus.component.RouterCallbacks;
import com.sun.xml.bind.v2.model.core.ClassInfo;


public class BICKeysCheck {
	
	/**
	 * @author Dimitris Dimitriou 
	 */
	private static final String  	VALIDIY_CHECK_BOX = "authBICValidity";
	private static final String  	DESTINATION_BIC_CODE = "destBICCode";
	private static final String 	PRODUCT_TYPE_CODE = "PCO" ;


	private static final Logger LOG = LoggerFactory.getLogger(BICKeysCheck.class);
	
	public static boolean onPostInitialise(EventExtensionRouterBase eventExtensionRouterBase ) {
		Loggers.method().enter(LOG);
		String classname = BICKeysCheck.class.getName();
		
		LOG.info(String.format("%s %s",	classname, "Start" ));
		//eventExtensionRouterBase.getPaneCallbacks().setControlVisible(VALIDIY_CHECK_BOX, Boolean.FALSE);
		//eventExtensionRouterBase.getPaneCallbacks().setControlVisible(DESTINATION_BIC_CODE, Boolean.FALSE);
		LOG.info(String.format("%s %s", classname, "Exits" ));
		Loggers.method().exit(LOG);
		
		return true;
	}
	
	public static void onValidate( 	RouterCallbacks routerCallbacks,  ValidationDetails validationDetails) {
			
		Loggers.method().enter(LOG);
		
		String queryString ="";
		//Boolean validKey = false;
		
		// Διαβάζουμε το product
		String 	pCode 	= routerCallbacks.getEventFieldAsText( PRODUCT_TYPE_CODE, "s", "");
		String 	destBIC = (String)routerCallbacks.getPaneCallbacks().getCustomField(DESTINATION_BIC_CODE);
		String 	isCorresp ="";

		
		LOG.info(" BICKeyCheck-onValidate  Product Code ({})", pCode  );
		LOG.info(" BICKeyCheck-onValidate  destBic ({})", destBIC  );
		
		String errorText;
		if ( pCode.equals("ILC") ) { 
			
			errorText = findRMABank(routerCallbacks.getPaneCallbacks());
			
			if ( StringUtils.isNotBlank( errorText) )
				validationDetails.addError(ErrorType.Other, errorText);
				
		}
		
		Loggers.method().exit(LOG);
	}

	public static void onBICAuthCheck_Button(
		PaneRouterCallbacks paneCallbacks) {
		Loggers.method().enter(LOG);
			findRMABank(paneCallbacks);
		Loggers.method().exit(LOG);
		
	}
	
	private static String findRMABank( PaneRouterCallbacks  paneCallbacks ) {
		String destBIC = (String)paneCallbacks.getCustomField(DESTINATION_BIC_CODE);
		String transferMethod = paneCallbacks.getEventFieldAsText("TFM", "s", "");

		String	sentToSWCODE ="", advisingName ="";
		String 	errorText ="";
		//TFM	Transfer method	3
		//TFMD	Transfer method description	Telex via SWIFT
		if ( !transferMethod.equals("4") ) {
			return errorText;
		}
		
		// AVLX Available with type (A = Advising)
		String availableWithType = paneCallbacks.getEventFieldAsText("AVLX", "s", "");
		LOG.debug("BICKeyCheck-availableWithType = " + availableWithType );
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
		
	
	private static boolean checkIDC( RouterCallbacks routerCallbacks , ValidationDetails validationDetails) {
		String sentToSWCODE ="";
		String msgTXT =routerCallbacks.getEventFieldAsText("SMSGN", "*", "MTXT");
		if ( msgTXT != null) {
			String swiftType =  routerCallbacks.getEventFieldAsText("SMSGN", "*", "MSGT");
			
			if (swiftType != null) {  
				validationDetails.addWarning(WarningType.Other, "SWIFT TYPE "+swiftType  );
				
				String adviseMethod =  routerCallbacks.getEventFieldAsText("TFMD", "s", "");
				String errorText ="";
				if ( adviseMethod.toUpperCase() =="SWIFT" ) 
					sentToSWCODE= findRMABank (	routerCallbacks.getPaneCallbacks());
			}
			else
				validationDetails.addWarning(WarningType.Other, "Payment will be sent without SWIFT message ? ");
		}
		return true;
	}
} // BICKeysCheck class

