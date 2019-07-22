package com.it.ocs.publication.service.external;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.publication.model.EBayTimingPlanModel;
import com.it.ocs.publication.model.GridResult;
import com.it.ocs.publication.model.LabelMiddleModel;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.model.PublicationSaveAsModel;
import com.it.ocs.publication.model.ShipLoactionModel;
import com.it.ocs.publication.model.ShipOptionModel;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.publication.vo.ItemLabelVO;
import com.it.ocs.publication.vo.PublicationPageVO;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;
import com.it.ocs.synchronou.model.EBaySiteDetailsModel;

public interface IPublicationService {

	/**
	 * 
	 * @param publicationVOs
	 * @return
	 * 描述：批量快速修改 
	 */
	public OperationResult batchUpdates(PublicationVO[] publicationVOs);
	/**
	 * 
	 * @param publicationVOs
	 * @return
	 * 描述：修改数量为0
	 */
	public OperationResult updateLineZero(PublicationVO[] publicationVOs);
	public OperationResult savePubInfo(Map<String, Object> map);
	
	public ResponseResult<PublicationPageVO> queryPublication(RequestParam param);
	
	public ResponseResult<PublicationPageVO> queryLinePublication(RequestParam param);
	public PublicationVO getObjById(Long id);
	public PublicationVO getLineById(Long id);
	public PublicationVO getPublicationById(Long id ,String conditions);
	public OperationResult removePubById(String id);
	public OperationResult cancelTimingPlan(String id);
	public OperationResult lineRemoveByIds(String id);
	public void parseItemDataStructure(String setName);

	public OperationResult sysPuHitCount(Map<String, Object> map);
	
	public List<ComboBoxVO> getPromoteList(String type);

	public List<ComboBoxVO> getSellerDescription(String siteId);

	public EBayReturnPolicyDetailModel getReturnPolicyData(String siteId);

	public List<ShipOptionModel> getPublictonTransById(String id);

	public List<ComboBoxVO> getTransTypeData(String siteId);

	public List<ComboBoxVO> getPaymentSupportData(String siteId);

	public ResponseResult<PublicationSaveAsModel> saveAslist(RequestParam param);

	public OperationResult saveAs(Map<String, Object> map);
	
	public ResponseResult<PublicationSaveAsModel> modelManagerlist(RequestParam param);
	
	public OperationResult saveAsDelete(String ids);

	public List<ComboBoxVO> getSiteList();

	public OperationResult templateView(Map<String, Object> map);

	public OperationResult templateViewForOld(String templateId);

	public OperationResult checkSKUAndTitle(Map<String, Object> map);

	public OperationResult countPub(Map<String, Object>map);
	
	public OperationResult countLinePub(Map<String, Object>map);

	public OperationResult copyItemPublication(Long id,PublicationModel publicationModel);

	public OperationResult copyLineItemPublication(PublicationModel publicationModel);
	public OperationResult savePlanPublication(Map<String, Object> map);

	public List<ComboBoxVO> getAccountList();
	
	public OperationResult updateCorrelation(PublicationVO publicationVO);
	
	public void runSynTimingPlan(List<EBayTimingPlanModel> eBayTimingPlanModels);
	
	public PublicationModel selectPubById(Long id);

	public GridResult<Map<String, Object>> aLotModifyList(RequestParam param);
	
	public GridResult<Map<String, Object>> aLotLineModifyList(RequestParam param);
	
	public OperationResult aLotModifySave(Map<String, Object> map);

	public List<ComboBoxVO> getTemplateBanner(String siteId);

	public OperationResult aLotModifyAllSave(Map<String, Object> map);
	
	public OperationResult aLotModifySaveAndUpdate(Map<String, Object> map);
	
	public OperationResult aLotModifySaveToCorrelation(Map<String, Object> map);
	
	public OperationResult copyLinePus(PublicationVO[] publicationVOs);
	
	public OperationResult  getPImgByItemId(String itemId);
	
	/**
	 * 描述：同步产品信息
	 * @return
	 */
	public OperationResult synchronouPublication();
	
	public void runTiming();
	
	public OperationResult aLotModifySaveNew(Map<String, Object> map);
	
	public List<ItemLabelVO> getItemLabelByState(String labelState);
	
	public ResponseResult<ItemLabelVO> getItemLabelPage(RequestParam param);
	
	public OperationResult labelSave(ItemLabelVO itemLabelVO);
	
	public OperationResult labelRemove(Long id);
	
	public OperationResult relevancyItemLable(String ids,Long id);
	
	public OperationResult insertItemLabel(List<ItemLabelVO> itemLabelVOs);
	
	public OperationResult updateItemLabel(List<ItemLabelVO> itemLabelVOs);
	
	public OperationResult insertLableMiddles(List<LabelMiddleModel> labelMiddleModels);
	
	public OperationResult aLotModifySaveToPublication(Map<String, Object> map);
	
	public void updatePublicationDate();
	
	public OperationResult insertActiveItems(List<PublicationModel> publicationModels);
	
	public OperationResult updateActiveItems(List<PublicationModel> publicationModels);
	
	public OperationResult synSelectItems(PublicationVO[] publicationVOs);
	
	public List<ComboBoxVO> getModelTempData(String siteId, int type);
	
	public OperationResult getModeSet(int id);
	
	public OperationResult syncStock();
	
	public List<ShipLoactionModel> getTransShipWide(int siteId);
}
