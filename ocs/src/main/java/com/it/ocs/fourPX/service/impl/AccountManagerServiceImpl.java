package com.it.ocs.fourPX.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.it.ocs.fourPX.dao.AccountManagerDao;
import com.it.ocs.fourPX.service.AccountManagerService;
import com.it.ocs.fourPX.utils.ReflectUtils;
import com.it.ocs.fourPX.vo.FourPXRequestVO;

@Service
public class AccountManagerServiceImpl implements AccountManagerService {
	 

	@Autowired
	private AccountManagerDao accountManagerDao;

	@Override
	public FourPXRequestVO selectOneByCode(String code) {
		return this.selectByArea(code, "US");
	}
	
	@Override
	public FourPXRequestVO selectDeOneByCode(String code) {
		return this.selectByArea(code, "DE");
	}

	private FourPXRequestVO selectByArea(String code, String busarea) {
		Assert.hasText(code, "The parameter 'code' is null or empty or blank.");
		
		Map<String, Object> one = null;
		if("DE".equals(busarea)) {
			one = this.accountManagerDao.selectDeOneByCode(code);
		} else {
			one = this.accountManagerDao.selectOneByCode(code);
		}
		if(null == one || one.isEmpty()) {
			return null;
		}
		
		FourPXRequestVO vo = new FourPXRequestVO();
		ReflectUtils.fillingTarget(vo, one, true, false);
		return vo;
	}
}
