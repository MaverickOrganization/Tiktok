package com.dy.dyweb.service.impl;

import com.dy.dyweb.bean.Comment;
import com.dy.dyweb.bean.Works;
import com.dy.dyweb.dao.CommentDao;
import com.dy.dyweb.mapper.CommentMapper;
import com.dy.dyweb.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<CommentDao> getCommentInfo(Comment comment) {
        return commentMapper.getCommentInfo(comment);
    }

    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public boolean deleteComment(Long commentId) {
        return commentMapper.deleteComment(commentId);
    }
}
