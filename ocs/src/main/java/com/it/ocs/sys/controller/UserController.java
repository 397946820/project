package com.it.ocs.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.cache.UserCache;
import com.it.ocs.common.OperationResult;
import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.sys.service.IUserService;
import com.it.ocs.sys.vo.UserVO;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<UserVO> list(RequestParam param) {
		ResponseResult<UserVO> userPageResult = userService.queryUser(param);
		return userPageResult;
	}
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	@ResponseBody
	public boolean valUserExist(UserVO param) {
		return userService.existByParam(param);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult saveUser(UserVO param) {
		return userService.saveUser(param);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult removeUser(Long id) {
		return userService.removeUser(id);
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "admin/sys/user";
	}

	@RequestMapping(value = "/castRole", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult castRole(@RequestBody UserVO param) {
		return userService.castRole(param);
	}
	
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult resetPwd(Long id) {
		return userService.resetPwd(id);
	}

	@RequestMapping(value = "/doLogin")
	public String doLogin(String username, String password, String valCode, HttpServletRequest req) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			String verifyCode = (String) req.getSession().getAttribute("rand");
			if (!valCode.toLowerCase().trim().equals(verifyCode.toLowerCase().trim())) {
				req.getSession().setAttribute("loginMsg", "验证码错误");
				return "redirect:/login.jsp";
			}
			currentUser.login(token);
		} catch (Exception e) {
			req.getSession().setAttribute("loginMsg", "用户名或密码错误");
			return "redirect:/login.jsp";
		}
		return "redirect:/index";
	}
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult changePwd(String newPassword) {
		return userService.changePwd(newPassword);
	}
	@RequestMapping(value = "/valPwdIsTrue", method = RequestMethod.POST)
	@ResponseBody
	public boolean valPwdIsTrue(String password) {
		return userService.valPwdIsTrue(password);
	}
	@RequestMapping(value = "/doLogout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		UserCache.getAuthMap().clear();
		return "redirect:/login.jsp";
	}
	/**
	 * 根据当前用户部门查询部门用户,如果是系统管理员,查询所有用户
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<UserVO> query(RequestParam param) {
		ResponseResult<UserVO> userPageResult = userService.query(param);
		return userPageResult;
	}
	@RequestMapping(value = "/skuRelIndex", method = RequestMethod.GET)
	public String skuRelIndex() {
		return "admin/sys/skuRelationManage";
	}
}
