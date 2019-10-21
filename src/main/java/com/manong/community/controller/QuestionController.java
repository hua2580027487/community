package com.manong.community.controller;

import com.manong.community.cache.HotTagCache;
import com.manong.community.dto.CommentDTO;
import com.manong.community.dto.QuestionDTO;
import com.manong.community.enums.CommentTypeEnums;
import com.manong.community.service.CommentService;
import com.manong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.personQuestionById(id);
        List<QuestionDTO> selectRelatedQuestions = questionService.selectRelaed(questionDTO);
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnums.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("personQuestionDTO", questionDTO);
        model.addAttribute("comments",commentDTOList);
        model.addAttribute("relatedQuestions",selectRelatedQuestions);
        return "question";
    }
}
