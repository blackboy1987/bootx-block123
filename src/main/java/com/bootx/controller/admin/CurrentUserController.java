package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.entity.Admin;
import com.bootx.security.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController("adminCurrentUserController")
@RequestMapping("/block/admin/currentUser")
public class CurrentUserController extends BaseController {

    @PostMapping
    public Map<String,Object> index(@CurrentUser Admin admin, HttpServletResponse response){
        return getUser(admin,response);
    }

    private Map<String,Object> getUser(Admin admin, HttpServletResponse response) {
        Map<String,Object> data=  new HashMap<>();
        if(admin!=null){
            data.put("name",admin.getName());
            data.put("title",admin.getUsername());
            data.put("group","group");
            data.put("signature","signature");
            data.put("userid",admin.getId());
            data.put("access","admin");
            data.put("unreadCount",0);
        }else{
            data.put("code",999);
            response.setContentType("application/json");
            data.put("message", Message.error("请先登录"));
            response.setStatus(999);
        }
        return data;
    }
}
