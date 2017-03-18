package orders_data_form;

import base.AlertsBuilder;
import base.DataFormComboBoxController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Client;
import models.Cottage;
import models.DateWorker;
import models.Order;

/**
 * Created by shrralis on 3/13/17.
 */
@SuppressWarnings("unused")
public class Controller extends DataFormComboBoxController {
    {
        objectToProcess = new Order();
    }

    @FXML public DatePicker date_from;
    @FXML public DatePicker date_to;
    @FXML public ComboBox<Cottage> cottage;
    @FXML public ComboBox<Client> client;
    @FXML public CheckBox bDate_from;
    @FXML public CheckBox bDate_to;
    @FXML public CheckBox bCottage;
    @FXML public CheckBox bClient;

    @Override
    protected boolean areTheFieldsValidForAdding() {
        return !isAnyTextFieldEmpty() && !isAnyComboBoxEmpty();
    }
    @Override
    protected void objectToProcessBasedOnFields() {
        if (bDate_from.isSelected()) {
            ((Order) objectToProcess).setDate_from(date_from.getValue() == null ? null : DateWorker.convertToDate(date_from));
        }

        if (bDate_to.isSelected()) {
            ((Order) objectToProcess).setDate_to(date_to.getValue() == null ? null : DateWorker.convertToDate(date_to));
        }

        if (bCottage.isSelected()) {
            ((Order) objectToProcess).setCottage(cottage.getValue());
        }

        if (bClient.isSelected()) {
            ((Order) objectToProcess).setClient(client.getValue());
        }
    }
    @Override
    protected String getDatabaseTableName() {
        return "orders";
    }
}
