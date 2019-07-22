//package com.it.ocs.synchronou.service.impl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ebay.sdk.ApiContext;
//import com.ebay.sdk.call.GeteBayDetailsCall;
//import com.ebay.soap.eBLBaseComponents.DetailNameCodeType;
//import com.ebay.soap.eBLBaseComponents.ReturnPolicyDetailsType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.synchronou.dao.IEBayReturnPolicyDetailDao;
//import com.it.ocs.synchronou.mapping.SiteIDEnum;
//import com.it.ocs.synchronou.model.EBayReturnPolicyDetailModel;
//import com.it.ocs.synchronou.service.IEBayReturnPolicyDetailService;
//import com.it.ocs.synchronou.util.ReturnPolicySDK;
//import com.it.ocs.synchronou.vo.ReturnPolicyDetailVO;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@Service
//public class EBayReturnPolicyDetailService extends BaseService implements IEBayReturnPolicyDetailService {
//    private static final Logger log = Logger.getLogger(EBayReturnPolicyDetailService.class);
//	@Autowired
//	private IEBayReturnPolicyDetailDao returnPolicyDao;
//	@Override
//	public OperationResult synchronouReturnPolicyDetail(Long site_id) {
//		OperationResult result = new OperationResult();
//		try {
//			BaseEbaySDKService baseService = new BaseEbaySDKService();
//			ApiContext apiContext = baseService.getApiContext("AgAAAA**AQAAAA**aAAAAA**u1RjWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEmYSiC5SDpQ2dj6x9nY+seQ**Xq8DAA**AAMAAA**nzc7lCXNjRxjfZiTVjNJ6hYFYlZxK+5R6x7rr5WSGrTGJTjr3ykfT4YvBuhdDheZSpftIpfgfQMA//5Y1AhRHhckFpPnVU+7t2R7pViACV9wyiDGh2UjZ/XRuh1v5dr6zqUQN8AIo47gJE+AXeeehIC7G77E0DY2/0p+IxIWCtqP9MNjAfMIPWYsOhX4OnqMQgBIQdcMZZ5/3PoQRj/0ELiTn3RqltZq/T9BnyNv14qXBfXcUiijizx2a0OZBv1dfIrog7/ohP/sqBgvgyl31Ebi78Qp/TgIZtjP9r44tbw7kmsk/X1jtjeeXYfKZ+JelJlYZLZX4DTCCobYAZ7E5sbPI7frJFyRBsm7gxV5sZeBPdwDC5hB9pOTQpM27CdOsz2e089ughAEQw8H9ZV+DXUR4Aw3BNr40QwAkh34dBDF7UFo6JU8bWYTeweFT2vAdxUhvEfFjb5FRMcU5A9LK6SUkcoXP5f2DOD9nloZQOw6X6rczVsDeXd2dCPGTK+vpf82gjY+NWbVanPjICAEwFQZqPQe2OesAcbbK5ztYmeftzAvIEoWR71fHMrKDNrVeEFzzP9a9Pdl/C1UZ5Ys/xlLU07BnqL/idcx0+2WdcW3ZKpwMD7M6NobbqAuHBE7pvezSZ1DAVl+h5mrEURUQdOUMCRV5nKNkOYooeORqtKVclyNAOMPnvJwgn1sr/gdLuzoYtgsREiKBv1wJmni2YwcU80d+uLmCk41nqG3n8unVN0CURR+8g5BGSNFVvl1", "https://api.ebay.com/wsapi");
//			GeteBayDetailsCall eBayDetailsCall = new GeteBayDetailsCall(apiContext);
//			DetailNameCodeType[] nameCodeTypes = new DetailNameCodeType[1];
//			nameCodeTypes[0] = DetailNameCodeType.RETURN_POLICY_DETAILS;
//			eBayDetailsCall.setDetailName(nameCodeTypes);
//			eBayDetailsCall.setSite(SiteIDEnum.searchBySiteID(site_id));
//			eBayDetailsCall.geteBayDetails();
//		    ReturnPolicyDetailsType returnPolicyDetailsType = eBayDetailsCall.getReturnedReturnPolicyDetails();
//		    EBayReturnPolicyDetailModel returnPolicyDetailModel = new EBayReturnPolicyDetailModel();
//		    String refund = ReturnPolicySDK.getRefund(returnPolicyDetailsType.getRefund());
//		    returnPolicyDetailModel.setRefund(refund);
//		    String returns_within = ReturnPolicySDK.getReturnsWithin(returnPolicyDetailsType.getReturnsWithin());
//			returnPolicyDetailModel.setReturns_within(returns_within);
//		    String shippingcost_paidby = ReturnPolicySDK.getShippingCostPaidBy(returnPolicyDetailsType.getShippingCostPaidBy());
//			returnPolicyDetailModel.setShippingcost_paidby(shippingcost_paidby);
//		    String restocking_feevalue = ReturnPolicySDK.getRestockingFeeValue(returnPolicyDetailsType.getRestockingFeeValue());
//			returnPolicyDetailModel.setRestocking_feevalue(restocking_feevalue);
//		    String returns_accepted = ReturnPolicySDK.getReturnsAccepted(returnPolicyDetailsType.getReturnsAccepted());
//			returnPolicyDetailModel.setReturns_accepted(returns_accepted);
//		    String description = returnPolicyDetailsType.isDescription().toString();
//		    returnPolicyDetailModel.setDescription(description);
//		    returnPolicyDetailModel.setSite_id(site_id);
//		    List<EBayReturnPolicyDetailModel> oracleReturnPolicys = selectReturnPolicySiteIds();
//		    Map<Long, EBayReturnPolicyDetailModel> oracleMaps = new HashMap<>();
//		    for(EBayReturnPolicyDetailModel eBayReturnPolicyDetailModel : oracleReturnPolicys){
//		    	
//		    	oracleMaps.put(eBayReturnPolicyDetailModel.getSite_id(), eBayReturnPolicyDetailModel);
//		    }
//		    if(oracleMaps.get(site_id)!=null){
//		    	updateReturnPolicyDetail(returnPolicyDetailModel);
//		    }else{
//		    	insertReturnPolicyDetail(returnPolicyDetailModel);
//		    }
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult insertReturnPolicyDetail(EBayReturnPolicyDetailModel returnPolicyDetailModel) {
//		OperationResult result = new OperationResult();
//		try {
//			returnPolicyDao.insertReturnPolicyDetail(returnPolicyDetailModel);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("insert error");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public OperationResult updateReturnPolicyDetail(EBayReturnPolicyDetailModel returnPolicyDetailModel) {
//		OperationResult result = new OperationResult();
//		try {
//			returnPolicyDao.updateReturnPolicyDetail(returnPolicyDetailModel);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("update error");
//			e.printStackTrace();
//			log.error(e);
//		}
//		return result;
//	}
//
//	@Override
//	public ResponseResult<ReturnPolicyDetailVO> selectReturnPolicyDetails(RequestParam param) {
//		ResponseResult<ReturnPolicyDetailVO> result = new ResponseResult<>();
//		ReturnPolicyDetailVO returnPolicyDetailVO = BeanConvertUtil.mapToObject(param.getParam(), ReturnPolicyDetailVO.class);
//		List<EBayReturnPolicyDetailModel> eBayReturnPolicyDetailModels = returnPolicyDao.selectReturnPolicyDetails(param.getStartRow(), param.getEndRow());
//		int total = returnPolicyDao.getTotal();
//		List<ReturnPolicyDetailVO> returnPolicyDetailVOs = Lists.newArrayList();
//		convertList(eBayReturnPolicyDetailModels, returnPolicyDetailVOs);
//		for (ReturnPolicyDetailVO returnPolicyDetailVO2 : returnPolicyDetailVOs) {
//			StringBuffer refundResult = new StringBuffer();
//			refundResult.append("<select name=\"refundSelect\" style=\"width:240px\" >");
//			String refund = returnPolicyDetailVO2.getRefund();
//			if(refund!=null&&!refund.equals("")){
//				JSONArray jsonArray = JSONArray.fromObject(refund);
//				for (int i = 0; i < jsonArray.size(); i++) {
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					refundResult.append("<option value=\""+jsonObject.get("refundOption")+"\">");
//					refundResult.append(jsonObject.get("description")+"</option>");
//				}
//			}
//			refundResult.append("</select>");
//			returnPolicyDetailVO2.setRefund(refundResult.toString());
//			StringBuffer returnsWithinResult = new StringBuffer();
//			returnsWithinResult.append("<select name=\"returnsWithinOption\" style=\"width:240px\" >");
//			String returns_within = returnPolicyDetailVO2.getReturns_within();
//			if(returns_within!=null&&!returns_within.equals("")){
//				JSONArray jsonArray = JSONArray.fromObject(returns_within);
//				for (int i = 0; i < jsonArray.size(); i++) {
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					returnsWithinResult.append("<option value=\""+jsonObject.get("returnsWithinOption")+"\">");
//					returnsWithinResult.append(jsonObject.get("description")+"</option>");
//				}
//			}
//			returnsWithinResult.append("</select>");
//			returnPolicyDetailVO2.setReturns_within(returnsWithinResult.toString());
//			
//			StringBuffer shippingCostPaidByOptionResult = new StringBuffer();
//			shippingCostPaidByOptionResult.append("<select name=\"shippingCostPaidByOption\" style=\"width:240px\" >");
//		
//			String shippingCostPaidByOption = returnPolicyDetailVO2.getShippingcost_paidby();
//			if(shippingCostPaidByOption!=null&&!shippingCostPaidByOption.equals("")){
//				JSONArray jsonArray = JSONArray.fromObject(shippingCostPaidByOption);
//				for (int i = 0; i < jsonArray.size(); i++) {
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					shippingCostPaidByOptionResult.append("<option value=\""+jsonObject.get("shippingCostPaidByOption")+"\">");
//					shippingCostPaidByOptionResult.append(jsonObject.get("description")+"</option>");
//				}
//			}
//			shippingCostPaidByOptionResult.append("</select>");
//			returnPolicyDetailVO2.setShippingcost_paidby(shippingCostPaidByOptionResult.toString());
//			
//			StringBuffer restockingFeeValueOptionResult = new StringBuffer();
//			restockingFeeValueOptionResult.append("<select name=\"restockingFeeValueOption\" style=\"width:240px\" >");
//			
//			String restockingFeeValueOption = returnPolicyDetailVO2.getRestocking_feevalue();
//			if(restockingFeeValueOption!=null&&!restockingFeeValueOption.equals("")){
//				JSONArray jsonArray = JSONArray.fromObject(restockingFeeValueOption);
//				for (int i = 0; i < jsonArray.size(); i++) {
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					restockingFeeValueOptionResult.append("<option value=\""+jsonObject.get("restockingFeeValueOption")+"\">");
//					restockingFeeValueOptionResult.append(jsonObject.get("description")+"</option>");
//				}
//			}
//			restockingFeeValueOptionResult.append("</select>");
//			returnPolicyDetailVO2.setRestocking_feevalue(restockingFeeValueOptionResult.toString());
//			
//			StringBuffer returnsAcceptedOptionResult = new StringBuffer();
//			returnsAcceptedOptionResult.append("<select name=\"returnsAcceptedOption\" style=\"width:240px\" >");
//		
//			String returnsAcceptedOption = returnPolicyDetailVO2.getReturns_accepted();
//			if(returnsAcceptedOption!=null&&!returnsAcceptedOption.equals("")){
//				JSONArray jsonArray = JSONArray.fromObject(returnsAcceptedOption);
//				for (int i = 0; i < jsonArray.size(); i++) {
//					JSONObject jsonObject = jsonArray.getJSONObject(i);
//					returnsAcceptedOptionResult.append("<option value=\""+jsonObject.get("returnsAcceptedOption")+"\">");
//					returnsAcceptedOptionResult.append(jsonObject.get("description")+"</option>");
//				}
//			}
//			returnsAcceptedOptionResult.append("</select>");
//			returnPolicyDetailVO2.setReturns_accepted(returnsAcceptedOptionResult.toString());
//			
//		}
//		result.setRows(returnPolicyDetailVOs);
//		result.setTotal(total);
//		return result;
//	}
//
//	@Override
//	public List<EBayReturnPolicyDetailModel> selectReturnPolicySiteIds() {
//		return returnPolicyDao.selectReturnPolicySiteIds();
//	}
//	
//	private void convertList(List<EBayReturnPolicyDetailModel> source, final List<ReturnPolicyDetailVO> target) {
//		CollectionUtil.each(source, new IAction<EBayReturnPolicyDetailModel>() {
//			@Override
//			public void excute(EBayReturnPolicyDetailModel obj) {
//				ReturnPolicyDetailVO returnPolicyDetailVO = new ReturnPolicyDetailVO();
//				BeanUtils.copyProperties(obj, returnPolicyDetailVO);
//				target.add(returnPolicyDetailVO);
//			}
//		});
//	}
//
//	@Override
//	public List<EBayReturnPolicyDetailModel> selectReturnPolicysBySiteId(Long site_id) {
//		return null;
//	}
//}
