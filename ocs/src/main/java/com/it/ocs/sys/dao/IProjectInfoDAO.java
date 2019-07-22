package com.it.ocs.sys.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.sys.model.ProjectInfoModel;
@Component
public interface IProjectInfoDAO extends IBaseDAO<ProjectInfoModel> {
	public ProjectInfoModel getByProjectCode(@Param(value="projectCode") String projectCode);
}
