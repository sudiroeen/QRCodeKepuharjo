package myfirstapp.example.com.qrcodekepuharjo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// Coba JSON:
// {"Nama":"Kursi", "Lokasi":"Aula", "Tanggal":"02 Februari 2020", "Kode":"YO-145", "Asal":"Bantuan Kabupaten Sleman", "Harga":"Rp. 100.000"}
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.content.Intent;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentIntegrator;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.net.Uri;

import java.util.Random;
import java.util.RandomAccess;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_scan;
    private Button btn_scan_map;

    private TextView tv_nama_barang;
    private TextView tv_lokasi;
    private TextView tv_tanggal;
    private TextView tv_kode;
    private TextView tv_asal_barang;
    private TextView tv_harga;

    String URL;
    Uri URI;
    int nilai_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializeobject
        btn_scan =(Button)findViewById(R.id.btn_scan);
        btn_scan_map = (Button)findViewById(R.id.btn_scan_map);

        tv_nama_barang = (TextView)findViewById(R.id.tv_nama_barang);
        tv_lokasi = (TextView)findViewById(R.id.tv_lokasi);
        tv_tanggal = (TextView)findViewById(R.id.tv_tanggal);
        tv_kode = (TextView)findViewById(R.id.tv_kode);
        tv_asal_barang = (TextView)findViewById(R.id.tv_asal_barang);
        tv_harga = (TextView)findViewById(R.id.tv_harga);

        btn_scan.setOnClickListener(this);
        btn_scan_map.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                switch(nilai_id){
                    case R.id.btn_scan:
                        try{
                            JSONObject json_object = new JSONObject(result.getContents());

                            tv_nama_barang.setText(json_object.getString("Nama"));
                            tv_lokasi.setText(json_object.getString("Lokasi"));
                            tv_tanggal.setText(json_object.getString("Tanggal"));
                            tv_kode.setText(json_object.getString("Kode"));
                            tv_asal_barang.setText(json_object.getString("Asal"));
                            tv_harga.setText(json_object.getString("Harga"));
                        }catch(JSONException e){
                            e.printStackTrace();
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                            Intent show2random = new Intent(this, RandomDataActivity.class);
                            String data2send = result.getContents();
                            show2random.putExtra("data_random", data2send);
                            startActivity(show2random);
                        }
                        break;
                    case R.id.btn_scan_map:
                        URL = result.getContents();
                        Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                        URI = Uri.parse(URL);
                        Intent intent_search = new Intent(Intent.ACTION_VIEW, URI);
                        if(intent_search.resolveActivity(getPackageManager()) != null){
                            startActivity(intent_search);
                        }else{
                            Intent show2random = new Intent(this, RandomDataActivity.class);
                            String data2send = result.getContents();
                            show2random.putExtra("data_random", data2send);
                            startActivity(show2random);
                        }
                        break;
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v){
        nilai_id = v.getId();
        switch(v.getId()){
            case R.id.btn_scan:
                // Inisialisasi IntentIntegrator(scanQR)
                IntentIntegrator scan_invent_int = new IntentIntegrator(this);
                scan_invent_int.initiateScan();
                break;
            case R.id.btn_scan_map:
                // Inisialisasi IntentIntegrator(scanQR)
                IntentIntegrator scan_map_integrator = new IntentIntegrator(this);
                scan_map_integrator.initiateScan();
                break;
        }
    }
}
