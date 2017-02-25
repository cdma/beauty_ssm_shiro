package com.yingjun.ssm.entity;

import java.util.List;

/**
 * Created by quanlie on 17/2/25.
 */
public class UserRoleDetail extends User {
	private List<Role> roleList;


	@Override
	public String toString() {
		return "UserRoleDetail{" +
				super.toString() +
				"roleList=" + roleList +
				'}';
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
