package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private String mUsername = "";
    private String mPassword = "";
    private String mCharName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent recvdIntent = getIntent();



        final EditText edtUsername = (EditText) findViewById(R.id.edt_username);
        final EditText edtPassword = (EditText) findViewById(R.id.edt_password);
        final EditText edtCharName = (EditText) findViewById(R.id.edt_char_name);

       /* mUsername = edtUsername.getText().toString();
        mPassword = edtPassword.getText().toString();
        mCharName = edtCharName.getText().toString();*/


        Button btnSubmit = (Button) findViewById(R.id.btn_nextr);

        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitUserData();
                        String username = edtUsername.getText().toString();
                        String password = edtPassword.getText().toString();
                        String charName = edtCharName.getText().toString();

                        Intent reg = new Intent(Register.this, CharCreation.class);
                        reg.putExtra("CHARNAME", charName);
                        startActivity(reg);
                        //return;
                    }
                }
        );

        return;
    }

    private void submitUserData() {


        DNDApp app = (DNDApp) getApplication();
        app.saveUserData(mUsername,
                mPassword,
                mCharName);
        //finish();
        //return;
    }

}
