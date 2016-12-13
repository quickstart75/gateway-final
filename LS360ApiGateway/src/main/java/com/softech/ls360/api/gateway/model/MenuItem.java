package com.softech.ls360.api.gateway.model;


import java.util.Objects;


import java.util.ArrayList;
import java.util.List;


/**
 * Main Menu Item
 */
public class MenuItem  {
  
  public MenuItem(List<MenuItem> child, String label, String type, String url) {
		super();
		this.child = child;
		this.label = label;
		this.type = type;
		this.url = url;
	}

//private List<SubMenuItem> child = new ArrayList<SubMenuItem>();
  private List<MenuItem> child = new ArrayList<MenuItem>();
  private String label = "Terms Of Use";
  private String type = "Terms";
  private String url = "#xyz";

  
  /**
   * Sub Menu Items
   **/
  
  

  /**
   * Type for main Menu Item
   **/
  public MenuItem type(String type) {
    this.type = type;
    return this;
  }
  
   public List<MenuItem> getChild() {
	return child;
}

public void setChild(List<MenuItem> child) {
	this.child = child;
}

public String getLabel() {
	return label;
}

public void setLabel(String label) {
	this.label = label;
}

public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  /**
   * URL to be invoked on click.
   **/
  public MenuItem url(String url) {
    this.url = url;
    return this;
  }
  
 public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuItem menuItem = (MenuItem) o;
    return Objects.equals(this.child, menuItem.child) &&
        Objects.equals(this.label, menuItem.label) &&
        Objects.equals(this.type, menuItem.type) &&
        Objects.equals(this.url, menuItem.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(child, label, type, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MenuItem {\n");
    
    sb.append("    child: ").append(toIndentedString(child)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

