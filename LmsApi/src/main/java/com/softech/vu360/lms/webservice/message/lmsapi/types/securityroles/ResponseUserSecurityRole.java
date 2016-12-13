//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.25 at 07:11:10 PM PKT 
//


package com.softech.vu360.lms.webservice.message.lmsapi.types.securityroles;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.AssignedOrganizationalGroups;
import com.softech.vu360.lms.webservice.message.lmsapi.types.orggroup.UnassignedOrganizationalGroups;


/**
 * <p>Java class for ResponseUserSecurityRole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseUserSecurityRole"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SecurityRoleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AssignedSecurityRoleUsers" type="{http://securityroles.types.lmsapi.message.webservice.lms.vu360.softech.com}AssignedSecurityRoleUsers" minOccurs="0"/&gt;
 *         &lt;element name="UnassignedSecurityRoleUsers" type="{http://securityroles.types.lmsapi.message.webservice.lms.vu360.softech.com}UnassignedSecurityRoleUsers" minOccurs="0"/&gt;
 *         &lt;element name="AssignedOrganizationalGroups" type="{http://orggroup.types.lmsapi.message.webservice.lms.vu360.softech.com}AssignedOrganizationalGroups" minOccurs="0"/&gt;
 *         &lt;element name="UnassignedOrganizationalGroups" type="{http://orggroup.types.lmsapi.message.webservice.lms.vu360.softech.com}UnassignedOrganizationalGroups" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseUserSecurityRole", propOrder = {
    "securityRoleName",
    "assignedSecurityRoleUsers",
    "unassignedSecurityRoleUsers",
    "assignedOrganizationalGroups",
    "unassignedOrganizationalGroups"
})
public class ResponseUserSecurityRole
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SecurityRoleName")
    protected String securityRoleName;
    @XmlElement(name = "AssignedSecurityRoleUsers")
    protected AssignedSecurityRoleUsers assignedSecurityRoleUsers;
    @XmlElement(name = "UnassignedSecurityRoleUsers")
    protected UnassignedSecurityRoleUsers unassignedSecurityRoleUsers;
    @XmlElement(name = "AssignedOrganizationalGroups")
    protected AssignedOrganizationalGroups assignedOrganizationalGroups;
    @XmlElement(name = "UnassignedOrganizationalGroups")
    protected UnassignedOrganizationalGroups unassignedOrganizationalGroups;
    @XmlAttribute(name = "errorCode")
    protected String errorCode;
    @XmlAttribute(name = "errorMessage")
    protected String errorMessage;

    /**
     * Gets the value of the securityRoleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityRoleName() {
        return securityRoleName;
    }

    /**
     * Sets the value of the securityRoleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityRoleName(String value) {
        this.securityRoleName = value;
    }

    /**
     * Gets the value of the assignedSecurityRoleUsers property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedSecurityRoleUsers }
     *     
     */
    public AssignedSecurityRoleUsers getAssignedSecurityRoleUsers() {
        return assignedSecurityRoleUsers;
    }

    /**
     * Sets the value of the assignedSecurityRoleUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignedSecurityRoleUsers }
     *     
     */
    public void setAssignedSecurityRoleUsers(AssignedSecurityRoleUsers value) {
        this.assignedSecurityRoleUsers = value;
    }

    /**
     * Gets the value of the unassignedSecurityRoleUsers property.
     * 
     * @return
     *     possible object is
     *     {@link UnassignedSecurityRoleUsers }
     *     
     */
    public UnassignedSecurityRoleUsers getUnassignedSecurityRoleUsers() {
        return unassignedSecurityRoleUsers;
    }

    /**
     * Sets the value of the unassignedSecurityRoleUsers property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnassignedSecurityRoleUsers }
     *     
     */
    public void setUnassignedSecurityRoleUsers(UnassignedSecurityRoleUsers value) {
        this.unassignedSecurityRoleUsers = value;
    }

    /**
     * Gets the value of the assignedOrganizationalGroups property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedOrganizationalGroups }
     *     
     */
    public AssignedOrganizationalGroups getAssignedOrganizationalGroups() {
        return assignedOrganizationalGroups;
    }

    /**
     * Sets the value of the assignedOrganizationalGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignedOrganizationalGroups }
     *     
     */
    public void setAssignedOrganizationalGroups(AssignedOrganizationalGroups value) {
        this.assignedOrganizationalGroups = value;
    }

    /**
     * Gets the value of the unassignedOrganizationalGroups property.
     * 
     * @return
     *     possible object is
     *     {@link UnassignedOrganizationalGroups }
     *     
     */
    public UnassignedOrganizationalGroups getUnassignedOrganizationalGroups() {
        return unassignedOrganizationalGroups;
    }

    /**
     * Sets the value of the unassignedOrganizationalGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnassignedOrganizationalGroups }
     *     
     */
    public void setUnassignedOrganizationalGroups(UnassignedOrganizationalGroups value) {
        this.unassignedOrganizationalGroups = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

}
