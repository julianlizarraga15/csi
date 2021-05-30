package com.example.myfirstapp.helper;

import android.os.AsyncTask;
import com.google.gson.stream.JsonReader;

import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;


public class CallWebservice{

    private String bkupUrl = "https://api.estadisticasbcra.com/inflacion_esperada_oficial";
    private String bkupToken = "BEARER eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3NTQ0MjksInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJqdWxpYW4ubGl6YXJyYWdhLjE1QGdtYWlsLmNvbSJ9.FgtSh24cf6yYA96OFITng0RxeXXshRQKKZsQPtXKZIVGSsFq1qziZjcybFU_BNVJ_P-960skdUTdUvJqRqu35Q";
    private String url = "";
    private String token = "";

    private Gson gson = new Gson();
    private List<EstimatedInflationAtDate> estimatedInflationDateList = new LinkedList<EstimatedInflationAtDate>();
    private TextView tvInflacionREM;
    private String lastValue = "";

    public CallWebservice(String url, String token){
        this.url = url;
        this.token = token;
    }

    public void run(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create URL
                try {
                    URL githubEndpoint = new URL(url);

                    // Create connection
                    HttpsURLConnection myConnection =
                            (HttpsURLConnection) githubEndpoint.openConnection();

                    myConnection.setRequestProperty("Authorization", token);


                    if (myConnection.getResponseCode() == 200) {
                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");

                        JsonReader jsonReader = new JsonReader(responseBodyReader);
                        List o = gson.fromJson(jsonReader, List.class);

                        int cont = 0;
                        System.out.println("ContentLenght: "+myConnection.getContentLength());

                        for (Object o1 : o){
                            System.out.println(cont++);
                            //System.out.println(o1.toString());
                            estimatedInflationDateList.add(gson.fromJson(o1.toString(), EstimatedInflationAtDate.class));
                            //System.out.println(estimatedInflationAtDate.getDate());
                            //System.out.println(estimatedInflationAtDate.getValue());
                        }
                        lastValue = estimatedInflationDateList.get(estimatedInflationDateList.size()-1).getValue();
                    } else {
                        Toast.makeText(null,"Connection error.",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    public String getResult()
    {
        while (true)
        {
            if(!lastValue.equals("")){

                System.out.println("LastValue: "+lastValue);
                return lastValue;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
