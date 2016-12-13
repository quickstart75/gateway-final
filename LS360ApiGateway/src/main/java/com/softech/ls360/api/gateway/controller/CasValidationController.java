package com.softech.ls360.api.gateway.controller;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.softech.ls360.api.gateway.config.spring.annotation.WebController;
import com.softech.ls360.util.constraints.NotBlank;
import com.softech.ls360.util.network.NetworkUtil;
import com.softech.ls360.util.xml.XmlUtil;

@WebController
@RequestMapping(value="/sso/cas")
public class CasValidationController {
	
	private static final Logger logger = LogManager.getLogger();
	
	/**With @Value annotation, you can use the properties key to get the value from properties file.*/
	@Value("${lf.url}")
	private String linuxFoundationUrl;
	
	@Value("${lf.cas.service.url}")
	private String serviceUrl;
	
	@Value("${lf.cas.validation.url}")
	private String casValidatingUrl;
	
	@Value("${lf.certificate.file.path}")
	private String certFilePath;
	
	@Value("${lf.digest.key}")
	private String key;
	
	@Value("${lms.seamless.login.url}")
	private String lmsSeamlessLoginUrl;
	
	@RequestMapping(value = "lf", method = RequestMethod.GET)
	public ModelAndView validate(HttpServletResponse response,
			@RequestParam(value="ticket", required = true) @NotBlank(message="validate.cas.ticket") String serviceTicket) throws Exception {
		
		logger.info("Request received at CasValidationController for Linux foundation");
		
		String xmlResponse = null;
		try {
			logger.info("Start validating ticket");
			logger.info("Linux Foundation URL: " + linuxFoundationUrl);
			logger.info("Service URL: " + serviceUrl);
			logger.info("CAS Validating URL: " + casValidatingUrl);
			logger.info("Certificate File Path: " + certFilePath);
			xmlResponse = validateTicket(serviceUrl, serviceTicket, casValidatingUrl, certFilePath);
		} catch (Exception e) {
			logger.error("Exceotion occurs while validating ticket: " + e);
			logger.info("Redirecting to Linux Foundation URL: " + linuxFoundationUrl);
			response.sendRedirect(linuxFoundationUrl);
		}
		
		try {
			logger.info("Linux Foundation Response: " + xmlResponse);
			Document xmlDocument = XmlUtil.getXmlDocument(xmlResponse);
			if (isValidUser(xmlDocument)) {
				logger.info("Valid User");
				Node userNode = getNode(xmlDocument, "cas:user"); 
				String userName = getNodeValue(userNode);
				String linuxFoundationUserName = "lfid_" + userName;
				Map<String, String> modelMap = new HashMap<String, String>();
				modelMap.put("userName", linuxFoundationUserName); // admin@winmanagement.com
				modelMap.put("password", getEncryptedPassword(linuxFoundationUserName, key) + "lf360");
			
				modelMap.put("seamlessLoginUrl", lmsSeamlessLoginUrl);
				return new ModelAndView("seamlessLogin", modelMap);
			} else {
				logger.info("User is not validated. Redirecting to linux foundation URL: " + linuxFoundationUrl);
				response.sendRedirect(linuxFoundationUrl);
			}
		} catch (Exception e) {
			logger.error("Exception occurs while validating user from xml response: " + e);
		}
		
		logger.info("Returning null from Linux Foundation CAS");
		return null;
	}
	
	private String validateTicket(String serviceUrl, String ticket, String casValidatingUrl, String certFilePath) throws Exception {
		
		String requestParameters = String.format("?service=%s&ticket=%s", serviceUrl, ticket);
		String casValidationUrl = casValidatingUrl + requestParameters;
		logger.info("CAS Validation URL: " + casValidationUrl);
		String xmlResponse = NetworkUtil.getResponseAsString(certFilePath, casValidationUrl);
		return xmlResponse;
	}
	
	private boolean isValidUser(Document xmlDocument) {
		
		Node authenticationFailureNode = getNode(xmlDocument, "cas:authenticationFailure");
		if (authenticationFailureNode != null) {
			return false;
		}
		return true;
	}
	
	private Node getNode(Document xmlDocument, String nodeName) {
		
		Node node = null;
		NodeList nodeList = xmlDocument.getElementsByTagName(nodeName);
		int nodeListLength = nodeList.getLength();
		if (nodeListLength > 0) {
			node = nodeList.item(0);
		}
		return node;
	}
	
	private String getNodeValue(Node node) {
		
		if (node == null) {
			return null;
		}
		
		Node textNode = node.getFirstChild();
		return textNode.getNodeValue();
	}
	
	private String getEncryptedPassword(String userName, String key) throws Exception {
		
		String digestKey = userName + key;
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] encodedBytesWithMd = Base64.getEncoder().encode(md.digest(digestKey.getBytes()));
		String encodedStringWithMd = new String(encodedBytesWithMd);
		return encodedStringWithMd;
		
	}
	
}
