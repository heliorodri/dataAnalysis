package com.heliorodri.challenge.datreader.service;

import com.heliorodri.challenge.datreader.domain.Customer;
import com.heliorodri.challenge.datreader.connector.CustomerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerConnector customerConnector;

    public void saveCustomer(Customer customer){
        this.customerConnector.save(customer);
    }

    public List<Customer> getCustomers(){
        return this.customerConnector.findAll();
    }
}
