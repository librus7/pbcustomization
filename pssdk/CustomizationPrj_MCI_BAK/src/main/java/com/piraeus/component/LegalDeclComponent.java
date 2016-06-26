package com.piraeus.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails.WarningType;
import com.misys.tiplus2.foundations.lang.logging.Loggers;


public class LegalDeclComponent {
	
	/**
	 * @author Balazs Torocsik
	 * This class will check the LegalisationExpiryDate and PeriodicDeclarationTaxesExpiryDate 
	 * against the processing date and send error/warning message if needed.
	 */

	private static final Logger LOG = LoggerFactory.getLogger(LegalDeclComponent.class);

	private static final String DATE_FORMAT_STRING = "dd/MM/yyyy";
	
	private static final String PROCESSING_DATE_CODE = "TDY" ;
	private static final String CURRENT_STEP_ID_CODE = "CSID" ;
	private static final String APPLICANT_PTY_CODE = "APP" ;
	private static final String LEGALISATION_EXPIRY_DATE_PART = "cABJ" ;
	private static final String PERIODIC_DECL_EXP_DATE_PART = "cABL" ;
	
	private static final String PRODUCT_TYPE_CODE = "PCO" ;

	private static final String INPUT_STEP_STRING_FIELD_CODE = "Input" ;
	private static final String REVIEW_STEP_STRING_FIELD_CODE = "Review" ;
	private static final String MAINAUTHORISE_STEP_STRING_FIELD_CODE = "Main Authorise" ;

	private static final String STRING_TYPE = "s" ;
	private static final String DATE_TYPE = "d" ;
	private static final String PARTY_TYPE = "p" ;

	public static void onValidate(RouterCallbacks router, ValidationDetails validationDetails) {
		Loggers.method().enter(LOG);
		String step = router.getEventFieldAsText(CURRENT_STEP_ID_CODE, STRING_TYPE, "");

//		if(!step.isEmpty()){
//			if (step.equals(INPUT_STEP_STRING_FIELD_CODE) ||
//				step.equals(REVIEW_STEP_STRING_FIELD_CODE) ||
//				step.equals(MAINAUTHORISE_STEP_STRING_FIELD_CODE))
//				checkLegalDecl(router, validationDetails);
//		}

		Loggers.method().exit(LOG);
	}
	
	protected static void checkLegalDecl(RouterCallbacks router, ValidationDetails valResult) {
		Loggers.method().enter(LOG);
		String procDate = router.getEventFieldAsText(PROCESSING_DATE_CODE, DATE_TYPE, "");
		String prodType = router.getEventFieldAsText(PRODUCT_TYPE_CODE, STRING_TYPE, "");
		String evfLegExpDate = null;
		String evfPeriodicExpDate = null;
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.ENGLISH);
		Date datProcDate = null;
		Date datLegExpDate = null;
		Date datPeriodicExpDate = null;

		/*LOG.info("checkLegalDecl: prodType: " + prodType.getValue());*/		
		if (prodType.equals("ILC") || prodType.equals("IGT") ||
				prodType.equals("ISB")) {
			evfLegExpDate = router.getEventFieldAsText(APPLICANT_PTY_CODE, PARTY_TYPE, LEGALISATION_EXPIRY_DATE_PART);
			evfPeriodicExpDate = router.getEventFieldAsText(APPLICANT_PTY_CODE, PARTY_TYPE, PERIODIC_DECL_EXP_DATE_PART);
			/*LOG.info("checkLegalDecl: evfPeriodicExpDate: " + evfPeriodicExpDate.getValue());*/		

			try {
				datProcDate = format.parse(procDate);
			} catch (ParseException e) {
				LOG.error("****Parse Exception occurred:---"+e.getMessage());
			}	
			/*LOG.info("checkLegalDecl: datProcDate: " + datProcDate.toString());*/		
	
			if (evfLegExpDate != null && !evfLegExpDate.isEmpty()) {
	
				try {
					datLegExpDate = format.parse(evfLegExpDate);
				} catch (ParseException e) {
					LOG.error("****Parse Exception occurred:---"+e.getMessage());
				}	
	
				if (datLegExpDate.equals(datProcDate) || datLegExpDate.before(datProcDate)) {
					valResult.addWarning(WarningType.Other, "Applicant's legalisation date expired. Please check customer details.");
				}
	
				/*LOG.info("checkLegalDecl: evfLegExpDate: " + evfLegExpDate.toString());*/		
			}
	
			if (evfPeriodicExpDate != null && !evfPeriodicExpDate.isEmpty()) {
	
				try {
					datPeriodicExpDate = format.parse(evfPeriodicExpDate);
				} catch (ParseException e) {
					LOG.error("****Parse Exception occurred:---"+e.getMessage());
				}	
				
				if (datPeriodicExpDate.equals(datProcDate) || datPeriodicExpDate.before(datProcDate)) {
					valResult.addWarning(WarningType.Other, "Applicant's periodic declaration taxes date expired. Please check customer details.");
				}
					
				/*LOG.info("checkLegalDecl: evfPeriodicExpDate: " + evfLegExpDate.toString());*/		
			}
		} // if (prodType.getValue().equals
	} // checkLegalDecl()
} // LegalDeclComponent class