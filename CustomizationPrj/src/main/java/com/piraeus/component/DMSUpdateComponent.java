package com.piraeus.component;

import java.text.NumberFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.generic.dms.FieldCode;
import com.misys.ps.generic.dms.MetadataStash;
import com.misys.ps.piraeus.dms.wsc.CreateCoverPageIdOutput;
import com.misys.ps.piraeus.dms.wsc.DmsWebServiceProxy;
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
	private static final String COVER_PAGE_LINK_CONTROL = "ViewCoverSheetIssue_ISIHyperlink";
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
		if (cover_page_id != null && !cover_page_id.trim().isEmpty()) {
			pane.setHyperlinkURL(COVER_PAGE_LINK_CONTROL, dms.getCoverPageURL(Integer.valueOf(cover_page_id)));
			pane.setControlVisible(COVER_PAGE_LINK_CONTROL, true);
		} else {
			pane.setControlVisible(COVER_PAGE_LINK_CONTROL, false);			
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
		String event_reference = callbacks.getEventFieldAsText("MER","r", "");
		String principal = callbacks.getEventFieldAsText("PRI", "p", "no");
		String branch = callbacks.getEventFieldAsText("BOB", "b", "c");
		String user = callbacks.getUser();
		
		int customer_id = -1;
		
		try { 
			customer_id = Integer.valueOf(principal); 
		} catch (RuntimeException e) { 
			LOG.warn("Could not convert customer code " + principal + " to integer");
		}
		
		CreateCoverPageIdOutput result = null;
		
		try {
			result = dms.createCoverPageId(event_reference, customer_id, user, branch);
		
			if (result != null && WSAdapter.isOK(result)) {
				int id = Integer.valueOf(result.getCoverPageId());
				callbacks.setCustomField(COVER_SHEET_ID_FIELD, String.valueOf(id));
				callbacks.setHyperlinkURL(COVER_PAGE_LINK_CONTROL, dms.getCoverPageURL(id));
				callbacks.setControlVisible(COVER_PAGE_LINK_CONTROL, true);
			} else {
				throw new RuntimeException("unable to get cover page id from web service");
			}
		} catch (Exception e) {
			throw new RuntimeException("exception getting cover page id from web service",e);
		} finally {
			Loggers.method().exit(LOG);
		}
	}
}
