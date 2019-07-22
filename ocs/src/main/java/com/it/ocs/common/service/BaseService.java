package com.it.ocs.common.service;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.it.ocs.cal.dao.IProductRelationDAO;
import com.it.ocs.common.model.BaseModel;
import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;
import com.it.ocs.compare.dao.IEbayOrderCompareDAO;
import com.it.ocs.compare.dao.ILightOrderCompareDAO;
import com.it.ocs.pic.dao.IPicCategoryDAO;
import com.it.ocs.publication.dao.IAdvertFeatureDAO;
import com.it.ocs.publication.dao.IAuctionDAO;
import com.it.ocs.publication.dao.IBuyerRequireDAO;
import com.it.ocs.publication.dao.IOtherDAO;
import com.it.ocs.publication.dao.IPaymentDAO;
import com.it.ocs.publication.dao.IProductCommentDAO;
import com.it.ocs.publication.dao.IProductPlaceDAO;
import com.it.ocs.publication.dao.IPublicationInfoDAO;
import com.it.ocs.publication.dao.IPublicationRelationDAO;
import com.it.ocs.publication.dao.IReturnPolicyPUBDAO;
import com.it.ocs.publication.dao.ITransChooseDAO;
import com.it.ocs.sys.dao.IDepartmentDAO;
import com.it.ocs.sys.dao.IPermissionDAO;
import com.it.ocs.sys.dao.IProjectInfoDAO;
import com.it.ocs.sys.dao.IRoleDAO;
import com.it.ocs.sys.dao.IRolePermissionDAO;
import com.it.ocs.sys.dao.IRoleUserDAO;
import com.it.ocs.sys.dao.IUserDAO;
import com.it.ocs.sys.model.RoleModel;
import com.it.ocs.sys.model.UserModel;

public abstract class BaseService {
	private final String ADMINISTRATOR_CODE = "ADMINISTRATOR";
	@Autowired
	protected IPublicationInfoDAO pubInfoDAO;
	@Autowired
	protected IAuctionDAO auctionDAO;
	@Autowired
	protected IProductCommentDAO productCommentDAO;
	@Autowired
	protected IPaymentDAO paymentDAO;
	@Autowired
	protected IBuyerRequireDAO buyerRequireDAO;
	@Autowired
	protected IReturnPolicyPUBDAO returnPolicyDAO;
	@Autowired
	protected IProductPlaceDAO productPlaceDAO;
	@Autowired
	protected ITransChooseDAO transChooseDAO;
	@Autowired
	protected IAdvertFeatureDAO advertFeatureDAO;
	@Autowired
	protected IOtherDAO otherDAO;
	@Autowired
	protected IPublicationRelationDAO publicationRelationDAO;
	@Autowired
	protected IUserDAO userDAO;
	@Autowired
	protected IRoleUserDAO roleUserDAO;
	@Autowired
	protected IRoleDAO roleDAO;
	@Autowired
	protected IDepartmentDAO departmentDAO;
	@Autowired
	protected IProjectInfoDAO projectInfoDAO;
	@Autowired
	protected IPicCategoryDAO picCategoryDAO;
	@Autowired
	protected IPermissionDAO permissionDAO;
	@Autowired
	protected IRolePermissionDAO rolePermissionDAO;
	@Autowired
	protected IProductRelationDAO relationDAO;
	@Autowired
	protected ILightOrderCompareDAO lightDAO;
	@Autowired
	protected IEbayOrderCompareDAO ebayCompareDAO;
	
	public <T extends BaseModel> void insertInit(T obj) {
		UserModel userModel = getCurentUser();
		obj.setCreateBy(userModel.getId());
		obj.setCreationDate(new Date());
		obj.setLastUpdateBy(userModel.getId());
		obj.setLastUpdationDate(new Date());
	}
	public <T extends BaseModel> void updateInit(T obj) {
		UserModel userModel = getCurentUser();
		obj.setLastUpdateBy(userModel.getId());
		obj.setLastUpdationDate(new Date());
	}
	protected UserModel getCurentUser() {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser.getSession().getAttribute("user")) {
			return (UserModel)currentUser.getSession().getAttribute("user");
		}
		return null;
	}
	protected boolean isAdministrator() {
		UserModel userModel = getCurentUser();
		List<RoleModel> roleModels = roleDAO.queryByUserId(userModel.getId());
		return null != CollectionUtil.search(roleModels, new IFunction<RoleModel, Boolean>() {
			@Override
			public Boolean excute(RoleModel model) {
				return model.getCode().equals(ADMINISTRATOR_CODE);
			}
		});
	}
	
}
