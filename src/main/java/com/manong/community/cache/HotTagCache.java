package com.manong.community.cache;

import com.manong.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private List<String> hotsTags = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 5;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max) {
                priorityQueue.add(hotTagDTO);
            } else {
                //热度小就被替换下来
                HotTagDTO minHotTag = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHotTag) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<String> sortedTags = new ArrayList<>();
        HotTagDTO pollTag = priorityQueue.poll();
        while (pollTag != null) {
            sortedTags.add(0,pollTag.getName());
            pollTag = priorityQueue.poll();
        }
        hotsTags = sortedTags;
        System.out.println(hotsTags);
    }
}
