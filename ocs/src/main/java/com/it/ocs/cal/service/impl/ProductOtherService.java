package com.it.ocs.cal.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.cal.common.IBaseService;
import com.it.ocs.cal.dao.IProductEntityDao;
import com.it.ocs.cal.dao.IProductOtherDao;
import com.it.ocs.cal.model.ProductEntityModel;
import com.it.ocs.cal.model.ProductOtherModel;
import com.it.ocs.cal.service.IProductOtherService;
import com.it.ocs.cal.utils.ExportExcelUtil;
import com.it.ocs.cal.utils.ImportExcelUtil;
import com.it.ocs.cal.utils.Tools;
import com.it.ocs.cal.utils.Utils;
import com.it.ocs.cal.vo.ProductOtherVo;
import com.it.ocs.common.BeanConvertUtil;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.UserUtils;

@Service
@Transactional
public class ProductOtherService extends IBaseService implements IProductOtherService {

	@Autowired
	protected IProductOtherDao productOtherDao;
	
	@Override
	public ResponseResult<ProductOtherVo> findAll(RequestParam param, Boolean flag) {
		ResponseResult<ProductOtherVo> result = new ResponseResult<>();

		param = Tools.getRequestParam(param);

		ProductOtherVo other = BeanConvertUtil.mapToObject(param.getParam(), ProductOtherVo.class);
		
		if(!flag) {
			if(other == null) {
				other = new ProductOtherVo();
			}
			other.setUserId(UserUtils.getUserId());
		}
		
		
		List<ProductOtherModel> list = productOtherDao.queryByPage(other, param.getStartRow(), param.getEndRow(),
				param.getSort(), param.getOrder());

		list = Tools.changeList(list);

		int total = productOtherDao.count(other);

		result.setRows(CollectionUtil.beansConvert(list, ProductOtherVo.class));

		result.setTotal(total);

		return result;
	}

	@Override
	public OperationResult edit(ProductOtherVo productOther) {
		OperationResult result = new OperationResult();
		try {
			productOther.setUpdatedAt(new Date());
			productOtherDao.update(productOther);
		} catch (Exception e) {
			result.setErrorCode(1);
			result.setDescription("操作失败");
			throw new RuntimeException();
		}
		return result;
	}
	
	@Override
	public ResponseResult<ProductOtherVo> findByParentId(RequestParam param) {
		ResponseResult<ProductOtherVo> result = new ResponseResult<>();

		ProductOtherVo other = BeanConvertUtil.mapToObject(param.getParam(), ProductOtherVo.class);
		
		Long parentId = other.getParentId();
		
		List<ProductOtherModel> list = productOtherDao.findByParentId(parentId);
		
		result.setRows(CollectionUtil.beansConvert(list, ProductOtherVo.class));
		
		result.setTotal(0);
		
		return result;
	}

	@Override
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, String template, Boolean flag, List<String> strings) {
		// 输出的文件名称
		String fileName = "";
		Long userId = UserUtils.getUserIdByFlag(flag);
		List<ProductOtherModel> list = productOtherDao.findByUserId(userId);
		// 要导出的
		List<ProductOtherModel> result = new ArrayList<>();
		if (!CollectionUtil.isNullOrEmpty(list)) {
			if (StringUtils.isNotBlank(template)) {
				// 导出模板
				fileName = "产品其他模板" + Utils.getFileName() + ".xlsx";
				result.add(list.get(0));
			} else {
				// 导出全部
				fileName = "产品其他数据" + Utils.getFileName() + ".xlsx";
				result.addAll(list);
			}
			try {
				ExportExcelUtil.writeExcel(response, request, fileName, result, ProductOtherModel.class, template,strings);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				throw new RuntimeException();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public OperationResult uploadExcel(MultipartFile file) {
		OperationResult result = new OperationResult();
		String flag = "";
		int f = 0;
		try {
			InputStream inputStream = file.getInputStream();
			List<ProductOtherModel> list = ImportExcelUtil.importExcel(ProductOtherModel.class, inputStream);
			for (ProductOtherModel model : list) {
				String sku = model.getSku();
				//根据sku查找父id
				Long parentId = productEntityDao.findEntityIdBySku(sku);
				if(parentId != null) {
					//判断该sku在表中是否已经有8条数据
					int count = productOtherDao.findCountByParent(parentId);
					if(count == 8) {
						flag = sku;
						f = 1;
						throw new RuntimeException();
					} else {
						model.setParentId(parentId);
						model.setCreatedAt(new Date());
						model.setUpdatedAt(new Date());
						productOtherDao.add(model);
					}
				} else {
					flag = sku;
					f = 2;
					throw new Exception();
				}
			}
		} catch (Exception e) {
			result.setErrorCode(1);
			if(f == 0) {
				result.setDescription("操作失败");
			} else if(f== 1) {
				result.setDescription("导入失败," + flag + "  此sku在表中已经存在8条数据,无法再导入");
			} else if(f== 2) {
				result.setDescription("导入失败," + flag + "  此sku在产品表中不存在");
			}
			throw new RuntimeException();
		}
		return result;
	}
}
