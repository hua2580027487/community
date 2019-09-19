package com.manong.community.mapper;

import com.manong.community.model.Comment;

public interface CommentExtMapper {
    int incCommentView(Comment comment);
}