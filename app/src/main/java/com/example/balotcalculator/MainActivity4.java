package com.example.balotcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    DB_Sqllite db = new DB_Sqllite(MainActivity4.this);


    ListView show_record;
    Button btnRemove;
    ArrayAdapter RecordAdapter;

    ArrayList<String> record = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRemove = (Button) findViewById(R.id.btn_delete);
        show_record = (ListView) findViewById(R.id.ddbb);

        Cursor data = db.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(MainActivity4.this, "لا يوجد شيء هنا", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                record.add(data.getString(1));
                RecordAdapter = new ArrayAdapter(this, R.layout.change_db_arraylist_disgn, record);
                show_record.setAdapter(RecordAdapter);

            }
        }
        btnRemove.setOnClickListener(new View.OnClickListener() { // listener for button
            @Override
            public void onClick(View v) { // OnClickListener fo button >>> action for button
                try {
                    db.deleteData();
                    RecordAdapter.clear();
                    RecordAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}

