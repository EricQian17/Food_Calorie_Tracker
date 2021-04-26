package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.UserData;
import template.IQuery;

public class UserDataDAO implements IQuery<Integer, UserData> {
	
	@Override
	public List<UserData> findBy(Map<String, Object> mapQuery) {
		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Food");
		
		MongoCollection<Document> collection = database.getCollection("user_data");
		
		String date = mapQuery.get("date").toString();
		
		Document filter = new Document();
		filter.append("date",date);

		
		FindIterable<Document> rows = collection.find(filter);
		
		List<UserData> list = new ArrayList<>();
		Gson gson = new Gson();
		for (Document document : rows) {
			String json = document.toJson();
			UserData data = gson.fromJson(json, UserData.class);
			list.add(data);
		}

		mongoClient.close();

		return list;
		
	}

	@Override
	public List<UserData> findAll() {
		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Food");
		
		MongoCollection<Document> collection = database.getCollection("user_data");

		FindIterable<Document> rows = collection.find();

		List<UserData> list = new ArrayList<>();
		Gson gson = new Gson();
		for (Document document : rows) {
			String json = document.toJson();
			UserData data = gson.fromJson(json, UserData.class);

			list.add(data);

		}

		mongoClient.close();

		return list;
	}

}
