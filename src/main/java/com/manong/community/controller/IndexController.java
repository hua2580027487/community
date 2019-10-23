package com.manong.community.controller;

import com.manong.community.cache.HotTagCache;
import com.manong.community.dto.PageDTO;
import com.manong.community.schedule.HotTagTasks;
import com.manong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private HotTagTasks hotTagTasks;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag) {
        //数据传递到页面
        PageDTO pageDTO = questionService.allPostList(search,tag,page, size);
        List<String> hotsTags = hotTagCache.getHotsTags();
        Map<String, Integer> hotTagCommentCount = hotTagTasks.getHotTagCommentCount();
        Map<String, Integer> hotTagLikeCount = hotTagTasks.getHotTagLikeCount();
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("search", search);
        model.addAttribute("hotsTags",hotsTags);
        model.addAttribute("tag",tag);
        model.addAttribute("hotTagCommentCount",hotTagCommentCount);
        model.addAttribute("hotTagLikeCount",hotTagLikeCount);
        return "index";
    }
}
