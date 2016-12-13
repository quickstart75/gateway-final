package com.softech.ls360.api.gateway.service.model.response;


import java.util.Objects;


import java.util.ArrayList;
import java.util.List;


/**
 * Top menu item list.
 */
public class TopMenu  {
  
  public TopMenu(List<SubMenuItem> child) {
		super();
		this.child = child;
	}

private List<SubMenuItem> child = new ArrayList<SubMenuItem>();

  
  /**
   * Menu Item List
   **/
  public TopMenu child(List<SubMenuItem> child) {
    this.child = child;
    return this;
  }
  
 public List<SubMenuItem> getChild() {
    return child;
  }
  public void setChild(List<SubMenuItem> child) {
    this.child = child;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TopMenu topMenu = (TopMenu) o;
    return Objects.equals(this.child, topMenu.child);
  }

  @Override
  public int hashCode() {
    return Objects.hash(child);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TopMenu {\n");
    
    sb.append("    child: ").append(toIndentedString(child)).append("\n");
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

