package com.manong.community.controller;

import com.manong.community.dto.PageDTO;
import com.manong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        //数据传递到页面
        PageDTO pageDTO = questionService.allPostList(page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "index";
    }
}
