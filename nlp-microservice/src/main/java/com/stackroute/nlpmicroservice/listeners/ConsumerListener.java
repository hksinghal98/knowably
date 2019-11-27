package com.stackroute.nlpmicroservice.listeners;

import com.google.gson.Gson;
import com.stackroute.nlpmicroservice.domain.SitesContent;
import com.stackroute.nlpmicroservice.services.HtmlService;
import com.stackroute.nlpmicroservice.services.NLPResultsService;
import com.stackroute.nlpmicroservice.services.ParagraphLemmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;

@Service
public class ConsumerListener {
    private HtmlService htmlService;
    private ParagraphLemmaService paragraphLemmaService;
    private NLPResultsService nlpResultsService;

    @Autowired
    public ConsumerListener(HtmlService htmlService, ParagraphLemmaService paragraphLemmaService,
                            NLPResultsService nlpResultsService) {
        this.htmlService = htmlService;
        this.paragraphLemmaService = paragraphLemmaService;
        this.nlpResultsService = nlpResultsService;
    }


    //This method is used to consume json object from producer
    // @KafkaListener(topics = "TopicPayload", groupId = "group_id")
    public void consume(String body_payloads) {
        Gson gson = new Gson();
        SitesContent sitesContent = gson.fromJson(body_payloads, SitesContent.class);

        /*System.out.println(sitesContent.toString());
        String pl1=sitesContent.getPayload().get(0);
        System.out.println(pl1);*/

        List<String> allPayloads = sitesContent.getPayload();
        if (allPayloads.isEmpty())
            System.out.println("no payloads found");
        ListIterator<String> iterator = allPayloads.listIterator();
        System.out.println("\n----Using ListIterator:\n-----");
        while (iterator.hasNext()) {
            System.out.println("Value is : " + iterator.next());
            List<String> paraList = htmlService.getAllParagraphs(iterator.next());
            if (!paraList.isEmpty()) {
                for (String p : paraList) {
                    String cleanHtml = htmlService.html2text(p);
                    String lemPara = paragraphLemmaService.getParagraphWithLemmieWords(cleanHtml);
                    nlpResultsService.StepwiseNlp(lemPara);
                }
            }
        }
    }
}