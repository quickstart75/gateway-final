package com.softech.ls360.api.gateway.service.model.response;

import java.util.List;

public class UserGroupwithUserRest {
		Long guid;
		String name;
		String userCount;
		List<UserRest> users;
		
		public UserGroupwithUserRest() {
			super();
		}
		
		public UserGroupwithUserRest(Long guid, String name) {
			super();
			this.guid = guid;
			this.name = name;
		}
		
		public UserGroupwithUserRest(Long guid, String name, String userCount, List<UserRest> users) {
			super();
			this.guid = guid;
			this.name = name;
			this.userCount = userCount;
			this.users = users;
		}

		public Long getGuid() {
			return guid;
		}
		public void setGuid(Long guid) {
			this.guid = guid;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<UserRest> getUsers() {
			return users;
		}
		public void setUsers(List<UserRest> users) {
			this.users = users;
		}
		public String getUserCount() {
			return userCount;
		}
		public void setUserCount(String userCount) {
			this.userCount = userCount;
		}
}
