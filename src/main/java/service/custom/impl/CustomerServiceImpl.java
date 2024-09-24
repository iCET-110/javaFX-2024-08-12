package service.custom.impl;

import javafx.collections.ObservableList;
import model.Customer;
import service.custom.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        System.out.println("Service Layer : "+customer);
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAll() {
        return null;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public ObservableList<String> getCustomerIds() {
        return null;
    }
}
