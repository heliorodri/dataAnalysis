package com.heliorodri.challenge.datreader.service;

import com.heliorodri.challenge.datreader.domain.Salesman;
import com.heliorodri.challenge.datreader.connector.SalesmanConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesmanService {
    @Autowired
    private SalesmanConnector salesmanConnector;

    public void saveSalesman(Salesman salesman){
        this.salesmanConnector.save(salesman);
    }

    public List<Salesman> getSalesmen(){
        return (List<Salesman>) this.salesmanConnector.findAll();
    }
}
