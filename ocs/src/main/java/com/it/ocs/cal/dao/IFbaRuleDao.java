package com.it.ocs.cal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.FbaRuleModel;

@Repository
public interface IFbaRuleDao extends IBaseDao<FbaRuleModel> {

	List<FbaRuleModel> findRulesByCountry(String country);

}
