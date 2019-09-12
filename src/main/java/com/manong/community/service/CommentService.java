package com.manong.community.service;

import com.manong.community.enums.CommentTypeEnums;
import com.manong.community.exception.CustomizeErrorCode;
import com.manong.community.exception.CustomizeException;
import com.manong.community.mapper.CommentMapper;
import com.manong.community.mapper.QuestionExtMapper;
import com.manong.community.mapper.QuestionMapper;
import com.manong.community.model.Comment;
import com.manong.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TAEGET_PAEAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnums.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PAEAM_ERROR);
        }

        if (comment.getType() == CommentTypeEnums.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(dbcomment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentView(question);
        }
    }
}
