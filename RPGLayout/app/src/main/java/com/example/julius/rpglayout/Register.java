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

        mUsername = recvdIntent.getStringExtra("USERNAME");
        mPassword = recvdIntent.getStringExtra("PASSWORD");
        mCharName = recvdIntent.getStringExtra("CHARNAME");

        Button btnSubmit = (Button) findViewById(R.id.btn_nextr);
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitUserData();
                        Intent reg = new Intent(Register.this,CharCreation.class);
                        startActivity(reg);
                        return;
                    }
                }
        );

        return;
    }

    private void submitUserData() {
        EditText edtUsername = (EditText) findViewById(R.id.edt_username);
        EditText edtPassword = (EditText) findViewById(R.id.edt_password);
        EditText edtCharName = (EditText) findViewById(R.id.edt_char_name);

        DNDApp app = (DNDApp) getApplication();
        app.saveUserData(edtUsername.getText().toString(),
                edtPassword.getText().toString(),
                edtCharName.getText().toString() );
        finish();
        return;
    }

}
