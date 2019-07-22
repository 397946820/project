var noShip = {
	init: function(){
		this.eventBind();
	},
	data: {

	},
	eventBind: function(){
		var self = this;
		//显示/隐藏所有国家
		$(".showAll").on("click",function(){
			var content = $(this).parents(".subRegion").next(".content");
			if($(this).html() == "显示所有国家"){
				$(this).html("隐藏所有国家");
				content.show();
			}else{
				$(this).html("显示所有国家");
				content.hide();
			}
		});

		//全选、反选
		$(".subRegion input").on("change",function(){
			var selectContent = $(this).parent(".subRegion").next(".content");
			if($(this).is(':checked')){
				selectContent.find("input").prop("checked",true);
				self.showSelected();
			}else{
				selectContent.find("input").prop("checked",false);
				self.showSelected();
			}
		});
		$(".content input").on("change",function(){
			var this_ = this;
			var contentInp = $(this_).parents(".content").find("input");
			var contentInps = contentInp.length;
			var selectedInps = 0;
			var subRegionInp = $(this_).parents(".content").prev(".subRegion").find("input");
			contentInp.each(function(){
				if($(this).is(':checked')){
					selectedInps++;
				}
			});
			if(selectedInps >= contentInps){
				subRegionInp.prop("checked",true);
				self.showSelected();
			}else{
				subRegionInp.prop("checked",false);
				self.showSelected();
			}
		});

		//取消已选
		$(".cancelSelected").on("click",function(){
			$(".international").find("input").prop("checked",false);
			self.showSelected();
		});
	},
	showSelected: function(){
		var checkLength = $(".international").find("input:checked").length;
		var allInp = $(".international").find(".subRegion input");
   		var	check_val = [];//存放被选中的元素的值
		if(checkLength <= 0){
			$(".noSelected").show();
			$(".selected").hide();
			$(".selected").find("b").html("");
		}else{
			$(".noSelected").hide();
			$(".selected").show();
			allInp.each(function(){
	   			if($(this).is(':checked')){
	   				check_val.push($(this).siblings("label").html());
	   			}else{
	   				var contentInp = $(this).parent(".subRegion").next(".content").find("input");
	   				contentInp.each(function(){
	   					if($(this).is(':checked')){
		   					check_val.push($(this).siblings("label").html());
		   				}
	   				});
	   			}
	   		});
	   		check_val = check_val.join(",");
   			$(".selected").find("b").html(check_val);
		}	
	}
};
noShip.init();