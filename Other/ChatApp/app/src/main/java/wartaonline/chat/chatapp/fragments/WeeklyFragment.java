package wartaonline.chat.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import wartaonline.chat.chatapp.AddWeeklyActivity;
import wartaonline.chat.chatapp.R;
import wartaonline.chat.chatapp.adapters.WeeklyAdapter;
import wartaonline.chat.chatapp.models.Post;
import wartaonline.chat.chatapp.models.User;
import wartaonline.chat.chatapp.utils.PrefUtils;


public class WeeklyFragment extends Fragment {
    private EditText title,content;
    private Button btnsave;

    private RecyclerView recyclerView;
    private WeeklyAdapter weeklyAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User userData;

    public WeeklyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), AddWeeklyActivity.class);
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("weekly");
        userData = PrefUtils.getCurrentUser(getActivity());


        final List<Post> items = new ArrayList<>();
        final List<String> itemkey = new ArrayList<>();
        weeklyAdapter = new WeeklyAdapter(getActivity(),items,itemkey);
        recyclerView.setAdapter(weeklyAdapter);
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

                weeklyAdapter.insertData(list,listkey);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(eventListener);
        weeklyAdapter.notifyDataSetChanged();
        return view;
    }


}
