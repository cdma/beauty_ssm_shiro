package com.yingjun.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.dao.UserMapper;
import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.entity.UserExample;
import com.yingjun.ssm.enums.ResultEnum;
import com.yingjun.ssm.exception.BizException;
import com.yingjun.ssm.service.PasswordHelper;
import com.yingjun.ssm.service.UserService;
import com.yingjun.ssm.utils.DebugUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    //private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    //@Autowired
    //private RoleService roleService;
    @Autowired
    private RedisCache cache;
    @Autowired
    private UserMapper userMapper;

    /**
     * 创建用户
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public int createUser(User user) {
	    DebugUtils.transactionRequired("UserServiceImpl.createUser");
        //加密密码
        passwordHelper.encryptPassword(user);
        return userMapper.insertSelective(user);
        /*UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
            userDto.setRole_ids(user.getRoleIdsStr());
            userDao.createUser(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void createUsers(List<User> list) {
        for (User user : list) {
            createUser(user);
        }
    }

   @Override
    public void updateUser(User user) {
        UserDto userDto = new UserDto();
        /*try {
            BeanUtils.copyProperties(userDto, user);
            userDto.setRole_ids(user.getRoleIdsStr());
            userDao.updateUser(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
    }

    /*@Override
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }*/

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        //User user = userDao.findOne(userId);
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        updateUser(user);
    }

    @Override
    //@MethodCache(clz = User.class)
    public User findOne(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    //@MethodCache(isCollection = 1, clz = User.class, expire = 300) // 触发缓存aop，返回结果是集合，集合内对象类型是User，缓存过期时间5分钟
    public List<User> findAll() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
	    PageHelper.offsetPage(1,3);
        return userMapper.selectByExample(userExample);
    }

    @Override
    //@MethodCache(clz = User.class)
    public User findByUsername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(example);
        return userList.isEmpty() ? null : userList.get(0);
        //return userDao.findByUsername(username);
    }

    @Override
    //@MethodCache(isCollection = 1, clz = String.class)
    public List<String> findRoles(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> roleList = new LinkedList<String>();
        roleList.add("admin");
        return roleList;
        //return roleService.findRoles(user.getRoleIds()); // 目的在于：程序健壮性（user.roleIds可不算数，需要去role中验证/获取）
    }

    @Override
    //@MethodCache(isCollection = 1, clz = String.class)
    public List<String> findPermissions(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> permissionList = new LinkedList<String>();
        permissionList.add("organization:*");
        permissionList.add("user:*");
        permissionList.add("resource:*");
        return permissionList;
        //return roleService.findPermissions(user.getRoleIds());
    }

    @Override
    //@MethodCache(isCollection = 1, clz = User.class)
    public List<User> findUsers(){
        // 参数处理 （idOrUsername）
        User user = new User();
       /* if (StringUtils.isNumeric(idOrUsername)) {
            user.setId(Long.parseLong(idOrUsername));
        } else {
            user.setUsername(idOrUsername);
        }*/

        // 业务处理
        List<User> users = findAll();
        if (users == null || users.size() == 0) {
            throw new BizException(ResultEnum.DB_SELECTONE_IS_NULL.getMsg());
        }
        return users;
    }
}
