<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<style type="text/css">
.STYLE1 {
	color: #00FFFF;
	font-weight: bold;
}

input {
	width: 120px;
}
</style>

		<title>LE后台管理系统</title>
		<script>
	function makeimage() {
		document.getElementById("check_image").src = "<%=request.getContextPath()%>/verify/verifyCode?rnd"+Math.random();

	}
	function val(){
		if(document.myForm.username.value==""){
			alert("用户名不能为空");
			return false;
		}
		if(document.myForm.password.value==""){
			alert("密码不能为空");
			return false;
		}
		if(document.myForm.valCode.value==""){
			alert("验证码不能为空");
			return false;
		}
        return true;

	}
</script>

	</head>

	<body style="background-color: #CDD7ED;" onload="makeimage()">

		<div
			style="width: 100%; height: 100%; padding: 0px; margin: 0px; padding-top: 250px; font-size: 12px;">
			<form name="myForm" method="post"
				action="<%=request.getContextPath()%>/user/doLogin" onsubmit="return val()">
				<table align="center" style="text-align: center" cellpadding="0"
					height="190" width="519" border="0" cellspacing="0"
					background="images/bg.gif">
					<tr>
						<td colspan="4" height="34" background="images/bg.jpg"
							align="center">
							<span class="STYLE1">LE后台管理系统</span>
						</td>
					</tr>
					<tr>
						<td width="106" align="center">
							用户名：
						</td>
						<td align="center">
							<input type="text" name="username" id="username" />
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							密 码：
						</td>
						<td align="center">
							<input type="password" name="password" id="password" />
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td align="center">
							验证码
						</td>
						<td width="209" align="center">
							<input type="text" name="valCode" id="valCode" />
						</td>
						<td align="left" width="90">
							<img id="check_image" alt="" src="" />
						</td>
						<td align="left" width="104">
							<a href="#" onclick=makeimage();>看不清，换一张</a>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center" style="padding-right: 50px;">
							<input type="submit" value="登　录" />
						</td>
					</tr>

					<tr>
						<td colspan="4" align="left" style="color: red; font-size: 13px;">
							${loginMsg } &nbsp;
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
