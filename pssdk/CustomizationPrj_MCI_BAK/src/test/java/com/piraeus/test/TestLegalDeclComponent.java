/**
 * 
 */
package com.piraeus.test;

import static org.junit.Assert.assertEquals;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.piraeus.component.LegalDeclComponent;
import com.piraeus.component.PaneRouterCallbacks;
import com.piraeus.component.RouterCallbacks;

/**
 * @author localadmin
 *
 */
public class TestLegalDeclComponent {
	
	private static final String PROCESSING_DATE_CODE = "TDY" ;
	private static final String CURRENT_STEP_ID_CODE = "CSID" ;
	private static final String APPLICANT_PTY_CODE = "APP" ;
	private static final String LEGALISATION_EXPIRY_DATE_PART = "cABJ" ;
	private static final String PERIODIC_DECL_EXP_DATE_PART = "cABL" ;
	
	private static final String PRODUCT_TYPE_CODE = "PCO" ;

	private static final String STRING_TYPE = "s" ;
	private static final String DATE_TYPE = "d" ;
	private static final String PARTY_TYPE = "p" ;
	
	private static final String INPUT_STEP_STRING_FIELD_CODE = "Input" ;
	private static final String REVIEW_STEP_STRING_FIELD_CODE = "Review" ;
	private static final String MAINAUTHORISE_STEP_STRING_FIELD_CODE = "Main Authorise" ;

	
	public static RouterCallbacks CALLBACKS_1 = new RouterCallbacks() {
		
		private TreeMap<String, String> STRING_FIELDS = new TreeMap<String,String>() {{
			put(CURRENT_STEP_ID_CODE, INPUT_STEP_STRING_FIELD_CODE);
			put(PRODUCT_TYPE_CODE, "ILC");
		}};
		
		private TreeMap<String, String> DATE_FIELDS = new TreeMap<String,String>() {{
			put(PROCESSING_DATE_CODE, "11/11/2011");
		}};
		
		private TreeMap<String, String> PARTY_FIELDS = new TreeMap<String,String>() {{
			put(APPLICANT_PTY_CODE + "/" + LEGALISATION_EXPIRY_DATE_PART, "11/11/2011");
			put(APPLICANT_PTY_CODE + "/" + PERIODIC_DECL_EXP_DATE_PART, "11/11/2011");
		}};

		public String getEventFieldAsText(String code, String type, String part) {
			if (type != null && code != null) {
				if (part != null && part.length() > 0) code = code + "/" + part;
				if (STRING_TYPE.equals(type)) return STRING_FIELDS.get(code);
				if (DATE_TYPE.equals(type)) return DATE_FIELDS.get(code);
				if (PARTY_TYPE.equals(type)) return PARTY_FIELDS.get(code);
			}
			return null;
		}
		
		public PaneRouterCallbacks getPaneCallbacks() {
			return null;
		}

		@Override
		public Object getCustomField(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setCustomField(String name, Object value) {
			// TODO Auto-generated method stub
			
		}

	};

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.piraeus.component.LegalDeclComponent#onValidate(com.piraeus.component.RouterCallbacks, com.misys.tiplus2.enigma.customisation.validation.ValidationDetails)}.
	 */
	@Test
	public void testOnValidate() {
		ValidationDetails validations = new ValidationDetails();
		LegalDeclComponent.onValidate(CALLBACKS_1, validations);
		assertEquals(2, validations.getWarningList().size());
	}

}
