package com.piraeus.component.router;

import com.piraeus.component.ExtensionPaneRouterBase;

public class ExtensionPaneRouter_ELC_NAME extends ExtensionPaneRouterBase {

	private static final long serialVersionUID = 2349545784944122525L;
	
	@Override
	public void onGoodsCodeDescrBtnAmend_NAMEButton() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCodeDescr_Button(this);
	}   
	
	@Override
	public void onGoodsCodeAmend_NAMEAutoSubmit() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCode_AutoSubmit(this);
	}

}
