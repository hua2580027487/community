package com.manong.community.service;

import com.manong.community.dto.CommentDTO;
import com.manong.community.enums.CommentTypeEnums;
import com.manong.community.exception.CustomizeErrorCode;
import com.manong.community.exception.CustomizeException;
import com.manong.community.mapper.CommentMapper;
import com.manong.community.mapper.QuestionExtMapper;
import com.manong.community.mapper.QuestionMapper;
import com.manong.community.mapper.UserMapper;
import com.manong.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

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
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            System.out.println(dbComment);
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
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

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnums type) {
        //id为commentId
        System.out.println("看看这个是什么鬼id————————" + id);
        CommentExample commentExample = new CommentExample();
//        if (commentMapper.selectByPrimaryKey(id) == null) {
//            //parentId
//            commentExample.createCriteria()
//                    .andParentIdEqualTo(id)
//                    .andTypeEqualTo(type.getType());
//        } else {
//            //commentId这样只查询了一条数据
//            Long commentId = commentMapper.selectByPrimaryKey(id).getId() + 1;
//            commentExample.createCriteria()
//                    .andParentIdEqualTo(commentMapper.selectByPrimaryKey(commentId).getParentId())
//                    .andTypeEqualTo(type.getType());
//        }

        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        //拿到所有的comments
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        //
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转化为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转化comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
