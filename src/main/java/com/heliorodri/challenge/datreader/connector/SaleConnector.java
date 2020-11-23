package com.heliorodri.challenge.datreader.connector;

import com.heliorodri.challenge.datreader.domain.Sale;
import com.heliorodri.challenge.datreader.utils.WriteSales;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleConnector {
    private List<Sale> sales;

    public SaleConnector() {
        this.sales = new ArrayList<>();
    }

    public void save(Sale sale){
        this.sales.add(sale);
        WriteSales.save(sale);
    }

    public List<Sale> findAll(){
        return this.sales;
    }

}
