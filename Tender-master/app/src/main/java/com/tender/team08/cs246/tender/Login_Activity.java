package com.tender.team08.cs246.tender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * First Activity used to log in or register
 * User must be logged in so this must be completed
 * Class returns intent
 *
 */
public class Login_Activity extends AppCompatActivity {

    TextView feedbackMsgView;
    EditText pinTextView;
    EditText edit;
    EditText firstTxt;
    EditText lastTxt;
    EditText phoneTxt;
    EditText emailTxt;
    EditText streetTxt;
    EditText cityTxt;
    EditText stateTxt;
    EditText zipTxt;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    String pin; //stores results of a shared preference key in this case the PIN

    /**
     * Constructor Creates input filters and collects input views
     * Also sets toggle listener and collects shared preferences
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        pinTextView = findViewById(R.id.pinTextView);
        firstTxt    = findViewById(R.id.firstEditTxt);
        lastTxt     = findViewById(R.id.lastEditTxt);
        phoneTxt    = findViewById(R.id.phoneEditTxt);
        emailTxt    = findViewById(R.id.emailEditTxt);
        streetTxt   = findViewById(R.id.streetEditTxt);
        cityTxt     = findViewById(R.id.cityEditTxt);
        stateTxt    = findViewById(R.id.stateEditTxt);
        zipTxt      = findViewById(R.id.zipEditTxt);

        //set filters
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(4);
        pinTextView.setFilters(filter);
        filter[0] = new InputFilter.LengthFilter(5);
        zipTxt.setFilters(filter);
        filter[0] = new InputFilter.LengthFilter(2);
        stateTxt.setFilters(filter);
        filter[0] = new InputFilter.LengthFilter(10);
        phoneTxt.setFilters(filter);

        //put input feedback in this object
        feedbackMsgView = findViewById(R.id.feedbackMsgView);

        //this toggle makes the registration forms visible and invisible
        ToggleButton signTog = (ToggleButton) findViewById(R.id.signInToggle);
        signTog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) viewAll();
                else   viewLess();
        }});

        //there shouldn't be a lot of shared preferences so a single file is used instead of getSharedPreferences
        sharedPref = getPreferences(MODE_PRIVATE);
    }

    /**
     * Method that calls the input fields to visibility
     * gets called when the toggle is set to register
     */
    private void viewAll() {
        feedbackMsgView.setText("Please enter your information to register to...");

        //set registration fields to visible
        firstTxt.setVisibility(View.VISIBLE);
        lastTxt.setVisibility(View.VISIBLE);
        phoneTxt.setVisibility(View.VISIBLE);
        emailTxt.setVisibility(View.VISIBLE);
        streetTxt.setVisibility(View.VISIBLE);
        cityTxt.setVisibility(View.VISIBLE);
        stateTxt.setVisibility(View.VISIBLE);
        zipTxt.setVisibility(View.VISIBLE);
    }

    /**
     * Method that calls the input fields to hidden
     * gets called when the toggle is set to login
     */
    private void viewLess() {
        feedbackMsgView.setText("Please sign in with your four digit pin to...");

        //set registration fields to invisible
        firstTxt.setVisibility(View.INVISIBLE);
        lastTxt.setVisibility(View.INVISIBLE);
        phoneTxt.setVisibility(View.INVISIBLE);
        emailTxt.setVisibility(View.INVISIBLE);
        streetTxt.setVisibility(View.INVISIBLE);
        cityTxt.setVisibility(View.INVISIBLE);
        stateTxt.setVisibility(View.INVISIBLE);
        zipTxt.setVisibility(View.INVISIBLE);
    }

    /**
     * Submits the forms for registration
     * submit based on PIN, signin / register toggles
     * Checks for valid signin / registration
     *
     * @param view
     */
    public void submit(View view) {

        edit = findViewById(R.id.pinTextView);
        if (edit.length() < 4) {
            feedbackMsgView.setText("Invalid length. Pin must be four digits");
            return;
        }

        editor = sharedPref.edit();
        pin = edit.getText().toString();

        ToggleButton signIn = findViewById(R.id.signInToggle);

        if (!signIn.isChecked()) { // not-checked is signing in
            Gson gson = new Gson();
            String[] results = gson.fromJson(sharedPref.getString(pin, ""), String[].class);
            if (results == null) {
                //if the default value is returned the pin was not found
                //error message
                feedbackMsgView.setText("Invalid PIN. Enter another PIN or register a new PIN");
            }
            else { //success
                feedbackMsgView.setText("Sign In Successful");
                loginSuccess(results);
            }
        }
        else { // register
            // check for valid submission
            // will expand these checks later most likely
            if (firstTxt.length() == 0 ||
                    lastTxt.length() == 0 ||
                    phoneTxt.length() == 0 ||
                    emailTxt.length() == 0 ||
                    streetTxt.length() == 0 ||
                    cityTxt.length() == 0 ||
                    stateTxt.length() == 0 ||
                    zipTxt.length() == 0) {
                feedbackMsgView.setText("Please input all fields");
            }
            else registerSubmit();
    }   }

    /**
     * Method that completes the login process
     * Successful login creates the User Therapist/Patient Module...
     * returns it to Main Activity as serialized in Intent
     * !!Appointments have not yet been defined!!
     */
    private void loginSuccess(String[] results) {
        Therapist user = new Therapist(
                results[3],
                results[0],
                results[1],
                results[2],
                results[4],
                results[5],
                results[6],
                results[7],
                "",
                pin
        );

        Intent resultIntent = new Intent();
        resultIntent.putExtra("userResult", user);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    /* Registers new User and returns to Main Activity...
     * serialized in intent
     * Didn't need to be separate but it's long
     * !!Appointments have not yet been defined!!
     */
    private void registerSubmit() {

        // save user to shared preferences
        Gson gson = new Gson();
        List<String> list = new ArrayList();
        list.add(firstTxt.getText().toString());
        list.add(lastTxt.getText().toString());
        list.add(phoneTxt.getText().toString());
        list.add(emailTxt.getText().toString());
        list.add(streetTxt.getText().toString());
        list.add(cityTxt.getText().toString());
        list.add(stateTxt.getText().toString());
        list.add(zipTxt.getText().toString());

        editor.putString(pin, gson.toJson(list)); //registers the pin as the key value in sharedPreferences
        editor.commit(); // could cause pausing, may need to create aSync task

        // this is what we'll pass back to MainActivity
        Therapist user = new Therapist(
                emailTxt.getText().toString(),
                firstTxt.getText().toString(),
                lastTxt.getText().toString(),
                phoneTxt.getText().toString(),
                streetTxt.getText().toString(),
                cityTxt.getText().toString(),
                stateTxt.getText().toString(),
                zipTxt.getText().toString(),
                "",
                pin
        );

        Intent resultIntent = new Intent();
        resultIntent.putExtra("userResult", user);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}