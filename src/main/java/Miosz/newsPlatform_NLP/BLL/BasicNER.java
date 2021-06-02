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
    public void entityFinder(String newsId) throws IOException {
        Document news = new News().ReadNews(newsId);
        String sentence = news.getString("content");

        System.out.println("Starting Tokenizer");
        InputStream inputStreamTokenizer = new FileInputStream("/usr/local/tomcat/webapps/models/en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
        TokenizerME tokenizer = new TokenizerME(tokenModel);

        String[] tokens = tokenizer.tokenize(sentence);
        System.out.println(tokens.length);
        System.out.println("personModel");
        InputStream inputStreamPersonFinder = new FileInputStream("/usr/local/tomcat/webapps/models/en-ner-person.bin");
        TokenNameFinderModel personModel = new TokenNameFinderModel(inputStreamPersonFinder);

        NameFinderME personFinder = new NameFinderME(personModel);
        System.out.println("locationModel");
        InputStream inputStreamLocationFinder = new FileInputStream("/usr/local/tomcat/webapps/models/en-ner-location.bin");
        TokenNameFinderModel locationModel = new TokenNameFinderModel(inputStreamLocationFinder);

        NameFinderME locationFinder = new NameFinderME(locationModel);
        System.out.println("organizationModel");
        InputStream inputStreamOrganizationFinder = new FileInputStream("/usr/local/tomcat/webapps/models/en-ner-organization.bin");
        TokenNameFinderModel organizationModel = new TokenNameFinderModel(inputStreamOrganizationFinder);

        NameFinderME organizationFinder = new NameFinderME(organizationModel);
        System.out.println("Creating News Node");
        new Nodes().createNewsNode(newsId,news.getString("title") , "news");
        System.out.println("Start Entity Finder method");
        findEntity(tokens, personFinder, newsId);
        findEntity(tokens, locationFinder, newsId);
        findEntity(tokens, organizationFinder, newsId);
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
