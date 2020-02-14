package myfirstapp.example.com.qrcodekepuharjo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RandomDataActivity extends AppCompatActivity implements View.OnClickListener{

    TextView  tv_random;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_data);

        tv_random = (TextView)findViewById(R.id.tv_random);
        btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(this);

        Intent takeIntent = getIntent();
        String fullData = takeIntent.getStringExtra("data_random");
        tv_random.setText(fullData);
    }

    @Override
    public void onClick(View v_random){
        Intent intent2main = new Intent(this, MainActivity.class);
        startActivity(intent2main);
    }
}
