package service.custom.impl;

import model.Customer;
import service.custom.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public boolean addCustomer(Customer customer) {
        System.out.println("Service Layer : "+customer);
        return false;
    }
}
