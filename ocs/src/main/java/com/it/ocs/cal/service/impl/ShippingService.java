package com.it.ocs.cal.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.common.model.ShippingFeeModel;
import com.it.ocs.cal.common.model.StorageCostModel;
import com.it.ocs.cal.dao.IShippingDao;
import com.it.ocs.cal.model.ShippingModel;
import com.it.ocs.cal.service.IShippingService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.ShippingVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class ShippingService extends IBaseService implements IShippingService {

	@Autowired
	protected IShippingDao shippingDao;
	
	@Override
	public ResponseResult<ShippingVo> findAll(RequestParam param, String string) {
		ResponseResult<ShippingVo> result = new ResponseResult<>();

		param = Tools.getRequestParam(param);

		ShippingVo shipping = BeanConvertUtil.mapToObject(param.getParam(), ShippingVo.class);

		if (StringUtils.isBlank(string)) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}

		List<ShippingModel> list = shippingDao.queryByPage(shipping, param.getStartRow(), param.getEndRow(),
				param.getSort(), param.getOrder());

		if (StringUtils.isBlank(string)) {

			PageInfo<ShippingModel> pageInfo = new PageInfo<>(list);

			List<ShippingVo> rows = CollectionUtil.beansConvert(pageInfo.getList(), ShippingVo.class);

			result.setRows(rows);

			result.setTotal((int) pageInfo.getTotal());
		} else {
			result.setRows(CollectionUtil.beansConvert(list, ShippingVo.class));
		}

		return result;
	}

	@Override
	public OperationResult saveEdit(ShippingVo shipping) {
		OperationResult result = new OperationResult();
		try {
			shipping.setUpdatedAt(new Date());
			if (shipping.getEntityId() == null) {
				shipping.setCreatedAt(new Date());
				shippingDao.add(shipping);
			} else {
				shippingDao.update(shipping);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template) {
		// 输出的文件名称
		String fileName = "";
		// 要导出的
		List<ShippingVo> rows = new ArrayList<>();
		try {
			if (template.equals("template")) {
				// 导出模板
				List<ShippingModel> list = shippingDao.findByTemplate();
				if (!CollectionUtil.isNullOrEmpty(list)) {
					ShippingModel model = list.get(0);
					ShippingVo vo = new ShippingVo();
					BeanUtils.copyProperties(model, vo);
					rows.add(vo);
				}
				fileName = "运费模板" + Utils.getFileName() + ".xlsx";
			} else {
				// 导出全部
				RequestParam param = RequestParamUtils.getRequestParam(template, ShippingVo.class);
				ResponseResult<ShippingVo> responseResult = findAll(param, "excel");
				rows.addAll(responseResult.getRows());
				fileName = "运费数据" + Utils.getFileName() + ".xlsx";
			}
			ShippingFeeModel af = null;
			ShippingFeeModel sf = null;
			ShippingFeeModel co = null;
			StorageCostModel cost = null;
			for (ShippingVo model : rows) {
				af = JSON.parseArray(model.getAfShippingFee(), ShippingFeeModel.class).get(0);
				sf = JSON.parseArray(model.getSfShippingFee(), ShippingFeeModel.class).get(0);
				co = JSON.parseArray(model.getCoShippingFee(), ShippingFeeModel.class).get(0);
				cost = JSON.parseArray(model.getStorageCost(), StorageCostModel.class).get(0);
				// 空运
				model.setAf_cost(af.getCost());
				model.setAf_currency(af.getCurrency());
				model.setAf_cost_fluctuation(af.getCost_fluctuation());
				model.setAf_profit_rate(af.getProfit_rate());
				model.setAf_multi_profit_rate(af.getMulti_profit_rate());

				// 海运
				model.setSf_cost(sf.getCost());
				model.setSf_currency(sf.getCurrency());
				model.setSf_cost_fluctuation(sf.getCost_fluctuation());
				model.setSf_profit_rate(sf.getProfit_rate());
				model.setSf_multi_profit_rate(sf.getMulti_profit_rate());

				// 快递
				model.setCo_cost(co.getCost());
				model.setCo_currency(co.getCurrency());
				model.setCo_cost_fluctuation(co.getCost_fluctuation());
				model.setCo_profit_rate(co.getProfit_rate());
				model.setCo_multi_profit_rate(co.getMulti_profit_rate());

				// 仓储成本费
				model.setCost_month(cost.getMonth());
				model.setCost_standard_size(cost.getStandard_size());
				model.setCost_over_size(cost.getOver_size());
				model.setCost_param_one(cost.getParam_one());
			}

			ExportExcelUtil.writeExcel(response, request, fileName, rows, ShippingVo.class, template, null);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@SuppressWarnings({ "unchecked", "unused", "null" })
	@Override
	public OperationResult uploadExcel(MultipartFile file) {
		OperationResult result = new OperationResult();
		try {
			InputStream inputStream = file.getInputStream();
			List<ShippingVo> list = ImportExcelUtil.importExcel(ShippingVo.class, inputStream);

			ShippingFeeModel fee = new ShippingFeeModel();
			List<ShippingFeeModel> fees = new ArrayList<>();
			StorageCostModel cost = new StorageCostModel();
			List<StorageCostModel> costs = new ArrayList<>();

			for (ShippingVo model : list) {

				// 空运
				fee.setCost(model.getAf_cost());
				fee.setCurrency(model.getAf_currency());
				fee.setCost_fluctuation(model.getAf_cost_fluctuation());
				fee.setProfit_rate(model.getAf_profit_rate());
				fee.setMulti_profit_rate(model.getAf_multi_profit_rate());
				fees.add(fee);
				model.setAfShippingFee(JSON.toJSONString(fees));
				fee = new ShippingFeeModel();
				fees.clear();

				// 海运
				fee.setCost(model.getSf_cost());
				fee.setCurrency(model.getSf_currency());
				fee.setCost_fluctuation(model.getSf_cost_fluctuation());
				fee.setProfit_rate(model.getSf_profit_rate());
				fee.setMulti_profit_rate(model.getSf_multi_profit_rate());
				fees.add(fee);
				model.setSfShippingFee(JSON.toJSONString(fees));
				fee = new ShippingFeeModel();
				fees.clear();

				// 快递
				fee.setCost(model.getCo_cost());
				fee.setCurrency(model.getCo_currency());
				fee.setCost_fluctuation(model.getCo_cost_fluctuation());
				fee.setProfit_rate(model.getCo_profit_rate());
				fee.setMulti_profit_rate(model.getCo_multi_profit_rate());
				fees.add(fee);
				model.setCoShippingFee(JSON.toJSONString(fees));
				fee = new ShippingFeeModel();
				fees.clear();

				// 仓储成本
				cost.setMonth(model.getCost_month());
				cost.setStandard_size(model.getCost_standard_size());
				cost.setOver_size(model.getCost_over_size());
				cost.setParam_one(model.getCost_param_one());
				costs.add(cost);
				model.setStorageCost(JSON.toJSONString(costs));
				cost = new StorageCostModel();
				costs.clear();

				model.setCreatedAt(new Date());
				model.setUpdatedAt(new Date());
				shippingDao.add(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}
}
