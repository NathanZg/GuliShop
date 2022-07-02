package com.ekertree.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ChapterVo
 * Description:章节
 * date: 2022/6/29 21:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();//小节
}
