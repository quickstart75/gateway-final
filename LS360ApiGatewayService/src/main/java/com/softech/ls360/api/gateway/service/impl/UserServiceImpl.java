package com.softech.ls360.api.gateway.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.UserService;
import com.softech.ls360.api.gateway.service.model.vo.VU360UserVO;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Inject
	VU360UserRepository vu360UserRepository;
	
	@Override
	public boolean changePermission(List<VU360UserVO> lstUserVO){
		 for(VU360UserVO vU360UserVO : lstUserVO){
			 VU360User user =  vu360UserRepository.findByUsername(vU360UserVO.getUsername());
			 if(user!=null){
				 if(vU360UserVO.getLocked()!=null){
					 user.setAccountNonLockedTf(vU360UserVO.getLocked());
				 }
				 if(vU360UserVO.getEnabled()!=null){
					 user.setEnabledTf(vU360UserVO.getEnabled());
				 }
				 vu360UserRepository.save(user);
			 }
		 }
		 
		return true;
	}
	
	@Override
	public VU360User findByUsername(String userName){
		return vu360UserRepository.findByUsername(userName);
	}
	
	@Override
	public VU360User findById(Long vu360userId){
		return vu360UserRepository.findOne(vu360userId);
	}
}
