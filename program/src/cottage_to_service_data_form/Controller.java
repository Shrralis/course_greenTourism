package cottage_to_service_data_form;

import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import models.Service;
import models.ServiceToCottage;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new ServiceToCottage();
    }

    @FXML public ComboBox<Service> service;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyComboBoxEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        ((ServiceToCottage) objectToProcess).setService(service.getValue());
    }
    @Override
    protected String getDatabaseTableName() {
        return "cottages_has_services";
    }
}
