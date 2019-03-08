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

        // 將 Application 與 AWT 連結
        Env2a.link(this);

        // 設定圖案是要抗鋸齒
        Env2a.configPaintAntiAliasFlag(true);

        // 初始化 Y2 Library 運行環境
        Y2Env.initial(true);

        // 預設 Y2 Font
        Y2Font.defaultFont("Droid", 40, Y2FontSizeType.PIXEL);
    }
}
