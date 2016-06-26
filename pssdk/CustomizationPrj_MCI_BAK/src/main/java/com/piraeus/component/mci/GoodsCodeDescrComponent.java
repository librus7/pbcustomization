package com.piraeus.component.mci;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.misys.tiplus2.customisation.entity.PIRGOODSFULL;
import com.misys.tiplus2.enigma.customisation.validation.ValidationDetails;
import com.misys.tiplus2.foundations.lang.logging.Loggers;
import com.piraeus.component.PaneRouterCallbacks;
import com.piraeus.component.RouterCallbacks;

/**
 * @author Spiros Tsanos
*/
public class GoodsCodeDescrComponent {

	private static final String ZONE_DATASOURCE = "jdbc/zone";
	private static final String GOODS_CODE = "goodsCode";
	private static final String GOODS_FULL_SHORT_DESCRIPTION = "goodsFullShortDescription";
	private static final String GOODS_SHORT_DESCRIPTION_COLUMN = "SHORTDSC";
	private static final String GOODS_CODES_FULL_DESCR_TABLE = "PIRGOODSFULL";
	
	private static final Logger LOG = LoggerFactory.getLogger(GoodsCodeDescrComponent.class);
	
	public static void onValidate(
			RouterCallbacks callback,
			ValidationDetails validationDetails) {
		Loggers.method().enter(LOG);
		
		String goodsFullShortDescription = (String) callback.getPaneCallbacks().getCustomField(GOODS_FULL_SHORT_DESCRIPTION);
	
		if (StringUtils.isBlank(goodsFullShortDescription)){
			
			String goodsCode = (String)callback.getPaneCallbacks().getCustomField(GOODS_CODE);
			
			goodsFullShortDescription = findGoodsFullShortDescr(callback.getPaneCallbacks(), goodsCode);

			if (!StringUtils.isBlank(goodsFullShortDescription)){
				callback.setCustomField(GOODS_FULL_SHORT_DESCRIPTION, goodsFullShortDescription);
				callback.getPaneCallbacks().setCustomField(GOODS_FULL_SHORT_DESCRIPTION, callback.getCustomField(GOODS_FULL_SHORT_DESCRIPTION));
			}
		}
		
		Loggers.method().exit(LOG);
	}
	
	public static void onGoodsCodeDescr_Button(
			PaneRouterCallbacks paneCallbacks) {
		
		Loggers.method().enter(LOG);
		
		String goodsCode = (String)paneCallbacks.getCustomField(GOODS_CODE);
		String goodsFullShortDescription = findGoodsFullShortDescr(paneCallbacks, goodsCode);
		if (!StringUtils.isBlank(goodsFullShortDescription)){
			paneCallbacks.setCustomField(GOODS_FULL_SHORT_DESCRIPTION, goodsFullShortDescription);
		}
		
		Loggers.method().exit(LOG);
		
	}
	
	
	public static void onGoodsCode_AutoSubmit(
			PaneRouterCallbacks paneCallbacks) {
		
		Loggers.method().enter(LOG);
		
		String goodsCode = (String)paneCallbacks.getCustomField(GOODS_CODE);
		String goodsFullShortDescription = findGoodsFullShortDescr(paneCallbacks, goodsCode);
		if (!StringUtils.isBlank(goodsFullShortDescription)){
			paneCallbacks.setCustomField(GOODS_FULL_SHORT_DESCRIPTION, goodsFullShortDescription);
		}
		
		Loggers.method().exit(LOG);
	}
	
	
	
	private static String findGoodsFullShortDescr(PaneRouterCallbacks paneCallbacks, String goodsCode){
		Loggers.method().enter(LOG);
		
		String goodsFullShortDescription = "";

		try {
			//adhoc query
			PIRGOODSFULL pirgoodsfull = paneCallbacks.createQuery_PIRGOODSFULL(GOODS_CODES_FULL_DESCR_TABLE, String.format("CODE = '%s'", goodsCode.trim() ));
			if (null!=pirgoodsfull){
				goodsFullShortDescription = pirgoodsfull.getShortdsc();
			}
			
			return goodsFullShortDescription;
		
		} catch (Exception e) {
			throw new RuntimeException("exception getting goods code short description", e);
		}		
		finally {
			Loggers.method().exit(LOG);
		}
	
	}


}
