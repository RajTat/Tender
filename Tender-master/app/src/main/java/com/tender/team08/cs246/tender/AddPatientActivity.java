package com.tender.team08.cs246.tender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Class that prompts user for information to create a new Patient
 */
public class AddPatientActivity extends AppCompatActivity implements AsyncResponse {

    EditText _firstName;
    EditText _lastName;
    EditText _email;
    EditText _street;
    EditText _phoneNumber;
    EditText _city;
    EditText _state;
    EditText _zip;
    Patient _newPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        _firstName = (EditText)findViewById(R.id.addFirstName);
        _lastName = (EditText)findViewById(R.id.addLastName);
        _email = (EditText)findViewById(R.id.addEmail);
        _street = (EditText)findViewById(R.id.addStreet);
        _phoneNumber = (EditText)findViewById(R.id.addPhone);
        _state = (EditText)findViewById(R.id.addState);
        _city = (EditText)findViewById(R.id.addCity);
        _zip = (EditText)findViewById(R.id.addZip);

    }

    /**
     *Method to get input fields and store them in a new Patient instance
     *
     * Calls AddPatientTask
     *
     * @param view
     */
    public void addPatient(View view){
        String firstName = _firstName.getText().toString();
        String lastName = _lastName.getText().toString();
        String street = _street.getText().toString();
        String phone = _phoneNumber.getText().toString();
        String email = _email.getText().toString();
        String city = _city.getText().toString();
        String state = _state.getText().toString();
        String zip = _zip.getText().toString();
        _newPatient = new Patient(email,firstName,lastName,phone,street,city,state,zip,"","", "");
        AddPatientTask addPatientActivity = new AddPatientTask(this, _newPatient);
        addPatientActivity.delegate = this;
        addPatientActivity.execute();
    }

    /**
     * Method that creates a new Activity
     *
     * Creates new Intent with the content first and last name
     *
     * @param uniqueEntry conditional for executing
     */
    @Override
    public void processFinish(boolean uniqueEntry) {
        if(uniqueEntry){
            Intent intent = new Intent(this, DisplayPatientActivity.class);
            intent.putExtra("firstName", _newPatient.getFirstName());
            intent.putExtra("lastName",_newPatient.getLastName());
            startActivity(intent);
        }
    }


    /**
     * Quits the current Activity
     *
     * @param view
     */
    public void goBack(View view) {
        finish();
    }

    @Override
    public void processFinish(Patient patient) {

    }
}
