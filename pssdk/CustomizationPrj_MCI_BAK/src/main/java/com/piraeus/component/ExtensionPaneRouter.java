package com.piraeus.component;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.piraeus.ti.WSAdapter;
import com.misys.tiplus2.customisation.entity.PIRGOODSFULL;
import com.misys.tiplus2.customisation.entity.PIRRMABANKS;
import com.misys.tiplus2.customisation.pane.EventPane;
import com.misys.tiplus2.enigma.customisation.control.ExtendedHyperlinkControlWrapper;
import com.misys.tiplus2.enigma.lang.control.wrapper.EnigmaControlWrapper;
import com.misys.tiplus2.foundations.lang.logging.Loggers;

public class ExtensionPaneRouter extends EventPane implements PaneRouterCallbacks {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -402221448253913976L;

	private static final Logger LOG = LoggerFactory.getLogger(ExtensionPaneRouter.class);
	
	@Override
	public void onGenerateCoverSheetIssue_ISIButton() {
		Loggers.method().enter(LOG);
		DMSUpdateComponent.onGenerateCoverSheet(this, WSAdapter.getInstance());
		Loggers.method().exit(LOG);
	}
	
	public void setCustomField(String name, Object value) {
		Loggers.method().enter(LOG, name, value);
		try {
			PropertyUtils.setProperty(this, name, value);
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
			return PropertyUtils.getProperty(this, name);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			LOG.debug("getCustomField caught {}", e);
			throw new RuntimeException("No custom field called " + name);
		} finally {
			Loggers.method().exit(LOG);
		}
	}
	
	public String getEventFieldAsText(String code, String type, String part) {
		Loggers.method().enter(LOG, code, type, part);
		try {
			return getDriverWrapper().getEventFieldAsText(code, type, part);
		} finally {
			Loggers.method().exit(LOG);
		}
	}
	
	public String getUser() {
		Loggers.method().enter(LOG);
		try {
			return this.getApplicUser();
		} finally {
			Loggers.method().exit(LOG);
		}
	}
	
	private EnigmaControlWrapper getControl(String name) {
		Loggers.method().enter(LOG, name);
		if (name == null) throw new RuntimeException("name cannot be null");
		
		if (Character.isLowerCase(name.charAt(0))) {
			name = "" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}
		
		try {
			return (EnigmaControlWrapper)PropertyUtils.getProperty(this, "ctl" + name);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | RuntimeException e) {
			throw new RuntimeException("Invalid control name: " +  name);
		} finally {
			Loggers.method().exit(LOG);
		}

	}
	
	public void setControlVisible(String name, boolean value) {
		Loggers.method().enter(LOG);
		getControl(name).setVisible(value);
		Loggers.method().exit(LOG);
	}

	public void setControlEnabled(String name, boolean value) {
		Loggers.method().enter(LOG);
		getControl(name).setEnabled(value);
		Loggers.method().exit(LOG);
	}
	
	public void setHyperlinkURL(String name, String url) {
		Loggers.method().enter(LOG);
		((ExtendedHyperlinkControlWrapper)getControl(name)).setUrl(url);
		Loggers.method().exit(LOG);
	}

	@Override
	public PIRGOODSFULL createQuery_PIRGOODSFULL(String className,
			String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PIRRMABANKS createQuery_PIRRMABanks(String className,
			String condition) {
		// TODO Auto-generated method stub
		return null;
	}
}
