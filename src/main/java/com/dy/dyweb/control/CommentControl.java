package com.dy.dyweb.control;

import com.dy.dyweb.bean.Comment;
import com.dy.dyweb.bean.CommentRequest;
import com.dy.dyweb.bean.ResultBean;
import com.dy.dyweb.service.CommentService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResultBean addComment(@RequestBody CommentRequest comment) {
        Comment bean = new Comment();
        BeanUtils.copyProperties(comment, bean);
        bean.setReferredId(comment.getReferredId().toJSONString());
        boolean result = commentService.addComment(bean);
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
