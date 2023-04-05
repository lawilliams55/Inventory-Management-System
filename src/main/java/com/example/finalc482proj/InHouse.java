package com.example.finalc482proj;

/**
 * @author lukea
 * This class stores in house data
 * FUTURE ENHANCEMENT: update the getter so that users can't enter machine ids that do not exist
 * */
public class InHouse extends Part {
    //Setting variable for machine ID
    private int machineId;

    /**
     * Method for new instance of an in house object
     * @param id the part id
     * @param name name of a part
     * @param price the price of a part
     * @param stock amount of parts in inventory
     * @param min min amount for a part
     * @param max max amount for a part
     * @param machineId accepts integer as machine ID
     * Runtime Error: Tried to make the method an integer. Had to remove 'int' from method declaration.
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id,name,price,stock,min,max);
        this.machineId = machineId;
    }
    /**
     * This method sets the machine ID
     * @param machineId accepts an integer as the machine ID
     * Runtime Error: Tried making the method static at first. Fixed error by removing static declaration
     * */
    public void setMachineId(Integer machineId){
        this.machineId = machineId;
    }
    /**
     * This method gets the machine ID
     * @return returns the machineId
     * */
    public int getMachineId(){
        return machineId;
    }
}
