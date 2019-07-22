package com.it.ocs.upload.dao;

import java.util.Map;

import com.it.ocs.common.OperationResult;
import com.it.ocs.upload.vo.FileVO;

public interface IUploadDao {

	public long getImgId();

	public void addEbayImages(FileVO fileVO);

	public void updateEbayImageInfo(FileVO fileVO);

	public String getImgPathById(long id);

	public void updateUploadResult(FileVO fileVO);

	public void updateImageInfo(FileVO image);

	public void imageDelete(String[] idsArry);

}
