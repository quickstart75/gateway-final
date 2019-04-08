package com.softech.ls360.api.gateway.service;

import java.util.List;

import com.softech.ls360.api.gateway.service.model.vo.VU360UserVO;
import com.softech.ls360.lms.repository.entities.VU360User;

public interface UserService {
	boolean changePermission(List<VU360UserVO> lstUserVO);
	VU360User findByUsername(String userName);
}
