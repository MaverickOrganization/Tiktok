package com.dy.dyweb.control;

import com.dy.dyweb.bean.ResultBean;
import com.dy.dyweb.bean.User;
import com.dy.dyweb.dao.UserDao;
import com.dy.dyweb.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserControl {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public ResultBean login(User user) {
        UserDao userByPhone = userService.selectUserByPhone(user.getPhone());
        if (userByPhone == null) {
            return ResultBean.fail("手机号不存在");
        }
        UserDao userDao = userService.login(user);
        if (userDao == null) {
            return ResultBean.fail("密码错误");
        }
        return ResultBean.ok(userDao);
    }

    @RequestMapping("/selectByPhone")
    public ResultBean selectByPhone(User user) {
        UserDao userDao = userService.selectUserByPhone(user.getPhone());
        if (userDao == null) {
            return ResultBean.fail("该手机号不存在!");
        }
        return ResultBean.ok(user);
    }

    @RequestMapping("/register")
    public ResultBean register(@RequestBody User user) {
        UserDao userDao = userService.selectUserByPhone(user.getPhone());
        if (userDao != null) {
            return ResultBean.ok(ResultBean.PHONE_EXIST, "手机号已存在！");
        }
        boolean register = userService.register(user);
        if (register) {
            return ResultBean.ok("注册成功!");
        } else {
            return ResultBean.fail("服务端异常!");
        }
    }

    @RequestMapping("/selectUser")
    public ResultBean selectUser(Long userId) {
        UserDao userDao = userService.selectUser(userId);
        if (userDao == null) {
            return ResultBean.fail("该用户不存在!");
        }
        return ResultBean.ok(userDao);
    }
}
