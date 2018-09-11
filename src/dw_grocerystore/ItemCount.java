/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_grocerystore;

import static dw_grocerystore.DW_GroceryStore.custCount;
import static dw_grocerystore.DW_GroceryStore.dateIsWeekend;
import static dw_grocerystore.DW_GroceryStore.getDate;
import static dw_grocerystore.DW_GroceryStore.itemCountPerDay;
import static dw_grocerystore.DW_GroceryStore.randRange;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author harini kumar
 */
public class ItemCount {
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

public static void main(String args[])
{
    getDailyItemSale();
    printDailyItemCount();
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
public static void writeDailyItemCount(int itemSKU) {

        if (itemCountPerDay.containsKey(itemSKU)) {
            itemCountPerDay.put(itemSKU, itemCountPerDay.get(itemSKU) + 1);
        } else {
            itemCountPerDay.put(itemSKU, 1);
        }
    }
    public static Product getRandomItem(List<Product> p) {

        List<Product> plist = p;
        int productIndex = randRange(0, plist.size());
        Product p12 = plist.get(productIndex);
        return p12;
    }
    public static void printDailyItemCount() {

        for (Map.Entry<Integer, Integer> entry : itemCountPerDay.entrySet()) {
            System.out.println("" + entry.getKey() + "," + entry.getValue());
        }
    }

}