package com.smallserver.pfmp.dal.object;

import com.alibaba.druid.util.StringUtils;
import com.smallserver.pfmp.util.BaseUitls;

import java.sql.Timestamp;

/**
 * Created by WQ on 2017/12/30.
 */
public class MonitorIpv4InfoDO {

    private Integer   id;
    private String    type;
    private String    typeStr;
    private String    seqId;
    private String    pickName;
    private String    address;
    private String    netType;
    private String    ipv4;
    private Timestamp createTime;
    private String createTimeStr;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MonitorIpv4InfoDO{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append(", loginId='").append(seqId).append('\'');
        sb.append(", pickName='").append(pickName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", netType='").append(netType).append('\'');
        sb.append(", ipv4='").append(ipv4).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }

    public String getSeqId() {
        return StringUtils.isEmpty(seqId) ? "访客seq_id" : seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getPickName() {
        return StringUtils.isEmpty(pickName) ? "访客" : pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLoginName(String loginName) {
        this.pickName = loginName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getCreateTime() {
        return BaseUitls.formatTimeToStr1(createTime);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return BaseUitls.formatTimeToStr1(createTime);
    }

    public String getTypeStr() {
        return type.equalsIgnoreCase("login") ? "登录" : "访问";
    }
}
