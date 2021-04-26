package drivers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import dao.FoodTrackerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Food;

public class FoodTrackerDriver {

	public static void main(String[] args) {
		
		double protien = 3;
		double fat = 9;
		double carbs = 25;
		double fiber = 0;
		double result = protien*4+carbs*4+fat*4;
		double result2 = protien*4+(carbs-fiber)*4+fat*9;
		System.out.println(result);
		System.out.println(result2);
		
		List<Food> list = new ArrayList<>();
		FoodTrackerDAO dao = new FoodTrackerDAO();
	
		Map<String,Object> mapQuery = new HashMap<>();
		mapQuery.put("description", "Tutturosso Green 14.5oz. NSA Italian Diced Tomatoes");
		
    	list=dao.findBy(mapQuery);
		//list.forEach(System.out::println);
		
		
		MongoClientURI uri = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("Food");
		
		MongoCollection<Document> collection = database.getCollection("user_data");
		collection.updateOne(Filters.eq("name", "Jack"), Updates.set("quantity", 10));
		
		mongoClient.close();

	}

}
