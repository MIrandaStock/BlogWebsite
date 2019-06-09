package cn.roothub.service;

import cn.roothub.dto.PageDataBody;
import cn.roothub.dto.UserExecution;
import cn.roothub.entity.User;

import javax.servlet.http.HttpServletRequest;


/**
 * RootUserService
 * @author sen
 * 2018年5月6日
 * 下午9:10:40
 * TODO
 */
public interface UserService {

	/**
	 * 根据ID查找用户
	 * @param userId
	 * @return
	 */
	User findById(Integer userId);

	/**
	 * 根据昵称查找注册成功用户
	 * @param userName
	 * @return
	 */
	User findByNameno(String userName);
	
	/**
	 * 根据昵称在所有用户里查找用户
	 * @param userName
	 * @return
	 */
	User findByName(String userName);


	/**
	 * 根据email查找用户
	 * @param email
	 * @return
	 */
	User findByEmail(String email);
	
	/**
	 * 通过昵称和密码查找用户
	 * @param password
	 * @return
	 */
	User findByUserNameAndPassword(String userName,String password);
	
	/**
	 * 根据邮箱和密码查找用户
	 * @param email
	 * @param password
	 * @return
	 */
	User findByEmailAndPassword(String email, String password);
	

	
	/**
	 * 更新积分
	 * @param score:积分值
	 * @param userId:用户ID
	 * @return
	 */
	void updateScore(Integer score,Integer userId);

	/**
	 *
	 * 同意注册后更新类型
	 */
     void updateUserType(String userName);
	/**
	 * 更新头像
	 * @param avatarBase64:base64格式的图片
	 * @param path:自定义保存路径
	 * @param user:用户对象
	 * @param request
	 */
	void updateAvatar(String avatarBase64,String path,User user,HttpServletRequest request);
	
	/**
	 * 分页查询所有用户，倒序
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageDataBody<User> page(Integer pageNumber, Integer pageSize);
	
	/**
	 * 更新用户
	 * @param user
	 */
	UserExecution updateUser(User user);
	
	/**
	 * 根据ID删除用户
	 * @param userId
	 */
	void deleteUserById(Integer userId);
	
	/**
	 * 根据昵称删除用户
	 * @param userName
	 */
	void deleteUserByName(String userName);
	
	/**
	 * 用户注册
	 * @param user
	 */
	UserExecution save(User user);
	
	UserExecution createUser(String username,String password,String email);
	
	/**
	 * 统计所有注册会员的数量
	 * @return
	 */
	int countUserAll();
	
	/**
	 * 积分值
	 * @param userId
	 * @return
	 */
	int countScore(Integer userId);
	
	/**
	 * 统计当天用户数
	 * @return
	 */
	int countToday();
	
	PageDataBody<User> pageForAdmin(String username, String email, Integer pageNumber, Integer pageSize);

	/**
	 * 后台通过用户类型查询申请注册的用户
	 * @return
	 */
	PageDataBody<User> pageForAdminByUserType(String username, String email, Integer pageNumber, Integer pageSize);
	
	int countAllForAdmin(String username,String email);
	
	/**
	 * 更新用户，主要用于后台操作
	 * @param user
	 */
	void updateAdmin(User user);
	
	/**
	 * 删除用户，主要用于后台操作
	 * @param id
	 */
	void deleteAdmin(Integer id);
}
