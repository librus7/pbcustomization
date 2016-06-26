package com.piraeus.component.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.ps.piraeus.ti.WSAdapter;
import com.misys.tiplus2.foundations.lang.logging.Loggers;
import com.piraeus.component.DMSUpdateComponent;
import com.piraeus.component.ExtensionPaneRouterBase;

public class ExtensionPaneRouter_ILC_ISI extends ExtensionPaneRouterBase {

	private static final long serialVersionUID = 2349545784944122525L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ExtensionPaneRouter_ILC_ISI.class);
	
	@Override
	public void onGenerateCoverSheetIssue_ISIButton() {
		Loggers.method().enter(LOG);
		DMSUpdateComponent.onGenerateCoverSheet(this, WSAdapter.getInstance());
		Loggers.method().exit(LOG);
	}
	
	@Override
	public void onGoodsCodeDescrBtnIssue_ISIButton() {
		Loggers.method().enter(LOG);
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCodeDescr_Button(this);
		Loggers.method().exit(LOG);
		
	}   
	
	@Override
	public void onBICAuthCheckBtnIssue_ISIButton() {
		Loggers.method().enter(LOG);
		
		com.piraeus.component.mci.BICKeysCheck.onBICAuthCheck_Button(this); 

		Loggers.method().exit(LOG);
	}
	
	@Override
	public void onGoodsCodeIssue_ISIAutoSubmit() {
		Loggers.method().enter(LOG);
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCode_AutoSubmit(this);
		Loggers.method().exit(LOG);
	}

}
