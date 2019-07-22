package com.it.ocs.publication.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.ocs.common.dao.IBaseDAO;
import com.it.ocs.itemDescribe.model.EBayPromoteModel;
import com.it.ocs.publication.model.EBayTimingPlanModel;
import com.it.ocs.publication.model.ItemLabelModel;
import com.it.ocs.publication.model.LabelMiddleModel;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.model.PublicationSaveAsModel;
import com.it.ocs.publication.model.ShipLoactionModel;
import com.it.ocs.publication.model.ShipOptionModel;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.seller.model.EBayItemPromoteModel;
import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;
import com.it.ocs.sys.model.EbayOnlineModel;
import com.it.ocs.sys.model.ItemDataModel;
import com.it.ocs.upload.vo.FileVO;
@Repository
public interface IPublicationInfoDAO extends IBaseDAO<PublicationModel> {

	public void batchUpdates(List<PublicationModel> publicationModels);
	
	public void batchLineUpdates(List<PublicationModel> publicationModels);
	
	public void updateAvailableByItemId(PublicationModel publicationModel);
	
	public List<ItemDataModel> getItemDataModels(long id);

	public ItemDataModel getItemNodeInfo(long id);

	public void updateItemData(ItemDataModel itemDataModel);

	public List<EbayOnlineModel> getEBayItemList();

	public void updateEbayXML(EbayOnlineModel onlineMode);

	public void excute(@Param("sql")String sql);

	public String queryData(@Param("sql") String sql);

	public Long getLineId();
	
	public List<PublicationModel> selectAllLinePus();
	
	public void updatePuHitCountByItemID(PublicationModel publicationModel);
	
	public void addItemData(ItemDataModel itemDataModel);

	public List<ComboBoxVO> getPromoteList(String type);

	public String getPromoteItemIds(String templateId);

	public List<EBayPromoteModel> getProductInfo(List idList);

	public List<ComboBoxVO> getSellerDescription(String siteId);

	public EBayReturnPolicyDetailModel getReturnPolicyData(String siteId);

	public void addPublicationTransInfo(ShipOptionModel ship);

	public void updatePublicationTransInfo(ShipOptionModel ship);

	public List<ShipOptionModel> getPublictonTransById(String id);

	public List<ComboBoxVO> getTransTypeData(String siteId);

	public List<ComboBoxVO> getPaymentSupportData(String siteId);

	public List<PublicationSaveAsModel> queryByPageSaveAs( @Param("saveAsModel") Map<String, Object> map, @Param("startRow") int startRow, @Param("endRow") int endRow);

	public int countSaveAs( @Param("saveAsModel")Map<String, Object> map);

	public int saveAs(Map<String, Object> map);

	public List<PublicationSaveAsModel> queryByPageModelManager( @Param("model")Map<String, Object> map,@Param("startRow") int startRow,@Param("endRow") int endRow);

	public int countModelManager(@Param("model")Map<String, Object> map);

	public int updateSaveAs(Map<String, Object> map);

	public int saveAsDelete(List list);

	public List<ComboBoxVO> getSiteList();
	
	public void updatePublicationById(PublicationModel publicationModel);

	public int checkSKUAndTitle(Map<String, Object> map);

	
	
	public int countLinePub(@Param("siteId")String siteId,@Param("conditions")String conditions,@Param("correlation_id")Long correlation_id);
	public int allPublicationTotal(@Param("siteId")String siteId);
	public int linePublicationTotal(@Param("siteId")String siteId);
	public int timingPublicationTotal(@Param("siteId")String siteId);
	public int downlinePublicationTotal(@Param("siteId")String siteId);
	public void updatePublictionInfoEndDateByItemId(PublicationModel publicationModel);
	public void addPublicationTransInfos(@Param("list")List<ShipOptionModel> shipOptionModels);
	public List<PublicationModel> selectPuiItemIdByCounter(@Param("sellerId")String sellerId);
	
	public List<PublicationModel> selectBestOfferItems(@Param(value="starRow") Integer starRow,@Param(value="endRow") Integer endRow);

	public Integer getBestOfferTotal();
	
	public void updatePublicationVariations(PublicationModel publicationModel);
	
	public void updateById(PublicationModel publicationModel);
	public void insertPlanPublications(List<EBayTimingPlanModel> planPublicationModes);
	
	public List<EBayTimingPlanModel> selectPlanPublicationModes();
	
	public List<PublicationModel> selectPublicateByPlans(List<EBayTimingPlanModel> planPublicationModes);

	public void updatePublicatePlan(PublicationModel publicationModel);
	
	public void updatePublicateCopyNumber(PublicationModel publicationModel);
	
	public PublicationModel selectPublicByItemId(@Param("itemId")String itemId);
	
	public List<PublicationModel> selectLineExist(PublicationModel publicationModel);

	public PublicationModel selectPubById(@Param("id")Long id);

	public List<String> getItemList();

	public String getItemInfoXML(String item);

	public void updateItemInfo(Map<String, Object> map);

	public List<Map<String, Object>> getALotModifyList(@Param("param")Map<String, Object> map);

	public List<Map<String, Object>> getLineALotModifyList(@Param("param")Map<String, Object> map,@Param("start")int start,@Param("end")int end);
	
	public int countALotModifyList(@Param("param")Map<String, Object> map);
	
	public int countLALotModifyList(@Param("param")Map<String, Object> map);
	
	public void updatePulicationByALot(@Param("param")Map<String, Object> row);

	public List<Map<String, Object>> getTransInfoById(String tempId);

	public void updateTrans(Map<String, Object> dom);

	public List<ComboBoxVO> getTemplateBanner(String siteId);

	public void updateAllPulicationByALot(Map<String, Object> data);
	
	public PublicationModel getLineById(Long id);

	public void addLine(PublicationModel publicationModel);
	public void updateAllTrans(Map<String, Object> dom);
	public void lineUpdate(PublicationModel publicationModel);
	public void updateLineCorrelation(PublicationModel publicationModel);

	public EBayItemPromoteModel getItemPromoteInfoById(String templateId);
	
	public PublicationModel getPImgByItemId(@Param("itemId")String itemId);
	
	public EbayOnlineModel getEbayOnlineAll(Integer page);
	
	public Integer getEbayOnLineCount();
	
	public void updateLinePuByOnline(PublicationModel publicationModel);
	public void updateLinePuByOnline2(PublicationModel publicationModel);
	public void updateLinePuByOnline3(PublicationModel publicationModel);
	public void updateLinePuByOnline4(PublicationModel publicationModel);

	public void updatePulicationLineByALot(@Param("param")Map<String, Object> row);

	public int transHasExist(ShipOptionModel ship);

	public Map<String, Object> getPulicationLineOldData(@Param("param")Map<String, Object> updateMap);

	public void deletePublicationTranInfo(String tempId);

	public void rollBackTranData(List<Map<String, Object>> domesticTransOld);
	
	public List<ItemLabelModel> getItemLabelByState(String labelState);
	
	public List<ItemLabelModel> getItemLabelPage(@Param("param")Map<String, Object> map,@Param("start")int start,@Param("end")int end);
	
	public Integer getLabelTotal(@Param("param")Map<String, Object> map);
	
	public void  updateItemLabels(List<ItemLabelModel> itemLabelModels);
	
	public void insertItemLabels(List<ItemLabelModel> itemLabelModels);
	
	public void insertLableMiddles(List<LabelMiddleModel> labelMiddleModels);
	
	public void labelRemove(Long id);

	public String getLinkTemplateIdByLineId(String[] tempId);
	
	public void updatePublicationDate(PublicationModel publicationModel);
	
	public List<PublicationModel> selectOnlineDate();
	
	public void insertPublicationLine(PublicationModel publicationModel);
	
	public void insertActiveItems(List<PublicationModel> publicationModels);
	
	public void updateActiveItems(List<PublicationModel> publicationModels);
	
	public void updateItemStartByAccount(String account);
	
	public PublicationModel selectOnItemByID(String itemId);
	
	public PublicationModel selectRecentlyItemByID(String itemId);
	
	public List<PublicationModel> selectIsLineByAccount(String account);

	public List<ComboBoxVO> getModelTempData(@Param("param")Map<String, Object> map);

	public String getModeSet(int id);
	
	public void updatePublicationInfo(PublicationModel publicationModel);
	
	public void deleteTransInfoByTID(Long tid);
	
	public void updateInfo(PublicationModel publicationModel);

	public FileVO getImageInfoById(String imageId);

	public FileVO getImageInfoByServerUrl(String url);

	public void updateImageServerInfo(FileVO image);

	public String getLastEndTime(Map<String, Object> timeSetMap);

	public int getTimeSetId();

	public void addTimeSet(Map<String, Object> timeSetMap);

	public void updateTimeSet(Map<String, Object> timeSetMap);

	public int itemIsExist(String itemId);

	public void updateItemToLineData(@Param("param") Map<String, Object> item);

	public String getVariationByItemId(String itemId);

	public void updateItemByOnline(@Param("param") Map<String, Object> map);

	public int variationHasError(String itemId);

	public void clearTransByType(Map<String, Object> map);

	public void updateItemBySellerListApi(@Param("param")Map<String, Object> item);

	public List<ShipLoactionModel> getTransShipWide(int siteId);
	
	
}

