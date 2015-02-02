import play.Application;
import play.GlobalSettings;

import models.Shop;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        super.onStart(app);
        if (Shop.INSTANCE.list().isEmpty()) {
            Shop.INSTANCE.create("Play Framework Essentials", 42.0);
        }
    }
}