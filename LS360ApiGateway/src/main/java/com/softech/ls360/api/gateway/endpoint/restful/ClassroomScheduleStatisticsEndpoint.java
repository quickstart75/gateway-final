package com.softech.ls360.api.gateway.endpoint.restful;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.model.ClassroomCourseRequest;
import com.softech.ls360.api.gateway.service.ClassroomCourseService;
import com.softech.ls360.api.gateway.service.EmailService;
import com.softech.ls360.api.gateway.service.model.response.ClassroomCourseInfo;
import com.softech.ls360.lms.repository.entities.Learner;
import com.softech.ls360.lms.repository.entities.LearnerProfile;
import com.softech.ls360.lms.repository.entities.Subscription;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.LearnerProfileRepository;
import com.softech.ls360.lms.repository.repositories.LearnerRepository;
import com.softech.ls360.lms.repository.repositories.SubscriptionRepository;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;
import com.softech.ls360.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muhammad.sajjad on 10/26/2016.
 */

@RestEndpoint
@RequestMapping("/classroomcourse")
public class ClassroomScheduleStatisticsEndpoint {

    @Inject
    private ClassroomCourseService classroomCourseService;

    @Inject
    private VU360UserRepository vu360UserRepository;

    @Inject
    private LearnerRepository learnerRepository;

    @Inject
    private LearnerProfileRepository learnerProfileRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @RequestMapping(value = "/schedule", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ClassroomCourseInfo> getClassroomCourseScheduleData(@RequestBody ClassroomCourseRequest request){
        List<ClassroomCourseInfo> responseData = classroomCourseService.getClassroomCourseScheduleStatistics(request.getCourseGuid());
        return responseData;
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> sendSubscriptionEmail(@RequestBody ClassroomCourseRequest request){

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        VU360User user = vu360UserRepository.findByUsername(userName);
        Subscription subscription = subscriptionRepository.findBySubscriptionCode(request.getSubscriptionCode());

        Learner learner = learnerRepository.findByVu360UserUsername("manager1");
        LearnerProfile learnerProfile = learnerProfileRepository.findOne(learner.getId());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("subscriberName", user.getFirstName() +" "+ user.getLastName());
        dataMap.put("subscriberEmail", user.getEmailAddress());
        dataMap.put("subscriptionName", subscription.getSubscriptionName());
        dataMap.put("learnerName", request.getLearnerFirstName() + " " + request.getLearnerLastName());
        dataMap.put("learnerEmail", request.getLearnerEmail());
        dataMap.put("learnerPhone", !StringUtils.isEmpty(learnerProfile.getMobilePhone()) ? learnerProfile.getMobilePhone() : "");
        dataMap.put("className", !StringUtils.isEmpty(request.getClassName()) ? request.getClassName() : "");
        dataMap.put("locationName", !StringUtils.isEmpty(request.getLocationName()) ? request.getLocationName() : "");
        dataMap.put("startDateTime", !StringUtils.isEmpty(request.getStartDateTime()) ? request.getStartDateTime() : "");
        dataMap.put("comments", !StringUtils.isEmpty(request.getComments()) ? request.getComments() : "");

        boolean isEmailSent = emailService.sendSubscriptionEmailToSupport(dataMap);
        Map<String,Boolean> emailresponse = new HashMap<>();
        emailresponse.put("status", isEmailSent);
        return emailresponse;
    }
}
