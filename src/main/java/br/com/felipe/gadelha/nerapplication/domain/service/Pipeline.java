package br.com.felipe.gadelha.nerapplication.domain.service;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class Pipeline {

    private static Properties properties;
    private static String propertiesName = "tokenize, ssplit, pos, lemma, ner";
    private static StanfordCoreNLP stanfordCoreNLP;

    private Pipeline() {

    }

    static {
        properties = new Properties();
        properties.setProperty("ner.useSUTime", "false");
        properties.setProperty("annotators", propertiesName);
    }

    @Bean(name = "stanfordCoreNLP")
    public static StanfordCoreNLP getInstance() {
        if(stanfordCoreNLP == null) {
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}