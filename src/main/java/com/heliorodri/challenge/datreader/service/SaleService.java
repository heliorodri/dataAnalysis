package com.heliorodri.challenge.datreader.service;

import com.heliorodri.challenge.datreader.domain.Sale;
import com.heliorodri.challenge.datreader.connector.SaleConnector;
import com.heliorodri.challenge.datreader.utils.WriteSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleConnector saleConnector;

    public void saveSale(Sale sale){
        this.saleConnector.save(sale);
    }

    public List<Sale> getSales(){
        return this.saleConnector.findAll();
    }
}
