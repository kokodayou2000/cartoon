package org.example.utlis;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


import java.io.IOException;

public class ImageToPdfConverter {
    /**
     * 从 url 获取图片，并转换成 bytes
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public static byte[] getImageBytesFromUrl(String imageUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.bytes();
            }
        }
        return null;
    }
}