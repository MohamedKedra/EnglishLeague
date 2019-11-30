package com.mohamed.englishleague;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageGenerator {

    private static OkHttpClient client;

    public static void fetchImage(final Context context, String url, final ImageView target) {

        if (client == null) {
            client = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1024))
                    .build();
        }
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                target.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                target.setImageBitmap(bitmap);
            }
        });

    }

}
