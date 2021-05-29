package com.example.myfirstapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.Cache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myfirstapp.calculate.NetPresentValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private TextView tvNetPresentValue, tvDiscount, tvSaving, tvInflacionREM;
    private EditText montoTotal, cantidadCuotas, inflacionAnual;
    private Button test;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

        montoTotal = findViewById(R.id.montoTotal);
        cantidadCuotas = findViewById(R.id.cantidadCuotas);
        inflacionAnual = findViewById(R.id.inflacionAnual);
        test = findViewById(R.id.buttonTest);
        /*
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_LONG).show();
            }
        });
        */
        tvNetPresentValue = findViewById(R.id.tvNetPresentValue);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvSaving = findViewById(R.id.tvSaving);
        tvInflacionREM = findViewById(R.id.tvInflacionREM);

        final String DEBUG_TAG = "NetworkStatusExample";
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.estadisticasbcra.com/inflacion_esperada_oficial";


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(MainActivity.this, "ESTO ANDA" + response, Toast.LENGTH_SHORT).show();
                    tvInflacionREM.setText(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "BEARER eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3NTQ0MjksInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJqdWxpYW4ubGl6YXJyYWdhLjE1QGdtYWlsLmNvbSJ9.FgtSh24cf6yYA96OFITng0RxeXXshRQKKZsQPtXKZIVGSsFq1qziZjcybFU_BNVJ_P-960skdUTdUvJqRqu35Q");
                return headers;
            }
         };

       queue.add(req);
    }

    public void testButton(View view){

        Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    //public void getInflationFromREM (View v) {

       // Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
      //  RequestQueue queue = Volley.newRequestQueue(this);
       // String url ="https://www.google.com";


   // }

    public void getNetPresentValue(View v){
        double value, nOfPayments, inflationAnnual;
        String discountWithPercentage;

        value = Double.parseDouble(montoTotal.getText().toString());
        nOfPayments = Double.parseDouble(cantidadCuotas.getText().toString());

        inflationAnnual = Double.parseDouble((inflacionAnual.getText().toString()))/100;

        NetPresentValue netPresentValueObj = new NetPresentValue(value, nOfPayments, inflationAnnual);
        netPresentValueObj.calculate();

        tvNetPresentValue.setText(String.valueOf(netPresentValueObj.getNetPresentValue()));
        discountWithPercentage = String.valueOf(netPresentValueObj.getDiscount()) + '%';
        tvDiscount.setText(discountWithPercentage);
        tvSaving.setText(String.valueOf(netPresentValueObj.getSaving()));

    }
}

