package com.smallserver.pfmp.controller;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.AsyncUtil;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.object.MonitorIpv4InfoDO;
import com.smallserver.pfmp.dal.object.UserInfoDO;
import com.smallserver.pfmp.dal.object.UserMessageInfoDO;
import com.smallserver.pfmp.service.inter.EmailSenderService;
import com.smallserver.pfmp.service.inter.MonitorDataService;
import com.smallserver.pfmp.service.inter.UserInfoService;
import com.smallserver.pfmp.service.inter.UserMessageInfoService;
import com.smallserver.pfmp.util.AjaxModel;
import com.smallserver.pfmp.util.BaseUitls;
import com.smallserver.pfmp.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by WQ on 2017/11/29.
 */
@Controller
@RequestMapping("/user")
public class UserInfoController extends AjaxModel {

    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserMessageInfoService userMessageInfoService;
    @Autowired
    private MonitorDataService monitorDataService;
    @Autowired
    private EmailSenderService emailSenderService;

    private static final String [] registNames = {"admin", "root", "管理员", "系统", "平台", "钱呢", "王琦", "开发","测试"};


    @GetMapping(value = "/login")
    public void userLogin() {
        String checkCode = null;
        try {
            checkCode = session.getAttribute("imageCode").toString();
        } catch (Exception e) {
            print(JsonResult.getError("404", "验证码获取有误，点击切换重新获取!"));
            return;
        }
        String inputCheckCode = request.getParameter("checkCode");
        if (!checkCode.equalsIgnoreCase(inputCheckCode)) {
            print(JsonResult.getError("200", "验证码错误!"));
            return;
        }
        String pickName = request.getParameter("pickName");
        String password = request.getParameter("password");
        UserInfoDO userInfo = new UserInfoDO(pickName, password);
        JSONObject result = userInfoService.login(userInfo);
        if ((Boolean) result.get("success")) {
            UserInfoDO user = (UserInfoDO) result.get("data");
            session.setAttribute("user", user);
            session.setAttribute("seqId", user.getSeqId());
            session.setAttribute("pickName", user.getPickName());
            String ip = null == session.getAttribute("ip") ? "" : session.getAttribute("ip").toString();
            String address = null == session.getAttribute("address") ? "" : session.getAttribute("address").toString();
            String netType = null == session.getAttribute("net_type") ? "" : session.getAttribute("net_type").toString();
            // 异步调用监控登录ip、时间点
            AsyncUtil.execute(new Runnable() {
                @Override
                public void run() {
                    MonitorIpv4InfoDO ipInfo = new MonitorIpv4InfoDO();
                    ipInfo.setSeqId(user.getSeqId());
                    ipInfo.setLoginName(user.getPickName());
                    ipInfo.setType("login");
                    ipInfo.setNetType(netType);
                    ipInfo.setAddress(address);
                    ipInfo.setIpv4(ip);
                    monitorDataService.monitorRequesterIpv4(ipInfo);
                }
            });
        }
        print(result);
        return;
    }

    @PostMapping(value = "/delete")
    public void deleteUser() {
        String seqId = request.getParameter("seq_id");
        if (userInfoService.delateUser(seqId)){
            print(JsonResult.getSuccess());return;
        }
        print(JsonResult.getError("304", "删除失败"));return;
    }

    @PostMapping(value = "/regCode/send")
    public void sendRegEmailCheckCode(){
        String email = request.getParameter("email");

        // 校验邮箱是否已经注册
        if(userInfoService.existEmail(email)){
            print(JsonResult.getError("邮箱已占用"));
            return;
        }
        String title = "钱呢，邮箱验证";
        // 生成验证码
        ImageCodeUtil.createImage();
        String checkCode = session.getAttribute("imageCode").toString();
        session.setAttribute("regEmailCode", checkCode);
        String content = "你好，您的邮箱验证码为："+checkCode+"，[30分钟内有效！]";
        boolean sendEmail = emailSenderService.sendMail("", email, title, content);
        if (sendEmail){
            print(JsonResult.getSuccess());
        } else {
            print(JsonResult.getError("验证码发送失败，请稍后重试！"));
        }
    }

    @PostMapping(value = "/regist")
    public void userRegist() {
        String checkCode = session.getAttribute("regEmailCode").toString();
        String inputCheckCode = request.getParameter("checkCode");
        String pickName = request.getParameter("pickName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if (!checkCode.equalsIgnoreCase(inputCheckCode)) {
            print(JsonResult.getError("200", "邮箱验证码错误!"));
            return;
        }
        for (String names : registNames){
            if (pickName.indexOf(names) > -1){
                print(JsonResult.getError("200", "注册失败，用户名中包含敏感词\t\""+names+"\""));
                return;
            }
        }
        UserInfoDO userInfo = new UserInfoDO();
        userInfo.setPickName(pickName);
        userInfo.setEmail(email);
        userInfo.setSeqId(UUID.randomUUID().toString().replaceAll("-", ""));
        userInfo.setPassword(password);
        userInfo.setPhoto("/images/photo/default.png");
        JSONObject result = userInfoService.regist(userInfo);
        AsyncUtil.execute(new Runnable() {
            @Override
            public void run() {
                UserMessageInfoDO userMessageInfoDO = new UserMessageInfoDO();
                userMessageInfoDO.setReceiver(pickName);
                userMessageInfoDO.setSender("系统");
                userMessageInfoDO.setType("个人信息");
                userMessageInfoDO.setTitle("账号注册通知");
                userMessageInfoDO.setContent("账号：" + pickName + "已注册成功，赶紧体验一下吧");
                userMessageInfoService.send(userMessageInfoDO);

                String title = "钱呢，用户注册";
                // 生成验证码
                String content = "用户注册邮件通知： 昵称为'" + pickName + "'的用户在" +
                                 BaseUitls.formatTimeToStr1(new Timestamp(System.currentTimeMillis())) + "注册了钱呢。";
                boolean sendEmail = emailSenderService.sendMail("", "1124602935@qq.com", title, content);
                if (sendEmail){
                    print(JsonResult.getSuccess());
                } else {
                    print(JsonResult.getError("验证码发送失败，请稍后重试！"));
                }

            }
        });
        print(result);
        return;
    }

    @GetMapping(value = "/query/size")
    public void queryMessageListSize() {
        if (session.getAttribute("pickName") == null) {
            print(JsonResult.getError("404", "登录会话失效"));
            return;
        }
        String loginId = session.getAttribute("pickName").toString();
        print(userInfoService.queryUserCount(loginId));
    }

    @PostMapping(value = "/update/pwd")
    public void updatePassword() {
        UserInfoDO createUser = (UserInfoDO) session.getAttribute("user");
        String oldPassword = request.getParameter("oldPassword");
        if (oldPassword.equals(createUser.getPassword())) {
            createUser.setPassword(request.getParameter("newPassword"));
            JSONObject result = userInfoService.updatePassword(createUser);
            if ((Boolean) result.get("success")) {
                session.setAttribute("user", createUser);
                AsyncUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        UserMessageInfoDO userMessageInfoDO = new UserMessageInfoDO();
                        userMessageInfoDO.setReceiver(createUser.getPickName());
                        userMessageInfoDO.setSender("系统");
                        userMessageInfoDO.setType("个人信息");
                        userMessageInfoDO.setTitle("密码修改成功");
                        userMessageInfoDO.setContent("账号：" + createUser.getPickName() + "密码修改成功");
                        userMessageInfoService.send(userMessageInfoDO);
                    }
                });
                print(result);
                return;
            }
        } else {
            print(JsonResult.getError("405", "旧密码验证错误"));
            return;
        }
    }

    @PostMapping(value = "/update/email")
    public void updateEmail() {
        UserInfoDO createUser = (UserInfoDO) session.getAttribute("user");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        String checkCode = session.getAttribute("regEmailCode").toString();
        if (!checkCode.equalsIgnoreCase(code)) {
            print(JsonResult.getError("200", "邮箱验证码错误!"));
            return;
        }
        createUser.setEmail(email);
        JSONObject result = userInfoService.updateEmail(createUser);
        if ((Boolean) result.get("success")) {
            session.setAttribute("user", createUser);
            AsyncUtil.execute(new Runnable() {
                @Override
                public void run() {
                    UserMessageInfoDO userMessageInfoDO = new UserMessageInfoDO();
                    userMessageInfoDO.setReceiver(createUser.getPickName());
                    userMessageInfoDO.setSender("系统");
                    userMessageInfoDO.setType("个人信息");
                    userMessageInfoDO.setTitle("邮箱绑定成功");
                    userMessageInfoDO.setContent("账号：" + createUser.getPickName() + "成功绑定邮箱："+email);
                    userMessageInfoService.send(userMessageInfoDO);
                }
            });
            print(result);
            return;
        }
    }

    @GetMapping(value = "/valid.login")
    public void validLoginStatus() {
        if (null == session.getAttribute("user")) {
            print(JsonResult.getError("404", "未登录"));
            return;
        }
        print(JsonResult.getSuccess());
        return;
    }

    @GetMapping (value = "/query.all")
    public void queryAll() {
        print(userInfoService.queryAllUser());return;
    }

    @PostMapping (value = "/role/update")
    public void updateRole() {
        String seqId = request.getParameter("seq_id");
        Integer role = Integer.parseInt(request.getParameter("role"));
        if (userInfoService.updateRole(seqId, role)){
            print(JsonResult.getSuccess());return;
        }
        print(JsonResult.getError("304", "授权失败"));return;
    }

    @GetMapping(value = "/out")
    public String logoutSystem() throws ServletException, IOException {
        session.setAttribute("user", null);
        session.setAttribute("seqId", null);
        session.setAttribute("pickName", null);
        return "forward:/";
    }
}
