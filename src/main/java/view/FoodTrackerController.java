package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.controlsfx.control.textfield.TextFields;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import dao.DistinctDAO;
import dao.FoodTrackerDAO;
import dao.UserDataDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.Food;
import model.UserData;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;

public class FoodTrackerController implements Initializable {

	@FXML
	private TextField txtFood;

	@FXML
	private TableView<Food> tblInfo;

	@FXML
	private TableColumn<Food, Double> colName;

	@FXML
	private TableColumn<Food, Double> colCalories;

	@FXML
	private TableColumn<Food, Double> colFat;

	@FXML
	private TableColumn<Food, Double> colCarbs;

	@FXML
	private TableColumn<Food, Double> colProtein;

	@FXML
	private TextField txtQuantity;

	@FXML
	private DatePicker txtDate;

	@FXML
	private Button btnSubmit;

	@FXML
	private Button btnCheckLogs;

	@FXML
	private Button btnShow;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnAdd;

	private Map<String, String> mapDB = new HashMap<>();
	private List<String> foodNames = new ArrayList<String>();

	@FXML
	void showData(ActionEvent event) {

		tblInfo.getItems().clear();

		String description = txtFood.getText();

		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("description", description);

		FoodTrackerDAO dao = new FoodTrackerDAO();
		List<Food> list = dao.findBy(mapQuery);
		ObservableList<Food> data = FXCollections.observableArrayList(list);
		if (data.size() == 0) {
			Food customEntry = new Food();
			customEntry.setDescription(txtFood.getText());
			tblInfo.getItems().add(customEntry);

		} else {
			tblInfo.setItems(data);
		}

	}

	@FXML
	void CheckLogs(ActionEvent event) {

		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource("UserDataView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setTitle("User Data");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.out.println(e);

		}
	}

	@FXML
	void deleteEntry(ActionEvent event) {
		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> foodCollection = database.getCollection("food_information");

		Document deleteDoc = new Document();

		Food food;
		food = tblInfo.getSelectionModel().getSelectedItem();

		deleteDoc.put("_id", new ObjectId(food.get_id().toString()));

		foodCollection.deleteOne(deleteDoc);

		txtFood.clear();
		tblInfo.getItems().clear();
		DistinctDAO.distinct(mapDB, "description", String.class).forEach(foodNames::add);
		TextFields.bindAutoCompletion(txtFood, foodNames);
		mongoClientData.close();

	}

	@FXML
	void addEntry(ActionEvent event) {

		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> foodCollection = database.getCollection("food_information");

		Document addDoc = new Document();

		Food food;
		food = tblInfo.getSelectionModel().getSelectedItem();

		addDoc.put("description", food.getDescription());
		addDoc.put("calories", food.getCalories());
		addDoc.put("fat", food.getFat());
		addDoc.put("carbs", food.getCarbs());
		addDoc.put("protein", food.getProtein());

		foodCollection.insertOne(addDoc);
		DistinctDAO.distinct(mapDB, "description", String.class).forEach(foodNames::add);
		TextFields.bindAutoCompletion(txtFood, foodNames);

		mongoClientData.close();

	}

	@FXML
	void Submit(ActionEvent event) {

		Document submitDoc = new Document();
		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");
		MongoCollection<Document> userDataCollection = database.getCollection("user_data");

		Food food;
		food = tblInfo.getSelectionModel().getSelectedItem();

		submitDoc.put("date", txtDate.getValue().toString());

		String description = txtFood.getText();

		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("description", description);

		FoodTrackerDAO dao = new FoodTrackerDAO();
		List<Food> list = dao.findBy(mapQuery);
		ObservableList<Food> data = FXCollections.observableArrayList(list);

		if (data.size() == 0) {
			JOptionPane.showMessageDialog(null, "Please Add New Entry Before Submitting", "Not Found",
					JOptionPane.ERROR_MESSAGE);
		} else {
			submitDoc.put("name", food.getDescription());
			submitDoc.put("calories", food.getCalories());
			submitDoc.put("fat", food.getFat());
			submitDoc.put("carbs", food.getCarbs());
			submitDoc.put("protein", food.getProtein());
			int quantity;
			try {
				quantity = Integer.parseInt(txtQuantity.getText());
				submitDoc.put("quantity", quantity);
				userDataCollection.insertOne(submitDoc);
				JOptionPane.showMessageDialog(null, "Entry has been submitted", "Inserted",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please Enter a Whole Number Quantity", "Not a Number",
						JOptionPane.ERROR_MESSAGE);

			}
			txtFood.clear();
			tblInfo.getItems().clear();
			txtDate.setValue(null);
			txtQuantity.clear();
			DistinctDAO.distinct(mapDB, "description", String.class).forEach(foodNames::add);
			TextFields.bindAutoCompletion(txtFood, foodNames);

			mongoClientData.close();
		}

	}

	@FXML
	void commitCalories(CellEditEvent<Food, Double> event) {

		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> foodCollection = database.getCollection("food_information");

		Food calorieSelected = tblInfo.getSelectionModel().getSelectedItem();

		try {
			calorieSelected.setCalories(Double.parseDouble(event.getNewValue().toString()));

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Please Enter a Valid Number", "Not a Number",
					JOptionPane.ERROR_MESSAGE);

		}
		foodCollection.updateOne(Filters.eq("description", txtFood.getText()),
				Updates.set("calories", calorieSelected.getCalories()));
		mongoClientData.close();
	}

	@FXML
	void commitCarbs(CellEditEvent<Food, Double> event) {
		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> foodCollection = database.getCollection("food_information");

		Food carbsSelected = tblInfo.getSelectionModel().getSelectedItem();

		try {
			carbsSelected.setCarbs(Double.parseDouble(event.getNewValue().toString()));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please Enter a Valid Number", "Not a Number",
					JOptionPane.ERROR_MESSAGE);

		}
		foodCollection.updateOne(Filters.eq("description", txtFood.getText()),
				Updates.set("carbs", carbsSelected.getCarbs()));
		mongoClientData.close();

	}

	@FXML
	void commitFat(CellEditEvent<Food, Double> event) {

		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> foodCollection = database.getCollection("food_information");

		Food fatSelected = tblInfo.getSelectionModel().getSelectedItem();
		try {
			fatSelected.setFat(Double.parseDouble(event.getNewValue().toString()));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please Enter a Valid Number", "Not a Number",
					JOptionPane.ERROR_MESSAGE);

		}
		foodCollection.updateOne(Filters.eq("description", txtFood.getText()),
				Updates.set("fat", fatSelected.getFat()));
		mongoClientData.close();

	}

	@FXML
	void commitProtein(CellEditEvent<Food, Double> event) {

		MongoClientURI uriData = new MongoClientURI(
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/Food?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClientData = new MongoClient(uriData);
		MongoDatabase database = mongoClientData.getDatabase("Food");

		MongoCollection<Document> foodCollection = database.getCollection("food_information");

		Food proteinSelected = tblInfo.getSelectionModel().getSelectedItem();
		try {
			System.out.println(event.getNewValue().toString());
			proteinSelected.setProtein(Double.parseDouble(event.getNewValue().toString()));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please Enter a Valid Number", "Not a Number",
					JOptionPane.ERROR_MESSAGE);

		}
		foodCollection.updateOne(Filters.eq("description", txtFood.getText()),
				Updates.set("protien", proteinSelected.getProtein()));
		mongoClientData.close();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tblInfo.setEditable(true);
		colCalories.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		colCarbs.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		colFat.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		colProtein.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		mapDB.put("uri",
				"mongodb://eqian17:password1029@cluster0-shard-00-00.1jdg9.mongodb.net:27017,cluster0-shard-00-01.1jdg9.mongodb.net:27017,cluster0-shard-00-02.1jdg9.mongodb.net:27017/sample_restaurants?ssl=true&replicaSet=atlas-9ozhs4-shard-0&authSource=admin&retryWrites=true&w=majority");
		mapDB.put("db", "Food");
		mapDB.put("col", "food_information");
		DistinctDAO.distinct(mapDB, "description", String.class).forEach(foodNames::add);
		TextFields.bindAutoCompletion(txtFood, foodNames);

	}

}