package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import dao.UserDataDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Food;
import model.UserData;

public class UserDataController {

    @FXML
    private TableView<UserData> tblUserData;

    @FXML
    private TableColumn<UserData, String> colDate;

    @FXML
    private TableColumn<UserData, String> colName;

    @FXML
    private TableColumn<UserData, Double> colCalories;

    @FXML
    private TableColumn<UserData, Double> colFat;

    @FXML
    private TableColumn<UserData, Double> colCarbs;

    @FXML
    private TableColumn<UserData, Double> colProtein;

    @FXML
    private TableColumn<UserData, Double> colQuantity;

    @FXML
    private DatePicker txtDate;

    @FXML
    private Button btnShow;

    @FXML
    private Button btnDelete;
    
    @FXML
    void deleteEntry(ActionEvent event) {
    	
    	MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> userDataCollection = database.getCollection("user_data");

		Document deleteDoc = new Document();

		UserData userdata;
		userdata = tblUserData.getSelectionModel().getSelectedItem();
		System.out.println(userdata.get_id());
		
		deleteDoc.put("_id", new ObjectId(userdata.get_id().toString()));

		userDataCollection.deleteOne(deleteDoc);
		tblUserData.getItems().remove(userdata);
		mongoClientData.close();
		

    }

    @FXML
    void showData(ActionEvent event) {
    	
    	tblUserData.getItems().clear();
    	
    	Map<String, Object> mapQuery = new HashMap<>();
    	UserDataDAO dao = new UserDataDAO();
    	try {
    		String date = txtDate.getValue().toString();
    		
    		mapQuery.put("date", date);
    		List<UserData> list = dao.findBy(mapQuery);
    		ObservableList<UserData> data = FXCollections.observableArrayList(list);
    		tblUserData.setItems(data);
			
		} catch (Exception e) {
			
			List<UserData> list = dao.findAll();
    		ObservableList<UserData> data = FXCollections.observableArrayList(list);
    		tblUserData.setItems(data);
		}

		


    }

}
