package com.piraeus.component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.piraeus.ti.WSAdapter;
import com.misys.tiplus2.apps.ti.kernel.extpm.entity.ExtEventWrapper;
import com.misys.tiplus2.apps.ti.kernel.extpm.pane.ExtEventExtensionDriverPWrapper;
import com.misys.tiplus2.customisation.extension.EventExtension;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.misys.tiplus2.enigma.lang.datatype.Entity;
import com.misys.tiplus2.enigma.lang.pane.EnigmaExtensionPane;
import com.misys.tiplus2.foundations.lang.logging.Loggers;

/** Event extension router.
 * 
 * This is the extension typically associated with a layout created with the customization editor.
 * 
 * Ususally we should not write custom code directly in the router. Instead, we should make calls
 * to module classes where business code is actually implemented.
 * 
 * @author localadmin
 *
 */
public class EventExtensionRouter extends EventExtension implements RouterCallbacks {

	private static final Logger LOG = LoggerFactory.getLogger(EventExtensionRouter.class);
	
	public EventExtensionRouter() {
		Loggers.method().enter(LOG);
		Loggers.method().exit(LOG);
	}

	/** Callback method to get event field value.
	 * 
	 * Modules invoked from this router can call back to it in order to get information required.
	 * 
	 * @param code Field code
	 * @param type Expected type
	 * @param part Field code part
	 * @return Event field value as text.
	 */
	public String getEventFieldAsText(String code, String type, String part) {
		Loggers.method().enter(LOG, code, type, part);
		String result = getDriverWrapper().getEventFieldAsText(code, type, part);
		Loggers.method().exit(LOG, result);
		return result;
	}
	
	public void setCustomField(String name, Object value) {
		Loggers.method().enter(LOG, name, value);
		try {
			PropertyUtils.setProperty(getWrapper(), name, value);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			LOG.debug("setCustomField caught {}", e);
			throw new RuntimeException("No custom field called " + name);
		} finally {
			Loggers.method().exit(LOG);
		}
	}

	public Object getCustomField(String name) {
		Loggers.method().enter(LOG, name);
		try {
			return PropertyUtils.getProperty(getWrapper(), name);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			LOG.debug("getCustomField caught {}", e);
			throw new RuntimeException("No custom field called " + name);
		} finally {
			Loggers.method().exit(LOG);
		}
	}

	
	
	public EventType getEventType() {
		return new EventType(getEventFieldAsText("PCO", "s", ""), getEventFieldAsText("EVCD", "s", ""));
	}


	/** Get ExtEventWrapper.
	 * 
	 * Returns a subclass of ExtEventWrapper that provides access to getTable method.
	 * 
	 * @return
	 */
	public ExtEventWrapper getWrapper_public() {
		return super.getWrapper();
	}
	
	/** Widens visibility of EventExtension.getDriverWrapper.
	 * 
	 * Because getDriverWrapper is protected and final, we can't simply widen visibility,
	 * we have to give the method a new name.
	 * 
	 * Ideally we shouldn't use this, but instead implement router callback methods per getEventFieldAsText above.
	 * 
	 * @return The driver wrapper return by super.getDriverWrapper().
	 */
	public ExtEventExtensionDriverPWrapper getDriverWrapper_public() {
			return super.getDriverWrapper();
	}
	
	/** Get callbacks for Pane.
	 * 
	 * @return Callbacks for associated pane.
	 */
	public PaneRouterCallbacks getPaneCallbacks() {
		return ((ExtensionPaneRouter)getPane());
	}
	
	/** Call validation methods on all modules relevant to this layout.
	 * 
	 */
	@Override
	public void onValidate(ValidationDetails validationDetails) {
		Loggers.method().enter(LOG);
		LegalDeclComponent.onValidate(this, validationDetails);
		// Add validations for additional modules below.
		Loggers.method().exit(LOG);
	}
	

	

	/** Call initialiseUserFieldCodes methods on all modules relevant to this layout.
	 * 
	 */	
	@Override
	public void initialiseUserFieldCodes() {
		Loggers.method().enter(LOG);
		// MyModule.initialiseUserFieldCodes(this);
		Loggers.method().exit(LOG);
	}	

	
	/** Get date-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return a field  value of type date.
	 * 
	 */	
	@Override
	public Date getUserFieldCodeDate (String code)
	{
		Loggers.method().enter(LOG, code);
		Date result = null;
		// if (result != null) result = MyModule.getUserFieldCodeDate(this, code);
		if (result == null) result = super.getUserFieldCodeDate(code);
		Loggers.method().exit(LOG);
		return result;
	 }

	/** Get boolean-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return a field value of type boolean.
	 * 
	 */	
	@Override
	public Boolean getUserFieldCodeBoolean (String code) {
		Loggers.method().enter(LOG, code);
		Boolean result = null;;
		// if (result == null) result = MyModule.getUserFieldCodeBoolean(this, code);
		if (result == null) result = super.getUserFieldCodeBoolean(code);
		Loggers.method().exit(LOG);
		return result;
	}
	
	/** Get integer-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return an integer field value.
	 * 
	 */		
	@Override
	public Integer getUserFieldCodeInteger (String code) {
		Loggers.method().enter(LOG, code);
		Integer result = null;;
		// if (result == null) result = MyModule.getUserFieldCodeInteger(this, code);
		if (result == null) result = super.getUserFieldCodeInteger(code);
		Loggers.method().exit(LOG);
		return result;
	}

	/** Get string-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return a string field value.
	 * 
	 */		
	@Override
	public String getUserFieldCodeString (String code) {
		Loggers.method().enter(LOG, code);
		String result = null;
		// if (result == null) result = MyModule.getUserFieldCodeString(this, code);
		if (result == null) result = super.getUserFieldCodeString(code);
		Loggers.method().exit(LOG);
		return result;
	}

	/** Get amount-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return an amount field value (a formatted string)
	 * 
	 */	
	@Override
	protected String getUserFieldCodeAmount(String code) {
		Loggers.method().enter(LOG, code);
		String result = null;;
		// if (result == null) result = MyModule.getUserFieldCodeAmount(this, code);
		if (result == null) result = super.getUserFieldCodeAmount(code);
		Loggers.method().exit(LOG);
		return result;
	}
	
	/** Call onSaving in all modules relevant to this layout.
	 * 
	 * @return true if onSaving returns true in all modules and superclass.
	 * 
	 */
	@Override
    public boolean onSaving() {		
		Loggers.method().enter(LOG);
		boolean result = true;
		if (!DMSUpdateComponent.onSaving(this)) result = false;
		if (!super.onSaving()) result = false;
		Loggers.method().exit(LOG);
		return result;		
	}
	
	@Override
	public boolean onPostInitialise() {
		Loggers.method().enter(LOG);
		boolean result = true;
		if (!DMSUpdateComponent.onPostInitialise(this, WSAdapter.getInstance())) result = false;
		Loggers.method().exit(LOG);
		return result;
	}

	/** Call dataCaptured in all modules relevant to this layout.
	 * 
	 * @return true if onSaving returns true in all modules and superclass.
	 * 
	 */
	@Override
    public void dataCaptured() {
		Loggers.method().enter(LOG);
		super.dataCaptured();	
		// MyModule.dataCaptured(this);			
		Loggers.method().exit(LOG);
	}	
}
