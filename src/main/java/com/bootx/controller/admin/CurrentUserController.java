package com.bootx.controller.admin;

import com.bootx.entity.Admin;
import com.bootx.security.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("adminCurrentUserController")
@RequestMapping("/block/admin/currentUser")
public class CurrentUserController extends BaseController {

    @PostMapping
    public Map<String,Object> index(@CurrentUser Admin admin){
        return getUser(admin);
    }

    private Map<String,Object> getUser(Admin admin) {
        Map<String,Object> data=  new HashMap<>();
        data.put("name",admin.getName());
        data.put("title",admin.getUsername());
        data.put("group","group");
        data.put("signature","signature");
        data.put("userid",admin.getId());
        data.put("access","admin");
        data.put("unreadCount",0);
        return data;
    }
}
