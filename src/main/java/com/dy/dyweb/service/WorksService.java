package com.dy.dyweb.service;

import com.dy.dyweb.bean.Works;
import com.dy.dyweb.dao.WorksDao;

import java.util.List;

public interface WorksService {
    /**
     * 得到用户发布的所有作品
     * @param works
     * @return
     */
    List<WorksDao> getUserAllWorks(Works works);

    /**
     * 获取所以发布作品
     * @param works
     * @return
     */
    List<WorksDao> getAllWorks(Works works);

    /**
     * 发布作品
     * @param works
     * @return
     */
    boolean addWorks(Works works);

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
