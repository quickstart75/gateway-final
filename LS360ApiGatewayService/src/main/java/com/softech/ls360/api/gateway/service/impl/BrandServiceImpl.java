package com.softech.ls360.api.gateway.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softech.ls360.api.gateway.service.BrandService;
import com.softech.ls360.api.gateway.service.LearnerService;
import com.softech.ls360.api.gateway.service.LmsRoleLmsFeatureService;
import com.softech.ls360.api.gateway.service.model.request.LMSBrandingRequest;
import com.softech.ls360.api.gateway.service.model.response.Brand;
import com.softech.ls360.api.gateway.service.model.response.LMSBrandingResponse;
import com.softech.ls360.api.gateway.service.model.response.LeftMenu;
import com.softech.ls360.api.gateway.service.model.response.Logo;
import com.softech.ls360.api.gateway.service.model.response.MenuItem;
import com.softech.ls360.api.gateway.service.model.response.SubMenuItem;
import com.softech.ls360.api.gateway.service.model.response.TopMenu;
import com.softech.ls360.api.gateway.service.model.response.UserData;
import com.softech.ls360.lms.repository.entities.LCMSAuthor;
import com.softech.ls360.lms.repository.entities.LmsRole;
import com.softech.ls360.lms.repository.entities.VU360User;
import com.softech.ls360.lms.repository.repositories.LCMSAuthorRepository;
import com.softech.ls360.lms.repository.repositories.VU360UserRepository;
import com.softech.ls360.util.json.JsonUtil;

@Service
public class BrandServiceImpl implements BrandService{

	@Inject
	private Environment env;

	private static final Logger logger = LogManager.getLogger();

	@Inject
	private VU360UserRepository vu360UserRepository;

	@Inject
	private LCMSAuthorRepository lcmsAuthorRepositoryTest;	
	
	@Inject	
	private LearnerService learnerService;
	
	@Inject
	private LmsRoleLmsFeatureService lmsRoleLmsFeatureService;
	
	String userName = null;
	@Override
	public Brand getBrand(String userName, String token) {
		
		// Extract the token from the HTTP Authorization header
        this.userName = userName;		
		
		VU360User vu360User = vu360UserRepository.findUserAndRolesByUsername(userName);
		if (vu360User == null) {
			logger.debug("Invalid user");
			return new Brand();
		} 

		LCMSAuthor author = lcmsAuthorRepositoryTest.findUserByUserID(BigInteger.valueOf(vu360User.getId()));

		Set<LmsRole> lmsRoles = vu360User.getLmsRoles();
		Iterator<LmsRole> it = lmsRoles.iterator();

		// Branding
		Brand bj = new Brand();
		
		// calling LMS for logo			
		String inputJSON = getRequestJson();
		String responseJson = null;
		
		String lmsBrandurl = env.getProperty("lms.brand");		
		String userRestEndPoint = lmsBrandurl;//"http://10.0.100.94:8090/lms/rest/udpbrand/brandIds";		
		try {
			responseJson = getResponse(userRestEndPoint,inputJSON,token);			
			
		} catch (Exception e) {			
			logger.debug("Lms not responding");
			//return new Brand();
		}
		//JSON from String to Object
		Map map=null;
		String logoUrl = null;
		if(responseJson != null){
		ObjectMapper mapper = new ObjectMapper();
		LMSBrandingResponse obj = null;
		
		try {
			obj = mapper.readValue(responseJson, LMSBrandingResponse.class);
		} catch (JsonParseException e) {			
			logger.debug("JsonParseException");
		} catch (JsonMappingException e) {			
			logger.debug("JsonMappingException");
		} catch (IOException e) {			
			logger.debug("IOException");
		}
		
		map= obj.getBrandKeyValue();
				
		logoUrl = (String) map.get("lms.header.logo.src");
		}
		//
		
		//bj.logo(new Logo());
		Logo logo = new Logo();
		if(logoUrl != null){
			logo.setSource(logoUrl);
		}
		bj.setLogo(logo);
		
		// set top menu items privacy suppport terms
		bj.topMenu(new TopMenu(this.getTopMenu(map, lmsRoles)));
		
		
		//

		bj.userData(new UserData(vu360User.getEmailAddress(), userName, vu360User.getFirstName(), vu360User.getLastName()));
		LeftMenu leftMenu = new LeftMenu();
		List<MenuItem> lsMenueItems = new ArrayList<MenuItem>();

		if (isRoleExist("ROLE_LEARNER", lmsRoles)) {
			lsMenueItems.add(getLearnerMenu());
			leftMenu.child(lsMenueItems);
			bj.leftMenu(leftMenu);
		}

		if (author != null) {
			lsMenueItems.add(getAuthorMenu());
			leftMenu.child(lsMenueItems);
			bj.leftMenu(leftMenu);
		}

		if (isRoleExist("ROLE_TRAININGADMINISTRATOR", lmsRoles)) {
			lsMenueItems.add(getManagerMenu());
			leftMenu.child(lsMenueItems);
			bj.leftMenu(leftMenu);
		}

		lsMenueItems.add(getResourcesMenu());
		leftMenu.child(lsMenueItems);
		bj.leftMenu(leftMenu);

		//set server date
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    //get current date
	    Date date = new Date();	    
	    bj.setServerCurrentDate(dateFormat.format(date).toString());
		return bj;

	}

	private MenuItem getLearnerMenu() {
		MenuItem My_Courses = new MenuItem(null, "My Courses", "courses", "#");
		MenuItem My_Subscriptions = new MenuItem(null, "My Subscriptions", "subscriptions", "#");		
		MenuItem My_transcriptions = new MenuItem(null, "Transcripts & Certificates", "transcriptions", "#");
		List<MenuItem> lsLearner = new ArrayList<MenuItem>();
		lsLearner.add(My_Courses);
		lsLearner.add(My_Subscriptions);
		lsLearner.add(My_transcriptions);		
		MenuItem Learn = new MenuItem(lsLearner, "Learner", "learn", "#");
		return Learn;
	}
			

	private MenuItem getManagerMenu() {
		
		String casLoginURL = env.getProperty("cas.login.url");
		String lmsURL = env.getProperty("lms.url");
		
		String lmsManageUserURL = env.getProperty("lms.manage.user.url");
		String lmsEnrollUserURL = env.getProperty("lms.enroll.user.url");
		String lmsRunReportURL = env.getProperty("lms.run.report.url");
		
		MenuItem manage_users = new MenuItem(null, "Manage Users", "manage-users", "#");
		MenuItem enroll_users = new MenuItem(null, "Enroll Users", "enroll-users", "#");
		MenuItem runReports = new MenuItem(null, "Run Reports", "run-report", "#");
		List<MenuItem> lsLearner = new ArrayList<MenuItem>();
		
		if(casLoginURL != null && lmsURL != null){
			if(lmsManageUserURL != null){
				String lmsCasManageUserURL = casLoginURL + lmsURL + lmsManageUserURL;
				manage_users.setUrl(lmsCasManageUserURL);
			}
			if(lmsEnrollUserURL != null){
				String lmsCasEnrollUserURL = casLoginURL + lmsURL + lmsEnrollUserURL;
				enroll_users.setUrl(lmsCasEnrollUserURL);
			}
			if(lmsRunReportURL != null){
				String lmsCasRunReportURL = casLoginURL + lmsURL + lmsRunReportURL;
				runReports.setUrl(lmsCasRunReportURL);
			}
			
		}
		
		lsLearner.add(manage_users);
		lsLearner.add(enroll_users);
		lsLearner.add(runReports);
		MenuItem manage = new MenuItem(lsLearner, "Manager", "manage", "#");
		return manage;
	}
	

	private MenuItem getAuthorMenu() {
		
		String casLoginURL = env.getProperty("cas.login.url");
		String wlcmsURL = env.getProperty("wlcms.url");
		
		String wlcmsCreateCoursesURL = env.getProperty("wlcms.create.manage.courses.url");
		String wlcmsCourseReportURL = env.getProperty("wlcms.course.report.url");
		
		
		MenuItem createCourse = new MenuItem(null, "Create & Manage Courses", "create-course", "#");
		
		// replace course report # with below static url with concat  storeid
		//https%3A%2F%2Fqa-ws1.360training.com%2FAuthorCourseSalesPerformanceReportView%3FlangId%3D-1%26isredirect%3Dtrue%26storeId%3D 
		//sf.course.reports
		MenuItem courseReport = new MenuItem(null, "Course Reports", "course-report", "#");
		String courseReportURL = env.getProperty("sf.course.reports");
		
		int storeID = getStoreID();
		if(casLoginURL != null && courseReportURL != null && storeID >0){			
				String sfCourseReportURL = casLoginURL + courseReportURL +storeID;
				courseReport.setUrl(sfCourseReportURL);			
		}		
		
		List<MenuItem> lsLearner = new ArrayList<MenuItem>();
		
		if(casLoginURL != null && wlcmsURL != null){
			if(wlcmsCreateCoursesURL != null){
				String createCourseURL = casLoginURL + wlcmsURL + wlcmsCreateCoursesURL;
				createCourse.setUrl(createCourseURL);
			}
			/*
			if(wlcmsCourseReportURL != null){
				String CourseReportURL = casLoginURL + wlcmsURL + wlcmsCourseReportURL;
				courseReport.setUrl(CourseReportURL);
			}
			*/
			
		}
		
		
		lsLearner.add(courseReport);
		lsLearner.add(createCourse);
		MenuItem manage = new MenuItem(lsLearner, "Author", "author", "#");
		return manage;
	}

	private MenuItem getResourcesMenu() {
		String casLoginURL = env.getProperty("cas.login.url");
		String sfVarURL =  env.getProperty("sf.var");
		int storeID = getStoreID();
		
		MenuItem shopCourses = new MenuItem(null, "Shop Courses", "shop", "#");
		
		// 
		String shopCoursesURL = env.getProperty("sf.shop.courses");
		if(casLoginURL != null && shopCoursesURL != null && storeID > 0 && storeID == 21701){			
			String sfshopCoursesURL = casLoginURL + shopCoursesURL ;
			shopCourses.setUrl(sfshopCoursesURL);			
		}
		else if(sfVarURL != null && storeID > 0 && storeID != 21701){
			String sfShopCoursesVarURL = sfVarURL+storeID;
			shopCourses.setUrl(sfShopCoursesVarURL);
		}
		
		
		MenuItem browseFreeCourses = new MenuItem(null, "Browse Free Courses", "browse-free-courses", "#");
		
		// 
		String freeCoursesURL = env.getProperty("sf.browse.free.courses");
		if(casLoginURL != null && freeCoursesURL != null && storeID > 0 && storeID == 21701){			
			String sfFreeCoursesURL = casLoginURL + freeCoursesURL ;
			browseFreeCourses.setUrl(sfFreeCoursesURL);			
		}
		//
				
		MenuItem support = new MenuItem(null, "Support Forum", "support", "#");
		List<MenuItem> lsResources = new ArrayList<MenuItem>();
		
		if(storeID == 21701){			
			lsResources.add(browseFreeCourses);
		}
		if(storeID > 0){
			lsResources.add(shopCourses);	
		}
		lsResources.add(support);
		MenuItem manage = new MenuItem(lsResources, "Resources", "resources", "#");
		return manage;
	}

	private List<SubMenuItem> getTopMenu(Map map, Set<LmsRole> roles) {
		String casLoginURL = env.getProperty("cas.login.url");
		String sfVarURL =  env.getProperty("sf.var");

		SubMenuItem sb1 = new SubMenuItem("Account Information", "account-info", "#");
		SubMenuItem sb2 = new SubMenuItem("Address Book", "address-book", "#");		
		
		// # replaced by sf order page concat with Learner>customer>Distributor>Distributor code (storeID)
		//https%3A%2F%2Fqa-ws1.360training.com%2FTrackOrderStatus%3ForderStatusStyle%3Dstrong%26langId%3D-1%26showOrderHeader%3Dtrue%26storeId%3D
		SubMenuItem sb3 = new SubMenuItem("My Orders", "orders", "#");		
		
		String myOrderURL = env.getProperty("sf.myorder.page");
		int storeID = getStoreID();
		if(casLoginURL != null && myOrderURL != null && storeID > 0 && storeID == 21701){			
			String sfMyOrderURL = casLoginURL + myOrderURL +storeID;
			sb3.setUrl(sfMyOrderURL);			
		}else if(storeID > 0 && storeID != 21701){
			String sfMyOrderVarURL = sfVarURL+storeID;
			sb3.setUrl(sfMyOrderVarURL);
		}
		
		//# replaced by sf order page concat with Learner>customer>Distributor>Distributor code
		//https://qa-cas.360training.com/cas/login?service=https%3A%2F%2Fqa-ws1.360training.com%2FTrackOrderStatus%3FsubscriptionStatusStyle%3Dstrong%26memberId%3D%26isSubscription%3Dtrue%26langId%3D-1%26showOrderHeader%3Dtrue%26storeId%3D                 
		SubMenuItem sb4 = new SubMenuItem("Billing & Subscription", "billing", "#");		
		
		String billingURL = env.getProperty("sf.billing.subscription");		
		if(casLoginURL != null && billingURL != null && storeID > 0 && storeID == 21701){			
				String sfbillingURL = casLoginURL + billingURL +storeID;
				sb4.setUrl(sfbillingURL);			
		}else if(storeID > 0 && storeID != 21701){
			String sfbillingVarURL = sfVarURL + storeID;
			sb4.setUrl(sfbillingVarURL);
		}
		
		//
		
		SubMenuItem sb5 = new SubMenuItem("Support", "support", null);
		//contact us
		String supportURL = null;
		String supportContent = null;
		if(map != null){
		 supportURL = (String) map.get("contactusURL");
		 supportContent = (String) map.get("contactusContent");
		}
		
		if(supportURL == null){
			sb5.setContent(supportContent);
			
		}else{
			sb5.setUrl(supportURL);
		}
		
		//
		
		SubMenuItem sb6 = new SubMenuItem("MENU_DIVIDER", "menu_divider", "#");
		
		SubMenuItem sb7 = new SubMenuItem("Terms Of Use", "terms", null);		
		//Terms Of Use
		String touURL = null;
		String touContent = null;
		if (map != null){
		 touURL = (String) map.get("touURL");
		 touContent = (String) map.get("touContent");
		}
		if(touURL == null){
			sb7.setContent(touContent);
			
		}else{
			sb7.setUrl(touURL);
		}
		//Terms Of Use
		
		SubMenuItem sb8 = new SubMenuItem("Privacy Policy", "privacy-policy", null);
		//Privacy Policy
		String privacyURL = null;
		String privacyContent =  null;
		if(map != null){
		 privacyURL = (String) map.get("privacyURL");
		 privacyContent = (String) map.get("privacyContent");
		}
		if(privacyURL == null){
			sb8.setContent(privacyContent);
			
		}else{
			sb8.setUrl(privacyURL);
		}
		//Privacy Policy
				
		
		SubMenuItem sb9 = new SubMenuItem("MENU_DIVIDER", "menu_divider", "#");
		SubMenuItem sb10 = new SubMenuItem("Sign Out", "sign-out", "http://www.360training.com");

		List<SubMenuItem> topMenu = new ArrayList<SubMenuItem>();
		
		for(LmsRole role : roles){
			if(role.getRoleName().equalsIgnoreCase("LEARNER")){
				if(lmsRoleLmsFeatureService.hasFeature("My Profile", role)){
					topMenu.add(sb1);
					break;
				}
			}
		}
		
		//remove address book
		//topMenu.add(sb2);
		//My order
		//billing
		if(storeID != 0){
			topMenu.add(sb3);		
			topMenu.add(sb4);
		}
		topMenu.add(sb5);
		topMenu.add(sb6);
		topMenu.add(sb7);
		topMenu.add(sb8);
		topMenu.add(sb9);
		topMenu.add(sb10);
		return topMenu;
	}

	private boolean isRoleExist(String role, Set<LmsRole> lmsRoles) {

		for (LmsRole lmsRole : lmsRoles) {
			String roleType = lmsRole.getRoleType();
			if (role.equalsIgnoreCase(roleType)) {
				return true;
			}
		}
		return false;
	}
	
private String getResponse(String restEndPoint,String requestJson,String token) throws Exception {
		
		URL url = new URL(restEndPoint);        
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        
        byte[] postDataBytes = requestJson.getBytes();
        
        /**
         * To do a POST with HttpURLConnection, you need to write the parameters to the connection after you have opened the 
         * connection.
         */
        conn.setRequestMethod("POST");        
        conn.setRequestProperty("token", token);
        conn.setRequestProperty("Content-Type", "application/json");
		//conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);       
		conn.getOutputStream().write(postDataBytes);       
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));        
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;) {
        	 sb.append((char)c);
        }
        String response = sb.toString().trim().replaceAll("[\r\n]", "");
        //response = response.toString().trim().replaceAll(" ", "");
        
        return response;
		
	}
	private String getRequestJson() {
		LMSBrandingRequest req = new LMSBrandingRequest();
		List<String> brandKeyValue = new ArrayList();
		brandKeyValue.add("lms.header.logo.src");
		brandKeyValue.add("lms.header.keywords");		
		req.setBrandKeyValue(brandKeyValue);
		return JsonUtil.convertObjectToJson(req);
	}
	
	private int getStoreID() {		
		int storeId= learnerService.getStoreId(userName);
		return storeId;		
	}

}

