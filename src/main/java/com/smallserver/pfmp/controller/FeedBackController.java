package com.smallserver.pfmp.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.dal.object.FeedBackDO;
import com.smallserver.pfmp.dal.object.UserInfoDO;
import com.smallserver.pfmp.service.inter.FeedBackService;
import com.smallserver.pfmp.util.AjaxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by WQ on 2017/11/29.
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedBackController extends AjaxModel {

    @Autowired
    private HttpSession        session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FeedBackService    feedBackService;

    @PostMapping (value = {"/add"})
    public String addFeedBack() {
        String type = request.getParameter("type");
        String content = request.getParameter("content");
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return "forward:/";
        }
        UserInfoDO user = (UserInfoDO) userObj;
        String seqId = user.getSeqId();
        String pickName = user.getPickName();
        String photo = user.getPhoto();
        FeedBackDO feedBackDO = new FeedBackDO(1, type, seqId, pickName, content, photo);
        JSONObject result = feedBackService.add(feedBackDO);
        print(result); return null;
    }

    @GetMapping(value = {"/query/all"})
    public String queryAllFeedBack() {
        Object userObj = session.getAttribute("user");
        if (userObj == null) {
            return "forward:/";
        }
        JSONObject result = feedBackService.queryAll();
        print(result); return null;
    }
}
