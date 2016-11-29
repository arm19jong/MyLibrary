package com.teamsmokeweed.mylibrary.book;

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
import com.teamsmokeweed.mylibrary.Member.ShowMemberActivity;
import com.teamsmokeweed.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongzazaal on 20/11/2559.
 */

public class ShowBookActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmember);
        //From Here Starts All The Swipe To
        // Refresh Initialisation And Setter Methods.
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);//Initialising
        //Setting Up OnRefresh Listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                final Dialog dialog = new Dialog(ShowBookActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_book);
                dialog.setCancelable(true);

                final EditText idBook = (EditText) dialog.findViewById(R.id.idBook);
                final EditText nameBook = (EditText) dialog.findViewById(R.id.nameBook);
                final EditText nameWriter = (EditText) dialog.findViewById(R.id.nameWriter);


                Button addBook = (Button) dialog.findViewById(R.id.addBook);
                addBook.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Toast.makeText(ShowMemberActivity.this
//                                , name.getText().toString(), Toast.LENGTH_SHORT);
//                        surname.setText(name.getText().toString());

                        new DBCheck(ShowBookActivity.this).AddBook(idBook.getText().toString(),
                                nameBook.getText().toString(), nameWriter.getText().toString()
                        );
                        dialog.cancel();

                    }
                });
                Button updateBook = (Button) dialog.findViewById(R.id.updateBook);
                updateBook.setVisibility(View.GONE);
                Button delBook = (Button) dialog.findViewById(R.id.delBook);
                delBook.setVisibility(View.GONE);

                dialog.show();
            }
        });
    }

    public void initializeRecyclerView() {

        List<ArrayList<String>> book = new ArrayList<>();
//    Type 0-> Member
//         1-> Admin

        book = new DBCheck(ShowBookActivity.this).SelectBook();

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, book.get(0)));
        final List<ArrayList<String>> finalBook = book;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                //Toast.makeText(ShowMemberActivity.this, position+"", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(ShowBookActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_book);
                dialog.setCancelable(true);

                final EditText idBook = (EditText) dialog.findViewById(R.id.idBook);
                final EditText nameBook = (EditText) dialog.findViewById(R.id.nameBook);
                final EditText nameWriter = (EditText) dialog.findViewById(R.id.nameWriter);

//                Toast.makeText(ShowMemberActivity.this, ""+(finalMember.get(1)).get(position), Toast.LENGTH_SHORT).show();
                ArrayList<String> BookList = new DBCheck(ShowBookActivity.this).ShowBook(finalBook.get(1).get(position));

                idBook.setText(BookList.get(0));
                nameBook.setText(BookList.get(1));
                nameWriter.setText(BookList.get(2));

                idBook.setEnabled(false);

                Button addBook = (Button) dialog.findViewById(R.id.addBook);
                addBook.setVisibility(View.GONE);

                Button updateMember = (Button) dialog.findViewById(R.id.updateBook);
                updateMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DBCheck(ShowBookActivity.this).UpdateBook(idBook.getText().toString(),
                                nameBook.getText().toString(), nameWriter.getText().toString()
                        );
                        dialog.cancel();

                    }
                });
                Button delMember = (Button) dialog.findViewById(R.id.delBook);
                delMember.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DBCheck(ShowBookActivity.this).DelBook(finalBook.get(1).get(position));
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}