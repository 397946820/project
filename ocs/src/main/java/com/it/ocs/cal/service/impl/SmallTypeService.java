package com.it.ocs.cal.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ocs.cal.dao.ISmallTypeDao;
import com.it.ocs.cal.model.SmallTypeModel;
import com.it.ocs.cal.service.ISmallTypeService;
import com.it.ocs.cal.vo.SmallTypeVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.enums.Style;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.StringUtil;

@Service
@Transactional
public class SmallTypeService implements ISmallTypeService {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ISmallTypeDao smallTypeDao;

	@Override
	public ResponseResult<SmallTypeVo> findAll(RequestParam param) {

		ResponseResult<SmallTypeVo> result = new ResponseResult<>();

		SmallTypeModel smallType = BeanConvertUtil.mapToObject(param.getParam(), SmallTypeModel.class);

		PageHelper.startPage(param.getPage(), param.getRows());

		List<SmallTypeModel> list = smallTypeDao.query(smallType,
				StringUtil.convertByStyle(param.getSort(), Style.camelhumpAndUppercase), param.getOrder());

		PageInfo<SmallTypeModel> pageInfo = new PageInfo<>(list);
		result.setRows(CollectionUtil.beansConvert(pageInfo.getList(), SmallTypeVo.class));
		result.setTotal((int) pageInfo.getTotal());

		return result;
	}

	@Override
	public OperationResult saveEdit(SmallTypeVo smallType) {
		OperationResult result = new OperationResult();
		try {
			SmallTypeModel model = smallTypeDao.queryByParam(new SmallTypeModel(smallType.getTypeName()));
			if (model == null) {
				smallTypeDao.add(smallType);
			} else {
				smallType.setEntityId(model.getEntityId());
				smallType.setUpdatedAt(new Date());
				smallTypeDao.update(smallType);
			}
		} catch (Exception e) {
			logger.error("小包计价器类型添加或者修改失败!", e);
			throw new RuntimeException(e.getMessage());
		}
		return result;
	}

}
