package com.qiluomite.mywechat.bean;

public class Msg {

	private String msgId;
	private String fromUserName;
	private String toUserName;
	private Integer msgType;
	private String content;
	private Integer status;
	private Integer imgStatus;
	private Integer createTime;
	private Integer voiceLength;
	private Integer playLength;
	private String fileName;
	private String fileSize;
	private String mediaId;
	private String url;
	private Integer appMsgType;
	private Integer statusNotifyCode;
	private String statusNotifyUserName;
	private String recommendInfo;

	public String getMsgId() {
		return msgId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public String getContent() {
		return content;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getImgStatus() {
		return imgStatus;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public Integer getVoiceLength() {
		return voiceLength;
	}

	public Integer getPlayLength() {
		return playLength;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getUrl() {
		return url;
	}

	public Integer getAppMsgType() {
		return appMsgType;
	}

	public Integer getStatusNotifyCode() {
		return statusNotifyCode;
	}

	public String getStatusNotifyUserName() {
		return statusNotifyUserName;
	}

	public String getRecommendInfo() {
		return recommendInfo;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setImgStatus(Integer imgStatus) {
		this.imgStatus = imgStatus;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public void setVoiceLength(Integer voiceLength) {
		this.voiceLength = voiceLength;
	}

	public void setPlayLength(Integer playLength) {
		this.playLength = playLength;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAppMsgType(Integer appMsgType) {
		this.appMsgType = appMsgType;
	}

	public void setStatusNotifyCode(Integer statusNotifyCode) {
		this.statusNotifyCode = statusNotifyCode;
	}

	public void setStatusNotifyUserName(String statusNotifyUserName) {
		this.statusNotifyUserName = statusNotifyUserName;
	}

	public void setRecommendInfo(String recommendInfo) {
		this.recommendInfo = recommendInfo;
	}

	// {"UserName":"","NickName":"","QQNum":0,"Province":"","City":"","Content":"","Signature":"","Alias":"","Scene":0,"VerifyFlag":0,"AttrStatus":0,"Sex":0,"Ticket":"","OpCode":0},
	// "ForwardFlag":0,"AppInfo":{"AppID":"","Type":0},"HasProductId":0,"Ticket":"","ImgHeight":0,"ImgWidth":0,"SubMsgType":0,"NewMsgId":2692073074812973378,"OriContent":""}

}
