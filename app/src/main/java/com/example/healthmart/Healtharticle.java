package com.example.healthmart;

import static com.example.healthmart.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class Healtharticle extends AppCompatActivity {

    // Ensure both arrays have 5 elements
    private final String[][] health_details = {
            {"Walking Daily", "", "", "Click More Details"},
            {"Home care of COVID-19", "", "", "Click More Details"},
            {"Stop Smoking", "", "", "Click More Details"},
            {"Menstrual Cramps", "", "", "Click More Details"},
            {"Healthy Gut", "", "", "Click More Details"} // 5 entries
    };

    private final int[] images = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5 // Ensure this array has 5 elements
    };

    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    ListView lst ;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_healtharticle);
        lst = findViewById(id.listview1);
        btn = findViewById(id.buttonBack);

        list = new ArrayList<>();
        for (int i = 0; i < health_details.length; i++) {
            item = new HashMap<>();
            item.put("line1", health_details[i][0]);
            item.put("line2", health_details[i][1]);
            item.put("line3", health_details[i][2]);
            item.put("line4", health_details[i][3]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multiline,
                new String[]{"line1", "line2", "line3", "line4"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d});

        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(Healtharticle.this, Articles.class);
                it.putExtra("text1", health_details[i][0]);
                it.putExtra("text2", images[i]); // Make sure both arrays are aligned in size
                startActivity(it);
            }

        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Healtharticle.this,Home.class));
            }
        });
    }
}