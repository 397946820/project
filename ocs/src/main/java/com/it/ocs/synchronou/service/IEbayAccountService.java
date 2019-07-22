package com.it.ocs.synchronou.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.synchronou.model.EbayAccountModel;

public interface IEbayAccountService {
	public List<EbayAccountModel> getAccounts();
	public EbayAccountModel getAccountModelByName(String account);
	public EbayAccountModel getAccountByAccount(String account);

}
