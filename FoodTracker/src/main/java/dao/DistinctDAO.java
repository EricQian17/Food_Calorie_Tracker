package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DistinctDAO {

	public static <T> List<T> distinct(Map<String, String> map, String field, Class<T> classOfT) {

		String sURI = map.get("uri");
		String db = map.get("db");
		String col = map.get("col");

		MongoClientURI uri = new MongoClientURI(sURI);
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase(db);

		MongoCollection<Document> collection = database.getCollection(col);

		DistinctIterable<T> distinctList = collection.distinct(field, classOfT);

		List<T> list = new ArrayList<>();
		for (T string : distinctList) {
			list.add(string);
		}

		mongoClient.close();

		return list;
	}

}
