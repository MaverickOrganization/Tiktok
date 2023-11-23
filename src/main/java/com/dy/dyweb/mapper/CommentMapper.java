package com.dy.dyweb.mapper;

import com.dy.dyweb.bean.Comment;
import com.dy.dyweb.bean.Works;
import com.dy.dyweb.dao.CommentDao;
import com.dy.dyweb.dao.WorksDao;

import java.util.List;

public interface CommentMapper {

    List<CommentDao> getCommentInfo(Comment comment);

    boolean addComment(Comment comment);

    boolean deleteComment(Long commentId);
}
