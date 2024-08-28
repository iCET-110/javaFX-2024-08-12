package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class ViewFormController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public TableColumn colSalary;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public DatePicker dateDob;
    public ComboBox cmbTitle;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Customer> tblCustomers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr");
        titles.add("Miss");
        titles.add("Ms");
        cmbTitle.setItems(titles);
        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            setTextToValues(newValue);
        }));
        loadTable();
    }
    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }


    public void btnAddOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                txtId.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtAddress.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtCity.getText(),txtProvince.getText(),
                txtPostalCode.getText()
        );
//INSERT INTO Customer VALUES('C001','Mr','Danapala','1981-2-6',40000,'No.20 Walana','Panadura','Western',12500);
        try {
            String SQL ="INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,customer.getId());
            psTm.setObject(2,customer.getTitle());
            psTm.setObject(3,customer.getName());
            psTm.setDate(4, Date.valueOf(customer.getDob()));
            psTm.setDouble(5,customer.getSalary());
            psTm.setObject(6,customer.getAddress());
            psTm.setObject(7,customer.getCity());
            psTm.setObject(8,customer.getProvince());
            psTm.setObject(9,customer.getPostalCode());

          boolean b = psTm.executeUpdate()>0;
            System.out.println(b);

            if(b){
                new Alert(Alert.AlertType.INFORMATION,"Customer Added!").show();
                loadTable();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(customer);
    }


    private void loadTable(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        List<Customer> customerList = DBConnection.getInstance().getConnection();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        customerList.forEach(obj->{
            customerObservableList.add(obj);
        });


        tblCustomers.setItems(customerObservableList);
// -----------------------------------------------------------------------------------


        try {
            String SQL = "SELECT * FROM customer";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("CustTitle")+resultSet.getString("CustName"));
                Customer customer =new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
                System.out.println(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
