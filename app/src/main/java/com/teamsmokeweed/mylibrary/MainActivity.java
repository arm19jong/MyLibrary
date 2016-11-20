package com.teamsmokeweed.mylibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teamsmokeweed.mylibrary.Member.ShowMemberActivity;
import com.teamsmokeweed.mylibrary.book.ShowBookActivity;

public class MainActivity extends AppCompatActivity {

    Button bMember, bAdmin, bBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        String fullName = i.getStringExtra("fullName");
        Toast.makeText(MainActivity.this, "Hi: "+fullName, Toast.LENGTH_SHORT).show();

        bMember = (Button) findViewById(R.id.bMember);
        bMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowMemberActivity.class);
//    Type 0-> Member
//         1-> Admin
                i.putExtra("type", 0);
                startActivity(i);
            }
        });

        bAdmin = (Button) findViewById(R.id.bAdmin);
        bAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowMemberActivity.class);
//    Type 0-> Member
//         1-> Admin
                i.putExtra("type", 1);
                startActivity(i);
            }
        });
        bBook = (Button) findViewById(R.id.bBook);
        bBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowBookActivity.class);
//    Type 0-> Member
//         1-> Admin
                startActivity(i);
            }
        });

    }
}
