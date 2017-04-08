package tiwaco.thefirstapp;

/**
 * Created by TUTRAN on 15/03/2017.
 */

public class Customer {
    String Name;
    String Address;

    Customer(String name, String addr){
        this.Name =  name;
        this.Address = addr;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setName(String name) {
        Name = name;
    }


}
