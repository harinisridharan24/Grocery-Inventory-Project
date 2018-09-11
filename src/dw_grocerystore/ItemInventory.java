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
public class ItemInventory {
    
    private int SKU;
    private int initialItemCount;
    private int itemsLeft;
    private int totalCasesOrdered;
    private String itemType;
    
    public ItemInventory(int SKU, int initialItemCount, int itemsLeft, int totalCasesOrdered, String itemType)
    {
        this.SKU = SKU;
        this.initialItemCount = initialItemCount;
        this.itemsLeft = itemsLeft;
        this.totalCasesOrdered = totalCasesOrdered;
        this.itemType = itemType;
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
    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    /**
     * @return the initialItemCount
     */
    public int getInitialItemCount() {
        return initialItemCount;
    }

    /**
     * @param initialItemCount the initialItemCount to set
     */
    public void setInitialItemCount(int initialItemCount) {
        this.initialItemCount = initialItemCount;
    }

    /**
     * @return the itemsLeft
     */
    public int getItemLeft() {
        return itemsLeft;
    }

    /**
     * @param itemsLeft the itemsLeft to set
     */
    public void setItemLeft(int itemsLeft) {
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
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    
    public String toString(){
        return this.SKU + " " + this.initialItemCount + " " + this.totalCasesOrdered;
    }   
    
    
}
