package com.piraeus.component.router;

import java.util.Date;

import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.piraeus.component.EventExtensionRouterBase;
import com.piraeus.component.LegalDeclComponent;

public class EventExtensionRouter_ICC_NCAJ extends EventExtensionRouterBase {

	@Override
	protected void onValidateExt(
			EventExtensionRouterBase eventExtensionRouterBase,
			ValidationDetails validationDetails) {
		// 
		com.piraeus.component.mci.GoodsCodeDescrComponent.onValidate(this, validationDetails);

	}

	@Override
	protected void initialiseUserFieldCodesExt(
			EventExtensionRouterBase eventExtensionRouterBase) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Date getUserFieldCodeDateExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Boolean getUserFieldCodeBooleanExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Integer getUserFieldCodeIntegerExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUserFieldCodeStringExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUserFieldCodeAmountExt(
			EventExtensionRouterBase eventExtensionRouterBase, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean onSavingExt(
			EventExtensionRouterBase eventExtensionRouterBase) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onPostInitialiseExt(
			EventExtensionRouterBase eventExtensionRouterBase) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void dataCapturedExt(
			EventExtensionRouterBase eventExtensionRouterBase) {
		// TODO Auto-generated method stub

	}

}
