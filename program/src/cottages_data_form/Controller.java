package cottages_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Company;
import models.Cottage;

import java.util.Calendar;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormComboBoxController {
    {
        objectToProcess = new Cottage();
    }

    @FXML public TextField name;
    @FXML public TextField district;
    @FXML public TextField village;
    @FXML public TextField address;
    @FXML public TextField coordinates;
    @FXML public TextField price_per_day;
    @FXML public ComboBox<Company> company;
    @FXML public CheckBox bName;
    @FXML public CheckBox bDistrict;
    @FXML public CheckBox bVillage;
    @FXML public CheckBox bAddress;
    @FXML public CheckBox bCoordinates;
    @FXML public CheckBox bPrice_per_day;
    @FXML public CheckBox bCompany;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty() || isAnyComboBoxEmpty()) {
            return false;
        }

        if (!price_per_day.getText().trim().matches("^\\d+(\\.\\d+)?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + price_per_day.getPromptText() + " повинно містити ціле або дробове число!\n" +
                            "Приклад: 1.09")
                    .build()
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Cottage) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }

        if (bDistrict.isSelected()) {
            ((Cottage) objectToProcess).setDistrict(district.getText().trim().isEmpty() ? null : district.getText().trim());
        }

        if (bVillage.isSelected()) {
            ((Cottage) objectToProcess).setVillage(village.getText().trim().isEmpty() ? null : village.getText().trim());
        }

        if (bAddress.isSelected()) {
            ((Cottage) objectToProcess).setAddress(address.getText().trim().isEmpty() ? null : address.getText().trim());
        }

        if (bCoordinates.isSelected()) {
            ((Cottage) objectToProcess).setCoordinates(coordinates.getText().trim().isEmpty() ? null :
                    coordinates.getText().trim());
        }

        if (bPrice_per_day.isSelected()) {
            ((Cottage) objectToProcess).setPrice_per_day(price_per_day.getText().trim().isEmpty() ? null :
                    new Double(price_per_day.getText().trim()));
        }

        if (bCompany.isSelected()) {
            ((Cottage) objectToProcess).setCompany(company.getValue());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "cottages";
    }
}
