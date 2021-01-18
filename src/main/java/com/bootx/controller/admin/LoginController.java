package com.bootx.controller.admin;

import com.bootx.entity.Admin;
import com.bootx.security.UserAuthenticationToken;
import com.bootx.service.AdminService;
import com.bootx.service.UserService;
import com.bootx.util.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("adminLoginController")
@RequestMapping("/block/admin/login")
public class LoginController extends BaseController {

    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;

    @PostMapping
    public Map<String,Object> index(String type,String username, String password, HttpServletRequest request) {
        Map<String,Object> data = new HashMap<>();
        data.put("type",type);
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            data.put("status","error");
            data.put("content","请输入用户名或密码");
            return data;
        }
        Admin admin = adminService.findByUsername(username);
        if(admin==null){
            data.put("status","error");
            data.put("content","用户名或密码输入错误");
            return data;
        }
        if(!admin.isValidCredentials(password)){
            data.put("status","error");
            data.put("content","用户名或密码输入错误");
            return data;
        }
        data.put("status","ok");
        data.put("content","登陆成功");
        Map<String,Object> user = new HashMap<>();
        user.put("id",admin.getId());
        user.put("username",admin.getUsername());
        data.put("user",user);
        data.put("token", JWTUtils.create(admin.getId()+"",user));
        userService.login(new UserAuthenticationToken(Admin.class, username, password, false, request.getRemoteAddr()));
        return data;
    }
}
