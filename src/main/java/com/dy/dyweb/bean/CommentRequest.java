package com.dy.dyweb.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    // 评论Id
    private Long commentId;

    // 作品Id
    private Long worksId;

    // 在该评论下接着评论
    private Long parentCommentId;

    // 用户发布评论Id
    private Long userId;

    // @的用户集合
    private JSONArray referredId;

    // 回复的用户Id
    private Long replyUserId;

    // 评论内容
    private String content;

    private int page;

    private int rows;

}
