package drivers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

import dao.UserDataDAO;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.UserData;

public class UserDataDriver {

	public static void main(String[] args) {

		List<UserData> list = new ArrayList<>();
		UserDataDAO dao = new UserDataDAO();
		String date = "2021-04-21";
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("date", date);

		list = dao.findBy(mapQuery);

		list.forEach(System.out::println);
		

	}

}
