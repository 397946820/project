<div id="noShipDialog" class="easyui-dialog" title="不运送地区"
	style="width: 1000px; height: 700px; padding: 10px;"
	data-options="modal:true,
     closed:true,
     buttons: [{
        text:'确认',
        iconCls:'icon-save',
        handler:noShipSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:noShipNo
    }]">
	<div class="domestic" >
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div class="subRegion">
				<input type="checkbox" id="alsj" value="Alaska/Hawaii"> <label for="alsj">阿拉斯加/夏威夷</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="APO/FPO"> <label>APO/FPO</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="US Protectorates"> <label>美国保护</label>
			</div>
		</div>
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Channel Islands"> <label>海峡群岛</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Isle of Man"> <label>马恩岛</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Isle of Wight"> <label>怀特岛</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" value="Northern Ireland"> <label>北爱尔兰</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Scilly Isles"> <label>锡利群岛</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Scottish Highlands"> <label>苏格兰高地</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Scottish Islands"> <label>苏格兰群岛</label>
			</div>
		</div>
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Ostfriesische Inseln"> <label>东弗里西亚群岛</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Hiddensee"> <label>Hiddensee</label>
			</div>
			<div class="subRegion">
				<input type="checkbox" name="" value="Nordfriesische Inseln"> <label>北弗里西亚群岛</label>
			</div>
		</div>
	</div>
	<div class="international">
	<div style=" margin=10px 0;font-weight:300;">International</div>
		<div class="left">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Africa"> <label>非洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox"  name="" value="DZ"> <label>阿尔及利亚</label>
						</li>
						<li><input type="checkbox" name="" value="AO"> <label>安哥拉</label>
						</li>
						<li><input type="checkbox" name="" value="BJ"> <label>贝宁</label>
						</li>
						<li><input type="checkbox" name="" value="BW"> <label>博茨瓦纳</label>
						</li>
						<li><input type="checkbox" name="" value="BF"> <label>布基那法索</label>
						</li>
						<li><input type="checkbox" name="" value="BI"> <label>布隆迪</label>
						</li>
						<li><input type="checkbox" name="" value="CM"> <label>喀麦隆</label>
						</li>
						<li><input type="checkbox" name="" value="CV"> <label>佛得角群岛</label>
						</li>
						<li><input type="checkbox" name="" value="CF"> <label>中非共和国</label>
						</li>
						<li><input type="checkbox" name="" value="TD"> <label>乍得</label>
						</li>
						<li><input type="checkbox" name="" value="KM"> <label>科摩罗</label>
						</li>
						<li><input type="checkbox" name="" value="CD"> <label>刚果民主共和国</label>
						</li>
						<li><input type="checkbox" name="" value="CG"> <label>刚果共和国</label>
						</li>
						<li><input type="checkbox" name="" value="CI"> <label>科特迪瓦（象牙海岸）</label>
						</li>
						<li><input type="checkbox" name="" value="DJ"> <label>吉布提</label>
						</li>
						<li><input type="checkbox" name="" value="EG"> <label>埃及</label>
						</li>
						<li><input type="checkbox" name="" value="GQ"> <label>赤道几内亚</label>
						</li>
						<li><input type="checkbox" name="" value="ER"> <label>厄立特里亚</label>
						</li>
						<li><input type="checkbox" name="" value="ET"> <label>埃塞俄比亚</label>
						</li>
						<li><input type="checkbox" name="" value="GA"> <label>加蓬共和国</label>
						</li>
						<li><input type="checkbox" name="" value="GM"> <label>冈比亚</label>
						</li>
						<li><input type="checkbox" name="" value="GH"> <label>加纳</label>
						</li>
						<li><input type="checkbox" name="" value="GN"> <label>几内亚</label>
						</li>
						<li><input type="checkbox" name="" value="GW"> <label>几内亚比绍</label>
						</li>
						<li><input type="checkbox" name="" value="KE"> <label>肯尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="LS"> <label>莱索托</label>
						</li>
						<li><input type="checkbox" name="" value="LR"> <label>利比里亚</label>
						</li>
						<li><input type="checkbox" name="" value="LY"> <label>利比亚</label>
						</li>
						<li><input type="checkbox" name="" value="MG"> <label>马达加斯加</label>
						</li>
						<li><input type="checkbox" name="" value="MW"> <label>马拉维</label>
						</li>
						<li><input type="checkbox" name="" value="ML"> <label>马里</label>
						</li>
						<li><input type="checkbox" name="" value="MR"> <label>毛里塔尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="MU"> <label>毛里求斯</label>
						</li>
						<li><input type="checkbox" name="" value="YT"> <label>马约特</label>
						</li>
						<li><input type="checkbox" name="" value="MA"> <label>摩洛哥</label>
						</li>
						<li><input type="checkbox" name="" value="MZ"> <label>莫桑比克</label>
						</li>
						<li><input type="checkbox" name="" value="NA"> <label>纳米比亚</label>
						</li>
						<li><input type="checkbox" name="" value="NE"> <label>尼日尔</label>
						</li>
						<li><input type="checkbox" name="" value="NG"> <label>尼日利亚</label>
						</li>
						<li><input type="checkbox" name="" value="RE"> <label>留尼汪島</label>
						</li>
						<li><input type="checkbox" name="" value="RW"> <label>卢旺达</label>
						</li>
						<li><input type="checkbox" name="" value="SH"> <label>圣赫勒拿</label>
						</li>
						<li><input type="checkbox" name="" value="SN"> <label>塞内加尔</label>
						</li>
						<li><input type="checkbox" name="" value="SC"> <label>塞舌尔</label>
						</li>
						<li><input type="checkbox" name="" value="SL"> <label>塞拉利昂</label>
						</li>
						<li><input type="checkbox" name="" value="SO"> <label>索马里</label>
						</li>
						<li><input type="checkbox" name="" value="ZA"> <label>南非</label>
						</li>
						<li><input type="checkbox" name="" value="SZ"> <label>斯威士兰</label>
						</li>
						<li><input type="checkbox" name="" value="TZ"> <label>坦桑尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="TG"> <label>多哥</label>
						</li>
						<li><input type="checkbox" name="" value="TN"> <label>突尼斯</label>
						</li>
						<li><input type="checkbox" name="" value="UG"> <label>乌干达</label>
						</li>
						<li><input type="checkbox" name="" value="EH"> <label>西撒哈拉</label>
						</li>
						<li><input type="checkbox" name="" value="ZM"> <label>赞比亚</label>
						</li>
						<li><input type="checkbox" name="" value="ZW"> <label>津巴布韦</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Europe"> <label>欧洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="AL"> <label>阿尔巴尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="AD"> <label>安道尔</label>
						</li>
						<li><input type="checkbox" name="" value="AT"> <label>奥地利</label>
						</li>
						<li><input type="checkbox" name="" value="BY"> <label>白俄罗斯</label>
						</li>
						<li><input type="checkbox" name="" value="BE"> <label>比利时</label>
						</li>
						<li><input type="checkbox" name="" value="BA"> <label>波斯尼亚 - 黑塞哥维那</label>
						</li>
						<li><input type="checkbox" name="" value="BG"> <label>保加利亚</label>
						</li>
						<li><input type="checkbox" name="" value="HR"> <label>克罗地亚共和国</label>
						</li>
						<li><input type="checkbox" name="" value="CY"> <label>塞浦路斯</label>
						</li>
						<li><input type="checkbox" name="" value="CZ"> <label>捷克共和国</label>
						</li>
						<li><input type="checkbox" name="" value="DK"> <label>丹麦</label>
						</li>
						<li><input type="checkbox" name="" value="EE"> <label>爱沙尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="FI"> <label>芬兰</label>
						</li>
						<li><input type="checkbox" name="" value="FR"> <label>法国</label>
						</li>
						<li><input type="checkbox" name="" value="DE"> <label>德国</label>
						</li>
						<li><input type="checkbox" name="" value="GI"> <label>直布罗陀</label>
						</li>
						<li><input type="checkbox" name="" value="GR"> <label>希腊</label>
						</li>
						<li><input type="checkbox" name="" value="GG"> <label>根西岛</label>
						</li>
						<li><input type="checkbox" name="" value="HU"> <label>匈牙利</label>
						</li>
						<li><input type="checkbox" name="" value="IS"> <label>冰岛</label>
						</li>
						<li><input type="checkbox" name="" value="IE"> <label>爱尔兰</label>
						</li>
						<li><input type="checkbox" name="" value="IT"> <label>意大利</label>
						</li>
						<li><input type="checkbox" name="" value="JE"> <label>泽西</label>
						</li>
						<li><input type="checkbox" name="" value="LV"> <label>拉托维亚</label>
						</li>
						<li><input type="checkbox" name="" value="LI"> <label>列支敦士登</label>
						</li>
						<li><input type="checkbox" name="" value="LT"> <label>立陶宛</label>
						</li>
						<li><input type="checkbox" name="" value="LU"> <label>卢森堡</label>
						</li>
						<li><input type="checkbox" name="" value="MK"> <label>马其顿</label>
						</li>
						<li><input type="checkbox" name="" value="MT"> <label>马耳他</label>
						</li>
						<li><input type="checkbox" name="" value="MD"> <label>摩尔多瓦</label>
						</li>
						<li><input type="checkbox" name="" value="MC"> <label>摩纳哥</label>
						</li>
						<li><input type="checkbox" name="" value="ME"> <label>黑山</label>
						</li>
						<li><input type="checkbox" name="" value="NL"> <label>荷兰</label>
						</li>
						<li><input type="checkbox" name="" value="NO"> <label>挪威</label>
						</li>
						<li><input type="checkbox" name="" value="PL"> <label>波兰</label>
						</li>
						<li><input type="checkbox" name="" value="PT"> <label>葡萄牙</label>
						</li>
						<li><input type="checkbox" name="" value="RO"> <label>罗马尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="RU"> <label>俄罗斯联邦</label>
						</li>
						<li><input type="checkbox" name="" value="SM"> <label>圣马力诺</label>
						</li>
						<li><input type="checkbox" name="" value="RS"> <label>塞尔维亚</label>
						</li>
						<li><input type="checkbox" name="" value="SK"> <label>斯洛伐克</label>
						</li>
						<li><input type="checkbox" name="" value="SI"> <label>斯洛文尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="ES"> <label>西班牙</label>
						</li>
						<li><input type="checkbox" name="" value="SJ"> <label>斯瓦尔巴岛和扬马延岛</label>
						</li>
						<li><input type="checkbox" name="" value="SE"> <label>瑞典</label>
						</li>
						<li><input type="checkbox" name="" value="CH"> <label>瑞士</label>
						</li>
						<li><input type="checkbox" name="" value="UA"> <label>乌克兰</label>
						</li>
						<li><input type="checkbox" name="" value="GB"> <label>联合王国</label>
						</li>
						<li><input type="checkbox" name="" value="VA"> <label>梵蒂冈</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Oceania"> <label>大洋洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="AS"> <label>美属萨摩亚</label>
						</li>
						<li><input type="checkbox" name="" value="AU"> <label>澳大利亚</label>
						</li>
						<li><input type="checkbox" name="" value="CK"> <label>库克群岛</label>
						</li>
						<li><input type="checkbox" name="" value="FJ"> <label>斐济</label>
						</li>
						<li><input type="checkbox" name="" value="PF"> <label>法属玻利尼西亚</label>
						</li>
						<li><input type="checkbox" name="" value="GU"> <label>关岛</label>
						</li>
						<li><input type="checkbox" name="" value="KI"> <label>基里巴斯</label>
						</li>
						<li><input type="checkbox" name="" value="MH"> <label>马绍尔群岛</label>
						</li>
						<li><input type="checkbox" name="" value="FM"> <label>密克罗尼西亚</label>
						</li>
						<li><input type="checkbox" name="" value="NR"> <label>瑙鲁</label>
						</li>
						<li><input type="checkbox" name="" value="NC"> <label>新喀里多尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="NZ"> <label>新西兰</label>
						</li>
						<li><input type="checkbox" name="" value="NU"> <label>纽埃</label>
						</li>
						<li><input type="checkbox" name="" value="PW"> <label>帕劳</label>
						</li>
						<li><input type="checkbox" name="" value="PG"> <label>巴布亚新几内亚</label>
						</li>
						<li><input type="checkbox" name="" value="SB"> <label>所罗门群岛</label>
						</li>
						<li><input type="checkbox" name="" value="TO"> <label>汤加</label>
						</li>
						<li><input type="checkbox" name="" value="TV"> <label>图瓦卢</label>
						</li>
						<li><input type="checkbox" name="" value="VU"> <label>瓦努阿图</label>
						</li>
						<li><input type="checkbox" name="" value="WF"> <label>瓦利斯和富图纳</label>
						</li>
						<li><input type="checkbox" name="" value="WS"> <label>西萨摩亚</label>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="center">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Asia"> <label>亚洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="AF"> <label>阿富汗</label>
						</li>
						<li><input type="checkbox" name="" value="AM"> <label>亚美尼亚</label>
						</li>
						<li><input type="checkbox" name="" value="AZ"> <label>阿塞拜疆共和国</label>
						</li>
						<li><input type="checkbox" name="" value="BD"> <label>孟加拉</label>
						</li>
						<li><input type="checkbox" name="" value="BT"> <label>不丹</label>
						</li>
						<li><input type="checkbox" name="" value="CN"> <label>中国</label>
						</li>
						<li><input type="checkbox" name="" value="GE"> <label>格鲁吉亚</label>
						</li>
						<li><input type="checkbox" name="" value="IN"> <label>印度</label>
						</li>
						<li><input type="checkbox" name="" value="JP"> <label>日本</label>
						</li>
						<li><input type="checkbox" name="" value="KZ"> <label>哈萨克斯坦</label>
						</li>
						<li><input type="checkbox" name="" value="KR"> <label>南韩</label>
						</li>
						<li><input type="checkbox" name="" value="KG"> <label>吉尔吉斯</label>
						</li>
						<li><input type="checkbox" name="" value="MV"> <label>马尔代夫</label>
						</li>
						<li><input type="checkbox" name="" value="MN"> <label>蒙古</label>
						</li>
						<li><input type="checkbox" name="" value="NP"> <label>尼泊尔</label>
						</li>
						<li><input type="checkbox" name="" value="PK"> <label>巴基斯坦</label>
						</li>
						<li><input type="checkbox" name="" value="RU"> <label>俄罗斯联邦</label>
						</li>
						<li><input type="checkbox" name="" value="LK"> <label>斯里兰卡</label>
						</li>
						<li><input type="checkbox" name="" value="TJ"> <label>塔吉克斯坦</label>
						</li>
						<li><input type="checkbox" name="" value="TM"> <label>土库曼斯坦</label>
						</li>
						<li><input type="checkbox" name="" value="UZ"> <label>乌兹别克斯坦</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Middle East"> <label>中东</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="BH"> <label>巴林</label>
						</li>
						<li><input type="checkbox" name="" value="IQ"> <label>伊拉克</label>
						</li>
						<li><input type="checkbox" name="" value="IL"> <label>以色列</label>
						</li>
						<li><input type="checkbox" name="" value="JO"> <label>约旦</label>
						</li>
						<li><input type="checkbox" name="" value="KW"> <label>科威特</label>
						</li>
						<li><input type="checkbox" name="" value="LB"> <label>黎巴嫩</label>
						</li>
						<li><input type="checkbox" name="" value="OM"> <label>阿曼</label>
						</li>
						<li><input type="checkbox" name="" value="QA"> <label>卡塔尔</label>
						</li>
						<li><input type="checkbox" name="" value="SA"> <label>沙特阿拉伯</label>
						</li>
						<li><input type="checkbox" name="" value="TR"> <label>土耳其</label>
						</li>
						<li><input type="checkbox" name="" value="AE"> <label>阿拉伯联合酋长国</label>
						</li>
						<li><input type="checkbox" name="" value="YE"> <label>也门</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Southeast Asia"> <label>东南亚</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="BN"> <label>文莱达鲁萨兰国</label>
						</li>
						<li><input type="checkbox" name="" value="KH"> <label>柬埔寨</label>
						</li>
						<li><input type="checkbox" name="" value="HK"> <label>香港</label>
						</li>
						<li><input type="checkbox" name="" value="ID"> <label>印度尼西亚</label>
						</li>
						<li><input type="checkbox" name="" value="LA"> <label>老挝</label>
						</li>
						<li><input type="checkbox" name="" value="MO"> <label>澳门</label>
						</li>
						<li><input type="checkbox" name="" value="MY"> <label>马来西亚</label>
						</li>
						<li><input type="checkbox" name="" value="PH"> <label>菲律宾</label>
						</li>
						<li><input type="checkbox" name="" value="SG"> <label>新加坡</label>
						</li>
						<li><input type="checkbox" name="" value="TW"> <label>台湾</label>
						</li>
						<li><input type="checkbox" name="" value="TH"> <label>泰国</label>
						</li>
						<li><input type="checkbox" name="" value="VN"> <label>越南</label>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Central America and Caribbean">
					<label>中美洲和加勒比海</label> <span>[<a class="showAll"
						href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="AI"> <label>安圭拉</label>
						</li>
						<li><input type="checkbox" name="" value="AG"> <label>安提瓜及巴布达</label>
						</li>
						<li><input type="checkbox" name="" value="AW"> <label>阿鲁巴</label>
						</li>
						<li><input type="checkbox" name="" value="BS"> <label>巴哈马</label>
						</li>
						<li><input type="checkbox" name="" value="BB"> <label>巴巴多斯</label>
						</li>
						<li><input type="checkbox" name="" value="BZ"> <label>伯利兹</label>
						</li>
						<li><input type="checkbox" name="" value="VG"> <label>英属维尔京群岛</label>
						</li>
						<li><input type="checkbox" name="" value="KY"> <label>开曼群岛</label>
						</li>
						<li><input type="checkbox" name="" value="CR"> <label>哥斯达黎加</label>
						</li>
						<li><input type="checkbox" name="" value="DM"> <label>多米尼加</label>
						</li>
						<li><input type="checkbox" name="" value="DO"> <label>多米尼加共和国</label>
						</li>
						<li><input type="checkbox" name="" value="SV"> <label>萨尔瓦多</label>
						</li>
						<li><input type="checkbox" name="" value="GD"> <label>格林纳达</label>
						</li>
						<li><input type="checkbox" name="" value="GP"> <label>瓜德罗普岛</label>
						</li>
						<li><input type="checkbox" name="" value="GT"> <label>危地马拉</label>
						</li>
						<li><input type="checkbox" name="" value="HT"> <label>海地</label>
						</li>
						<li><input type="checkbox" name="" value="HN"> <label>洪都拉斯</label>
						</li>
						<li><input type="checkbox" name="" value="JM"> <label>牙买加</label>
						</li>
						<li><input type="checkbox" name="" value="MQ"> <label>马提尼克</label>
						</li>
						<li><input type="checkbox" name="" value="MS"> <label>蒙特塞拉特</label>
						</li>
						<li><input type="checkbox" name="" value="AN"> <label>荷属安的列斯</label>
						</li>
						<li><input type="checkbox" name="" value="NI"> <label>尼加拉瓜</label>
						</li>
						<li><input type="checkbox" name="" value="PA"> <label>巴拿马</label>
						</li>
						<li><input type="checkbox" name="" value="PR"> <label>波多黎各</label>
						</li>
						<li><input type="checkbox" name="" value="KN"> <label>圣基茨 - 尼维斯</label>
						</li>
						<li><input type="checkbox" name="" value="LC"> <label>圣卢西亚</label>
						</li>
						<li><input type="checkbox" name="" value="VC"> <label>圣文森特和格林纳丁斯</label>
						</li>
						<li><input type="checkbox" name="" value="TT"> <label>特立尼达和多巴哥</label>
						</li>
						<li><input type="checkbox" name="" value="TC"> <label>特克斯和凯科斯群岛</label>
						</li>
						<li><input type="checkbox" name="" value="VI"> <label>美属维京群岛</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="North America"> <label>北美洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="BM"> <label>百慕大</label>
						</li>
						<li><input type="checkbox" name="" value="CA"> <label>加拿大</label>
						</li>
						<li><input type="checkbox" name="" value="GL"> <label>格陵兰</label>
						</li>
						<li><input type="checkbox" name="" value="MX"> <label>墨西哥</label>
						</li>
						<li><input type="checkbox" name="" value="PM"> <label>圣皮埃尔和密克隆</label>
						</li>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="South America"> <label>南美洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						<li><input type="checkbox" name="" value="AR"> <label>阿根廷</label>
						</li>
						<li><input type="checkbox" name="" value="BO"> <label>玻利维亚</label>
						</li>
						<li><input type="checkbox" name="" value="BR"> <label>巴西</label>
						</li>
						<li><input type="checkbox" name="" value="CL"> <label>智利</label>
						</li>
						<li><input type="checkbox" name="" value="CO"> <label>哥伦比亚</label>
						</li>
						<li><input type="checkbox" name="" value="EC"> <label>厄瓜多尔</label>
						</li>
						<li><input type="checkbox" name="" value="FK"> <label>福克兰群岛（马尔维那斯群岛）</label>
						</li>
						<li><input type="checkbox" name="" value="GF"> <label>法属圭亚那</label>
						</li>
						<li><input type="checkbox" name="" value="GY"> <label>圭亚那</label>
						</li>
						<li><input type="checkbox" name="" value="PY"> <label>巴拉圭</label>
						</li>
						<li><input type="checkbox" name="" value="PE"> <label>秘鲁</label>
						</li>
						<li><input type="checkbox" name="" value="SR"> <label>苏里南</label>
						</li>
						<li><input type="checkbox" name="" value="UY"> <label>乌拉圭</label>
						</li>
						<li><input type="checkbox" name="" value="VE"> <label>委内瑞拉</label>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="showSelected">
		<span class="noSelected">您尚未选择国家或地区</span> <span class="selected"
			style="display: none;">您已经选择的国家或地区:&nbsp;&nbsp;&nbsp; <span id="noShip"></span>
			[<a class="cancelSelected" href="javascript:void(0);">取消已选</a>]
		</span>
	</div>
</div>