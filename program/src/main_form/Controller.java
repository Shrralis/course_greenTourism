package main_form;

import base.AlertsBuilder;
import base.DataFormComboBoxControllerAdditional;
import base.DataFormControllerInterface;
import base.OnMouseClickListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import server.DatabaseWorker;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Controller {
    @FXML private TabPane tabs;
    @FXML private TableView<Cottage> tableCottages;
    @FXML TableColumn<Cottage, String> columnCompanyTableCottages;
    @FXML private TableView<Company> tableCompanies;
    @FXML private TableView<Specialization> tableSpecializations;
    @FXML private TableView<Service> tableServices;
    @FXML private TableView<Distance> tableDistances;
    @FXML private TableView<Order> tableOrders;
    @FXML TableColumn<Order, String> columnDateFromTableOrders;
    @FXML TableColumn<Order, String> columnDateToTableOrders;
    @FXML TableColumn<Order, String> columnCottageTableOrders;
    @FXML TableColumn<Order, String> columnClientTableOrders;
    @FXML private TableView<Client> tableClients;

    @FXML
    public void initialize() {
        columnCompanyTableCottages.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getCompany().getName() != null) {
                return new SimpleStringProperty(param.getValue().getCompany().toString());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnDateFromTableOrders.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getDate_from() != null) {
                return new SimpleStringProperty(DateWorker.convertDateToString(param.getValue().getDate_from()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnDateToTableOrders.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getDate_to() != null) {
                return new SimpleStringProperty(DateWorker.convertDateToString(param.getValue().getDate_to()));
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnCottageTableOrders.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getCottage() != null) {
                return new SimpleStringProperty(param.getValue().getCottage().toString());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        columnClientTableOrders.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getClient() != null) {
                return new SimpleStringProperty(param.getValue().getClient().toString());
            } else {
                return new SimpleStringProperty("невідомо");
            }
        });
        tableCottages.setRowFactory(param -> {
            final TableRow<Cottage> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addDistance = new MenuItem("Додати відстань до...");
            final MenuItem addService = new MenuItem("Додати послугу");
            final MenuItem addSpecializations = new MenuItem("Додати спеціалізацію");
            final MenuItem findDistances = new MenuItem("Подивитися відстань звідси до...");
            final MenuItem findServices = new MenuItem("Послуги, які тут надаються");
            final MenuItem findSpecializations = new MenuItem("На чому спеціалізований");

            addDistance.setOnAction(event -> openAddForm(Distance.class));
            addService.setOnAction(event -> openAddForm(Service.class));
            addSpecializations.setOnAction(event -> openAddForm(Specialization.class));
            findDistances.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("cottage", tableCottages.getSelectionModel().getSelectedItem().getId());
                tableDistances.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "cottages_has_distances",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(4);
            });
            findServices.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("cottage", tableCottages.getSelectionModel().getSelectedItem().getId());
                tableServices.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "cottages_has_services",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(3);
            });
            findSpecializations.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("cottage", tableCottages.getSelectionModel().getSelectedItem().getId());
                tableSpecializations.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "cottages_has_specializations",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(2);
            });
            contextMenu.getItems().addAll(
                    addDistance,
                    addService,
                    addSpecializations,
                    findDistances,
                    findServices,
                    findSpecializations
            );
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableSpecializations.setRowFactory(param -> {
            final TableRow<Specialization> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToCottage = new MenuItem("Додати до котеджу");
            final MenuItem findCottages = new MenuItem("Котеджі, які спеціалізуються на цьому");

            addToCottage.setOnAction(event -> openAddToForm(tableSpecializations.getSelectionModel().getSelectedItem()));
            findCottages.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("specialization", tableSpecializations.getSelectionModel().getSelectedItem().getId());
                tableCottages.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "cottages_has_specializations",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(1);
            });
            contextMenu.getItems().addAll(addToCottage, findCottages);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableServices.setRowFactory(param -> {
            final TableRow<Service> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToCottage = new MenuItem("Додати до котеджу");
            final MenuItem findCottages = new MenuItem("Котеджі, які мають дану послугу");

            addToCottage.setOnAction(event -> openAddToForm(tableServices.getSelectionModel().getSelectedItem()));
            findCottages.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("service", tableServices.getSelectionModel().getSelectedItem().getId());
                tableCottages.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "cottages_has_services",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(1);
            });
            contextMenu.getItems().addAll(addToCottage, findCottages);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
        tableDistances.setRowFactory(param -> {
            final TableRow<Distance> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem addToCottage = new MenuItem("Додати до котеджу");
            final MenuItem findCottages = new MenuItem("Котеджі, у яких визначена мають відстань до таких об'єктів");

            addToCottage.setOnAction(event -> openAddToForm(tableDistances.getSelectionModel().getSelectedItem()));
            findCottages.setOnAction(event -> {
                HashMap<String, Object> params = new HashMap<>();

                params.put("distance", tableDistances.getSelectionModel().getSelectedItem().getId());
                tableCottages.setItems(
                        FXCollections.observableArrayList(
                                DatabaseWorker.processQuery(
                                        ServerQuery.create(
                                                "cottages_has_distances",
                                                "get",
                                                null,
                                                params
                                        )
                                ).getObjects()
                        )
                );
                tabs.getSelectionModel().select(1);
            });
            contextMenu.getItems().addAll(addToCottage, findCottages);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }
    @FXML
    private void onButtonAddClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Add);
    }
    @FXML
    private void onButtonEditClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Edit);
    }
    @FXML
    private void onButtonDeleteClick() {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();

        if (sSelectedTab.equalsIgnoreCase("котеджі")) {
            deleteRecord(tableCottages);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            deleteRecord(tableCompanies);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спеціалізації")) {
            deleteRecord(tableSpecializations);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("послуги")) {
            deleteRecord(tableServices);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("відстані")) {
            deleteRecord(tableDistances);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("замовлення")) {
            deleteRecord(tableOrders);
        } else {
            deleteRecord(tableClients);
        }
    }
    @FXML
    private void onButtonSearchClick() throws IOException {
        openDataForm(DataFormControllerInterface.Type.Search);
    }
    @FXML
    private void onButtonRefreshClick() {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();

        if (sSelectedTab.equalsIgnoreCase("котеджі")) {
            loadDataToTableFromDatabase(tableCottages, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            loadDataToTableFromDatabase(tableCompanies, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спеціалізації")) {
            loadDataToTableFromDatabase(tableSpecializations, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("послуги")) {
            loadDataToTableFromDatabase(tableServices, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("відстані")) {
            loadDataToTableFromDatabase(tableDistances, null);
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("замовлення")) {
            loadDataToTableFromDatabase(tableOrders, null);
        } else {
            loadDataToTableFromDatabase(tableClients, null);
        }
    }

    private void openDataForm(DataFormControllerInterface.Type type) throws IOException {
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();
        TableView tableView;
        FXMLLoader loader;

        if (sSelectedTab.equalsIgnoreCase("котеджі")) {
            tableView = tableCottages;
            loader = new FXMLLoader(getClass().getResource("/cottages_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            tableView = tableCompanies;
            loader = new FXMLLoader(getClass().getResource("/companies_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спеціалізації")) {
            tableView = tableSpecializations;
            loader = new FXMLLoader(getClass().getResource("/specializations_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("послуги")) {
            tableView = tableServices;
            loader = new FXMLLoader(getClass().getResource("/services_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("відстані")) {
            tableView = tableDistances;
            loader = new FXMLLoader(getClass().getResource("/distances_data_form/data.fxml"));
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("замовлення")) {
            tableView = tableOrders;
            loader = new FXMLLoader(getClass().getResource("/orders_data_form/data.fxml"));
        } else {
            tableView = tableClients;
            loader = new FXMLLoader(getClass().getResource("/clients_data_form/data.fxml"));
        }
        loader.load();

        DataFormControllerInterface controller = loader.getController();
        Stage dataFormStage = new Stage();

        if (type == DataFormControllerInterface.Type.Edit) {
            if (tableView.getSelectionModel().getSelectedItem() == null) {
                AlertsBuilder.start()
                        .setAlertType(Alert.AlertType.WARNING)
                        .setTitle("Помилка")
                        .setHeaderText("Помилка вибору")
                        .setContentText("Для редагування не було вибрано жодного значення у таблиці зверху!")
                        .build()
                        .showAndWait();
                return;
            }
            controller.setObjectToProcess((Owner) tableView.getSelectionModel().getSelectedItem());
        }
        setDataFormClickListeners(controller, type);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setType(type);
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.show();
    }

    private void setDataFormClickListeners(DataFormControllerInterface controller, DataFormControllerInterface.Type type) {
        OnMouseClickListener okListener = null;
        String sSelectedTab = tabs.getSelectionModel().getSelectedItem().getText();
        TableView tableView;

        if (sSelectedTab.equalsIgnoreCase("котеджі")) {
            tableView = tableCottages;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("компанії")) {
            tableView = tableCompanies;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("спеціалізації")) {
            tableView = tableSpecializations;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("послуги")) {
            tableView = tableServices;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("відстані")) {
            tableView = tableDistances;
        } else if (tabs.getSelectionModel().getSelectedItem().getText().equalsIgnoreCase("замовлення")) {
            tableView = tableOrders;
        } else {
            tableView = tableClients;
        }

        switch (type) {
            case Add:
                okListener = () -> controller.addObjectToTableView(tableView);
                break;
            case Edit:
                okListener = () -> controller.editObjectInTableView(tableView);
                break;
            case Search:
                okListener = () -> controller.search(this, tableView);
                break;
        }
        controller.setOnMouseOkClickListener(okListener);
    }

    void setupAllTables() {
        loadDataToTableFromDatabase(tableCottages, null);
        loadDataToTableFromDatabase(tableCompanies, null);
        loadDataToTableFromDatabase(tableSpecializations, null);
        loadDataToTableFromDatabase(tableServices, null);
        loadDataToTableFromDatabase(tableDistances, null);
        loadDataToTableFromDatabase(tableOrders, null);
        loadDataToTableFromDatabase(tableClients, null);
    }
    @SuppressWarnings("unchecked")
    public void loadDataToTableFromDatabase(TableView tableView, HashMap<String, Object> params) {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.getType() == TableView.class && field.get(this) == tableView) {
                    tableView.setItems(
                            FXCollections.observableArrayList(
                                    DatabaseWorker.processQuery(
                                            ServerQuery.create(
                                                    field.getName().substring("table".length()).toLowerCase(),
                                                    "get", null, params
                                            )
                                    ).getObjects()
                            )
                    );
                    break;
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    private void deleteRecord(TableView tableView) {
        String tableName = null;

        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.getType() == TableView.class && field.get(this) == tableView) {
                    tableName = field.getName().substring("table".length()).toLowerCase();

                    break;
                }
            } catch (IllegalAccessException ignored) {}
        }

        ServerResult result = DatabaseWorker.processQuery(
                ServerQuery.create(
                        tableName,
                        "delete",
                        (Owner) tableView.getSelectionModel().getSelectedItem(),
                        null
                )
        );

        if (result != null && result.getResult() == 0) {
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        } else {
            AlertsBuilder.start()
                    .setAlertType(Alert.AlertType.WARNING)
                    .setTitle("Видалення")
                    .setHeaderText("Помилка видалення")
                    .build()
                    .showAndWait();
        }
    }

    private <T extends Owner, E extends DataFormComboBoxControllerAdditional> void openAddForm(Class<T> disOrSerOrSpec) {
        FXMLLoader loader;

        if (disOrSerOrSpec.equals(Distance.class)) {
            loader = new FXMLLoader(getClass().getResource("/cottage_to_distance_data_form/data.fxml"));
        } else if (disOrSerOrSpec.equals(Service.class)) {
            loader = new FXMLLoader(getClass().getResource("/cottage_to_service_data_form/data.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/cottage_to_specialization_data_form/data.fxml"));
        }

        try {
            loader.load();
        } catch (IOException ignored) {}

        E controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setObjectToSearch(tableCottages.getSelectionModel().getSelectedItem());
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }

    private <T extends Owner, E extends DataFormComboBoxControllerAdditional> void openAddToForm(T disOrSerOrSpec) {
        FXMLLoader loader;

        if (disOrSerOrSpec instanceof Distance) {
            loader = new FXMLLoader(getClass().getResource("/distance_to_cottage_data_form/data.fxml"));
        } else if (disOrSerOrSpec instanceof Service) {
            loader = new FXMLLoader(getClass().getResource("/service_to_cottage_data_form/data.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("/specialization_to_cottage_data_form/data.fxml"));
        }

        try {
            loader.load();
        } catch (IOException ignored) {}

        E controller = loader.getController();
        Stage dataFormStage = new Stage();

        controller.setObjectToSearch(disOrSerOrSpec);
        dataFormStage.setScene(new Scene(loader.getRoot()));
        controller.setPrimaryStage(dataFormStage);
        dataFormStage.showAndWait();
    }
}
