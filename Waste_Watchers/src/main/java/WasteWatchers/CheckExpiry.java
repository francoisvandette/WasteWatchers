package WasteWatchers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CheckExpiry implements Runnable{

    AppDao app;
    @Override
    public void run() {
        try {
            Date curDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(curDate);
            c.add(Calendar.HOUR, 62);
            Date dateRange = c.getTime();
            app = new AppDao();
            ArrayList<User> users = app.getUsers();
            for (User user : users) {
                ArrayList<Item> items = app.getItems(user.getUsername());
                for (Item item : items) {
                    if (inRange(item.getExpiry(), dateRange, curDate)) {
                        EmailBuilder email = new EmailBuilder();
                        email.setEmail(user.getEmail());
                        email.setBody(user.getUsername() +", you have an item that is expiring soon! "
                                +item.getName() +" will expire on " +item.getExpiry());
                        
                        email.setSubject("Item expiring soon");
                        email.start();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    
    private boolean inRange (Date expiry, Date dateRange, Date curDate) {
        if (expiry.before(dateRange)) {
            if (expiry.after(curDate)) {
                return true;
            }
        }
        return false;

    }

}
