package com.heliorodri.challenge.datreader.reader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith( SpringRunner.class )
@SpringBootTest
public class DatProcessorTest {
    private static final String file = System.getProperty("user.dir") + "/src/test/java/com/heliorodri/challenge/datreader/files/test.dat";
    private static final String fileEmpty = System.getProperty("user.dir") + "/src/test/java/com/heliorodri/challenge/datreader/files/testEmptyFile.dat";

    private static final String logFile = System.getProperty("user.dir") + "/src/main/java/com/heliorodri/challenge/datreader/database/log.json";
    private static final String salesFile = System.getProperty("user.dir") + "/src/main/java/com/heliorodri/challenge/datreader/database/sales.json";

    @Autowired
    private DatProcessor datProcessor;

    private void clearBases(){
        this.datProcessor.getSalesmanService().getSalesmen().clear();
        this.datProcessor.getCustomerService().getCustomers().clear();
        this.datProcessor.getSaleService().getSales().clear();

        this.datProcessor.writeFile("", salesFile);
        this.datProcessor.writeFile("", logFile);
    }

    @Test
    public void shouldLoadBasesFromFile() {
        this.datProcessor.loadBases(file);

        assertEquals(2, this.datProcessor.getCustomerService().getCustomers().size());
        assertEquals(2, this.datProcessor.getSalesmanService().getSalesmen().size());
        assertEquals(6, this.datProcessor.getSaleService().getSales().size());

        clearBases();

    }

    @Test
    public void testIfSalesWereCreated(){
        this.datProcessor.loadBases(file);

        assertEquals(1, this.datProcessor.getSaleService().getSales().get(0).getItemId());
        assertEquals(10, this.datProcessor.getSaleService().getSales().get(0).getItemQuantity());
        assertEquals("Diego", this.datProcessor.getSaleService().getSales().get(0).getSalesmenName());
        assertEquals(1, this.datProcessor.getSaleService().getSales().get(1).getSaleId());
        assertEquals(6, this.datProcessor.getSaleService().getSales().size());
        assertEquals("Renato", this.datProcessor.getSaleService().getSales().get(3).getSalesmenName());

        clearBases();
    }

    @Test
    public void testIfCustomersWereCreated(){
        this.datProcessor.loadBases(file);

        assertEquals("Jose da Silva", this.datProcessor.getCustomerService().getCustomers().get(0).getName());
        assertEquals("Rural", this.datProcessor.getCustomerService().getCustomers().get(0).getBusinessArea());
        assertEquals("2345675433444345", this.datProcessor.getCustomerService().getCustomers().get(1).getCnpj());

        clearBases();
    }

    @Test
    public void testIfSalesmenWereCreated(){
        this.datProcessor.loadBases(file);

        assertEquals("1234567891234", this.datProcessor.getSalesmanService().getSalesmen().get(0).getCpf());
        assertEquals("Diego", this.datProcessor.getSalesmanService().getSalesmen().get(0).getName());
        assertEquals(50000, this.datProcessor.getSalesmanService().getSalesmen().get(0).getSalary(), 0);

        assertEquals("3245678865434", this.datProcessor.getSalesmanService().getSalesmen().get(1).getCpf());
        assertEquals("Renato", this.datProcessor.getSalesmanService().getSalesmen().get(1).getName());
        assertEquals(40000.99, this.datProcessor.getSalesmanService().getSalesmen().get(1).getSalary(), 0);

        clearBases();
    }

    @Test
    public void shouldMontFile(){
        this.datProcessor.loadBases(file);

        assertEquals("Worst salesman ever: Renato\n" +
                        "Most expensive sale: id - 1\n" +
                        "Cheapest sale: id - 2\n" +
                        "Ammount of salesmen: 2\n" +
                        "Ammount of customers: 2\n",
                datProcessor.mountOutFile(file));

        clearBases();
    }

    @Test
    public void shouldMontEmptyFile(){
        this.datProcessor.loadBases(fileEmpty);

        assertEquals("empty sales list\n" +
                        "empty customers list\n" +
                        "empty salesmen list\n",
                this.datProcessor.mountOutFile(file));

        clearBases();
    }
}