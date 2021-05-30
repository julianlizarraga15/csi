package com.example.myfirstapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.myfirstapp.calculate.NetPresentValue;
import com.example.myfirstapp.helper.CallWebservice;
import com.example.myfirstapp.helper.EstimatedInflationAtDate;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private TextView tvNetPresentValue, tvDiscount, tvSaving;
    private TextView tvInflacionREM;
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

        CallWebservice callWebeservice = new CallWebservice("https://api.estadisticasbcra.com/inflacion_esperada_oficial","BEARER eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDc3NTQ0MjksInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJqdWxpYW4ubGl6YXJyYWdhLjE1QGdtYWlsLmNvbSJ9.FgtSh24cf6yYA96OFITng0RxeXXshRQKKZsQPtXKZIVGSsFq1qziZjcybFU_BNVJ_P-960skdUTdUvJqRqu35Q");
        callWebeservice.run();
        String lastValue = callWebeservice.getResult();
        tvInflacionREM.setText(lastValue);


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

