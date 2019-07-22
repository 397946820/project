package com.it.ocs.synchronou.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.it.ocs.synchronou.model.MemberMessageInfoModel;
import com.it.ocs.synchronou.model.MessageInfoModel;
import com.it.ocs.upload.vo.FileVO;

public interface IEbayMessageDao {

	public int isExist(String messageId);

	public void insertData(Map<String, Object> map);

	public void updateData(Map<String, Object> map);

	public void updateMessageText(Map<String, Object> message);

	public List<MessageInfoModel> queryByPage(@Param("param") Map<String, Object> map, @Param("startRow") int startRow,
			@Param("endRow") int endRow);

	public int count(@Param("param") Map<String, Object> map);

	public int remark(Map<String, Object> message);

	public int isExistForMember(String messageId);

	public void updateMemberData(Map<String, Object> map);

	public void insertMemberData(Map<String, Object> map);

	public List<MemberMessageInfoModel> memberMessageQueryByPage(@Param("param") Map<String, Object> map,
			@Param("startRow") int startRow, @Param("endRow") int endRow);

	public int memberMessageCount(@Param("param") Map<String, Object> map);

	public int memberRemark(Map<String, Object> messageModel);

	public List<MemberMessageInfoModel> getOldQuestion(String id);

	public void addAnswer(Map<String, Object> amswerModel);

	public void updateRead(@Param("id") String id, @Param("read") Integer read);

	public MemberMessageInfoModel memberMessageById(Long id);

	public void addAskInfo(Map<String, Object> messages);

	public void updateOcsReadStatus(@Param("ids") List<String> ids, @Param("ocsRead") String ocsStatus);

	public String getMessageBody(String messageId);

	public FileVO getImgById(String imgId);

}
