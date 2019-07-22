var renderCommentModel = function(commentModel) {
	if(commentModel){
		$('#topPromotionType').combobox("setValue",commentModel.topPromotionType);
		$('#footerPromotionType').combobox("setValue",commentModel.footerPromotionType);
		$('#inPromotionType').combobox("setValue",commentModel.inPromotionType);
		//setSelectValue('#counterType',commentModel.counterType);
		//$('#appComment').text(commentModel.appComment)
		//editor.html("#commentContent",commentModel.appComment);
		//KindEditor.html('#commentContent', commentModel.appComment);
		$("#commentContent").prev().find(".ke-content").html(commentModel.comments);
		$("#commentContent").val(commentModel.comments);
		$("#templateTitle").val(commentModel.templateTitle);
		$("#appComment").val(commentModel.appComment);
		$('#chkProtectWebPage').attr('checked',eval(commentModel.openPageProtect)?true:false);
		var imgs = commentModel.ebayImages;
		var imgListHtml = [];
		if(imgs){
			var imgUrls = imgs.split(",");
			$.each(imgUrls,function(){
				//$(".eBayPicture").append(imgLi(this));
				imgListHtml.push(imgLi(this));
			});
			$(".eBayPicture").html(imgListHtml.join(""));
		}
		imgs = commentModel.templateImages;
		imgListHtml = [];
		if(imgs){
			var imgUrls = imgs.split(",");
			$.each(imgUrls,function(){
				//$(".templatePicture").append(imgLi(this));
				imgListHtml.push(imgLi(this));
			});
			$(".templatePicture").html(imgListHtml.join(""));
		}
		$('#sellerDescriptionTemp').combobox("setValue",commentModel.sellerDescription);
		debugger;
		$('#advert_id').combogrid("setValue",commentModel.advert_id);
	}
	
}

function imgLi(imgUrl){
    var imgLi =
    			'<li class="pa ui-widget">'+
    				
                    '<div>'+
                        '<a rel="example_group" target="_blank">'+
                            '<img src='+imgUrl+'>'+
                        '</a>'+
                    '</div>'+
                    '<span class="delete">X</span>'+
                '</li>';
    return imgLi;
}
function changeSize(){
	$('.ui-sortable').sortable({
		placeholder:"portlet-placeholder",
		cancel: ".portlet-toggle"
	});
	$('.ui-sortable').disableSelection();
}
changeSize();
var constructCommentModel = function() {
	var commentModel = {
		topPromotionType : 'dasfdasfdsaf',
		footerPromotionType : 'le.deutschland',
		inPromotionType:'',
		counterType : '4',
		openPageProtect : false,
		templateTitle : 'dasfdasf',
		ebayImages : "",
		templateImages : "",
		appComment : '',
		sellerDescription :"",
		comments : "",
		advert_id:""
	};
	commentModel.topPromotionType = $('#topPromotionType').combobox("getValue");
	commentModel.footerPromotionType = $('#footerPromotionType').combobox("getValue");
	commentModel.inPromotionType = $('#inPromotionType').combobox("getValue");
	if($('#counterType').val()==0){
		commentModel.counterType = "NoHitCounter";
	}else{
		commentModel.counterType = $('#counterType').val();
	}
	if($('#chkProtectWebPage').is(':checked')){
		commentModel.openPageProtect = "true";
	}else{
		commentModel.openPageProtect = "false";
	}
	commentModel.templateTitle = $("#templateTitle").val();
	commentModel.appComment = $('#appComment').val();
	var imgUrls = "";
	$(".eBayPicture li").each(function(){
		if(imgUrls == ""){
			imgUrls = $(this).find("img").attr("src");
		}else{
			imgUrls = imgUrls + ","+$(this).find("img").attr("src");
		}
	});
	commentModel.ebayImages = imgUrls;
	imgUrls = "";
	$(".templatePicture li").each(function(){
		if(imgUrls == ""){
			imgUrls = $(this).find("img").attr("src");
		}else{
			imgUrls = imgUrls + ","+$(this).find("img").attr("src");
		}
	});
	commentModel.templateImages = imgUrls;
	/*$("select[name='commentType']").each(function(){
		var idNum = $(this).attr("id").split("_")[1];
		var commentObj = {
				commentType:$(this).val(),
				commentContent:$("#commentContent_"+idNum).val()
		}
		commentModel.comment.push(commentObj);
	});*/
	commentModel.comments = $("#commentContent").val();
	commentModel.sellerDescription = $('#sellerDescriptionTemp').combobox("getValue");
	commentModel.advert_id = $('#advert_id').combogrid("getValue");
	return commentModel;
}
var descriptionNum = 1;
description = function(num) {
	var description = '<div class="description_wrap">'
			+ '<div class="form-group">'
			+ '<label class="form_label col-md-2">描述'
			+ num
			+ '</label>'
			+ '<div class="col-md-4 form_inline">'
			+ '<span>'
			+ '<select id="commentType_'+descriptionNum+'" name="commentType" id="ddlDescription0" class="form-control input-medium">'
			+ '<option value="">-- 选择 --</option>' + '</select>' + '</span>'
			+ '</div>' + '</div>' + '<div class="form-group">'
			+ '<label class="form_label col-md-2"></label>'
			+ '<div class="col-md-4 form_inline">'
			+ '<textarea id="commentContent_'+descriptionNum+'" class="form-control" rows="5" cols="20"></textarea>'
			+ '</div>' + '</div>' + '</div> ';
	return description;
}
$(function() {
	descriptionNum = $('#articleCommentCount').val();
	$("#hrefAddDescription").on(
			"click",
			function() {
				$("#hrefRemoveDescription").show();
				if (descriptionNum < 5) {
					$(this).parents(".addDescription_wrap").before(
							description(descriptionNum));
					if (descriptionNum == 4) {
						$("#hrefAddDescription").hide();
					}
					descriptionNum++;
				}
			});

	// 移除描述
	$("#hrefRemoveDescription").on("click", function() {
		$(this).parents(".addDescription_wrap").prev().remove();
		var descriptionLength = $(".description_wrap").length;
		descriptionNum = descriptionLength + 1;
		if (descriptionLength <= 0) {
			$("#hrefRemoveDescription").hide();
			$("#hrefAddDescription").show();
		} else {
			$("#hrefAddDescription").show();
		}
	});
})