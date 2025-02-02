package com.springAIDemo.DeepSeekRAGImplement.controller;

import com.springAIDemo.DeepSeekRAGImplement.service.SpringAIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class SpringAIController {
    @Autowired
    private SpringAIService aiService;
    @Autowired
    private ChatClient chatClient;
    @PostMapping("/response")
    public Map<String, String> getResponse(@RequestParam String question){
        VectorStore vecStore = aiService.loadDataVectorDB();
        return Map.of("response", chatClient.prompt().advisors(new QuestionAnswerAdvisor(vecStore, SearchRequest.builder().query(question).topK(5).similarityThresholdAll().build()))
                .user(question).call().content());
    }
}
