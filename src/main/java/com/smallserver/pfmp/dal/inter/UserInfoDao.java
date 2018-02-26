package com.smallserver.pfmp.dal.inter;

import com.smallserver.pfmp.dal.object.UserInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WQ on 2018/2/1.
 */
public interface UserInfoDao {

    boolean create (UserInfoDO user);
    boolean updateRoleBySeqId (@Param("role") Integer role, @Param("seqId") String seqId);
    boolean delete (@Param("seqId") String seqId);
    int updatePassword (UserInfoDO user);
    int updateEmail (UserInfoDO user);
    int queryCount();
    UserInfoDO queryByPickName (@Param("pickName") String pickName);
    UserInfoDO existEmail (@Param("email") String email);
    List<UserInfoDO> queryAll ();
}
