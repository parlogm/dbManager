package ro.utm.dbManager;

import org.apache.log4j.Logger;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.config.PropertyHolder;
import ro.utm.dbManager.dao.DAOInit;
import ro.utm.dbManager.frames.Desktop;

public class App {

    final static Logger log = Logger.getLogger(App.class);

    public static void main(String[] args)
    {
        log.info("Initializing the application...");

        PropertyHolder.init();
        I18N.init();
        DAOInit.init();
        macosConfig();

        log.info("Starting " + PropertyHolder.getProperty("app.finalName") + " Application...");

        // display the desktop frame :
        new Desktop();

        log.info("Application " + PropertyHolder.getProperty("app.finalName") + " started.");
    }

    /**
     * Special settting for macOS.
     */
    public static void macosConfig()
    {
        if (System.getProperty("os.name").contains("Mac"))
        {
            log.debug("Special settings for macOS users...");

            // take the menu bar off the jframe :
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
    }

}
