package com.softech.ls360.api.gateway.model;


import java.util.Objects;


/**
 * Sub Menu Item
 */
public class SubMenuItem  {
  
  public SubMenuItem(String label, String type, String url) {
		super();
		this.label = label;
		this.type = type;
		this.url = url;
	}

private String label = "MENU_DIVIDER";
  private String type = "menu_divider";
  private String url = "#abc";

  
  /**
   * Label for top menu Item.
   **/
  public SubMenuItem label(String label) {
    this.label = label;
    return this;
  }
  
public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }


  /**
   * Menu Item type
   **/
  public SubMenuItem type(String type) {
    this.type = type;
    return this;
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
  public SubMenuItem url(String url) {
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
    SubMenuItem subMenuItem = (SubMenuItem) o;
    return Objects.equals(this.label, subMenuItem.label) &&
        Objects.equals(this.type, subMenuItem.type) &&
        Objects.equals(this.url, subMenuItem.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, type, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubMenuItem {\n");
    
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

