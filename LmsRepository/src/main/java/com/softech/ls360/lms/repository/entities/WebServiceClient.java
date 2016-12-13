package com.softech.ls360.lms.repository.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;

@Entity
@NamedEntityGraphs({
	@NamedEntityGraph(
		name = "WebServiceClient.ScopeAndGrantTypeAndRedirectUri",
		attributeNodes = {
			@NamedAttributeNode(value="scope"),
			@NamedAttributeNode(value="authorizedGrantTypes"),
			@NamedAttributeNode(value="registeredRedirectUri")
		}
	)	
})
public class WebServiceClient extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clientId;
	private String clientSecret;
	private Set<String> scope;
	private Set<String> authorizedGrantTypes;
	private Set<String> registeredRedirectUri;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	/**
	 * @ElementCollection is new annotation introduced in JPA 2.0, This will help us get rid of One-Many and Many-One syntax. It is 
	 * meant to handle several non-standard relationship mappings. An ElementCollection can be used to define a one-to-many 
	 * relationship to an Embeddable object, or a Basic value (such as a collection of Strings). An ElementCollection can also be 
	 * used in combination with a Map to define relationships where the key can be any type of object, and the value is an 
	 * Embeddable object or a Basic value.
	 * 
	 * In JPA an ElementCollection relationship is defined through the @ElementCollection annotation or the <element-collection> 
	 * element.
	 * 
	 * The ElementCollection values are always stored in a separate table. The table is defined through the @CollectionTable 
	 * annotation or the <collection-table> element. The CollectionTable defines the table's name and @JoinColumn or @JoinColumns if 
	 * a composite primary key.
	 * 
	 * An ElementCollection mapping can be used to define a collection of Basic objects. The Basic values are stored in a separate 
	 * collection table. This is similar to a OneToMany, except the target is a Basic value instead of an Entity. This allows 
	 * collections of simple values to be easily defined, without requiring defining a class for the value.
	 * 
	 * There is no cascade option on an ElementCollection, the target objects are always persisted, merged, removed with their 
	 * parent. ElementCollection still can use a fetch type and defaults to LAZY the same as other collection mappings.
	 * 
	 * @return
	 */
	@ElementCollection
    @CollectionTable(name = "WebServiceClient_Scope", joinColumns = {
            @JoinColumn(name = "WebServiceClient_Id",
                    referencedColumnName = "Id")
    })
    @Column(name = "Scope")
	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	@ElementCollection
    @CollectionTable(name = "WebServiceClient_Grant", joinColumns = {
            @JoinColumn(name = "WebServiceClient_Id",
                    referencedColumnName = "Id")
    })
    @Column(name = "GrantName")
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	@ElementCollection
    @CollectionTable(name = "WebServiceClient_RedirectUri", joinColumns = {
            @JoinColumn(name = "WebServiceClient_Id",
                    referencedColumnName = "Id")
    })
    @Column(name = "Uri")
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

}
