//Tender App Team 08
//Michael Goytia
//Ryan Phillips
//Anthony Wagner

package com.tender.team08.cs246.tender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Console;
import java.io.Serializable;


/**
 * First class called for main navigation
 * Before the UI can be used, first sends user to Login_Activity
 * Receives the Therapist class instance from Login or else sends it back
 */


import android.widget.Button;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    //commenting out classes not created / imported yet

    private String profilename;
    //private appdatabase db;
    private ListView appointmentListView;
    private TextView welcomeMessage;
    private ListView searchDropDownView;
    private LoadPatientTask loadPatient;
    private LoadAppointmentTask loadAppointments;
    private LoadTherapistTask loadTherapist;
    private CalendarActivity calendarActivity;

    private Therapist user;

    /**
     * Constructor which starts by creating the Login_Activity
     *
     * @param savedInstanceState not used
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, Login_Activity.class);
        startActivityForResult(intent, 01);
    }

    /**
     * Method for handling a return from the Login_Activity
     * Deserializes the data or sends back to Login_Activity
     *
     * @param requestCode not used
     * @param resultCode needs to be RESULT_OK or sends back to login
     * @param data received from login activity with the serialized Therapist instance
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.i("MainActivity onResults", "Result OK");
            Bundle bundle = data.getExtras();
            user = (Therapist) bundle.getSerializable("userResult");
            Log.i("MainActivity onResults", "user received full name: " + user.getFullName());
            Log.i("MainActivity onResults", "user received address: " + user.getAddress());
            Log.i("MainActivity onResults", "user received contact: " + user.getContact());
        }
        else {
            if (user == null) {
                Log.w("MainActivity onResults", "reading null user");
                Intent intent = new Intent(this, Login_Activity.class);
                startActivityForResult(intent, 01);
            }
            else Log.w("MainActivity onResults", "result error, user ok");
        }
    }

    /**
     * sends user to PatientActivity
     *
     * @param view
     */
    public void goToPatients(View view) {
        Intent intent = new Intent(this, PatientActivity.class);
        startActivity(intent);
    }

    /**
     * sends user to DisplayTherapistActivity
     *
     * @param view
     */
    public void goToTherapist(View view) {
        Intent intent = new Intent( this, DisplayTherapistActivity.class);
        Bundle name = new Bundle();
        intent.putExtra("Therapist", user);
        startActivity(intent);
    }
}
