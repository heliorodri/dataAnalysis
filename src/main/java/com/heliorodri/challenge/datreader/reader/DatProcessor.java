package com.heliorodri.challenge.datreader.reader;

import com.google.gson.Gson;
import com.heliorodri.challenge.datreader.domain.Customer;
import com.heliorodri.challenge.datreader.domain.Sale;
import com.heliorodri.challenge.datreader.domain.Salesman;
import com.heliorodri.challenge.datreader.service.CustomerService;
import com.heliorodri.challenge.datreader.service.SaleService;
import com.heliorodri.challenge.datreader.service.SalesmanService;
import com.heliorodri.challenge.datreader.utils.Infos;
import com.heliorodri.challenge.datreader.utils.WriteLog;
import com.heliorodri.challenge.datreader.utils.WriteSales;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

public class DatProcessor {
    private static final char separator = 'ç';

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SaleService saleService;
    @Autowired
    private SalesmanService salesmanService;


    public CustomerService getCustomerService() {
        return customerService;
    }

    public SaleService getSaleService() {
        return saleService;
    }

    public SalesmanService getSalesmanService() {
        return salesmanService;
    }

    public void analize(String file) {
        this.loadBases(Infos.INPUT_DIR.getValue() + file);
        this.writeFile(this.mountOutFile(file),
                Infos.OUTPUT_DIR.getValue() + file.replace(".dat", ".done.dat"));
    }

    public String mountOutFile(String file) {
        StringBuilder data = new StringBuilder();

        List<Sale> salesLog = salesLog();

        if (salesLog.size() > 0) {

            Map<String, Double> map = salesLog.stream()
                    .collect(groupingBy(Sale::getSalesmenName, summingDouble(sale -> sale.getItemPrice() * sale.getItemQuantity())));


            map = (Map<String, Double>) map.entrySet()
                    .stream()
                    .sorted(comparingByValue())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            data.append("Worst salesman ever: ").append(map.entrySet().stream().findFirst().get().getKey()).append("\n");
        }

        if (this.saleService.getSales().size() > 0) {
            Sale expensiveSale = Collections
                    .max(this.saleService.getSales(),
                            Comparator.comparing(
                                    s -> this.saleService.getSales().stream()
                                            .filter(oSale -> oSale.getSaleId() == s.getSaleId())
                                            .mapToDouble(Sale::getItemPrice)
                                            .sum()
                            )
                    );

            data.append("Most expensive sale: ").append("id - ").append(expensiveSale.getSaleId()).append("\n");

            Sale cheapSale = Collections
                    .min(this.saleService.getSales(),
                            Comparator.comparing(
                                    s -> this.saleService.getSales().stream()
                                            .filter(oSale -> oSale.getSaleId() == s.getSaleId())
                                            .mapToDouble(Sale::getItemPrice)
                                            .sum()
                            )
                    );
            data.append("Cheapest sale: ").append("id - ").append(cheapSale.getSaleId()).append("\n");

        } else {
            data.append("empty sales list").append("\n");
        }

        if (this.customerService.getCustomers().size() == 0) {
            data.append("empty customers list").append("\n");
        } else {
            data.append("Ammount of salesmen: ").append(this.salesmanService.getSalesmen().size()).append("\n");
        }

        if (this.salesmanService.getSalesmen().size() == 0) {
            data.append("empty salesmen list").append("\n");
        } else {
            data.append("Ammount of customers: ").append(this.customerService.getCustomers().size()).append("\n");
        }

        return data.toString();

    }

    public void loadBases(String file) {
        List<List<String>> records = null;
        records = readFile(file);

        try {
            for (List<String> record : records) {
                if (record.get(0).equals(Infos.SALESMEN_CODE.getValue())) {
                    salesmanService.saveSalesman(new Salesman(
                            record.get(2),
                            record.get(1),
                            Double.parseDouble(record.get(3))));

                } else if (record.get(0).equals(Infos.CUSTOMER_CODE.getValue())) {
                    customerService.saveCustomer(new Customer(
                            record.get(2),
                            record.get(1),
                            record.get(3)));

                } else if (record.get(0).equals(Infos.SALE_CODE.getValue())) {
                    String[] sale = record.get(2)
                            .replace("[", "")
                            .replace("]", "")
                            .split(",");

                    ArrayList<String> sales = new ArrayList<>(Arrays.asList(sale));

                    for (String oSale : sales) {
                        String[] aSale = oSale.replace(" ", "").split("-");
                        saleService.saveSale(new Sale(
                                Integer.parseInt(record.get(1)),
                                Integer.parseInt(aSale[0]),
                                Integer.parseInt(aSale[1]),
                                Double.parseDouble(aSale[2]),
                                record.get(3)));
                    }
                }
            }
        } catch (RuntimeException e) {
            WriteLog.log(e, this.getClass(), "loadBases");
        }
    }

    private List<List<String>> readFile(String file) {
        List<List<String>> records = new ArrayList<List<String>>();

        try (CSVReader csvReader = createCSVReader(file)) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            WriteLog.log(e, this.getClass(), "readFile");
        }

        return records;
    }

    private CSVReader createCSVReader(String file) throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(separator).build();

        BufferedReader buffer = Files.newBufferedReader(Paths.get(file), Charset.defaultCharset());

        return new CSVReaderBuilder(buffer).withCSVParser(parser).build();
    }

    public void writeFile(String data, String fileName) {
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e) {
            WriteLog.log(e, this.getClass(), "writeFile");
        }
    }

    public List<Sale> salesLog() {
        Gson g = new Gson();
        ArrayList<Sale> salesLog = new ArrayList<>();

        JSONArray jsonArray = WriteSales.getSalesLog();

        for (int i = 0; i < jsonArray.size(); i++) {
            salesLog.add(g.fromJson(jsonArray.get(i).toString(), Sale.class));
        }

        return salesLog;
    }
}
