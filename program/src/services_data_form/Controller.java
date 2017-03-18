package services_data_form;

import base.DataFormController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Distance;
import models.Service;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormController {
    {
        objectToProcess = new Service();
    }

    @FXML public TextField name;
    @FXML public CheckBox bName;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyTextFieldEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bName.isSelected()) {
            ((Service) objectToProcess).setName(name.getText().trim().isEmpty() ? null : name.getText().trim());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "services";
    }
}
