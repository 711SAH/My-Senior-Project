package com.example.balotcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    // create instance for SqlLite class
    DB_Sqllite db = new DB_Sqllite(this); // TODO: instance for database

    // global things
    // creating an object for what I want to handle --  >>>
    EditText us , hem;  // this for edit text for name

    TextToSpeech textToSpeech;

    EditText edit , edit2;
    Button btnClickHere;
    TextView rotate;
    ListView right , left;

    AdView adView1; // for google ads

    public static ArrayList<String> playerList = new ArrayList<String>();
    public static ArrayList<String> playerList2 = new ArrayList<String>();


    int total = 0;
    int total2 = 0;

    ArrayAdapter arrayAdapter;
    ArrayAdapter arrayAdapter2;


    // for adding edit and edit2 then calculate them by total and total2
    String E;
    String E2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() { // TODO: this for adding admob ads.
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                adView1 = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                adView1.loadAd(adRequest);

            }
        });

        // TODO: this code to prevent the soft keyboard from pushing my view up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("الـــنـــشــــرة"); // title for my bar
        toolbar.setTitleTextColor(0xFFFFFFFF);   // set color for the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make toolbar show navigation button (i.e back button with arrow icon)

        // for adding background image...
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cons);
        linearLayout.setBackground(getResources().getDrawable(R.drawable.goodwork));
        ////////////////////////////////////////////////////////////////////////////////////

        us = (EditText) findViewById(R.id.us); // creating variable from object
        hem = (EditText) findViewById(R.id.hem);
        edit = (EditText) findViewById(R.id.editTextleft);
        edit2 = (EditText) findViewById(R.id.editTextrhgit);
        rotate = (TextView) findViewById(R.id.orintation);
        btnClickHere = (Button) findViewById(R.id.button);
        right = (ListView) findViewById(R.id.rhgit);
        left = (ListView) findViewById(R.id.left);



        // using Custom Adapter class                                                                   // I can do it by using CustomAdapter class -- replace this code with -- CustomAdapter arrayAdapter = new CustomAdapter (this,playerList); --
        arrayAdapter = new ArrayAdapter(this, R.layout.changing_viewlist_textcolor, playerList);  // here I am using custom arrayAdapter i have made it , because I need to do the logic of OOP -- changing_viewlist_textcolor -- I have use the layout for changing text modal but in CustomAdapter class
        arrayAdapter2 = new ArrayAdapter(this, R.layout.changing_viewlist_textcolor, playerList2); // here I am using default arrayAdapter , because I didn't need to create other class


        btnClickHere.setOnClickListener(new View.OnClickListener() { // listener for button
            @Override
            public void onClick(View v) { // OnClickListener fo button >>> action for button

                if (edit.getText().toString().startsWith("0")  // to make remove leading zeros from Edit Text
                        && edit.getText().length() > 1) {
                    Toast.makeText(MainActivity.this,
                            "لايمكن بدأ القيمة بصفر", Toast.LENGTH_LONG).show();
                    edit.setText("");
                    edit.setSelection(edit.getText().toString().length());
                }

                if (edit2.getText().toString().startsWith("0")
                        && edit2.getText().length() > 1) {
                    Toast.makeText(MainActivity.this,
                            "لايمكن بدأ القيمة بصفر", Toast.LENGTH_LONG).show();
                    edit2.setText("");
                    edit2.setSelection(edit2.getText().toString().length());
                }
//****************************************************************************************************//

                edit.setImeOptions(EditorInfo.IME_ACTION_DONE); // to hide keyboard when clicked Done button in keyboard
                edit2.setImeOptions(EditorInfo.IME_ACTION_DONE);

                rotate.setRotation(rotate.getRotation() - 90); // rotate the arrow each time clicking button

                E = edit.getText().toString().trim();

                if (E.length() == 0) {
                    int a = 0;
                    playerList.add(String.valueOf(a)); // I can do it with different way by using -- playerList.add(String.valueOf(0)); --
                }

                if (E.length() != 0) {
                    total = total + Integer.parseInt(E);
                    playerList.add(E);
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // to hide keyboard after adding item
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    edit.setText("");
                }

                if (playerList.size() > 1) {
                    String L = "------";
                    playerList.add(L);
                    playerList.add(String.valueOf(total));
                    edit.setText(""); // adds text to arraylist and make edittext blank again
                }
                // add condition if player has more than 152 openDialog will show >>> I have do it with detecting the last position and do the if statement
                // I should adding (try catch)
                try {
                    if (Integer.parseInt(playerList.get(playerList.size() - 1)) >= 152 &&
                            Integer.parseInt(playerList.get(playerList.size() - 1)) > Integer.parseInt(playerList2.get(playerList2.size() - 1))) {
                        // TODO: call dialog
                        dialog();

                        // TODO: call Database to insert the victor game result
                        String get = playerList.get(playerList.size() -1);
                        Boolean result =  db.insertData(  "(إنتصار) لفريق " + hem.getText() + " " +"بنتيجة " + get +"\n");
                        if(result == true){
                            Toast.makeText(MainActivity.this, "تم حفظ النتيجة", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "لم يتم حفظ النتيجية", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception error1) {
                    error1.printStackTrace();
                }
                left.setAdapter(arrayAdapter);

                ////////////////////////////////////////////////////////////////////////////////////////

                E2 = edit2.getText().toString().trim();

                if (E2.length() == 0) {
                    int b = 0;
                    playerList2.add(String.valueOf(b)); // I can do it with different way by using -- playerList2.add(String.valueOf(0)); --
                }

                if (E2.length() != 0) {
                    total2 = total2 + Integer.parseInt(E2);
                    playerList2.add(E2);
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // to hide keyboard after adding item
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    edit2.setText("");
                }

                if (playerList2.size() > 1) {
                    String L2 = "------";
                    playerList2.add(L2);
                    playerList2.add(String.valueOf(total2));
                    edit2.setText(""); // adds text to arraylist and make edittext blank again
                }

                // add condition if player has more than 152 openDialog will show >>> I have do it with detecting the last position and do the if statement --- I should adding (try catch)
                try {
                    if (Integer.parseInt(playerList2.get(playerList2.size() - 1)) >= 152 &&
                            Integer.parseInt(playerList2.get(playerList2.size() - 1)) > Integer.parseInt(playerList.get(playerList.size() - 1))) {
                        // TODO: call dialog
                        dialog();

                        // TODO: call Database to insert the victor game result
                        String get = playerList2.get(playerList2.size() -1);
                       Boolean result =  db.insertData(  "(إنتصار) لفريق " + us.getText() + " " +"بنتيجة " + get +"\n");
                       if(result == true){
                           Toast.makeText(MainActivity.this, "تم حفظ النتيجة", Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(MainActivity.this, "لم يتم حفظ النتيجية", Toast.LENGTH_SHORT).show();
                       }

                    }
                } catch (Exception error1) {
                    error1.printStackTrace();
                }
                right.setAdapter(arrayAdapter2);

                left.setSelection(left.getAdapter().getCount()-1); // to set the focus on the last element in a list view 
                right.setSelection(right.getAdapter().getCount()-1);
            }
        });
    }


    // dialog method
    public void dialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("");
        alertDialog.setMessage("هل تريد إعادة اللعبة ؟");
        TextView title = new TextView(this);
        try {  // start comparisons in arraylist who is the bigger to append it to title
            if (Integer.parseInt(playerList.get(playerList.size() - 1)) > Integer.parseInt(playerList2.get(playerList2.size() - 1))) {
                title.append(hem.getText() + " - لقد فاز عليك بـ : " + playerList.get(playerList.size() - 1));
            }
          else if (Integer.parseInt(playerList2.get(playerList2.size() - 1)) > Integer.parseInt(playerList.get(playerList.size() - 1)) ){
              title.append(us.getText() + " - لقد فاز عليك بـ : " + playerList2.get(playerList2.size() - 1));
            }
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.WHITE);
                    title.setTextSize(23);
                    alertDialog.setCustomTitle(title);

            }catch (Exception ee){
                    ee.printStackTrace();
                }

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "نعم",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {  // adding OnClickListener to  POSITIVE button
                        arrayAdapter.clear();
                        arrayAdapter2.clear();
                        total = 0;
                        total2 = 0;
                        rotate.setRotation(0); // rotate the arrow to default

                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "لا",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { // adding OnClickListener to  NEGATIVE button
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        // Start making button in center and customize color for it
        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        btnPositive.setTextColor(Color.parseColor("#c62828")); // to make color of btnPositive red and btnNegative blue light color
        btnNegative.setTextColor(Color.parseColor("#00b0ff"));
        btnNegative.setBackgroundColor(Color.parseColor("#e0e0e0"));  // gray light
        btnPositive.setBackgroundColor(Color.parseColor("#e0e0e0"));  // the same

        btnPositive.setTextSize(20);
        btnNegative.setTextSize(20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10,0,10,10);
        params.weight = 40;
        params.gravity = Gravity.CENTER; //this is layout_gravity
        btnNegative.setLayoutParams(params);
        btnPositive.setLayoutParams(params); // end making button in center and customize color for it

        // to change message style
        TextView textView = alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(20);

        // to make message gravity center
        TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextColor(Color.WHITE);
        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alartdilogroundedbackground));

    } // dialog method end


    // جزء القائمة "المنيو"
    // start from here to until the  bottom
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId(); // If someone presses home button in title bar it will remove the last 3 position in array list
        if (id==android.R.id.home) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("");
            alertDialog.setMessage( "هل فعلاً تريد التراجع ؟" );

            TextView title = new TextView(this);
            title.append("تراجع");

            try {
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(23);
                alertDialog.setCustomTitle(title);
                    }catch (Exception ee){
                        ee.printStackTrace();
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "نعم",
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {  // adding OnClickListener to  POSITIVE button

                            try {

                                if (playerList.size() == 1){
                                    playerList.clear();
                                    arrayAdapter.notifyDataSetChanged();
                                }
                                if (playerList2.size() == 1){
                                    playerList2.clear();
                                    arrayAdapter2.notifyDataSetChanged();
                                }

                                playerList.set(playerList.size()-3,"") ; // To solve the problem of changing the place in the arraylist
                                playerList.set(playerList.size()-2,"") ;// before removing item in players list first thing I should make it equle to "0">>>
                                playerList.set(playerList.size()-1,"") ;// because if I did't do this the sort will change suddenly

                                playerList2.set(playerList2.size()-3,"") ;
                                playerList2.set(playerList2.size()-2,"") ;
                                playerList2.set(playerList2.size()-1,"") ;

                                arrayAdapter.remove(arrayAdapter.getItem(arrayAdapter.getCount() - 3)); // to remove the last three element in arraylist
                                arrayAdapter.remove(arrayAdapter.getItem(arrayAdapter.getCount() - 2));
                                arrayAdapter.remove(arrayAdapter.getItem(arrayAdapter.getCount() - 1));
                                arrayAdapter2.remove(arrayAdapter2.getItem(arrayAdapter2.getCount() - 3));
                                arrayAdapter2.remove(arrayAdapter2.getItem(arrayAdapter2.getCount() - 2));
                                arrayAdapter2.remove(arrayAdapter2.getItem(arrayAdapter2.getCount() - 1));


                                if (playerList.size() == 0) {  // if arraylist is zero return value of total zero
                                    total = 0;
                                }

                                if (playerList2.size() == 0) {
                                    total2 = 0;
                                }

                                total = Integer.parseInt(playerList.get(playerList.size() - 1)); // to let total equal to the last element
                                total2 = Integer.parseInt(playerList2.get(playerList2.size() - 1));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } // dialog Yes end
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "لا",
                            new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { // adding OnClickListener to  NEGATIVE button
                            dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    // Start making button in center and customize color for it
                    Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    btnPositive.setTextColor(Color.parseColor("#c62828")); // to make color of btnPositive red and btnNegative blue light color
                    btnNegative.setTextColor(Color.parseColor("#00b0ff"));
                    btnNegative.setBackgroundColor(Color.parseColor("#e0e0e0"));  // gray light
                    btnPositive.setBackgroundColor(Color.parseColor("#e0e0e0"));  // the same

                    btnPositive.setTextSize(20);
                    btnNegative.setTextSize(20);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
                    params.setMargins(10,0,10,10);
                    params.weight = 40;
                    params.gravity = Gravity.CENTER; //this is layout_gravity
                    btnNegative.setLayoutParams(params);
                    btnPositive.setLayoutParams(params); // end making button in center and customize color for it


                    // to change message style
                    TextView textView = alertDialog.findViewById(android.R.id.message);
                    textView.setTextSize(20);

                    // to make message gravity center
                    TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
                    messageView.setGravity(Gravity.CENTER);
                    messageView.setTextColor(Color.WHITE);
                    alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alartdilogroundedbackground));
                }


                // قرعة ولد
                int id2 = item.getItemId();
                if(id2==R.id.item1) {

                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("");

                    if (playerList.size() >= 1 || playerList2.size() >= 1) { // making condition if there a result give me alert dialog

                        alertDialog.setMessage(" سيتم حذف النتيجة" + "\n" + "هل تريد المتابعة ؟ ");

                        TextView title = new TextView(this);
                        title.append("⛔  تنبيه  ⛔");

                        try {
                            title.setGravity(Gravity.CENTER);
                            title.setTextColor(Color.WHITE);
                            title.setTextSize(23);
                            alertDialog.setCustomTitle(title);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "نعم",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {  // adding OnClickListener to  POSITIVE button
                                        try {  // move to another activity
                                            // move to another activity
                                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                                            startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        arrayAdapter.clear();  // clear all stuff
                                        arrayAdapter2.clear();
                                        total = 0;
                                        total2 = 0;
                                        rotate.setRotation(0); // rotate the arrow to default

                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "لا",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) { // adding OnClickListener to  NEGATIVE button
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        // Start making button in center and customize color for it
                        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                        btnPositive.setTextColor(Color.parseColor("#c62828")); // to make color of btnPositive red and btnNegative blue light color
                        btnNegative.setTextColor(Color.parseColor("#00b0ff"));
                        btnNegative.setBackgroundColor(Color.parseColor("#e0e0e0"));  // gray light
                        btnPositive.setBackgroundColor(Color.parseColor("#e0e0e0"));  // the same

                        btnPositive.setTextSize(20);
                        btnNegative.setTextSize(20);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( //start making buttons center
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(10,0,10,10);
                        params.weight = 40;
                        params.gravity = Gravity.CENTER; //this is layout_gravity
                        btnNegative.setLayoutParams(params);
                        btnPositive.setLayoutParams(params); // end making button in center and customize color for it

                        // to change message style
                        TextView textView = alertDialog.findViewById(android.R.id.message);
                        textView.setTextSize(20);

                        // to make message gravity center
                        TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
                        messageView.setGravity(Gravity.CENTER);
                        messageView.setTextColor(Color.WHITE);
                        alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alartdilogroundedbackground));
                    }
                    if(playerList.size() == 0 && playerList2.size() == 0){  // making condition if there is no result just open new activity3
                        try {  // move to another activity
                            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }

                // لعبة جديدة
        switch (item.getItemId()) {  // if someone click the button item2 "لعبة جديدة" it will restart the game
            case R.id.item2:
                try {
                    arrayAdapter.clear();
                    arrayAdapter2.clear();
                    total = 0;
                    total2 = 0;
                    rotate.setRotation(0); // rotate the arrow to default
                    Toast.makeText(this, "لقد تم اعادة اللعبة", Toast.LENGTH_SHORT).show();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // تعليم البلوت
            case R.id.item3: // to go another window
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("");

                if(playerList.size() >= 1 || playerList2.size() >= 1) { // making condition if there a result give me alert dialog

                    alertDialog.setMessage(" سيتم حذف النتيجة" + "\n" + "هل تريد المتابعة ؟");

                    TextView title = new TextView(this);
                    title.append("⛔  تنبيه  ⛔");

                    try {
                        title.setGravity(Gravity.CENTER);
                        title.setTextColor(Color.WHITE);
                        title.setTextSize(23);
                        alertDialog.setCustomTitle(title);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "نعم",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {  // adding OnClickListener to  POSITIVE button
                                    try {  // move to another activity
                                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    arrayAdapter.clear();  // clear all stuff
                                    arrayAdapter2.clear();
                                    total = 0;
                                    total2 = 0;
                                    rotate.setRotation(0); // rotate the arrow to default

                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "لا",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) { // adding OnClickListener to  NEGATIVE button
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    // Start making button in center and customize color for it
                    Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    btnPositive.setTextColor(Color.parseColor("#c62828")); // to make color of btnPositive red and btnNegative blue light color
                    btnNegative.setTextColor(Color.parseColor("#00b0ff"));
                    btnNegative.setBackgroundColor(Color.parseColor("#e0e0e0"));  // gray light
                    btnPositive.setBackgroundColor(Color.parseColor("#e0e0e0"));  // the same

                    btnPositive.setTextSize(20);
                    btnNegative.setTextSize(20);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( //start making buttons center
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(10,0,10,10);
                    params.weight = 40;
                    params.gravity = Gravity.CENTER; //this is layout_gravity
                    btnNegative.setLayoutParams(params);
                    btnPositive.setLayoutParams(params); // end making button in center and customize color for it

                    // to change message style
                    TextView textView = alertDialog.findViewById(android.R.id.message);
                    textView.setTextSize(20);

                    // to make message gravity center
                    TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
                    messageView.setGravity(Gravity.CENTER);
                    messageView.setTextColor(Color.WHITE);
                    alertDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.alartdilogroundedbackground));
                }

                if(playerList.size() == 0 && playerList2.size() == 0){  // making condition if there is no result just open new activity
                    try {  // move to another activity
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                    return true;

                // التحدث الالي
            case R.id.itemspech: // to make voice when you need the result of the game
                    textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status == TextToSpeech.SUCCESS) {
                                int result = textToSpeech.setLanguage(Locale.US);
                                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Log.e("error", "This Language is not supported");
                                } else {
                                    if ((playerList.size() == 0 ) && (playerList2.size() == 0 )) {
                                    texttoSpeak2();
                                    }
                                    if (playerList.size() >= 1 || playerList2.size() >= 1) {
                                        texttoSpeak();
                                    }
                                }
                            } else {
                                Log.e("error", "Failed to Initialize");
                            }
                        }
                        private void texttoSpeak() {
                            String text = "The result is, " + playerList.get(playerList.size() - 1) + "And the other side is, " + playerList2.get(playerList2.size() - 1);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                            } else {
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                        private void texttoSpeak2() {
                            String text = "please enter something.";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                            } else {
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    });
                    return true;

                // سجل الانتصارات
            case R.id.Historical:
                try {  // move to another activity
                    Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                            startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
                 default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    // to show menu in item
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bilud_menu, menu);
        return true;

    }
}
