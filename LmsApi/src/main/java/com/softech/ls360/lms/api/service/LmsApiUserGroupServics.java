package com.softech.ls360.lms.api.service;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.softech.ls360.lms.api.model.request.AssignUserGroupRequest;

public interface LmsApiUserGroupServics {
	public  Map assignUsergroups(String authorization, AssignUserGroupRequest assignUserGroupRequest);
}
