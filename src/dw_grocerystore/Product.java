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
class Product{

    private String name;
    private double price;
    private int SKU;
    private String itemType;

    public Product(String name, int SKU, double price, String itemType){
        this.price = price;
        this.setName(name);
        this.SKU = SKU;
        this.itemType = itemType;
        //this.setName(item_type);
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
public void setPrice(double price) {
    this.price = price;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
    this.name = name;
}

/**
 * @return the name
 */
public String getName() {
    return name;
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

/*public void setitem_type(String item_type) {
    this.item_type = item_type;
}

/**
 * @return the name
 */
/*public String getItem_type() {
    return item_Type;
}
*/

public String toString(){
    return this.price + " " + this.name + " " + this.SKU;
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


}


