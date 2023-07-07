package model;

/** The InHouse class, which allows for the creation of InHouse parts. Extends the Part class. */
public class InHouse extends Part{
    /** Private class member that creates an int variable. */
    private int machineId;


    /**
     * InHouse constructor
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * sets the machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
