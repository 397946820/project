// 国内运输添加模板,标准版
var DomesticShipping = function(num, id) {
	var domesticShipping = '<div class="add_domesticShipping">'
			+ '<div class="form-group">'
			+ '<label class="form_label col-md-2"></label>'
			+ '<div class="col-md-4 form_inline"><p class="shippingtitle">第'
			+ num
			+ '运输</p>'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="form_label col-md-2">运输方式</label>'
			+ '<div class="col-md-10 form_inline">'
			+ '<select id="domesticShippingType_'
			+ id
			+ '" class="form-control width_auto">'
			+ '<option value="" selected="selected">-- 选择 --</option>'
			+ '<optgroup label="Economy services - 经济服务">'
			+ '<option value="US_DGMSmartMailGround">DGM SmartMail Ground ( 3 to 8 business days ) - DGM SmartMail地面服务</option>'
			+ '<option value="US_DGMSmartMailGroundFromCN">DGM SmartMail Ground from CN ( 3 to 8 business days ) - 中国DGM SmartMail地面服务</option>'
			+ '<option value="US_DGMSmartMailGroundFromHK">DGM SmartMail Ground from HK ( 3 to 8 business days ) - 香港DGM SmartMail地面服务</option>'
			+ '<option value="US_DGMSmartMailGroundFromTW">DGM SmartMail Ground from TW ( 3 to 8 business days ) - 台湾DGM SmartMail地面服务</option>'
			+ '<option value="Other">Economy Shipping ( 1 to 10 business days ) - 经济运输</option>'
			+ '<option value="FedExSmartPost">FedEx SmartPost ( 2 to 8 business days ) - 联邦智能包裹</option>'
			+ '<option value="US_UPSSurePost">UPS Surepost ( 1 to 6 business days ) - UPS Surepost</option>'
			+ '<option value="US_UPSSurePostFromCN">UPS Surepost from CN ( 1 to 6 business days ) - 中国UPS Surepost</option>'
			+ '<option value="US_UPSSurePostFromHK">UPS Surepost from HK ( 1 to 6 business days ) - 香港UPS Surepost</option>'
			+ '<option value="US_UPSSurePostFromTW">UPS Surepost from TW ( 1 to 6 business days ) - 台湾UPS Surepost</option>'
			+ '<option value="USPSMedia">USPS Media Mail ( 2 to 8 business days ) - USPS媒体邮件</option>'
			+ '<option value="USPSParcel">USPS Parcel Select Ground ( 2 to 9 business days ) - 美国邮政包裹选择地面</option>'
			+ '<option value="USPSStandardPost">USPS Retail Ground ( 2 to 9 business days ) - 美国邮政零售地</option>'
			+ '</optgroup>'
			+ '</select>'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="form_label col-md-2">运费</label>'
			+ '<div class="col-md-4 form_inline">'
			+ '<input id="domShippingCost_'
			+ id
			+ '" name="txtShippingDomCost'
			+ id
			+ '" type="text" value="0.00" class="form-control input_small">'
			+ '<span class="CSymbol help-inline">USD</span>'
			+ '<input id="isFreeDomShipping_'
			+ id
			+ '" type="checkbox" name="chkShippingFreeDom'
			+ id
			+ '"> <label class="free" for="chkShippingFreeDom1">免费</label>'
			+ '</div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="form_label col-md-2">额外每件加收</label>'
			+ '<div id="ddShippingFeeAddDom'
			+ id
			+ '" class="col-md-4 form_inline">'
			+ '<input id="additionalCost_'
			+ id
			+ '" name="txtShippingDomAddCost1'
			+ id
			+ '" type="text" value="0.00" class="form-control input_small">'
			+ '<span class="CSymbol help-inline">USD</span>'
			+ '</div>'
			+ '</div>'
			+ '<div id="dtShippingDomSurcharge'
			+ id
			+ '" class="form-group">'
			+ '<label class="form_label col-md-2">AK,HI,PR 额外收费</label>'
			+ '<div id="ddShippingDomSurcharge'
			+ id
			+ '" class="col-md-4 form_inline">'
			+ '<input id="akHiPrAdditionalCost_'
			+ id
			+ '" name="txtShippingDomSurcharge'
			+ id
			+ '" type="text" value="0.00" class="form-control input_small">'
			+ '<span class="CSymbol help-inline">USD</span>'
			+ '</div>'
			+ '</div>' + '</div>';
	return domesticShipping;
};

//国际运输添加模板
var InternationalShipping = function(num, id) {
	 var InternationalShipping = '<div class="add_internationalShipping">'+
  '<div class="form-group">'+
      '<label class="form_label col-md-2"></label>'+
      '<div class="col-md-4 form_inline">'+
          '<p class="shippingtitle">第'+num+'运输</p>'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="form_label col-md-2">运输方式</label>'+
      '<div class="col-md-10 form_inline">'+
          '<select id="ddlShippingType_'+id+'" name="ddlShippingInt'+id+'" class="form-control width_auto">'+
              '<option value="" selected="selected">-- 选择 --</option>'+
              '<optgroup label="Economy services - 经济服务">'+
              '<option value="US_IntlEconomyShippingFromGC">Economy Shipping from China/Hong Kong/Taiwan to worldwide ( 11 to 35 business days ) - 从中国大陆/香港/台湾发往世界各地的经济运输</option>'+
              '</optgroup>'+
              '<optgroup label="Expedited services - 加急服务">'+
              '<option value="ExpeditedDeliveryToRussia">Expedited International Courier Delivery to Russia ( 7 to 14 business days ) - 发往俄罗斯的国际快捷服务</option>'+
              '<option value="US_IntlExpeditedShippingFromGC">Expedited Shipping from China/Hong Kong/Taiwan to worldwide ( 2 to 7 business days ) - 从中国大陆/香港/台湾发往世界各地的快捷运输</option>'+
              '</optgroup>'+
              '<optgroup label="Other services - 其它服务">'+
              '<option value="OtherInternational">Economy International Shipping - 国际航运经济</option>'+
              '<option value="ExpeditedInternational">Expedited International Shipping - 加快国际航运</option>'+
              '<option value="FedExGroundInternationalToCanada">FedEx Ground International to Canada - 联邦地面国际到加拿大</option>'+
              '<option value="FedExInternationalEconomy">FedEx International Economy - 联邦国际经济特快服务</option>'+
              '<option value="FedExInternationalPriority">FedEx International Priority - 联邦国际优先特快服务</option>'+
              '<option value="US_RUTrackedFromChina">RU Tracked Packet from China ( 7 to 15 business days ) - 来自中国的RU挂号小包</option>'+
              '<option value="StandardInternational">Standard International Shipping - 标准的国际航运</option>'+
              '<option value="UPSStandardToCanada">UPS Standard to Canada - 发往加拿大的UPS标准服务</option>'+
              '<option value="UPSWorldWideExpedited">UPS Worldwide Expedited ( 2 to 5 business days ) - UPS全球快捷服务</option>'+
              '<option value="UPSWorldWideExpress">UPS Worldwide Express ( 1 to 2 business days ) - UPS全球特快服务</option>'+
              '<option value="UPSWorldWideExpressPlus">UPS Worldwide Express Plus ( 1 to 2 business days ) - UPS全球特快加急服务</option>'+
              '<option value="UPSWorldwideSaver">UPS Worldwide Saver ( 1 to 3 business days ) - UPS全球速快服务</option>'+
              '<option value="USPSFirstClassMailInternational">USPS First Class Mail Intl / First Class Package Intl Service - USPS国际普通邮件/国际普通包裹</option>'+
              '<option value="USPSExpressMailInternational">USPS Priority Mail Express International ( 3 to 5 business days ) - USPS国际优先特快邮件</option>'+
              '<option value="USPSExpressMailInternationalFlatRateEnvelope">USPS Priority Mail Express International Flat Rate Envelope ( 3 to 5 business days ) - USPS国际优先特快邮件统一邮资信封</option>'+
              '<option value="USPSExpressMailInternationalLegalFlatRateEnvelope">USPS Priority Mail Express International Legal Flat Rate Envelope ( 3 to 5 business days ) - USPS优先邮件快递国际法律的扁平率信封</option>'+
              '<option value="USPSPriorityMailInternational">USPS Priority Mail International ( 6 to 10 business days ) - USPS国际优先邮件</option>'+
              '<option value="USPSPriorityMailInternationalFlatRateEnvelope">USPS Priority Mail International Flat Rate Envelope ( 6 to 10 business days ) - USPS国际优先邮件统一邮资信封</option>'+
              '<option value="USPSPriorityMailInternationalLargeFlatRateBox">USPS Priority Mail International Large Flat Rate Box ( 6 to 10 business days ) - USPS国际优先邮件大型统一邮资包装盒</option>'+
              '<option value="USPSPriorityMailInternationalLegalFlatRateEnvelope">USPS Priority Mail International Legal Flat Rate Envelope ( 6 to 10 business days ) - USPS国际优先邮件统一邮资法律信封</option>'+
              '<option value="USPSPriorityMailInternationalFlatRateBox">USPS Priority Mail International Medium Flat Rate Box ( 6 to 10 business days ) - USPS国际优先邮件中型统一邮资包装盒</option>'+
              '<option value="USPSPriorityMailInternationalPaddedFlatRateEnvelope">USPS Priority Mail International Padded Flat Rate Envelope ( 6 to 10 business days ) - USPS国际优先邮件统一邮资加垫信封</option>'+
              '<option value="USPSPriorityMailInternationalSmallFlatRateBox">USPS Priority Mail International Small Flat Rate Box ( 6 to 10 business days ) - USPS国际优先邮件小型统一邮资包装盒</option>'+
              '</optgroup>'+
              '<optgroup label="Standard services - 标准服务">'+
              '<option value="US_IntlStandardShippingFromGC">Standard Shipping from China/Hong Kong/Taiwan to worldwide ( 7 to 19 business days ) - 从中国大陆/香港/台湾发往世界各地的标准运输</option>'+
              '</optgroup>'+
          '</select>'+
      '</div>'+
  '</div>'+
  '<div id="dtShippingFeeInt'+id+'" class="form-group">'+
      '<label class="form_label col-md-2">运费</label>'+
      '<div id="ddShippingFeeInt'+id+'" class="col-md-4 form_inline">'+
          '<input id="shippingIntCost_'+id+'" name="ShippingIntCost'+id+'" type="text" value="0.00" id="txtShippingIntCost1" class="form-control input-small">'+
          '<span class="CSymbol help-inline">USD</span>'+
          '<input id="isFreeShippingInt_'+id+'" type="checkbox" name="chkShippingFreeInt'+id+'"><label class="free" for="chkShippingFreeInt'+id+'">免费</label>'+
      '</div>'+
  '</div>'+
  '<div id="dtShippingFeeAddInt'+id+'" class="form-group">'+
      '<label class="form_label col-md-2">额外每件加收</label>'+
      '<div id="ddShippingFeeAddInt'+id+'" class="col-md-4 form_inline">'+
          '<input id="shippingIntAddCost_'+id+'" name="ShippingIntAddCost'+id+'" type="text" value="0.00" class="form-control input_small">'+
          '<span class="CSymbol help-inline">USD</span>'+
      '</div>'+
  '</div>'+
  '<div class="form-group">'+
      '<label class="form_label col-md-2"> 运到</label>'+
      '<div class="col-md-10 form_inline">'+
          '<div>'+
              '<input id="isShipWorldWide_'+id+'" name="chkShipWorldWide'+id+'" type="checkbox" id="chkShip2WorldWide" value="Worldwide"> <label for="chkShip2WorldWide">全球 </label>'+
              '<a class="selectAll">选择以下所有国家和地区</a>'+
          '</div>'+
          '<ul id="chkShipTo_'+id+'" class="shipcountry">'+
              '<li><input type="checkbox" name="chkShipTo0" value="CN"><label for="chkShipTo0">中国</label></li>'+
              '<li><input type="checkbox" name="chkShipTo1" value="RU"><label for="chkShipTo1">俄罗斯联邦</label></li>'+
              '<li><input type="checkbox" name="chkShipTo2" value="CA"><label for="chkShipTo2">加拿大</label></li>'+
              '<li><input type="checkbox" name="chkShipTo3" value="BR"><label for="chkShipTo3">巴西</label></li>'+
              '<li><input type="checkbox" name="chkShipTo4" value="DE"><label for="chkShipTo4">德国</label></li>'+
              '<li><input type="checkbox" name="chkShipTo5" value="FR"><label for="chkShipTo5">法国</label></li>'+
              '<li><input type="checkbox" name="chkShipTo6" value="Europe"><label for="chkShipTo6">欧洲</label></li>'+
              '<li><input type="checkbox" name="chkShipTo7" value="GB"><label for="chkShipTo7">联合王国</label></li>'+
              '<li><input type="checkbox" name="chkShipTo8" value="Americas"><label for="chkShipTo8">美洲</label></li>'+
              '<li><input type="checkbox" name="chkShipTo9" value="Asia"><label for="chkShipTo9">亚洲</label></li>'+
              '<li><input type="checkbox" name="chkShipTo10" value="AU"><label for="chkShipTo10">澳大利亚</label></li>'+
              '<li><input type="checkbox" name="chkShipTo11" value="MX"><label for="chkShipTo11">墨西哥</label></li>'+
              '<li><input type="checkbox" name="chkShipTo12" value="JP"><label for="chkShipTo12">日本</label></li>'+
          '</ul>'+
      '</div>'+
  '</div>'+
'<div>';
	return InternationalShipping;
}
var InternationalShipping2 = function(num,id){
    var InternationalShipping2 = '<div class="add_internationalShipping">'+
    '<div class="form-group">'+
        '<label class="form_label col-md-2"></label>'+
        '<div class="col-md-4 form_inline">'+
            '<p class="shippingtitle">第'+num+'运输</p>'+
        '</div>'+
    '</div>'+
    '<div class="form-group">'+
        '<label class="form_label col-md-2">运输方式</label>'+
        '<div class="col-md-10 form_inline">'+
            '<select id="ddlShippingType_'+id+'" name="ddlShippingInt'+id+'" class="form-control width_auto">'+
                '<option value="" selected="selected">-- 选择 --</option>'+
                '<optgroup label="Other services - 其它服务">'+
                    '<option value="UPSStandardToCanada">UPS Standard to Canada - 发往加拿大的UPS标准服务</option>'+
                    '<option value="UPSWorldWideExpedited">UPS Worldwide Expedited ( 2 to 5 business days ) - UPS全球快捷服务</option>'+
                    '<option value="UPSWorldWideExpress">UPS Worldwide Express ( 1 to 2 business days ) - UPS全球特快服务</option>'+
                    '<option value="UPSWorldWideExpressPlus">UPS Worldwide Express Plus ( 1 to 2 business days ) - UPS全球特快加急服务</option>'+
                    '<option value="UPSWorldwideSaver">UPS Worldwide Saver ( 1 to 3 business days ) - UPS全球速快服务</option>'+
                    '<option value="USPSFirstClassMailInternational">USPS First Class Mail Intl / First Class Package Intl Service - USPS国际普通邮件/国际普通包裹</option>'+
                    '<option value="USPSExpressMailInternational">USPS Priority Mail Express International ( 3 to 5 business days ) - USPS国际优先特快邮件</option>'+
                    '<option value="USPSExpressMailInternationalFlatRateEnvelope">USPS Priority Mail Express International Flat Rate Envelope ( 3 to 5 business days ) - USPS国际优先特快邮件统一邮资信封</option>'+
                    '<option value="USPSExpressMailInternationalLegalFlatRateEnvelope">USPS Priority Mail Express International Legal Flat Rate Envelope ( 3 to 5 business days ) - USPS优先邮件快递国际法律的扁平率信封</option>'+
                    '<option value="USPSPriorityMailInternational">USPS Priority Mail International ( 6 to 10 business days ) - USPS国际优先邮件</option>'+
                    '<option value="USPSPriorityMailInternationalFlatRateEnvelope">USPS Priority Mail International Flat Rate Envelope ( 6 to 10 business days ) - USPS国际优先邮件统一邮资信封</option>'+
                    '<option value="USPSPriorityMailInternationalLargeFlatRateBox">USPS Priority Mail International Large Flat Rate Box ( 6 to 10 business days ) - USPS国际优先邮件大型统一邮资包装盒</option>'+
                    '<option value="USPSPriorityMailInternationalLegalFlatRateEnvelope">USPS Priority Mail International Legal Flat Rate Envelope ( 6 to 10 business days ) - USPS国际优先邮件统一邮资法律信封</option>'+
                    '<option value="USPSPriorityMailInternationalFlatRateBox">USPS Priority Mail International Medium Flat Rate Box ( 6 to 10 business days ) - USPS国际优先邮件中型统一邮资包装盒</option>'+
                    '<option value="USPSPriorityMailInternationalPaddedFlatRateEnvelope">USPS Priority Mail International Padded Flat Rate Envelope ( 6 to 10 business days ) - USPS国际优先邮件统一邮资加垫信封</option>'+
                    '<option value="USPSPriorityMailInternationalSmallFlatRateBox">USPS Priority Mail International Small Flat Rate Box ( 6 to 10 business days ) - USPS国际优先邮件小型统一邮资包装盒</option>'+
                '</optgroup>'+
            '</select>'+
        '</div>'+
    '</div>'+
    '<div class="form-group">'+
        '<label class="form_label col-md-2"> 运到</label>'+
        '<div class="col-md-10 form_inline">'+
            '<div>'+
                '<input id="isShipWorldWide_'+id+'" name="chkShipWorldWide'+id+'" type="checkbox" value="Worldwide"> <label for="chkShipWorldWide'+id+'">全球 </label>'+
                '<a class="selectAll">选择以下所有国家和地区</a>'+
            '</div>'+
            '<ul id="chkShipTo_'+id+'" class="shipcountry">'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo0" value="CN">'+
                    '<label for="chkShipTo0">中国</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo1" value="RU">'+
                    '<label for="chkShipTo1">俄罗斯联邦</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo2" value="CA">'+
                    '<label for="chkShipTo2">加拿大</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo3" value="BR">'+
                    '<label for="chkShipTo3">巴西</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo4" value="DE">'+
                    '<label for="chkShipTo4">德国</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo5" value="FR">'+
                    '<label for="chkShipTo5">法国</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo6" value="Europe">'+
                    '<label for="chkShipTo6">欧洲</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo7" value="GB">'+
                    '<label for="chkShipTo7">联合王国</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo8" value="Americas">'+
                    '<label for="chkShipTo8">美洲</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo9" value="Asia">'+
                    '<label for="chkShipTo9">亚洲</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo10" value="AU">'+
                    '<label for="chkShipTo10">澳大利亚</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo11" value="MX">'+
                    '<label for="chkShipTo11">墨西哥</label>'+
                '</li>'+
                '<li>'+
                    '<input type="checkbox" name="chkShipTo12" value="JP">'+
                    '<label for="chkShipTo12">日本</label>'+
                '</li>'+
            '</ul>'+
        '</div>'+
    '</div>'+
'</div>';
return InternationalShipping2;
}
//国内运输添加模版,计算版
var DomesticShipping2 = function(num, id) {
	var domesticShipping2 = '<div class="add_domesticShipping">'
			+ '<div class="form-group">'
			+ '<label class="control-label col-md-2"></label>'
			+ '<div class="col-md-4 form_inline"><p class="shippingtitle">第'
			+ num
			+ '运输</p></div>'
			+ '</div>'
			+ '<div class="form-group">'
			+ '<label class="control-label col-md-2">运输方式</label>'
			+ '<div class="col-md-10 form_inline">'
			+ '<select id="domCalculationShipping_'
			+ id
			+ '" class="form-control width_auto">'
			+ '<option value="" selected="selected">-- 选择 --</option>'
			+ '<optgroup label="Economy services - 经济服务">'
			+ '<option value="FedExSmartPost">FedEx SmartPost ( 2 to 8 business days ) - 联邦智能包裹</option>'
			+ '<option value="USPSMedia">USPS Media Mail ( 2 to 8 business days ) - USPS媒体邮件</option>'
			+ '<option value="USPSParcel">USPS Parcel Select Ground ( 2 to 9 business days ) - 美国邮政包裹选择地面</option>'
			+ '<option value="USPSStandardPost">USPS Retail Ground ( 2 to 9 business days ) - 美国邮政零售地</option>'
			+ '</optgroup>'
			+ '<optgroup label="Expedited services - 加急服务">'
			+ '<option value="FedEx2Day">FedEx 2Day ( 1 to 2 business days ) - 联邦两日达特快服务</option>'
			+ '<option value="FedExExpressSaver">FedEx Express Saver ( 1 to 3 business days ) - 联邦特惠特快服务</option>'
			+ '<option value="UPS2ndDay">UPS 2nd Day Air ( 2 business days ) - UPS两日达航空服务</option>'
			+ '<option value="UPS3rdDay">UPS 3 Day Select ( 3 business days ) - UPS三日达服务</option>'
			+ '<option value="USPSPriority">USPS Priority Mail ( 1 to 3 business days ) - USPS优先邮件</option>'
			+ '<option value="USPSExpressMail">USPS Priority Mail Express ( 1 business day ) - USPS优先特快邮件</option>'
			+ '<option value="USPSExpressFlatRateEnvelope">USPS Priority Mail Express Flat Rate Envelope ( 1 business day ) - USPS优先特快邮件统一邮资信封</option>'
			+ '<option value="USPSExpressMailLegalFlatRateEnvelope">USPS Priority Mail Express Legal Flat Rate Envelope ( 1 business day ) - USPS优先特快邮件统一邮资法律信封</option>'
			+ '<option value="USPSPriorityFlatRateEnvelope">USPS Priority Mail Flat Rate Envelope ( 1 to 3 business days ) - USPS优先邮件统一邮资信封</option>'
			+ '<option value="USPSPriorityMailLargeFlatRateBox">USPS Priority Mail Large Flat Rate Box ( 1 to 3 business days ) - USPS优先邮件大型统一邮资包装盒</option>'
			+ '<option value="USPSPriorityMailLegalFlatRateEnvelope">USPS Priority Mail Legal Flat Rate Envelope ( 1 to 3 business days ) - USPS优先邮件统一邮资法律信封</option>'
			+ '<option value="USPSPriorityFlatRateBox">USPS Priority Mail Medium Flat Rate Box ( 1 to 3 business days ) - USPS优先邮件中型统一邮资包装盒</option>'
			+ '<option value="USPSPriorityMailPaddedFlatRateEnvelope">USPS Priority Mail Padded Flat Rate Envelope ( 1 to 3 business days ) - USPS优先邮件统一邮资加垫信封</option>'
			+ '<option value="USPSPriorityMailSmallFlatRateBox">USPS Priority Mail Small Flat Rate Box ( 1 to 3 business days ) - USPS优先邮件小型统一邮资包装盒</option>'
			+ '</optgroup>'
			+ '<optgroup label="One-day services - 一天送货">'
			+ '<option value="FedExPriorityOvernight">FedEx Priority Overnight ( 1 business day ) - 联邦隔夜优先特快服务</option>'
			+ '<option value="FedExStandardOvernight">FedEx Standard Overnight ( 1 business day ) - 联邦隔夜标准特快服务</option>'
			+ '<option value="UPSNextDayAir">UPS Next Day Air ( 1 business day ) - UPS次日达航空服务</option>'
			+ '<option value="UPSNextDay">UPS Next Day Air Saver ( 1 business day ) - UPS次日达航空速快服务</option>'
			+ '</optgroup>'
			+ '<optgroup label="Pickup - 拾取">'
			+ '<option value="Pickup">Local Pickup - 本地自提</option>'
			+ '</optgroup>'
			+ '<optgroup label="Standard services - 标准服务">'
			+ '<option value="FedExHomeDelivery">FedEx Ground or FedEx Home Delivery ( 1 to 5 business days ) - 联邦地面或家庭住址运送服务</option>'
			+ '<option value="UPSGround">UPS Ground ( 1 to 5 business days ) - UPS地面</option>'
			+ '<option value="USPSFirstClass">USPS First Class Package ( 2 to 3 business days ) - USPS普通包裹</option>'
			+ '</optgroup>' + '</select>' + '</div>' + '</div>' + '</div>';
	return domesticShipping2;
}

