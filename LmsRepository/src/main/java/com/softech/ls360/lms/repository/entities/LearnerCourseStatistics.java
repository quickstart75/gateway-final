package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;

@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
    	name="LearnerCourseStatistics.update",
        query="update LEARNERCOURSESTATISTICS "
        		+ "SET [STATUS]=:status, COMPLETED=:completed, AVERAGEPOSTTESTSCORE=:averagePostTestScore, "
        		+ "LOWESTPOSTTESTSCORE=:lowestPostTestScore, HIGHESTPOSTTESTSCORE=:highestPostTestScore, "
        		+ "NUMBERPOSTTESTSTAKEN=:numberOfPostTestAttempts, TOTALTIMEINSECONDS=:totalTimeSpent, "
        		+ "FIRSTPOSTTESTDATE=:firstPostTestDate, LASTPOSTTESTDATE=:lastPostTestDate, "
        		+ "COMPLETIONDATE=:completionDate "
        		+ "where LearnerEnrollment_Id=:enrollmentId"
    )
})
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "LearnerCourseStatistics.LearnerEnrollments",
		attributeNodes = {
			@NamedAttributeNode(value="learnerEnrollment"),
			@NamedAttributeNode(value = "learnerEnrollment", subgraph = "enrollmentGraph")
		},
		subgraphs = {
                @NamedSubgraph(
                        name = "enrollmentGraph",
                        attributeNodes = {
                                @NamedAttributeNode(value = "course", subgraph = "course.labType"),
                                @NamedAttributeNode(value = "learner")
                        }
                ),
                @NamedSubgraph(
                        name = "course.labType",
                        attributeNodes = {
                                @NamedAttributeNode(value = "labType")
                        }
                )
        }
	)	
})
public class LearnerCourseStatistics extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NOT_STARTED = "notstarted";

	private double averagePostTestScore = 0;
	private double averageQuizScore = 0;
	private Boolean completed;
	private LocalDateTime completionDate;
	private LocalDateTime firstAccessDate;
	private LocalDateTime firstPostTestDate;
	private LocalDateTime firstQuizDate;
	private double highestQuizScore = 0;
	private LocalDateTime lastAccessDate;
	private LocalDateTime lastPostTestDate;
	private LocalDateTime lastQuizDate;
	private int launchesAccrued = 0;
	private double lowestPostTestScore = -1;
	private double lowestQuizScore = -1;
	private int numberPostTestsTaken = 0;
	private double numberQuizesTaken = 0;
	private double percentComplete = 0;
	private LocalDateTime preTestDate;
	private double pretestScore = -1;
	private String status = NOT_STARTED;
	private int totalTimeInSeconds = 0;
	private Integer learnerCourseStatistics;
	private double highestPostTestScore = 0;
	private LearnerEnrollment learnerEnrollment;
	private LocalDateTime reportDate;
	private Boolean reported;
	private String certificateURL;
	private String debugInfo = "";
	private String certificateNumber;
	private LocalDateTime certificateIssuedDate;

	public double getAveragePostTestScore() {
		return averagePostTestScore;
	}

	public void setAveragePostTestScore(double averagePostTestScore) {
		this.averagePostTestScore = averagePostTestScore;
	}

	public double getAverageQuizScore() {
		return averageQuizScore;
	}

	public void setAverageQuizScore(double averageQuizScore) {
		this.averageQuizScore = averageQuizScore;
	}

	public Boolean isCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}

	public LocalDateTime getFirstAccessDate() {
		return firstAccessDate;
	}

	public void setFirstAccessDate(LocalDateTime firstAccessDate) {
		this.firstAccessDate = firstAccessDate;
	}

	public LocalDateTime getFirstPostTestDate() {
		return firstPostTestDate;
	}

	public void setFirstPostTestDate(LocalDateTime firstPostTestDate) {
		this.firstPostTestDate = firstPostTestDate;
	}

	public LocalDateTime getFirstQuizDate() {
		return firstQuizDate;
	}

	public void setFirstQuizDate(LocalDateTime firstQuizDate) {
		this.firstQuizDate = firstQuizDate;
	}

	public double getHighestQuizScore() {
		return highestQuizScore;
	}

	public void setHighestQuizScore(double highestQuizScore) {
		this.highestQuizScore = highestQuizScore;
	}

	public LocalDateTime getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(LocalDateTime lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public LocalDateTime getLastPostTestDate() {
		return lastPostTestDate;
	}

	public void setLastPostTestDate(LocalDateTime lastPostTestDate) {
		this.lastPostTestDate = lastPostTestDate;
	}

	public LocalDateTime getLastQuizDate() {
		return lastQuizDate;
	}

	public void setLastQuizDate(LocalDateTime lastQuizDate) {
		this.lastQuizDate = lastQuizDate;
	}

	public int getLaunchesAccrued() {
		return launchesAccrued;
	}

	public void setLaunchesAccrued(int launchesAccrued) {
		this.launchesAccrued = launchesAccrued;
	}

	public double getLowestPostTestScore() {
		return lowestPostTestScore;
	}

	public void setLowestPostTestScore(double lowestPostTestScore) {
		this.lowestPostTestScore = lowestPostTestScore;
	}

	public double getLowestQuizScore() {
		return lowestQuizScore;
	}

	public void setLowestQuizScore(double lowestQuizScore) {
		this.lowestQuizScore = lowestQuizScore;
	}

	public int getNumberPostTestsTaken() {
		return numberPostTestsTaken;
	}

	public void setNumberPostTestsTaken(int numberPostTestsTaken) {
		this.numberPostTestsTaken = numberPostTestsTaken;
	}

	public double getNumberQuizesTaken() {
		return numberQuizesTaken;
	}

	public void setNumberQuizesTaken(double numberQuizesTaken) {
		this.numberQuizesTaken = numberQuizesTaken;
	}

	public double getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(double percentComplete) {
		this.percentComplete = percentComplete;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotalTimeInSeconds() {
		return totalTimeInSeconds;
	}

	public void setTotalTimeInSeconds(int totalTimeInSeconds) {
		this.totalTimeInSeconds = totalTimeInSeconds;
	}

	public Integer getLearnerCourseStatistics() {
		return learnerCourseStatistics;
	}

	public void setLearnerCourseStatistics(Integer learnerCourseStatistics) {
		this.learnerCourseStatistics = learnerCourseStatistics;
	}

	public double getHighestPostTestScore() {
		return highestPostTestScore;
	}

	public void setHighestPostTestScore(double highestPostTestScore) {
		this.highestPostTestScore = highestPostTestScore;
	}

	@OneToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="LEARNERENROLLMENT_ID")
	public LearnerEnrollment getLearnerEnrollment() {
		return learnerEnrollment;
	}

	public void setLearnerEnrollment(LearnerEnrollment learnerEnrollment) {
		this.learnerEnrollment = learnerEnrollment;
	}

	public LocalDateTime getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDateTime reportDate) {
		this.reportDate = reportDate;
	}

	public Boolean isReported() {
		return reported;
	}

	public void setReported(Boolean reported) {
		this.reported = reported;
	}

	public String getCertificateURL() {
		return certificateURL;
	}

	public void setCertificateURL(String certificateURL) {
		this.certificateURL = certificateURL;
	}

	public String getDebugInfo() {
		return debugInfo;
	}

	public void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
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

}
