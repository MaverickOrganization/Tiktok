package com.dy.dyweb.mapper;

import com.dy.dyweb.dao.WorkInfo;
import com.dy.dyweb.bean.Works;
import java.util.List;

public interface WorksMapper {

    /**
     * 得到用户发布的所有作品
     * @param userId
     * @return
     */
    List<WorkInfo> getUserAllWorks(long userId);

    /**
     * 发布作品
     * @param works
     * @return
     */
    boolean publicWorks(Works works);

    /**
     * 删除作品
     * @param worksId
     * @return
     */
    boolean deleteWorks(long worksId);

    /**
     * 更新作品信息
     * @param works
     * @return
     */
    boolean updateWorks(Works works);
}
