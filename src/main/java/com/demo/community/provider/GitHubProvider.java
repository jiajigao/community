package com.demo.community.provider;

import com.alibaba.fastjson.JSON;
import com.demo.community.dto.AccessTockenDTO;
import com.demo.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getAccessTockenDTO(AccessTockenDTO accessTockenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTockenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String[] split = string.split("&");
                String tokenString = split[0];
                String token = tokenString.split("=")[1];
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
    public GitHubUser getUser(String accessTocken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessTocken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return gitHubUser;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
