package model;

/** The Outsourced class, which allows for the creation of outsourced parts. Extends the Part class. */
public class Outsourced extends Part{
    /** Creates a private string called companyName */
    private String companyName;

    /**
     * InHouse constructor
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Public method that returns the companyName string */
    public String getCompanyName() {
        return companyName;
    }

    /** Public method that sets the company name string that is passed into it to the objects companyName */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
