package com.bootx.controller.api.app.user.v2;

import com.bootx.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user/v2/new")
public class NewsController {
    @Autowired
    private ArticleService articleService;

}
