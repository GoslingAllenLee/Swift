package wartaonline.chat.chatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import wartaonline.chat.chatapp.adapters.CommentAdapter;
import wartaonline.chat.chatapp.models.Comment;
import wartaonline.chat.chatapp.models.User;
import wartaonline.chat.chatapp.models.WorldPost;
import wartaonline.chat.chatapp.utils.PrefUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by faisalrizarakhmat on 24/01/18.
 */

public class CommentWeeklyActivity extends AppCompatActivity {

    String title, content, image, key;
    TextView txtNamePost, txtContentPost;
    ImageView imgview;
    EditText etPesan;
    Button btnSend;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User userData;
    private StorageReference mStorage;

    private List<Comment> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    //private CommentAdapter mAdapter;
    private CommentAdapter timelineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worlddetailpost);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNamePost = (TextView) findViewById(R.id.txtNamePost);
        txtContentPost = (TextView) findViewById(R.id.txtContentPost);
        imgview = (ImageView) findViewById(R.id.imgview);
        etPesan = (EditText) findViewById(R.id.etPesan);
        btnSend = (Button) findViewById(R.id.btnsend);

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("comment").child("-L3cWTIdUqHoHkFvI0ON");
        //userData = PrefUtils.getCurrentUser(this);
        mStorage = FirebaseStorage.getInstance().getReference();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

//        mAdapter = new CommentAdapter(movieList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(mAdapter);


        final List<Comment> items = new ArrayList<>();
        final List<String> itemkey = new ArrayList<>();
        timelineAdapter = new CommentAdapter(getApplicationContext(),items,itemkey);

        recyclerView.setAdapter(timelineAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        Comment movie = new Comment("Gaji", "4000000");
//        movieList.add(movie);
//
//        movie = new Comment("Bonus", "2500000");
//        movieList.add(movie);



        timelineAdapter.notifyDataSetChanged();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                title = null;
                content = null;
                image = null;

            } else {
                title= extras.getString("title");
                content= extras.getString("content");
                image= extras.getString("image");
                key = extras.getString("key");

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("comment_weekly").child(key);

                txtNamePost.setText(title);
                txtContentPost.setText(content);
                Picasso.with(getApplicationContext()).load(image).into(imgview);

//                final WorldPost post = new WorldPost();
//                post.username = title.toString();
//                post.content = content.toString();


                //String uid = databaseReference.push().getKey();
                //databaseReference.child("-L-tiWIXYYlP_2RLgJwP").child(uid).setValue(post);

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Comment> list = new ArrayList<>();
                        List<String> listkey = new ArrayList<>();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Log.d(TAG, "onDataChange xxxx: "+snapshot);
                            Comment p = snapshot.getValue(Comment.class);
                            list.add(p);
                            listkey.add(String.valueOf(snapshot.getKey()));
                        }
                        //Log.d(TAG, "onDataChange: datalist"+listkey.);
                        timelineAdapter.insertData(list,listkey);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };

                databaseReference.addValueEventListener(eventListener);
                timelineAdapter.notifyDataSetChanged();

                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userData  = PrefUtils.getCurrentUser(CommentWeeklyActivity.this);
                        final WorldPost post = new WorldPost();
                        post.username = userData.name.toString(); //title.toString();
                        post.content = etPesan.getText().toString();

                        String uid = databaseReference.push().getKey();
                        databaseReference.child(uid).setValue(post);
                        Log.d(TAG, "click click: "+userData.name.toString());
                    }
                });

            }
        } else {
            //newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
