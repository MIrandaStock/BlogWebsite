package cn.roothub.web.admin;

import cn.roothub.dto.Result;
import cn.roothub.entity.User;
import cn.roothub.service.UserService;
import cn.roothub.util.SendMailText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin/node")
public class NodeAdminController {

	@Autowired
	private UserService userService;





	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(String username, String email, @RequestParam(value = "p",defaultValue = "1") Integer p, Model model) {
		if(StringUtils.isEmpty(username)) username = null;
		if(StringUtils.isEmpty(email)) email = null;
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("p", p);
		model.addAttribute("page", userService.pageForAdminByUserType(username, email, p, 25));
		return "admin/node/list";
	}

	/**
	 * 通过用户申请
	 * @param email
	 * @return
	 */

	@RequestMapping(value = "/pass",method = RequestMethod.GET)
	@ResponseBody
	public Result<String> pass(@RequestParam("email") String email){
		try{
			SendMailText.sendEmail(email,"恭喜您成功通过注册申请，欢迎您使用本网站！");
		}catch (Exception e){
		}
		User user=userService.findByEmail(email);
		userService.updateUserType(user.getUserName());
		return new Result<>(true, "已同意注册！");
	}

	/**
	 * 拒绝用户注册申请
	 * @param email
	 * @return
	 */

	@RequestMapping(value = "/refuse",method = RequestMethod.GET)
	@ResponseBody
	public Result<String> refuse(@RequestParam("email") String email){
		try{
			SendMailText.sendEmail(email,"很遗憾您未能通过注册申请，您的注册申请信息将被清除，感谢您对我们的支持！");
		}catch (Exception e){
		}
		User user=userService.findByEmail(email);
		userService.deleteUserByName(user.getUserName());
		return new Result<>(true, "已拒绝注册！");
	}

}
