package com.softech.ls360.api.gateway.endpoint.restful;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.softech.ls360.api.gateway.config.spring.annotation.RestEndpoint;

/**
 * The discovery methods in the AccountRestEndpoint help clients learn about the account resource and what they can do with it, but
 * it doesn’t complete the RESTful discovery mechanism. Typically you would create an index endpoint that lists your web service’s 
 * available resources. In the simplest sense, this is just a static list of links in your code, created with your application’s 
 * domain name and context path in front. The IndexRestEndpoint does just this. It returns resource links using the HAL standard, 
 * and because this standard has very different XML and JSON representations, it uses some helper POJOs and two different discovery
 * methods to return the appropriate response. @RequestMapping’s produces attribute helps identify which method should be called 
 * based on the request’s Accept header.
 * 
 * @author basit.ahmed
 *
 */
@RestEndpoint
public class IndexRestEndpoint {
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = {"application/json", "text/json" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> discoverJson() {
		
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();

		Map<String, JsonLink> links = new Hashtable<>(2);
		links.put("self", new JsonLink(builder.path("").build().toString()));
		links.put("account", new JsonLink(builder.path("/account").build().toString()));

		Map<String, Object> response = new Hashtable<>(1);
		response.put("_links", links);
		return response;
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET, produces = {"application/xml", "text/xml" })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Resource discoverXml() {
		
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentServletMapping();

		Resource resource = new Resource();
		resource.addLink(new Link("self", builder.path("").build().toString()));
		resource.addLink(new Link("account", builder.path("/account").build().toString()));
		return resource;
	}

	public static class JsonLink {
		private String href;

		public JsonLink(String href) {
			this.href = href;
		}

		@XmlAttribute
		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}
	}

	public static class Link extends JsonLink {
		private String rel;

		public Link(String rel, String href) {
			super(href);
			this.rel = rel;
		}

		@XmlAttribute
		public String getRel() {
			return rel;
		}

		public void setRel(String rel) {
			this.rel = rel;
		}
	}

	@XmlRootElement
	public static class Resource {
		private List<Link> links = new ArrayList<>();

		@XmlElement(name = "link")
		public List<Link> getLinks() {
			return links;
		}

		public void setLinks(List<Link> links) {
			this.links = links;
		}

		public void addLink(Link link) {
			this.links.add(link);
		}
	}
}
