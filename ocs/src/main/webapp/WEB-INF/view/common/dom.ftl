<#macro domCalShippingSelect id="">
    <select id="${id}" class="form-control width_auto">
       <option value="" selected="selected">-- 选择 --</option>
       <optgroup label="Economy services - 经济服务">
       <option value="FedExSmartPost">FedEx SmartPost ( 2 to 8 business days ) - 联邦智能包裹</option>
       <option value="USPSMedia">USPS Media Mail ( 2 to 8 business days ) - USPS媒体邮件</option>
       <option value="USPSParcel">USPS Parcel Select Ground ( 2 to 9 business days ) - 美国邮政包裹选择地面</option>
       <option value="USPSStandardPost">USPS Retail Ground ( 2 to 9 business days ) - 美国邮政零售地</option>
       </optgroup>
       <optgroup label="Expedited services - 加急服务">
          <option value="FedEx2Day">FedEx 2Day ( 1 to 2 business days ) - 联邦两日达特快服务</option>
          <option value="FedExExpressSaver">FedEx Express Saver ( 1 to 3 business days ) - 联邦特惠特快服务</option>
          <option value="UPS2ndDay">UPS 2nd Day Air ( 2 business days ) - UPS两日达航空服务</option>
          <option value="UPS3rdDay">UPS 3 Day Select ( 3 business days ) - UPS三日达服务</option>
          <option value="USPSPriority">USPS Priority Mail ( 1 to 3 business days ) - USPS优先邮件</option>
          <option value="USPSExpressMail">USPS Priority Mail Express ( 1 business day ) - USPS优先特快邮件</option>
          <option value="USPSExpressFlatRateEnvelope">USPS Priority Mail Express Flat Rate Envelope ( 1 business day ) - USPS优先特快邮件统一邮资信封</option>
          <option value="USPSExpressMailLegalFlatRateEnvelope">USPS Priority Mail Express Legal Flat Rate Envelope ( 1 business day ) - USPS优先特快邮件统一邮资法律信封</option>
          <option value="USPSPriorityFlatRateEnvelope">USPS Priority Mail Flat Rate Envelope ( 1 to 3 business days ) - USPS优先邮件统一邮资信封</option>
          <option value="USPSPriorityMailLargeFlatRateBox">USPS Priority Mail Large Flat Rate Box ( 1 to 3 business days ) - USPS优先邮件大型统一邮资包装盒</option>
          <option value="USPSPriorityMailLegalFlatRateEnvelope">USPS Priority Mail Legal Flat Rate Envelope ( 1 to 3 business days ) - USPS优先邮件统一邮资法律信封</option>
          <option value="USPSPriorityFlatRateBox">USPS Priority Mail Medium Flat Rate Box ( 1 to 3 business days ) - USPS优先邮件中型统一邮资包装盒</option>
          <option value="USPSPriorityMailPaddedFlatRateEnvelope">USPS Priority Mail Padded Flat Rate Envelope ( 1 to 3 business days ) - USPS优先邮件统一邮资加垫信封</option>
          <option value="USPSPriorityMailSmallFlatRateBox">USPS Priority Mail Small Flat Rate Box ( 1 to 3 business days ) - USPS优先邮件小型统一邮资包装盒</option>
       </optgroup>
       <optgroup label="One-day services - 一天送货">
          <option value="FedExPriorityOvernight">FedEx Priority Overnight ( 1 business day ) - 联邦隔夜优先特快服务</option>
          <option value="FedExStandardOvernight">FedEx Standard Overnight ( 1 business day ) - 联邦隔夜标准特快服务</option>
          <option value="UPSNextDayAir">UPS Next Day Air ( 1 business day ) - UPS次日达航空服务</option>
          <option value="UPSNextDay">UPS Next Day Air Saver ( 1 business day ) - UPS次日达航空速快服务</option>
       </optgroup>
       <optgroup label="Pickup - 拾取">
          <option value="Pickup">Local Pickup - 本地自提</option>
       </optgroup>
       <optgroup label="Standard services - 标准服务">
          <option value="FedExHomeDelivery">FedEx Ground or FedEx Home Delivery ( 1 to 5 business days ) - 联邦地面或家庭住址运送服务</option>
          <option value="UPSGround">UPS Ground ( 1 to 5 business days ) - UPS地面</option>
          <option value="USPSFirstClass">USPS First Class Package ( 2 to 3 business days ) - USPS普通包裹</option>
       </optgroup>
   </select>
</#macro>
<#macro domShippingSelect id="">
   <select id="${id}" class="form-control width_auto">
	  <option value="" selected="selected">-- 选择 --</option>
	  <optgroup label="Economy services - 经济服务">
		  <option value="US_DGMSmartMailGround">DGM SmartMail Ground ( 3 to 8 business days ) - DGM SmartMail地面服务</option>
		  <option value="US_DGMSmartMailGroundFromCN">DGM SmartMail Ground from CN ( 3 to 8 business days ) - 中国DGM SmartMail地面服务</option>
		  <option value="US_DGMSmartMailGroundFromHK">DGM SmartMail Ground from HK ( 3 to 8 business days ) - 香港DGM SmartMail地面服务</option>
		  <option value="US_DGMSmartMailGroundFromTW">DGM SmartMail Ground from TW ( 3 to 8 business days ) - 台湾DGM SmartMail地面服务</option>
		  <option value="Other">Economy Shipping ( 1 to 10 business days ) - 经济运输</option>
		  <option value="FedExSmartPost">FedEx SmartPost ( 2 to 8 business days ) - 联邦智能包裹</option>
		  <option value="US_UPSSurePost">UPS Surepost ( 1 to 6 business days ) - UPS Surepost</option>
		  <option value="US_UPSSurePostFromCN">UPS Surepost from CN ( 1 to 6 business days ) - 中国UPS Surepost</option>
		  <option value="US_UPSSurePostFromHK">UPS Surepost from HK ( 1 to 6 business days ) - 香港UPS Surepost</option>
		  <option value="US_UPSSurePostFromTW">UPS Surepost from TW ( 1 to 6 business days ) - 台湾UPS Surepost</option>
		  <option value="USPSMedia">USPS Media Mail ( 2 to 8 business days ) - USPS媒体邮件</option>
		  <option value="USPSParcel">USPS Parcel Select Ground ( 2 to 9 business days ) - 美国邮政包裹选择地面</option>
		  <option value="USPSStandardPost">USPS Retail Ground ( 2 to 9 business days ) - 美国邮政零售地</option>
	  </optgroup>
	</select>
</#macro>
<#macro domCauShipping index="0" title="">
	<div class="form-group">
		<label class="control-label col-md-2"></label>
		<div class="col-md-4 form_inline">
			<p class="shippingtitle">${title}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-md-2">运输方式</label>
		<div class="col-md-10 form_inline">
			<@FTL.D.domCalShippingSelect id="domCalcuShippingType_${index}" />
		</div>
	</div>
</#macro>
<#macro domStandShipping 
title="" 
isFreeDomShipping=false
fare=""
additionalCost=""
akHiPrAdditionalCost=""
index="0"
>
	<div class="form-group">
		<label class="form_label col-md-2"></label>
		<div class="col-md-4 form_inline">
			<p class="shippingtitle">${title}</p>
		</div>
	</div>
	<div class="form-group">
		<label class="form_label col-md-2">运输方式</label>
		<div class="col-md-10 form_inline">
			<@FTL.D.domShippingSelect id="domesticShippingType_${index}" />
		</div>
	</div>
	<div class="form-group">
		<label class="form_label col-md-2">运费</label>
		<div class="col-md-4 form_inline">
			<input id="domShippingCost_${index!''}" name="txtShippingDomCost2" type="text" value="${fare!'0.00'}" class="form-control input_small">
			<span class="CSymbol help-inline">USD</span>
			<input id="isFreeDomShipping_${index}" type="checkbox" <#if isFreeDomShipping==true>checked="checked"</#if> name="chkShippingFreeDom2"> 
			<label class="free" for="chkShippingFreeDom1">免费</label>
		</div>
	</div>
	<div class="form-group">
		<label class="form_label col-md-2">额外每件加收</label>
		<div id="ddShippingFeeAddDom2" class="col-md-4 form_inline">
			<input id="additionalCost_${index}" name="txtShippingDomAddCost12" type="text" value="${additionalCost!'0.00'}" class="form-control input_small">
			<span class="CSymbol help-inline">USD</span>
		</div>
	</div>
	<div class="form-group">
		<label class="form_label col-md-2">AK,HI,PR 额外收费</label>
		<div class="col-md-4 form_inline">
			<input id="akHiPrAdditionalCost_${index}" name="txtShippingDomSurcharge2" type="text" value="${akHiPrAdditionalCost!'0.00'}" class="form-control input_small">
			<span class="CSymbol help-inline">USD</span>
		</div>
	</div>
</#macro>