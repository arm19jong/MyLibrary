package com.teamsmokeweed.mylibrary.Member;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.teamsmokeweed.mylibrary.DatabaseMyLibrary.DBCheck;
import com.teamsmokeweed.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongzazaal on 18/11/2559.
 */

public class ShowMemberActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    SwipeRefreshLayout swipeRefreshLayout;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmember);
        Intent i = getIntent();
        type = i.getIntExtra("type", 0);

        //From Here Starts All The Swipe To
        // Refresh Initialisation And Setter Methods.
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);//Initialising
        //Setting Up OnRefresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                //onRefresh method is used to perform all the action
                // when the swipe gesture is performed.
                try {
                    //new RecentCustomAdapter().clearData();
                    initializeRecyclerView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //This are some optional methods for customizing
        // the colors and size of the loader.
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue,       //This method will rotate
                R.color.red,        //colors given to it when
                R.color.yellow,     //loader continues to
                R.color.green);     //refresh.

        //setSize() Method Sets The Size Of Loader
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //Below Method Will set background color of Loader
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);

        initializeRecyclerView();



        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("จิ้มๆๆ");
                final Dialog dialog = new Dialog(ShowMemberActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_member_add);
                dialog.setCancelable(true);

                final EditText idPerson = (EditText) dialog.findViewById(R.id.idPerson);
                final EditText name = (EditText) dialog.findViewById(R.id.name);
                final EditText lastName = (EditText) dialog.findViewById(R.id.lastName);
                final EditText tel = (EditText) dialog.findViewById(R.id.tel);

                final EditText username = (EditText) dialog.findViewById(R.id.username);
                final EditText password = (EditText) dialog.findViewById(R.id.password);

                if(type == 0) {
                    username.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                }
                Button addMember = (Button)dialog.findViewById(R.id.addMember);
                addMember.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Toast.makeText(ShowMemberActivity.this
//                                , name.getText().toString(), Toast.LENGTH_SHORT);
//                        surname.setText(name.getText().toString());

                        //dialog.cancel();
                        new DBCheck(ShowMemberActivity.this).AddMember(idPerson.getText().toString(),
                                name.getText().toString(), lastName.getText().toString(),
                                tel.getText().toString(), type
                        );
                        if(type == 1){
                            new DBCheck(ShowMemberActivity.this).AddUsernamePassword(idPerson.getText().toString(),
                                    username.getText().toString(), password.getText().toString());
                        }
                        dialog.cancel();

                    }
                });
                Button updateMember = (Button) dialog.findViewById(R.id.updateMember);
                updateMember.setVisibility(View.GONE);
                Button delMember = (Button) dialog.findViewById(R.id.delMember);
                delMember.setVisibility(View.GONE);

                dialog.show();
            }
        });
    }
    public void initializeRecyclerView(){
        List<ArrayList<String>> member = new ArrayList<>();
//    Type 0-> Member
//         1-> Admin

        member = new DBCheck(ShowMemberActivity.this).SelectMember(type);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, member.get(0)));
        final List<ArrayList<String>> finalMember = member;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //Toast.makeText(ShowMemberActivity.this, position+"", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(ShowMemberActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_member_add);
                dialog.setCancelable(true);

                final EditText idPerson = (EditText) dialog.findViewById(R.id.idPerson);
                final EditText name = (EditText) dialog.findViewById(R.id.name);
                final EditText lastName = (EditText) dialog.findViewById(R.id.lastName);
                final EditText tel = (EditText) dialog.findViewById(R.id.tel);

                EditText username = (EditText) dialog.findViewById(R.id.username);
                EditText password = (EditText) dialog.findViewById(R.id.password);

                username.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
//                Toast.makeText(ShowMemberActivity.this, ""+(finalMember.get(1)).get(position), Toast.LENGTH_SHORT).show();
                ArrayList<String> memberList = new DBCheck(ShowMemberActivity.this).ShowMember(finalMember.get(1).get(position));

                idPerson.setText(memberList.get(0));
                name.setText(memberList.get(1));
                lastName.setText(memberList.get(2));
                tel.setText(memberList.get(3));

                idPerson.setEnabled(false);

                Button addMember = (Button)dialog.findViewById(R.id.addMember);
                addMember.setVisibility(View.GONE);

                Button updateMember = (Button) dialog.findViewById(R.id.updateMember);
                updateMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DBCheck(ShowMemberActivity.this).UpdateMember(idPerson.getText().toString(),
                                name.getText().toString(), lastName.getText().toString(),
                                tel.getText().toString());
                        dialog.cancel();

                    }
                });
                Button delMember = (Button) dialog.findViewById(R.id.delMember);
                delMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DBCheck(ShowMemberActivity.this).DelMember(finalMember.get(1).get(position));
                        dialog.cancel();
                    }
                });



                dialog.show();

            }
        });
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}