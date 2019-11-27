package com.stackroute.nlpmicroservice.controller;

import com.stackroute.nlpmicroservice.model.TypeEnum;
import com.stackroute.nlpmicroservice.services.HtmlService;
import com.stackroute.nlpmicroservice.services.NLPResultsService;
import com.stackroute.nlpmicroservice.services.ParagraphLemmaService;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1")
public class NERController {
    private HtmlService htmlService;
    private ParagraphLemmaService paragraphLemmaService;
    private NLPResultsService nlpResultsService;

    @Autowired
    public NERController(HtmlService htmlService,
                         ParagraphLemmaService paragraphLemmaService,
                         NLPResultsService nlpResultsService)
    {this.htmlService=htmlService;
    this.paragraphLemmaService = paragraphLemmaService;
    this.nlpResultsService=nlpResultsService;}

    @Autowired
    StanfordCoreNLP stanfordCoreNLP;

    @PostMapping(value = "nlp")
    public ResponseEntity<?> nlp(@RequestBody String html)
    {
        ResponseEntity responseEntity;
        try
        {
            List<String> paraList=htmlService.getAllParagraphs(html);
            if(!paraList.isEmpty())
            {
                for(String p:paraList)
                {
                    String cleanHtml=htmlService.html2text(p);
                    String lemPara=paragraphLemmaService.getParagraphWithLemmieWords(cleanHtml);
                    nlpResultsService.StepwiseNlp(lemPara);
                }
            }
            responseEntity = new ResponseEntity<String>( htmlService.html2text(html), HttpStatus.OK);
        } catch (Exception e)
        {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PostMapping(value = "format")
    public ResponseEntity<?> htmlFormatter(@RequestBody String html)
    {
        ResponseEntity responseEntity;
        try
        {
            responseEntity = new ResponseEntity<String>( htmlService.html2text(html), HttpStatus.OK);
            System.out.println("*********************lemma para ");
            System.out.println(paragraphLemmaService.getParagraphWithLemmieWords(html));
        } catch (Exception e)
        {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PostMapping(value = "ner")
    public Set<String> ner(@RequestBody String input, @RequestParam TypeEnum type)
    {
        CoreDocument coreDocument=new CoreDocument(htmlService.html2text(input));
        System.out.println("====Core document======="+coreDocument);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabels= coreDocument.tokens();
        System.out.println("**list of core labels*****="+coreLabels);
        return new HashSet<>(collectList(coreLabels,type))  ;
    }

    private List<String> collectList(List<CoreLabel> coreLabels,TypeEnum typeEnum)
    {
        return coreLabels.stream().filter(coreLabel -> typeEnum.getName()
                .equalsIgnoreCase(coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class)))
                .map(CoreLabel::originalText).collect(Collectors.toList());
    }

    @PostMapping(value = "tokenize")
    private void tokenizeSentence(@RequestBody String text)
    {
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);
        System.out.println("annotated document= "+document);
        // run all Annotators on this text
        stanfordCoreNLP.annotate(document);

        /********************************************************************/
/*        // 10th token of the document
        CoreLabel token = document.tokens().get(10);
        System.out.println("Example: token");
        System.out.println(token);
        System.out.println();

        // text of the first sentence
        String sentenceText = document.sentences().get(0).text();
        System.out.println("Example: sentence");
        System.out.println(sentenceText);
        System.out.println();

        // second sentence
        CoreSentence sentence = document.sentences().get(1);

        // list of the part-of-speech tags for the second sentence
        List<String> posTags = sentence.posTags();
        System.out.println("Example: pos tags");
        System.out.println(posTags);
        System.out.println();

        // list of the ner tags for the second sentence
        List<String> nerTags = sentence.nerTags();
        System.out.println("Example: ner tags");
        System.out.println(nerTags);
        System.out.println();

        // constituency parse for the second sentence
        Tree constituencyParse = sentence.constituencyParse();
        System.out.println("Example: constituency parse");
        System.out.println(constituencyParse);
        System.out.println();

        // entity mentions in the second sentence
        List<CoreEntityMention> entityMentions = sentence.entityMentions();
        System.out.println("Example: entity mentions");
        System.out.println(entityMentions);
        System.out.println();*/

        /********************************************************************/
/*        CoreDocument coreDocument=new CoreDocument(text);
        List<CoreLabel> coreLabelList=coreDocument.tokens();*/
        /*********************************************************************/

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        System.out.println("Sentences="+sentences);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            System.out.println("Sentence= "+sentence);
            for (CoreMap entityMention : sentence.get(CoreAnnotations.MentionsAnnotation.class)) {
                System.out.print(entityMention.get(CoreAnnotations.TextAnnotation.class));
                System.out.print("**********"+"\t"+"******");
                System.out.print(entityMention.get(CoreAnnotations.NamedEntityTagAnnotation.class));
                System.out.println("**********************");
            }
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class))
            {
                System.out.println("token="+token);
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                System.out.println(String.format("Print : word - [%s] , pos - [%s] , ner - [%s]",word,pos,ne));
            }
        }
    }
}
