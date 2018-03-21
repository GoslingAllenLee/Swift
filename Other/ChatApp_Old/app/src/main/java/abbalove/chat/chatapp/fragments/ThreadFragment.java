package abbalove.chat.chatapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import abbalove.chat.chatapp.AddPostActivity;
import abbalove.chat.chatapp.AddThreadActivity;
import abbalove.chat.chatapp.R;
import abbalove.chat.chatapp.adapters.ThreadAdapter;
import abbalove.chat.chatapp.adapters.TimelineAdapter;
import abbalove.chat.chatapp.models.Post;
import abbalove.chat.chatapp.models.Thread;
import abbalove.chat.chatapp.models.User;
import abbalove.chat.chatapp.utils.PrefUtils;

import static android.content.ContentValues.TAG;


public class ThreadFragment extends Fragment {
    private EditText title,content;
    private Button btnsave;

    private RecyclerView recyclerView;
    private ThreadAdapter threadAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User userData;

    public ThreadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thread, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), AddThreadActivity.class);
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("threads");
        userData = PrefUtils.getCurrentUser(getActivity());


        final List<Post> items = new ArrayList<>();
        final List<String> itemkey = new ArrayList<>();
        threadAdapter = new ThreadAdapter(getActivity(),items,itemkey);
        recyclerView.setAdapter(threadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> list = new ArrayList<>();
                List<String> listkey = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post p = snapshot.getValue(Post.class);
                    list.add(p);
                    listkey.add(String.valueOf(snapshot.getKey()));
                }

                threadAdapter.insertData(list,listkey);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(eventListener);
        threadAdapter.notifyDataSetChanged();
        return view;
    }


}
