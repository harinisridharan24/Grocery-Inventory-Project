/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_grocerystore;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.text.*;
import dw_grocerystore.Product;
import dw_grocerystore.SaleRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author harini kumar
 */
public class DW_GroceryStore {

    static int loopCount = 0;
    final static int customersLow = 1140;
    final static int customersHigh = 1180;
    final static double cPriceMultiplier = 1.1;
    final static int cMaxItems = 100;
    final static int custWeekendIncrease = 50;
    static String currentDate = "2017-01-01";
    static int custCount = 0;
    static List<Product> product = new ArrayList<Product>();
    static List<Product> milkProducts = new ArrayList<Product>();
    static List<Product> cerealProducts = new ArrayList<Product>();
    static List<Product> babyFoodProducts = new ArrayList<Product>();
    static List<Product> diaperProducts = new ArrayList<Product>();
    static List<Product> breadProducts = new ArrayList<Product>();
    static List<Product> pbProducts = new ArrayList<Product>();
    static List<Product> jamProducts = new ArrayList<Product>();
    static List<Product> otheritems = new ArrayList<Product>();
    static List<SaleRecord> saleRecord = new ArrayList<SaleRecord>();
    static List<ItemInventory> itemInventory = new ArrayList<ItemInventory>();
    static int totalCustomers = 0;
    static Map<Integer, Integer> itemCountList = new HashMap<>();
    static Map<Integer, Integer> itemCountPerDay = new HashMap<>();
    static Map<String, Double> weeklySalesItem = new HashMap<>();
    static Map<String, Integer> itemLeft = new HashMap<>();
    static Map<String, Integer> casesOrdered = new HashMap<>();
    static int insertCount = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readPipeDelimitedFile();
        getDailyItemSale();
        createInventory();
        connect();
        for (int i = 0; i < 365; i++) {
            if (i > 0) {
                currentDate = getDate(currentDate);
            }
            if (isDeliveryDay(currentDate)) {
                updateSupply(true);
            }
            updateSupply(false);

            //dateIsWeekend(currentDate);
            if (dateIsWeekend(currentDate)) {
                custCount = randRange(customersLow + custWeekendIncrease, customersHigh + custWeekendIncrease);
            }
            custCount = randRange(customersLow, customersHigh);
            for (int j = 1; j <= custCount; j++) {
                totalCustomers = totalCustomers + custCount;
                double myItems = randRange(1, cMaxItems);
                int k = 0;
                if (randRange(1, 100) <= 70) {/* 70% probability of buying milk */
                    Product p = getRandomItemInventory(milkProducts);
                    writeRecord(currentDate, j, p.getSKU(), p.getPrice());
                    k++;
                    if (randRange(1, 100) <= 50) { 
                        /* 50% prob of buying cereal */
                        Product p1 = getRandomItemInventory(cerealProducts);
                        writeRecord(currentDate, j, p1.getSKU(), p1.getPrice());
                        k++;
                    } 
                } else /* didn’t buy milk. Only 5% prob of buying cereal */ if (randRange(1, 100) <= 5) {
                    Product p2 = getRandomItemInventory(cerealProducts);
                    writeRecord(currentDate, j, p2.getSKU(), p2.getPrice());
                    k++;
                }

                if (randRange(1, 100) <= 20) {
                    /* 20% probability of buying baby food */
                    Product p3 = getRandomItemInventory(babyFoodProducts);
                    writeRecord(currentDate, j, p3.getSKU(), p3.getPrice());
                    k++;
                    if (randRange(1, 100) <= 80) {
                        /* 80% prob of buying diaper */
                        Product p4 = getRandomItemInventory(diaperProducts);
                        writeRecord(currentDate, j, p4.getSKU(), p4.getPrice());
                        k++;
                    }
                } else /* didn’t buy baby food. Only 1% prob of buying diaper */ if (randRange(1, 100) <= 1) {
                    Product p5 = getRandomItemInventory(diaperProducts);
                    writeRecord(currentDate, j, p5.getSKU(), p5.getPrice());
                    k++;
                }
                if (randRange(1, 100) <= 50) {/* 50% probability of buying bread */
                    Product p6 = getRandomItemInventory(breadProducts);
                    writeRecord(currentDate, j, p6.getSKU(), p6.getPrice());
                    k++;
                }

                if (randRange(1, 100) <= 10) {/* 10% probability of buying peanut butter */
                    Product p7 = getRandomItemInventory(pbProducts);
                    writeRecord(currentDate, j, p7.getSKU(), p7.getPrice());
                    k++;

                    if (randRange(1, 100) <= 90) {
                        /* 90% prob of buying jelly */
                        Product p8 = getRandomItemInventory(jamProducts);
                        writeRecord(currentDate, j, p8.getSKU(), p8.getPrice());
                        k++;
                    }
                } else /* didn’t buy milk. Only 5% prob of buying cereal */ if (randRange(1, 100) <= 5) {
                    Product p9 = getRandomItemInventory(jamProducts);
                    writeRecord(currentDate, j, p9.getSKU(), p9.getPrice());
                    k++;
                }
                /* now buy (myItems – k) random products */
                for (int m = k; m < myItems; m++) {
                    Product p = getRandomItemInventory(product);
                    writeRecord(currentDate, j, p.getSKU(), p.getPrice());
                }
            }
        }
        writeSaleRecord();
        printSummary();
    }

    public static void createProductList(String[] newProduct) {
        String name = newProduct[1];
        String pricesplit = newProduct[5].replace("$", "");;
        double price = Double.parseDouble(pricesplit);
        int sku = Integer.parseInt(newProduct[4]);
        Product p = new Product(name, sku, price, newProduct[3]);
        product.add(p);
        switch (newProduct[3].toLowerCase()) {
            case "milk":
                milkProducts.add(p);
                break;
            case "cereal":
                cerealProducts.add(p);
                break;
            case "baby food":
                babyFoodProducts.add(p);
                break;
            case "diapers":
                diaperProducts.add(p);
                break;
            case "bread":
                breadProducts.add(p);
                break;
            case "peanut butter":
                pbProducts.add(p);
                break;
            case "jelly/jam":
                jamProducts.add(p);
                break;
            default:
                otheritems.add(p);
                break;
        }
    }

    public static void readPipeDelimitedFile() {
        try {
            //URL path = DW_GroceryStore.class.getResource("groceryfile.txt");
            File f = new File("groceryfile.txt");
            Scanner sc = new Scanner(f);
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] details = line.split("\\|");
                createProductList(details);
            }
        } catch (Exception e) {
            //System.out.println("exception"+ e.getCause());
            System.out.println(e);
        }
    }

    public static Product getRandomItemInventory(List<Product> p) {
        int itemLeft = -1;
        int productIndex = randRange(0, p.size());
        Product p15 = p.get(productIndex);
        ItemInventory iiy = getItem(p15.getSKU());
        itemLeft = iiy.getItemLeft();
        if(itemLeft> 0){
            return p15;
        }
       
        while (itemLeft <= 0) {
            p15 = getRandomItem(p);
            iiy = getItem(p15.getSKU());
            itemLeft = iiy.getItemLeft();
        }
        return p15;
    }

    public static Product getRandomItem(List<Product> p) {

        List<Product> plist = p;
        int productIndex = randRange(0, plist.size());
        Product p12 = plist.get(productIndex);
        return p12;
    }

    public static void writeRecord(String salesDate, int customerNo, int SKU, double price) {
        ItemInventory inventory = getItem(SKU);
        int itemLeftInInventory = inventory.getItemLeft() - 1;
        if(itemLeftInInventory<0){
            //System.out.println(SKU);
        }
        int totalCasesOrderedInInventory = inventory.getTotalCasesOrdered();
        SaleRecord s = new SaleRecord(salesDate, customerNo, SKU, (price * 1.10), itemLeftInInventory, totalCasesOrderedInInventory);
        inventory.setItemLeft(itemLeftInInventory);
        saleRecord.add(s);
        if (itemCountList.containsKey(SKU)) {
            itemCountList.put(SKU, itemCountList.get(SKU) + 1);
        } else {
            itemCountList.put(SKU, 1);
        }
    }

    public static ItemInventory getItem(int sku) {

        //System.out.println((loopCount = loopCount + itemInventory.size()));
        for (ItemInventory i : itemInventory) {
            if (i.getSKU() == sku) {
                return i;
            }
        }

        return null;
    }

    public static void writeSaleRecord() {
        /*try {
        FileWriter fw = new FileWriter("salesRecord.txt");
        fw.write("Customer No|Date Purchased|SKU|Price|Items Left|Total Cases Ordered"+ System.getProperty("line.separator"));
        for(SaleRecord sr: saleRecord){
            fw.write(sr.toString()+ System.getProperty("line.separator"));
        }
        fw.close();
        }
        catch(Exception ex){
            System.out.println(ex);
        }*/
        try {
            Connection c = null;
            Statement stmt = null;
            Class.forName("org.sqlite.JDBC");
            String sqlTotal = "";
            c = DriverManager.getConnection("jdbc:sqlite:rowan.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            System.out.println("Starting SQL Build...");
            
            for (SaleRecord sr : saleRecord) {
                String someDate = sr.getDatePurchased();
                //String newStr = someDate.substring(0,4) + "-" + someDate.substring(4,6) + "-" + someDate.substring(6); 
                sqlTotal += "INSERT INTO SALERECORD (DATE_PURCHASED, CUSTOMER_NO, SKU, PRICE, ITEMS_LEFT, CASES_ORDERED) "
                        + "VALUES (" +  "\"" +  someDate +"\"" + "," + sr.getCustomerNo()+ "," + sr.getSKU() + "," + sr.getPrice() + "," + sr.getItemsLeft() + "," + sr.getTotalCasesOrdered() + ");";
                //System.out.println("Inserted successfully" + ++insertCount);
                //stmt.executeUpdate(sqlTotal);
                 c.commit();
                if (insertCount++ % 1000 == 0) {
                    stmt.executeUpdate(sqlTotal);
                    sqlTotal = "";
                    System.out.println(insertCount);
                }
            }
            
            stmt.executeUpdate(sqlTotal);
            System.out.println("End SQL Build...");

            stmt.close();
           
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public static void printSummary() {

//        try {
//        FileWriter fw = new FileWriter("summary.txt");
//        fw.write("Summary:"+ System.getProperty("line.separator"));
//        fw.write("Total Customers: "+ totalCustomers+ System.getProperty("line.separator"));
//        fw.write("Total Items Sold: " + saleRecord.size()+ System.getProperty("line.separator"));        
//        fw.write("Total Sales: " + getTotalPrice()+ System.getProperty("line.separator"));
//        fw.write("Top 10 Items Sold:"+ System.getProperty("line.separator"));
//        Map<Integer, Integer> sortedMap = sortByValue(itemCountList);
//        int i = 0;
//        for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
//            i++;
//            if(i==10){
//                break;
//            }
//            fw.write("Item SKU : " + entry.getKey()+ " Count: " + entry.getValue()+ System.getProperty("line.separator"));
//        }
//        fw.close();
//        }
        try {
            int i = 0;
            Connection c = null;
            Statement stmt = null;
            String sql = "";

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:rowan.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            Map<Integer, Integer> sortedMap = sortByValue(itemCountList);
            for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
                i++;
                if (i == 10) {
                    break;
                }
                sql += "INSERT INTO TOPITEMS (SKU, ITEMS_SOLD) "
                        + "VALUES (" + entry.getKey() + "," + entry.getValue() + ");";
            }

            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static double getTotalPrice() {
        double totalPrice = 0.0;
        for (SaleRecord sr : saleRecord) {
            totalPrice += sr.getPrice();
        }
        return totalPrice;
    }

    public static String getDate(String currentDate) {

        String newDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            newDate = sdf.format(c.getTime());
        } catch (Exception e) {
            System.out.println(e);
        }
        return newDate;
    }

    public static boolean dateIsWeekend(String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static int randRange(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min)) + min;
        return randomNum;
    }

    private static Map<Integer, Integer> sortByValue(Map<Integer, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Integer>> list
                = new LinkedList<Map.Entry<Integer, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1,
                    Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static void writeDailyItemCount(int itemSKU) {

        if (itemCountPerDay.containsKey(itemSKU)) {
            itemCountPerDay.put(itemSKU, itemCountPerDay.get(itemSKU) + 1);
        } else {
            itemCountPerDay.put(itemSKU, 1);
        }
    }

    public static void printDailyItemCount() {

        for (Map.Entry<Integer, Integer> entry : itemCountPerDay.entrySet()) {
            System.out.println("" + entry.getKey() + "," + entry.getValue());
        }
    }

    public static void writeWeeklySales(int itemSKU, double item_price) {
        String item_name = "";
        if (itemSKU == 0) {
            item_name = Integer.toString(itemSKU);
        } else {
            item_name = Integer.toString(itemSKU);
        }
        if (weeklySalesItem.containsKey(item_name)) {
            weeklySalesItem.put(item_name, weeklySalesItem.get(item_name) + item_price);
        } else {
            weeklySalesItem.put(item_name, item_price);
        }
    }

    public static void printWeeklySales() {
        for (Map.Entry<String, Double> entry : weeklySalesItem.entrySet()) {
            System.out.println("" + entry.getKey() + "," + entry.getValue());
        }
    }

    public static void updateSupply(boolean isDeliveryDay) {
        int itemsCount = 0;
        int casesToBeOrdered, totalItemsCount;
        for (ItemInventory i : itemInventory) {
            if (!isDeliveryDay) {
                if (i.getItemType().equalsIgnoreCase("milk")) {
                    itemsCount = i.getInitialItemCount() - i.getItemLeft();
                    if (i.getItemLeft() >= 0) {
                        casesToBeOrdered = (int) Math.ceil(itemsCount / 12);
                        totalItemsCount = i.getItemLeft() + (casesToBeOrdered * 12);
                        i.setItemLeft(totalItemsCount);
                        i.setTotalCasesOrdered(i.getTotalCasesOrdered() + casesToBeOrdered);
                    }
                }
            } else {
                itemsCount = i.getInitialItemCount() - i.getItemLeft();
                if (i.getItemLeft() >= 0) {
                    casesToBeOrdered = (int) Math.ceil(itemsCount / 12);
                    totalItemsCount = i.getItemLeft() + (casesToBeOrdered * 12);

                    i.setItemLeft(totalItemsCount);
                    i.setTotalCasesOrdered(i.getTotalCasesOrdered() + casesToBeOrdered);
               }
            }
        }
    }

    public static boolean isDeliveryDay(String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDate));
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void getDailyItemSale() {
        String todayDate = "2017-01-01";
        for (int i = 0; i < 21; i++) {
            if (i > 0) {
                todayDate = getDate(todayDate);
            }
            if (dateIsWeekend(todayDate)) {
                custCount = randRange(customersLow + custWeekendIncrease, customersHigh + custWeekendIncrease);
            }
            custCount = randRange(customersLow, customersHigh);
            for (int j = 1; j <= custCount; j++) {
                totalCustomers = totalCustomers + custCount;
                double myItems = randRange(1, cMaxItems);
                int k = 0;
                if (randRange(1, 100) <= 70) {/* 70% probability of buying milk */
                    Product p = getRandomItem(milkProducts);
                    writeDailyItemCount(p.getSKU());
                    if (randRange(1, 100) <= 50) {
                        /* 50% prob of buying cereal */
                        Product p1 = getRandomItem(cerealProducts);
                        writeDailyItemCount(p1.getSKU());
                        k++;
                    }
                } else /* didn’t buy milk. Only 5% prob of buying cereal */ if (randRange(1, 100) <= 5) {
                      Product p2 = getRandomItem(cerealProducts);
                    writeDailyItemCount(p2.getSKU());
                    k++;
                }

                if (randRange(1, 100) <= 20) {
                    /* 20% probability of buying baby food */
                    Product p3 = getRandomItem(babyFoodProducts);
                    writeDailyItemCount(p3.getSKU());
                    k++;
                    if (randRange(1, 100) <= 80) {
                        /* 80% prob of buying diaper */
                        Product p4 = getRandomItem(diaperProducts);
                        writeDailyItemCount(p4.getSKU());
                        k++;
                    }
                } else /* didn’t buy baby food. Only 1% prob of buying diaper */ if (randRange(1, 100) <= 1) {
                    Product p5 = getRandomItem(diaperProducts);
                    writeDailyItemCount(p5.getSKU());
                    k++;
                }
                if (randRange(1, 100) <= 50) {/* 50% probability of buying bread */
                    Product p6 = getRandomItem(breadProducts);
                    writeDailyItemCount(p6.getSKU());
                    k++;
                }

                if (randRange(1, 100) <= 10) {/* 10% probability of buying peanut butter */
                    Product p7 = getRandomItem(pbProducts);
                    writeDailyItemCount(p7.getSKU());
                    k++;
                    if (randRange(1, 100) <= 90) {
                        /* 90% prob of buying jelly */
                        Product p8 = getRandomItem(jamProducts);
                        writeDailyItemCount(p8.getSKU());
                        k++;
                    }
                } else /* didn’t buy milk. Only 5% prob of buying cereal */ if (randRange(1, 100) <= 5) {
                    Product p9 = getRandomItem(jamProducts);
                    writeDailyItemCount(p9.getSKU());
                    k++;
                }
                /* now buy (myItems – k) random products */
                for (int m = k; m <= myItems; m++) {
                    Product p10 = getRandomItem(product);
                    writeDailyItemCount(p10.getSKU());
                }
            }
        }
    }

    public static void createInventory() {
        int itemCount = 0;
        int avgItemCount = 0;
        int totalCasesOrdered = 0;
        for (Product p11 : product) {
            if (p11.getItemType().toLowerCase().equalsIgnoreCase("milk")) {
                itemCount = (int) Math.ceil((itemCountPerDay.get(p11.getSKU())) / 21);
                avgItemCount = (int) Math.ceil(itemCount * 1.7);
                totalCasesOrdered = (int) Math.ceil(avgItemCount / 12);
                ItemInventory i = new ItemInventory(p11.getSKU(), totalCasesOrdered*12, totalCasesOrdered*12, totalCasesOrdered, p11.getItemType());
                itemInventory.add(i);
            } else {
                itemCount = (int) Math.ceil(itemCountPerDay.get(p11.getSKU()) / 21);
                avgItemCount = (int) Math.ceil(itemCount * 3.75);
                totalCasesOrdered = (int) Math.ceil(avgItemCount / 12);
                ItemInventory i1 = new ItemInventory(p11.getSKU(), totalCasesOrdered*12, totalCasesOrdered*12, totalCasesOrdered, p11.getItemType());
                itemInventory.add(i1);
            }
        }

    }

    public static void connect() {
        Connection conn;
        Statement stmt;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:rowan.db");
            System.out.println("Opened database successfully");

            String sql = "";            
            String sql1 = "";

            stmt = conn.createStatement();
            sql = "DROP TABLE IF EXISTS TOPITEMS;";
            stmt.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS SALERECORD;";
            stmt.executeUpdate(sql);
            
            sql = "CREATE TABLE SALERECORD "
                    + " (DATE_PURCHASED TEXT,"
                    + " CUSTOMER_NO , "
                    + " SKU INT NOT NULL, "
                    + " PRICE FLOAT NOT NULL, "
                    + " ITEMS_LEFT INT NOT NULL, "
                    + " CASES_ORDERED INT NOT NULL);";
            stmt.executeUpdate(sql);
            
            sql1 = "CREATE TABLE TOPITEMS "
                    + "(SKU INTEGER PRIMARY KEY NOT NULL,"
                    + " ITEMS_SOLD REAL NOT NULL);";

            stmt.executeUpdate(sql1);
            stmt.close();
            conn.close(); 
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
            System.out.println(e.getMessage());
        }
    }
}
     