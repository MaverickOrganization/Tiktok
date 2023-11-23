package com.dy.dyweb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    // 评论Id
    private Long commentId;

    // 在该评论下接着评论
    private Long parentCommentId;

    // 用户发布评论Id
    private Long userId;

    // @的用户集合
    private List<Long> referredUserId;

    // 回复的用户Id
    private Long replyUserId;

    // 评论内容
    private String content;

    // 评论时间
    private LocalDateTime createTime;

}
