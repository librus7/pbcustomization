package com.piraeus.component.router;

import com.piraeus.component.ExtensionPaneRouterBase;

public class ExtensionPaneRouter_IDC_CRE extends ExtensionPaneRouterBase {

	private static final long serialVersionUID = 2349545784944122525L;
	
	@Override
	public void onGoodsCodeDescrBtnIDC_Create_CREButton() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCodeDescr_Button(this);
	}   
	
	@Override
	public void onGoodsCodeIDC_Create_CREAutoSubmit() {
		com.piraeus.component.mci.GoodsCodeDescrComponent.onGoodsCode_AutoSubmit(this);
	}

}
