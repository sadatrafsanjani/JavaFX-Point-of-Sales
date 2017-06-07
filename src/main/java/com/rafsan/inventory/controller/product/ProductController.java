package com.rafsan.inventory.controller.product;

import com.rafsan.inventory.entity.Product;
import com.rafsan.inventory.entity.Sale;
import com.rafsan.inventory.model.ProductModel;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.rafsan.inventory.interfaces.ProductInterface;
import static com.rafsan.inventory.interfaces.ProductInterface.PRODUCTLIST;
import com.rafsan.inventory.model.SalesModel;
import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ProductController implements Initializable, ProductInterface {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Long> idColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn,nameColumn, supplierColumn, descriptionColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn, quantityColumn;
    @FXML
    private TextField searchField;
    private ProductModel model;
    @FXML
    private Button editButton, deleteButton;

    @FXML
    private LineChart<String, Number> productChart;
    @FXML
    CategoryAxis pxAxis;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button menu;
    @FXML
    private VBox drawer;

    private SalesModel salesModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new ProductModel();
        salesModel = new SalesModel();
        drawerAction();
        loadData();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryColumn.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getCategory().getType()));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        supplierColumn.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getSupplier().getName()));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        productTable.setItems(PRODUCTLIST);

        filterData();

        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadProductSalesChart(newValue));

        editButton
                .disableProperty()
                .bind(Bindings.isEmpty(productTable.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(productTable.getSelectionModel().getSelectedItems()));
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
            sortedData.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sortedData);
        });
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

    private void loadData() {

        if (!PRODUCTLIST.isEmpty()) {
            PRODUCTLIST.clear();
        }
        PRODUCTLIST.addAll(model.getProducts());
    }

    private void loadProductSalesChart(Product p) {

        if (p != null) {

            String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
            ObservableList lists = FXCollections.observableArrayList(months);
            pxAxis.setCategories(lists);

            productChart.getData().clear();

            List<Sale> sales = salesModel.getSaleByProductId(p.getId());

            XYChart.Series series = new XYChart.Series();
            series.setName(p.getProductName());

            for (Sale s : sales) {

                String month = convertDate(s.getDate());
                series.getData().add(new XYChart.Data(month, s.getTotal()));
            }

            productChart.getData().addAll(series);
        }

    }

    private String convertDate(String date) {

        int d = Integer.parseInt(date.substring(5, 7));

        return new DateFormatSymbols().getMonths()[d - 1];
    }

    @FXML
    public void adminAction(ActionEvent event) throws Exception {
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
    public void reportAction(ActionEvent event) throws Exception {
        windows("/fxml/Report.fxml", "Report", event);
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
    public void addAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/product/Add.fxml"));
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
        stage.setTitle("New Product");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void editAction(ActionEvent event) throws Exception {

        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int selectedProductId = productTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/product/Edit.fxml")));
        EditController controller = new EditController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Product");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setProduct(selectedProduct, selectedProductId);
        productTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void deleteAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

            model.deleteProduct(selectedProduct);
            PRODUCTLIST.remove(selectedProduct);
        }

        productTable.getSelectionModel().clearSelection();
    }

}
