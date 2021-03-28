package com.example.driver_1.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.driver_1.R;
import com.example.driver_1.data.loggedInDriver.MainActivity;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.register2);

        // The Edit Text that I need to get the changed data from
        EditText username = this.findViewById(R.id.usernameEditTextRegister);
        EditText password = this.findViewById(R.id.passwordEditTextRegister);
        EditText address = this.findViewById(R.id.addressEditTextRegister);
        EditText phoneNumber = this.findViewById(R.id.phoneNumberEditTextRegister);
        EditText email = this.findViewById(R.id.emailEditTextRegister);
        EditText age = this.findViewById(R.id.ageEditTextRegister);
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
        //Spinner gender = this.findViewById(R.id.genderChoiceRegister);
        //gender.setOnItemClickListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
        //        R.array.genderOptions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //gender.setAdapter(adapter);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", email.getText().toString());
                editor.putString("password", String.valueOf(password));
                editor.apply();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}