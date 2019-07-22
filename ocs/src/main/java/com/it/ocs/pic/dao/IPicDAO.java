package com.it.ocs.pic.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.pic.model.PicModel;
@Repository
public interface IPicDAO extends IBaseDAO<PicModel> {
	public List<PicModel> queryByPage(@Param("condition")Map<String, Object> map, @Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow);
	
	public Integer count(@Param("condition") Map<String, Object> map);
	
	public PicModel queryById(@Param("id")Long id);
	public List<PicModel> queryByIds(@Param("ids")String ids);
	
	public void updateUrlById(PicModel picModel);
}
