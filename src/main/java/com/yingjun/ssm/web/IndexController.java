package com.yingjun.ssm.web;

import com.yingjun.ssm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
public class IndexController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    //private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        List<String> permissions = userService.findPermissions(username); // username -> user.roleIds -> role.resourceIds -> resource.permission
        //List<Resource> menus = resourceService.findMenus(permissions); // resource.permission -> resource.name (type=menu and parentId!=0)

        List<Menu> menuList = new ArrayList<Menu>();
        Menu m1 = new Menu();
        m1.setName("组织机构管理");
        m1.setUrl("/organization");
        menuList.add(m1);

        Menu m2 = new Menu();
        m2.setName("用户管理");
        m2.setUrl("/user");
        menuList.add(m2);
        model.addAttribute("menus", menuList);

        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    public static  class Menu {
        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public String getName() {
            return name;
        }

        public void setUrl(String url) {

            this.url = url;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
