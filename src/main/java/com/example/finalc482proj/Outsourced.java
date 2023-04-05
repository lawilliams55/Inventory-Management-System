package com.example.finalc482proj;

/**
 * @author lukea
 * This class stores out sourced data
 * FUTURE ENHANCEMENTt: update the getter so that users can't enter company names that do not exist
 * RUNTIME ERROR: forgot to 'extends Part' causing super to not work correctly. Fixed by declaring 'extends part'
 * */

public class Outsourced extends Part{

    private String companyName;

    /**
     * This method creates a new instance for an outsourced part
     * @param companyName accepts a name for a company
     * @param max defines maximum amount of this part
     * @param min defines minimum amount of this part
     * @param stock defines how many of this part are in inventory
     * @param price accepts price for outsourced part
     * @param name accepts name for outsourced part
     * @param id accepts ID for outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id,name,price,stock,min,max);
        this.companyName=companyName;
    }
    /**
     * This method sets a companies name
     * @param companyName accepts string to set as a company name
     * */
    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }
    /**
     * This method returns a company name
     * @return returns the name of the company
     * */
    public String getCompanyName(){
        return companyName;
    }
}
