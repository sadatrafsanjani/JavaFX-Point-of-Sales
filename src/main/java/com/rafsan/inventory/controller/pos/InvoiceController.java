package com.rafsan.inventory.controller.pos;

import com.rafsan.inventory.entity.Invoice;
import com.rafsan.inventory.entity.Item;
import com.rafsan.inventory.entity.Payment;
import com.rafsan.inventory.entity.Product;
import com.rafsan.inventory.entity.Sale;
import com.rafsan.inventory.model.EmployeeModel;
import com.rafsan.inventory.model.InvoiceModel;
import com.rafsan.inventory.model.ProductModel;
import com.rafsan.inventory.model.SalesModel;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InvoiceController implements Initializable {

    @FXML
    private TextField totalAmountField, paidAmountField;
    private double netPrice;
    private ObservableList<Item> items;
    private EmployeeModel employeeModel;
    private ProductModel productModel;
    private SalesModel salesModel;
    private InvoiceModel invoiceModel;
    private Payment payment;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productModel = new ProductModel();
        employeeModel = new EmployeeModel();
        salesModel = new SalesModel();
        invoiceModel = new InvoiceModel();
        totalAmountField.setText(String.valueOf(netPrice));
    }

    public void setData(double netPrice, ObservableList<Item> items, Payment payment) {

        this.netPrice = netPrice;
        this.items = FXCollections.observableArrayList(items);
        this.payment = payment;
    }

    @FXML
    public void confirmAction(ActionEvent event) throws Exception {

        if (validateInput()) {
            double paid = Double.parseDouble(paidAmountField.getText().trim());
            double retail = Math.abs(paid - netPrice);

            String invoiceId = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

            Invoice invoice = new Invoice(
                    invoiceId,
                    employeeModel.getEmployee(2),
                    payment.getSubTotal(),
                    payment.getVat(),
                    payment.getDiscount(),
                    payment.getPayable(),
                    paid,
                    retail
            );

            invoiceModel.saveInvoice(invoice);

            for (Item i : items) {

                Product p = productModel.getProductByName(i.getItemName());
                double quantity = p.getQuantity() - i.getQuantity();
                p.setQuantity(quantity);
                productModel.decreaseProduct(p);

                Sale sale = new Sale(
                        invoiceModel.getInvoice(invoiceId),
                        productModel.getProductByName(i.getItemName()),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getTotal()
                );

                salesModel.saveSale(sale);
            }

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/Confirm.fxml")));
            ConfirmController controller = new ConfirmController();
            controller.setData(retail, items, invoiceId);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            root.setOnMousePressed((MouseEvent e) -> {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent e) -> {
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);
            });
            stage.setTitle("Confirm");
            stage.getIcons().add(new Image("/images/logo.png"));
            stage.setScene(scene);
            stage.show();
        }

    }

    private boolean validateInput() {

        String errorMessage = "";

        if (paidAmountField.getText() == null || paidAmountField.getText().length() == 0) {
            errorMessage += "Invalid Input!\n";
        } else if (Double.parseDouble(paidAmountField.getText()) < netPrice) {
            errorMessage += "Insufficient Input!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please input the valid amount");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            paidAmountField.setText("");

            return false;
        }
    }

    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
