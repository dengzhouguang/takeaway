package com.dzg.takeaway;

import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.util.FileUtil;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        OkHttpClient httpClient= new OkHttpClient.Builder()
                .connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        Request.Builder requestBuilder=new Request.Builder();
//        https://www.ele.me/restapi/shopping/v2/menu?restaurant_id=143184454
        Request accept = requestBuilder.url("https://www.ele.me/restapi/shopping/v2/menu?restaurant_id=143184454")
                        .addHeader("x-shard","shopid=1").get().build();
        Response execute = httpClient.newCall(accept).execute();
        System.out.println(execute.body().string());
    }
}