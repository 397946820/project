package com.it.ocs.publication.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.model.PublicationRelationModel;
import com.it.ocs.publication.model.RelationDataModel;
@Repository
public interface IPublicationRelationDAO extends IBaseDAO<PublicationModel> {
	public List<RelationDataModel> queryRelationDatas(@Param(value="relationModels") List<PublicationRelationModel> relationModels);

	public List<PublicationModel> queryByPageList(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow,@Param("sort") String sort,@Param("order")String order);
   
	public List<PublicationModel> queryLineByPageList(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow,@Param("sort") String sort,@Param("order")String order);
	
	public List<PublicationModel> selectPubByCondition(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow,@Param("sort") String sort,@Param("order")String order);
	
	public int countList(@Param("pubModel")Map<String, Object> map);
	
	public int countLineList(@Param("pubModel") Map<String, Object> map);
	
	public int conditionCountList(@Param("pubModel")Map<String, Object> map);
	
	public List<PublicationModel> selectAllPublicationList(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow);
	public int getAllPublicationList(@Param("pubModel")Map<String, Object> map);
	public List<PublicationModel> selectLinePublicationList(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow);
	public int getLinePublicationList(@Param("pubModel")Map<String, Object> map);
	public List<PublicationModel> selectTimingPublicationList(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow);
	public int getTimingPublicationList(@Param("pubModel")Map<String, Object> map);
	public List<PublicationModel> selectDownlinePublicationList(@Param("pubModel")Map<String,Object> params, @Param("starRow") int startRow,  @Param("endRow")int endRow);
	public int getDownlinePublicationList(@Param("pubModel")Map<String, Object> map);
    public void delete(@Param("id")String id);
    public void cancelTimingPlan(@Param("id")String id);
    public void lineDelete(@Param("id")String id);
    
    public List<PublicationModel> queryLineList(@Param("pubModel")Map<String, Object> map);
}
