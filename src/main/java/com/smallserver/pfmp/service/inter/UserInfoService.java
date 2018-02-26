package com.smallserver.pfmp.service.inter;

import com.alibaba.fastjson.JSONObject;
import com.smallserver.pfmp.dal.object.UserInfoDO;

/**
 * Created by WQ on 2018/2/1.
 */
public interface UserInfoService {

    /**
     * 用户登录
     * @param userInfoDO
     * @return
     */
    JSONObject login(UserInfoDO userInfoDO);

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    boolean existEmail(String email);

    /**
     * 用户注册
     * @param userInfoDO
     * @return
     */
    JSONObject regist(UserInfoDO userInfoDO);

    /**
     * 更新密码
     * @param userInfoDO
     * @return
     */
    JSONObject updatePassword(UserInfoDO userInfoDO);

    /**
     * 绑定邮箱
     * @param userInfoDO
     * @return
     */
    JSONObject updateEmail(UserInfoDO userInfoDO);

    JSONObject queryUserCount(String loginId);

    /**
     * 获取全部用户信息数据
     * @return
     */
    JSONObject queryAllUser();

    /**
     * 用户角色控制
     * @param seqId
     * @param role
     * @return
     */
    Boolean updateRole(String seqId, Integer role);

    /**
     * 删除用户
     * @param seqId
     * @return
     */
    Boolean delateUser(String seqId);

}
