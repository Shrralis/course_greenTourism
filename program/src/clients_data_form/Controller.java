package clients_data_form;

import base.DataFormComboBoxController;
import base.DataFormController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Client;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormController {
    {
        objectToProcess = new Client();
    }

    @FXML public TextField name;
    @FXML public TextField surname;
    @FXML public TextField passport;
    @FXML public TextField mobile;
    @FXML public CheckBox bName;
    @FXML public CheckBox bSurname;
    @FXML public CheckBox bPassport;
    @FXML public CheckBox bMobile;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyTextFieldEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Client) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bSurname.isSelected()) {
            ((Client) objectToProcess).setSurname(surname.getText().trim().isEmpty() ? null : surname.getText().trim());
        }

        if (bPassport.isSelected()) {
            ((Client) objectToProcess).setPassport(passport.getText().trim().isEmpty() ? null : passport.getText().trim());
        }

        if (bMobile.isSelected()) {
            ((Client) objectToProcess).setMobile(mobile.getText().trim().isEmpty() ? null : mobile.getText().trim());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "clients";
    }
}
