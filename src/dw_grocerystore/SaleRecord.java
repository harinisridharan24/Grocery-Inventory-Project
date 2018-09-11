/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dw_grocerystore;

/**
 *
 * @author harini kumar
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author harini kumar
 */
class SaleRecord{

    private String datePurchased;
    private int customerNo;
    private int SKU;
    private double price;
    private int itemsLeft;
    private int totalCasesOrdered;

    public SaleRecord(String datePurchased, int customerNo, int SKU, double price, int itemsLeft, int totalCasesOrdered){
        this.customerNo = customerNo;
        this.setName(datePurchased);
        this.SKU = SKU;
        this.price = price;
        this.itemsLeft = itemsLeft;
        this.totalCasesOrdered = totalCasesOrdered;
    }

    /**
     * @return the customerNo
     */
public int getCustomerNo() {
    return customerNo;
}

/**
 * @param customerNo the customerNo to set
 */
public void setCustomerNo(int customerNo) {
    this.customerNo = customerNo;
}

/**
 * @param datePurchased the datePurchased to set
 */
public void setName(String datePurchased) {
    this.setDatePurchased(datePurchased);
}

/**
 * @return the datePurchased
 */
public String getName() {
    return getDatePurchased();
}

/**
 * @return the SKU
 */
public int getSKU() {
    return SKU;
}

/**
 * @param SKU the SKU to set
 */
public void setSKU(int sku) {
    this.SKU = sku;
}

/**
 * @return the price
 */
public double getPrice() {
    return price;
}

/**
 * @param price the price to set
 */
public void setSKU(double price) {
    this.price = price;
}

public String toString(){
    return this.customerNo + "|" + this.getDatePurchased() + "|" + this.SKU + "|"+this.price+ "|"+this.itemsLeft+ "|"+this.totalCasesOrdered;
}

    /**
     * @return the itemsLeft
     */
    public int getItemsLeft() {
        return itemsLeft;
    }

    /**
     * @param itemsLeft the itemsLeft to set
     */
    public void setItemsLeft(int itemsLeft) {
        this.itemsLeft = itemsLeft;
    }

    /**
     * @return the totalCasesOrdered
     */
    public int getTotalCasesOrdered() {
        return totalCasesOrdered;
    }

    /**
     * @param totalCasesOrdered the totalCasesOrdered to set
     */
    public void setTotalCasesOrdered(int totalCasesOrdered) {
        this.totalCasesOrdered = totalCasesOrdered;
    }

    /**
     * @return the datePurchased
     */
    public String getDatePurchased() {
        return datePurchased;
    }

    /**
     * @param datePurchased the datePurchased to set
     */
    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }


}

