package com.stackroute.nlpmicroservice.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlService {

    public String html2text(String html) {
        //System.out.println("+++++++++++"+Jsoup.clean(html,Whitelist.none())+"+++++++++++++");
        return Jsoup.parse(html).text();
    }
    public String WhiteListBasic(String html)
    {
        //System.out.println("***********"+Jsoup.clean(html, Whitelist.basic())+"*************");
        //it can also do paragraphs extraction.  also there is something called whitelist basic
        return Jsoup.clean(html,Whitelist.basic());
    }
    public List<String> getAllParagraphs(String html) {
        Document doc=Jsoup.parse(html);
        Elements paragraphs = doc.getElementsByTag("p");
        int numOfp=paragraphs.size();
        List<String> paragraphList=new ArrayList<>();
        System.out.println("num of paragraphs found ="+numOfp);
        for (Element p : paragraphs) {
          if(p.hasText())
          {
              paragraphList.add(p.text());
          }

        }
        return paragraphList;
    }

    public void getAllLi(String html) {
        Document doc = Jsoup.parse(html);
        Elements liTags = doc.getElementsByTag("li");
        //if(paragraphs.hasText());// can be used to check text is present orr not in between a tag
        int numOfLi = liTags.size();
        System.out.println("num of lists found =" + numOfLi);
        for (Element p : liTags) {
            System.out.println(p);
        }
    }
    public void getAllTr(String html) {
        Document doc = Jsoup.parse(html);
        Elements TrTags = doc.getElementsByTag("tr");
        //if(paragraphs.hasText());// can be used to check text is present orr not in between a tag
        int numOfTr = TrTags.size();
        System.out.println("num of tr tags found =" + numOfTr);
        for (Element tr : TrTags) {
            if(tr.hasText())
            {
                System.out.println("found text inside whole tr tag =" + tr);
                System.out.println("tr.text="+tr.text()+
                        "next element sibling="+tr.nextElementSibling()+
                        "previous element sibling="+tr.previousElementSibling());
            }
            else
                System.out.println("not found *** text ********="+tr);
        }
    }

    public void getAllScripts(String html) {
        Document doc = Jsoup.parse(html);
        Elements TrTags = doc.getElementsByTag("script");
        //if(paragraphs.hasText());// can be used to check text is present orr not in between a tag
        int numOfTr = TrTags.size();
        System.out.println("num of script tags  found =" + numOfTr);
        for (Element tr : TrTags) {
            System.out.println(tr);
        }
    }
}
