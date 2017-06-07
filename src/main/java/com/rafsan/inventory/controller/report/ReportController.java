package com.rafsan.inventory.controller.report;

import com.rafsan.inventory.entity.Invoice;
import com.rafsan.inventory.interfaces.ReportInterface;
import com.rafsan.inventory.model.InvoiceModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ReportController implements Initializable, ReportInterface {

    @FXML
    private TableView<Invoice> reportTable;
    @FXML
    private TableColumn<Invoice, Long> idColumn;
    @FXML
    private TableColumn<Invoice, String> employeeColumn, dateColumn;
    @FXML
    private TableColumn<Invoice, Double> totalColumn, vatColumn, discountColumn, 
            payableColumn, paidColumn, returnedColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button viewButton;
    private InvoiceModel model;

    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private Button menu;
    @FXML
    private VBox drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new InvoiceModel();
        
        drawerAction();
        loadData();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p)
                -> new SimpleStringProperty(p.getValue().getEmployee().getUserName()));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        vatColumn.setCellValueFactory(new PropertyValueFactory<>("vat"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        payableColumn.setCellValueFactory(new PropertyValueFactory<>("payable"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        returnedColumn.setCellValueFactory(new PropertyValueFactory<>("returned"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reportTable.setItems(REPORTLIST);

        filterData();

        viewButton
                .disableProperty()
                .bind(Bindings.isEmpty(reportTable.getSelectionModel().getSelectedItems()));
    }

    private void filterData() {
        FilteredList<Invoice> searchedData = new FilteredList<>(REPORTLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(report -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return report.getEmployee().getUserName().toLowerCase().contains(lowerCaseFilter);
                });
            });

            SortedList<Invoice> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(reportTable.comparatorProperty());
            reportTable.setItems(sortedData);
        });
    }
    
    private void loadData(){
    
        if (!REPORTLIST.isEmpty()) {
            REPORTLIST.clear();
        }
        REPORTLIST.addAll(model.getInvoices());
    }
    
    private void drawerAction() {

        TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
        menu.setOnAction((ActionEvent evt) -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
                menu.getStyleClass().remove("hamburger-button");
                menu.getStyleClass().add("open-menu");
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
                menu.getStyleClass().remove("open-menu");
                menu.getStyleClass().add("hamburger-button");
            }
        });
    }

    @FXML
    public void adminAction(ActionEvent event) throws Exception {
        windows("/fxml/Admin.fxml", "Admin", event);
    }
    
    @FXML
    public void productAction(ActionEvent event) throws Exception {

        windows("/fxml/Admin.fxml", "Admin", event);
    }
    
    @FXML
    public void categoryAction(ActionEvent event) throws Exception {

        windows("/fxml/Category.fxml", "Category", event);
    }

    @FXML
    public void purchaseAction(ActionEvent event) throws Exception {

        windows("/fxml/Purchase.fxml", "Purchase", event);
    }

    @FXML
    public void salesAction(ActionEvent event) throws Exception {

        windows("/fxml/Sales.fxml", "Sales", event);
    }

    @FXML
    public void supplierAction(ActionEvent event) throws Exception {
        windows("/fxml/Supplier.fxml", "Supplier", event);
    }
    
    @FXML
    public void staffAction(ActionEvent event) throws Exception {
        windows("/fxml/Employee.fxml", "Employee", event);
    }

    @FXML
    public void logoutAction(ActionEvent event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("Inventory:: Version 1.0");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }

    private void windows(String path, String title, ActionEvent event) throws Exception {

        double width = ((Node) event.getSource()).getScene().getWidth();
        double height = ((Node) event.getSource()).getScene().getHeight();

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root, width, height);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void viewAction(ActionEvent event) throws IOException {

        Invoice selectedInvoice = reportTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/report/View.fxml")));
        ViewController controller = new ViewController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("View Details");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setReport(selectedInvoice);
        reportTable.getSelectionModel().clearSelection();
    }
}
