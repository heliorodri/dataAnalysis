package com.heliorodri.challenge.datreader.connector;

import com.heliorodri.challenge.datreader.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class CustomerConnector {
    private List<Customer> customers;

    public CustomerConnector() {
        this.customers = new ArrayList<>();
    }

    public void save(Customer customer){
        this.customers.add(customer);
    }
    public List<Customer> findAll(){
        return this.customers;
    }

}
