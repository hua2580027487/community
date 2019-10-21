package com.manong.community.schedule;

import com.manong.community.cache.HotTagCache;
import com.manong.community.mapper.QuestionMapper;
import com.manong.community.model.Question;
import com.manong.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    protected HotTagCache hotTagCache;

    @Scheduled(fixedRate = 5000 * 60 * 24)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 5;
        log.info("The schedule start{}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        Map<String, Integer> tagCommentCount = new HashMap<>();
        Map<String, Integer> tagLikeCount = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }

                    //回复todo
                    Integer tagQuestion = tagCommentCount.get(tag);
                    if (tagQuestion != null) {
                        tagCommentCount.put(tag, ++tagQuestion);
                    } else {
                        tagCommentCount.put(tag, 1);
                    }

                    //关注todu
                    Integer tagReply = tagLikeCount.get(tag);
                    if (tagReply != null) {
                        tagLikeCount.put(tag, tagReply + question.getLikeCount());
                    } else {
                        tagLikeCount.put(tag, question.getLikeCount());
                    }
                }
            }
            offset += limit;
        }

        priorities.forEach((k, v) -> {
            System.out.print(k);
            System.out.print(":");
            System.out.print(v);
            System.out.println();
        });
        hotTagCache.updateTags(priorities);
        log.info("The schedule stop{}", new Date());
    }

}
