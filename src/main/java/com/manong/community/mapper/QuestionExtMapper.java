package com.manong.community.mapper;

import com.manong.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentView(Question question);
    List<Question> selectRelated(Question question);
}