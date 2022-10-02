package WasteWatchers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BackgroundListener implements ServletContextListener {
    
    private ScheduledExecutorService scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newScheduledThreadPool(1);
        CheckExpiry check = new CheckExpiry();
        scheduler.scheduleAtFixedRate(check, 0, 1, TimeUnit.DAYS);
    }
    
    

}
