package Miosz.newsPlatform_NLP.DAL;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Edges {
    public void createEdgeToNews(String newsID, String nodeID){
        MongoClient mongoClient = MongoClients.create("mongodb://mongo:27017");
        MongoDatabase database = mongoClient.getDatabase("TestGraphDatabase");

        Document node = new Document("_id", new ObjectId())
                .append("source", newsID)
                .append("target", nodeID);

        BasicDBObject criteria = new BasicDBObject();
        criteria.append("source", newsID);
        criteria.append("target", nodeID);
        if(database.getCollection("Edges").find(criteria).first() == null){
            database.getCollection("Edges").insertOne(node);
        }
        else{
            System.out.println("Edge Already exists");
        }
        mongoClient.close();
    }
}
