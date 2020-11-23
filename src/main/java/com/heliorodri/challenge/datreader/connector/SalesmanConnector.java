package com.heliorodri.challenge.datreader.connector;

import com.heliorodri.challenge.datreader.domain.Salesman;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalesmanConnector {
    private List<Salesman> salesmanList;

    public SalesmanConnector() {
        salesmanList = new ArrayList<>();
    }

    public void save(Salesman salesman){
        this.salesmanList.add(salesman);
    }

    public List<Salesman> findAll(){
        return this.salesmanList;
    }
}
