package com.yingjun.ssm.utils;

import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 * Created by quanlie on 17/2/22.
 */
public class UserUtils {
	 public static User UserDto2User(UserDto userDto) throws BeansException {
		User user = new User();
        try {
            BeanUtils.copyProperties(userDto, user);
        } catch (BeansException e) {
	        e.printStackTrace();
        }
		return user;
	}
}
