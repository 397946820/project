package com.it.ocs.upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.OperationResult;
import com.it.ocs.upload.core.Progress;
import com.it.ocs.upload.service.IUploadService;
import com.it.ocs.upload.vo.FileVO;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private IUploadService iUploadService;

	/**
	 * 获取进度条状态
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProcessStatus")
	public Progress getProcessStatus(HttpSession session) {
		return (Progress) session.getAttribute("status");
	}

	@RequestMapping("/ebayImg")
	@ResponseBody
	public List<FileVO> uploadEbayImgFile(@RequestParam(value = "file", required = false) MultipartFile[] files)
			throws IOException {
		return iUploadService.uploadEbayImgFile(files);
	}
	@RequestMapping("/ebayImgSync")
	@ResponseBody
	public List<FileVO> ebayImgSync(@RequestParam(value = "file", required = false) MultipartFile[] files)
			throws IOException {
		return iUploadService.ebayImgSync(files);
	}

	@RequestMapping(value = "/updateEbayImageInfo")
	@ResponseBody
	public OperationResult updateEbayImageInfo(@RequestBody FileVO fileVO) {
		return iUploadService.updateEbayImageInfo(fileVO);
	}

	@RequestMapping(value = "/image/{id}")
	public void getImage(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
		iUploadService.getImage(id,request,response);
	}
	
	@RequestMapping(value = "/imageDelete")
	@ResponseBody
	public OperationResult imageDelete(String ids) {
		return iUploadService.imageDelete(ids);
	}

}
