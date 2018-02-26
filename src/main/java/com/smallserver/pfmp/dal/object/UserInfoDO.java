package com.smallserver.pfmp.dal.object;

import com.smallserver.pfmp.util.BaseUitls;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by WQ on 2018/2/1.
 */
public class UserInfoDO implements Serializable{

    private Integer id;
    private Integer status;
    private String seqId;
    private String pickName;
    private String password;
    private String mobile;
    private String photo;
    private Integer role;
    private String email;
    private String roleStr;
    private Timestamp createTime;
    private String createTimeStr;

    public UserInfoDO() {
    }

    public UserInfoDO(String pickName, String password) {
        this.pickName = pickName;
        this.password = password;
    }

    public String getRoleStr() {
        return getRoleStr(role);
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return BaseUitls.formatTimeToStr1(createTime);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfoDO{");
        sb.append("id=").append(id);
        sb.append(", seqId='").append(seqId).append('\'');
        sb.append(", pickName='").append(pickName).append('\'');
        sb.append(", status=").append(status);
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", createTimeStr='").append(createTimeStr).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private String getRoleStr(Integer role){
        if (role == 0){
            return "普通用户";
        } else if (role == 1){
            return "管理员";
        } else if (role == -1){
            return "账号已冻结";
        }
        return "普通用户";
    }
}
