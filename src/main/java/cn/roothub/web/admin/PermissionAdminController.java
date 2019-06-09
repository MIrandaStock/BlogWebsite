package cn.roothub.web.admin;

import cn.roothub.dto.Result;
import cn.roothub.entity.Permission;
import cn.roothub.exception.ApiAssert;
import cn.roothub.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * <p></p>
 * @author: miansen.wang
 * @date: 2019-03-05
 */
@Controller
@RequestMapping("/admin/permission")
public class PermissionAdminController {

	@Autowired
	private PermissionService permissionService;
	

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model) {
		Map<String, Object> permissionMap = permissionService.permissionMap();
		model.addAttribute("permissionMap", permissionMap);
		return "admin/permission/list";
	}
	
	/**
	 * 添加权限
	 * @param pid: 父权限ID
	 * @param pname: 父权限名
	 * @param id: 权限ID
	 * @param name: 权限名
	 * @param value: 权限值
	 * @return
	 */

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Result<String> add(Integer pid,String pname,Integer id,String name,String value){
		ApiAssert.notEmpty(name, "权限名不能为空");
		ApiAssert.notEmpty(value, "权限值不能为空");
		permissionService.save(pname, name, value);
		return new Result<>(true, "添加权限成功");
	}
	
	/**
	 * 编辑权限
	 * @param pid: 父权限ID
	 * @param pname: 父权限名
	 * @param id: 权限ID
	 * @param name: 权限名
	 * @param value: 权限值
	 * @return
	 */

	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Result<String> edit(Integer pid,String pname,Integer id,String name,String value){
		ApiAssert.notNull(id, "权限ID不能为空");
		ApiAssert.notEmpty(name, "权限名不能为空");
		ApiAssert.notEmpty(value, "权限值不能为空");
		Permission permission = permissionService.getById(id);
		ApiAssert.notNull(permission, "权限不存在");
		permission.setPermissionName(name);
		permission.setPermissionValue(value);
		permission.setUpdateDate(new Date());
		permissionService.update(permission);
		return new Result<>(true, "编辑权限成功");
	}
	

	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public String delete(Integer id,String name) {
		permissionService.remove(id, name);
		return "redirect: /admin/permission/list";
	}
}
