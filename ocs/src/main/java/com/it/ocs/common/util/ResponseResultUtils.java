package com.it.ocs.common.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.log4j.Logger;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.dao.IResponseDao;
import com.it.ocs.common.enums.Style;

/**
 * 
 * @ClassName: ResponseResultUtils
 * @Description:
 * @author: yecaiqing
 */
public class ResponseResultUtils {

	/**
	 * 查询
	 * 
	 * @Title: getResponseResult
	 * @Description:
	 * @param responseDao
	 * @param param
	 * @param clazz
	 * @return ResponseResult<V>
	 * @author yecaiqing
	 */
	public static <T, V extends Serializable> ResponseResult<V> getResponseResult(IResponseDao<T> responseDao,
			RequestParam param, Class<V> clazz) {
		ResponseResult<V> result = new ResponseResult<>();

		PageHelper.startPage(param.getPage(), param.getRows());

		List<T> list = responseDao.query(param.getParam(),
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<T> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), clazz));
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	/**
	 * 添加/修改
	 * 
	 * @Title: saveEdit
	 * @Description:
	 * @param logger
	 * @param responseDao
	 * @param param
	 * @return OperationResult
	 * @author yecaiqing
	 */
	public static <T> OperationResult saveEdit(Logger logger, IResponseDao<T> responseDao, T param) {
		OperationResult result = new OperationResult();
		Map<String, Object> map = new BeanMap(param);
		try {
			if (map.get("entityId") == null) {
				responseDao.add(map);
			} else {
				map.put("updatedAt", new Date());
				responseDao.update(map);
			}
		} catch (Exception e) {
			logger.error("保存或者更新失败!", e);
			result.setErrorCode(1);
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @Title: deleteById
	 * @Description:
	 * @param logger
	 * @param responseDao
	 * @param entityId
	 * @return OperationResult
	 * @author yecaiqing
	 */
	public static <T> OperationResult deleteById(Logger logger, IResponseDao<T> responseDao, Long entityId) {
		OperationResult result = new OperationResult();
		try {
			responseDao.deleteById(entityId);
		} catch (Exception e) {
			logger.error("删除失败!", e);
			result.setErrorCode(1);
		}
		return result;
	}
}
