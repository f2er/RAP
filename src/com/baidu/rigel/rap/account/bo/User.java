package com.baidu.rigel.rap.account.bo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baidu.rigel.rap.project.bo.Project;

public class User implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String name;
	private String account;
	private String password;
	private String email;
	private Date createDate;
	private boolean isLockedOut;
	private Date lastLoginDate;
	private int incorrectLoginAttempt;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	private Set<Role> roleList = new HashSet<Role>();
	
	public Set<Role> getRoleList() {
		return this.roleList;
	}
	
	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}
	
	public boolean isUserInRole(String roleName) {
		for (Role r : getRoleList()) {
			if (r.getName().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
	
	public String getRoleListStr() {
		String str = "";
		for (Role r : getRoleList()) {
			str += r.getName() + "  ";
		}
		return str;
	}
	
	public List<Project> getAccessibleProjectList() {
		List<Project> projectList = new ArrayList<Project>();
		for (Project p : getCreatedProjectList()) {
			p.setIsManagable(true);
			projectList.add(p);
		}
		for (Project p : getJoinedProjectList()) {
			projectList.add(p);
		}
		return projectList;
	}
	
	public boolean haveAccessOfProject(int projectId) {
		if (isUserInRole("admin")) {
			return true;
		}
		for (Project p : getCreatedProjectList()) {
			if (p.getId() == projectId) {
				return true;
			}
		}
		
		for (Project p : getJoinedProjectList()) {
			if (p.getId() == projectId) {
				return true;
			}
		}
		return false;
	}
	
	private Set<Project> createdProjectList = new HashSet<Project>();
	
	public Set<Project> getCreatedProjectList() {
		return this.createdProjectList;
	}
	
	public void setCreatedProjectList(Set<Project> createdProjectList) {
		this.createdProjectList = createdProjectList;
	}
	
	private Set<Project> joinedProjectList = new HashSet<Project>();
	
	public Set<Project> getJoinedProjectList() {
		return this.joinedProjectList;
	}
	
	public void setJoinedProjectList(Set<Project> joinedProjectList) {
		this.joinedProjectList = joinedProjectList;
	}
	
	public String getAccount() {
		return this.account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateDateStr() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(getCreateDate());
	}
	
	public boolean getIsLockedOut() {
		return this.isLockedOut;
	}
	
	public void setIsLockedOut(boolean isLockedOut) {
		this.isLockedOut = isLockedOut;
	}
	
	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}
	
	public String getLastLoginDateStr() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(getLastLoginDate());
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public int getIncorrectLoginAttempt() {
		return this.incorrectLoginAttempt;
	}
	
	public void setIncorrectLoginAttempt(int incorrectLoginAttempt) {
		this.incorrectLoginAttempt = incorrectLoginAttempt;
	}
	
	public void addCreatedProject(Project project) {
		project.setUser(this);
		this.createdProjectList.add(project);
	}
	
	private boolean isHintEnabled;
	
	public boolean getIsHintEnabled() {
		return isHintEnabled;
	}
	
	public void setIsHintEnabled(boolean isHintEnabled) {
		this.isHintEnabled = isHintEnabled;
	}
	
	public String toString() {
		return "{\"name\":\"" + this.name + "\",\"id\":" + this.id + "}";
	}

	public boolean canManageProject(int projectId) {
		if (isUserInRole("admin")) {
			return true;
		}
		for (Project p : getCreatedProjectList()) {
			if (p.getId() == projectId) {
				return true;
			}
		}		
		return false;
	}

	public String getWorkRole() {
		for (Role r : getRoleList()) {
			if (r.getName().length() == 2) {
				return r.getName().toUpperCase();
			}
		}
		return "";
	}
}
