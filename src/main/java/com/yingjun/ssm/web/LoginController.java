package com.yingjun.ssm.web;

import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.service.UserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        System.out.println("--->正常登录不会进入，这个是.get('/login', ..)");
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/test/date", method = {RequestMethod.POST, RequestMethod.GET})
    public Object test_date(@Valid User user, BindingResult result) {
        System.out.println("user:"+user.getCreateTime());
	    userService.findAll();
        return user;
    }


//    private void ensureUserIsLoggedOut() {
//        try {
//            // Get the user if one is logged in.
//            Subject currentUser = SecurityUtils.getSubject();
//            if (currentUser == null)
//                return;
//
//            // Log the user out and kill their session if possible.
//            currentUser.logout();
//            Session session = currentUser.getSession(false);
//            if (session == null)
//                return;
//
//            session.stop();
//        } catch (Exception e) {
//            // Ignore all errors, as we're trying to silently
//            // log the user out.
//        }
//    }
}
