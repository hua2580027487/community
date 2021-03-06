package com.manong.community.controller;

import com.manong.community.dto.CommentCreateDTO;
import com.manong.community.dto.CommentDTO;
import com.manong.community.dto.ResultDTO;
import com.manong.community.enums.CommentTypeEnums;
import com.manong.community.exception.CustomizeErrorCode;
import com.manong.community.model.Comment;
import com.manong.community.model.User;
import com.manong.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //打印
        System.out.println(commentCreateDTO.toString());
        //是否登录校验
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //内容是否为空
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment, user);
        return ResultDTO.accessOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnums.COMMENT);
        System.out.println(commentDTOS.isEmpty());
        return ResultDTO.accessOf(commentDTOS);
    }
}
