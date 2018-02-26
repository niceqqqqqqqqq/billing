package com.smallserver.pfmp.dal.object;

import com.smallserver.pfmp.enums.FeedBackEnum;
import com.smallserver.pfmp.util.BaseUitls;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by WQ on 2018/2/1.
 */
public class FeedBackDO implements Serializable{

    private Integer id;
    private Integer upId;
    private Integer status;
    private Integer level;
    private String  type;
    private String  seqId;
    private String  pickName;
    private String  content;
    private String   photo;
    private Timestamp createTime;
    private String createTimeStr;

    public FeedBackDO(){}

    public FeedBackDO(Integer level, String type, String seqId, String pickName, String content, String photo) {
        this.level = level;
        this.type = type;
        this.seqId = seqId;
        this.pickName = pickName;
        this.content = content;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpId() {
        return upId;
    }

    public void setUpId(Integer upId) {
        this.upId = upId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreateTime() {
        return BaseUitls.formatTimeToStr1(createTime);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FeedBackDO{");
        sb.append("id=").append(id);
        sb.append(", upId=").append(upId);
        sb.append(", status=").append(status);
        sb.append(", level=").append(level);
        sb.append(", type='").append(type).append('\'');
        sb.append(", seqId='").append(seqId).append('\'');
        sb.append(", pickName='").append(pickName).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", createTimeStr='").append(createTimeStr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
