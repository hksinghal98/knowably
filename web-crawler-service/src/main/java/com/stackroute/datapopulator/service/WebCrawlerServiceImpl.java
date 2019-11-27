package com.stackroute.datapopulator.service;

import com.stackroute.datapopulator.domain.Input;
import com.stackroute.datapopulator.domain.WebCrawl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//implements from WebCrawler interface and overrides methods defined in WebCrawler
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private WebCrawl webCrawl;

    public WebCrawlerServiceImpl() {
    }
    //To extract content from the webpage:getContent() function is called from the controller
    @Override
    public CompletableFuture<WebCrawl> getContent(Input input) {
        List<String> content;
        String[] url = input.getUrl();
        int count = 0;
        count = 0;
        while (url.length > count) {
            String urls = url[count];
            try {

                /*Jsoup connects to the url and convert it into DOM for Extracting Content*/
                Document document = Jsoup.connect(urls).get();
                //change Css query to get required content
                Elements body = document.select("body");
                content = new ArrayList<>();
                /*Adding each element to the  Content list*/
                for (Element element : body) {
                    content.add(element.toString());
                }
                count++;

                /*Setting all the properties of the object Webcrawl and storing it in Webcrawls List*/
                webCrawl = new WebCrawl();
                webCrawl.setId(input.getId());
                webCrawl.setConcept(input.getConcept());
                webCrawl.setConcept(input.getConcept());
                webCrawl.setDomain(input.getDomain());
                webCrawl.setUrl(urls);
                /*
                * Setting the Produced Content(Payload) to the WebCrawl payload property which can be used by NLP service
                * */
                webCrawl.setPayload(content);
            } catch (IOException e) {
                System.err.println("For '" + urls + "': " + e.getMessage());
            }
        }

/*Returning Completable Future of Webcrawl to the controller*/
        return CompletableFuture.completedFuture(webCrawl);
    }

}

