package com.it.ocs.fourPX.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.support.IAction;
import com.it.ocs.excel.service.AExcelDynamicExport;
import com.it.ocs.fourPX.dao.SellerInventoryLogDao;
import com.it.ocs.fourPX.service.FpxServiceUtils;
import com.it.ocs.fourPX.vo.SellerInventoryLogExportVO;


@Service("sellerInventoryLogExport")
public class SellerInventoryLogExportService extends AExcelDynamicExport {

	@Autowired
	private SellerInventoryLogDao sellerInventoryLogDao;
	
	@Override
	protected List<Map<String, Object>> getData(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			param.put(entry.getKey(), null == entry.getValue() || entry.getValue().length == 0 ? null : entry.getValue()[0]);
		}
		
		List<Map<String, Object>> data = this.sellerInventoryLogDao.select(param);
		FpxServiceUtils.groupByUnqualified(data, new IAction<Map<String,Object>>() {
			@Override
			public void excute(Map<String, Object> map) {
				String codeType = (String) map.get(SellerInventoryLogExportVO.CODETYPE);
				map.put(SellerInventoryLogExportVO.CODETYPE, FpxServiceUtils.formatCodeType(codeType));
			}
		});
		return data;
	}

	@Override
	protected void init(HttpServletRequest request) {
		String filename = String.format("%s_%s.xlsx", FpxServiceUtils.GET_SELLER_INVENTORY_LOG, new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		String paramIncludeFields = request.getParameter("includeFields");
		String[] includeFields = StringUtils.isBlank(paramIncludeFields) ? null : paramIncludeFields.split(",");
		super.initModel(SellerInventoryLogExportVO.class, filename, includeFields);
	}

}
