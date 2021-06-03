package Miosz.newsPlatform_NLP.BLL;

import Miosz.newsPlatform_NLP.DAL.Edges;
import Miosz.newsPlatform_NLP.DAL.News;
import Miosz.newsPlatform_NLP.DAL.Nodes;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BasicNER {
    public void entityFinder(String newsId) {
        Document news = new News().ReadNews(newsId);
        String sentence = news.getString("content");
        System.out.println("Starting Tokenizer");
        InputStream inputStreamTokenizer;
        TokenizerModel tokenModel = null;

        System.out.println("Creating News Node");
        new Nodes().createNewsNode(newsId,news.getString("title") , "news");
        System.out.println("Start Entity Finder method");

        try {
            inputStreamTokenizer = new FileInputStream("/usr/local/tomcat/webapps/models/en-token.bin");
            tokenModel = new TokenizerModel(inputStreamTokenizer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TokenizerME tokenizer = new TokenizerME(tokenModel);

        String[] tokens = tokenizer.tokenize(sentence);
        tokenizer=null;
        System.out.println(tokens.length);
        System.out.println("personModel");
        TokenNameFinderModel personModel = null;
        try {
            InputStream inputStreamPersonFinder = new FileInputStream("/usr/local/tomcat/webapps/models/en-ner-person.bin");
            personModel = new TokenNameFinderModel(inputStreamPersonFinder);

        } catch (IOException e) {
            e.printStackTrace();
        }
        NameFinderME personFinder = new NameFinderME(personModel);
        findEntity(tokens, personFinder, newsId);
        personFinder=null;
        System.out.println("locationModel");
        TokenNameFinderModel locationModel = null;
        try {
            InputStream inputStreamLocationFinder = new FileInputStream("/usr/local/tomcat/webapps/models/en-ner-location.bin");
            locationModel = new TokenNameFinderModel(inputStreamLocationFinder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        NameFinderME locationFinder = new NameFinderME(locationModel);
        findEntity(tokens, locationFinder, newsId);
        locationFinder=null;
        System.out.println("organizationModel");
        TokenNameFinderModel organizationModel = null;
        try {
            InputStream inputStreamOrganizationFinder = new FileInputStream("/usr/local/tomcat/webapps/models/en-ner-organization.bin");
            organizationModel = new TokenNameFinderModel(inputStreamOrganizationFinder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        NameFinderME organizationFinder = new NameFinderME(organizationModel);
        findEntity(tokens, organizationFinder, newsId);
        organizationFinder=null;
    }

    public void findEntity(String[] tokens, NameFinderME finder, String newsId){
        Span[] entities = finder.find(tokens);
        for(Span s: entities){
            String entity = "";

            for (int i = s.getStart(); i < s.getEnd(); i++) {
                entity = "" + entity +" "+ tokens[i];
            }
            System.out.println(s.toString()+ " " + entity);

            String nodeID = new Nodes().createNode(entity, s.getType()).get("_id").toString();

            new Edges().createEdgeToNews(newsId, nodeID);
        }
    }
}
