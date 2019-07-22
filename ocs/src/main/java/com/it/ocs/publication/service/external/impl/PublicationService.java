//package com.it.ocs.publication.service.external.impl;
//
//import java.lang.reflect.Field;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TimeZone;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.apache.log4j.Logger;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.ApiException;
//import com.ebay.sdk.SdkException;
//import com.ebay.sdk.call.ReviseInventoryStatusCall;
//import com.ebay.sdk.call.ReviseItemCall;
//import com.ebay.sdk.call.SetUserPreferencesCall;
//import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
//import com.ebay.soap.eBLBaseComponents.FeeType;
//import com.ebay.soap.eBLBaseComponents.InventoryFeesType;
//import com.ebay.soap.eBLBaseComponents.InventoryStatusType;
//import com.ebay.soap.eBLBaseComponents.ItemType;
//import com.ebay.soap.eBLBaseComponents.SellerFavoriteItemPreferencesType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.listener.ProjectApplicationContext;
//import com.it.ocs.publication.dao.IEBayTimingPlanDAO;
//import com.it.ocs.publication.model.EBayTimingPlanModel;
//import com.it.ocs.publication.model.GridResult;
//import com.it.ocs.publication.model.ItemDataStructureModel;
//import com.it.ocs.publication.model.ItemLabelModel;
//import com.it.ocs.publication.model.LabelMiddleModel;
//import com.it.ocs.publication.model.ParseItemSellXMLModel;
//import com.it.ocs.publication.model.ParseOnlineItemOfAllXMLModel;
//import com.it.ocs.publication.model.ParseSellerListItemXMLModel;
//import com.it.ocs.publication.model.PublicationAddItemModel;
//import com.it.ocs.publication.model.PublicationModel;
//import com.it.ocs.publication.model.PublicationRelationModel;
//import com.it.ocs.publication.model.PublicationSaveAsModel;
//import com.it.ocs.publication.model.RelationDataModel;
//import com.it.ocs.publication.model.ShipLoactionModel;
//import com.it.ocs.publication.model.ShipOptionModel;
//import com.it.ocs.publication.service.external.IPublicationService;
//import com.it.ocs.publication.service.inner.IPublicationViewService;
//import com.it.ocs.publication.service.inner.impl.EbayImageUploadService;
//import com.it.ocs.publication.vo.ComboBoxVO;
//import com.it.ocs.publication.vo.ItemLabelVO;
//import com.it.ocs.publication.vo.PublicationPageVO;
//import com.it.ocs.publication.vo.PublicationVO;
//import com.it.ocs.synchronou.dao.ITemplateDao;
//import com.it.ocs.synchronou.mapping.CurrencyCodeTypeEnum;
//import com.it.ocs.synchronou.mapping.EbayMappingToSDK;
//import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;
//import com.it.ocs.synchronou.model.EbayAccountModel;
//import com.it.ocs.synchronou.model.RequestModel;
//import com.it.ocs.synchronou.model.TemplateModel;
//import com.it.ocs.synchronou.service.IEBayItemService;
//import com.it.ocs.synchronou.service.impl.BaseEbaySDKService;
//import com.it.ocs.synchronou.service.impl.BaseHttpsService;
//import com.it.ocs.synchronou.service.impl.EbayAccountService;
//import com.it.ocs.synchronou.service.impl.TemplateService;
//import com.it.ocs.synchronou.util.ArrayUtil;
//import com.it.ocs.synchronou.util.ConversionDateUtil;
//import com.it.ocs.synchronou.util.RequestSDKMessage;
//import com.it.ocs.synchronou.util.ServerUrl;
//import com.it.ocs.synchronou.util.UTCTimeUtils;
//import com.it.ocs.synchronou.util.ValidationUtil;
//import com.it.ocs.sys.model.UserModel;
//import com.it.ocs.task.core.TaskExecutorUtil;
//import com.it.ocs.task.core.TaskRunnable;
//import com.it.ocs.task.model.ScheduleJob;
//import com.it.ocs.task.service.IScheduleJobAllService;
//import com.it.ocs.upload.core.JSCHImageUploadSupport;
//import com.it.ocs.upload.service.impl.UploadService;
//import com.it.ocs.upload.vo.FileVO;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@Service
//public class PublicationService extends BaseService implements IPublicationService {
//
//	private static final Logger logger = Logger.getLogger(PublicationService.class);
//	
//	private static Integer k=0;
//	@Autowired
//	private ITemplateDao templateDao;
//	@Autowired
//	private IEBayItemService ieBayItemService;
//	@Autowired
//	private IEBayTimingPlanDAO  timingPlanDao;
//	@Autowired
//	private EbayAccountService ebayAccountService;
//	@Autowired
//	private IPublicationViewService publicationViewService ;
//	@Autowired
//	private ProjectApplicationContext pac;
//	@Autowired
//	private IScheduleJobAllService  iJobAllService;
//	@Autowired
//	private EbayImageUploadService ebayImageUploadService;
//	
//	@Autowired
//	private TemplateService templateService;
//
//	@Autowired
//	private BaseHttpsService baseHttpService;
//
//	@Override
//	public void runTiming(){
//		ExecutorService cExecutorService = Executors.newCachedThreadPool();
//		cExecutorService.execute(new Runnable() {
//			
//			@Override
//			public void run() {
//				Date startDate = new Date();
//				String start = ConversionDateUtil.dateToCharFormat(startDate, "yyyy-MM-dd HH:mm:ss");
//				startDate = ConversionDateUtil.charToDate(start, "yyyy-MM-dd HH:mm:ss");
//				List<EBayTimingPlanModel> eBayTimingPlanModels = timingPlanDao.selectTimingPlanByFD(startDate);
//				if (eBayTimingPlanModels.size()>0) {
//					runSynTimingPlan(eBayTimingPlanModels);
//				}
//			
//			}
//		});
//	}
//	@Override
//	public void runSynTimingPlan(List<EBayTimingPlanModel> eBayTimingPlanModels){
//		try {
//			
//		if(eBayTimingPlanModels.size()>0){
//			Map<Integer, EBayTimingPlanModel> map = new HashMap<>();
//			for (EBayTimingPlanModel eBayTimingPlanModel : eBayTimingPlanModels) {
//				map.put(eBayTimingPlanModel.getTemplate_id(), eBayTimingPlanModel);
//			}
//			List<PublicationModel> PublicationModels = pubInfoDAO.selectPublicateByPlans(eBayTimingPlanModels);
//		    for (PublicationModel publicationModel : PublicationModels) {
//		    	ExecutorService cExecutorService = Executors.newCachedThreadPool();
//		    	cExecutorService.execute(new Runnable() {
//					
//					@Override
//					public void run() {
//						OperationResult result = new OperationResult();
//				    	Long id = publicationModel.getId();
//				    	EBayTimingPlanModel eBayTimingPlanModel = map.get(Integer.valueOf(id.toString()));
//						
//						result = ieBayItemService.publishedItem(id);
//						
//						eBayTimingPlanModel.setIs_success(result.getErrorCode());
//						eBayTimingPlanModel.setPublication_info(result.getDescription());
//						eBayTimingPlanModel.setReal_end_date(result.getEndDate());
//						eBayTimingPlanModel.setReal_publication_date(result.getStartDate());
//						eBayTimingPlanModel.setError_info(result.getError());
//						eBayTimingPlanModel.setNew_template_id(result.getId());
//						timingPlanDao.updateTimingPlan(eBayTimingPlanModel);
//					}
//			  });
//			}
//		}
//		} catch (Exception e) {
//			logger.error(e);
//		}
//	
//	}
//	@Override
//	public OperationResult batchUpdates(PublicationVO[] publicationVOs) {
//		OperationResult result = new OperationResult();
//		try {
//			List<PublicationModel> publicationModels = Arrays.asList(publicationVOs);
//			StringBuffer st =new StringBuffer();
//			for (PublicationVO publicationModel : publicationVOs) {
//				if(!ValidationUtil.isNullOrEmpty(publicationModel.getItemId())){
//					String state = updateItemType(publicationModel);
//					if(!ValidationUtil.isNull(state)){
//						st.append(state+"\n");
//					}
//				}else{
//					result.setDescription("更改成功");
//					pubInfoDAO.batchUpdates(publicationModels);
//					return result;
//				}
//				
//			}
//			
//			if(st.length()>0){
//				result.setDescription("以下物品号更新到线上失败，请手动更新！\n"+st);
//				
//			}else{
//				result.setDescription("更改成功");
//				
//			}
//			if(publicationModels.size()>0){
//				pubInfoDAO.batchLineUpdates(publicationModels);
//			}
//			
//		} catch (Exception e) {
//			result.setDescription("操作失败！");
//			e.printStackTrace();
//			return result;
//		}
//		return result;
//	}
//	/**
//	 * 检查ebay图片,必须上传ebay
//	 * @param urls
//	 * @param ebayAccount
//	 * @return
//	 */
//	private String checkAndUpdateEbayImage(String urls,String ebayAccount){
//		if(null == urls || "".equals(urls)){
//			return urls;
//		}
//		String images[] = urls.split(",");
//		StringBuffer newImages = new StringBuffer();
//		for(int i= 0;i<images.length;i++){
//			String img = images[i];
//			if(img.indexOf("i.ebayimg.com")== -1){
//				String url = "";
//				FileVO image = null;
//				if(img.indexOf("ocs/upload/image")>0){
//					//需判断图片是否上传图片服务器
//					String imageId = "";
//					if(img.endsWith("/")){
//						imageId = img.substring(0,img.length()-1);
//						imageId = imageId.substring(imageId.lastIndexOf("/")+1);
//					}else{
//						imageId = img.substring(img.lastIndexOf("/")+1);
//					}
//					image = pubInfoDAO.getImageInfoById(imageId);
//					image = uploadImageToServer(image);
//				}else if(img.indexOf("www.lightingimg.com")>0){
//					image = pubInfoDAO.getImageInfoByServerUrl(img);
//				}else{
//					//不是通过服务器上传的图片
//					image = new FileVO();
//					image.setServerUrl(img);
//					image.setName(String.valueOf(System.currentTimeMillis()));
//				}
//				url = image.getServerUrl();
//				if(null == url){
//					return "UPLOAD_FAIL";
//				}
//				url = ebayImageUploadService.imageUploadEbay(image,ebayAccount);
//				img = url;
//			}else{
//				
//			}
//			newImages.append(img+",");
//		}
//		urls = newImages.toString();
//		return urls.substring(0,urls.length()-1);
//	}
//	private FileVO uploadImageToServer(FileVO image) {
//		//没有上传图片服务器
//		if(image.getIsUpload() != 1){
//			String serverName = image.getSavePath();
//			serverName = serverName.substring(serverName.lastIndexOf("/")+1);
//			String serverUrl = uploadImageToServer(serverName,image.getSavePath());
//			image.setServerUrl(serverUrl);
//			image.setIsUpload(1);
//			//更新信息
//			pubInfoDAO.updateImageServerInfo(image);
//		}
//		return image;
//	}
//	
//	private String uploadImageToServer(String name,String filePath){
//		JSCHImageUploadSupport upload = JSCHImageUploadSupport.getInstance();
//		boolean f = upload.put(name, filePath);
//		int i = 1;
//		//失败一共重试3次
//		while(!f && i < 3){
//			f = upload.put(name, filePath);
//			i++;
//		}
//		if(f){
//			return UploadService.SERVCE_PATH+name;
//		}else{
//			return null;
//		}
//	}
//
//	private String checkAndUpdateTemplateImage(String urls,String ebayAccount){
//		if(null == urls || "".equals(urls)){
//			return urls;
//		}
//		String images[] = urls.split(",");
//		StringBuffer newImages = new StringBuffer();
//		for(int i= 0;i<images.length;i++){
//			String img = images[i];
//			if(img.indexOf("ocs/upload/image")>0){
//				//需判断图片是否上传图片服务器
//				String imageId = "";
//				if(img.endsWith("/")){
//					imageId = img.substring(0,img.length()-1);
//					imageId = imageId.substring(imageId.lastIndexOf("/")+1);
//				}else{
//					imageId = img.substring(img.lastIndexOf("/")+1);
//				}
//				FileVO image = pubInfoDAO.getImageInfoById(imageId);
//				image = uploadImageToServer(image);
//				//没有上传图片服务器
//				String serverUrl = image.getServerUrl();
//				if(null == serverUrl){
//					return "UPLOAD_FAIL";
//				}
//				newImages.append(serverUrl+",");
//			}else{
//				newImages.append(img+",");
//			}
//		}
//		urls = newImages.toString();
//		return urls.substring(0,urls.length()-1);
//	}
//	
//	@Override
//	@Transactional
//	public OperationResult savePubInfo(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		PublicationModel pubInfo = BeanConvertUtil.mapToObject2(map, PublicationModel.class);
//		Object advertId = map.get("advert_id");
//		if (!ValidationUtil.isNullOrEmpty(advertId)) {
//			pubInfo.setAdvert_id(Long.parseLong(advertId.toString()));
//		}
//		//检查ebay图片信息
//		String ebayImages = this.checkAndUpdateEbayImage(pubInfo.getEbayImages(),pubInfo.getEbayAccount());
//		if("UPLOAD_FAIL".equals(ebayImages)){
//			result.setDescription("图片服务器异常,请联系管理员！");
//			return result;
//		}
//		pubInfo.setEbayImages(ebayImages);
//
//		//检查template图片信息
//		String templateImages = this.checkAndUpdateTemplateImage(pubInfo.getTemplateImages(),pubInfo.getEbayAccount());
//		if("UPLOAD_FAIL".equals(templateImages)){
//			result.setDescription("图片服务器异常,请联系管理员！");
//			return result;
//		}
//		pubInfo.setTemplateImages(templateImages);
//		//多属性图片检查
//		String variations = pubInfo.getVariations();
//		if(null != variations && !"".equals(variations)){
//			JSONObject json = JSONObject.fromObject(variations);
//			JSONArray pictures = json.getJSONArray("pictures");
//			if(null != pictures&& !pictures.isEmpty()){
//				for(int i=0;i<pictures.size();i++){
//					JSONObject pic = pictures.getJSONObject(i);
//					String url = pic.getString("PictureURL");
//					String newUrl = this.checkAndUpdateEbayImage(url, pubInfo.getEbayAccount());
//					if(null != newUrl && !"".equals(newUrl)&&!newUrl.equals(url)){
//						variations = variations.replace(url, newUrl);
//					}
//				}
//			}
//			pubInfo.setVariations(variations);
//		}
//		
//		
//		String tempLateId = (String) map.get("id");
//		String state = map.get("state").toString();
//		Object itemId = map.get("itemId");
//		if (null != tempLateId && !StringUtils.isEmpty(tempLateId)) {// 更新
//			pubInfo.setId(Long.parseLong(tempLateId));
//			if(null != map.get("siteId")){
//				pubInfo.setSiteId(Long.parseLong(map.get("siteId").toString()));
//			}
//			super.updateInit(pubInfo);
//			if(ValidationUtil.isNullOrEmpty(itemId)){
//				pubInfoDAO.update(pubInfo);
//				pubInfoDAO.updateInfo(pubInfo);
//			}else{
//				pubInfoDAO.lineUpdate(pubInfo);
//			}
//			//更新运输方式
//			List<Map> domesticTrans = pubInfo.getDomesticTrans();
//			updatePublicationTransInfo(domesticTrans,tempLateId,0);
//			List<Map> calCulateTrans = pubInfo.getCalCulateTrans();
//			updatePublicationTransInfo(calCulateTrans,tempLateId,1);
//		} else {// 插入
//			Long id = pubInfoDAO.getId();
//			pubInfo.setId(id);
//			if(null != map.get("siteId")){
//				pubInfo.setSiteId(Long.parseLong(map.get("siteId").toString()));
//			}
//			super.insertInit(pubInfo);
//			pubInfoDAO.add(pubInfo);
//			pubInfoDAO.updateInfo(pubInfo);
//			//新增运输方式信息
//			List<Map> domesticTrans = pubInfo.getDomesticTrans();
//			addPublicationTransInfo(domesticTrans,id);
//			List<Map> calCulateTrans = pubInfo.getCalCulateTrans();
//			addPublicationTransInfo(calCulateTrans,id);
//		}
//		result.setDescription("保存成功！");
//		
//		Long publicationId = pubInfo.getId();
//		if(state.equals("1")){
//			if(ValidationUtil.isNullOrEmpty(itemId)){
//				result = ieBayItemService.publishedItem(publicationId);
//			}else{
//				Long endDateL = Long.parseLong(map.get("end_publication_date").toString());
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTimeInMillis(endDateL);
//				Date date = calendar.getTime();
//				Date sysdate = new Date();
//				String publicationDays = map.get("publicationDays").toString();
//				if(!ValidationUtil.isEquals(publicationId, "GTC")){
//					if(date.compareTo(sysdate)<=0){
//						result = ieBayItemService.relistPublicationItem(publicationId);
//					}else{
//						result = ieBayItemService.updateItemType(publicationId);
//					}
//				}
//			}
//		}
//		result.setData(pubInfo);
//		return result;
//	}
//	
//	/**
//	 * 新增范本关联运输信息
//	 * @param domesticTrans
//	 * @param id
//	 */
//	private void addPublicationTransInfo(List<Map> domesticTrans, Long id) {
//		if(null == domesticTrans){
//			return;
//		}
//		for(Map map: domesticTrans){
//			ShipOptionModel ship = formartShipOptionModel(id, map);
//			pubInfoDAO.addPublicationTransInfo(ship);
//		}
//		
//	}
//
//	private ShipOptionModel formartShipOptionModel(Long id, Map map) {
//		ShipOptionModel ship = new ShipOptionModel();
//		ship.setDomesticExtraCost(map.get("domesticExtraCost").toString());
//		if(null != map.get("domesticShipAkHiPr")){
//			ship.setDomesticShipAkHiPr(map.get("domesticShipAkHiPr").toString());
//		}
//		ship.setDomesticShipType(map.get("domesticShipType").toString());
//		ship.setDomesticShipCost(map.get("domesticShipCost").toString());
//		if(null != map.get("domesticShipId")){
//			ship.setDomesticShipId(map.get("domesticShipId").toString());
//		}
//		if(null != map.get("shipLocationIn")){
//			ship.setShipLocationIn(map.get("shipLocationIn").toString());
//		}
//		ship.setTranKind(map.get("tranKind").toString());
//		ship.setTranOrder(map.get("tranOrder").toString());
//		ship.setTempId(id);
//		return ship;
//	}
//	
//	/**
//	 * 更新范本关联运输信息
//	 * @param domesticTrans
//	 * @param tempLateId
//	 */
//	private void updatePublicationTransInfo(List<Map> domesticTrans, String tempLateId,int type) {
//		if(null == domesticTrans || domesticTrans.size() == 0){
//			Map<String,Object> map = new HashMap<>();
//			map.put("tempLateId", tempLateId);
//			map.put("type", type);
//			pubInfoDAO.clearTransByType(map);
//			return;
//		}
//		for(Map map: domesticTrans){
//			ShipOptionModel ship = formartShipOptionModel(Long.parseLong(tempLateId), map);
//			//List<ShipOptionModel> shipOptionModels = pubInfoDAO.getPublictonTransById(ship.getTempId().toString());
//			int hasExist = pubInfoDAO.transHasExist(ship);
//			if(hasExist>0){
//				pubInfoDAO.updatePublicationTransInfo(ship);
//			}else{
//				addPublicationTransInfo(domesticTrans, Long.parseLong(tempLateId));
//			}
//		}
//		
//	}
//
//	private List<Map<String, String>> updatePublicationModel(PublicationModel pubInfo, PublicationModel pubInfo1) throws Exception {
//		List<Map<String, String>> retruns = new ArrayList();
//		// 获取两个类中所有的属性
//		Field fields1[] = pubInfo.getClass().getDeclaredFields();
//		Field fields2[] = pubInfo1.getClass().getDeclaredFields();
//		for (int i = 0, j = fields1.length; i < j; i++) {
//			Field field1 = fields1[i];
//			field1.setAccessible(true);
//			for (int m = 0, n = fields2.length; m < n; m++) {
//				Field field2 = fields2[m];
//				field2.setAccessible(true);
//				if(field1.getName().equals(field2.getName())){
//					Object obj1 = field1.get(pubInfo);
//					Object obj2 = field2.get(pubInfo1);
//					if(obj1 instanceof String || obj1 instanceof Long){
//						if(null != obj1&&null != obj2 && (!field1.get(pubInfo).equals(field2.get(pubInfo1)))){
//							Map<String,String> map = new HashMap<String,String>();
//							map.put(field1.getName().toString(), String.valueOf(field1.get(pubInfo)));
//							retruns.add(map);
//						}
//					}
//					
//					break;
//				}
//
//			}
//		}
//		return retruns;
//	}
//
//	@Override
//	public ResponseResult<PublicationPageVO> queryPublication(RequestParam param) {
//		ResponseResult<PublicationPageVO> result = new ResponseResult<>();
//		Map<String,Object> map = param.getParam();
//		String conditions = map.get("conditions").toString();
//		List<PublicationModel> relationModels  = Lists.newArrayList();
//		int count =0;
//	    relationModels = publicationRelationDAO.queryByPageList(map, param.getStartRow(),param.getEndRow(),param.getSort(),param.getOrder());
//	    count = publicationRelationDAO.countList(map);
//		List<PublicationPageVO> datas = Lists.newArrayList();
//		convertList(relationModels, datas);
//		result.setRows(datas);
//		result.setTotal(count);
//		return result;
//	}
//	@Override
//	public ResponseResult<PublicationPageVO> queryLinePublication(RequestParam param) {
//		// TODO Auto-generated method stub
//		ResponseResult<PublicationPageVO> result = new ResponseResult<>();
//		Map<String,Object> map = param.getParam();
//		List<PublicationModel> relationModels  = Lists.newArrayList();
//		int count =0;
//		String conditions = map.get("conditions").toString();
//		if(conditions.equals("array")){
//			relationModels = publicationRelationDAO.selectPubByCondition(map, param.getStartRow(),param.getEndRow(),param.getSort(),param.getOrder());
//			count = publicationRelationDAO.conditionCountList(map);
//		}else{
//			relationModels = publicationRelationDAO.queryLineByPageList(map, param.getStartRow(),param.getEndRow(),param.getSort(),param.getOrder());
//			
//			count = publicationRelationDAO.countLineList(map);
//		}
//		List<PublicationPageVO> datas = Lists.newArrayList();
//		
//		convertList(relationModels, datas);
//		
//		result.setRows(datas);
//		result.setTotal(count);
//		return result;
//	}
//
//	private void convertList(List<PublicationModel> source, final List<PublicationPageVO> target) {
//		CollectionUtil.each(source, new IAction<PublicationModel>() {
//			@Override
//			public void excute(PublicationModel obj) {
//				PublicationPageVO pubPageVO = new PublicationPageVO();
//				BeanUtils.copyProperties(obj, pubPageVO);
//				target.add(pubPageVO);
//			}
//		});
//	}
//
//	private List<PublicationPageVO> constructPageVOs(List<RelationDataModel> dataModels,
//			List<PublicationRelationModel> relationModels) {
//		List<PublicationPageVO> result = Lists.newArrayList();
//		if (!CollectionUtil.isNullOrEmpty(dataModels)) {
//			CollectionUtil.each(dataModels, new IAction<RelationDataModel>() {
//				@SuppressWarnings("unchecked")
//				@Override
//				public void excute(RelationDataModel dataModel) {
//					PublicationPageVO publicationPageVO = new PublicationPageVO();
//					BeanUtils.copyProperties(dataModel, publicationPageVO);
//					if (!StringUtils.isEmpty(dataModel.getAutionItemInfo())) {
//						Map<String, Object> auctionItemInfo = JSONObject.fromObject(dataModel.getAutionItemInfo());
//						if (auctionItemInfo.containsKey("auctionCount")) {
//							publicationPageVO.setCount(auctionItemInfo.get("auctionCount").toString());
//						}
//					}
//					result.add(publicationPageVO);
//				}
//			});
//		}
//		return result;
//	}
//
//	@Override
//	public PublicationVO getObjById(Long id) {
//		PublicationModel publicatiocMdel = pubInfoDAO.getById(id);
//		PublicationVO publicationVO = new PublicationVO();
//		BeanUtils.copyProperties(publicatiocMdel, publicationVO);
//		return publicationVO;
//	}
//	@Override
//	public PublicationVO getLineById(Long id) {
//		PublicationModel publicationModel = pubInfoDAO.getLineById(id);
//		PublicationVO publicationVO = new PublicationVO();
//		BeanUtils.copyProperties(publicationModel, publicationVO);
//		return publicationVO;
//	}
//	@Override
//	public OperationResult lineRemoveByIds(String id) {
//		OperationResult result = new OperationResult();
//		try {
//			publicationRelationDAO.lineDelete(id);
//			result.setDescription("删除成功！");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			result.setDescription("删除失败");
//			return result;
//		}
//		return result;
//	}
//	@Override
//	public OperationResult removePubById(String id) {
//		
//		OperationResult result = new OperationResult();
//		try {
//			publicationRelationDAO.delete(id);
//			result.setDescription("删除成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setDescription("删除失败！");
//			return result;
//		}
//		return result;
//	}
//
//	@Override
//	public void parseItemDataStructure(String setName) {
//		ItemDataStructureModel itemDataStructureModel = otherDAO.getItemDataStructureXML(setName);
//		if (isParsed(setName)) {
//			logger.info(setName + " has parsed ,don't parse it");
//			return;
//		}
//		String xml = itemDataStructureModel.getContentXML();
//		if (StringUtils.isEmpty(xml)) {
//			logger.info("this xml is null ,don't parse it");
//			return;
//		}
//		try {
//			Document document = DocumentHelper.parseText(xml);
//			Element root = document.getRootElement();
//			Element item = root.element(setName);
//			itemDataStructureModel.setItem(item);
//			itemDataStructureModel.setName(setName);
//			itemDataStructureModel.setPath(setName);
//			List<ItemDataStructureModel> list = new ArrayList<>();
//			list.addAll(this.skipNodes(itemDataStructureModel));
//			otherDAO.saveItemDataStructure(list);
//		} catch (DocumentException e) {
//			logger.info("parse xml is failed ", e);
//		}
//	}
//
//	private List<ItemDataStructureModel> skipNodes(ItemDataStructureModel node) {
//		List<ItemDataStructureModel> list = new ArrayList<>();
//		// 递归遍历当前节点所有的子节点
//		List<Element> listElement = node.getItem().elements();// 所有一级子节点的list
//		for (Element element : listElement) {// 遍历所有一级子节点
//			ItemDataStructureModel itemDataStructureModel = new ItemDataStructureModel();
//			itemDataStructureModel.setId(otherDAO.getDataStructureId());
//			itemDataStructureModel.setItem(element);
//			itemDataStructureModel.setName(element.getName());
//			itemDataStructureModel.setPath(node.getPath() + "." + element.getName());
//			itemDataStructureModel.setParentId(node.getId());
//			list.add(itemDataStructureModel);
//			List<ItemDataStructureModel> items = this.skipNodes(itemDataStructureModel);// 递归
//			list.addAll(items);
//		}
//
//		return list;
//	}
//
//	private boolean isParsed(String name) {
//		if (otherDAO.getChildrenCount(name) > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public List<ComboBoxVO> getPromoteList(String type) {
//		return pubInfoDAO.getPromoteList(type);
//	}
//
//	@Override
//	public List<ComboBoxVO> getSellerDescription(String siteId) {
//		return pubInfoDAO.getSellerDescription(siteId);
//	}
//
//	@Override
//	public EBayReturnPolicyDetailModel getReturnPolicyData(String siteId) {
//		return pubInfoDAO.getReturnPolicyData(siteId);
//	}
//
//	@Override
//	public List<ShipOptionModel> getPublictonTransById(String id) {
//		return pubInfoDAO.getPublictonTransById(id);
//	}
//
//	@Override
//	public List<ComboBoxVO> getTransTypeData(String siteId) {
//		return pubInfoDAO.getTransTypeData(siteId);
//	}
//
//	@Override
//	public List<ComboBoxVO> getPaymentSupportData(String siteId) {
//		return pubInfoDAO.getPaymentSupportData(siteId);
//	}
//
//	@Override
//	public ResponseResult<PublicationSaveAsModel> saveAslist(RequestParam param) {
//		ResponseResult<PublicationSaveAsModel> result = new ResponseResult<>();
//		Map<String,Object> map = param.getParam();
//		List<PublicationSaveAsModel> saveAsList = pubInfoDAO.queryByPageSaveAs(map,param.getStartRow(),param.getEndRow());
//		int count = pubInfoDAO.countSaveAs(map);
//		result.setRows(saveAsList);
//		result.setTotal(count);
//		return result;
//	}
//
//	@Override
//	public OperationResult saveAs(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		Object id = map.get("id");
//		Integer count = null;
//		if(null == id||id==""){
//			count = pubInfoDAO.saveAs(map);
//		}else{
//			count = pubInfoDAO.updateSaveAs(map);
//		}
//		result.setData(count);
//		return result;
//	}
//	
//	@Override
//	public ResponseResult<PublicationSaveAsModel> modelManagerlist(RequestParam param) {
//		ResponseResult<PublicationSaveAsModel> result = new ResponseResult<>();
//		Map<String,Object> map = param.getParam();
//		List<PublicationSaveAsModel> saveAsList = pubInfoDAO.queryByPageModelManager(map,param.getStartRow(),param.getEndRow());
//		int count = pubInfoDAO.countModelManager(map);
//		result.setRows(saveAsList);
//		result.setTotal(count);
//		return result;
//	}
//
//	@Override
//	public OperationResult saveAsDelete(String ids) {
//		OperationResult result = new OperationResult();
//		if(null != ids && !StringUtils.isEmpty(ids)){
//			String[] idArray = ids.split("-");
//			List list = new ArrayList();
//			list = Arrays.asList(idArray);
//			int count = pubInfoDAO.saveAsDelete(list);
//			result.setData(count);
//		}
//		return result;
//	}
//
//	@Override
//	public List<ComboBoxVO> getSiteList() {
//		return pubInfoDAO.getSiteList();
//	}
//
//	@Override
//	public OperationResult templateView(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		PublicationModel pubInfo = BeanConvertUtil.mapToObject2(map, PublicationModel.class);
//		Object advertId = map.get("advert_id");
//		if(!ValidationUtil.isNullOrEmpty(advertId)){
//			pubInfo.setAdvert_id(Long.parseLong(advertId.toString()));
//		}
//		if(null != map.get("siteId")){
//			pubInfo.setSiteId(Long.parseLong(map.get("siteId").toString()));
//		}
//		result.setData(publicationViewService.toViewString(pubInfo));
//		return result;
//	}
//
//	@Override
//	public OperationResult templateViewForOld(String templateId) {
//		OperationResult result = new OperationResult();
//		PublicationModel pubInfo = pubInfoDAO.getById(Long.parseLong(templateId));
//		if(null != pubInfo.getOldDescription()&&!"".equals(pubInfo.getOldDescription())){
//			result.setData(pubInfo.getOldDescription());
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult checkSKUAndTitle(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		result.setData(pubInfoDAO.checkSKUAndTitle(map));
//		return result;
//	}
//
//	@Override
//	public OperationResult countPub(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		Object siteId = map.get("siteId");
//		if("all".equals(siteId)) {map.put("siteId", "");}
//		result.setData(publicationRelationDAO.countList(map));
//		return result;
//	}
//	@Override
//	public OperationResult countLinePub(Map<String, Object>map) {
//		OperationResult result = new OperationResult();
//		if("all".equals(map.get("siteId"))) map.put("siteId", "");
//		result.setData(publicationRelationDAO.countLineList(map));
//		return result;
//	}
//	
//	@Override
//	public OperationResult copyItemPublication(Long id,PublicationModel param) {
//		OperationResult result = new OperationResult();
//		try {
//				Long seq = null;
//				String name = param.getName();
//				String itemId = param.getItemId();
//				PublicationModel publicationModel   = pubInfoDAO.getById(id);
//				publicationModel.setEbayProductURL(param.getEbayProductURL());
//				publicationModel.setItemId(param.getItemId());
//				publicationModel.setEnd_publication_date(param.getEnd_publication_date());
//				publicationModel.setPublication_date(param.getPublication_date());
//				publicationModel.setProductCount(param.getProductCount());
//				if(ValidationUtil.isNull(name)){
//					publicationModel.setCorrelation_id(id);
//				}else{
//					publicationModel.setTemplateName(param.getName());
//				}
//				if(ValidationUtil.isNull(itemId)){
//					seq = pubInfoDAO.getId();
//					publicationModel.setId(seq);
//					pubInfoDAO.add(publicationModel);
//					pubInfoDAO.updateInfo(publicationModel);
//				}else{
//					seq = pubInfoDAO.getLineId();
//					publicationModel.setId(seq);
//					pubInfoDAO.addLine(publicationModel);
//					pubInfoDAO.updateLinePuByOnline2(publicationModel);
//				}
//				result.setId(seq);
//				publicationModel.setId(id);
//				List<ShipOptionModel> shipOptionModels =  pubInfoDAO.getPublictonTransById(id.toString());
//				if(shipOptionModels.size()>0){
//					for (ShipOptionModel shipOptionModel : shipOptionModels) {
//						shipOptionModel.setTempId(seq);
//					}
//					pubInfoDAO.addPublicationTransInfos(shipOptionModels);
//					
//				}
//		} catch (Exception e) {
//			result.setDescription("复制失败！");
//			logger.error(e);
//		}
//		return result;
//	}
//	@Override
//	public OperationResult copyLinePus(PublicationVO[] publicationVOs) {
//		OperationResult result = new OperationResult();
//		StringBuffer str = new StringBuffer();
//		for (PublicationVO publicationVO : publicationVOs) {
//			PublicationVO publicationVO2 = new PublicationVO();
//			publicationVO2.setId(publicationVO.getId());
//			publicationVO2.setName(publicationVO.getTemplateName());
//			publicationVO2.setItemId(publicationVO.getItemId());
//			publicationVO2.setSku(publicationVO.getSku());
//			String correlationId = publicationVO.getCorrelationId();
//			if(!ValidationUtil.isNull(correlationId)&&correlationId.equals("关联")){
//				publicationVO2.setCorrelation_id(1l);
//			}
//			result = copyLineItemPublication(publicationVO2);
//			str.append(publicationVO2.getItemId()+":"+result.getDescription()+"<br>");
//		}
//		
//			
//		result.setDescription(str.toString());
//		return result;
//	}
//	@Override
//	public OperationResult synSelectItems(PublicationVO[] publicationVOs) {
//		OperationResult result = new OperationResult();
//		for (PublicationVO publicationVO : publicationVOs) {
//			 List<PublicationModel> publicationModels = Lists.newArrayList();
//			 PublicationModel publicationModel = new PublicationModel();
//			 publicationModel.setSiteId(publicationVO.getSiteId());
//			 publicationModel.setItemId(publicationVO.getItemId());
//			 publicationModels.add(publicationModel);
//			 EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(publicationVO.getEbayAccount());
//			 updatePublicationInfo(ebayAccountModel, publicationModels);
//		}
//		result.setDescription("操作成功");
//		return result;
//	}
//	@Override
//	public OperationResult copyLineItemPublication(PublicationModel param) {
//		OperationResult result = new OperationResult();
//		try {
//				Long seq = pubInfoDAO.getId();
//				Long id = param.getId();
//				String sku = param.getSku();
//				PublicationModel publicationModel   = pubInfoDAO.getLineById(id);
//				publicationModel.setEbayProductURL(null);
//				publicationModel.setItemId(null);
//				publicationModel.setEnd_publication_date(null);
//				publicationModel.setPublication_date(null);
//				publicationModel.setCorrelation_id(null);
//				publicationModel.setTemplateName(param.getName());
//				if(!ValidationUtil.isNull(sku)){
//					publicationModel.setSku(sku);
//				}
//				
//				publicationModel.setId(seq);
//				pubInfoDAO.add(publicationModel);
//				pubInfoDAO.updatePublicationInfo(publicationModel);
//				result.setId(seq);
//				publicationModel.setId(id);
//				List<ShipOptionModel> shipOptionModels =  pubInfoDAO.getPublictonTransById(id.toString());	
//				if(shipOptionModels.size()>0){
//					for (ShipOptionModel shipOptionModel : shipOptionModels) {
//						shipOptionModel.setTempId(seq);
//					}
//					pubInfoDAO.addPublicationTransInfos(shipOptionModels);
//					
//				}
//				if(!ValidationUtil.isNull(param.getCorrelation_id())){
//					publicationModel.setCorrelation_id(seq);
//					pubInfoDAO.updateLineCorrelation(publicationModel);
//				}
//				result.setDescription("复制成功！");
//		} catch (Exception e) {
//			result.setDescription("复制失败！");
//			logger.error(e);
//		}
//		return result;
//	}
//	@Override
//	public OperationResult savePlanPublication(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		List<EBayTimingPlanModel> planPublicationModes = Lists.newArrayList();
//		PublicationModel publicationModel = new PublicationModel();
//		String strDate= map.get("timingPlanPublicationDate").toString();
//		strDate = strDate.replace("+", " ");
//		
//	    Date start = ConversionDateUtil.charToDate(strDate, "yyyy-MM-dd HH:mm:ss");
//		Integer dayMumber = Integer.parseInt(map.get("mumberOfDays").toString());
//		Integer numberOf = Integer.parseInt(map.get("numberOf").toString());
//		String delayMinutes = map.get("delayMinutes").toString();
//		if(ValidationUtil.isNullOrEmpty(delayMinutes)){
//			delayMinutes="0";
//		}
//		Integer id = Integer.parseInt(map.get("id").toString());
//		publicationModel.setId(Long.parseLong(map.get("id").toString()));
//		publicationModel.setDay_number(dayMumber);
//		publicationModel.setOf_number(numberOf);
//		publicationModel.setIs_plan(1);
//		Integer siteId = Integer.parseInt(map.get("timingSiteId").toString());
//		String name = map.get("timingTemplateName").toString();
//		name = name.replace("+", " ");
//	    for (int i=0;i<numberOf;i++) {
//	    	Integer day = dayMumber*i;
//	    	Calendar calendar = Calendar.getInstance();
//	    	calendar.setTime(start);
//	    	calendar.add(Calendar.DAY_OF_MONTH, day);
//			EBayTimingPlanModel planPublicationMode = new EBayTimingPlanModel();
//			Date siteDate = calendar.getTime();
//			planPublicationMode.setFirst_site_date(siteDate);
//			Integer minutesNumber = Integer.parseInt(delayMinutes)*(i+1);
//			calendar.add(Calendar.MINUTE, minutesNumber);
//			String siteDateStr = ConversionDateUtil.dateToCharFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
//			siteDateStr =ieBayItemService.getLocalBySiteId(siteDateStr, Long.parseLong(siteId.toString())).getData().toString();
//			planPublicationMode.setFirst_date(ConversionDateUtil.charToDate(siteDateStr, "yyyy-MM-dd HH:mm:ss"));
//			planPublicationMode.setTemplate_id(id);
//			planPublicationMode.setSite_id(siteId);
//			planPublicationMode.setTemplate_name(name);
//			planPublicationModes.add(planPublicationMode);
//					
//		}
//	    pubInfoDAO.insertPlanPublications(planPublicationModes);
//	    pubInfoDAO.updatePublicatePlan(publicationModel);
//	    EBayTimingPlanModel planPublicationMode = new EBayTimingPlanModel();
//	    planPublicationMode.setTemplate_id(id);
//	    List<ScheduleJob> scheduleJobs = timingPlanDao.getScheduleJobByPId(planPublicationMode);
//	    for (ScheduleJob scheduleJob : scheduleJobs) {
//	    	iJobAllService.addTask(scheduleJob);
//		}
//	    
//	    result.setDescription("定时计划成功！");
//	    return result;
//	}
//
//	
//
//	
//	public  String updateItemType(PublicationVO publicationVO){
//		String ebayAccount =publicationVO.getEbayAccount();
//		EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(ebayAccount);
//		String token = ebayAccountModel.getToken();
//		String serverUrl = ServerUrl.getServerUrl(ebayAccount);
//		
//		ApiContext apiContext = BaseEbaySDKService.getApiContext(token, serverUrl);
//		ReviseItemCall reviseItemCall = new ReviseItemCall(apiContext);
//		ItemType itemType = getItemType(publicationVO);
//		reviseItemCall.setItemToBeRevised(itemType);
//		try {
//			 reviseItemCall.reviseItem();
//		} catch (Exception e) {
//			logger.error(e);
//			return itemType.getItemID();
//		}
//		return null;
//	}
//	
//	public ItemType getItemType(PublicationVO publicationVO){
//		ItemType itemType = new ItemType();
//		Long siteID = publicationVO.getSiteId();
//		CurrencyCodeType currency = CurrencyCodeTypeEnum.searchCurrencyBySiteID(siteID);
//		itemType.setItemID(publicationVO.getItemId());
//		
//		itemType.setSKU(publicationVO.getSku());
//		itemType.setTitle(publicationVO.getProductTitle());
//		itemType.setSubTitle(publicationVO.getProductSubtitle());
//		String price = publicationVO.getPrice();
//		if (!ValidationUtil.isNullOrEmpty(price)) {
//			itemType.setStartPrice(EbayMappingToSDK.getAmountTypeByStr(publicationVO.getPrice(), currency));
//
//		}
//		String reservePrice = publicationVO.getReserverPrice();
//		if (!ValidationUtil.isNullOrEmpty(reservePrice)) {
//			itemType.setReservePrice(EbayMappingToSDK.getAmountTypeByStr(reservePrice, currency));//保留价格
//
//		}
//		String buyoutPrice = publicationVO.getBuyoutPrice();
//		if(!ValidationUtil.isNullOrEmpty(buyoutPrice)){
//		itemType.setBuyItNowPrice(EbayMappingToSDK.getAmountTypeByStr(buyoutPrice, currency));
//		}
//		String  quantity = publicationVO.getProductCount();
//		if(!ValidationUtil.isNullOrEmpty(quantity)){
//		itemType.setQuantity(Integer.parseInt(quantity));
//		}
//		return itemType;
//	}
//	@Override
//	public List<ComboBoxVO> getAccountList() {
//		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
//		List<ComboBoxVO> comboBoxVOs = new ArrayList<>();
//		for(EbayAccountModel ebayAccount :accounts){
//			ComboBoxVO combobox = new ComboBoxVO();
//			combobox.setDisplayName(ebayAccount.getAccount());
//			combobox.setValue(ebayAccount.getAccount());
//			comboBoxVOs.add(combobox);
//		}
//		return comboBoxVOs;
//	}
//	@Override
//	public PublicationModel selectPubById(Long id) {
//		return pubInfoDAO.selectPubById(id);
//	}
//	@Override
//	public GridResult<Map<String, Object>> aLotModifyList(RequestParam param) {
//		StringBuffer sqlCondition = new StringBuffer();
//		Map<String,Object> map = param.getParam();
//		String tableFlag = (String)map.get("tableFlag");
//		if("line".equals(tableFlag)){
//			map.put("table", "ebay_publication_line");
//		}else{
//			map.put("table", "ebay_publication_info");
//		}
//
//		List<Map<String,Object>> list = pubInfoDAO.getALotModifyList(map);
//		List<Map<String,Object>> rows = new ArrayList<>();
//		for(Map<String,Object> publication : list){
//			Map<String,Object> row = new HashMap<>();
//			//查询关联的运输信息
//			List<Map<String,Object>> trans = pubInfoDAO.getTransInfoById(String.valueOf(publication.get("TEMPLATE_ID")));
//			Map<String,Object> dom = null;
//			Map<String,Object> cu = null;
//			if(null != trans){
//				for(Map<String,Object> tran : trans){
//					if("0".equals(String.valueOf(tran.get("TRAN_KIND")))){
//						cu = tran;
//					}else{
//						dom = tran;
//					}
//				}
//			}
//			
//			for(Map.Entry<String, Object> entry : map.entrySet()){
//				String key = entry.getKey();
//				if(key.startsWith("1_")){
//					if(null != dom){
//						row.put(key, dom.get(key.substring(2)));
//					}else{
//						row.put(key, "");
//					}
//				}else if(key.startsWith("0_")){
//					if(null != cu){
//						row.put(key, cu.get(key.substring(2)));
//					}else{
//						row.put(key, "");
//					}
//				}else if("_buyer_require".endsWith(key)){
//					row.put("ALLOW_ALLBUYER",publication.get("ALLOW_ALLBUYER"));
//					row.put("NO_PAYPAL",publication.get("NO_PAYPAL"));
//					row.put("OUT_SHIP_COUNTRY",publication.get("OUT_SHIP_COUNTRY"));
//					row.put("BUYER_REQ_1",publication.get("BUYER_REQ_1"));
//					row.put("BUYER_REQ_2",publication.get("BUYER_REQ_2"));
//					row.put("BUYER_REQ_3",publication.get("BUYER_REQ_3"));
//					row.put("BUYER_REQ_4",publication.get("BUYER_REQ_4"));
//					row.put("BUYER_REQ_4_1",publication.get("BUYER_REQ_4_1"));
//				}else{
//					row.put(key, publication.get(key));
//				}
//			}
//			row.put("TEMPLATE_ID", publication.get("TEMPLATE_ID"));
//			rows.add(row);
//		}
//		
//		GridResult<Map<String, Object>> re = new GridResult<Map<String, Object>>();
//		
//		re.setRows(rows);
//		//int count = pubInfoDAO.countALotModifyList(map);
//		re.setTotal(list.size());
//		return re;
//	}
//	@Override
//	public OperationResult aLotModifySave(Map<String, Object> map) {
//		List<Map<String,Object>> dataList = (ArrayList<Map<String,Object>>)map.get("data");
//		String type = (String)map.get("type");
//		
//		for(Map<String,Object> row :dataList){
//			StringBuffer sqlSet = new StringBuffer();
//			UserModel user =  getCurentUser();
//			StringBuffer domUpdate = new StringBuffer();
//			StringBuffer cuUpdate = new StringBuffer();
//			Map<String,Object> dom = new HashMap<>();
//			dom.put("TRAN_KIND", 1);
//			Map<String,Object> cu = new HashMap<>();
//			cu.put("TRAN_KIND", 0);
//			sqlSet.append(" LAST_UPDATE_DATE = sysdate , LAST_UPDATE_BY = "+ user.getId());
//			for(Map.Entry<String,Object> entry:row.entrySet()){
//				String key = entry.getKey();
//				if(key.startsWith("1_")){
//					dom.put(key.substring(2), entry.getValue());
//					domUpdate.append(","+key.substring(2)+"='"+entry.getValue()+"'");
//					continue;
//				}else if(key.startsWith("0_")){
//					cu.put(key.substring(2), entry.getValue());
//					cuUpdate.append(","+key.substring(2)+"='"+entry.getValue()+"'");
//					continue;
//				}
//				if(!"TEMPLATE_ID".equals(key)){
//					sqlSet.append(","+key+"='"+entry.getValue()+"'");
//				}
//			}
//			row.put("updateSet", sqlSet.toString());
//			if(null != type && "line".equals(type)){
//				pubInfoDAO.updatePulicationLineByALot(row);
//			}else{
//				pubInfoDAO.updatePulicationByALot(row);
//			}
//			if(dom.size() > 1){
//				dom.put("TEMPLATE_ID", row.get("TEMPLATE_ID"));
//				dom.put("UPDATESET", domUpdate.toString().substring(1));
//				pubInfoDAO.updateTrans(dom);
//			}
//			if(cu.size()>1){
//				cu.put("TEMPLATE_ID", row.get("TEMPLATE_ID"));
//				cu.put("UPDATESET", cuUpdate.toString().substring(1));
//				pubInfoDAO.updateTrans(cu);
//			}
//		}
//		OperationResult or = null;
//		//在线刊登，更新线上数据
//		if(null != type && "line".equals(type)){
//			or = this.aLotModifySaveAndUpdate(map);
//		}else{
//			or = new OperationResult();
//		}
//		or.setData("success");
//		return or;
//	}
//	@Override
//	public List<ComboBoxVO> getTemplateBanner(String siteId) {
//		return pubInfoDAO.getTemplateBanner(siteId);
//	}
//	@Override
//	public OperationResult aLotModifyAllSave(Map<String, Object> map) {
//		//条件
//		Map<String,Object> condition = (Map<String,Object> )map.get("condition");
//		StringBuffer sqlCondition = new StringBuffer();
//		for(Map.Entry<String, Object> entry : condition.entrySet()){
//			String value = String.valueOf(entry.getValue());
//			String key = entry.getKey();
//			if(key.startsWith("1_")||key.startsWith("0_")){
//				continue;
//			}
//			if(null !=value && !"".equals(value)){
//				if("TEMPLATE_NAME".equals(key)||"PRODUCT_TITLE".equals(key)){
//					sqlCondition.append(" AND "+entry.getKey() + " like '%"+value+"%' ");
//				}else{
//					sqlCondition.append(" AND "+entry.getKey() + "='"+value+"' ");
//				}
//			}
//		}
//		
//		//数据
//		Map<String,Object> data = (Map<String,Object> )map.get("data");
//		StringBuffer sqlSet = new StringBuffer();
//		UserModel user =  getCurentUser();
//		StringBuffer domUpdate = new StringBuffer();
//		StringBuffer cuUpdate = new StringBuffer();
//		Map<String,Object> dom = new HashMap<>();
//		dom.put("TRAN_KIND", 1);
//		Map<String,Object> cu = new HashMap<>();
//		cu.put("TRAN_KIND", 0);
//		sqlSet.append(" LAST_UPDATE_DATE = sysdate , LAST_UPDATE_BY = "+ user.getId());
//		for(Map.Entry<String,Object> entry:data.entrySet()){
//			String key = entry.getKey();
//			if(key.startsWith("1_")){
//				dom.put(key.substring(2), entry.getValue());
//				domUpdate.append(","+key.substring(2)+"='"+entry.getValue()+"'");
//				continue;
//			}else if(key.startsWith("0_")){
//				cu.put(key.substring(2), entry.getValue());
//				cuUpdate.append(","+key.substring(2)+"='"+entry.getValue()+"'");
//				continue;
//			}
//			if(!"TEMPLATE_ID".equals(key)){
//				sqlSet.append(","+key+"='"+entry.getValue()+"'");
//			}
//		}
//		data.put("updateSet", sqlSet.toString());
//		data.put("CONDITION", sqlCondition.toString());
//		pubInfoDAO.updateAllPulicationByALot(data);
//		if(dom.size() > 1){
//			dom.put("CONDITION", sqlCondition.toString());
//			dom.put("UPDATESET", domUpdate.toString().substring(1));
//			pubInfoDAO.updateAllTrans(dom);
//		}
//		if(cu.size()>1){
//			cu.put("CONDITION", sqlCondition.toString());
//			cu.put("UPDATESET", cuUpdate.toString().substring(1));
//			pubInfoDAO.updateAllTrans(cu);
//		}
//		
//		OperationResult re = new OperationResult();
//		re.setData("success");
//		return re;
//	}
//	@Override
//	public OperationResult aLotModifySaveAndUpdate(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		StringBuffer stringBuffer = new StringBuffer();
//		List<Map<String,Object>> dataList = (ArrayList<Map<String,Object>>)map.get("data");
//		for (Map<String, Object> map2 : dataList) {
//			PublicationModel publicationModel = pubInfoDAO.getLineById(Long.parseLong(map2.get("TEMPLATE_ID").toString()));
//		    Date date = new Date();
//			if(publicationModel.getItemId()!=null&&publicationModel.getEnd_publication_date().compareTo(date)>0){
//				OperationResult operationResult = ieBayItemService.updateLineItem(new PublicationAddItemModel(publicationModel));
//				String ack = operationResult.getData().toString();
//				if(!ack.equalsIgnoreCase("Warning")&&!ack.equalsIgnoreCase("Success")){
//					stringBuffer.append(operationResult.getDescription());
//				}
//			}
//		}
//		
//		result.setDescription(stringBuffer.toString());
//		return result;
//	}
//	@Override
//	public OperationResult aLotModifySaveToCorrelation(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		try {
//			
//				List<Map<String,Object>> dataList = (ArrayList<Map<String,Object>>)map.get("data");
//				List<Map<String, Object>> nullList = Lists.newArrayList();
//				for (Map<String, Object> map2 : dataList) {
//					PublicationModel publicationModel = pubInfoDAO.getById(Long.parseLong(map2.get("TEMPLATE_ID").toString()));
//					Long correlation = publicationModel.getCorrelation_id();
//					if(!ValidationUtil.isNull(correlation)){
//						map2.put("TEMPLATE_ID", correlation);
//					}else{
//						nullList.add(map2);
//					}
//				}
//				dataList.removeAll(nullList);
//				if(dataList.size()>0){
//					aLotModifySave(map);
//				}
//				result.setDescription("更改成功！");
//				
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setDescription("更改失败！");
//			return result ;
//		}
//		return result;
//	}
//	
//	@Override
//	public PublicationVO getPublicationById(Long id, String conditions) {
//		if(conditions.equals("main")){
//			return getObjById(id);
//		}else{
//			return getLineById(id);
//		}
//	}
//	@Override
//	public OperationResult updateCorrelation(PublicationVO publicationVO) {
//		OperationResult result = new OperationResult();
//		try {
//			pubInfoDAO.updateLineCorrelation(publicationVO);
//			result.setDescription("操作成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setDescription("操作失败！");
//			return result;
//		}
//		return result;
//	}
//	@Override
//	public OperationResult sysPuHitCount(Map<String, Object> map) {
//		OperationResult result = new OperationResult();
//		List<PublicationModel> publicationModels =  publicationRelationDAO.queryLineList(map);
//		for (PublicationModel publicationModel : publicationModels) {
//			EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(publicationModel.getEbayAccount());
//			List<PublicationModel> publicationModels2 = Lists.newArrayList();
//			publicationModels2.add(publicationModel);
//			updatePublicationInfo(ebayAccountModel, publicationModels2);
//			result.setDescription("操作成功！ ");
//			
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult updateLineZero(PublicationVO[] publicationVOs) {
//		OperationResult result = new OperationResult();
//		StringBuffer stringBuffer = new StringBuffer();
//		for (PublicationVO publicationVO : publicationVOs) {
//			
//			  String ebayAcsount = publicationVO.getEbayAccount();
//			  String itemId= publicationVO.getItemId();
//			  EbayAccountModel ebayAccountModel = ebayAccountService.getAccountByAccount(ebayAcsount);
//			  String serverUrl = ServerUrl.getServerUrl(ebayAcsount);
//			  ApiContext apiContext = BaseEbaySDKService.getApiContext(ebayAccountModel.getToken(), serverUrl);
//			  SetUserPreferencesCall setUserPreferencesCall = new SetUserPreferencesCall(apiContext);
//			  SellerFavoriteItemPreferencesType sellerferencesType = new SellerFavoriteItemPreferencesType();
//			  List<String> itemIdList = Lists.newArrayList();
//			  itemIdList.add(publicationVO.getItemId());
//			  String[] itemIdArray = new String[itemIdList.size()];
//			  String[] ia = itemIdList.toArray(itemIdArray);
//			  sellerferencesType.setFavoriteItemID(ia);
//			   setUserPreferencesCall.setOutOfStockControlPreference(Boolean.TRUE);
//			   setUserPreferencesCall.setSellerFavoriteItemPreferences(sellerferencesType);
//			   ReviseInventoryStatusCall reviseInventoryStatusCall= new ReviseInventoryStatusCall();
//			   try {
//				setUserPreferencesCall.setUserPreferences();
//				String variations = publicationVO.getVariations();
//				 reviseInventoryStatusCall = new ReviseInventoryStatusCall(apiContext);
//				List<InventoryStatusType> inventoryStatusTypes = Lists.newArrayList();
//				if(ValidationUtil.isNull(variations)){
//					InventoryStatusType inventoryStatusType = new InventoryStatusType();
//					inventoryStatusType.setSKU(publicationVO.getSku());
//					inventoryStatusType.setItemID(itemId);
//					inventoryStatusType.setQuantity(0);
//					inventoryStatusTypes.add(inventoryStatusType);
//				}else{
//					JSONObject jsonObject = JSONObject.fromObject(variations);
//					JSONArray jsonArray = jsonObject.getJSONArray("variations");
//					JSONArray jsonArray2 = new JSONArray();
//					for (Object object : jsonArray) {
//						JSONObject jsonObject2 = JSONObject.fromObject(object);
//						InventoryStatusType inventoryStatusType = new InventoryStatusType();
//						inventoryStatusType.setItemID(itemId);
//						inventoryStatusType.setSKU(jsonObject2.getString("SKU"));
//						inventoryStatusType.setQuantity(0);
//						inventoryStatusTypes.add(inventoryStatusType);
//						jsonObject2.put("Quantity", "0");
//						object=jsonObject2.toString();
//						jsonArray2.add(object);
//					}
//					jsonObject=new JSONObject();
//					jsonObject.put("variations", jsonArray2.toString());
//					
//					
//					publicationVO.setVariations(jsonObject.toString());
//				}
//				reviseInventoryStatusCall.setInventoryStatus(ArrayUtil.listToArray(inventoryStatusTypes, InventoryStatusType.class));
//				reviseInventoryStatusCall.reviseInventoryStatus();
//				InventoryFeesType[] inventoryFeesType = reviseInventoryStatusCall.getReturnedFees();
//				stringBuffer.append(itemId+RequestSDKMessage.getSDKMessage(reviseInventoryStatusCall, "隐藏"));
//				for (InventoryFeesType inventoryFeesType2 : inventoryFeesType) {
//					FeeType[] feesTypes = inventoryFeesType2.getFee();
//					Map<String, String> feeMap = RequestSDKMessage.getSDKFeeMessage(feesTypes);
//					stringBuffer.append(feeMap.get("totalPrice"));
//					stringBuffer.append(feeMap.get("feeBuffer"));
//				}
//				publicationVO.setQuantity_available(0);
//				pubInfoDAO.updateAvailableByItemId(publicationVO);
//				
//			} catch (ApiException e) {
//				stringBuffer.append(itemId+RequestSDKMessage.getSDKMessage(reviseInventoryStatusCall, "隐藏"));
//				logger.error(e);
//			} catch (SdkException e) {
//				stringBuffer.append(itemId+RequestSDKMessage.getSDKMessage(reviseInventoryStatusCall, "隐藏"));
//				logger.error(e);
//			} catch (Exception e) {
//				stringBuffer.append(itemId+RequestSDKMessage.getSDKMessage(reviseInventoryStatusCall, "隐藏"));
//				logger.error(e);
//			}
//			
//		}
//		result.setDescription(stringBuffer.toString());
//		return result;
//	}
//	@Override
//	public OperationResult getPImgByItemId(String itemId) {
//		OperationResult result = new OperationResult();
//		try {
//			PublicationModel publicationModel =pubInfoDAO.getPImgByItemId(itemId);
//		    if(!ValidationUtil.isNull(publicationModel)){
//		    	result.setDescription(publicationModel.getEbayImages());
//		    }else{
//		    	result.setDescription(null);
//		    }
//		
//		} catch (Exception e) {
//			logger.error(e);
//			result.setDescription(null);
//			return result;
//		}
//		return result;
//	}
//	@Override
//	public OperationResult synchronouPublication() {
//		OperationResult result = new OperationResult();
//		List<EbayAccountModel> ebayAccountModels = ebayAccountService.getAccounts();
//		result = getAllLineItem(ebayAccountModels);
//		return result;
//	}
//	public OperationResult getAllLineItem(List<EbayAccountModel> ebayAccountModels){
//		OperationResult result = new OperationResult();
//		//ExecutorService cExecutorService = Executors.newFixedThreadPool(100);
//		for (EbayAccountModel ebayAccountModel : ebayAccountModels) {
//			ExecutorService cExecutorService = Executors.newCachedThreadPool();
//					int total = getMyebaySell(ebayAccountModel);
//					pubInfoDAO.updateItemStartByAccount(ebayAccountModel.getAccount());
//					for(Integer i=1;i<=total;i++){
//						Integer k=i;
//					   cExecutorService.execute(new Runnable() {
//					   @Override
//					   public void run() {
//					   System.out.println("--------------------------------------------------"+k+"页-----------------------------------------------");
//					   List<PublicationModel> publicationModels = Lists.newArrayList();
//					   Map<String, String> tMap = new HashMap<>();
//					   tMap.put("name", "GetAllItem");
//					   tMap.put("type", "ebay");
//					   TemplateModel templateModel = templateDao.getTemplateContent(tMap);
//						String xml = templateModel.getContent();
//						BaseHttpsService sandHttp = new BaseHttpsService();
//						tMap.put("token", ebayAccountModel.getToken());
//						tMap.put("page", k.toString());
//						xml = TemplateService.formatTemplat(tMap,xml);
//						Map<String, String> headerMap = new HashMap<>();
//						headerMap.put("X-EBAY-API-SITEID", "0");
//						headerMap.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
//						headerMap.put("X-EBAY-API-CALL-NAME", "GetMyeBaySelling");
//						Document doc = sandHttp.getPesponseXml(templateModel.getUrl(), headerMap, xml);
//						Element root = doc.getRootElement();
//						Element activeList = root.element("ActiveList");
//						String account = ebayAccountModel.getAccount();
//						
//						publicationModels = getOnlineItem(activeList);
//						List<PublicationModel> insertItems = Lists.newArrayList();
//						List<PublicationModel> updateItems = Lists.newArrayList();
//						for (PublicationModel publicationModel : publicationModels) {
//							publicationModel.setEbayAccount(account);
//							if (null == pubInfoDAO.selectOnItemByID(publicationModel.getItemId())) {
//								insertItems.add(publicationModel);
//							}else{
//								updateItems.add(publicationModel);
//							}
//						}						
//							if(insertItems.size()>0){
//							pubInfoDAO.insertActiveItems(insertItems);
//							}
//						pubInfoDAO.updateActiveItems(updateItems);
//						
//						List<PublicationModel> updateItemLines = Lists.newArrayList();
//						//获取最新更新大约2两小时的产品更新
//						for (PublicationModel publicationModel : updateItems) {
//							 String itemId = publicationModel.getItemId();
//							if (null != pubInfoDAO.selectRecentlyItemByID(itemId)
//									|| null == pubInfoDAO.selectPublicByItemId(itemId)) {
//								updateItemLines.add(publicationModel);
//							}
//						}
//						updateItemLines.addAll(insertItems);
//						updatePublicationInfo(ebayAccountModel, updateItemLines);
//					   }
//					});
//					
//			}	
//			cExecutorService.shutdown();
//			while(true){
//				if(cExecutorService.isTerminated()){
//											break;
//				}
//				try {
//											Thread.sleep(2000);
//				} catch (InterruptedException e) {
//											logger.equals(e);
//				}
//			}	
//			
//		   //更新在本地数据在线的产品而在Ebay没有在线的产品信息
//			List<PublicationModel> publicationModels = pubInfoDAO.selectIsLineByAccount(ebayAccountModel.getAccount());
//			if(publicationModels.size()>0){
//				updatePublicationInfo(ebayAccountModel, publicationModels);
//			}
//		}
//
//		result.setDescription("操作成功！");
//		return result ;
//	}
//	public List<PublicationModel> getOnlineItem(Element activeList){
//		List<PublicationModel> publicationModels = Lists.newArrayList();
//		if(null!=activeList){
//			Element itemArray =activeList.element("ItemArray");
//			List<Element> items = itemArray.elements("Item");
//			for (Element item : items) {
//				PublicationModel publicationModel = new PublicationModel();
//				//super.insertInit(publicationModel);
//				Date date = new Date();
//				publicationModel.setCreationDate(date);
//				publicationModel.setLastUpdationDate(date);
//				publicationModel.setCreateBy(0l);
//				publicationModel.setItemId(item.elementText("ItemID"));
//				publicationModel.setSiteId(0l);
//				Element details =  item.element("ListingDetails");
//				if(null!=details){
//					publicationModel.setEbayProductURL(details.elementText("ViewItemURL"));
//				}
//				publicationModel.setProductTitle(item.elementText("Title"));
//				publicationModels.add(publicationModel);
//			}
//			
//			
//		}
//		return publicationModels;
//	}
//	public Integer getMyebaySell(EbayAccountModel ebayAccountModel){
//		Integer total = new Integer(0);
//		/*ebayAccountModel.setToken("AgAAAA**AQAAAA**aAAAAA**u1RjWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**Xq8DAA**AAMAAA**nzc7lCXNjRxjfZiTVjNJ6hYFYlZxK+5R6x7rr5WSGrTGJTjr3ykfT4YvBuhdDheZSpftIpfgfQMA//5Y1AhRHhckFpPnVU+7t2R7pViACV9wyiDGh2UjZ/XRuh1v5dr6zqUQN8AIo47gJE+AXeeehIC7G77E0DY2/0p+IxIWCtqP9MNjAfMIPWYsOhX4OnqMQgBIQdcMZZ5/3PoQRj/0ELiTn3RqltZq/T9BnyNv14qXBfXcUiijizx2a0OZBv1dfIrog7/ohP/sqBgvgyl31Ebi78Qp/TgIZtjP9r44tbw7kmsk/X1jtjeeXYfKZ+JelJlYZLZX4DTCCobYAZ7E5sbPI7frJFyRBsm7gxV5sZeBPdwDC5hB9pOTQpM27CdOsz2e089ughAEQw8H9ZV+DXUR4Aw3BNr40QwAkh34dBDF7UFo6JU8bWYTeweFT2vAdxUhvEfFjb5FRMcU5A9LK6SUkcoXP5f2DOD9nloZQOw6X6rczVsDeXd2dCPGTK+vpf82gjY+NWbVanPjICAEwFQZqPQe2OesAcbbK5ztYmeftzAvIEoWR71fHMrKDNrVeEFzzP9a9Pdl/C1UZ5Ys/xlLU07BnqL/idcx0+2WdcW3ZKpwMD7M6NobbqAuHBE7pvezSZ1DAVl+h5mrEURUQdOUMCRV5nKNkOYooeORqtKVclyNAOMPnvJwgn1sr/gdLuzoYtgsREiKBv1wJmni2YwcU80d+uLmCk41nqG3n8unVN0CURR+8g5BGSNFVvl1");
//		ebayAccountModel.setAccount("uk.le");
//		ebayAccountModel.setId(2l);*/
//				List<PublicationModel> publicationModels = Lists.newArrayList();
//				Map<String, String> tMap = new HashMap<>();
//				tMap.put("name", "GetAllItem");
//				tMap.put("type", "ebay");
//				TemplateModel templateModel = templateDao.getTemplateContent(tMap);
//				String xml = templateModel.getContent();
//				BaseHttpsService sandHttp = new BaseHttpsService();
//				tMap.put("token", ebayAccountModel.getToken());
//				tMap.put("page", "1");
//				xml = TemplateService.formatTemplat(tMap,xml);
//				Map<String, String> headerMap = new HashMap<>();
//				headerMap.put("X-EBAY-API-SITEID", "0");
//				headerMap.put("X-EBAY-API-COMPATIBILITY-LEVEL", "967");
//				headerMap.put("X-EBAY-API-CALL-NAME", "GetMyeBaySelling");
//				Document doc = sandHttp.getPesponseXml(templateModel.getUrl(), headerMap, xml);
//				Element root = doc.getRootElement();
//				Element activeList = root.element("ActiveList");
//				if (null==activeList) {
//					return 0;
//				}
//				if(activeList.element("PaginationResult")==null){
//					total=1;
//				}else {
//					String totalPages = activeList.element("PaginationResult").elementText("TotalNumberOfPages");
//					total = Integer.parseInt(totalPages);
//				}
//		return total;
//	}
//	public   OperationResult updatePublicationInfo(EbayAccountModel ebayAccountModel ,List<PublicationModel> publicationModels){
//		OperationResult result = new OperationResult();
//		StringBuffer itemStr = new StringBuffer();
//		for (PublicationModel publicationModel : publicationModels) {
//			Document doc = ieBayItemService.getItemInfoByIds(ebayAccountModel, publicationModel);
//			Element root = doc.getRootElement();
//			Element ack = root.element("Ack");
//			if(!ack.getTextTrim().equals("Success")){
//				itemStr.append(publicationModel.getItemId()+"\n"+root.elementText("Errors")+"<br>");
//			}else{
//				PublicationModel model =  ieBayItemService.parseItemXml(doc);
//				
//				synchronized (model) {
//					    
//					    PublicationModel publicationModel2 = pubInfoDAO.selectPublicByItemId(model.getItemId());
//						if(null==publicationModel2){
//							Long puId = pubInfoDAO.getId();
//							model.setId(puId);
//							pubInfoDAO.insertPublicationLine(model);
//						}else{
//							model.setId(publicationModel2.getId());
//						}
//						updatePubPiecewise(model);
//						pubInfoDAO.deleteTransInfoByTID(model.getId());
//						List<ShipOptionModel> shipOptionModels = model.getShipOptionModels();
//						for (ShipOptionModel shipOptionModel : shipOptionModels) {
//							shipOptionModel.setTempId(model.getId());
//							pubInfoDAO.addPublicationTransInfo(shipOptionModel);
//						}
//				}
//			}
//		}
//		if(itemStr.length()>8){
//			result.setDescription(itemStr.toString());
//		}else{
//			result.setDescription("操作成功");
//		}
//		
//		return result;
//	}
//	@Override
//	public OperationResult insertActiveItems(List<PublicationModel> publicationModels) {
//		OperationResult result = new OperationResult();
//		pubInfoDAO.insertActiveItems(publicationModels);
//		result.setErrorCode(1);
//		return result;
//	}
//	@Override
//	public OperationResult updateActiveItems(List<PublicationModel> publicationModels) {
//		OperationResult result = new OperationResult();
//		pubInfoDAO.updateActiveItems(publicationModels);
//		result.setErrorCode(1);
//		return result;
//	}
//	public OperationResult updatePubPiecewise(PublicationModel publicationModel){
//		OperationResult result = new OperationResult();
//		pubInfoDAO.updateLinePuByOnline(publicationModel);
//		if(!ValidationUtil.isNullOrEmpty(publicationModel.getComments())){
//		pubInfoDAO.updateLinePuByOnline2(publicationModel);
//		}
//		pubInfoDAO.updateLinePuByOnline3(publicationModel);
//		pubInfoDAO.updateLinePuByOnline4(publicationModel);
//		result.setDescription("操作成功！");
//		return result;
//	}
//	@Override
//	public GridResult<Map<String, Object>> aLotLineModifyList(RequestParam param) {
//		StringBuffer sqlCondition = new StringBuffer();
//		Map<String,Object> map = param.getParam();
//		boolean isHasTran = false;
//		for(Map.Entry<String, Object> entry : map.entrySet()){
//			String value = String.valueOf(entry.getValue());
//			String key = entry.getKey();
//			if(key.startsWith("1_")||key.startsWith("0_")){
//				isHasTran = true;
//				continue;
//			}
//			if(null !=value && !"".equals(value)){
//				if("TEMPLATE_NAME".equals(key)||"PRODUCT_TITLE".equals(key)){
//					sqlCondition.append(" AND "+entry.getKey() + " like '%"+value+"%' ");
//				}else{
//					sqlCondition.append(" AND "+entry.getKey() + "='"+value+"' ");
//				}
//				
//			}
//		}
//		map.put("CONDITION", sqlCondition.toString());
//		List<Map<String,Object>> list = pubInfoDAO.getLineALotModifyList(map,param.getStartRow(),param.getEndRow());
//		List<Map<String,Object>> rows = new ArrayList<>();
//		for(Map<String,Object> publication : list){
//			Map<String,Object> row = new HashMap<>();
//			//查询关联的运输信息
//			List<Map<String,Object>> trans = pubInfoDAO.getTransInfoById(String.valueOf(publication.get("TEMPLATE_ID")));
//			Map<String,Object> dom = null;
//			Map<String,Object> cu = null;
//			if(null != trans){
//				for(Map<String,Object> tran : trans){
//					if("0".equals(String.valueOf(tran.get("TRAN_KIND")))){
//						cu = tran;
//					}else{
//						dom = tran;
//					}
//				}
//			}
//			
//			for(Map.Entry<String, Object> entry : map.entrySet()){
//				String key = entry.getKey();
//				if(key.startsWith("1_")){
//					if(null != dom){
//						row.put(key, dom.get(key.substring(2)));
//					}else{
//						row.put(key, "");
//					}
//				}else if(key.startsWith("0_")){
//					if(null != cu){
//						row.put(key, cu.get(key.substring(2)));
//					}else{
//						row.put(key, "");
//					}
//				}else if("_buyer_require".endsWith(key)){
//					row.put("ALLOW_ALLBUYER",publication.get("ALLOW_ALLBUYER"));
//					row.put("NO_PAYPAL",publication.get("NO_PAYPAL"));
//					row.put("OUT_SHIP_COUNTRY",publication.get("OUT_SHIP_COUNTRY"));
//					row.put("BUYER_REQ_1",publication.get("BUYER_REQ_1"));
//					row.put("BUYER_REQ_2",publication.get("BUYER_REQ_2"));
//					row.put("BUYER_REQ_3",publication.get("BUYER_REQ_3"));
//					row.put("BUYER_REQ_4",publication.get("BUYER_REQ_4"));
//					row.put("BUYER_REQ_4_1",publication.get("BUYER_REQ_4_1"));
//				}else{
//					row.put(key, publication.get(key));
//				}
//			}
//			row.put("TEMPLATE_ID", publication.get("TEMPLATE_ID"));
//			rows.add(row);
//		}
//		GridResult<Map<String, Object>> re = new GridResult<Map<String, Object>>();
//		re.setRows(rows);
//		int count = pubInfoDAO.countLALotModifyList(map);
//		re.setTotal(count);
//		return re;
//	}
//	@Override
//	public OperationResult cancelTimingPlan(String id) {
//		OperationResult result = new OperationResult();
//		try {
//			publicationRelationDAO.cancelTimingPlan(id);
//			result.setDescription("取消成功！");
//		} catch (Exception e) {
//			logger.error("取消定时刊登失败", e);
//			result.setDescription("取消失败！");
//		}
//		return result;
//	}
//	@Override
//	public OperationResult aLotModifySaveNew(Map<String, Object> map) {
//		String ids = (String)map.get("ids");
//		String tempId[] = ids.split(",");
//		String type = (String)map.get("type");
//		boolean tranUpdateflag = false;
//		List<Map> domesticTrans = null;
//		if(null != map.get("domesticTrans")){
//			domesticTrans = (List<Map>)map.get("domesticTrans");
//			tranUpdateflag = true;
//		}
//		List<Map> calCulateTrans = null;
//		if(null != map.get("calCulateTrans")){
//			calCulateTrans = (List<Map>)map.get("calCulateTrans");
//			tranUpdateflag = true;
//		}
//		
//		//更新数据MAP
//		Map<String,Object> updateMap = new HashMap<>();
//		//组合更新字段和失败后回退数据key
//		UserModel user =  getCurentUser();
//		StringBuffer failRollBackKey = new StringBuffer(" to_char(LAST_UPDATE_DATE,'yyyy-MM-dd hh24:mi:ss') LAST_UPDATE_DATE,LAST_UPDATE_BY");
//		StringBuffer sb = new StringBuffer(" LAST_UPDATE_DATE = sysdate , LAST_UPDATE_BY = "+ user.getId());
//		for(Map.Entry<String, Object> entry : map.entrySet()){
//			String key = entry.getKey();
//			if(!"domesticTrans".equals(key)&&!"calCulateTrans".equals(key)&&!"ids".equals(key)&&!"type".equals(key)){
//				sb.append(" ,"+key +"='"+entry.getValue()+"' ");
//				failRollBackKey.append(","+key);
//			}
//		}
//		updateMap.put("updateSet", sb.toString());
//		updateMap.put("oldKeys", failRollBackKey.toString());
//		Map<String,String> resultMap = new HashMap<>();
//		for(int i=0;i<tempId.length;i++){
//			updateMap.put("TEMPLATE_ID", tempId[i]);
//			//更新运输方式前查询老数据,失败后待回退更新
//			List<Map<String,Object>> domesticTransOld = null;
//			if(tranUpdateflag){
//				domesticTransOld = pubInfoDAO.getTransInfoById(tempId[i]);
//				//更新运输方式
//				updatePublicationTransInfo(domesticTrans, tempId[i],0);
//				updatePublicationTransInfo(calCulateTrans, tempId[i],1);
//			}
//			if("line".equals(type)){
//				//更新先备份刊登老数据，失败后待回退信息
//				Map<String,Object> oldPublicationData = pubInfoDAO.getPulicationLineOldData(updateMap);
//				//更新数据
//				pubInfoDAO.updatePulicationLineByALot(updateMap);
//				//线上数据更新线上范本
//				PublicationModel publicationModel = pubInfoDAO.getLineById(Long.parseLong(tempId[i]));
//				OperationResult operationResult = ieBayItemService.updateLineItem(new PublicationAddItemModel(publicationModel));
//				String ack = operationResult.getData().toString();
//				if(!ack.equalsIgnoreCase("Warning")&&!ack.equalsIgnoreCase("Success")){
//					//回退数据
//					rollBackTranData(domesticTransOld,tempId[i]);
//					rollBackPublicationLineData(oldPublicationData,tempId[i]);
//					resultMap.put(tempId[i], "<span style='color:red;'>失败！</span>"+operationResult.getDescription());
//				}else{
//					resultMap.put(tempId[i], "<span style='color:green;'>成功！</span>"+operationResult.getDescription());
//				}
//				
//			}else{
//				pubInfoDAO.updatePulicationByALot(updateMap);
//			}
//		}
//		OperationResult or = new OperationResult();
//		if(resultMap.isEmpty()){
//			or.setData("success");
//		}else{
//			or.setData(resultMap);
//		}
//		return or;
//	}
//	/**
//	 * 更新到相关联的范本
//	 */
//	@Override
//	public OperationResult aLotModifySaveToPublication(Map<String, Object> map) {
//		String ids = (String)map.get("ids");
//		String tempId[] = ids.split(",");
//		//查询相关联的范本id
//		String temIds = this.pubInfoDAO.getLinkTemplateIdByLineId(tempId);
//		if(null != temIds && !"".equals(temIds)){
//			map.put("ids", temIds);
//			map.put("type", "template");
//			return aLotModifySaveNew(map);
//		}else{
//			OperationResult or = new OperationResult();
//			or.setData("没有找到相关联的范本！");
//			return or;
//		}
//	}
//	
//	/**
//	 * 回滚批量更新刊登信息
//	 * 
//	 * @param oldPublicationData
//	 * @param string
//	 */
//	private void rollBackPublicationLineData(Map<String, Object> oldPublicationData, String tempId) {
//		StringBuffer sb = new StringBuffer();
//		for(Map.Entry<String, Object> entry : oldPublicationData.entrySet()){
//			String key = entry.getKey();
//			if(!"".equals(sb.toString())){
//				sb.append(" ,");
//			}
//			if("LAST_UPDATE_DATE".equals(key)){
//				sb.append(key +"=to_date('"+entry.getValue()+"','yyyy-MM-dd hh24:mi:ss') ");
//			}else{
//				sb.append(key +"='"+entry.getValue()+"' ");
//			}
//		}
//		oldPublicationData.put("updateSet", sb.toString());
//		oldPublicationData.put("TEMPLATE_ID", tempId);
//		pubInfoDAO.updatePulicationLineByALot(oldPublicationData);
//	}
//	/**
//	 * 回滚批量更新运输信息
//	 * @param domesticTransOld
//	 * @param tempId
//	 */
//	private void rollBackTranData(List<Map<String, Object>> domesticTransOld, String tempId) {
//		if(null != domesticTransOld){
//			//清除数据
//			pubInfoDAO.deletePublicationTranInfo(tempId);
//			pubInfoDAO.rollBackTranData(domesticTransOld);
//		}
//	}
//	@Override
//	public List<ItemLabelVO> getItemLabelByState(String labelState) {
//		List<ItemLabelVO> itemLabelVOs = Lists.newArrayList();
//		List<ItemLabelModel> itemLabelModels =  pubInfoDAO.getItemLabelByState(labelState);
//		ItemLabelVO itemLabelVO = new ItemLabelVO();
//		itemLabelVO.setId(1l);
//		itemLabelVO.setLabelName("编辑");
//		itemLabelVOs.add(itemLabelVO);
//		for (ItemLabelModel itemLabelModel : itemLabelModels) {
//			itemLabelVO = new ItemLabelVO();
//			BeanUtils.copyProperties(itemLabelModel, itemLabelVO);
//			itemLabelVOs.add(itemLabelVO);
//		}
//		return itemLabelVOs;
//	}
//	@Override
//	public ResponseResult<ItemLabelVO> getItemLabelPage(RequestParam param) {
//		ResponseResult<ItemLabelVO> result = new ResponseResult<>();
//		List<ItemLabelVO> itemLabelVOs = Lists.newArrayList();
//		Map<String, Object> map = param.getParam();
//		List<ItemLabelModel> itemLabelModels = pubInfoDAO.getItemLabelPage(map, param.getStartRow(), param.getEndRow());
//		for (ItemLabelModel itemLabelModel : itemLabelModels) {
//			ItemLabelVO itemLabelVO = new ItemLabelVO();
//			BeanUtils.copyProperties(itemLabelModel, itemLabelVO);
//			itemLabelVOs.add(itemLabelVO);
//		}
//		Integer total = pubInfoDAO.getLabelTotal(map);
//		result.setRows(itemLabelVOs);
//		result.setTotal(total);
//		return result;
//	}
//	@Override
//	public OperationResult labelSave(ItemLabelVO itemLabelVO) {
//		
//		OperationResult result = new OperationResult();
//		List<ItemLabelModel> labelModels = Lists.newArrayList();
//		ItemLabelModel itemLabelModel = new ItemLabelVO();
//		itemLabelModel=itemLabelVO;
//		labelModels.add(itemLabelModel);
//		if(ValidationUtil.isNull(itemLabelVO.getId())){
//			pubInfoDAO.insertItemLabels(labelModels); 
//		}else{
//			pubInfoDAO.updateItemLabels(labelModels);
//		}
//		result.setData(1);
//		result.setDescription("操作成功！");
//		return result;
//	}
//	@Override
//	public OperationResult insertItemLabel(List<ItemLabelVO> itemLabelVOs) {
//            OperationResult result = new OperationResult();
//        	List<ItemLabelModel> itemLabelModels = Lists.newArrayList();
//        	for (ItemLabelVO itemLabelVO : itemLabelVOs) {
//        		ItemLabelModel itemLabelModel = new ItemLabelVO();
//        		itemLabelModel =itemLabelVO;
//        		itemLabelModels.add(itemLabelModel);
//            	
//			}
//        	pubInfoDAO.insertItemLabels(itemLabelModels);
//        	result.setData("1");
//		    return result;
//	}
//	@Override
//	public OperationResult updateItemLabel(List<ItemLabelVO> itemLabelVOs) {
//
//		OperationResult result = new OperationResult();
//     	List<ItemLabelModel> itemLabelModels = Lists.newArrayList();
//     	for (ItemLabelVO itemLabelVO : itemLabelVOs) {
//     		ItemLabelModel itemLabelModel = new ItemLabelVO();
//     		itemLabelModel =itemLabelVO;
//     		itemLabelModels.add(itemLabelModel);
//         	
//			}
//     	pubInfoDAO.updateItemLabels(itemLabelModels);
//     	result.setData("1");
//		return result;
//	}
//	@Override
//	public OperationResult labelRemove(Long id) {
//		OperationResult result  = new OperationResult();
//		pubInfoDAO.labelRemove(id);
//		result.setDescription("操作成功！");
//		return result;
//	}
//	@Override
//	public OperationResult relevancyItemLable(String ids, Long id) {
//		OperationResult result = new OperationResult();
//		List<LabelMiddleModel> labelMiddleModels = Lists.newArrayList();
//		if(!ValidationUtil.isNullOrEmpty(ids)){
//			String[] templateId =  ids.split(",");
//			for (String string : templateId) {
//				LabelMiddleModel labelMiddleModel = new LabelMiddleModel();
//				labelMiddleModel.setLebelId(id);
//				labelMiddleModel.setTemplateId(Long.parseLong(string));
//				labelMiddleModels.add(labelMiddleModel);
//			}
//		}
//		result = insertLableMiddles(labelMiddleModels);
//		result.setDescription("添加成功！");
//		return result;
//	}
//	@Override
//	public OperationResult insertLableMiddles(List<LabelMiddleModel> labelMiddleModels) {
//		OperationResult result = new OperationResult();
//     	pubInfoDAO.insertLableMiddles(labelMiddleModels);
//     	result.setData("1");
//		return result;
//	}
//	@Override
//	public void updatePublicationDate() {
//		List<PublicationModel> publicationModels =  pubInfoDAO.selectOnlineDate();
//		for (PublicationModel publicationModel : publicationModels) {
//		try {
//			Document document = 	DocumentHelper.parseText(publicationModel.getComments());
//			Element root = document.getRootElement();
//			Element item = root.element("Item");
//			Element listDs = item.element("ListingDetails");
//			Element start = listDs.element("StartTime");
//			Element end = listDs.element("EndTime");
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//			Date startD = ConversionDateUtil.charToDate(start.getTextTrim(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//			calendar.setTime(startD);
//			Calendar calendar2 = Calendar.getInstance();
//			calendar2.setTimeZone(TimeZone.getTimeZone("UTC"));
//			Date startE = ConversionDateUtil.charToDate(end.getTextTrim(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//			calendar2.setTime(startE);
//		    publicationModel.setPublication_date(ConversionDateUtil.getDateByCZ(calendar, "GMT+8"));
//			publicationModel.setEnd_publication_date(ConversionDateUtil.getDateByCZ(calendar2, "GMT+8"));
//			pubInfoDAO.updatePublicationDate(publicationModel);
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//		}
//		
//		
//	}
//	@Override
//	public List<ComboBoxVO> getModelTempData(String siteId,int type) {
//		Map<String,Object> map = new HashMap<>();
//		if(null == siteId || "all".equals(siteId)){
//			map.put("siteId", "");
//		}else{
//			map.put("siteId", siteId);
//		}
//		map.put("type", type);
//		return pubInfoDAO.getModelTempData(map);
//	}
//	@Override
//	public OperationResult getModeSet(int id) {
//		OperationResult or = new OperationResult();
//		or.setData(pubInfoDAO.getModeSet(id));
//		return or;
//	}
//	
//	/**
//	 * 获取所有在线刊登
//	 */
//	public void syncOnlineItemsOfAll(){
//		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
//		//String timeFrom = UTCTimeUtils.getUTCTimeStr(- 24);
//		for(EbayAccountModel account:accounts){
//			//account.setAccount("uk.nm");
//			//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
//			account.setSiteId("201");
//			createThreadSyncOnlineItemOfAllByAccount(account);
//			//break;
//		}
//	}
//	
//	private void createThreadSyncOnlineItemOfAllByAccount(EbayAccountModel account) {
//		TaskExecutorUtil.threadRun(new TaskRunnable() {
//
//			@Override
//			protected void runTask() {
//				Map<String,String> xmlKeyValues = new HashMap<>();
//				xmlKeyValues.put("page", "1");
//				// 获取请求模板
//				TemplateModel xml = templateService.getTemplateContent("GetAllItem", "ebay");
//				RequestModel request = new RequestModel(xml,account,xmlKeyValues);
//				Document doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//				ParseOnlineItemOfAllXMLModel parse = new ParseOnlineItemOfAllXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//				addOrUpdateItem(parse.getResult());
//				int totalPages = parse.getTotalPages();
//				for(int i = 2; i <= totalPages;i++){
//					xmlKeyValues.put("page", String.valueOf(i));
//					request.setParam(xmlKeyValues);
//					doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//					ParseOnlineItemOfAllXMLModel parse1 = new ParseOnlineItemOfAllXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//					addOrUpdateItem(parse1.getResult());
//				}
//				
//			}
//
//			private void addOrUpdateItem(List<Map<String, Object>> result) {
//				for(Map<String, Object> map : result){
//					String itemId = map.get("ItemID").toString();
//					if(pubInfoDAO.itemIsExist(itemId) == 0){
//						addItem(account, itemId);
//					}
//					//更新数量信息等
//					int quantity = 0;
//					int quantityAvailable = paseInt(map.get("QuantityAvailable").toString());
//					String type = map.get("ListingType").toString();
//					if("Chinese".equals(type)){
//						quantity = 1;
//					}else{
//						quantity = paseInt(map.get("Quantity").toString());
//						//临时代码，补全之前多属性刊登信息
//						if(pubInfoDAO.variationHasError(itemId) > 0){
//							addItem(account, itemId);
//						}
//						
//					}
//					int sold = quantity - quantityAvailable;
//					map.put("Quantity_sold", sold);
//					//多属性信息更新
//					
//					Object obj = map.get("variations");
//					if(null != obj){
//						List<Map<String,Object>> variations = (List<Map<String,Object>>)obj;
//						String variationsStr = pubInfoDAO.getVariationByItemId(itemId);
//						JSONObject variationJson = JSONObject.fromObject(variationsStr);
//						JSONArray array = variationJson.getJSONArray("variations");
//						for(int i = 0;i<array.size();i++){
//							JSONObject json = array.getJSONObject(i);
//							String sku = json.getString("SKU");
//							String jsonStr = json.toString();
//							for(Map<String,Object> m : variations){
//								if(!sku.equals(m.get("SKU").toString())){
//									continue;
//								}
//								boolean isThis = true;
//								List<String> values = (List<String>)m.get("values");
//								for(String value : values){
//									if(jsonStr.indexOf("\"Value\":\""+value+"\"") == -1){
//										isThis = false;
//										break;
//									}
//								}
//								//匹配到信息，更新信息并进行匹配下一个属性
//								if(isThis){
//									String vaQuantity = m.get("Quantity").toString();
//									if("".equals(vaQuantity)){
//										json.put("Quantity", "0");
//									}else{
//										int qty =paseInt(m.get("Quantity").toString()) - paseInt(m.get("QuantitySold").toString());
//										json.put("Quantity", String.valueOf(qty));
//									}
//									array.set(i, json);
//									break;
//								}
//							}
//						}
//						variationJson.put("variations", array);
//						map.put("variations", variationJson.toString());
//					}
//					
//					pubInfoDAO.updateItemByOnline(map);
//					
//				}
//			}
//			
//			private void addItem(EbayAccountModel account, String itemId) {
//				//TODO 暂时使用yangguanbao代码，需优化修改
//				List<PublicationModel> pus = new ArrayList<>();
//				PublicationModel mode = new PublicationModel();
//				mode.setItemId(itemId);
//				mode.setSiteId(0L);
//				pus.add(mode);
//				updatePublicationInfo(account,pus);
//			}
//			
//		});
//	}
//	/**
//	 * 根据修改时间同步item
//	 */
//	public void syncItemSellInfoOnStartTime(){
//		//TemplateModel xml = templateService.getTemplateContent("GetOrdersRepair", "ebay");
//		syncItemByGetSellerEvents(3);
//	}
//	
//	/**
//	 * 根据修改时间同步item
//	 */
//	public void syncItemSellInfoOnEndTime(){
//		//TemplateModel xml = templateService.getTemplateContent("GetOrdersRepair", "ebay");
//		syncItemByGetSellerEvents(2);
//	}
//	
//	/**
//	 * 根据修改时间同步item
//	 */
//	public void syncItemSellInfoOnModifyTime(){
//		//TemplateModel xml = templateService.getTemplateContent("GetOrdersRepair", "ebay");
//		syncItemByGetSellerEvents(1);
//	}
//	
//	
//	public void syncItemByGetSellerEvents(int type){
//		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
//		//String timeFrom = UTCTimeUtils.getUTCTimeStr(- 24);
//		for(EbayAccountModel account:accounts){
//			//account.setAccount("uk.nm");
//			//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
//			createThreadSyncItemByAccount(account,type);
//			//break;
//		}
//	}
//	private void createThreadSyncItemByAccount(EbayAccountModel account,int type) {
//		TaskExecutorUtil.threadRun(new TaskRunnable() {
//			@Override
//			protected void runTask() {
//				account.setSiteId("201");
//				//设置模板
//				TemplateModel xml = null;
//				//按照修改时间
//				if(type == 1){
//					syncByModifyTime(account);
//				//按照刊登结束时间
//				}else if(type == 2){
//					syncByEndTime(account);
//				//按照刊登起始时间
//				}else if(type == 3){
//					syncByStartTime(account);
//				//手动按照刊登修改时间
//				}else if(type == 4){
//					syncByModifyTimeOfPage(account);
//				}
//			}
//			
//			/**
//			 * 根据刊登结束时间同步item
//			 * @param account
//			 */
//			public void syncByEndTime(EbayAccountModel account){
//				Map<String, String> map = new HashMap<>();
//				map.put("fromTime", UTCTimeUtils.getUTCTimeStr(-2));
//				map.put("toTime", UTCTimeUtils.getUTCTimeStrByMINUTE(-2));
//				TemplateModel template = templateService.getTemplateContent("GetSellerEventsByEndTime", "ebay"); 
//				RequestModel request = new RequestModel(template,account,map);
//				Document doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//				ParseItemSellXMLModel parse = new ParseItemSellXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//				updateData(parse.getResult(),account);
//			}
//			/**
//			 * 根据刊登起始时间同步item
//			 * @param account
//			 */
//			public void syncByStartTime(EbayAccountModel account){
//				//默认开始时间
//				Calendar curCalendar = Calendar.getInstance();
//				curCalendar.add(Calendar.HOUR, -24*7);
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//				String fromTime = sdf.format(curCalendar.getTime());
//				
//				//判断时间间隔长度，最大不能超过48小时，
//				List<Map<String,String>> timeList = UTCTimeUtils.splitTimeForOneHourList(fromTime, 24);
//				for(Map<String,String> map : timeList){
//					TemplateModel template = templateService.getTemplateContent("GetSellerEventsByStartTime", "ebay"); 
//					RequestModel request = new RequestModel(template,account,map);
//					Document doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//					ParseItemSellXMLModel parse = new ParseItemSellXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//					updateData(parse.getResult(),account);
//
//				}
//			}
//			/**
//			 * 判断不存在，新增item
//			 * @param items
//			 * @param account
//			 */
//			public void updateStartData(List<Map<String, Object>> items,EbayAccountModel account){
//				for(Map<String, Object> item : items){
//					String itemId = item.get("ItemID").toString();
//					//item 不存在 同步 item的信息
//					if(pubInfoDAO.itemIsExist(itemId) == 0){
//						addItem(account, itemId);
//					}
//				}
//			}
//			/**
//			 * 获取item信息
//			 * @param account
//			 * @param itemId
//			 */
//			private void addItem(EbayAccountModel account, String itemId) {
//				//TODO 暂时使用yangguanbao代码，需优化修改
//				List<PublicationModel> pus = new ArrayList<>();
//				PublicationModel mode = new PublicationModel();
//				mode.setItemId(itemId);
//				mode.setSiteId(0L);
//				pus.add(mode);
//				updatePublicationInfo(account,pus);
//			}
//			
//			public void syncByModifyTimeOfPage(EbayAccountModel account){
//				 Map<String, String> map = new HashMap<>();
//				 map.put("fromTime", UTCTimeUtils.getUTCTimeStr(-24));
//				 map.put("toTime", UTCTimeUtils.getUTCTimeStrByMINUTE(-5));
//				 syncData(account,map);
//			}
//			
//			/**
//			 * 根据item修改时间同步相关信息
//			 * @param account
//			 */
//			public void syncByModifyTime(EbayAccountModel account){
//				//获取时间
//				Map<String,Object> timeSetMap = new HashMap<>();
//				timeSetMap.put("account", account.getAccount());
//				timeSetMap.put("type", type);
//				String fromTime = pubInfoDAO.getLastEndTime(timeSetMap);
//				//插入执行时间记录
//				timeSetMap.put("fromTime", fromTime);
//				int id = pubInfoDAO.getTimeSetId();
//				timeSetMap.put("id", id);
//				pubInfoDAO.addTimeSet(timeSetMap);
//				//判断时间间隔长度，最大不能超过48小时，
//				List<Map<String,String>> timeList = UTCTimeUtils.splitTimeForOneHourList(fromTime, 24);
//				for(Map<String,String> map : timeList){
//					syncData(account, map);
//					String toTime =  map.get("toTime");
//					toTime = toTime.replace("T", " ");
//					toTime = toTime.replace("Z", "");
//					timeSetMap.put("toTime",toTime);
//					pubInfoDAO.updateTimeSet(timeSetMap);
//				}
//			}
//			private void syncData(EbayAccountModel account, Map<String, String> map) {
//				TemplateModel template = templateService.getTemplateContent("GetSellerEventsByModifyTime", "ebay"); 
//				RequestModel request = new RequestModel(template,account,map);
//				Document doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//				ParseItemSellXMLModel parse = new ParseItemSellXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//				updateData(parse.getResult(),account);
//			}
//			/**
//			 * 保存item相关的信息
//			 * @param items
//			 * @param account
//			 */
//			public void updateData(List<Map<String, Object>> items,EbayAccountModel account){
//				for(Map<String, Object> item : items){
//					String itemId = item.get("ItemID").toString();
//					//item 不存在 同步 item的信息
//					if(pubInfoDAO.itemIsExist(itemId) == 0){
//						addItem(account, itemId);
//					}
//					
//					Object qtyObj = item.get("Quantity");
//					Object soldQtyObj = item.get("SellingStatus_QuantitySold");
//					String pubType = item.get("ListingType").toString();
//					int qty = 0;
//					int soldQty = 0;
//					if(null != qtyObj&&!"".equals(qtyObj.toString())){
//						qty = Integer.parseInt(qtyObj.toString());
//						if(null != soldQtyObj&&!"".equals(soldQtyObj.toString())){
//							soldQty = Integer.parseInt(soldQtyObj.toString());
//							item.put("leaveQty", qty-soldQty);
//						}else{
//							item.put("leaveQty", qty);
//							item.put("SellingStatus_QuantitySold", 0);
//						}
//					}else{
//						if("Chinese".equals(pubType)){
//							item.put("Quantity", 1);
//							if(null != soldQtyObj&&!"".equals(soldQtyObj.toString())){
//								item.put("leaveQty", 1);
//							}else{
//								item.put("leaveQty", 0);
//								item.put("SellingStatus_QuantitySold", 0);
//							}
//						}else{
//							item.put("leaveQty", 0);
//						}
//						
//					}
//					Object obj = item.get("variations");
//					if(null != obj){
//						List<Map<String,Object>> variations = (List<Map<String,Object>>)obj;
//						String variationsStr = pubInfoDAO.getVariationByItemId(itemId);
//						JSONObject variationJson = JSONObject.fromObject(variationsStr);
//						JSONArray array = variationJson.getJSONArray("variations");
//						for(int i = 0;i<array.size();i++){
//							JSONObject json = array.getJSONObject(i);
//							String sku = json.getString("SKU");
//							String jsonStr = json.toString();
//							for(Map<String,Object> m : variations){
//								if(!sku.equals(m.get("SKU").toString())){
//									continue;
//								}
//								boolean isThis = true;
//								List<String> values = (List<String>)m.get("values");
//								for(String value : values){
//									if(jsonStr.indexOf("\"Value\":\""+value+"\"") == -1){
//										isThis = false;
//										break;
//									}
//								}
//								//匹配到信息，更新信息并进行匹配下一个属性
//								if(isThis){
//									String vaQuantity = m.get("Quantity").toString();
//									if("".equals(vaQuantity)){
//										json.put("Quantity", "0");
//									}else{
//										int qty1 =paseInt(m.get("Quantity").toString()) - paseInt(m.get("QuantitySold").toString());
//										json.put("Quantity", String.valueOf(qty1));
//									}
//									array.set(i, json);
//									break;
//								}
//							}
//						}
//						variationJson.put("variations", array);
//						item.put("variations", variationJson.toString());
//					}
//					//更新信息
//					pubInfoDAO.updateItemToLineData(item);
//				}
//			}
//			
//		});
//	}
//    
//	
//	public static int paseInt(String str){
//		int a = 0;
//		if(null != str && !"".equals(str)){
//			a = Integer.parseInt(str);
//		}
//		return a;
//	}
//	@Override
//	public OperationResult syncStock() {
//		//syncItemByGetSellerEvents(4);
//		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
//		final CountDownLatch countDownLatch=new CountDownLatch(accounts.size());
//		for(EbayAccountModel account:accounts){
//			account.setSiteId("201");
//			//account.setAccount("uk.nm");
//			//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
//			createThreadSyncStock(account,countDownLatch);
//		}
//		try {
//			countDownLatch.await();
//		} catch (InterruptedException e) {
//			logger.error("---页面点击同步库存主线程等待子线程结束失败---");
//		}
//		OperationResult or = new OperationResult();
//		or.setData("success");
//		return or;
//	}
//	private void createThreadSyncStock(EbayAccountModel account,CountDownLatch countDownLatch) {
//		TaskExecutorUtil.threadRun(new TaskRunnable() {
//			@Override
//			protected void runTask() {
//				getSellerList(account);
//				countDownLatch.countDown();
//			}
//		});
//	}
//	/**
//	 * 根据GetSellerList接口同步信息
//	 * 此接口可以自定义返回信息
//	 */
//	public void syncSellerListInfo(){
//		List<EbayAccountModel> accounts = ebayAccountService.getAccounts();
//		for(EbayAccountModel account:accounts){
//			//account.setAccount("uk.nm");
//			//account.setToken("AgAAAA**AQAAAA**aAAAAA**3vplWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGl4SpD5eFpwydj6x9nY+seQ**Xq8DAA**AAMAAA**iydAm7Kq16duV/Musk4kXJRQmu77ZJqV7oQ+dPkGGilARK6NLpQuu9x+kS+2j7CcOVtGZfPg4jla4w+JwBnrr1vVtzQeV7/VwKuynHuEK4nqhWPi/JZhZ6zSNM+NbbIbI/a3TgvFkP5fyS2YSIALc6OCXH46jiQ77l+Qf7q2AkR8feOgTjlXRybuvDPXEk0MjfuAxb+wryC4JqiRkd6Y7gPHUAllXUBqI8JqL0RlD+Ewr3Hvsl6S892wnqSzlEkmrZm9YcO5hOco8xoMUDovZfXMAzcl56WlE+yZ6ghlJXTmX6BLXmj8VXSWGguG62ICT2waID1eM+aazxoRFgvqhua+tPKTJysql2TDrj/Rh0kT7O4VS+dVxrfHpzeovv+9O1DYyhSrOJfmi/gzvxAqTQt9hqpSe04omCXT4I04IUk+ZKPt0fYmFxH3T2bkscBUyXsxdbakRVWYGCr7jA4iegCSXUMbj4KQ1BRuVVRvAX2a63qcIfMWu9nB9IC4PdSogI3dccd1N6VmLPW/AlQSsGoPTtM2t4L8X6Gu1oitB6mkhvBWQ3CYfz8AjuV1CT2P/VJnSDplkqrYxxN7JeZL1/xeIETcBJ3KxWbhFEta4ERue9EYmBZtHCJ3BEmURV65kPpVfp8AZSGAGiXzOFGT2aYPLKUOtVe/qs9zte2GYSCdmvZ0tXIpZmMDY+zwSxClZ0WP+itTKzkXnrJSuhOGPCo3yITjpj7uO1r/0uJKOLwZ2MmIKH+yIhB3dYDg56MI");
//			account.setSiteId("201");
//			createThreadSyncSellerListInfo(account);
//			//break;
//		}
//	}
//	private void createThreadSyncSellerListInfo(EbayAccountModel account) {
//		TaskExecutorUtil.threadRun(new TaskRunnable() {
//			@Override
//			protected void runTask() {
//				getSellerList(account);
//			}
//		});
//	}
//	/**
//	 * GTC刊登类型默认是30天，默认根据刊登结束时间同步
//	 * 时间范围：当前时间之前48小时到之后30天
//	 */
//	public void getSellerList(EbayAccountModel account) {
//		Map<String,String> timeMap = new HashMap<>();
//		timeMap.put("fromTime", UTCTimeUtils.getUTCTimeStr(-48));
//		timeMap.put("toTime", UTCTimeUtils.getUTCTimeStr(24*30));
//		timeMap.put("pageNumber", "1");
//		TemplateModel template = templateService.getTemplateContent("GetSellerListByEndTime", "ebay"); 
//		RequestModel request = new RequestModel(template,account,timeMap);
//		Document doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//		if(null == doc){
//			logger.error("---同步ebay接口GetSellerList失败---"+account.getAccount());
//			return;
//		}
//		ParseSellerListItemXMLModel parse = new ParseSellerListItemXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//		updateSellerItemInfo(parse.getResult());
//		int pageCount = parse.getTotalPage();
//		for(int i = 2;i<=pageCount;i++){
//			timeMap.put("pageNumber", String.valueOf(i));
//			request.setParam(timeMap);
//			doc = baseHttpService.getPesponseXml(request.getUrl(), request.getRequestHead(), request.getRequestXML());
//			if(null == doc){
//				logger.error("---同步ebay接口GetSellerList失败(分页)---第"+i+"页--"+account.getAccount());
//				continue;
//			}
//			parse = new ParseSellerListItemXMLModel(doc,"urn:ebay:apis:eBLBaseComponents");
//			updateSellerItemInfo(parse.getResult());
//		}
//	}
//	
//	private void updateSellerItemInfo(List<Map<String, Object>> result) {
//		for(Map<String, Object> item :result){
//			String itemId = item.get("ItemID").toString();
//			Object obj = item.get("variations");
//			if(null != obj){
//				List<Map<String,Object>> variations = (List<Map<String,Object>>)obj;
//				String variationsStr = pubInfoDAO.getVariationByItemId(itemId);
//				if(null == variationsStr){
//					continue;
//				}
//				JSONObject variationJson = JSONObject.fromObject(variationsStr);
//				JSONArray array = variationJson.getJSONArray("variations");
//				for(int i = 0;i<array.size();i++){
//					JSONObject json = array.getJSONObject(i);
//					String sku = json.getString("SKU");
//					String jsonStr = json.toString();
//					for(Map<String,Object> m : variations){
//						if(!sku.equals(m.get("SKU").toString())){
//							continue;
//						}
//						String vaQuantity = m.get("Quantity").toString();
//						if("".equals(vaQuantity)){
//							json.put("Quantity", "0");
//						}else{
//							int qty1 =paseInt(m.get("Quantity").toString()) - paseInt(m.get("QuantitySold").toString());
//							json.put("Quantity", String.valueOf(qty1));
//						}
//						array.set(i, json);
//						break;
//					}
//				}
//				variationJson.put("variations", array);
//				item.put("variations", variationJson.toString());
//			}
//			//更新信息
//			pubInfoDAO.updateItemBySellerListApi(item);
//		}
//	}
//	@Override
//	public List<ShipLoactionModel> getTransShipWide(int siteId) {
//		return pubInfoDAO.getTransShipWide(siteId);
//	}
//
//}
//	
