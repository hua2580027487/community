package com.manong.community.controller;

import com.manong.community.dto.NotificationDTO;
import com.manong.community.dto.PageDTO;
import com.manong.community.enums.NotificationTypeEnum;
import com.manong.community.mapper.NotificationMapper;
import com.manong.community.model.User;
import com.manong.community.service.NotificationService;
import com.manong.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
       NotificationDTO notificationDTO = notificationService.read(id,user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
            || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
