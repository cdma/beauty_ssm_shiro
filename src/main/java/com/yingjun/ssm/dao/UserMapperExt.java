package com.yingjun.ssm.dao;

import com.yingjun.ssm.entity.UserRoleDetail;
import com.yingjun.ssm.entity.UserRoleDetail2;

import java.util.List;

/**
 * Created by quanlie on 17/2/25.
 */
public interface UserMapperExt {
	List<UserRoleDetail> getUserRoleDetail(Long id);
	List<UserRoleDetail2> getUserRoleDetail2(Long id);

}
