package com.it.ocs.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.sys.dao.IOperatingProfitViewDao;
import com.it.ocs.sys.model.OperatingProfitViewModel;
import com.it.ocs.sys.service.IOperatingProfitViewService;
import com.it.ocs.sys.utils.CodeUtils;
import com.it.ocs.sys.vo.OperatingProfitViewVo;

@Service
public class OperatingProfitViewService implements IOperatingProfitViewService {

	Logger logger = Logger.getLogger(this.getClass());

	private static Map<String, Object> combo = Maps.newHashMap();
	static {
		combo.put("VALUE", "全选/反选");
		combo.put("NAME", "");
	}

	@Autowired
	private IOperatingProfitViewDao operatingProfitViewDao;

	@Override
	public ResponseResult<OperatingProfitViewVo> findAll(RequestParam param) {
		ResponseResult<OperatingProfitViewVo> result = new ResponseResult<>();
		String code = CodeUtils.getCode();
		if (code == null) {
			return result;
		}
		String flag = "1";
		Map<String, Object> map = getParam(param.getParam());
		if (map.size() == 1 && map.keySet().contains("other")) {
			flag = "2";
		}
		map.put("flag", flag);
		map.put("code", code);
		map.put("userId", UserUtils.getUserId());
		map.put("count", 0);
		PageHelper.startPage(param.getPage(), param.getRows());
		List<OperatingProfitViewModel> list = operatingProfitViewDao.findAll(map,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<OperatingProfitViewModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), OperatingProfitViewVo.class));
		result.setTotal((int) pageInfo.getTotal());
		result.setSource(code);
		if (!CollectionUtil.isNullOrEmpty(result.getRows())) {
			map.put("count", 1);
			result.getRows().add(operatingProfitViewDao.getFooter(map));
		}
		return result;
	}

	@Override
	public Integer refreshBefore() {
		return operatingProfitViewDao.refreshBefore();
	}

	@Override
	public OperationResult refresh() {
		OperationResult result = new OperationResult();
		try {
			operatingProfitViewDao.refresh();
			return result;
		} catch (Exception e) {
			logger.error("经营利润报表刷新数据失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public OperationResult sure() {
		OperationResult result = new OperationResult();
		try {
			operatingProfitViewDao.sure();
			return result;
		} catch (Exception e) {
			logger.error("经营利润报表确认数据失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public OperationResult exportBefore(Map<String, Object> map) {
		OperationResult result = new OperationResult();
		String string = operatingProfitViewDao.exportBefore(map);
		result.setData(string);
		return result;
	}

	@Override
	public OperationResult generateDataBefore() {
		OperationResult result = new OperationResult();
		result.setErrorCode(operatingProfitViewDao.generateDataBefore());
		return result;
	}

	@Override
	public OperationResult generateData() {
		OperationResult result = new OperationResult();
		operatingProfitViewDao.generateData();
		return result;
	}

	@Override
	public OperationResult mappingSku() {
		OperationResult result = new OperationResult();
		result.setErrorCode(operatingProfitViewDao.mappingSku());
		return result;
	}

	@Override
	public OperationResult categorySku() {
		OperationResult result = new OperationResult();
		result.setErrorCode(operatingProfitViewDao.categorySku());
		return result;
	}

	@Override
	public OperationResult syncCategory() {
		OperationResult result = new OperationResult();
		operatingProfitViewDao.syncCategory();
		return result;
	}

	@Override
	public Map<String, Object> findAmazonFee(RequestParam param) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("rows", operatingProfitViewDao.queryAmazonFee(getParam(param.getParam()), param.getStartRow(),
				param.getEndRow()));
		result.put("total", operatingProfitViewDao.getCount(getParam(param.getParam())));
		result.put("footer", operatingProfitViewDao.getAmazonFeeFooter(getParam(param.getParam())));
		return result;
	}

	@Override
	public List<Map<String, Object>> getCategory() {
		List<Map<String, Object>> list = Lists.newArrayList();
		list.add(combo);
		String code = CodeUtils.getCode();
		if (code != null) {
			list.addAll(operatingProfitViewDao.getCategoryByCode(code, UserUtils.getUserId()));
			if(!code.equals("3") && !code.equals("7")) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("VALUE", "其他");
				map.put("NAME", "其他");
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getSite() {
		List<Map<String, Object>> result = Lists.newArrayList();
		result.add(combo);
		String code = CodeUtils.getCode();
		if (code != null) {
			List<Map<String, Object>> list = operatingProfitViewDao.getSiteByCode(code, UserUtils.getUserId());
			CollectionUtil.each(list, new IAction<Map<String, Object>>() {
				@Override
				public void excute(Map<String, Object> map) {
					map.put("VALUE", Tools.getCountry(map.get("VALUE").toString()));
					result.add(map);
				}
			});
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getSkuByCategory(Map<String, Object> map) {
		List<Map<String, Object>> result = Lists.newArrayList();
		result.add(combo);
		result.addAll(operatingProfitViewDao.getSkuByCategory(map.get("category")));
		return result;
	}

	@Override
	public List<Map<String, Object>> getPersonnelByDepartment(Map<String, Object> map) {
		List<Map<String, Object>> result = Lists.newArrayList();
		result.add(combo);
		result.addAll(operatingProfitViewDao.getPersonnelByDepartment(map.get("department").toString()));
		return result;
	}

	public Map<String, Object> getParam(Map<String, Object> param) {
		Map<String, Object> result = Maps.newHashMap();
		String site = "";
		if (param != null && param.size() > 0) {
			for (Object object : param.keySet()) {
				if (object.toString().equals("site")) {
					site = param.get(object).toString();
				}
				if (StringUtils.isNotBlank(param.get(object).toString())) {
					if (object.toString().equals("department") || object.toString().equals("monthOfYear")) {
						result.put(object.toString(), param.get(object));
					} else {
						result.put(object.toString(), param.get(object).toString().split(","));
					}
				}
			}
			if (site.equals("")) {
				result.put("other", 0);
			} else {
				result.put("other", 1);
			}
		} else {
			result.put("other", 0);
		}
		return result;
	}
}
