package com.smallserver.pfmp.controller;

import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.service.inter.UserMessageInfoService;
import com.smallserver.pfmp.util.AjaxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WQ on 2017/12/16.
 */
@Controller
@RequestMapping(value = "/msg")
public class UserMessageController extends AjaxModel {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @Autowired
    private UserMessageInfoService userMessageInfoService;

    @GetMapping(value = "/query/list")
    public void queryMessageList() {
        if (session.getAttribute("pickName") == null) {
            print(JsonResult.getError("404", "登录会话失效"));
            return;
        }
        String loginId = session.getAttribute("pickName").toString();
        Map<String, Object> map = new HashMap<>();
        map.put("receiver", loginId);
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        map.put("type", request.getParameter("type"));
        map.put("offset", 0);
        map.put("size", 10);
        print(userMessageInfoService.query(map));
    }

    @GetMapping(value = "/query/size")
    public void queryMessageListSize() {
        if (session.getAttribute("pickName") == null) {
            print(JsonResult.getError("404", "登录会话失效"));
            return;
        }
        String loginId = session.getAttribute("pickName").toString();
        print(userMessageInfoService.queryNewMessageCount(loginId));
    }

    @GetMapping(value = "/update/read")
    public void updateToRead() {
        Integer id = Integer.parseInt(request.getParameter("id").toString());
        print(userMessageInfoService.updateToRead(id));
    }
}
