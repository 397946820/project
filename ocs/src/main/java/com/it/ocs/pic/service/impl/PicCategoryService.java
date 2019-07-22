package com.it.ocs.pic.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.pic.model.PicCategoryModel;
import com.it.ocs.pic.service.IPicCategoryService;
import com.it.ocs.pic.vo.PicCategoryVO;
@Service
public class PicCategoryService extends BaseService implements IPicCategoryService {
	
	
	@Override
	public List<PicCategoryVO> query() {
		return dataRestructure(findAll());
	}
	private List<PicCategoryVO> findAll() {
		List<PicCategoryModel> allCategories = picCategoryDAO.query(null);
		for (PicCategoryModel picCategoryModel : allCategories) {
			picCategoryModel.setText(picCategoryModel.getName());
		}
		return CollectionUtil.beansConvert(allCategories, PicCategoryVO.class);
	}
	private void setParentName(List<PicCategoryVO> allCategoryVOs) {
		CollectionUtil.each(allCategoryVOs, new IAction<PicCategoryVO>() {
			@Override
			public void excute(PicCategoryVO picCategoryVO) {
				if (null != picCategoryVO.getPid() && picCategoryVO.getPid() != 0) {
					PicCategoryVO parentVO = CollectionUtil.search(allCategoryVOs, new IFunction<PicCategoryVO, Boolean>() {
						@Override
						public Boolean excute(PicCategoryVO obj) {
							return picCategoryVO.getPid() == obj.getId() || picCategoryVO.getPid().equals(obj.getId());
						}
					});
					if (null != parentVO) {
						picCategoryVO.setParentName(parentVO.getName());
					}
				}
			}
		});
	}
	private List<PicCategoryVO> dataRestructure(List<PicCategoryVO> allCategoryVOs) {
		setParentName(allCategoryVOs);
		List<PicCategoryVO> parents = CollectionUtil.searchList(allCategoryVOs, new IFunction<PicCategoryVO, Boolean>() {
			@Override
			public Boolean excute(PicCategoryVO obj) {
				return null == obj.getPid() || obj.getPid() == 0;
			}
		});
		CollectionUtil.each(parents, new IAction<PicCategoryVO>() {
			@Override
			public void excute(PicCategoryVO obj) {
				setChildren(obj, allCategoryVOs);
			}
		});
		return parents;
	}
	private void setChildren(PicCategoryVO parent,List<PicCategoryVO> allCategoryVOs) {
		List<PicCategoryVO> childrens = CollectionUtil.searchList(allCategoryVOs, new IFunction<PicCategoryVO, Boolean>() {
			@Override
			public Boolean excute(PicCategoryVO obj) {
				return null != obj.getPid() && obj.getPid().equals(parent.getId());
			}
		});
		CollectionUtil.each(childrens, new IAction<PicCategoryVO>() {
			@Override
			public void excute(PicCategoryVO obj) {
				setChildren(obj, allCategoryVOs);
			}
		});
		if (!CollectionUtil.isNullOrEmpty(childrens)) {
			parent.setChildren(childrens);
		}
	}

	@Override
	public OperationResult savePicCategory(PicCategoryVO picCategoryVO) {
		OperationResult result = new OperationResult();

		if (null != picCategoryVO.getId()) {
			updateInit(picCategoryVO);
			picCategoryDAO.update(picCategoryVO);
		} else {
			insertInit(picCategoryVO);
			picCategoryDAO.add(picCategoryVO);
		}
		result.setData("success");
		return result;
	}

	@Override
	public OperationResult removePicCategory(Long picCategoryId) {
		OperationResult result = new OperationResult();
		picCategoryDAO.deleteCategoryById(picCategoryId);
		result.setData("success");
		return result;
	}
	private void findSubIds(List<Long> ids,Long parentId,List<PicCategoryModel> models) {
		ids.add(parentId);
		CollectionUtil.each(CollectionUtil.searchList(models, new IFunction<PicCategoryModel, Boolean>() {
			@Override
			public Boolean excute(PicCategoryModel obj) {
				return null != obj.getPid() && obj.getPid().equals(parentId);
			}
		}), new IAction<PicCategoryModel>() {
			@Override
			public void excute(PicCategoryModel obj) {
				findSubIds(ids, obj.getId(), models);
			}
		});
	}
	@Override
	public List<PicCategoryVO> combotree() {
		List<PicCategoryVO> allCategoryVOs = findAll();
		CollectionUtil.each(allCategoryVOs, new IAction<PicCategoryVO>() {
			@Override
			public void excute(PicCategoryVO obj) {
				obj.setText(obj.getName());
			}
		});
		return dataRestructure(allCategoryVOs);
	}
	@Override
	public List<PicCategoryVO> conditionCombotree() {
		List<PicCategoryVO> allCategoryVO = combotree();
		if (!CollectionUtil.isNullOrEmpty(allCategoryVO)) {
			PicCategoryVO defaultCategory = new PicCategoryVO();
			defaultCategory.setId(-1L);
			defaultCategory.setText("所有类目");
			allCategoryVO.add(defaultCategory);
		}
		return allCategoryVO;
	}
	
}
