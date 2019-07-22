package com.it.ocs.synchronou.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.dao.IEbaySellerListDao;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
public class PaypalTokenService {
	@Autowired
	private IEbaySellerListDao ebaySellerListDao;
	
	public String getPaypalAccessTokenByPaypalAccount(String account) throws PayPalRESTException{
		String token = ebaySellerListDao.getPaypalAccessTokenByPaypalAccount(account);
		if(null == token || "".equals(token)){
			Map<String,Object> paypalAccountInfo = ebaySellerListDao.getPaypalAccountInfo(account);
			String clientId = paypalAccountInfo.get("CLIENT").toString();
			String clientSecret =  paypalAccountInfo.get("SECRET").toString();
			APIContext apiContext = new APIContext(clientId,clientSecret,"live");
			token = apiContext.fetchAccessToken();
			paypalAccountInfo.put("token", token);
			paypalAccountInfo.put("account", account);
			ebaySellerListDao.addCurToken(paypalAccountInfo);
		}
		return token;
	}
}
