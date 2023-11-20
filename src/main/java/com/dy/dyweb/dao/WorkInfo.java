package com.dy.dyweb.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkInfo {

    private long worksId;

    private long userId;

    private String title;

    private String url;

    private String username;

    private String userPic;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
