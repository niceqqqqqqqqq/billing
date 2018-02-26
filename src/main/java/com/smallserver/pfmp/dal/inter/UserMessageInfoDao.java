package com.smallserver.pfmp.dal.inter;

import com.smallserver.pfmp.dal.object.UserMessageInfoDO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * Created by WQ on 2017/12/31.
 */
public interface UserMessageInfoDao {

    boolean send(UserMessageInfoDO message);
    boolean batchSend(List<UserMessageInfoDO> list);
    boolean updateToRead(Integer id);
    int queryCount(@Param("receiver") String receiver);
    List<UserMessageInfoDO> query(Map<String, Object> map);
}
