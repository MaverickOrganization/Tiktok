package com.dy.dyweb.service;

import com.dy.dyweb.bean.Comment;
import com.dy.dyweb.bean.Works;
import com.dy.dyweb.dao.CommentDao;

import java.util.List;

public interface CommentService {

    List<CommentDao> getCommentInfo(Comment comment);

    boolean addComment(Comment comment);

    boolean deleteComment(Long commentId);
}
