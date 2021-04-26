package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Food;
import template.IQuery;

public class FoodTrackerDAO implements IQuery<Integer, Food> {
	
	@Override
	public List<Food> findBy(Map<String, Object> mapQuery) {
		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Food");
		
		MongoCollection<Document> collection = database.getCollection("food_information");
		
		String description = mapQuery.get("description").toString();
		
		Document filter = new Document();
		filter.append("description",description);

		
		FindIterable<Document> rows = collection.find(filter);
		
		List<Food> list = new ArrayList<>();
		Gson gson = new Gson();
		for (Document document : rows) {
			String json = document.toJson();
			Food food = gson.fromJson(json, Food.class);
			list.add(food);
		}

		mongoClient.close();

		return list;
		
	}

	@Override
	public List<Food> findAll() {
		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Food");
		
		MongoCollection<Document> collection = database.getCollection("food_information");

		FindIterable<Document> rows = collection.find();

		List<Food> list = new ArrayList<>();
		Gson gson = new Gson();
		for (Document document : rows) {
			String json = document.toJson();
			Food food = gson.fromJson(json, Food.class);

			list.add(food);

		}

		mongoClient.close();

		return list;
	}

}
