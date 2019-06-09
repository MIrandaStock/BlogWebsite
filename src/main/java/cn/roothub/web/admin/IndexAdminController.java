package cn.roothub.web.admin;

import cn.roothub.dto.Result;
import cn.roothub.entity.AdminUser;
import cn.roothub.service.AdminUserService;
import cn.roothub.service.ReplyService;
import cn.roothub.service.TopicService;
import cn.roothub.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author miansen.wang
 * @date 2019年2月25日 下午8:51:25
 */
@Controller
@RequestMapping("/admin")
public class IndexAdminController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    // 后台首页
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model) {
        // 查询当天新增话题
        model.addAttribute("topic_count", topicService.countToday());
        // 查询当天新增评论
        model.addAttribute("comment_count", replyService.countToday());
        // 查询当天新增用户
        model.addAttribute("user_count", userService.countToday());
        return "/admin/index";
    }

    // 后台登录页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/admin/index";
        }
        return "/admin/login";
    }

    // 后台登录处理
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, @RequestParam(defaultValue = "0") Boolean rememberMe, Model model, HttpServletRequest request) {
//        AdminUser adminUser=adminUserService.getByName(username);
//        ApiAssert.notNull(adminUser, "用户不存在");
//        ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, adminUser.getPassword()), "密码不正确");

        try {
            // 添加用户认证信息
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
                //进行验证，这里可以捕获异常，然后返回对应信息
                subject.login(token);
            }
        } catch (AuthenticationException e) {
            model.addAttribute("error", "用户名或密码错误");
            model.addAttribute("username", username);
            return "/admin/login";
        }
        return "redirect:/admin/index";
    }

    // 出错页面
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        return "admin/error/error";
    }

    /**
     * 获取后台登录用户的信息
     *
     * @return
     */
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    @ResponseBody
    public Result<AdminUser> getAdminUser() {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        return new Result<>(true, user);
    }
}
