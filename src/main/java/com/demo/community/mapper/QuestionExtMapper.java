package com.demo.community.mapper;

import com.demo.community.model.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionExtMapper {
    Long incView(Question record);

    int incCommentCount(Question record);
}
