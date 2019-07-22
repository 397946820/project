//package com.it.ocs.pic.service.impl;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.commons.fileupload.disk.DiskFileItem;
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.ebay.soap.eBLBaseComponents.PictureSetMemberType;
//import com.ebay.soap.eBLBaseComponents.SiteHostedPictureDetailsType;
//import com.google.common.collect.Lists;
//import com.it.ocs.common.BeanConvertUtil;
//import com.it.ocs.common.OperationResult;
//import com.it.ocs.common.RequestParam;
//import com.it.ocs.common.ResponseResult;
//import com.it.ocs.common.service.BaseService;
//import com.it.ocs.common.support.IAction;
//import com.it.ocs.common.support.IFunction;
//import com.it.ocs.common.util.CollectionUtil;
//import com.it.ocs.ebaySales.model.EBayBestOffersModel;
//import com.it.ocs.ebaySales.vo.BestOffersVo;
//import com.it.ocs.pic.dao.IPicDAO;
//import com.it.ocs.pic.model.PicCategoryModel;
//import com.it.ocs.pic.model.PicModel;
//import com.it.ocs.pic.service.IPicCategoryInnerService;
//import com.it.ocs.pic.service.IPicService;
//import com.it.ocs.pic.util.ImageUtil;
//import com.it.ocs.pic.vo.PicVO;
//import com.it.ocs.publication.model.PublicationModel;
//import com.it.ocs.publication.vo.PublicationVO;
//import com.it.ocs.synchronou.service.IEBayUploadSiteHostedPicturesService;
//import com.it.ocs.synchronou.util.ObjectAndJsonUtil;
//import com.it.ocs.synchronou.util.ValidationUtil;
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//
//@Service
//public class PicService extends BaseService implements IPicService {
//	private static final Logger log = Logger.getLogger(PicService.class);
//	//private final String IMAGE_PATH = "D:/images";
//	private final String IMAGE_PATH = "/home/oracle/OCS_TEST_FILE/Image";
//	
//	private final String SERVCE_PATH="https://www.lightingimg.com/javaimg/";
//	
//	@Autowired
//	private IPicDAO picDAO;
//	@Autowired
//	private IPicCategoryInnerService categoryInnerService;
//    @Autowired 
//    private IEBayUploadSiteHostedPicturesService uploadSiteHostPictures;
//	@Override
//	public PicVO getPicRealUrl(PicModel picModel,String ebayAccount) {
//		PicVO picVO = new PicVO();
//		picVO.setId(picModel.getId());
//		
//		SiteHostedPictureDetailsType req = uploadSiteHostPictures.uploadSitePictures(picModel.getServerUrl(),ebayAccount);
//	    if(ValidationUtil.isNull(req)){
//	    	picVO.setName(picModel.getName());
//	    	return picVO;
//	    }
//		picVO.setRealUrl(req.getFullURL());
//	    PictureSetMemberType[] pictureSetMemberTypes = req.getPictureSetMember();
//	    StringBuffer memberUrl = new StringBuffer();
//	    List<Map<String, String>> lists = Lists.newArrayList();
//	    for (PictureSetMemberType pictureSetMemberType : pictureSetMemberTypes) {
//	    	Map<String, String> map = new HashMap<>();
//	    	map.put("MemberURL",pictureSetMemberType.getMemberURL());
//	    	map.put("PictureHeight",pictureSetMemberType.getPictureHeight().toString());
//	    	map.put("PictureWidth",pictureSetMemberType.getPictureWidth().toString());
//	    	lists.add(map);
//	    }
//	    String realUrlInfo = ObjectAndJsonUtil.MapsToJsonArray(lists, null);
//	    picVO.setRealUrlInfo(realUrlInfo);
//	    picModel=picVO;
//	    picDAO.updateUrlById(picModel);
//		return picVO;
//	}
//	@Override
//	public ResponseResult<PicVO> getCategoryPicList(RequestParam param) {
//		ResponseResult<PicVO> result = new ResponseResult<>();
//		Map<String, Object> conditionMap = param.getParam();
//		List<PicModel> pics = picDAO.queryByPage(conditionMap, param.getStartRow(), param.getEndRow());
//
//		Integer count = picDAO.count(conditionMap);
//		List<PicVO> picVOs = Lists.newArrayList();
//		convertList(pics,picVOs);
//		result.setRows(picVOs);
//		result.setTotal(count);
//		return result;
//	}
//	@Override
//	public ResponseResult<PicVO> queryPic(RequestParam param) {
//		ResponseResult<PicVO> result = new ResponseResult<>();
//		Map<String, Object> conditionMap = param.getParam();
//		List<PicModel> pics = picDAO.queryByPage(conditionMap, param.getStartRow(), param.getEndRow());
//		Integer count = picDAO.count(conditionMap);
//		List<PicVO> picVOs = Lists.newArrayList();
//		convertList(pics, picVOs);
//		for (PicVO picVO : picVOs) {
//			String path = picVO.getUrl();
//			picVO.setUrl(ImageUtil.getImageBinary(path));
//			picVO.setImgType(path.substring(path.indexOf(".") + 1 , path.length()));
//
//		}
//		result.setRows(picVOs);
//		result.setTotal(count);
//		return result;
//	}
//	private void convertList(List<PicModel> source, final List<PicVO> target) {
//		CollectionUtil.each(source, new IAction<PicModel>() {
//			@Override
//			public void excute(PicModel obj) {
//				PicVO picVO = new PicVO();
//				BeanUtils.copyProperties(obj, picVO);
//				target.add(picVO);
//			}
//		});
//	}
//	@Override
//	public OperationResult savePic(PicVO picVO) {
//		OperationResult result = new OperationResult();
//		try {
//			MultipartFile file = picVO.getFile();
//			if(file.getSize()==0){
//			}else{
//				deleteFile(picVO.getUrl());
//				String iFileName = file.getOriginalFilename();
//				String fileName = StringUtils.isEmpty(iFileName)?picVO.getName():iFileName;
//				if (ValidationUtil.isNull(picVO.getName())) {
//					picVO.setName(iFileName);
//				}
//				fileName=fileName.replace(".", System.currentTimeMillis()+".");
//				InputStream iStream = file.getInputStream();
//				OperationResult result2 = uploadImgService(fileName,file.getInputStream());
//				if(result2.getErrorCode()==1){
//					result.setErrorCode(1);
//					result.setDescription("save error");
//					return result;
//				}
//				saveFileFromInputStream(iStream, IMAGE_PATH, fileName);
//				String servcePath = SERVCE_PATH+fileName;
//				System.out.println(servcePath);
//				picVO.setServerUrl(servcePath);
//				picVO.setRealName(fileName);
//				picVO.setUrl(IMAGE_PATH + "/" + fileName);
//			}
//			
//			
//			if (null != picVO.getId()) {
//				String url = picVO.getUrl();
//				if(url.length()>350){
//					picVO.setUrl("");
//				}
//				PicModel picModel= picVO;
//				picDAO.update(picModel);
//			} else {
//				Long id = picDAO.getSeq();
//				picVO.setId(id);
//				insertInit(picVO);
//				picDAO.add(picVO);
//				result.setId(id);
//			}
//			result.setData(picVO.getUrl());
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("save error");
//			log.error(e);
//		}
//
//		return result;
//	}
//
//	@Override
//	public OperationResult removePic(Long picId) {
//		OperationResult result = new OperationResult();
//		try {
//			picDAO.delete(picId);
//		} catch (Exception e) {
//			result.setErrorCode(1);
//			result.setDescription("remove error");
//			e.printStackTrace();
//		}
//		return result;
//	}
//	public static boolean deleteFile(String fileName){
//	        File file = new File(fileName);
//	        if(file.isFile() && file.exists()){
//	            Boolean succeedDelete = file.delete();
//	            if(succeedDelete){
//	                return true;
//	            }
//	            else{
//	                return true;
//	            }
//	        }else{
//	            return false;
//	        }
//	    }
//	private void saveFileFromInputStream(InputStream stream, String path, String fileName) throws IOException {
//		File file = new File(path);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		FileOutputStream fs = new FileOutputStream(path + "/" + fileName);
//		byte[] buffer = new byte[1024 * 1024];
//		int bytesum = 0;
//		int byteread = 0;
//		while ((byteread = stream.read(buffer)) != -1) {
//			bytesum = bytesum + byteread;
//			fs.write(buffer, 0, byteread);
//			fs.flush();
//		}
//		fs.close();
//		stream.close();
//	}
//	private void setCategoryName(List<PicVO> pics) {
//		if (!CollectionUtil.isNullOrEmpty(pics)) {
//			List<Long> categoryIds = Lists.newArrayList();
//			CollectionUtil.each(pics, new IAction<PicVO>() {
//				@Override
//				public void excute(PicVO obj) {
//					if (null != obj.getCategoryId() && !categoryIds.contains(obj.getCategoryId())) {
//						categoryIds.add(obj.getCategoryId());
//					}
//				}
//			});
//			List<PicCategoryModel> models = categoryInnerService.findByIds(categoryIds);
//			CollectionUtil.each(pics, new IAction<PicVO>() {
//				@Override
//				public void excute(PicVO obj) {
//					PicCategoryModel categoryModel = CollectionUtil.search(models, new IFunction<PicCategoryModel, Boolean>() {
//						@Override
//						public Boolean excute(PicCategoryModel category) {
//							return category.getId() == obj.getCategoryId() || category.getId().equals(obj.getCategoryId());
//						}
//					});
//					if (null != categoryModel) {
//						obj.setCategoryName(categoryModel.getName());
//					}
//				}
//			});
//		}
//	}
//	@Override
//	public PicModel queryById(Long id) {
//		PicModel picModel = picDAO.queryById(id);
//		String path = picModel.getUrl();
//		
//		picModel.setUrl(ImageUtil.getImageBinary(path));
//		picModel.setImgType(path.substring(path.indexOf(".") + 1 , path.length()));
//		return picModel;
//	}
//	@Override
//	public List<PicVO> getPicRealUrls(String ids, String ebayAccount) {
//		List<PicVO> picVOs = Lists.newArrayList();
//		List<PicModel> picModels = queryByIds(ids);
//		for (PicModel picModel : picModels) {
//			 picVOs.add(this.getPicRealUrl(picModel, ebayAccount));
//		}
//		return picVOs;
//	}
//	@Override
//	public List<PicModel> queryByIds(String ids) {
//		return picDAO.queryByIds(ids);
//	}
//	private static  OperationResult uploadImgService(String imgName,InputStream instream){
//		OperationResult result = new OperationResult();
//		String HOST_NAME = "144.217.149.37";// ftp服务器地址
//
//		Integer PORT = 22; // SFTP默认端口
//
//		String USER_NAME = "ebayimage";
//
//		String PASS_WORD = "RpL!hxGKX%";
//		ChannelSftp sftp = null;
//		Channel channel = null;
//		Session sshSession = null;
//		try {
//				JSch jsch = new JSch();
//				sshSession = jsch.getSession(USER_NAME, HOST_NAME, PORT);
//				sshSession.setPassword(PASS_WORD);
//				Properties sshConfig = new Properties();
//				sshConfig.put("StrictHostKeyChecking", "no");
//				sshSession.setConfig(sshConfig);
//				sshSession.connect();
//				channel = sshSession.openChannel("sftp");
//				channel.connect();
//				sftp = (ChannelSftp) channel;
//				sftp.cd("javaimg");
//			OutputStream outstream = sftp.put(imgName);
//			
//			 byte b[] = new byte[1024];
//			    int n;
//			    while ((n = instream.read(b)) != -1) {  
//			        outstream.write(b, 0, n);
//			    }  
//			    outstream.flush();  
//			    outstream.close();  
//			    instream.close();  
//		} catch (Exception e) {
//			log.error(e);
//			result.setErrorCode(1);
//			e.printStackTrace();
//		} finally {
//			closeChannel(sftp);
//			closeChannel(channel);
//			closeSession(sshSession);
//		}
//		return result;
//	}
//	private static void closeChannel(Channel channel) {
//		if (channel != null) {
//			if (channel.isConnected()) {
//				channel.disconnect();
//			}
//		}
//	}
//
//	private static void closeSession(Session session) {
//		if (session != null) {
//			if (session.isConnected()) {
//				session.disconnect();
//			}
//		}
//	}
//	
//
//}
