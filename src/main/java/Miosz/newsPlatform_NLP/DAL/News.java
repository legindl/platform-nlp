package Miosz.newsPlatform_NLP.DAL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.set;

public class News {
    public Document ReadNews(String id){
        MongoClient mongoClient = MongoClients.create("mongodb://mongo:27017");
        MongoDatabase database = mongoClient.getDatabase("TestDatabase");

        Document news = database.getCollection("News").find(new Document("_id", new ObjectId(id))).first();
        if(news.getString("content") != null) {
            mongoClient.close();
            return news;
        }
        else{
            System.out.println("null");
            mongoClient.close();
            return null;
        }
    }
}
