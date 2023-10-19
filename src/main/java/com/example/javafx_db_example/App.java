package com.example.javafx_db_example;



import com.example.javafx_db_example.db.ConnDbOps;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class App extends Application {
    private static ConnDbOps cdbop;



    @Override
    public void start(Stage stage) throws IOException {
        // Launch the GUI by loading the FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("db_interface_gui.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        cdbop = new ConnDbOps();
        Scanner scan = new Scanner(System.in);
        //menu
        char input;
        do {
            System.out.println(" ");
            System.out.println("============== Menu ==============");
            System.out.println("| To start GUI,           press 'g' |");
            System.out.println("| To connect to DB,       press 'c' |");
            System.out.println("| To display all users,   press 'a' |");
            System.out.println("| To insert to the DB,    press 'i' |");
            System.out.println("| To query by name,       press 'q' |");
            System.out.println("| To delete a user,       press 'd' |");
            System.out.println("| To edit user info,      press 'k' |");
            System.out.println("| To exit,                press 'e' |");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");
            input = scan.next().charAt(0);
            scan.nextLine(); // Consume the newline character

            switch (input) {
                case 'g':
                    // Launch the GUI, splash screen will be shown first
                    launch(args);
                    break;


                case 'c':
                    cdbop.connectToDatabase(); // Connect to the database
                    break;

                case 'a':
                    cdbop.listAllUsers(); // List all users in the DB
                    break;
                // insert new data for a new user(same feature can be done via gui)
                case 'i':
                    Person p;
                    System.out.print("Enter Name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scan.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scan.nextLine();
                    System.out.print("Enter Address: ");
                    String address = scan.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scan.nextLine();
                    p = new Person(name, email, address, password, phone);
                    cdbop.insertUser(p);
                    break;

                case 'q':
                    System.out.print("Enter the name to query: ");
                    String queryName = scan.nextLine();
                    cdbop.queryUserByName(queryName);
                    break;
                //safely deletes your info just by finding your email
                case 'd':
                    System.out.print("Enter the email to delete: ");
                    String deleteEmail = scan.nextLine();
                    Person personToDelete = new Person("", deleteEmail, "", "", "");
                    cdbop.deleteUser(personToDelete);
                    break;
                //updates your email and name
                case 'k':
                    System.out.print("Enter the email to update: ");
                    String updateEmail = scan.nextLine();
                    System.out.print("Enter the new name: ");
                    String newName = scan.nextLine();
                    Person updatedPerson = new Person(newName, updateEmail, "", "", "");
                    cdbop.updateUser(updatedPerson);
                    break;

                case 'e':
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(" ");
        } while (input != 'e');

        scan.close();
    }
}
