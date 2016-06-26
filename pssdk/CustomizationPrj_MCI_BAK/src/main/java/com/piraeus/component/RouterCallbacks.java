package com.piraeus.component;


/** Interface for Router Callbacks.
 * 
 * <p>
 * Why does this interface exist? Well, we wish to be able to write junit tests for extension module code.
 * </p><p>
 * The EventExtension class is quite heavy and has deep roots in the TI application framework. Creating an instance
 * of an EventExtension for unit testing purposes is not easy. Therefore, if we write module code that only makes calls to
 * this interface, we can create a lightweight implementation of this interface within our unit testing framework and thus
 * test our module code easily.
 * </p><p>
 * Feel free to add additional callback methods to this interface but try to avoid using heavy TI plus types that
 * cannot easily be used in a testing framework.
 * </p>
 * 
 * @author Jonathan Essex
 *
 */
public interface RouterCallbacks {
	
	/** Get the value of a field from TI plus.
	 * 
	 * The main implementation of this method, in EventExtensionRouter, returns getDriverWrapper().getEventFieldAsText(..).
	 * If an implemnenttion of this interface is created for unit testing purposes, this method should be implemented to retunr
	 * data as needed for the test.
	 *
	 * 
	 * @param code
	 * @param type
	 * @param part
	 * @return
	 */
	public String getEventFieldAsText(String code, String type, String part);
	
	public Object getCustomField(String name);
	public void setCustomField(String name, Object value);
	
	
	/** Get the callbacks object for the associated pane of controls.
	 * 
	 * @return
	 */
	public PaneRouterCallbacks getPaneCallbacks();

}
