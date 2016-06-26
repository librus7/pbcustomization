package com.piraeus.component;

import com.misys.tiplus2.customisation.entity.PIRGOODSFULL;
import com.misys.tiplus2.customisation.entity.PIRRMABANKS;

public interface PaneRouterCallbacks {
	
	public Object getCustomField(String name);
	public void setCustomField(String name, Object value);
	public String getEventFieldAsText(String string, String string2, String string3);
	public String getUser();
	public void setControlVisible(String name, boolean visible);
	public void setControlEnabled(String name, boolean visible);
	public void setHyperlinkURL(String name, String URL);
	
	public PIRGOODSFULL createQuery_PIRGOODSFULL(String className, String condition);
	public PIRRMABANKS createQuery_PIRRMABanks(String className, String condition );
	
}
