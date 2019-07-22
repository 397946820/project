package com.it.ocs.synchronou.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.ocs.synchronou.dao.IEBayAccountDao;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.service.IEbayAccountService;

@Service
public class EbayAccountService implements IEbayAccountService{
	private static final Logger log = Logger.getLogger(EbayAccountService.class);
	@Autowired
	private IEBayAccountDao eBayAccountDao;
    @Override
	public List<EbayAccountModel> getAccounts(){
		return eBayAccountDao.getAccounts();
	}
    @Override
	public EbayAccountModel getAccountModelByName(String account) {
		return eBayAccountDao.getAccountModelByName(account);
	}
	@Override
	public EbayAccountModel getAccountByAccount(String account) {
		return eBayAccountDao.getAccountByAccount(account);
	}
}
