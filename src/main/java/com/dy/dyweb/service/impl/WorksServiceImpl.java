package com.dy.dyweb.service.impl;

import com.dy.dyweb.bean.Works;
import com.dy.dyweb.dao.WorksDao;
import com.dy.dyweb.mapper.WorksMapper;
import com.dy.dyweb.service.WorksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorksServiceImpl implements WorksService {

    @Resource
    private WorksMapper worksMapper;

    @Override
    public List<WorksDao> getUserAllWorks(Works works) {
        return worksMapper.getUserAllWorks(works);
    }

    @Override
    public List<WorksDao> getAllWorks(Works works) {
        return worksMapper.getAllWorks(works);
    }

    @Override
    public boolean addWorks(Works works) {
        return worksMapper.addWorks(works);
    }

    @Override
    public boolean deleteWorks(long worksId) {
        return worksMapper.deleteWorks(worksId);
    }

    @Override
    public boolean updateWorks(Works works) {
        return worksMapper.updateWorks(works);
    }
}
