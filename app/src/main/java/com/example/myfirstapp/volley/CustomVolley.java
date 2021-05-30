package com.example.myfirstapp.volley;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomVolley extends JsonObjectRequest {
    public CustomVolley(int method, String url, @Nullable @org.jetbrains.annotations.Nullable JSONObject jsonRequest, Response.Listener listener, @Nullable @org.jetbrains.annotations.Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "BEARER eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3NTQ0MjksInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJqdWxpYW4ubGl6YXJyYWdhLjE1QGdtYWlsLmNvbSJ9.FgtSh24cf6yYA96OFITng0RxeXXshRQKKZsQPtXKZIVGSsFq1qziZjcybFU_BNVJ_P-960skdUTdUvJqRqu35Q");
        return headers;
    }


}
