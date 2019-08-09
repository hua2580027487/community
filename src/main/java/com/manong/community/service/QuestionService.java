package com.manong.community.service;

import com.manong.community.dto.QuestionDTO;
import com.manong.community.mapper.QuestionMapper;
import com.manong.community.mapper.UserMapper;
import com.manong.community.model.Question;
import com.manong.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> allList() {
        List<Question> questionList = questionMapper.questionList();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question:questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
