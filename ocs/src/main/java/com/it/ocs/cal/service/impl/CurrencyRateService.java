package com.it.ocs.cal.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.ICurrencyRateDao;
import com.it.ocs.cal.model.CurrencyRateModel;
import com.it.ocs.cal.service.ICurrencyRateService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.CurrencyRateVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class CurrencyRateService implements ICurrencyRateService {

	@Autowired
	protected ICurrencyRateDao currencyRateDao;
	
	@Override
	public ResponseResult<CurrencyRateVo> findAll(RequestParam param, String string) {
		ResponseResult<CurrencyRateVo> result = new ResponseResult<>();

		param = Tools.getRequestParam(param);

		CurrencyRateVo rate = BeanConvertUtil.mapToObject(param.getParam(), CurrencyRateVo.class);

		if (StringUtils.isBlank(string)) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}

		List<CurrencyRateModel> list = currencyRateDao.queryByPage(rate, param.getStartRow(), param.getEndRow(),
				param.getSort(), param.getOrder());


		if (StringUtils.isBlank(string)) {
			
			list = Tools.changeList(list);

			PageInfo<CurrencyRateModel> pageInfo = new PageInfo<>(list);

			List<CurrencyRateVo> rows = CollectionUtil.beansConvert(pageInfo.getList(), CurrencyRateVo.class);

			result.setRows(rows);

			result.setTotal((int) pageInfo.getTotal());
		} else {
			result.setRows(CollectionUtil.beansConvert(list, CurrencyRateVo.class));
		}

		return result;
	}

	@Override
	public OperationResult saveEdit(CurrencyRateVo currencyRate) {
		OperationResult result = new OperationResult();

		try {
			currencyRate.setUpdatedAt(new Date());
			if (currencyRate.getEntityId() == null) {
				currencyRate.setCreatedAt(new Date());
				currencyRateDao.add(currencyRate);
			} else {
				currencyRateDao.update(currencyRate);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}

		return result;
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template) {
		// 输出的文件名称
		String fileName = "";
		// 要导出的
		List<CurrencyRateModel> result = new ArrayList<>();
		try {
			if (template.equals("template")) {
				// 导出模板
				List<CurrencyRateModel> list = currencyRateDao.findByTemplate();
				if (!CollectionUtil.isNullOrEmpty(list)) {
					result.add(list.get(0));
				}
				fileName = "货币汇率模板" + Utils.getFileName() + ".xlsx";
			} else {
				// 导出全部
				RequestParam param = RequestParamUtils.getRequestParam(template, CurrencyRateVo.class);
				ResponseResult<CurrencyRateVo> responseResult = findAll(param, "excel");
				result.addAll(CollectionUtil.beansConvert(responseResult.getRows(), CurrencyRateModel.class));
				fileName = "货币汇率数据" + Utils.getFileName() + ".xlsx";
			}

			ExportExcelUtil.writeExcel(response, request, fileName, result, CurrencyRateModel.class, template, null);
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult uploadExcel(MultipartFile file) {
		OperationResult result = new OperationResult();

		try {
			InputStream inputStream = file.getInputStream();
			List<CurrencyRateModel> list = ImportExcelUtil.importExcel(CurrencyRateModel.class, inputStream);
			for (CurrencyRateModel model : list) {
				model.setCreatedAt(new Date());
				model.setUpdatedAt(new Date());
				currencyRateDao.add(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public List<CurrencyRateModel> findByTemplate() {
		return currencyRateDao.findByTemplate();
	}
}
