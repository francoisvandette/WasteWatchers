package WasteWatchers;

public class FoodItem {
    String name;
    String expDate;
    int id;
    
    public FoodItem (int id,String name, String expDate) {
        this.name = name;
        this.expDate = expDate;
        this.id = id;
               
    }

    public String getName() {
        return name;
    }

    public String getExpDate() {
        return expDate;
    }

    public int getId() {
        return id;
    }
    
}
