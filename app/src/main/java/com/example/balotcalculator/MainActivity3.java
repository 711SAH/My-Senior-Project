package com.example.balotcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity3 extends AppCompatActivity {

    EditText editText;
    Button add;
    ListView listView;
    Button done , update , clear;

    String ED;

    int j = 3;
    int s = 6;
    int counter = 1;



    public static ArrayList<String> playersList = new ArrayList<String>();
    public static ArrayList<String> playersListCopy = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    ArrayAdapter arrayAdapter2;


    public void toast() {
        Toast toast = Toast.makeText(MainActivity3.this, "يجب ادخال عدد زوجي من الاسامي ", Toast.LENGTH_LONG); // making toast
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // this code to prevent the soft keyboard from pushing my view up

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        add = (Button) findViewById(R.id.buttonadd);
        done = (Button) findViewById(R.id.buttondone);
        editText = (EditText) findViewById(R.id.editTextTextPersonName);
        listView = (ListView) findViewById(R.id.lv);
        update = (Button) findViewById(R.id.update);
        clear = (Button) findViewById(R.id.clear);


        arrayAdapter = new ArrayAdapter(this, R.layout.changing_viewlist_textcolor, playersList);
        arrayAdapter2 = new ArrayAdapter(this, R.layout.changing_viewlist_textcolor, playersListCopy);

        // set done button enabled by default
        done.setEnabled(false); // or to hide the button from the beginning


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("");

            alertDialog.setMessage("يجب ادخال عدد زوجي من الاسماء ويستحسن اضافة اربع اشخاص فما فوق ");

            TextView title = new TextView(this);
            title.append("ملاحظة");

            try {
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(23);
                alertDialog.setCustomTitle(title);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "حسناً",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {  // adding OnClickListener to  POSITIVE button
                            try {  // move to another activity
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
            alertDialog.show();
            // Start making button in center and customize color for it
            Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

            btnPositive.setTextColor(Color.parseColor("#00b0ff"));
            btnPositive.setBackgroundColor(Color.parseColor("#e0e0e0"));

            btnPositive.setTextSize(20);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);

         // end making button in center and customize color for it

            // to change message style
            TextView textView = alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(20);

            // to make message gravity center
            TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
            messageView.setTextColor(Color.WHITE);
            alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alartdilogroundedbackground));

            // if I come back after clicked back arrow to main activity this will clear old playerslist & the copy
            playersList.clear();
            playersListCopy.clear();


            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        add.setOnClickListener(new View.OnClickListener() { // listener for button
                @Override
                public void onClick(View v) {
                    try {

                        editText.setImeOptions(EditorInfo.IME_ACTION_DONE); // to hide keyboard when clicked done button in keyboard

                        //ED is a String
                        ED = editText.getText().toString().trim();
                        if (ED.length() == 0) {
                            Toast.makeText(MainActivity3.this, "ادخل حرف واحد على الاقل 😂", Toast.LENGTH_LONG).show();
                        }
                        if (ED.length() >= 1) {
                            String copy = editText.getText().toString().trim();
                            playersList.add(copy);
                            editText.setText("");
                        }

                        if(playersList.size() % 2 == 1){
                            done.setEnabled(false);
                            toast();
                        }
                        if(playersList.size() % 2 == 0){
                            done.setEnabled(true);
                        }
                        if(playersList.size() == 0){
                            done.setEnabled(false);
                        }

                        listView.setAdapter(arrayAdapter);

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity3.this, "ادخل حرف واحد على الاقل 😂", Toast.LENGTH_LONG).show();
                    }
                }
            });

        done.setOnClickListener(new View.OnClickListener() { // listener for button
            @Override
            public void onClick(View v) {
                try {
                     add.setEnabled(false);
                    Collections.shuffle(playersList);
                    arrayAdapter.notifyDataSetChanged();
                    playersListCopy.add("(فـريــق الـ ♥️♦️)");
                    for(int i = 0; i < playersList.size();i++){
                        playersListCopy.add(playersList.get(i) + "  " + "-" + counter++); // note int counter = 1;
                        arrayAdapter2.notifyDataSetChanged();
                        if(counter >  4){
                            counter = 1;
                        }
                        if (playersListCopy.size() == j){ // note int j = 3;
                            playersListCopy.add("(ضد فريق الـ ♠️♣️)");
                            arrayAdapter2.notifyDataSetChanged();
                            j = j + 6;
                        }
                        if (playersListCopy.size() == s){ // note int s = 6;
                            playersListCopy.add("(فـريــق الـ ♥️♦️)");
                            arrayAdapter2.notifyDataSetChanged();
                            s = s + 6;
                        }
                    }

                    playersListCopy.set(playersListCopy.size() -1,""); // to solve problem
                    arrayAdapter2.notifyDataSetChanged();

                    done.setEnabled(false);
                    update.setVisibility(View.INVISIBLE);
                    editText.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                listView.setAdapter(arrayAdapter2);
            }
        });



            update.setOnClickListener(new View.OnClickListener() { // listener for button
                @Override
                public void onClick(View v) {
                    try {
                        playersList.remove(playersList.get(playersList.size() - 1));
                        arrayAdapter.notifyDataSetChanged();
                        if(playersList.size() % 2 == 1) {
                            done.setEnabled(false);
                        }
                        if(playersList.size() % 2 == 0) {
                            done.setEnabled(true);
                        }
                        if(playersList.size() == 0){
                            done.setEnabled(false);
                        }
                        arrayAdapter.notifyDataSetChanged();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            clear.setOnClickListener(new View.OnClickListener() { // listener for button
                @Override
                 public void onClick(View v) {
                    try {
                        playersList.clear();
                        playersListCopy.clear();
                        add.setEnabled(true);
                        update.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.VISIBLE);
                        j = 3;
                        s = 6;
                        counter = 1;
                        arrayAdapter.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    listView.setAdapter(arrayAdapter);

                }
        });
    }
}