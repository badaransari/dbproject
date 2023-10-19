package com.example.javafx_db_example;


import com.example.javafx_db_example.db.ConnDbOps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DB_GUI_Controller implements Initializable {
    private final ObservableList<com.example.javafx_db_example.Person> data = FXCollections.observableArrayList();
    @FXML
    TextField name;
    @FXML
    TextField email;
    @FXML
    TextField address;
    @FXML
    TextField password;
    @FXML
    TextField phone;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, String> tv_name;
    @FXML
    private TableColumn<Person, String> tv_email;
    @FXML
    private TableColumn<Person, String> tv_address;
    @FXML
    private TableColumn<Person, String> tv_password;
    @FXML
    private TableColumn<Person, String> tv_phone;
    @FXML
    ImageView img_view;

    public DB_GUI_Controller() {
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tv_name.setCellValueFactory(new PropertyValueFactory("name"));
        this.tv_email.setCellValueFactory(new PropertyValueFactory("email"));
        this.tv_address.setCellValueFactory(new PropertyValueFactory("address"));
        this.tv_password.setCellValueFactory(new PropertyValueFactory("password"));
        this.tv_phone.setCellValueFactory(new PropertyValueFactory("phone"));
        this.loadUserData();
    }

    @FXML
    protected void addNewRecord() {
        Person newPerson = new Person(this.name.getText(), this.email.getText(), this.address.getText(), this.password.getText(), this.phone.getText());
        ConnDbOps dbOps = new ConnDbOps();
        dbOps.insertUser(newPerson);
        this.loadUserData();
    }

    @FXML
    protected void clearForm() {
        this.name.clear();
        this.email.clear();
        this.address.clear();
        this.password.clear();
        this.phone.clear();
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    @FXML
    protected void editRecord() {

        Person p = tv.getSelectionModel().getSelectedItem();
        if (p != null) {
            Person p2 = new Person(name.getText(), email.getText(), address.getText(), password.getText(), phone.getText());
            ConnDbOps dbOps = new ConnDbOps();
            dbOps.updateUser(p2);
            this.loadUserData();
        }
    }

    @FXML
    protected void deleteRecord() {
        Person p = (Person)this.tv.getSelectionModel().getSelectedItem();
        ConnDbOps dbOps = new ConnDbOps();
        dbOps.deleteUser(p);
        this.loadUserData();
    }

    @FXML
    protected void showImage() {
        File file = (new FileChooser()).showOpenDialog(this.img_view.getScene().getWindow());
        if (file != null) {
            this.img_view.setImage(new Image(file.toURI().toString()));
        }

    }

    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p = (Person)this.tv.getSelectionModel().getSelectedItem();
        this.name.setText(p.getName());
        this.email.setText(p.getEmail());
        this.address.setText(p.getAddress());
        this.password.setText(p.getPassword());
        this.phone.setText(p.getPhone());
    }

    private void loadUserData() {
        this.data.clear();
        ConnDbOps dbOps = new ConnDbOps();
        this.data.addAll(dbOps.retrieveDataFromDatabase());
        this.tv.setItems(this.data);
    }
}
