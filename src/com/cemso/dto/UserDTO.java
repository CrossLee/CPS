package com.cemso.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDTO {

	private Integer indexid;
	
	public Integer getIndexid() {
		return indexid;
	}
	public void setIndexid(Integer indexid) {
		this.indexid = indexid;
	}
	private String id;
	private String userPwd;
	private String userType;
	private String privileges;
	private String lastLogin;
	
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
    public String getPrivileges() {
        return privileges;
    }
    // display the full privileges
    public String getFullPrivileges() {
    	int i = privileges.length();
    	List<String> pp = new ArrayList<String>();
    	for(int k=0; k < i; k++){
    		pp.add(privileges.charAt(k)+"");
    	}
    	Collections.sort(pp);
    	StringBuilder sb = new StringBuilder("");
    	for(int m=0; m<pp.size();m++){
    		if(pp.get(m).equals("1")){
    			sb.append("首页，");
    			continue;
    		}
    		if(pp.get(m).equals("2")){
    			sb.append("资源管理，");
    			continue;
    		}
    		if(pp.get(m).equals("3")){
    			sb.append("模板管理，");
    			continue;
    		}
    		if(pp.get(m).equals("4")){
    			sb.append("模板设计，");
    			continue;
    		}
    		if(pp.get(m).equals("5")){
    			sb.append("节目管理，");
    			continue;
    		}
    		if(pp.get(m).equals("6")){
    			sb.append("播表管理，");
    			continue;
    		}
    		if(pp.get(m).equals("7")){
    			sb.append("播表推送，");
    			continue;
    		}
    		if(pp.get(m).equals("8")){
    			sb.append("设备管理，");
    			continue;
    		}
    		if(pp.get(m).equals("9")){
    			sb.append("用户管理，");
    			continue;
    		}
    	}
        return sb.toString().substring(0,sb.toString().length()-1);
    }
    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
}
