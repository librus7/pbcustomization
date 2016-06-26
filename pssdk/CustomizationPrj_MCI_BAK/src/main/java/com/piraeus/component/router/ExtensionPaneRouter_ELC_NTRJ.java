package com.piraeus.component.router;

import com.piraeus.component.ExtensionPaneRouterBase;

public class ExtensionPaneRouter_ELC_NTRJ extends ExtensionPaneRouterBase {

	private static final long serialVersionUID = 2349545784944122525L;
	
	@Override
	public void onGoodsCodeDescrBtnTransferLC_Adjust_NTRJButton() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCodeDescr_Button(this);
	}   
	
	@Override
	public void onGoodsCodeTransferLC_Adjust_NTRJAutoSubmit() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCode_AutoSubmit(this);
	}

}
