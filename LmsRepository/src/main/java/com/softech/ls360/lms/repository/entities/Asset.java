package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Asset extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String keywords;
	private String assetType;
	private List<AssetVersion> assetVersion;
	private String descripiton;
	private String fileName;
	private ContentOwner contentOwner;
	private String assetGuid;
	private String content;
	private Language language;
	private Integer numberOfCertificatePerPage;
	private Boolean active;
	private LocalDateTime createdDate;
	private VU360User createUserId;
	private LocalDateTime lastUpdatedDate;
	private VU360User lastUpdateUser;
	private String displayText1;
	private String displayText2;
	private String displayText3;
	private AffidavitTemplate affidavitTemplate;
	private String affidavitType;
	private String videoFileName;
	private String streamingServerApplication;
	private Integer startQueueHour;
	private Integer startQueueMinute;
	private Integer startQueueSecond;
	private Integer endQueueHour;
	private Integer endQueueminute;
	private Integer vidoeHieght;
	private Integer vidoeWidth;
	private Boolean videoFullScreen;
	private Long sizeInBytes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="asset")
	//@JoinColumn(name="CURRENT_ASSETVERSION_ID")
	public List<AssetVersion> getAssetVersion() {
		return assetVersion;
	}

	public void setAssetVersion(List<AssetVersion> assetVersion) {
		this.assetVersion = assetVersion;
	}

	public String getDescripiton() {
		return descripiton;
	}

	public void setDescripiton(String descripiton) {
		this.descripiton = descripiton;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTENTOWNER_ID")
	public ContentOwner getContentOwner() {
		return contentOwner;
	}

	public void setContentOwner(ContentOwner contentOwner) {
		this.contentOwner = contentOwner;
	}

	@Column(name="ASSET_GUID")
	public String getAssetGuid() {
		return assetGuid;
	}

	public void setAssetGuid(String assetGuid) {
		this.assetGuid = assetGuid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LANGUAGE_ID")
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Column(name="NUMOFCERTIFICATEPERPAGE")
	public Integer getNumberOfCertificatePerPage() {
		return numberOfCertificatePerPage;
	}

	public void setNumberOfCertificatePerPage(Integer numberOfCertificatePerPage) {
		this.numberOfCertificatePerPage = numberOfCertificatePerPage;
	}

	@Column(name="ACTIVETF")
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CreateUserId")
	public VU360User getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(VU360User createUserId) {
		this.createUserId = createUserId;
	}

	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LastUpdateUser")
	public VU360User getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(VU360User lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getDisplayText1() {
		return displayText1;
	}

	public void setDisplayText1(String displayText1) {
		this.displayText1 = displayText1;
	}

	public String getDisplayText2() {
		return displayText2;
	}

	public void setDisplayText2(String displayText2) {
		this.displayText2 = displayText2;
	}

	public String getDisplayText3() {
		return displayText3;
	}

	public void setDisplayText3(String displayText3) {
		this.displayText3 = displayText3;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AFFIDAVITTEMPLATE_ID")
	public AffidavitTemplate getAffidavitTemplate() {
		return affidavitTemplate;
	}

	public void setAffidavitTemplate(AffidavitTemplate affidavitTemplate) {
		this.affidavitTemplate = affidavitTemplate;
	}

	public String getAffidavitType() {
		return affidavitType;
	}

	public void setAffidavitType(String affidavitType) {
		this.affidavitType = affidavitType;
	}

	public String getVideoFileName() {
		return videoFileName;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}

	public String getStreamingServerApplication() {
		return streamingServerApplication;
	}

	public void setStreamingServerApplication(String streamingServerApplication) {
		this.streamingServerApplication = streamingServerApplication;
	}

	public Integer getStartQueueHour() {
		return startQueueHour;
	}

	public void setStartQueueHour(Integer startQueueHour) {
		this.startQueueHour = startQueueHour;
	}

	public Integer getStartQueueMinute() {
		return startQueueMinute;
	}

	public void setStartQueueMinute(Integer startQueueMinute) {
		this.startQueueMinute = startQueueMinute;
	}

	public Integer getStartQueueSecond() {
		return startQueueSecond;
	}

	public void setStartQueueSecond(Integer startQueueSecond) {
		this.startQueueSecond = startQueueSecond;
	}

	public Integer getEndQueueHour() {
		return endQueueHour;
	}

	public void setEndQueueHour(Integer endQueueHour) {
		this.endQueueHour = endQueueHour;
	}

	public Integer getEndQueueminute() {
		return endQueueminute;
	}

	public void setEndQueueminute(Integer endQueueminute) {
		this.endQueueminute = endQueueminute;
	}

	public Integer getVidoeHieght() {
		return vidoeHieght;
	}

	public void setVidoeHieght(Integer vidoeHieght) {
		this.vidoeHieght = vidoeHieght;
	}

	public Integer getVidoeWidth() {
		return vidoeWidth;
	}

	public void setVidoeWidth(Integer vidoeWidth) {
		this.vidoeWidth = vidoeWidth;
	}

	@Column(name="VIDEOFULLSCREENTF")
	public Boolean getVideoFullScreen() {
		return videoFullScreen;
	}

	public void setVideoFullScreen(Boolean videoFullScreen) {
		this.videoFullScreen = videoFullScreen;
	}

	public Long getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(Long sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

}
