package viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Person;
import model.Phonebook;
import model.PhonebookException;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class PhonebookC
{
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private Text txIndex;

    @FXML
    private Text txSize;


    private Phonebook phonebook;
    private Person person;

    private static NumberFormat nf;

    static {
        nf = NumberFormat.getIntegerInstance();
    }

    @FXML
    private void btPrevOnAction(ActionEvent actionEvent)
    {
        try
        {
            save();

            if (person != null)
            {
                Person p = person.prev();
                if (p != null)
                {
                    person = p;
                }
                display();
            }
        }
        catch(PhonebookException e)
        {
            displayErrorMessage(e);
        }
    }

    @FXML
    private void btNextOnAction(ActionEvent actionEvent)
    {
        if (person != null) {
            Person p = person.next();
            if (p != null) {
                person = p;
            }
            display();
        }
    }

    @FXML
    private void btNewOnAction(ActionEvent actionEvent)
    {
        try
        {
            person = new Person(phonebook, "", "");
            display();
        }
        catch(PhonebookException e)
        {
            displayErrorMessage(e);
        }
    }

    @FXML
    private void btDelOnAction(ActionEvent actionEvent)
    {
        Person del = person;
        Person next = del.next();
        Person prev = del.prev();

        phonebook.deletePerson(del);

        try {
            if (next != null) {
                person = next;
            } else {
                if (prev != null) {
                    person = prev;
                } else {
                    person = new Person(phonebook, "", "");
                }
            }

            display();
        } catch (PhonebookException e)
        {
            displayErrorMessage(e);
        }

    }

    public static void show(Stage stage, File db)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(PhonebookC.class.getResource("/viewcontroller/phonebookV.fxml"));
            Parent root = fxmlLoader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Phonebook");

            PhonebookC phonebookC = fxmlLoader.getController();
            phonebookC.init(stage, db);

            stage.show();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void init(Stage stage, File db)
    {
        // Loading Database
        phonebook = new Phonebook(db);

        // Display the first Person in the Database
        person = phonebook.firstPerson();
        display();

        // Saving Database when closing the program
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
			public void handle(WindowEvent windowEvent)
			{ phonebook.saveToCsv(db); }
		});
    }

    private void display()
    {
        // Display the current Person
        if (person != null)
        {
            tfName.setText(person.getName());
            tfAddress.setText(person.getAddress());
            tfPhone.setText(person.getPhone());
            txIndex.setText(nf.format(person.index()));
            txSize.setText(nf.format(phonebook.size()));
        }
    }

    private void save() throws PhonebookException
    {
        // get values
        person.setName(tfName.getText());
        person.setAddress(tfAddress.getText());
        person.setPhone(tfPhone.getText());

        // sollte eigentlich unnötig sein ...
        phonebook.sort();
    }

    public static void displayErrorMessage(PhonebookException e)
    {
        // Displaying an Error - Dialog to the User
        Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("An Error Occured!!!");
		alert.setHeaderText("Ungültige Eingabe!");
		alert.setContentText(e.getMessage());
		alert.showAndWait(); // Waiting for the User to do something
    }
}