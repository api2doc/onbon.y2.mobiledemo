package onbon.y2.mobiledemo;

import android.app.Application;

import onbon.y2.Y2Env;
import onbon.y2.common.Y2Font;
import onbon.y2.common.Y2FontSizeType;
import uia.j2a.Env2a;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Env2a.link(this);
        Env2a.configPaintAntiAliasFlag(true);

        Y2Env.initial(true);
        Y2Font.DEFAULT_TYPE = Y2FontSizeType.POINT;
    }
}
