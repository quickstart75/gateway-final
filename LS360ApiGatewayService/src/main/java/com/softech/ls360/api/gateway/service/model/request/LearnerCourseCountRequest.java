package com.softech.ls360.api.gateway.service.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Input payload for learner's course count
 */

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2016-06-20T10:22:59.333Z")
public class LearnerCourseCountRequest {

	private static final long serialVersionUID = 1L;

	private List<String> countType = new ArrayList<String>();

	/**
	 * Count Type, for examle ('notstarted', 'inprogress', 'completed', 'all',
	 * 'subscriptions')
	 **/
	public LearnerCourseCountRequest countType(List<String> countType) {
		this.countType = countType;
		return this;
	}

	@JsonProperty("countType")
	public List<String> getCountType() {
		return countType;
	}

	public void setCountType(List<String> countType) {
		this.countType = countType;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LearnerCourseCountRequest learnerCourseCount = (LearnerCourseCountRequest) o;
		return Objects.equals(this.countType, learnerCourseCount.countType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(countType);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class LearnerCourseCount {\n");

		sb.append("    countType: ").append(toIndentedString(countType))
				.append("\n");
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
