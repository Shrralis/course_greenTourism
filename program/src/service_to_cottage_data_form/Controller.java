package service_to_cottage_data_form;

import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import models.Cottage;
import models.ServiceToCottage;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new ServiceToCottage();
    }

    @FXML public ComboBox<Cottage> cottage;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyComboBoxEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        ((ServiceToCottage) objectToProcess).setCottage(cottage.getValue());
    }
    @Override
    protected String getDatabaseTableName() {
        return "cottages_has_services";
    }
}
