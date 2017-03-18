package distance_to_cottage_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Cottage;
import models.DistanceToCottage;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new DistanceToCottage();
    }

    @FXML public ComboBox<Cottage> cottage;
    @FXML public TextField kilometers;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        if (isAnyTextFieldEmpty() || isAnyComboBoxEmpty()) {
            return false;
        }

        if (!kilometers.getText().trim().matches("^\\d+(\\.\\d+)?$")) {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Помилка")
                    .setHeaderText("Помилка вводу")
                    .setContentText("Поле " + kilometers.getPromptText() + " повинно містити ціле або дробове число!\n" +
                            "Приклад: 1.09")
                    .build()
                    .showAndWait();
            return false;
        }
        return true;
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        ((DistanceToCottage) objectToProcess).setCottage(cottage.getValue());
        ((DistanceToCottage) objectToProcess).getDistance().setKilometers(kilometers.getText().isEmpty() ? null :
                new Double(kilometers.getText()));
    }
    @Override
    protected String getDatabaseTableName() {
        return "cottages_has_distances";
    }
}
