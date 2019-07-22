package com.it.ocs.publication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.paymentManagement.model.PayPalRefundModel;
import com.it.ocs.paymentManagement.service.inner.IPayPalService;
import com.it.ocs.publication.model.GridResult;
import com.it.ocs.publication.model.PublicationModel;
import com.it.ocs.publication.model.PublicationSaveAsModel;
import com.it.ocs.publication.model.ShipLoactionModel;
import com.it.ocs.publication.model.ShipOptionModel;
import com.it.ocs.publication.service.external.IPublicationService;
import com.it.ocs.publication.vo.ComboBoxVO;
import com.it.ocs.publication.vo.ItemLabelVO;
import com.it.ocs.publication.vo.PublicationPageVO;
import com.it.ocs.publication.vo.PublicationVO;
import com.it.ocs.synchronou.model.EBayCountryDetailsModel;
import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;
import com.it.ocs.synchronou.model.EBaySiteDetailsModel;
import com.it.ocs.synchronou.model.EbayAccountModel;
import com.it.ocs.synchronou.service.IEBayCountryDetailsService;
import com.it.ocs.synchronou.service.IEBaySiteDetailsService;
import com.it.ocs.synchronou.service.IEbayAccountService;

@Controller
@RequestMapping(value="/publication")
public class PublicationController {
//	@Autowired
	private IPublicationService pubService;
	@Autowired
	private IEBaySiteDetailsService siteDetailsService;
	@Autowired
	private IEBayCountryDetailsService countryDetailsService;
	@Autowired
	private IEbayAccountService eBayAccountService;
	@RequestMapping(value="/batchUpdates")
	@ResponseBody
	public OperationResult batchUpdates(@RequestBody PublicationVO[] publicationVOs){
		
		return pubService.batchUpdates(publicationVOs);
	}
	@RequestMapping(value="/updateLineZero")
	@ResponseBody
	public OperationResult updateLineZero(@RequestBody PublicationVO[] publicationVOs){
		return pubService.updateLineZero(publicationVOs);
	}
	@RequestMapping(value="/synchronouPublication")
	@ResponseBody
	public OperationResult synchronouPublication(){
		return pubService.synchronouPublication();
	}
	@RequestMapping(value="/copyLinePus")
	@ResponseBody
	public OperationResult copyLinePus(@RequestBody PublicationVO[] publicationVOs){
		
		return pubService.copyLinePus(publicationVOs);
	}
	@RequestMapping(value="/synSelectItems")
	@ResponseBody
	public OperationResult synSelectItems(@RequestBody PublicationVO[] publicationVOs){
		return pubService.synSelectItems(publicationVOs);
	}

	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<PublicationPageVO> list(RequestParam param) {
		ResponseResult<PublicationPageVO> publicationPageResult = pubService.queryPublication(param);
        return publicationPageResult;
    }
	@RequestMapping(value = "/index/{conditions}",method = RequestMethod.GET)
	public String index() {
		return "admin/publication/publication";
		
	}
	@RequestMapping(value="/lineIndex/{conditions}",method=RequestMethod.GET)
	public String lineIndex(){
		return "admin/publication/publicationLine";
	}
	@RequestMapping(value = "/lineList", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<PublicationPageVO> lineList(RequestParam param) {
		ResponseResult<PublicationPageVO> publicationPageResult = pubService.queryLinePublication(param);
        return publicationPageResult;
    }
	@RequestMapping(value = "/toAdd",method = RequestMethod.GET)
	public ModelAndView toAdd(){
		ModelAndView mav = new ModelAndView("admin/publication/savePublication");
		List<EBaySiteDetailsModel> eBaySiteDetailsModels = siteDetailsService.internalSelectSiteDetails();
		List<EBayCountryDetailsModel> eBayCountryDetailsModels = countryDetailsService.internalSelectCountryDetailsList();
		mav.addObject("eBaySiteDetailsModels",eBaySiteDetailsModels);
		mav.addObject("eBayCountryDetailsModels",eBayCountryDetailsModels);
		return mav;
	}
	@RequestMapping(value = "/remove")
	@ResponseBody
	public OperationResult removeSite(String id) {
		return pubService.removePubById(id);
	}
	@RequestMapping(value="/cancelTimingPlan")
	@ResponseBody
	public OperationResult cancelTimingPlan(String id){
		return pubService.cancelTimingPlan(id);
	}
	@RequestMapping(value="lineRemove")
	@ResponseBody
	public OperationResult lineRemove(String id){
		return pubService.lineRemoveByIds(id);
	}
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult add(@RequestBody Map<String, Object> map) {
		return pubService.savePubInfo(map);
	}
	@RequestMapping(value = "/toEdit",method = RequestMethod.GET)
	public ModelAndView toEdit(Long id,String conditions) {
		
		ModelAndView mav = new ModelAndView("admin/publication/savePublication");
		//PublicationSaveVO publicationVO = pubService.getObjById(id);
		//mav.addObject("conditions", conditions);
		
		return mav;
	}
	@RequestMapping(value = "/getById",method = RequestMethod.GET)
	@ResponseBody
	public PublicationVO getById(Long id,String conditions) {
		return pubService.getPublicationById(id, conditions);
	}

	@RequestMapping(value = "/parseItemDataStructure/{setName}",method = RequestMethod.GET)
	@ResponseBody
	public void parseItemDataStructure(@PathVariable String setName){
		pubService.parseItemDataStructure(setName);
	}
	@RequestMapping(value = "/getSiteList",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getSiteList(){
		return pubService.getSiteList();
	}
	
	@RequestMapping(value = "/getAccountList",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getAccountList(){
		return pubService.getAccountList();
	}
	
	@RequestMapping(value = "/getRegionList",method = RequestMethod.POST)
	@ResponseBody
	public List<EBayCountryDetailsModel> getRegionList(){
		return countryDetailsService.internalSelectCountryDetailsList();
	}
	@RequestMapping(value="/cancelCorrelation")
	@ResponseBody
	public OperationResult cancelCorrelation(PublicationVO publicationVO){
		return pubService.updateCorrelation(publicationVO);
	}
	@RequestMapping(value = "/getPromoteList/{type}",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getPromoteList(@PathVariable String type){
		return pubService.getPromoteList(type);
	}
	
	@RequestMapping(value = "/getSellerDescription/{siteId}",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getSellerDescription(@PathVariable String siteId){
		return pubService.getSellerDescription(siteId);
	}
	@RequestMapping(value = "/getReturnPolicyData/{siteId}",method = RequestMethod.POST)
	@ResponseBody
	public EBayReturnPolicyDetailModel getReturnPolicyData(@PathVariable String siteId){
		return pubService.getReturnPolicyData(siteId);
	}
	@RequestMapping(value = "/getPublictonTransById/{id}",method = RequestMethod.POST)
	@ResponseBody
	public List<ShipOptionModel> getPublictonTransById(@PathVariable String id){
		return pubService.getPublictonTransById(id);
	}
	
	@RequestMapping(value = "/getTransTypeData/{siteId}",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getTransTypeData(@PathVariable String siteId){
		return pubService.getTransTypeData(siteId);
	}
	
	@RequestMapping(value = "/getPaymentSupportData/{siteId}",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getPaymentSupportData(@PathVariable String siteId){
		return pubService.getPaymentSupportData(siteId);
	}
	
	@RequestMapping(value = "/saveAslist", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<PublicationSaveAsModel> saveAslist(RequestParam param) {
		return pubService.saveAslist(param);
    }
	
	@RequestMapping(value = "/saveAs",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveAs(@RequestBody Map<String, Object> map) {
		return pubService.saveAs(map);
	}
	
	@RequestMapping(value = "/modelManagerlist", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult<PublicationSaveAsModel> modelManagerlist(RequestParam param) {
		return pubService.modelManagerlist(param);
    }

	@RequestMapping(value = "/saveAsDelete/{ids}", method = RequestMethod.POST)
	@ResponseBody
    public OperationResult saveAsDelete(@PathVariable String ids) {
		return pubService.saveAsDelete(ids);
    }
	
	@RequestMapping(value = "/templateView",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult templateView(@RequestBody Map<String, Object> map) {
		return pubService.templateView(map);
	}
	@RequestMapping(value = "/templateViewForOld/{templateId}",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult templateViewForOld(@PathVariable String templateId) {
		return pubService.templateViewForOld(templateId);
	}
	@RequestMapping(value = "/checkSKUAndTitle",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult checkSKUAndTitle(@RequestBody Map<String, Object> map) {
		return pubService.checkSKUAndTitle(map);
	}
	@RequestMapping(value="/savePlanPublication")
	@ResponseBody
	public OperationResult savePlanPublication(@RequestBody Map<String, Object> map){
		return pubService.savePlanPublication(map);
	}
	@RequestMapping(value = "/countPub/site", method = RequestMethod.POST)
	@ResponseBody
    public OperationResult countPub(@RequestBody Map<String, Object> map) {
		return pubService.countPub(map);
    }
	@RequestMapping(value="/countLinePub/site",method= RequestMethod.POST)
	@ResponseBody
	public OperationResult countLinePub(@RequestBody Map<String, Object> map){
	 return pubService.countLinePub(map);	
	}
	@RequestMapping(value="/sysPuHitCount")
	@ResponseBody 
	public OperationResult sysPuHitCount(@RequestBody Map<String, Object> map){
		return pubService.sysPuHitCount(map);
	}
	@RequestMapping(value="/getAccounts")
	@ResponseBody
	public List<EbayAccountModel> getAccounts(){
		return eBayAccountService.getAccounts();
	}
	@RequestMapping(value="/copyItemPublication")
	@ResponseBody
	public OperationResult copyItemPublication(PublicationVO publicationModel){
		
		return pubService.copyItemPublication(publicationModel.getId(),publicationModel);
	}
	@RequestMapping(value="/copyLineItemPublication")
	@ResponseBody
	public OperationResult copyLineItemPublication(PublicationModel publicationModel){
		
		return pubService.copyLineItemPublication(publicationModel);
	}
	@RequestMapping(value = "/aLotModifyList", method = RequestMethod.POST)
	@ResponseBody
    public GridResult<Map<String,Object>> aLotModifyList(RequestParam param) {
		return pubService.aLotModifyList(param);
    }
	
	
	@RequestMapping(value = "/aLotModifySave",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult aLotModifySave(@RequestBody Map<String, Object> map) {
		return pubService.aLotModifySave(map);
	}
	
	@RequestMapping(value="/aLotModifySaveAndUpdate",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult aLotModifySaveAndUpdate(@RequestBody Map<String, Object> map){
		
		return pubService.aLotModifySaveAndUpdate(map);
	}
	@RequestMapping(value = "/getTemplateBanner/{siteId}",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getTemplateBanner(@PathVariable("siteId")String siteId){
		return pubService.getTemplateBanner(siteId);
	}
	
	@RequestMapping(value = "/aLotModifyAllSave",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult aLotModifyAllSave(@RequestBody Map<String, Object> map) {
		return pubService.aLotModifyAllSave(map);
	}
	
	@RequestMapping(value="/aLotModifySaveToCorrelation",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult aLotModifySaveToCorrelation(@RequestBody Map<String, Object> map){
		
		return  pubService.aLotModifySaveToCorrelation(map);
		
	}
	
	@RequestMapping(value="/getPImgByItemId")
	@ResponseBody
	public OperationResult getPImgByItemId(String itemId){
		return pubService.getPImgByItemId(itemId);
	}
	@RequestMapping(value="/selectPubById")
	@ResponseBody
	public PublicationModel selectPubById(Long id){
		return pubService.selectPubById(id);
	}
	@RequestMapping(value = "/toALotModify",method = RequestMethod.GET)
	public String toALotModify(){
		return "admin/publication/aLotModifyPublication";
	}
	
	@RequestMapping(value = "/aLotModifySaveNew",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult aLotModifySaveNew(@RequestBody Map<String, Object> map) {
		return pubService.aLotModifySaveNew(map);
	}
	
	@RequestMapping(value="/getItemLabelByState/{state}")
	@ResponseBody
	public List<ItemLabelVO> getItemLabelByState(@PathVariable String state){
		return pubService.getItemLabelByState(state);
	}
	@RequestMapping(value="/getItemLabelPage")
	@ResponseBody
	public ResponseResult<ItemLabelVO> getItemLabelPage(RequestParam param){
		return pubService.getItemLabelPage(param);
	}
	
	@RequestMapping(value="/labelSave")
	@ResponseBody
	public OperationResult lebelSave(@RequestBody ItemLabelVO itemLabelVO){
		
		return pubService.labelSave(itemLabelVO);
	}
	@RequestMapping(value="/labelRemove")
	@ResponseBody
	public OperationResult labelRemove(Long id){
		
		return pubService.labelRemove(id);
	}
	
	@RequestMapping(value="/relevancyItemLable")
	@ResponseBody
	public OperationResult relevancyItemLable(String ids,Long id){
		return pubService.relevancyItemLable(ids, id);
	}
	
	@RequestMapping(value = "/aLotModifySaveToPublication",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult aLotModifySaveToPublication(@RequestBody Map<String, Object> map) {
		return pubService.aLotModifySaveToPublication(map);
	}
	@Autowired
	private IPayPalService payPalService;
	@RequestMapping(value="/updatePublicationDate")
	public void updatePublicationDate(){
		PayPalRefundModel payPalRefundModel = new PayPalRefundModel();
		payPalRefundModel.setPayPalAccount("941201063@qq.com");
		payPalRefundModel.setTransaction("30H96861G6122453M");
		payPalRefundModel.setRefundAmount(1000.0);
		payPalRefundModel.setCurrency("USD");
		payPalService.payPalRefund(payPalRefundModel);
		//pubService.updatePublicationDate();
	}
	
	@RequestMapping(value="/modelManagerShow")
	public String show(){
		return "admin/moduleManagement/modelManager";
	}

	@RequestMapping(value = "/getModelTempData/{siteId}/{type}",method = RequestMethod.POST)
	@ResponseBody
	public List<ComboBoxVO> getModelTempData(@PathVariable("siteId") String siteId,@PathVariable("type")int type){
		return pubService.getModelTempData(siteId,type);
	}
	
	@RequestMapping(value = "/getModeSet/{modeId}",method = RequestMethod.POST)
	@ResponseBody
	public OperationResult getModeSet(@PathVariable("modeId") int id) {
		return pubService.getModeSet(id);
	}
	
	/**
	 * 手动同步库存（默认2天内库存有变化的item）
	 * @return
	 */
	@RequestMapping(value = "/syncStock",method = RequestMethod.GET)
	@ResponseBody
	public OperationResult syncStock() {
		return pubService.syncStock();
	}
	
	@RequestMapping(value = "/getTransShipWide/{siteId}",method = RequestMethod.GET)
	@ResponseBody
	public List<ShipLoactionModel> getTransShipWide(@PathVariable("siteId") int siteId){
		return pubService.getTransShipWide(siteId);
	}
}
