package com.it.ocs.publication.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.publication.model.ItemDataStructureModel;
import com.it.ocs.publication.model.OtherModel;
@Repository
public interface IOtherDAO extends IBaseDAO<OtherModel> {

	public ItemDataStructureModel getItemDataStructureXML(String setName);

	public Long getDataStructureId();

	public void saveItemDataStructure(List<ItemDataStructureModel> list);

	public int getChildrenCount(String name);

}
