package abbalove.chat.chatapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import abbalove.chat.chatapp.adapters.ItemAdapter;
import abbalove.chat.chatapp.adapters.TimelineAdapter;
import abbalove.chat.chatapp.models.Items;
import abbalove.chat.chatapp.models.Post;
import abbalove.chat.chatapp.models.User;
import abbalove.chat.chatapp.utils.PrefUtils;

public class ItemActivity extends AppCompatActivity {
    private EditText title,content;
    private Button btnsave;

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemActivity.this, AddItemActivity.class));
            }
        });
      /*  firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("items");
        userData = PrefUtils.getCurrentUser(ItemActivity.this);
        final List<Items> items = new ArrayList<>();
        itemAdapter = new ItemAdapter(ItemActivity.this,items);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ItemActivity.this));

        //tarik data
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Items> list = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Items p = snapshot.getValue(Items.class);
                    list.add(p);

                }
                itemAdapter.insertData(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(eventListener);
*/
    }


}
