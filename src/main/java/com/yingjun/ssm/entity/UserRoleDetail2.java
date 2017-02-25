package com.yingjun.ssm.entity;

/**
 * Created by quanlie on 17/2/25.
 */
public class UserRoleDetail2 {
	private User user;
	private Role role;


	@Override
	public String toString() {
		return "UserRoleDetail2{" +
				"user=" + user +
				", role=" + role +
				'}';
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
