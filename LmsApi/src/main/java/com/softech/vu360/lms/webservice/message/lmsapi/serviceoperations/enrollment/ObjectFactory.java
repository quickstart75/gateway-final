//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.25 at 07:11:10 PM PKT 
//


package com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.softech.vu360.lms.webservice.message.lmsapi.serviceoperations.enrollment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LearnerCoursesEnrollRequest }
     * 
     */
    public LearnerCoursesEnrollRequest createLearnerCoursesEnrollRequest() {
        return new LearnerCoursesEnrollRequest();
    }

    /**
     * Create an instance of {@link LearnerCoursesEnrollResponse }
     * 
     */
    public LearnerCoursesEnrollResponse createLearnerCoursesEnrollResponse() {
        return new LearnerCoursesEnrollResponse();
    }

    /**
     * Create an instance of {@link BulkEnrollmentRequest }
     * 
     */
    public BulkEnrollmentRequest createBulkEnrollmentRequest() {
        return new BulkEnrollmentRequest();
    }

    /**
     * Create an instance of {@link BulkEnrollmentResponse }
     * 
     */
    public BulkEnrollmentResponse createBulkEnrollmentResponse() {
        return new BulkEnrollmentResponse();
    }

}
