package com.piraeus.component;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.piraeus.ti.WSAdapter;
import com.misys.tiplus2.apps.ti.kernel.extpm.entity.ExtEventWrapper;
import com.misys.tiplus2.apps.ti.kernel.extpm.pane.ExtEventExtensionDriverPWrapper;
import com.misys.tiplus2.customisation.extension.EventExtension;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
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
public abstract class EventExtensionRouterBase extends EventExtension implements RouterCallbacks {

	private static final Logger LOG = LoggerFactory.getLogger(EventExtensionRouterBase.class);
	
	public EventExtensionRouterBase() {
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
		return ((ExtensionPaneRouterBase)getPane());
	}
	
	
	public void setCustomField(String name, Object value) {
		Loggers.method().enter(LOG, name, value);
		try {
			String methodName = "set" + WordUtils.capitalize(name);
			//-error---> PropertyUtils.setProperty(this.getDriverWrapper(), name, value); //16/06/16 comment because of error 
			MethodUtils.invokeExactMethod(this.getWrapper(), methodName, value);
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
			String methodName = "get" + WordUtils.capitalize(name);
			//-error--->return PropertyUtils.getProperty(this.getDriverWrapper(), name); //16/06/16 comment because of error
			return MethodUtils.invokeExactMethod(this.getWrapper(), methodName, (Object[])null);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			LOG.debug("getCustomField caught {}", e);
			throw new RuntimeException("No custom field called " + name);
		} finally {
			Loggers.method().exit(LOG);
		}
	}
	
	
	
	/** Call validation methods on all modules relevant to this layout.
	 * 
	 */
	@Override
	public void onValidate(ValidationDetails validationDetails) {
		Loggers.method().enter(LOG);
		
		//call LegalDeclComponent.onValidate method for all eventExtensionRouters
		LegalDeclComponent.onValidate(this, validationDetails); 
		
		// Add validations for additional modules below.
		onValidateExt(this, validationDetails);
		
		
		Loggers.method().exit(LOG);
	}
	

	protected abstract void onValidateExt(
			EventExtensionRouterBase eventExtensionRouterBase,
			ValidationDetails validationDetails);

	/** Call initialiseUserFieldCodes methods on all modules relevant to this layout.
	 * 
	 */	
	@Override
	public void initialiseUserFieldCodes() {
		Loggers.method().enter(LOG);
		
		// MyModule.initialiseUserFieldCodes(this);
		initialiseUserFieldCodesExt(this);
		
		Loggers.method().exit(LOG);
	}	

	
	protected abstract void initialiseUserFieldCodesExt(
			EventExtensionRouterBase eventExtensionRouterBase);

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
		result = getUserFieldCodeDateExt(this, code);
		
		if (result == null) result = super.getUserFieldCodeDate(code);
		Loggers.method().exit(LOG);
		return result;
	 }

	protected abstract Date getUserFieldCodeDateExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code);

	/** Get boolean-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return a field value of type boolean.
	 * 
	 */	
	@Override
	public Boolean getUserFieldCodeBoolean (String code) {
		Loggers.method().enter(LOG, code);
		Boolean result = null;
		
		// if (result == null) result = MyModule.getUserFieldCodeBoolean(this, code);
		result = getUserFieldCodeBooleanExt(this, code);
		
		if (result == null) result = super.getUserFieldCodeBoolean(code);
		Loggers.method().exit(LOG);
		return result;
	}
	
	protected abstract Boolean getUserFieldCodeBooleanExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code);

	/** Get integer-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return an integer field value.
	 * 
	 */		
	@Override
	public Integer getUserFieldCodeInteger (String code) {
		Loggers.method().enter(LOG, code);
		Integer result = null;
		
		// if (result == null) result = MyModule.getUserFieldCodeInteger(this, code);
		result = getUserFieldCodeIntegerExt(this, code);
		
		if (result == null) result = super.getUserFieldCodeInteger(code);
		Loggers.method().exit(LOG);
		return result;
	}

	protected abstract Integer getUserFieldCodeIntegerExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code);

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
		result = getUserFieldCodeStringExt(this, code);
		
		if (result == null) result = super.getUserFieldCodeString(code);
		Loggers.method().exit(LOG);
		return result;
	}

	protected abstract String getUserFieldCodeStringExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code);

	/** Get amount-type field values from all modules relevant to this layout.
	 * 
	 * @param code a field code identifying the requested value
	 * @return an amount field value (a formatted string)
	 * 
	 */	
	@Override
	protected String getUserFieldCodeAmount(String code) {
		Loggers.method().enter(LOG, code);
		String result = null;
		
		// if (result == null) result = MyModule.getUserFieldCodeAmount(this, code);
		result = getUserFieldCodeAmountExt(this, code);
		
		if (result == null) result = super.getUserFieldCodeAmount(code);
		Loggers.method().exit(LOG);
		return result;
	}
	
	protected abstract String getUserFieldCodeAmountExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code);

	/** Call onSaving in all modules relevant to this layout.
	 * 
	 * @return true if onSaving returns true in all modules and superclass.
	 * 
	 */
	@Override
    public boolean onSaving() {		
		Loggers.method().enter(LOG);
		boolean result = true;
		
		//call DMSUpdateComponent.onSaving method for all eventExtensionRouters
		if (!DMSUpdateComponent.onSaving(this)) result = false; 
		
		if (!onSavingExt(this)) result = false;
		
		if (!super.onSaving()) result = false;
		Loggers.method().exit(LOG);
		return result;		
	}
	
	protected abstract boolean onSavingExt(
			EventExtensionRouterBase eventExtensionRouterBase);

	@Override
	public boolean onPostInitialise() {
		Loggers.method().enter(LOG);
		boolean result = true;
		
		//call DMSUpdateComponent.onPostInitialise method for all eventExtensionRouters
		if (!DMSUpdateComponent.onPostInitialise(this, WSAdapter.getInstance())) result = false;
		
		if (!onPostInitialiseExt(this)) result = false;
		
		Loggers.method().exit(LOG);
		return result;
	}

	protected abstract boolean onPostInitialiseExt(
			EventExtensionRouterBase eventExtensionRouterBase);

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
		dataCapturedExt(this);
		
		Loggers.method().exit(LOG);
	}

	protected abstract void dataCapturedExt(
			EventExtensionRouterBase eventExtensionRouterBase);	
}
