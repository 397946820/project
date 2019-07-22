package com.it.ocs.synchronou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.synchronou.model.EbayAccountModel;

public interface IEBayAccountDao {

	public List<EbayAccountModel> getAccounts();

	public EbayAccountModel getAccountModelByName(String account);
	
	public EbayAccountModel getAccountByAccount(@Param("account")String account);

}
