package com.softech.ls360.api.gateway.service.model.response;


import java.util.Objects;




/**
 * Logo defined in customer&#39;s brand.
 */
public class Logo  {
  
  private String source = "assets/img/logo.svg";
  private String label = "360training";
  private String url = "/efg";

  
  /**
   * Logo's file source.
   **/
  public Logo source(String source) {
    this.source = source;
    return this;
  }
  
 public String getSource() {
    return source;
  }
  public void setSource(String source) {
    this.source = source;
  }


  /**
   * Logo's label
   **/
  public Logo label(String label) {
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
   * Logo's URL
   **/
  public Logo url(String url) {
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
    Logo logo = (Logo) o;
    return Objects.equals(this.source, logo.source) &&
        Objects.equals(this.label, logo.label) &&
        Objects.equals(this.url, logo.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, label, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Logo {\n");
    
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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

