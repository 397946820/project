package com.it.ocs.salesStatistics.service.impl;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cache.CurrencyRateCache;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.DataSources;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.DataSourceTypeManager;
import com.it.ocs.common.util.UserUtils;
import com.it.ocs.listener.CurrencyRateListener;
import com.it.ocs.salesStatistics.dao.ISalesStatisticsDao;
import com.it.ocs.salesStatistics.model.Footer;
import com.it.ocs.salesStatistics.model.SalesStatisticsModel;
import com.it.ocs.salesStatistics.service.IContrastService;
import com.it.ocs.salesStatistics.utils.TimeTools;
import com.it.ocs.salesStatistics.vo.SalesStatisticsVo;

@Service
public class ContrastService implements IContrastService {

	@Autowired
	private ISalesStatisticsDao salesStatisticsDao;

	private Map<String, List<SalesStatisticsModel>> map = new HashedMap();

	private String contrast = "contrast";

	private String oracle = "oracle";

	private String mysql = "mysql";

	private String[] strings = { "orderId", "sku" ,"status","station"};

	@Override
	public OperationResult uploadExcel(MultipartFile file) {

		OperationResult result = new OperationResult();

		try {
			InputStream inputStream = file.getInputStream();
			// 导入进来的数据
			List<SalesStatisticsModel> list = ImportExcelUtil.importExcel(SalesStatisticsModel.class, inputStream);
			if (!CollectionUtil.isNullOrEmpty(list)) {
				// 做一些处理
				CollectionUtil.sort(list, "updateddate", "asc");
				String start = list.get(0).getUpdateddate();
				String end = list.get(list.size() - 1).getUpdateddate();
				start = start.substring(0, start.lastIndexOf("T"));
				end = end.substring(0, end.lastIndexOf("T"));
				// 时间转换成date
				Date startTime = TimeTools.getTime(start);
				Date endTime = TimeTools.getTime(end);
				// 站点
				Set<String> stations = new HashSet<>();
				for (SalesStatisticsModel model : list) {
					// Non-Amazon的站点去掉
					if(!model.getStation().equals("Non-Amazon")) {
						stations.add(model.getStation());
					}
				}
				// oracle符合条件的
				List<SalesStatisticsModel> oracleModels = getModels(startTime,endTime,stations,"oracle");
				// mysql
				// 切换到mysql数据库
				DataSourceTypeManager.set(DataSources.SLAVE);
				// mysql符合条件的
				List<SalesStatisticsModel> mysqlModels = getModels(startTime,endTime,stations,"mysql");
				// 切换回到oracle
				DataSourceTypeManager.set(DataSources.MASTER);
				// 数据比对
				// 导入进来的时间做处理
				list = changeList(list);
				String[] strArr = { "orderId", "sku" };
				List<SalesStatisticsModel> diffrent1 = CollectionUtil.getDiffrent(list, oracleModels,
						SalesStatisticsModel.class, strArr);
				List<SalesStatisticsModel> diffrent2 = CollectionUtil.getDiffrent(list, mysqlModels,
						SalesStatisticsModel.class, strArr);
				List<SalesStatisticsModel> diffrent3 = CollectionUtil.getDiffrent(oracleModels, list,
						SalesStatisticsModel.class, strArr);
				List<SalesStatisticsModel> diffrent4 = CollectionUtil.getDiffrent(mysqlModels, list,
						SalesStatisticsModel.class, strArr);
				List<SalesStatisticsModel> diffrent5 = CollectionUtil.getDiffrent(mysqlModels, oracleModels,
						SalesStatisticsModel.class, strArr);
				Long userId = UserUtils.getUserId();
				map.put(contrast + userId, list);
				map.put(oracle + userId, oracleModels);
				map.put(mysql + userId, mysqlModels);
				// 比较的数据
				map.put(oracle + "_" + contrast + userId, diffrent1);
				map.put(mysql + "_" + contrast + userId, diffrent2);
				map.put(contrast + "_" + oracle + userId, diffrent3);
				map.put(contrast + "_" + mysql + userId, diffrent4);
				map.put(oracle + "_" + mysql + userId, diffrent5);
			}
		} catch (Exception e) {
			result.setDescription("导入失败");
			result.setErrorCode(1);
			throw new RuntimeException();
		}
		return result;
	}

	private List<SalesStatisticsModel> getModels(Date startTime, Date endTime, Set<String> stations, String string) throws ParseException {
		List<SalesStatisticsModel> list = new ArrayList<>();
		for (String key : stations) {
			Date start = TimeTools.getTimeByStation(startTime, key);
			Date end = TimeTools.getTimeByStation(endTime, key);
			if(string.equals("oracle")) {
				list.addAll(salesStatisticsDao.findModelsBySatationAndTime(key, start, end));
			} else if(string.equals("mysql")) {
				list.addAll(salesStatisticsDao.findByTime(key, start, end));
			}
		}
		return list;
	}

	private List<SalesStatisticsModel> changeList(List<SalesStatisticsModel> list) throws ParseException {

		for (SalesStatisticsModel model : list) {
			String purchasedate = model.getPurchasedate();
			String updateddate = model.getUpdateddate();
			Date purchase = stringToDate(purchasedate);
			Date updated = stringToDate(updateddate);
			model.setPurchaseat(purchase);
			model.setUpdatedat(updated);
			model.setCount(model.getQty());
		}
		return list;
	}

	private Date stringToDate(String string) throws ParseException {
		String date = string.substring(0, string.lastIndexOf("T")) + " "
				+ string.substring(string.lastIndexOf("T") + 1, string.lastIndexOf("+"));
		return TimeTools.getTime2(date);
	}

	@Override
	public ResponseResult<SalesStatisticsVo> findAllOriginal(RequestParam param) throws Exception {
		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo model = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);

		List<SalesStatisticsModel> models = new ArrayList<>();

		Long userId = UserUtils.getUserId();

		if (model == null) {
			model = new SalesStatisticsVo();
		}

		String flag = model.getFlag();

		if (flag == null || flag.equals("1") || flag.equals("3")  || flag.equals("5")) {
			// 展示全部
			models = map.get(contrast + userId);
		} else if (flag.equals("2")) {
			// 展示跟oracle对比没有的
			models = map.get(contrast + "_" + oracle + userId);
		} else if (flag.equals("4")) {
			// 展示跟mysql对比没有的数据
			models = map.get(contrast + "_" + mysql + userId);
		}

		changeResult(result,models,model,param,flag,"original");
		
		return result;
	}

	@Override
	public ResponseResult<SalesStatisticsVo> findAllOracle(RequestParam param) throws Exception {
		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo model = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);

		List<SalesStatisticsModel> models = new ArrayList<>();

		Long userId = UserUtils.getUserId();

		String flag = model.getFlag();

		if (flag.equals("1")) {
			// 展示跟导入对比没有的数据
			models = map.get(oracle + "_" + contrast + userId);
		} else if (flag.equals("2")  || flag.equals("6")) {
			// 展示全部
			models = map.get(oracle + userId);
		} else if(flag.equals("8")) {
			//展示跟mysql对比没有的数据
			models = map.get(oracle + "_" + mysql + userId);
		}

		changeResult(result,models,model,param,flag,"oracle");

		return result;
	}

	@Override
	public ResponseResult<SalesStatisticsVo> findAllMysql(RequestParam param) throws Exception {
		ResponseResult<SalesStatisticsVo> result = new ResponseResult<>();

		SalesStatisticsVo model = BeanConvertUtil.mapToObject(param.getParam(), SalesStatisticsVo.class);

		List<SalesStatisticsModel> models = new ArrayList<>();

		Long userId = UserUtils.getUserId();

		String flag = model.getFlag();

		if (flag.equals("3")) {
			// 展示跟导入对比没有的数据
			models = map.get(mysql + "_" + contrast + userId);
		} else if (flag.equals("4") || flag.equals("7")  || flag.equals("8")) {
			// 展示全部
			models = map.get(mysql + userId);
		}

		changeResult(result,models,model,param,flag,"mysql");

		return result;
	}
	
	private void changeResult(ResponseResult<SalesStatisticsVo> result, List<SalesStatisticsModel> models,
			SalesStatisticsVo model, RequestParam param,String flag, String string) throws Exception {
		List<SalesStatisticsModel> list = new ArrayList<>();
		
		for (SalesStatisticsModel salesStatisticsModel : models) {
			list.add(salesStatisticsModel);
		}

		list = CollectionUtil.queryByParam(list, model, strings,"like");
		
		if (param.getSort() != null && param.getOrder() != null) {
			CollectionUtil.sort(list, param.getSort(), param.getOrder());
		}

		List<SalesStatisticsModel> resultModels = CollectionUtil.pageList(list, param.getStartRow(), param.getEndRow());

		result.setRows(CollectionUtil.beansConvert(resultModels, SalesStatisticsVo.class));

		result.setTotal(list.size());
		
		List<Footer> footers = new ArrayList<>();
		Footer footer = new Footer();
		footers.add(footer);
		
		changeFooters(footers,list,flag,string);
		
		result.setFooter(footers);
		
	}

	private void changeFooters(List<Footer> footers, List<SalesStatisticsModel> list, String flag, String string) {
		Footer footer = new Footer();
		// 保留两位小数
		DecimalFormat sdf = new DecimalFormat("0.00");
		
		CurrencyRateListener.onApplicationEvent();
		//判断出按什么方式汇总
		Boolean bool = getFlag(flag,string);
		Map<String, Object> keys = new HashedMap();
		double sum = 0d;
		for (SalesStatisticsModel model : list) {
			if(StringUtils.isNotBlank(model.getCurrencycode())) {
				if(bool) {
					sum += (model.getPrice() + model.getItemTax() + model.getShippingPrice() + model.getShippingTax() + model.getGiftWrapTax() + model.getGiftWrapPrice()
					- model.getItemPromotionDiscount() - model.getShipPromotionDiscount()) * CurrencyRateCache.getCurrencyRate(model.getCurrencycode());
				} else {
					String orderId = model.getOrderId();
					if(!keys.containsKey(orderId)) {
						sum += model.getAmount()  * CurrencyRateCache.getCurrencyRate(model.getCurrencycode());
						keys.put(orderId, 1);
					}
				}
			}
		}
		footer.setCurrencycode(sdf.format(sum));
		footer.setPrice(sdf.format(sum * CurrencyRateCache.getCurrencyRate("RMB")));
		footers.add(footer);
	}

	private Boolean getFlag(String flag, String string) {
		Boolean bool = false;
		if(string.equals("original")) {
			if(flag == null || flag.equals("1") || flag.equals("3") || flag.equals("5")) {
				bool = true;
			}
		} else if(string.equals("oracle")) {
			if(!flag.equals("2") && !flag.equals("6") && !flag.equals("8")) {
				bool = true;
			}
		} else if (string.equals("mysql")) {
			if(!flag.equals("4") && !flag.equals("7") && !flag.equals("8")) {
				bool = true;
			}
		}
		return bool;
	}

}
