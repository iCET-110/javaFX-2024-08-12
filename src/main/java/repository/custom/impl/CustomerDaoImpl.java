package repository.custom.impl;

import javafx.collections.ObservableList;
import model.Customer;
import repository.custom.CustomerDao;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer customer) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ObservableList<Customer> getAll() {
        return null;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public Customer search(String id) {
        return null;
    }
}
