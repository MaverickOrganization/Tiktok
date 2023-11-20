package com.dy.dyweb.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorksDao {

    private long worksId;

    private long userId;

    private String title;

    private String url;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
