package com.it.ocs.sys.service.support;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.it.ocs.common.support.IAction;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.common.util.MapUtil;
import com.it.ocs.sys.model.PermissionModel;
import com.it.ocs.sys.model.RolePermissionModel;
import com.it.ocs.sys.vo.PermissionVO;

public class PermissionSupport {
	private static void setParentName(List<PermissionVO> allPermissionVOs) {
		CollectionUtil.each(allPermissionVOs, new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO permissionVO) {
				if (null != permissionVO.getParentId() && permissionVO.getParentId() != 0) {
					PermissionVO parentVO = CollectionUtil.search(allPermissionVOs,
							new IFunction<PermissionVO, Boolean>() {
								@Override
								public Boolean excute(PermissionVO obj) {
									return permissionVO.getParentId() == obj.getId()
											|| permissionVO.getParentId().equals(obj.getId());
								}
							});
					if (null != parentVO) {
						permissionVO.setParentName(parentVO.getName());
					}
				}
			}
		});
	}

	public static List<PermissionVO> dataRestructure(List<PermissionVO> allPermissionVOs) {
		setParentName(allPermissionVOs);
		List<PermissionVO> parents = CollectionUtil.searchList(allPermissionVOs,
				new IFunction<PermissionVO, Boolean>() {
					@Override
					public Boolean excute(PermissionVO obj) {
						return null == obj.getParentId() || obj.getParentId() == 0;
					}
				});
		CollectionUtil.each(parents, new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO obj) {
				setChildren(obj, allPermissionVOs);
			}
		});
		return parents;
	}

	public static void setChildren(PermissionVO parent, List<PermissionVO> allPermissionVOs) {
		List<PermissionVO> childrens = CollectionUtil.searchList(allPermissionVOs,
				new IFunction<PermissionVO, Boolean>() {
					@Override
					public Boolean excute(PermissionVO obj) {
						return null != obj.getParentId() && obj.getParentId().equals(parent.getId());
					}
				});
		CollectionUtil.each(childrens, new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO obj) {
				setChildren(obj, allPermissionVOs);
			}
		});
		if (!CollectionUtil.isNullOrEmpty(childrens)) {
			parent.setChildren(childrens);
		}
	}

	public static List<PermissionVO> findChildren(List<PermissionVO> allPermissions,
			Map<Long, PermissionVO> parentMap) {
		List<PermissionVO> result = Lists.newArrayList();
		Map<Long, PermissionVO> childrenMap = Maps.newConcurrentMap();
		CollectionUtil.each(allPermissions, new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO permission) {
				if (null != permission.getParentId() && parentMap.containsKey(permission.getParentId())) {
					childrenMap.put(permission.getId(), permission);
					result.add(permission);
				}
			}
		});
		if (!childrenMap.isEmpty()) {
			List<PermissionVO> childrenPermissions = findChildren(allPermissions, childrenMap);
			if (!CollectionUtil.isNullOrEmpty(childrenPermissions)) {
				result.addAll(childrenPermissions);
			}
		}
		return result;
	}

	public static void findSubIds(List<Long> ids, Long parentId, List<PermissionModel> models) {
		ids.add(parentId);
		CollectionUtil.each(CollectionUtil.searchList(models, new IFunction<PermissionModel, Boolean>() {
			@Override
			public Boolean excute(PermissionModel obj) {
				return null != obj.getParentId() && obj.getParentId().equals(parentId);
			}
		}), new IAction<PermissionModel>() {
			@Override
			public void excute(PermissionModel obj) {
				findSubIds(ids, obj.getId(), models);
			}
		});
	}

	public static void sort(List<PermissionVO> result) {
		if (!CollectionUtil.isNullOrEmpty(result)) {
			CollectionUtil.each(result, new IAction<PermissionVO>() {
				@Override
				public void excute(PermissionVO permission) {
					if (!CollectionUtil.isNullOrEmpty(permission.getChildren())) {
						permission.getChildren().sort(new Comparator<PermissionVO>() {
							@Override
							public int compare(PermissionVO o1, PermissionVO o2) {
								if (null != o1.getOrderNum() && null != o2.getOrderNum()) {
									return o1.getOrderNum().compareTo(o2.getOrderNum());
								} else {
									return 0;
								}

							}
						});
					}
				}
			});
			result.sort(new Comparator<PermissionVO>() {
				@Override
				public int compare(PermissionVO o1, PermissionVO o2) {
					if (null != o1.getOrderNum() && null != o2.getOrderNum()) {
						return o1.getOrderNum().compareTo(o2.getOrderNum());
					} else {
						return 0;
					}
				}
			});
		}
	}

	public static void setChecked(List<PermissionVO> list, List<RolePermissionModel> rolePermissionModels) {
		List<Long> checkedPermissionIds = Lists.newArrayList();
		CollectionUtil.each(rolePermissionModels, new IAction<RolePermissionModel>() {
			@Override
			public void excute(RolePermissionModel rolePermissionModel) {
				checkedPermissionIds.add(rolePermissionModel.getPermissionId());
			}
		});
		CollectionUtil.each(list, new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO permissionVO) {
				if (checkedPermissionIds.contains(permissionVO.getId()) && permissionVO.getParentId() != null) {
					permissionVO.setChecked(true);
				}
			}
		});
	}

	public static void setState(List<PermissionVO> list) {
		CollectionUtil.each(list, new IAction<PermissionVO>() {
			@Override
			public void excute(PermissionVO obj) {
				if (!CollectionUtil.isNullOrEmpty(obj.getChildren())) {
					obj.setState("closed");
					setState(obj.getChildren());
				}
			}
		});
	}

	public static List<PermissionModel> filterPermission2(List<RolePermissionModel> rolePermissionModels,
			Map<Long, PermissionModel> permissionMap) {
		List<PermissionModel> result = Lists.newArrayList();
		CollectionUtil.each(rolePermissionModels, new IAction<RolePermissionModel>() {
			@Override
			public void excute(RolePermissionModel rolePermissionModel) {
				findCascadeData2(rolePermissionModel.getPermissionId(), permissionMap, result);
			}
		});
		return result;
	}

	private static void findCascadeData2(Long permissionId, Map<Long, PermissionModel> permissionMap,
			List<PermissionModel> result) {
		if (permissionMap.containsKey(permissionId)) {
			PermissionModel permissionVO = permissionMap.get(permissionId);
			if (null != permissionVO.getParentId()) {
				findCascadeData2(permissionVO.getParentId(), permissionMap, result);
			}
			if (!result.contains(permissionVO)) {
				result.add(permissionVO);
			}
		}
	}

	public static List<PermissionVO> filterPermission(List<RolePermissionModel> rolePermissionModels,
			Map<Long, PermissionVO> permissionMap) {
		List<PermissionVO> result = Lists.newArrayList();
		CollectionUtil.each(rolePermissionModels, new IAction<RolePermissionModel>() {
			@Override
			public void excute(RolePermissionModel rolePermissionModel) {
				findCascadeData(rolePermissionModel.getPermissionId(), permissionMap, result);
			}
		});
		return result;
	}

	private static void findCascadeData(Long permissionId, Map<Long, PermissionVO> permissionMap,
			List<PermissionVO> result) {
		if (permissionMap.containsKey(permissionId)) {
			PermissionVO permissionVO = permissionMap.get(permissionId);
			if (null != permissionVO.getParentId()) {
				findCascadeData(permissionVO.getParentId(), permissionMap, result);
			}
			if (!result.contains(permissionVO) && (permissionVO.getPermissionType().equals("MODULE")
					|| permissionVO.getPermissionType().equals("MENU"))) {
				result.add(permissionVO);
			}
		}
	}
}
