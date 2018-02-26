package com.smallserver.pfmp.dal.inter;

import com.smallserver.pfmp.dal.object.FeedBackDO;
import java.util.List;

/**
 * Created by WQ on 2018/2/1.
 */
public interface FeedBackDao {

    boolean insert(FeedBackDO feedBackDO);
    boolean update(Integer id);
    List<FeedBackDO> query();
}
