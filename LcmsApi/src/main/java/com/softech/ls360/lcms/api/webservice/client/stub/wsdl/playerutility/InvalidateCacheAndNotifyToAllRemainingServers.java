//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.08.17 at 07:44:26 PM PKT 
//


package com.softech.ls360.lcms.api.webservice.client.stub.wsdl.playerutility;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="publishedCourseId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="notifytoAllRemainingServers" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "publishedCourseId",
    "notifytoAllRemainingServers"
})
@XmlRootElement(name = "InvalidateCacheAndNotifyToAllRemainingServers")
public class InvalidateCacheAndNotifyToAllRemainingServers
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected int publishedCourseId;
    protected boolean notifytoAllRemainingServers;

    /**
     * Gets the value of the publishedCourseId property.
     * 
     */
    public int getPublishedCourseId() {
        return publishedCourseId;
    }

    /**
     * Sets the value of the publishedCourseId property.
     * 
     */
    public void setPublishedCourseId(int value) {
        this.publishedCourseId = value;
    }

    /**
     * Gets the value of the notifytoAllRemainingServers property.
     * 
     */
    public boolean isNotifytoAllRemainingServers() {
        return notifytoAllRemainingServers;
    }

    /**
     * Sets the value of the notifytoAllRemainingServers property.
     * 
     */
    public void setNotifytoAllRemainingServers(boolean value) {
        this.notifytoAllRemainingServers = value;
    }

}
