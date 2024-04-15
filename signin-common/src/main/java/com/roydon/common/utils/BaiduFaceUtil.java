package com.roydon.common.utils;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

/**
 * BaiduFaceUtil
 *
 * @AUTHOR: roydon
 * @DATE: 2024/4/15
 **/
public class BaiduFaceUtil {

    public static final String API_KEY = "9dWb56XwFVmjYuJfEZcwR1az";
    public static final String SECRET_KEY = "NZcmyrCopfMKf38ZtnWgAgTyiXOLfdz7";

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * 对比人脸
     *
     * @param face1
     * @param face2
     * @return
     * @throws IOException
     */
    public String compareFace(String face1, String face2) throws IOException {
        String face1Base64 = getFileContentAsBase64(face1);
        String face2Base64 = getFileContentAsBase64(face2);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[{\"image\":\"" + face1Base64 + "\",\"image_type\":\"BASE64\"},{\"image\":\"" + face2Base64 + "\",\"image_type\":\"BASE64\"}]");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/match?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().string();
    }

    /**
     * 上传人脸
     *
     * @param imageUrl
     * @param userId
     * @throws IOException
     */
    public static void uploadUserFace(String imageUrl, String userId) throws IOException {
        String fileContentAsBase64 = getFileContentAsBase64(imageUrl);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"group_id\":\"user_grop1\",\"image\":\"" + fileContentAsBase64 + "\",\"image_type\":\"BASE64\",\"user_id\":\"" + userId + "\"}");

        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add?access_token=" + getAccessToken())
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());
        response.body().string();
    }

    /**
     * 获取文件base64编码
     *
     * @param imageUrl 文件路径
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    public static String getFileContentAsBase64(String imageUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            if (body != null) {
                byte[] imageBytes = body.bytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);

//                System.out.println(base64Image);

                return base64Image;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    public static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }


}
