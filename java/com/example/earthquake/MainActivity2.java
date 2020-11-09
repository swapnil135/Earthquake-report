package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button searchButton =(Button) findViewById(R.id.search_button);
        final RadioGroup sortButton=findViewById(R.id.sortGroup);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectid=sortButton.getCheckedRadioButtonId();
                RadioButton selectedButton=findViewById(selectid);
                TextView numRows=findViewById(R.id.numRows);
                TextView mag=findViewById(R.id.minMag);
                MainActivity.minmag=Integer.parseInt(mag.getText().toString());
                MainActivity.orderby=selectedButton.getText().toString();
                MainActivity.limit=Integer.parseInt(numRows.getText().toString());
                Intent i=new Intent(MainActivity2.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
