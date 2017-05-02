package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText edtUsername = (EditText) findViewById(R.id.edt_username);
        final EditText edtPW = (EditText) findViewById(R.id.edt_password);






        Button reg = (Button) findViewById(R.id.btn_reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(MainActivity.this,Register.class);
                startActivity(reg);
            }
        });

        Button main = (Button) findViewById(R.id.btn_login);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPW.getText().toString();
                Intent main = new Intent(MainActivity.this,Home.class);
                main.putExtra("USERNAME", username);
                main.putExtra("PASSWORD", password);
                startActivity(main);
            }
        });
    }
}
