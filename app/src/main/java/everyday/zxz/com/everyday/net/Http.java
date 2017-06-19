package everyday.zxz.com.everyday.net;

import everyday.zxz.com.everyday.base.MApplication;
import everyday.zxz.com.everyday.utils.Utils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/13.
 */

public class Http {
    private static Retrofit retrofit = null;

    private Http() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (!Utils.isNetworkConnected(MApplication.getAppContext())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    Response.Builder newBuilder = response.newBuilder();
                    if (Utils.isNetworkConnected(MApplication.getAppContext())) {
                        int maxAge = 0;
                        // 有网络时 设置缓存超时时间0个小时
                        newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
                    } else {
                        // 无网络时，设置超时为1周
                        int maxStale = 60 * 60 * 24 * 7;
                        newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
                    }
                    return newBuilder.build();
                })
                .cache(new Cache(MApplication.getAppContext().getCacheDir(), 1024 * 1024 * 10))
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Http getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Http INSTANCE = new Http();
    }
}
