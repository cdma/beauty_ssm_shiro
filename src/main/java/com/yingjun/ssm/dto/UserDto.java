package com.yingjun.ssm.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserDto {
	@Null
	private Long id;

	@NotBlank
	@Size(max=10, min=3)
	private String username;

	@NotBlank
	@Size(max=10, min=3)
	private String password;

	@Null
	private String state;

	@Null
	private Date createTime;

	private String salt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}