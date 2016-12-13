package com.softech.ls360.api.gateway.service.model.response;

import java.util.Objects;

/**
 * Main object containing all the sub-objects for all menu options and logo.
 */
public class Brand {
  
  private Logo logo = null;
  private TopMenu topMenu = null;
  private UserData userData = null;
  private LeftMenu leftMenu = null;
  String serverCurrentDate; 



public String getServerCurrentDate() {
	return serverCurrentDate;
}


public void setServerCurrentDate(String serverCurrentDate) {
	this.serverCurrentDate = serverCurrentDate;
}


/**
   * Logo details
   **/
  public Brand logo(Logo logo) {
    this.logo = logo;
    return this;
  }
  
 
  public Logo getLogo() {
    return logo;
  }
  public void setLogo(Logo logo) {
    this.logo = logo;
  }


  /**
   * Top menu Items
   **/
  public Brand topMenu(TopMenu topMenu) {
    this.topMenu = topMenu;
    return this;
  }
  

  public TopMenu getTopMenu() {
    return topMenu;
  }
  public void setTopMenu(TopMenu topMenu) {
    this.topMenu = topMenu;
  }


  /**
   * User Details
   **/
  public Brand userData(UserData userData) {
    this.userData = userData;
    return this;
  }
  
 
  public UserData getUserData() {
    return userData;
  }
  public void setUserData(UserData userData) {
    this.userData = userData;
  }


  /**
   * Left Panel Menu
   **/
  public Brand leftMenu(LeftMenu leftMenu) {
    this.leftMenu = leftMenu;
    return this;
  }
  

  public LeftMenu getLeftMenu() {
    return leftMenu;
  }
  public void setLeftMenu(LeftMenu leftMenu) {
    this.leftMenu = leftMenu;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Brand brandJSON = (Brand) o;
    return Objects.equals(this.logo, brandJSON.logo) &&
        Objects.equals(this.topMenu, brandJSON.topMenu) &&
        Objects.equals(this.userData, brandJSON.userData) &&
        Objects.equals(this.leftMenu, brandJSON.leftMenu);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logo, topMenu, userData, leftMenu);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BrandJSON {\n");
    
    sb.append("    logo: ").append(toIndentedString(logo)).append("\n");
    sb.append("    topMenu: ").append(toIndentedString(topMenu)).append("\n");
    sb.append("    userData: ").append(toIndentedString(userData)).append("\n");
    sb.append("    leftMenu: ").append(toIndentedString(leftMenu)).append("\n");
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


