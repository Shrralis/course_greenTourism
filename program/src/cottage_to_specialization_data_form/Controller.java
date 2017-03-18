package cottage_to_specialization_data_form;

import base.DataFormComboBoxControllerAdditional;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Specialization;
import models.SpecializationToCottage;

/**
 * Created by shrralis on 3/14/17.
 */
public class Controller extends DataFormComboBoxControllerAdditional {
    {
        objectToProcess = new SpecializationToCottage();
    }

    @FXML public ComboBox<Specialization> specialization;
    @FXML public TextField count;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyComboBoxEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        ((SpecializationToCottage) objectToProcess).setSpecialization(specialization.getValue());
    }
    @Override
    protected String getDatabaseTableName() {
        return "cottages_has_specializations";
    }
}
