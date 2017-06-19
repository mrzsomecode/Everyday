package everyday.zxz.com.everyday.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/4/13.
 */

public class MApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }
}
