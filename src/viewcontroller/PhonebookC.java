package viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Person;
import model.Phonebook;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * @author David Wandratsch
 * @version 1.0
 */

public class PhonebookC
{
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPhone;
    @FXML
    private Button btPrev;
    @FXML
    private Text txIndex;
    @FXML
    private Text txSize;
    @FXML
    private Button btNext;
    @FXML
    private Button btNew;
    @FXML
    private Button btDel;

    private Phonebook phonebook;
    private Person person;
    private File db;

    private static NumberFormat nf;

    static {
        nf = NumberFormat.getIntegerInstance();
    }

    @FXML
    private void btPrevOnAction(ActionEvent actionEvent) {
        if (person != null) {
            Person p = person.prev();
            if (p != null) {
                person = p;
            }
            display();
        }
    }

    @FXML
    private void btNextOnAction(ActionEvent actionEvent) {
        if (person != null) {
            Person p = person.next();
            if (p != null) {
                person = p;
            }
            display();
        }
    }

    @FXML
    private void btNewOnAction(ActionEvent actionEvent) {
        person = new Person(phonebook, "", "");
        display();
    }

    @FXML
    private void btDelOnAction(ActionEvent actionEvent) {
        Person del = person;
        Person next = del.next();
        Person prev = del.prev();

        phonebook.deletePerson(del);

        if (next != null) {
            person = next;
        }
        else {
            if (prev != null) {
                person = prev;
            }
            else {
                person = new Person(phonebook, "", "");
            }
        }

        display();
    }

    @FXML
    private void btSaveOnAction(ActionEvent actionEvent) {
        save();
    }

    @FXML
    private void btLoadCsvOnAction(ActionEvent actionEvent) {
        phonebook.loadFromCsv(db);

        person = phonebook.firstPerson();
        display();
    }

    @FXML
    private void btSaveCsvOnAction(ActionEvent actionEvent) {
        phonebook.saveToCsv(db);
    }


    public PhonebookC() {
    }

    public static void show(Stage stage, File db) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PhonebookC.class.getResource("/viewcontroller/phonebookV.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Phonebook");

            PhonebookC phonebookC = fxmlLoader.getController();
            phonebookC.init(stage, db);

            stage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void init(Stage stage, File db) {
        this.db = db;

        // Load Database
        phonebook = new Phonebook(db);
    }


    private void display() {
        if (person != null) {
            tfName.setText(person.getName());
            tfAddress.setText(person.getAddress());
            tfPhone.setText(person.getPhone());

            txIndex.setText(nf.format(person.index()));
            txSize.setText(nf.format(phonebook.size()));
        }
    }


    private void save() {
        // get values
        person.setName(tfName.getText());
        person.setAddress(tfAddress.getText());
        person.setPhone(tfPhone.getText());

        // sollte eigentlich unnötig sein ...
        phonebook.sort();

        // save to database
        // already in set - is saved afterwards
    }
}
