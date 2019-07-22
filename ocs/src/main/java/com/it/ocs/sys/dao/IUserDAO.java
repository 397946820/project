package com.it.ocs.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.sys.model.UserModel;

public interface IUserDAO extends IBaseDAO<UserModel> {
	public UserModel getByName(@Param(value = "name") String userName);

	public int updateProductEntityUser(@Param(value = "userId") Long userId, @Param(value = "skus") List<String> skus);

	public int updateCalPricePlanUser(@Param(value = "userId") Long userId, @Param(value = "skus") List<String> skus);

	public UserModel getByNick(@Param(value = "nick") String nick);
}
