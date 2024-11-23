package Bookstore.scenes.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import Bookstore.SqlConnectionPoolFactory;
import Bookstore.dataManagers.AdminManager;
import Bookstore.models.UserRole;
import Bookstore.models.UserSession;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserRoleManagerView {

    private final AnchorPane rootPane;
    private final TableView<UserSession> userTable;
    private final AdminManager adminManager;

    public UserRoleManagerView(){
        this.adminManager = new AdminManager(SqlConnectionPoolFactory.createConnectionPool());
        this.rootPane = new AnchorPane();
        this.userTable = new TableView<>();
        initializeUI();
    }

    public AnchorPane getRoot(){
        return rootPane;
    }

    private void initializeUI(){

        rootPane.setPrefSize(800, 600);

        Label title = new Label("User Role Management");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        userTable.setPrefWidth(600);
        userTable.setPrefHeight(400);

        // table config
        TableColumn<UserSession, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));

        TableColumn<UserSession, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

        TableColumn<UserSession, UserRole> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUserRole()));

        roleColumn.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<UserRole> roleComboBox = new ComboBox<>(FXCollections.observableArrayList(UserRole.values()));

            @Override 
            protected void updateItem(UserRole role, boolean empty){
                super.updateItem(role, empty);
                if (empty){
                    setGraphic(null);
                }else{
                    roleComboBox.setValue(role);
                    roleComboBox.setOnAction(e -> {
                        UserSession user = getTableView().getItems().get(getIndex());
                        user.setUserRole(roleComboBox.getValue());
                        handleEditUserRoles(user);
                    });
                    setGraphic(roleComboBox);
                }
            }
        });

        userTable.getColumns().addAll(usernameColumn, lastNameColumn, roleColumn);

        userTable.setItems(getUserData());

        VBox layout = new VBox(10, title, userTable);
        layout.setAlignment(Pos.CENTER);
        
        rootPane.getChildren().addAll(layout);
    }

    private ObservableList<UserSession> getUserData(){
        ObservableList<UserSession> users = FXCollections.observableArrayList();
        String query = "SELECT userID, username, lastName, role FROM Users";

        try (Connection connection = adminManager.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()){
                UserSession user = new UserSession();
                user.setUserId(resultSet.getInt("userID"));
                user.setUsername(resultSet.getString("username"));
                user.setLastName(resultSet.getString("lastName"));
                user.setUserRole(UserRole.fromString(resultSet.getString("role")));
                users.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
    
    private void handleEditUserRoles(UserSession user){
        try{
            adminManager.updateUserRole(user.getUserId(), user.getUserRole());
            System.out.println("Successfully updated role for user: " + user.getUsername());
        } catch(SQLException e){
            e.printStackTrace();
            System.err.println("Error updating role for user: " + user.getUsername());
        }
    }
}

