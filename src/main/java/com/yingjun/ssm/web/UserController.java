package com.yingjun.ssm.web;

import com.yingjun.ssm.dto.BaseResult;
import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.entity.UserRoleDetail;
import com.yingjun.ssm.enums.ResultEnum;
import com.yingjun.ssm.exception.BizException;
import com.yingjun.ssm.service.UserService;
import com.yingjun.ssm.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
   // @Autowired
   // private RoleService roleService;

    /**
     * @RequiresPermissions("user:view")：调用UserRealm的doGetAuthorizationInfo()获得用户权限，并判断
     */
    @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user/list";
    }

  /*  @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
  //      setCommonData(model);
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增");
        return "user/edit";
    }*/

    /**
     * 添加用户
     */
    //@RequiresPermissions("user:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResult<Integer> create(@Valid UserDto userDto, BindingResult result) {
        int count = userService.createUser(UserUtils.UserDto2User(userDto));
	    BaseResult<Integer> resp = null;
	    resp = new BaseResult<Integer>(true, new Integer(count));
	    return resp;
    }

	@ResponseBody
	@RequestMapping(value = "/role_detail_onetomany", method = {RequestMethod.POST, RequestMethod.GET})
	public BaseResult<List<UserRoleDetail>> role_detail(long userId) {
		List<UserRoleDetail> userRoleDetail = userService.getUserRoleDetail(userId);
		logger.info(userRoleDetail.toString());
		BaseResult<List<UserRoleDetail>> resp = null;
		resp = new BaseResult<List<UserRoleDetail>>(true, (userRoleDetail));
		return resp;
	}

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
   //     setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    /*@RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }*/


    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    @ResponseBody
    @RequestMapping("/findUsers")
    public BaseResult<List<User>> findUsers(){
        BaseResult<List<User>> resp = null;
        try {
            List<User> list = userService.findUsers();
            resp = new BaseResult<List<User>>(true, list);
        } catch(BizException e){
            resp = new BaseResult(false, e.getMessage());
        } catch(Exception e){
            resp = new BaseResult(false, ResultEnum.INNER_ERROR.getMsg());
        }
        return resp;
    }
}
