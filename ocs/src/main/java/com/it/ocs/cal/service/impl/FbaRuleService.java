package com.it.ocs.cal.service.impl;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.it.ocs.cal.dao.IFbaRuleDao;
import com.it.ocs.cal.model.FbaRuleModel;
import com.it.ocs.cal.service.IFbaRuleService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.FbaRuleVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.RequestParamUtils;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;

@Service
@Transactional
public class FbaRuleService implements IFbaRuleService {

	@Autowired
	protected IFbaRuleDao fbaRuleDao;

	@Override
	public ResponseResult<FbaRuleVo> findAll(RequestParam param, String string) {
		ResponseResult<FbaRuleVo> result = new ResponseResult<>();

		param = Tools.getRequestParam(param);

		FbaRuleVo fbaRule = BeanConvertUtil.mapToObject(param.getParam(), FbaRuleVo.class);

		if (StringUtils.isBlank(string)) {
			PageHelper.startPage(param.getPage(), param.getRows());
		}

		List<FbaRuleModel> list = fbaRuleDao.queryByPage(fbaRule, param.getStartRow(), param.getEndRow(),
				param.getSort(), param.getOrder());

		if (StringUtils.isBlank(string)) {
			list = Tools.changeList(list);

			PageInfo<FbaRuleModel> pageInfo = new PageInfo<>(list);

			List<FbaRuleVo> rows = CollectionUtil.beansConvert(pageInfo.getList(), FbaRuleVo.class);

			result.setTotal((int) pageInfo.getTotal());

			result.setRows(rows);
		} else {
			result.setRows(CollectionUtil.beansConvert(list, FbaRuleVo.class));
		}
		return result;
	}

	@Override
	public OperationResult saveEdit(FbaRuleVo fbaRule) {
		OperationResult result = new OperationResult();
		fbaRule.setUpdatedAt(new Date());
		try {
			if (fbaRule.getEntityId() == null) {
				fbaRule.setCreatedAt(new Date());
				fbaRuleDao.add(fbaRule);
			} else {
				fbaRuleDao.update(fbaRule);
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}

	// 导出
	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template) {
		// 输出的文件名称
		String fileName = "";
		// 要导出的
		List<FbaRuleModel> result = new ArrayList<>();
		try {
			if (template.equals("template")) {
				// 导出模板
				List<FbaRuleModel> list = fbaRuleDao.findByTemplate();
				if (!CollectionUtil.isNullOrEmpty(list)) {
					result.add(list.get(0));
				}
				fileName = "FBA规则模板" + Utils.getFileName() + ".xlsx";
			} else {
				// 导出全部
				RequestParam param = RequestParamUtils.getRequestParam(template, FbaRuleVo.class);
				ResponseResult<FbaRuleVo> responseResult = findAll(param, "excel");
				result.addAll(responseResult.getRows());
				fileName = "FBA规则数据" + Utils.getFileName() + ".xlsx";
			}
			ExportExcelUtil.writeExcel(response, request, fileName, result, FbaRuleModel.class, template, null);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	// 导入
	@SuppressWarnings("unchecked")
	@Override
	public OperationResult uploadExcel(MultipartFile file) {
		OperationResult result = new OperationResult();

		try {
			InputStream inputStream = file.getInputStream();
			List<FbaRuleModel> list = ImportExcelUtil.importExcel(FbaRuleModel.class, inputStream);
			// 获取当前月份
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String nowMonthOfYear = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			for (FbaRuleModel model : list) {
				if(!model.getPrice().contains("price")) {
					model.setPrice(new DecimalFormat("0.00").format(new Double(model.getPrice())));
				}
				model.setUpdatedAt(new Date());
				List<FbaRuleModel> subList = fbaRuleDao
						.queryByPage(new FbaRuleModel(model.getCountryId(), model.getSortOrder()), 0, 0, null, null);
				if (CollectionUtil.isNullOrEmpty(subList)) {
					model.setCreatedAt(new Date());
					fbaRuleDao.add(model);
				} else {
					CollectionUtil.each(subList, new IAction<FbaRuleModel>() {
						@Override
						public void excute(FbaRuleModel fba) {
							if(Arrays.asList(fba.getIsMonth().split(",")).contains(nowMonthOfYear)) {
								model.setEntityId(fba.getEntityId());
								fbaRuleDao.update(model);
							}
						}
					});
				}
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException(e);
		}
		return result;
	}

}
