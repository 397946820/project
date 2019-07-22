package com.it.ocs.fourPX.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.excel.service.AExcelImport;
import com.it.ocs.fourPX.dao.SellerInventoryLogDao;
import com.it.ocs.fourPX.service.FpxServiceUtils;
import com.it.ocs.fourPX.vo.FlowUploadVO;
import com.it.ocs.sys.model.UserModel;
import com.it.ocs.sys.service.IUserService;

@Service("inventoryFlowCodeTypeUpload")
public class InventoryFlowCodeTypeUploadService extends AExcelImport {
	@Autowired
	private SellerInventoryLogDao sellerInventoryLogDao;
	@Autowired
	private IUserService userService;

	@Override
	protected boolean validate(Map<String, Object> row, List<String> errorsMsg) {
		return true;
	}

	@Override
	protected boolean isExist(Map<String, Object> row, List<String> errorsMsg) {
		return true;
	}

	@Override
	protected int insert(List<Map<String, Object>> rows, List<String> errorsMsg) {
		return 0;
	}
	
	private static final String FORMAT_CODETYPEDETAIL_ERROR = "导入失败：第%s行业务类型细分值[%s]错误。（正确取值范围：%s）";

	@Override
	protected int update(List<Map<String, Object>> rows, List<String> errorsMsg) {
		UserModel concurrentUser = userService.getConcurrentUser();
		
		if(!CollectionUtil.isNullOrEmpty(rows)) {
			String ct;
			for (int i = 0; i < rows.size(); i++) {
				ct = (String) rows.get(i).get("codeType");
				if(!FpxServiceUtils.validCodetypedetail(ct)) {
					throw new RuntimeException(String.format(FORMAT_CODETYPEDETAIL_ERROR, (i + 1), ct, Arrays.toString(FpxServiceUtils.codetypedetails)));
				}
			}
			
			for (Map<String, Object> row : rows) {
				row.put("lastUpdateBy", concurrentUser.getId());
				sellerInventoryLogDao.batchUpdate(row);
			}
		}
		
		return 0;
	}

	@Override
	protected int refresh(List<Map<String, Object>> rows) {
		return 0;
	}

	@Override
	protected Class<?> getTemplateClazz(HttpServletRequest request) {
		return FlowUploadVO.class;
	}
}
