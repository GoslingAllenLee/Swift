package wartaonline.chat.chatapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import wartaonline.chat.chatapp.AddThreadActivity;
import wartaonline.chat.chatapp.R;
import wartaonline.chat.chatapp.adapters.ThreadAdapter;
import wartaonline.chat.chatapp.models.Post;
import wartaonline.chat.chatapp.models.User;
import wartaonline.chat.chatapp.utils.PrefUtils;

import static android.content.ContentValues.TAG;


public class ThreadFragment extends Fragment {
    private EditText title,content,txtsearch;
    private Button btnsave;


    private RecyclerView recyclerView;
    private ThreadAdapter threadAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User userData;

    List<Post> list = new ArrayList<>();
    List<String> listkey = new ArrayList<>();

    List<Post> items = new ArrayList<>();
    List<String> itemkey = new ArrayList<>();

    public ThreadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thread, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        txtsearch = (EditText) view.findViewById(R.id.txtsearch);

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


        createdata();

        threadAdapter = new ThreadAdapter(getActivity(),items,itemkey);
        recyclerView.setAdapter(threadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                List<Post> list = new ArrayList<>();
////                List<String> listkey = new ArrayList<>();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Post p = snapshot.getValue(Post.class);
//                    list.add(p);
//                    listkey.add(String.valueOf(snapshot.getKey()));
//                }
//
//                threadAdapter.insertData(list,listkey);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };

//        databaseReference.addValueEventListener(eventListener);
//        threadAdapter.notifyDataSetChanged();

        txtsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //threadAdapter.filter((String) cs);
                //ThreadFragment.this.threadAdapter.filter((String) cs);

                final List<Post> filteredModelList = filter(list, cs.toString());
                if (filteredModelList.size() > 0) {
                    threadAdapter.insertData(filteredModelList,);
                    //return true;
                } else {
                    Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
                    //return false;
                }
                Log.d(TAG, "onTextChanged: "+cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }

    private List<Post> filter(List<Post> models, String query) {
        query = query.toLowerCase();

        final List<Post> filteredModelList = new ArrayList<>();
        for (Post model : models) {
            final String text = model.title.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        createdata();
        threadAdapter = new ThreadAdapter(getActivity(),items,itemkey);
        recyclerView.setAdapter(threadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        threadAdapter.notifyDataSetChanged();
        return filteredModelList;
    }

    void createdata() {
          ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
    }


    void filter(String text){
        databaseReference.child("Users").orderByChild("content").equalTo(text).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post user = dataSnapshot.getChildren().iterator().next().getValue(Post.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        List<Post> temp = new ArrayList();
//        for(Post d: list){
//            //or use .equal(text) with you want equal match
//            //use .toLowerCase() for better matches
//            if(d.key.toLowerCase().contains(text)){
//                temp.add(d);
//            }
//        }
//        //update recyclerview
//        threadAdapter.updateList(temp);
//
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Post> list = new ArrayList<>();
//                List<String> listkey = new ArrayList<>();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Post p = snapshot.getValue(Post.class);
//                    list.add(p);
//                    listkey.add(String.valueOf(snapshot.getKey()));
//                }
//
//                threadAdapter.insertData(list,listkey);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        databaseReference.addValueEventListener(eventListener);
//        threadAdapter.notifyDataSetChanged();

    }

}
