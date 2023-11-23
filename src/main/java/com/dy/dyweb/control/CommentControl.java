package com.dy.dyweb.control;

import com.dy.dyweb.bean.Comment;
import com.dy.dyweb.bean.ResultBean;
import com.dy.dyweb.service.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentControl {

    @Resource
    private CommentService commentService;

    @RequestMapping("/getCommentInfo")
    public ResultBean getCommentInfo(Comment comment) {
        return ResultBean.ok(commentService.getCommentInfo(comment));
    }

    @RequestMapping("/addComment")
    public ResultBean addComment(Comment comment) {
        boolean result = commentService.addComment(comment);
        if (result) {
            return ResultBean.ok("新增评论成功!");
        } else {
            return ResultBean.fail("新增评论失败!");
        }
    }

    @RequestMapping("/deleteComment")
    public ResultBean deleteComment(Long commentId) {
        boolean result = commentService.deleteComment(commentId);
        if (result) {
            return ResultBean.ok("删除评论成功!");
        } else {
            return ResultBean.fail("删除评论失败!");
        }
    }

}
