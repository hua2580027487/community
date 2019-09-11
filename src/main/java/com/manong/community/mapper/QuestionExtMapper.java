package com.manong.community.mapper;

import com.manong.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentView(Question question);
}