<@FTL.admin id="paymentManage" title="支付管理" add_script_files=['admin/synchronou/paymentManage.js']>
	<!-- <div id="paypal-button-container"></div> -->
<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">
<input type="hidden" name="cmd" value="_xclick">
<input type="hidden" name="business" value="785833476@qq.com">
<input type="hidden" name="item_name"
value="Item Name Goes Here">
<input type="hidden" name="item_number"
value="Item Number Goes Here">
<input type="hidden" name="amount" value="100.00">
<input type="hidden" name="no_shipping" value="2">
<input type="hidden" name="no_note" value="1">
<input type="hidden" name="currency_code" value="USD">
<input type="hidden" name="bn" value="IC_Sample">
<input type="image" src="https://www.paypal.com/
en_US/i/btn/x-click-but23.gif"
name="submit" alt="Make payments with payPal - it's fast,
free and secure!">
<img alt=""
src="https://www.paypal.com/en_US/i/scr/pixel.gif"
width="1" height="1">
</form>
</@FTL.admin>