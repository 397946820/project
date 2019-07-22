package com.it.ocs.upload.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.OperationResult;
import com.it.ocs.upload.vo.FileVO;

public interface IUploadService {

	public List<FileVO> uploadEbayImgFile(MultipartFile[] files);

	public OperationResult updateEbayImageInfo(FileVO fileVO);

	public void getImage(long id, HttpServletRequest request, HttpServletResponse response);

	public OperationResult imageDelete(String ids);

	public List<FileVO> ebayImgSync(MultipartFile[] files);

}
