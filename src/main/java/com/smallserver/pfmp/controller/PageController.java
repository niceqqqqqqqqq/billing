package com.smallserver.pfmp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by WQ on 2017/11/29.
 */
@Controller
public class PageController {

    @Autowired
    private HttpSession session;

    @GetMapping(value = {"/", "/login"})
    public String page() {
        return "/common/login";
    }

    @RequestMapping("/to/{packageName}/{htmlName}")
    public String toPage(@PathVariable String packageName, @PathVariable String htmlName) {
        return packageName + "/" + htmlName;
    }
}
