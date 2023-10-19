package com.example.javafx_db_example.db;

import com.example.javafx_db_example.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Badar Ansari
 */
public class ConnDbOps {


    final String MYSQL_SERVER_URL = "jdbc:mysql://comp123.mariadb.database.azure.com/";
    final String DB_URL = "jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB";
    final String USERNAME = "computer@comp123";
    final String PASSWORD = "Nassau_123";



    /**
     * @return register users
     */
    public boolean connectToDatabase() {
        boolean hasRegistredUsers = false;



        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/", "computer@comp123", "Nassau_123");
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS CSC311DB");
            statement.close();
            conn.close();
            conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123");
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,name VARCHAR(200) NOT NULL,email VARCHAR(200) NOT NULL UNIQUE,phone VARCHAR(200),address VARCHAR(200),password VARCHAR(200) NOT NULL)";
            statement.executeUpdate(sql);
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");
            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return hasRegistredUsers;
    }

    public ObservableList<Person> retrieveDataFromDatabase() {
        ObservableList<Person> userList = FXCollections.observableArrayList();

        try{
            try  (Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123")) {
                String sql = "SELECT * FROM users";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    String password = resultSet.getString("password");
                    Person person = new Person(name, email, address, password, phone);
                    userList.add(person);
                }

                preparedStatement.close();
                conn.close();
            }
        } catch (SQLException var13) {
            var13.printStackTrace();
        }

        return userList;
    }
    //finds info based on the name
    public void queryUserByName(String name) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123");
            String sql = "SELECT * FROM users WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException var10) {
            var10.printStackTrace();
        }

    }
    // this function takes all the users that exist in the database with all of their info that is in the server
    public void listAllUsers() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123");
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                String password = resultSet.getString("password");
                Person person = new Person(name, email, address, password, phone);
                System.out.println("ID: " + id + ", Name: " + person.getName() + ", Email: " + person.getEmail() + ", Phone: " + person.getPhone() + ", Address: " + person.getAddress() + ", Password: " + person.getPassword());
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException var12) {
            var12.printStackTrace();
        }

    }
    // the insert function has been integrated with the person class, It was intended to insert data and save it in order for the controller class to connect to it
    public void insertUser(Person person) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123");
            String sql = "INSERT INTO users (name, email, address, password, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getEmail());
            preparedStatement.setString(3, person.getAddress());
            preparedStatement.setString(4, person.getPassword());
            preparedStatement.setString(5, person.getPhone());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }
    // the function below is connected to the person class and the
    public void updateUser(Person person) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123");
            String sql = "UPDATE users SET name=?, address=?, password=?, phone=? WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getAddress());
            preparedStatement.setString(3, person.getPassword());
            preparedStatement.setString(4, person.getPhone());
            preparedStatement.setString(5, person.getEmail());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("User updated successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }
    //deletes the user by finding the email
    public void deleteUser(Person person) {
        try {
            //connects to the server
            Connection conn = DriverManager.getConnection("jdbc:mysql://comp123.mariadb.database.azure.com/CSC311DB", "computer@comp123", "Nassau_123");
            //sql data base which sets the parameters for username and email
            String sql = "DELETE FROM users WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getEmail());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("User deleted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

    }
}
