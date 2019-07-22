package com.it.ocs.cal.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ICurrencyRateDao;
import com.it.ocs.cal.dao.ISmallTaximeterDao;
import com.it.ocs.cal.dao.ISmallTypeDao;
import com.it.ocs.cal.model.SmallTaximeterModel;
import com.it.ocs.cal.service.ISmallTaximeterService;
import com.it.ocs.cal.vo.SmallTaximeterVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
public class SmallTaximeterService implements ISmallTaximeterService {

	Logger logger = Logger.getLogger(this.getClass());

	private final static DecimalFormat SDF = new DecimalFormat("0.0000");

	@Autowired
	private ISmallTaximeterDao smallTaximeterDao;

	@Autowired
	private ISmallTypeDao smallTypeDao;
	
	@Autowired
	private ICurrencyRateDao currencyRateDao;

	@Override
	public ResponseResult<SmallTaximeterVo> findAll(RequestParam param) {
		ResponseResult<SmallTaximeterVo> result = new ResponseResult<>();

		SmallTaximeterModel smallTaximeter = BeanConvertUtil.mapToObject(param.getParam(), SmallTaximeterModel.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<SmallTaximeterModel> list = smallTaximeterDao.query(smallTaximeter,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<SmallTaximeterModel> pageInfo = new PageInfo<>(list);

		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), SmallTaximeterVo.class));

		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult editSmallTaximeter(SmallTaximeterModel model) {
		OperationResult result = new OperationResult();
		SmallTaximeterModel smallTaximeter = new SmallTaximeterModel();
		smallTaximeter.setEntityId(model.getEntityId());
		SmallTaximeterModel param = smallTaximeterDao.queryByParam(smallTaximeter);
		Double currencyRate = 1d;
		if(StringUtils.isNotBlank(model.getCurrencyCode())) {
			currencyRate = currencyRateDao.getCurrencyRateByCountry(model.getCurrencyCode());
			param.setFinalPrice(new Double(SDF.format(param.getFinalPrice() / currencyRate)));
		}
		if ((double) model.getFinalPrice() != (double) param.getFinalPrice()) {
			model.setProfitRateAction(new Double(SDF.format(
					((1 - (new Double(SDF.format(param.getFinalCost() / currencyRate)) * (1 + param.getVat())) / model.getFinalPrice()) / (1 + param.getVat())
							- param.getReferralRate() - param.getPaypalFee() - param.getAdvertisingRate())
							/ param.getProfitRate())));
			model.setQty(param.getQty());
			model.setFinalPrice(new Double(SDF.format(model.getFinalPrice() * currencyRate)));
		} else {
			if ((double) model.getProfitRateAction() != (double) param.getProfitRateAction()) {
				model.setQty(param.getQty());
				param.setProfitRateAction(model.getProfitRateAction());
			} else if ((int) model.getQty() != (int) param.getQty()) {
				model.setProfitRateAction(param.getProfitRateAction());
				param.setQty(model.getQty());
			}
			smallTaximeterDao.calculation(param);
			if (param.getPlatform().equals("ebay") && StringUtils.isNotBlank(param.getTradingMode())
					&& param.getTradingMode().equals("0")) {
				model.setFinalPrice(param.getFinalPrice_());
			} else {
				model.setFinalPrice(param.getFinalPrice());
			}
		}
		if (param.getFinalPrice() == 0) {
			result.setErrorCode(1);
			logger.info("小包计价器推算失败[小包类型--" + param.getTypeName() + " ,运输方式--" + param.getShippingType() + " ,SKU--"
					+ param.getSku() + " ,数量--" + param.getQty() + " ,利润系数--" + param.getProfitRateAction() + "]!");
		} else {
			model.setFinalCost(param.getFinalCost());
			smallTaximeterDao.update(model);
			smallTaximeter.setCurrencyCode(model.getCurrencyCode());
			model = smallTaximeterDao.queryByParam(smallTaximeter);
			result.setData(model);
			logger.info("小包计价器推算成功[小包类型--" + param.getTypeName() + " ,运输方式--" + param.getShippingType() + " ,SKU--"
					+ param.getSku() + " ,数量--" + param.getQty() + " ,利润系数--" + param.getProfitRateAction() + "]!");
		}
		return result;
	}

	@Override
	public OperationResult refresh() {
		OperationResult result = new OperationResult();
		try {
			smallTaximeterDao.refreshAll();
			logger.info("小包计价器刷新成功!");
		} catch (Exception e) {
			result.setErrorCode(1);
			logger.error("小包计价器刷新失败!", e);
		}
		return result;
	}
}
