package com.springAIDemo.DeepSeekRAGImplement.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpringAIService {
    private static final Logger log = LoggerFactory.getLogger(SpringAIService.class);
    @Value("classpath:/data/sample.txt")
    private Resource fileSource;

    @Autowired
    VectorStore vecStore;

    public VectorStore loadDataVectorDB(){
        TextReader txtReader = new TextReader(fileSource);
        txtReader.getCustomMetadata().put("filename", "sample.txt");
        List<Document> docs = txtReader.get();
        log.info("Total Docs Size: {}", docs.size());
        List<Document> splittedDocs = new TokenTextSplitter().apply(docs);
        log.info("Total Splitted Docs Count: {}",splittedDocs.size());
        vecStore.add(splittedDocs);
        return vecStore;
    }
}
