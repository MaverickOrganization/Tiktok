package com.dy.dyweb.control;

import com.dy.dyweb.bean.ResultBean;
import com.dy.dyweb.bean.Works;
import com.dy.dyweb.dao.WorksDao;
import com.dy.dyweb.service.WorksService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
@RequestMapping("/works")
public class WorksControl {

    @Resource
    private WorksService worksService;

    @RequestMapping("/getAllUserWorks")
    public ResultBean getUserAllWorks(Works works) {
        return ResultBean.ok(worksService.getUserAllWorks(works));
    }

    @RequestMapping("/getAllWorks")
    public ResultBean getAllWorks(Works works) {
        return ResultBean.ok(worksService.getAllWorks(works));
    }

    @RequestMapping("/addWorks")
    public ResultBean addWorks(@RequestBody Works works) {
        boolean addWorks = worksService.addWorks(works);
        if (addWorks) {
            return ResultBean.ok("新增作品成功!");
        }
        return ResultBean.fail("新增作品失败!");
    }

    @RequestMapping("/updateWorks")
    public ResultBean updateWorks(@RequestBody Works works) {
        boolean addWorks = worksService.updateWorks(works);
        if (addWorks) {
            return ResultBean.ok("更新作品成功!");
        }
        return ResultBean.fail("更新作品失败!");
    }

    @RequestMapping("/deleteWorks")
    public ResultBean deleteWorks(long worksId) {
        boolean addWorks = worksService.deleteWorks(worksId);
        if (addWorks) {
            return ResultBean.ok("删除作品成功!");
        }
        return ResultBean.fail("删除作品失败!");
    }
}
