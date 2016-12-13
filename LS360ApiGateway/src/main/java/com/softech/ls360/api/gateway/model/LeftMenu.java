package com.softech.ls360.api.gateway.model;


import java.util.Objects;


import java.util.ArrayList;
import java.util.List;


/**
 * Left panel menu
 */
public class LeftMenu  {
  
  private List<MenuItem> child = new ArrayList<MenuItem>();
  private String label = "Dashboard";

  
  /**
   * Main Menu Item
   **/
  public LeftMenu child(List<MenuItem> child) {
    this.child = child;
    return this;
  }
  
 public List<MenuItem> getChild() {
    return child;
  }
  public void setChild(List<MenuItem> child) {
    this.child = child;
  }


  /**
   * Label for Left Panel
   **/
  public LeftMenu label(String label) {
    this.label = label;
    return this;
  }
  
public String getLabel() {
    return label;
  }
  public void setLabel(String label) {
    this.label = label;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LeftMenu leftMenu = (LeftMenu) o;
    return Objects.equals(this.child, leftMenu.child) &&
        Objects.equals(this.label, leftMenu.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(child, label);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LeftMenu {\n");
    
    sb.append("    child: ").append(toIndentedString(child)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
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

