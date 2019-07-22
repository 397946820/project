package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ISmallTypeItemDao;
import com.it.ocs.cal.model.SmallTypeItemModel;
import com.it.ocs.cal.service.ISmallTypeItemService;
import com.it.ocs.cal.vo.SmallTypeItemVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class SmallTypeItemService implements ISmallTypeItemService {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ISmallTypeItemDao smallTypeItemDao;

	@Override
	public ResponseResult<SmallTypeItemVo> findAll(RequestParam param) {

		ResponseResult<SmallTypeItemVo> result = new ResponseResult<>();

		SmallTypeItemModel smallTypeItem = BeanConvertUtil.mapToObject(param.getParam(), SmallTypeItemModel.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<SmallTypeItemModel> list = smallTypeItemDao.query(smallTypeItem,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<SmallTypeItemModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), SmallTypeItemVo.class));
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(SmallTypeItemVo smallTypeItem) {
		OperationResult result = new OperationResult();
		try {
			
			if (smallTypeItem.getEntityId() == null) {
				smallTypeItemDao.add(smallTypeItem);
			} else {
				smallTypeItem.setUpdatedAt(new Date());
				smallTypeItemDao.update(smallTypeItem);
			}
		} catch (Exception e) {
			logger.error("小包计价器运输方式添加或者修改失败!", e);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getTypeName() {
		return smallTypeItemDao.getTypeName();
	}

	@Override
	public List<Map<String, Object>> getShippingTypeByTypeName(Map<String, Object> map) {
		return smallTypeItemDao.getShippingTypeByTypeName(map.get("typeName").toString());
	}
}
