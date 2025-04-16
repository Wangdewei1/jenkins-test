package cn.luminous.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: jenkins-test
 * @description:
 * @author: wang
 * @create: 2025-04-16 17:43
 **/
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index/index";
    }



}
