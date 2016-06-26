package com.piraeus.component;

import java.text.NumberFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.generic.dms.FieldCode;
import com.misys.ps.generic.dms.MetadataStash;
import com.misys.ps.piraeus.dms.wsc.CreateCoverPageIdOutput;
import com.misys.ps.piraeus.ti.WSAdapter;
import com.misys.tiplus2.enigma.customisation.control.ExtendedHyperlinkControlWrapper;
import com.misys.tiplus2.foundations.lang.logging.Loggers;
import com.misys.tiplus2.platform.core.deployment.DeploymentDetails;

/** Component implementing GUI components of DMS/Scanner interface.
 * 
 * @author jonathan.local
 *
 */
public class DMSUpdateComponent {
	
	/* Control IDs */
	private static final String COVER_PAGE_LINK_CONTROL = "ViewCoverSheet";
	private static final String COVER_SHEET_ID_FIELD = "coverSheetId";

	private static final Logger LOG = LoggerFactory.getLogger(DMSUpdateComponent.class);
	
	/** Generic function to store metadata needed later for DMS operations.
	 * 
	 * @param callbacks
	 */
	public static void stashMetadata(RouterCallbacks callbacks) {
		Loggers.method().enter(LOG);

		MetadataStash stash = MetadataStash.getInstance();
		
		LOG.info("Got stash object {}", stash);
		
		for(Map.Entry<String, FieldCode> entry : stash.getRequiredFieldsByCode()) {
			String name = entry.getKey();
			FieldCode code = entry.getValue();
			String value = callbacks.getEventFieldAsText(code.name, code.type, code.part);
			LOG.info("Stashing metadata ({},{})", name, value);
			stash.setAttribute(name, value);
		}
		
		Loggers.method().exit(LOG);
	}

	/** onSaving, we must stash metadata in order to make it accessible in the DMS when saving documents.
	 * 
	 * @param callbacks
	 */
	public static boolean onSaving(RouterCallbacks callbacks) {
		Loggers.method().enter(LOG);
		stashMetadata(callbacks);
		Loggers.method().exit(LOG);
		return true;
	}

	/** onPostInitialise, we hide the cover page hyperlink if a cover page has not been generated.
	 * 
	 * @param callbacks
	 */
	public static boolean onPostInitialise(
			RouterCallbacks callbacks, WSAdapter dms) {
		Loggers.method().enter(LOG);
		PaneRouterCallbacks pane = callbacks.getPaneCallbacks();
		String cover_page_id = (String)pane.getCustomField(COVER_SHEET_ID_FIELD);
		String cover_page_control = pane.resolveHyperlinkControlName(COVER_PAGE_LINK_CONTROL);
		if (pane.isControlOnPage(cover_page_control)) {
			if (cover_page_id != null && !cover_page_id.trim().isEmpty()) {
				String event_reference = callbacks.getEventFieldAsText("MER","r", "");
				String portal_reference = callbacks.getEventFieldAsText("GWYR","r", "");
				String principal = callbacks.getEventFieldAsText("PRI", "p", "cu");
				String branch = callbacks.getEventFieldAsText("BOB", "b", "c");
				String product_type = callbacks.getEventFieldAsText("PCO", "s", "");
				String event_type = callbacks.getEventFieldAsText("EVCD", "s", "");
				pane.setHyperlinkURL(cover_page_control, dms.getCoverPageURL(Integer.valueOf(cover_page_id), event_reference, portal_reference, principal, branch, product_type, event_type));
				pane.setControlVisible(cover_page_control, true);
			} else {
				pane.setControlVisible(cover_page_control, false);			
			}
		}
		return true;
	}

	/** onGenerateCoverSheet, we call a webservice to retrieve a cover sheet id and store it.
	 * 
	 * Once we have a cover sheet Id, we make the cover page hyperlink visible.
	 * 
	 * @param callbacks
	 */
	public static void onGenerateCoverSheet(PaneRouterCallbacks callbacks, WSAdapter dms) {
		Loggers.method().enter(LOG);
		String master_reference = callbacks.getEventFieldAsText("MST","r", "");
		String event_sub_reference = callbacks.getEventFieldAsText("EVR","r", "");
		String event_reference = callbacks.getEventFieldAsText("MER","r", "");
		String portal_reference = callbacks.getEventFieldAsText("GWYR","r", "");
		String principal = callbacks.getEventFieldAsText("PRI", "p", "cu");
		String branch = callbacks.getEventFieldAsText("BOB", "b", "c");
		String user = callbacks.getUser();
		String product_type = callbacks.getEventFieldAsText("PCO", "s", "");
		String event_type = callbacks.getEventFieldAsText("EVCD", "s", "");
				
		CreateCoverPageIdOutput result = null;
		
		try {
			result = dms.createCoverPageId(event_reference, portal_reference, principal, user, branch, product_type, event_type, master_reference, event_sub_reference);
		
			if (result != null && WSAdapter.isOK(result)) {
				Integer id = result.getCoverPageId();
				callbacks.setCustomField(COVER_SHEET_ID_FIELD, id.toString());
				String cover_page_control = callbacks.resolveHyperlinkControlName(COVER_PAGE_LINK_CONTROL);
				callbacks.setHyperlinkURL(cover_page_control, dms.getCoverPageURL(id, event_reference, portal_reference, principal, branch, product_type, event_type));
				callbacks.setControlVisible(cover_page_control, true);
			} else {
				throw new RuntimeException("Web service returned:" + WSAdapter.getError(result));
			}
		} catch (Exception e) {
			LOG.debug("Rethrowing:{}", e);
			throw new RuntimeException("exception getting cover page id from web service",e);
		} finally {
			Loggers.method().exit(LOG);
		}
	}
}
