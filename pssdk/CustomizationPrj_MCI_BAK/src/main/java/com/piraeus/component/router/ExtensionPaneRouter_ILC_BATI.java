package com.piraeus.component.router;

import com.piraeus.component.ExtensionPaneRouterBase;

public class ExtensionPaneRouter_ILC_BATI extends ExtensionPaneRouterBase {

	private static final long serialVersionUID = 2349545784944122525L;
	
	@Override
	public void onGoodsCodeDescrBtnTransferLCBenRespAmend_BATIButton() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCodeDescr_Button(this);
	}   
	
	@Override
	public void onGoodsCodeTransferLCBenRespAmend_BATIAutoSubmit() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCode_AutoSubmit(this);
	}

}
