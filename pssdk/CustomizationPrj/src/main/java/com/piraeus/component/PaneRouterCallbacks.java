package com.piraeus.component;

public interface PaneRouterCallbacks {
	
	public Object getCustomField(String name);
	public void setCustomField(String name, Object value);
	public String getEventFieldAsText(String string, String string2, String string3);
	public String getUser();
	public void setControlVisible(String name, boolean visible);
	public void setControlEnabled(String name, boolean visible);
	public boolean isControlOnPage(String name);
	public void setHyperlinkURL(String name, String URL);
	public String resolveHyperlinkControlName(String name);
	public RouterCallbacks getEventCallbacks();
}
