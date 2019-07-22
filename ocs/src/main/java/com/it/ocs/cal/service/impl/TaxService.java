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
import com.it.ocs.cal.dao.ITaxDao;
import com.it.ocs.cal.model.TaxModel;
import com.it.ocs.cal.service.ITaxService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.TaxVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class TaxService implements ITaxService {

	@Autowired
	protected ITaxDao taxDao;
	
	@Override
	public ResponseResult<TaxVo> findAll(RequestParam param, String string) {
		ResponseResult<TaxVo> result = new ResponseResult<>();
		param = Tools.getRequestParam(param);

		TaxVo tax = BeanConvertUtil.mapToObject(param.getParam(), TaxVo.class);

		if (StringUtils.isBlank(string)) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}

		List<TaxModel> list = taxDao.queryByPage(tax, param.getStartRow(), param.getEndRow(), param.getSort(),
				param.getOrder());

		if (StringUtils.isBlank(string)) {
			list = Tools.changeList(list);

			PageInfo<TaxModel> pageInfo = new PageInfo<>(list);

			List<TaxVo> rows = CollectionUtil.beansConvert(pageInfo.getList(), TaxVo.class);

			result.setRows(rows);

			result.setTotal((int) pageInfo.getTotal());
		} else {
			result.setRows(CollectionUtil.beansConvert(list, TaxVo.class));
		}

		return result;
	}

	@Override
	public OperationResult saveEdit(TaxVo tax) {
		OperationResult result = new OperationResult();
		tax.setUpdatedAt(new Date());
		try {
			if (tax.getEntityId() == null) {
				tax.setCreatedAt(new Date());
				taxDao.add(tax);
			} else {
				taxDao.update(tax);
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
		List<TaxModel> result = new ArrayList<>();
		try {
			if (template.equals("template")) {
				// 导出模板
				List<TaxModel> list = taxDao.findByTemplate();
				if (!CollectionUtil.isNullOrEmpty(list)) {
					result.add(list.get(0));
				}
				fileName = "税模板" + Utils.getFileName() + ".xlsx";
			} else {
				// 导出全部
				RequestParam param = RequestParamUtils.getRequestParam(template, TaxVo.class);
				ResponseResult<TaxVo> responseResult = findAll(param, "excel");
				result.addAll(CollectionUtil.beansConvert(responseResult.getRows(), TaxModel.class));
				fileName = "税数据" + Utils.getFileName() + ".xlsx";
			}
			ExportExcelUtil.writeExcel(response, request, fileName, result, TaxModel.class, template, null);
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
			List<TaxModel> list = ImportExcelUtil.importExcel(TaxModel.class, inputStream);
			for (TaxModel model : list) {
				model.setCreatedAt(new Date());
				model.setUpdatedAt(new Date());
				taxDao.add(model);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

}
