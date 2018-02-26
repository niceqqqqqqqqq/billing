package com.smallserver.pfmp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.common.JsonResult;
import com.smallserver.pfmp.dal.inter.UserInfoDao;
import com.smallserver.pfmp.dal.object.UserInfoDO;
import com.smallserver.pfmp.service.inter.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WQ on 2018/2/1.
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private HttpSession session;

    @Override
    public JSONObject login(UserInfoDO userInfoDO) {
        UserInfoDO user = userInfoDao.queryByPickName(userInfoDO.getPickName());
        if (user != null){
            if (user.getRole() == -1){
                return JsonResult.getError("400", "可能存在非法操作，账号已被封！如有疑问请联系管理员。");
            }
            if (user.getPassword().equalsIgnoreCase(userInfoDO.getPassword())){
                return JsonResult.getSuccess(user);
            } else {
                return JsonResult.getError("401", "账号或者密码错误");
            }
        }
        return JsonResult.getError("401", "账号不存在");
    }

    @Override
    public boolean existEmail(String email) {
        UserInfoDO userInfoDO = userInfoDao.existEmail(email);
        return userInfoDO == null ? false : true;
    }

    @Override
    public JSONObject regist(UserInfoDO userInfoDO) {
        UserInfoDO user = userInfoDao.queryByPickName(userInfoDO.getPickName());
        if (user != null){
            return JsonResult.getError("404", "账号已存在");
        }
        if(userInfoDao.create(userInfoDO)){
            return JsonResult.getSuccess();
        }
        return JsonResult.getError("404", "注册失败");
    }

    @Override
    public JSONObject updatePassword(UserInfoDO userInfoDO) {
        boolean validResult = validLoginSession(userInfoDO);
        if (!validResult){
            return JsonResult.getError("505", "登录会话失效，重修登录后修改信息");
        }
        int count = userInfoDao.updateEmail(userInfoDO);
        if (count > 0) {
            return JsonResult.getSuccess();
        } else {
            return JsonResult.getError("505", "邮箱绑定失败");
        }
    }

    @Override
    public JSONObject updateEmail(UserInfoDO userInfoDO) {
        boolean validResult = validLoginSession(userInfoDO);
        if (!validResult){
            return JsonResult.getError("505", "登录会话失效，重修登录后修改信息");
        }
        int count = userInfoDao.updateEmail(userInfoDO);
        if (count > 0) {
            return JsonResult.getSuccess();
        } else {
            return JsonResult.getError("505", "密码修改失败");
        }
    }

    @Override
    public JSONObject queryUserCount(String loginId) {
        int count = userInfoDao.queryCount();
        return JsonResult.getSuccess(count);
    }

    @Override
    public JSONObject queryAllUser() {
        List list = userInfoDao.queryAll();
        return JsonResult.getSuccess(list == null ? Collections.EMPTY_LIST : list);
    }

    @Override
    public Boolean updateRole(String seqId, Integer role) {
        return userInfoDao.updateRoleBySeqId(role, seqId);
    }

    @Override public Boolean delateUser(String seqId) {
        return userInfoDao.delete(seqId);
    }

    private boolean validLoginSession(UserInfoDO user){
        if( null == session.getAttribute("user")){
            return false;
        }
        return true;
    }
}
