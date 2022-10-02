package WasteWatchers;

import java.util.Date;

public class Item {
    private String name;
    private Date expiry;
 
    public Item (String name, Date expiry) {
        this.name = name;
        this.expiry = expiry;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    
    
}
