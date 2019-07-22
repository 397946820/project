package com.it.ocs.upload.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.it.ocs.common.OperationResult;
import com.it.ocs.common.service.BaseService;
import com.it.ocs.task.core.TaskExecutorUtil;
import com.it.ocs.task.core.TaskRunnable;
import com.it.ocs.upload.core.JSCHImageUploadSupport;
import com.it.ocs.upload.dao.IUploadDao;
import com.it.ocs.upload.service.IUploadService;
import com.it.ocs.upload.vo.FileVO;

@Service
public class UploadService extends BaseService implements IUploadService {

	private final static Logger log = Logger.getLogger(UploadService.class);

	private final static String EBAY_IMAGE_BASE_PATH = "/ocs/images/ebay/";

	public final static String SERVCE_PATH = "https://www.lightingimg.com/javaimg/";
	
	@Autowired
	private IUploadDao iUploadDao;
	
	/**
	 * 上传图片同步的 
	 */
	@Override
	public List<FileVO> ebayImgSync(MultipartFile[] files) {
		List<FileVO> result = uploadImages(files);
		//异步线程上传图片服务器和ebay服务器
		uploadImageToServer(result);
		return result;
	}
	

	/**
	 * 上传图片异步的 
	 */
	@Override
	public List<FileVO> uploadEbayImgFile(MultipartFile[] files) {
		List<FileVO> result = uploadImages(files);
		//异步线程上传图片服务器和ebay服务器
		uploadImagesToServerAndEbay(result);
		return result;
	}

	private List<FileVO> uploadImages(MultipartFile[] files) {
		List<FileVO> result = new ArrayList<>();
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile multipartfile = files[i];
				OutputStream os = null;
				InputStream is = null;
				FileVO fileVO = new FileVO();
				try {
					fileVO.setUser(this.getCurentUser().getId());
					long id = iUploadDao.getImgId();
					fileVO.setId(id);
					String originalFilename = multipartfile.getOriginalFilename();
					fileVO.setName(originalFilename);
					fileVO.setSize(String.valueOf(multipartfile.getSize()));
					String saveName = System.currentTimeMillis()+ originalFilename.substring(originalFilename.lastIndexOf("."));
					fileVO.setServerName(saveName);
					String filePath = EBAY_IMAGE_BASE_PATH + saveName;
					fileVO.setSavePath(filePath);
					os = new FileOutputStream(filePath);
					is = multipartfile.getInputStream();
					byte[] bts = new byte[2048];
					while (is.read(bts) != -1) {
						os.write(bts);
					}
					os.flush();
					result.add(fileVO);
				} catch (Exception e) {
					log.error("ebay图片上传失败", e);
				} finally {
					if (null != os) {
						IOUtils.closeQuietly(os);
					}
					if (null != is) {
						IOUtils.closeQuietly(is);
					}
				}
				
				//插入数据库
				iUploadDao.addEbayImages(fileVO);
			}
		}
		return result;
	}

	private void uploadImagesToServerAndEbay(List<FileVO> files) {
		createUploadThread(files);
		//启动监听线程判断上传是否成功
		
	}
	private void createUploadThread(List<FileVO> files){
		TaskExecutorUtil.threadRun(new TaskRunnable() {
			@Override
			public void runTask() {
				uploadImageToServer(files);
			}
		});
	}
	
	@Override
	public OperationResult updateEbayImageInfo(FileVO fileVO) {
		OperationResult or = new OperationResult();
		iUploadDao.updateEbayImageInfo(fileVO);
		or.setData("success");
		return or;
	}

	@Override
	public void getImage(long id, HttpServletRequest request, HttpServletResponse response) {
		String path = iUploadDao.getImgPathById(id);
		FileInputStream fis = null;
		OutputStream out = null;
		response.setContentType("image/gif");
		try {
			out = response.getOutputStream(); // 通过response显示图片
			File file = new File(path); // 服务器上图片的路径
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			log.error("图片"+path+"已不存在或者被删除.");
		} finally {
			if (fis != null) {
				IOUtils.closeQuietly(fis);
			}
			if(null != out){
				IOUtils.closeQuietly(out);
			}
		}
		
	}

	@Override
	public OperationResult imageDelete(String ids) {
		String idsArry[] = ids.split(",");
		iUploadDao.imageDelete(idsArry);
		OperationResult or = new OperationResult();
		or.setData("success");
		return or;
	}

	private void uploadImageToServer(List<FileVO> files) {
		//建立服务器连接
		JSCHImageUploadSupport upload = JSCHImageUploadSupport.getInstance();
		
		for(FileVO fileVO :files){
			boolean f = upload.put(fileVO.getServerName(), fileVO.getSavePath());
			Map<String,String> map = new HashMap();
			if(f){
				fileVO.setIsUpload(1);
				fileVO.setServerUrl(SERVCE_PATH+fileVO.getServerName());
			}else{
				fileVO.setIsUpload(0);
			}
			iUploadDao.updateUploadResult(fileVO);
		}
		
		upload.disConnect();
	}

	
}
