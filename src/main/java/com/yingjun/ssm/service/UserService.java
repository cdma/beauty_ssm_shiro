package com.yingjun.ssm.service;

import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.entity.UserRoleDetail;
import com.yingjun.ssm.entity.UserRoleDetail2;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    public int createUser(User user);

    /**
     * 批量添加用户
     * @param list
     */
    public void createUsers(List<User> list);

    public void updateUser(User user);

	public List<UserRoleDetail> getUserRoleDetail(Long userId);
	public List<UserRoleDetail2> getUserRoleDetail2(Long userId);

    //public void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public List<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public List<String> findPermissions(String username);

    /**
     * 根据params查询用户列表
     * @param idOrUsername
     * @return
     */
    public List<User> findUsers();

}
