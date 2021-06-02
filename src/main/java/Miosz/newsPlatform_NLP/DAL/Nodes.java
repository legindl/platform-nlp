package Miosz.newsPlatform_NLP.DAL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class Nodes {
    public Document createNode(String name, String type){
        MongoClient mongoClient = MongoClients.create("mongodb://mongo:27017");
        MongoDatabase database = mongoClient.getDatabase("TestGraphDatabase");

        Document node = new Document("_id", new ObjectId())
                .append("name", name)
                .append("type", type);

        if(database.getCollection("Nodes").find(eq("name", name)).first() == null){
            database.getCollection("Nodes").insertOne(node);
        }
        else{
            System.out.println("Node Already exists");
        }
        mongoClient.close();
        return node;
    }

    public void createNewsNode(String Id, String name, String type){
        MongoClient mongoClient = MongoClients.create("mongodb://mongo:27017");
        MongoDatabase database = mongoClient.getDatabase("TestGraphDatabase");

        Document node = new Document("_id", new ObjectId(Id))
                .append("name", name)
                .append("type", type);

        if(database.getCollection("Nodes").find(eq("name", name)).first() == null){
            database.getCollection("Nodes").insertOne(node);
        }
        else{
            System.out.println("Node Already exists");
        }
        mongoClient.close();
    }
}
