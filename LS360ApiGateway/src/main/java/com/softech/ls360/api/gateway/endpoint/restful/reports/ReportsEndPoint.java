package com.softech.ls360.api.gateway.endpoint.restful.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;
import com.softech.ls360.api.gateway.service.CustomerService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.model.vo.EnrollmentDetailVO;
import com.softech.ls360.api.gateway.service.model.vo.EnrollmentVO;
import com.softech.ls360.lms.repository.entities.Customer;

@RestEndpoint
@RequestMapping(value="/reports")
public class ReportsEndPoint {
	SimpleDateFormat formatY = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatM = new SimpleDateFormat("MM-dd-yyyy");
	
	@Inject
	private LearnerService learnerService;
	
	@Inject
	private CustomerService customerService; 
	
	@RequestMapping(value = "/emrollments", method = RequestMethod.GET)
	@ResponseBody
	public List<EnrollmentVO> getOrganizationgroupByCustomer(
			 HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		
		long diff = getDateDiff(sdate, edate);
		if(diff>-1 && diff<6)
		{	
			Date date = formatM.parse(sdate);
			sdate = formatY.format(date) + " 00:00:00";
			
			date = formatM.parse(edate);
			edate = formatY.format(date) + " 23:59:59";
			
			List<EnrollmentVO> lst = learnerService.getEnrollmentsByDates(sdate, edate);
			
			//-----------------generate File --- IO operation ------------------------------------
			response.setContentType("application/csv");   
			
			response.setHeader("content-disposition","attachment;filename =filename.csv"); 
			ServletOutputStream  writer = response.getOutputStream();   
			writer.println("Student-name ,Email-address ,Mobile-phone ,Office-phone ,Course-name ,Class-name ,Modality ,Class-startdate ,Class-enddate");
			for(EnrollmentVO vo : lst){
                writer.print(vo.getStudentName() + ",");
                writer.print(vo.getEmail() + ",");
                writer.print(vo.getMobilePhone() + ",");
                writer.print(vo.getOfficePhone() + ",");
                writer.print(vo.getCourseName() + ",");
                writer.print(vo.getClassName() + ",");
                writer.print(vo.getModality() + ",");
                writer.print(vo.getClassStartDate() + ",");
                writer.print(vo.getClassEndDate() + ",");
                writer.println();   
			}

	        writer.flush();
	        writer.close();
		}else{
			return null;
		}
			return null;
	}


	@RequestMapping(value = "/enrollmentsByCustomerID", method = RequestMethod.GET)
	@ResponseBody
	public Map getEnrollmentsByCustomerID(
			 HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		//String userName = auth.getName(); 
		
		Map<String, Object> col = new HashMap<String, Object>();
		//String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findByUsername("st2__qasprint14@mailinator.com"); 
        List<EnrollmentDetailVO> lst = learnerService.getEnrollmentsByCustomerID(customer.getId());
		
        col.put("status", Boolean.TRUE);
        col.put("result", lst);

	     
		
	return col;
}
	
		

long getDateDiff(String sDate, String eDate) {
	try{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
	    Date firstDate = sdf.parse(sDate);
	    Date secondDate = sdf.parse(eDate);
	 
	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    return diff;
	}catch(Exception ex){
		return -1;
	}
   
}
}