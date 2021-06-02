package Miosz.newsPlatform_NLP.DAL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

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
