package com.softech.ls360.api.gateway.service.model.response;

import java.time.LocalDateTime;
import java.util.Objects;



/**
 * Learner&#39;s Course Details
 */


public class LearnerCourseStatisticsResponse   {
	
	
	public LocalDateTime getFirstAccessDate() {
		return firstAccessDate;
	}

	public void setFirstAccessDate(LocalDateTime firstAccessDate) {
		this.firstAccessDate = firstAccessDate;
	}

	public LocalDateTime getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(LocalDateTime lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public int getLaunchesOccrued() {
		return launchesOccrued;
	}

	public void setLaunchesOccrued(int launchesOccrued) {
		this.launchesOccrued = launchesOccrued;
	}

	public LocalDateTime getPreTestDate() {
		return preTestDate;
	}

	public void setPreTestDate(LocalDateTime preTestDate) {
		this.preTestDate = preTestDate;
	}

	public double getPretestScore() {
		return pretestScore;
	}

	public void setPretestScore(double pretestScore) {
		this.pretestScore = pretestScore;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}

	public double getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(double percentComplete) {
		this.percentComplete = percentComplete;
	}

	public double getLowestPostTestScore() {
		return lowestPostTestScore;
	}

	public void setLowestPostTestScore(double lowestPostTestScore) {
		this.lowestPostTestScore = lowestPostTestScore;
	}

	public double getAveragePostTestScore() {
		return averagePostTestScore;
	}

	public void setAveragePostTestScore(double averagePostTestScore) {
		this.averagePostTestScore = averagePostTestScore;
	}

	public double getHighestPostTestScore() {
		return highestPostTestScore;
	}

	public void setHighestPostTestScore(double highestPostTestScore) {
		this.highestPostTestScore = highestPostTestScore;
	}

	public LocalDateTime getFirstPostTestDate() {
		return firstPostTestDate;
	}

	public void setFirstPostTestDate(LocalDateTime firstPostTestDate) {
		this.firstPostTestDate = firstPostTestDate;
	}

	public LocalDateTime getLastPostTestDate() {
		return lastPostTestDate;
	}

	public void setLastPostTestDate(LocalDateTime lastPostTestDate) {
		this.lastPostTestDate = lastPostTestDate;
	}

	public int getNumberPostTestsTaken() {
		return numberPostTestsTaken;
	}

	public void setNumberPostTestsTaken(int numberPostTestsTaken) {
		this.numberPostTestsTaken = numberPostTestsTaken;
	}

	public double getLowestQuizScore() {
		return lowestQuizScore;
	}

	public void setLowestQuizScore(double lowestQuizScore) {
		this.lowestQuizScore = lowestQuizScore;
	}

	public double getAverageQuizScore() {
		return averageQuizScore;
	}

	public void setAverageQuizScore(double averageQuizScore) {
		this.averageQuizScore = averageQuizScore;
	}

	public double getHighestQuizScore() {
		return highestQuizScore;
	}

	public void setHighestQuizScore(double highestQuizScore) {
		this.highestQuizScore = highestQuizScore;
	}

	public double getNumberQuizesTaken() {
		return numberQuizesTaken;
	}

	public void setNumberQuizesTaken(double numberQuizesTaken) {
		this.numberQuizesTaken = numberQuizesTaken;
	}

	public LocalDateTime getFirstQuizDate() {
		return firstQuizDate;
	}

	public void setFirstQuizDate(LocalDateTime firstQuizDate) {
		this.firstQuizDate = firstQuizDate;
	}

	public LocalDateTime getLastQuizDate() {
		return lastQuizDate;
	}

	public void setLastQuizDate(LocalDateTime lastQuizDate) {
		this.lastQuizDate = lastQuizDate;
	}

	public int getTotalTimeInSeconds() {
		return totalTimeInSeconds;
	}

	public void setTotalTimeInSeconds(int totalTimeInSeconds) {
		this.totalTimeInSeconds = totalTimeInSeconds;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public LocalDateTime getCertificateIssuedDate() {
		return certificateIssuedDate;
	}

	public void setCertificateIssuedDate(LocalDateTime certificateIssuedDate) {
		this.certificateIssuedDate = certificateIssuedDate;
	}

	public long getLearnerEnrollmentId() {
		return learnerEnrollmentId;
	}

	public void setLearnerEnrollmentId(long learnerEnrollmentId) {
		this.learnerEnrollmentId = learnerEnrollmentId;
	}

	private LocalDateTime firstAccessDate;
	private LocalDateTime lastAccessDate;
	private int launchesOccrued = 0;
	
	
	private LocalDateTime preTestDate;
	private double pretestScore = -1;
	
	private Boolean completed;
	private String status = "notstarted";
	private LocalDateTime completionDate;
	private double percentComplete = 0;
	
	private double lowestPostTestScore = -1;	
	private double averagePostTestScore = 0;
	private double highestPostTestScore = 0;
	private LocalDateTime firstPostTestDate;
	private LocalDateTime lastPostTestDate;
	private int numberPostTestsTaken = 0;
	
	
	private double lowestQuizScore = 0;
	private double averageQuizScore = 0;
	private double highestQuizScore = 0;
	private double numberQuizesTaken = 0;
	private LocalDateTime firstQuizDate;
	private LocalDateTime lastQuizDate;
	
	private int totalTimeInSeconds = 0;
	
	private String certificateNumber;
	private LocalDateTime certificateIssuedDate;
	
	private long learnerEnrollmentId;
	

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LearnerCourse {\n");
    
    
    sb.append("    firstAccessDate: ").append(toIndentedString(firstAccessDate)).append("\n");
    sb.append("    lastAccessDate: ").append(toIndentedString(lastAccessDate)).append("\n");
    sb.append("    launchesOccrued: ").append(toIndentedString(launchesOccrued)).append("\n");
    sb.append("    preTestDate: ").append(toIndentedString(preTestDate)).append("\n");
    sb.append("    pretestScore: ").append(toIndentedString(pretestScore)).append("\n");
    sb.append("    completed: ").append(toIndentedString(completed)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    completionDate: ").append(toIndentedString(completionDate)).append("\n");
    sb.append("    percentComplete: ").append(toIndentedString(percentComplete)).append("\n");
    sb.append("    lowestPostTestScore: ").append(toIndentedString(lowestPostTestScore)).append("\n");
    sb.append("    averagePostTestScore: ").append(toIndentedString(averagePostTestScore)).append("\n");
    sb.append("    highestPostTestScore: ").append(toIndentedString(highestPostTestScore)).append("\n");
    sb.append("    firstPostTestDate: ").append(toIndentedString(firstPostTestDate)).append("\n");
    sb.append("    lastPostTestDate: ").append(toIndentedString(lastPostTestDate)).append("\n");
    sb.append("    numberPostTestsTaken: ").append(toIndentedString(numberPostTestsTaken)).append("\n");
    sb.append("    lowestQuizScore: ").append(toIndentedString(lowestQuizScore)).append("\n");
    sb.append("    averageQuizScore: ").append(toIndentedString(averageQuizScore)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


