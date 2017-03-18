package distances_data_form;

import base.DataFormController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Distance;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormController {
    {
        objectToProcess = new Distance();
    }

    @FXML public TextField destination_name;
    @FXML public CheckBox bDestination_name;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyTextFieldEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bDestination_name.isSelected()) {
            ((Distance) objectToProcess).setDestination_name(destination_name.getText().trim().isEmpty() ? null :
                    destination_name.getText().trim());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "distances";
    }
}
