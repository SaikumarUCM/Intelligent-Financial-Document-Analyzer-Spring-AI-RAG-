package com.example.RAG_Project;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

@Component
public class IngestionService  implements CommandLineRunner {


    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Value("classpath:/Docs/article_thebeatoct2024.pdf")
    private Resource marketPDF;

    @Override
    public void run(String... args) throws Exception {
        var PDFReader= new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter splitter= new TokenTextSplitter();
        vectorStore.accept(splitter.apply(PDFReader.get()));
        log.info("VectorStore loaded with the Data ");

    }
}
