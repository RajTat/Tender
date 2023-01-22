package com.tender.team08.cs246.tender;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


/**
 * Hub for Patient information and all actions user can take regarding:
 * View Details
 * Go to Patient Image
 * Go to Calendar Activity
 *
 */
public class DisplayPatientActivity extends AppCompatActivity implements AsyncResponse {

    private String _firstName;
    private String _lastName;
    private Patient _patient;

    private Map<String , TextView> _views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patient);
        Intent intent = getIntent();
        _firstName = intent.getStringExtra("firstName");
        _lastName = intent.getStringExtra("lastName");
        _views = new HashMap<>();
        _views.put("firstName",(TextView)findViewById(R.id.displayFirstName));
        _views.put("lastName",(TextView)findViewById(R.id.displayLastName));
        _views.put("email",(TextView)findViewById(R.id.displayEmail));
        _views.put("phoneNumber",(TextView)findViewById(R.id.displayPhoneNumber));
        _views.put("street",(TextView)findViewById(R.id.displayStreet));
        _views.put("city",(TextView)findViewById(R.id.displayCity));
        _views.put("state",(TextView)findViewById(R.id.displayState));
        _views.put("zip",(TextView)findViewById(R.id.displayZip));
        loadPatientData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadPatientData();

    }

    private void loadPatientData() {
        LoadPatientTask loadPatientTask = new LoadPatientTask(this, _views, null, _firstName, _lastName);
        loadPatientTask.delegate = this;
        loadPatientTask.execute();
    }

    /**
     * Method ends activity
     *
     * @param view
     */
    public void goBack(View view) {
        finish();
    }

    /**
     * Method sends user to the Patient Image
     *
     * @param view
     */
    public void goToImage (View view) {
        Intent intent = new Intent(this, ImageActivity.class);
        Bundle name = new Bundle();
        intent.putExtra("Patient", _patient);
        startActivity(intent);
    }

    public void goToCalendar (View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra("first", _firstName);
        intent.putExtra("last", _lastName);
        TextView street = findViewById(R.id.displayStreet);
        intent.putExtra("street", street.getText());
        TextView city = findViewById(R.id.displayCity);
        intent.putExtra("city", city.getText());
        TextView state = findViewById(R.id.displayState);
        intent.putExtra("state", state.getText());
        TextView zip = findViewById(R.id.displayZip);
        intent.putExtra("zip", zip.getText());
        TextView email = findViewById(R.id.displayEmail);
        intent.putExtra("email", email.getText());
        startActivity(intent);
    }

    public void goToEditPatient(View view){
        Intent intent = new Intent(this, EditPatientActivity.class);
        intent.putExtra("firstName", _firstName);
        intent.putExtra("lastName",_lastName);
        startActivity(intent);
    }

    public void goToAllPatients(View view){
        Intent intent = new Intent(this, PatientActivity.class);

        startActivity(intent);
    }

    @Override
    public void processFinish(boolean uniqueEntry) {

    }

    @Override
    public void processFinish(Patient patient) {
        _patient = patient;
    }
}
