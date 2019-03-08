ONBON Y2 Java Library for Android
=====================

[English](README_en.md)

本文件說明如何在 [Android Studio](https://developer.android.com/studio/index.html) 開發環境下，使用 [Y2 Library](https://github.com/api2doc/onbon.y2.api) 開發項目。

## 相依檔案

### AAR
* j2a-0.1-release.aar - 解決 J2SE java/javax 核心類別問題。

## Project 相關設定

#### build.gradle

* flatDir - 設定相依 libs 的儲存位置。

``` gradle
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs 'libs'
        }
    }
}
```

## App 相關設定

#### build.gradle

* project.ext.set - 設定輸出檔案名稱。

* dexOptions - 允許掛載 j2a-0.1.1-release.aar。

* dependencies - 定義 Y2 相關的 JAR & AAR 等檔案。檔案儲存在 __libs__ 資料夾下。


``` gradle
android {
    ...
    defaultConfig {
        ...
        project.ext.set("archivesBaseName", "y2.mobiledemo-" + defaultConfig.versionName);
    }
    dexOptions {
        preDexLibraries = false
        additionalParameters =["--core-library"]
    }

}

dependencies {
    ...
    compile files('libs/y2-0.2.0-SNAPSHOT.jar')
    compile files('libs/y2-http-0.2.0-SNAPSHOT.jar')
    compile files('libs/y2-http-ok-0.2.0-SNAPSHOT.jar')
    compile files('libs/y2-message-0.2.0-SNAPSHOT.jar')
    compile files('libs/commons-codec-1.9.jar')
    compile files('libs/gson-2.8.5.jar')
    compile files('libs/log4j-1.2.14.jar')
    compile files('libs/okhttp-3.12.1.jar')
    compile files('libs/okio-1.15.0.jar')
    compile files('libs/simple-xml-2.7.1.jar')
    compile files('libs/stax-1.2.0.jar')
    compile files('libs/stax-api-1.0.1.jar')
    compile files('libs/uia-utils-0.2.0.jar')
    compile files('libs/xpp3-1.1.3.3.jar')
    compile(name:'j2a-0.1.1-release', ext:'aar')
    ...
}

// or
dependencies {
    ...
    implementation(name:'j2a-0.1-release', ext:'aar')
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    ...
}

```

#### AndroidManifest.xml

* android:name - 設定啟動應用程式類別，用來初始化 Y2 Library 運行環境。

* android.permission.INTERNET - 設定允許網路操作。

``` XML
<application
    android:name="onbon.y2.mobiledemo.MainApplication"
    ...>
</application>
<uses-permission android:name="android.permission.INTERNET" />
```


#### MainApplication.java

AndroidManifest.xml 中 __android:name__ 指定的類別，應用程式的啟動點。

``` Java
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
```

## 開發注意事項

#### Screen (控制器) 操作
因為 Socket Client 不得執行於 UI 線程上，所以對 Screen 的操作都須在新的線程上，方能正確工作。
``` Java
new Thread(new Runnable() {
    public void run() {
        // 操作 Screen
    }
}).start();
```

#### UI 更新
在非 UI 線程上操作 Screen 後，欲將結果回報至 UI 時，利用 __runOnUiThread__ 方法。
``` Java
runOnUiThread(new Runnable() {
    public void run() {
        // 更新 UI
    }
});
```
