package com.rafsan.inventory.controller.pos;

import com.rafsan.inventory.AppState;
import com.rafsan.inventory.entity.Item;
import com.rafsan.inventory.entity.Payment;
import com.rafsan.inventory.entity.Product;
import com.rafsan.inventory.model.ProductModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

import com.rafsan.inventory.interfaces.Controller;
import com.rafsan.inventory.interfaces.ProductInterface;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class PosController implements Initializable, ProductInterface, Controller {

    private AppState appState;

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Item> listTableView;
    @FXML
    private TableColumn<Product, String> productColumn;
    @FXML
    private TableColumn<Item, String> itemColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn, quantityColumn, totalColumn;
    @FXML
    private TextField searchField, productField, priceField, quantityField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField subTotalField, discountField, vatField, netPayableField;
    @FXML
    private Button addButton, removeButton, paymentButton;
    @FXML
    private Label quantityLabel;
    @FXML
    private ObservableList<Item> ITEMLIST;
    private ProductModel productModel;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ITEMLIST = FXCollections.observableArrayList();
        productModel = new ProductModel();

        loadData();

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
        productTableView.setItems(PRODUCTLIST);

        filterData();

        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        listTableView.setItems(ITEMLIST);

        addButton
                .disableProperty()
                .bind(Bindings.isEmpty(productTableView.getSelectionModel().getSelectedItems()));
        removeButton
                .disableProperty()
                .bind(Bindings.isEmpty(listTableView.getSelectionModel().getSelectedItems()));
    }

    private void filterData() {
        FilteredList<Product> searchedData = new FilteredList<>(PRODUCTLIST, e -> true);

        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Product> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
            productTableView.setItems(sortedData);
        });
    }

    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    private void loadData() {
        if (!PRODUCTLIST.isEmpty()) {
            PRODUCTLIST.clear();
        }

        PRODUCTLIST.addAll(productModel.getProducts());
    }

    private void showDetails(Product product) {
        if (product != null) {
            quantityField.setDisable(false);
            productField.setText(product.getProductName());
            priceField.setText(String.valueOf(product.getPrice()));

            double quantity = product.getQuantity();

            if (quantity > 0) {
                quantityField.setEditable(true);
                quantityField.setStyle(null);
            } else {
                quantityField.setEditable(false);
                quantityField.setStyle("-fx-background-color: red;");
            }
            quantityLabel.setText("Stock: " + String.valueOf(quantity));
            descriptionArea.setText(product.getDescription());
        } else {
            productField.setText("");
            priceField.setText("");
            quantityLabel.setText("");
            descriptionArea.setText("");
        }
    }

    private void resetProductTableSelection() {
        productTableView.getSelectionModel().clearSelection();
    }

    private void resetItemTable() {
        ITEMLIST.clear();
    }

    private void resetAdd() {
        productField.setText("");
        priceField.setText("");
        quantityField.setText("");
        resetQuantityField();
        quantityLabel.setText("Available: ");
        descriptionArea.setText("");
    }

    private void resetInvoice() {
        subTotalField.setText("0.00");
        vatField.setText("0.00");
        netPayableField.setText("0.00");
    }

    private void resetQuantityField() {
        quantityField.setDisable(true);
    }

    private void resetPaymentButton() {
        paymentButton.setDisable(true);
    }

    private void resetInterface() {
        loadData();
        resetPaymentButton();
        resetProductTableSelection();
        resetItemTable();
        resetQuantityField();
        resetAdd();
        resetInvoice();
    }

    @FXML
    public void resetAction(ActionEvent event) {
        resetInterface();
    }

    @FXML
    public void addAction(ActionEvent event) {

        if (validateInput()) {
            String productName = productField.getText();
            double unitPrice = Double.parseDouble(priceField.getText());
            double quantity = Double.parseDouble(quantityField.getText());
            double total = unitPrice * quantity;
            ITEMLIST.add(new Item(productName, unitPrice, quantity, total));
            calculation();

            resetAdd();
            productTableView.getSelectionModel().clearSelection();
        }
    }

    private void calculation() {

        double subTotalPrice = 0.0;
        subTotalPrice = listTableView.getItems().stream().map(
                (item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);

        if (subTotalPrice > 0) {
            paymentButton.setDisable(false);
            double vat = (double) subTotalPrice * 0.025;
            double netPayablePrice = (double) (Math.abs((subTotalPrice + vat) - 5));

            subTotalField.setText(String.valueOf(subTotalPrice));
            vatField.setText(String.valueOf(vat));
            netPayableField.setText(String.valueOf(netPayablePrice));
        }
    }

    @FXML
    public void paymentAction(ActionEvent event) throws Exception {

        Payment payment = new Payment(
                Double.parseDouble(subTotalField.getText().trim()),
                Double.parseDouble(vatField.getText().trim()),
                Double.parseDouble(discountField.getText().trim()),
                Double.parseDouble(netPayableField.getText().trim())
        );

        ObservableList<Item> sold = listTableView.getItems();

        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/Invoice.fxml")));
        InvoiceController controller = new InvoiceController();
        loader.setController(controller);
        controller.setData(Double.parseDouble(netPayableField.getText().trim()), sold, payment);
        Parent root = loader.load();
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Payment");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setScene(scene);
        stage.showAndWait();

        resetInterface();
    }

    @FXML
    public void removeAction(ActionEvent event) {

        int index = listTableView.getSelectionModel().getSelectedIndex();

        if (index > 0) {
            listTableView.getItems().remove(index);
            calculation();
        } else if (index == 0) {
            listTableView.getItems().remove(index);
            resetInvoice();
        }
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (quantityField.getText() == null || quantityField.getText().length() == 0) {
            errorMessage += "Quantity not supplied!\n";
        } else {
            double quantity = Double.parseDouble(quantityField.getText());
            String available = quantityLabel.getText();
            double availableQuantity = Double.parseDouble(available.substring(7));

            if (quantity > availableQuantity) {
                errorMessage += "Out of Stock!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please input the valid number of products");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            quantityField.setText("");

            return false;
        }
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
}
