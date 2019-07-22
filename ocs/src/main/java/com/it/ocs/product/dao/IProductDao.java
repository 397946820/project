package com.it.ocs.product.dao;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.product.model.ProductModel;

@Repository
public interface IProductDao extends IBaseDAO<ProductModel> {

}
