package com.it.ocs.cal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.cal.common.IBaseDao;
import com.it.ocs.cal.model.TaximeterModel;

@Repository
public interface ITaximeterDao extends IBaseDao<TaximeterModel> {

	List<TaximeterModel> queryBySku(@Param(value = "start") String start, @Param(value = "end") String end,
			@Param(value = "sku") String sku, @Param(value = "country") String string);

}
