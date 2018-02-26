package com.smallserver.pfmp.dal.object;

import com.smallserver.pfmp.util.BaseUitls;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by WQ on 2017/12/31.
 */
public class UserMessageInfoDO implements Serializable {

    private Integer     id;
    private String      receiver;
    private String      sender;
    private String      type;
    private String      title;
    private String      content;
    private Integer     status;
    private Timestamp   createTime;

    public UserMessageInfoDO(){}

    public UserMessageInfoDO(String receiver, String sender, String type, String title, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return BaseUitls.formatTimeToStr1(createTime);
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserMessageInfoDO{");
        sb.append("id=").append(id);
        sb.append(", receiver='").append(receiver).append('\'');
        sb.append(", sender='").append(sender).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
