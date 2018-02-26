package com.smallserver.pfmp.controller;

import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.object.UserInfoDO;
import com.smallserver.pfmp.util.AjaxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by WQ on 2017/12/26.
 */
@Controller
@RequestMapping(value = "/session")
public class RequestSessionDataController extends AjaxModel {

    @Autowired
    private HttpSession session;

    @GetMapping(value = "/{key}")
    public void getLoginUserInfo(@PathVariable String key) {
        Object obj = session.getAttribute(key);
        switch (key) {
            case "user":
                UserInfoDO user = obj == null ? null : (UserInfoDO) obj;
                if (user == null) {
                    print(JsonResult.getError("404", "未登录"));
                    return;
                }
                print(JsonResult.getSuccess(user));
                return;
            default:
                String data = (obj == null ? "" : obj.toString());
                print(JsonResult.getSuccess(data));
                return;
        }

    }
}
