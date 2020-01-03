package com.softech.ls360.api.gateway.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.UserGroupService;
import com.softech.ls360.api.gateway.service.model.vo.EnrollmentDetailVO;
import com.softech.ls360.api.gateway.service.model.vo.EnrollmentVO;
import com.softech.ls360.lms.repository.entities.Customer;
import com.softech.ls360.lms.repository.entities.Distributor;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerGroupMember;
import com.softech.ls360.lms.repository.projection.VU360UserDetailProjection;
import com.softech.ls360.lms.repository.repositories.LearnerEnrollmentRepository;
import com.softech.ls360.lms.repository.repositories.LearnerGroupMemberRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;


@Service
public class LearnerServiceImpl implements LearnerService {
	private static final Logger logger = LogManager.getLogger();

	@Inject
	private LearnerRepository learnerRepository;
	
	@Inject
	private LearnerEnrollmentRepository learnerEnrollmentRepository;
	
	@Inject
	private LearnerGroupMemberRepository learnerGroupMemberRepository;
	
	@Inject
	private SubscriptionRepository subscriptionRepository;
	
	@Inject
	private UserGroupService userGroupService;
	
	
	@Override
	public Learner findByVu360UserUsername(String userName){
		return learnerRepository.findByVu360UserUsername(userName);
	}
	
	@Override
	@Transactional
	public int getStoreId(String userName) {
		int storeID = 0;
		Learner learner = learnerRepository.findByVu360UserUsername(userName);
		
		if (learner == null){
			logger.debug("Invalid user. Learner is NULL");
			return 0;
		}		 
		
		Customer cust = learner.getCustomer();
		if (cust == null){
			logger.debug("Invalid user. Customer is NULL");
			return storeID ;
		}
		Distributor dist = cust.getDistributor();
		if (dist == null){
			logger.debug("Invalid user. Distributor is NULL");
			return storeID;
		}
		String distCode = dist.getDistributorCode();
		
		try{
			storeID = Integer.parseInt(distCode);
		}catch(NumberFormatException e){
			logger.debug("Invalid Store ID  :: NumberFormatException");			
		}		
		return storeID;		
	}

	public List<VU360UserDetailProjection> findByLearnerGroupId(Long learnerGroupId){
		List<VU360UserDetailProjection> lrnGroupMemberList = learnerGroupMemberRepository.findByLearnerGroupId(learnerGroupId);
		return lrnGroupMemberList;
	}
	
	public List<VU360UserDetailProjection> findByCustomer(Long learnerGroupId){
		List<VU360UserDetailProjection> lrnGroupMemberList = learnerGroupMemberRepository.findByCustomer(learnerGroupId);
		return lrnGroupMemberList;
	}
	
	@Transactional
	public String findLearnerGroupByUsername(String username){
		LearnerGroupMember lstLGM = learnerGroupMemberRepository.findFirstByLearner_Vu360User_Username(username);
		if(lstLGM!=null)
			return lstLGM.getLearnerGroup().getName();
		else
			return null;
	}
	
	@Transactional
	public void deleteLearnerFromLearnerGroup(String username){
		LearnerGroupMember lstLGM = learnerGroupMemberRepository.findFirstByLearner_Vu360User_Username(username);
		//for (LearnerGroupMember lg : lstLGM)
		if(lstLGM!=null)
			learnerGroupMemberRepository.delete(lstLGM);
	}
	
	public List<Object[]> findUserCourseAnalyticsByUserName(String username){
		List<Object[]> courseAnalytics = learnerRepository.findUserCourseAnalyticsByUserName(username);
		return courseAnalytics;
	}
	
	public List<Object[]> findUserCourseAnalyticsByUserNameByCourseGUIDs(String username, List<String> guids){
		List<Object[]> courseAnalytics = learnerRepository.findUserCourseAnalyticsByUserNameByCourseGUIDs(username, guids);
		return courseAnalytics;
	}	
	   
		public List<Object[]> findSubscriptionNameByUsername(String username){
		List<Object[]> subscriptioname = subscriptionRepository.findSubscriptionNameByUsername(username);
		return subscriptioname;
	}
	
	public Map<String, String> getAuthorByName(String authorName){
		
		Map<String, String> mapAuthor = new HashMap<String, String>();
		List<Object[]> author = learnerRepository.getAuthorByName(authorName);
		
		for(Object[]  objCE : author){
        	try{
        		mapAuthor.put(objCE[0].toString(), objCE[1].toString());
        	}catch(Exception ex){}
        }
		return mapAuthor;
	}
	
	public Long countByCustomerId(Long customerId){
		Long count = learnerRepository.countByCustomerId(customerId);
		if(count==null)
			return (long) 0;
		
		return count;
	}
	
	public List<EnrollmentVO> getEnrollmentsByDates(String sDate, String eDate){
		
		List<EnrollmentVO> lst = new ArrayList<EnrollmentVO>();
		List<Object[]> dbcol = learnerRepository.getEnrollmentsByDates(sDate, eDate);
		
		
		 for(Object[]  obj : dbcol){
			 EnrollmentVO vo = new EnrollmentVO();
			 vo.setStudentName(obj[0].toString());
			 
			 if(obj[1]!=null)
				 vo.setEmail(obj[1].toString());
			 if(obj[2]!=null)
				 vo.setMobilePhone(obj[2].toString());
			 if(obj[3]!=null)
				 vo.setOfficePhone(obj[3].toString());
			 
			 vo.setCourseName(obj[4].toString());

			 if(obj[6]==null){
				 vo.setModality("Self Paced Course");
			 }else{
		 		 vo.setModality("Classroom Course");
		 		 if(obj[5]!=null)
		 			 vo.setClassName(obj[5].toString());
		 		 if(obj[7]!=null)
		 			 vo.setClassStartDate(obj[7].toString());
		 		 if(obj[8]!=null)
		 			 vo.setClassEndDate(obj[8].toString());
		 	 }
			
			 lst.add(vo);
		 }
		return lst;
	}


public List<EnrollmentDetailVO> getEnrollmentsByCustomerID(Long customerID){
		
		List<EnrollmentDetailVO> lst = new ArrayList<EnrollmentDetailVO>();
		List<Object[]> dbcol = learnerRepository. getEnrollmentsByCustomerID(customerID);
		
		
		 for(Object[]  obj : dbcol){
			 EnrollmentDetailVO vo = new EnrollmentDetailVO();
			 
			 if(obj[0]!=null)
			 vo.setFirstName(obj[0].toString());
			 
			 if(obj[1]!=null)
				 vo.setLastName(obj[1].toString());
			 
			 if(obj[2]!=null)
				 vo.setEmailAddress(obj[2].toString());
			 
			 if(obj[3]!=null)
				 vo.setCourseName(obj[3].toString());
			 
			 if(obj[4]!=null)
			 vo.setLearnerStatisticsStatus(obj[4].toString());

			 if(obj[5]!=null)
				 vo.setLearnerStatisticsCompletionDate(obj[5].toString());
			
		 	
			
			 lst.add(vo);
		 }
		return lst;
	}

@Override
public List<Object[]> getCustomerLearnerEnrollmentCount(String startDate, String endDate) {
	return learnerEnrollmentRepository.getEnrollmentOfCustomerByDate(startDate, endDate);
}
     


}
