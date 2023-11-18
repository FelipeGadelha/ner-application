package br.com.felipe.gadelha.nerapplication.api.v1.controller;

import br.com.felipe.gadelha.nerapplication.domain.model.Type;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class NERController {

    private final StanfordCoreNLP stanfordCoreNLP;
//
    @Autowired
    public NERController(StanfordCoreNLP stanfordCoreNLP) {
        this.stanfordCoreNLP = stanfordCoreNLP;
    }

    @PostMapping("/ner")
    public Set<String> ner(@RequestBody final String input, @RequestParam final Type type) {
        final var coreDocument = new CoreDocument(input);
        stanfordCoreNLP.annotate(coreDocument);
        final var tokens = coreDocument.tokens();

        return new HashSet<>(collectList(tokens, type));
    }

    private List<String> collectList(List<CoreLabel> coreLabels, final Type type) {
        return coreLabels.stream()
            .filter(coreLabel -> type.getName().equalsIgnoreCase(coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class)))
            .map(CoreLabel::originalText)
            .toList();
    }
}
