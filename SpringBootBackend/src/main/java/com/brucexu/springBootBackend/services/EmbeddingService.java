package com.brucexu.springBootBackend.services;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmbeddingService {

    @Autowired
    private OpenAiEmbeddingModel embeddingModel;

    public float[] embed(String text) {

        Embedding embedding = embeddingModel
                .embed(text)
                .content();

        return embedding.vector();
    }

    public String toVectorString(float[] vector) {
        return "[" +
                IntStream.range(0, vector.length)
                        .mapToObj(i -> Float.toString(vector[i]))
                        .collect(Collectors.joining(",")) +
                "]";
    }

    public List<float[]> embedAll(List<String> texts) {

        List<TextSegment> segments = texts.stream()
                .map(TextSegment::from)
                .toList();

        List<Embedding> embeddings = embeddingModel
                .embedAll(segments)
                .content();

        return embeddings.stream()
                .map(Embedding::vector)
                .toList();
    }
}
