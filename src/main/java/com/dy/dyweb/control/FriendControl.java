package com.dy.dyweb.control;
import com.dy.dyweb.bean.Friend;
import com.dy.dyweb.bean.ResultBean;
import com.dy.dyweb.dao.FriendDao;
import com.dy.dyweb.service.FriendService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
@RestController
@RequestMapping("/friend")
public class FriendControl {

    @Resource
    private FriendService friendService;

    @RequestMapping("/selectAllFriend")
    public ResultBean selectAllFriend(Long userId) {
        return ResultBean.ok(friendService.selectAllFriend(userId));
    }

    @RequestMapping("/deleteFriend")
    public ResultBean deleteFriend(Friend friend) {
        boolean result = friendService.deleteFriend(friend);
        if (result) {
            return ResultBean.ok("取消关注成功！");
        }
        return ResultBean.fail("取消关注失败");
    }

    @RequestMapping("/addFriend")
    public ResultBean addFriend(@RequestBody Friend friend) {
        if(friendService.isFriend(friend) != null) {
            return ResultBean.ok("已经在你的关注列表中！");
        }
        boolean result = friendService.addFriend(friend);
        if (result) {
            return ResultBean.ok("关注成功！");
        }
        return ResultBean.fail("关注失败");
    }

    @RequestMapping("/isFriend")
    public ResultBean isFriend(Friend friend) {
        FriendDao result = friendService.isFriend(friend);
        return ResultBean.ok(result);
    }
}
