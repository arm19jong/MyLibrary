package com.teamsmokeweed.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamsmokeweed.mylibrary.DatabaseMyLibrary.DBCheck;

/**
 * Created by jongzazaal on 18/11/2559.
 */

public class Login extends AppCompatActivity {
    Button bLogin;
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bLogin = (Button) findViewById(R.id.bLogin);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBCheck dbCheck = new DBCheck(Login.this);
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                if (dbCheck.IsAdmin(sUsername, sPassword)) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    i.putExtra("fullName", dbCheck.Who(dbCheck.getIdPersonAdmin(sUsername, sPassword)));
                    startActivity(i);
                } else {
                    Toast.makeText(Login.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
