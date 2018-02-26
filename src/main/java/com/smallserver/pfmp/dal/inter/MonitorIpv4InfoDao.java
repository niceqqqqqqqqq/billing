package com.smallserver.pfmp.dal.inter;

import com.smallserver.pfmp.dal.object.MonitorIpv4InfoDO;
import org.apache.ibatis.annotations.Param;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by WQ on 2017/12/25.
 */
public interface MonitorIpv4InfoDao {

    boolean addMonitor(MonitorIpv4InfoDO ipv4Info);
    List<MonitorIpv4InfoDO> queryLoginIp();
    List<MonitorIpv4InfoDO> queryUserIp();
    List<MonitorIpv4InfoDO> existTwoHours(@Param("ip") String ip, @Param("time") Timestamp time);
    List<MonitorIpv4InfoDO> queryLogByLogin(@Param("seqId") String seqId);

}
