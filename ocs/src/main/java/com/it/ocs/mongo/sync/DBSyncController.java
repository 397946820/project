package com.it.ocs.mongo.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ocs.common.RequestParam;
import com.it.ocs.common.ResponseResult;
import com.it.ocs.mongo.sync.service.IDBSyncService;
import com.it.ocs.sys.vo.UserVO;

@Controller
@RequestMapping(value = "/dbsync")
public class DBSyncController {
	private IDBSyncService dbsyncService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<UserVO> list(RequestParam param) {
		ResponseResult<UserVO> userPageResult = null;
		return userPageResult;
	}
}
