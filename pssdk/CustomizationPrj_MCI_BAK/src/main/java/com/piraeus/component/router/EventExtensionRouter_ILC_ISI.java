package com.piraeus.component.router;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.piraeus.component.EventExtensionRouterBase;
import com.piraeus.component.mci.BICKeysCheck;

public class EventExtensionRouter_ILC_ISI extends EventExtensionRouterBase {

	private static final Logger LOG = LoggerFactory.getLogger(EventExtensionRouter_ILC_ISI.class);
	
	@Override
	protected void onValidateExt(
			EventExtensionRouterBase eventExtensionRouterBase,
			ValidationDetails validationDetails) {
		//
		//LegalDeclComponent.onValidate(this, validationDetails);
		
		try {
			com.piraeus.component.mci.GoodsCodeDescrComponent.onValidate(this, validationDetails);
		} catch (Exception e) {
			LOG.error("Exception in onValidateExt::GoodsCodeDescrComponent.onValidate: " + e.getMessage(), e);
		}
		
		try {
			com.piraeus.component.mci.BICKeysCheck.onValidate(this, validationDetails);
		} catch (Exception e) {
			LOG.error("Exception in onValidateExt::BICKeysCheck.onValidate: " + e.getMessage(), e);
		}
		
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
		BICKeysCheck.onPostInitialise(eventExtensionRouterBase);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void dataCapturedExt(
			EventExtensionRouterBase eventExtensionRouterBase) {
		// TODO Auto-generated method stub

	}

}
