package cn.roothub.web.front;

import cn.roothub.base.BaseEntity;
import cn.roothub.config.SiteConfig;
import cn.roothub.dto.PageDataBody;
import cn.roothub.dto.Result;
import cn.roothub.dto.UserExecution;
import cn.roothub.entity.Tab;
import cn.roothub.entity.Tag;
import cn.roothub.entity.Topic;
import cn.roothub.entity.User;
import cn.roothub.exception.ApiAssert;
import cn.roothub.service.*;
import cn.roothub.util.Base64Util;
import cn.roothub.util.CookieAndSessionUtil;
import cn.roothub.util.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller // 标注这是一个控制类，类名不能和注解名一样
// @RequestMapping("/root") // 访问父路径
public class IndexController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private CollectService collectDaoService;
	@Autowired
	private RedisTemplate<String, List<String>> redisTemplate;
	@Autowired
	private TabService tabService;
	@Autowired
	private SiteConfig siteConfig;
	@Autowired
	private BaseEntity baseEntity;

	/**
	 * 首页
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json; charset=utf-8") // 访问子路径
	private String index(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "p", defaultValue = "1") Integer p,
			@RequestParam(value = "tab", defaultValue = "all") String tab) {
		PageDataBody<Topic> page = topicService.pageAllByTab(p, 10, tab);
		List<Tab> tabList = tabService.selectAll();
		// 热门话题榜
		List<Topic> findHot = topicService.findHot(0, 10);
		// 今日等待回复的话题
		List<Topic> findTodayNoReply = topicService.findTodayNoReply(0, 10);
		// 最热标签
		PageDataBody<Tag> tag = topicService.findByTag(1, 10);
		// 注册会员的数量
		int countUserAll = userService.countUserAll();
		// 所有话题的数量
		int countAllTopic = topicService.countAllTopic(null);
		// 所有评论的数量
		int countAllReply = replyService.countAll();
		request.setAttribute("page", page);
		request.setAttribute("findHot", findHot);
		request.setAttribute("findTodayNoReply", findTodayNoReply);
		request.setAttribute("tabList", tabList);
		request.setAttribute("tab", tab);
		request.setAttribute("tag", tag);
		request.setAttribute("countUserAll", countUserAll);
		request.setAttribute("countAllTopic", countAllTopic);
		request.setAttribute("countAllReply", countAllReply);
		return "index";
	}

	/**
	 * 注册页面
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	private String register(HttpServletRequest request) {
		return "register";
	}

	/**
	 * 注册接口
	 * @param username
	 * @param password
	 * @param email
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	private Result<UserExecution> register(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("email") String email,
			HttpServletRequest request) {
		ApiAssert.notEmpty(username, "请输入用户名");
		ApiAssert.notEmpty(password, "请输入密码");
		ApiAssert.notEmpty(email, "请输入邮箱");
		User user = userService.findByName(username);
		ApiAssert.isNull(user, "用户已存在");
		user = userService.findByEmail(email);
		ApiAssert.isNull(user, "邮箱已存在");
		UserExecution save = userService.createUser(username, password, email);
		return new Result<UserExecution>(true, save);
	}

	/**
	 * 登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String login(HttpServletRequest request) {
		return "login";
	}

	/**
	 * 登录接口
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	private Result<User> login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		User user = userService.findByNameno(username);
		ApiAssert.notNull(user, "用户不存在");
		ApiAssert.isTrue(new BCryptPasswordEncoder().matches(password, user.getPassword()), "密码不正确");
		// 设置cookie
		CookieAndSessionUtil.setCookie(siteConfig.getCookieConfig().getName(),
				Base64Util.encode(user.getThirdAccessToken()), siteConfig.getCookieConfig().getMaxAge(),
				siteConfig.getCookieConfig().getPath(), siteConfig.getCookieConfig().isHttpOnly(), response);
		// 设置session
		CookieAndSessionUtil.setSession(request, "user", user);
		return new Result<>(true, user);
	}

	/**
	 * 退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	private String logout(HttpServletRequest request, HttpServletResponse response) {
		// stringRedisTemplate.delete("user");
		CookieAndSessionUtil.removeSession(request, "user");
		CookieAndSessionUtil.removeCookie(response, siteConfig.getCookieConfig().getName(),
				siteConfig.getCookieConfig().getPath(), siteConfig.getCookieConfig().isHttpOnly());
		return "redirect:/";
	}

	/**
	 * 标签页
	 * 
	 * @param request
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	private String tag(HttpServletRequest request, @RequestParam(value = "p", defaultValue = "1") Integer p) {
		PageDataBody<Tag> tag = topicService.findByTag(p, 10);
		request.setAttribute("tag", tag);
		return "tag/tag";
	}

	@RequestMapping(value = "/session", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, String> session(HttpServletRequest request) {
		User user = getUser(request);
		HashedMap map = new HashedMap();
		if (user != null) {
			map.put("success", true);
			map.put("user", user.getUserName());
			return map;
		} else {
			map.put("success", false);
			map.put("user", "");
			return map;
		}
	}

	/**
	 * 搜索
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	private String search(HttpServletRequest request, @RequestParam("s") String search,
			@RequestParam(value = "p", defaultValue = "1") Integer p) {
		PageDataBody<Topic> pageLike = topicService.pageLike(p, 10, search);
		request.setAttribute("pageLike", pageLike);
		request.setAttribute("search", search);
		return "search/search";
	}

	/**
	 * 关于
	 * 
	 * @return
	 */
	@RequestMapping(value = "/about")
	private String about() {
		return "foot/about";
	}

}
