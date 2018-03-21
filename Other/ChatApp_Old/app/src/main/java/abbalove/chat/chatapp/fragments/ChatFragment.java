package abbalove.chat.chatapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import abbalove.chat.chatapp.R;
import abbalove.chat.chatapp.adapters.HistoryAdapter;
import abbalove.chat.chatapp.models.Message;
import abbalove.chat.chatapp.models.User;
import abbalove.chat.chatapp.utils.PrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */

// chat fragment history

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference userRef;

    private User userData;
    private List<Message> list;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("chats");
        userRef = firebaseDatabase.getReference("users");
        userData = PrefUtils.getCurrentUser(getActivity());


        final List<Message> items = new ArrayList<>();
        historyAdapter = new HistoryAdapter(getActivity(),items);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();

        //narik data dari firebase
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //untuk mengambil chat terakhir
                String lastMassage = "";
                String name = "";

                //lop data
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.toString().contains(userData.uid)){


                        final String chatUid = snapshot.getKey().toString().substring(snapshot.getKey().toString().lastIndexOf("_") +1);

                        String[] parts = snapshot.getKey().toString().split("_");
                        Log.d("wkwkwk",parts.toString());

//                        for (String n : parts) {
//                            if (item == n) {
//                                return true;
//                            }
//                        }

                        for(DataSnapshot msg: snapshot.getChildren()){
                            if(msg.hasChildren()){
                                //ambil chat terakhir
                                Message message = msg.getValue(Message.class);
                                lastMassage = message.message;
                                name = message.name;
                            }
                        }


                        final Message history = new Message();
//                        history.name = getUsernameChat(chatUid);
                        history.message = lastMassage;
                        history.uid = chatUid;
                        history.name = chatUid;
//                        getUsernameChat(chatUid, history);
                        /*

                        Log.e("USER", "historiname : " + history.name);*/

                        list.add(history);


                    }
                }

                Log.e("CHAT", "count "+ list.size());
                final List<Message> list2 = new ArrayList<>();
                for (final Message msg: list){
                    Log.e("CHAT", "msg "+ msg.uid);
                    userRef.child(msg.uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                User user = dataSnapshot.getValue(User.class);
                                    list.clear();
                                Log.e("USER", "user : " + user.name);
                                if (user.uid.equals(msg.uid)) {
                                    //msg.name = user.name+"1";
                                    msg.name = snapshot.getValue().toString();
                                    list2.add(msg);
                                    break;
                                }
                                else{
                                    list2.add(msg);
                                    break;
                                }
                            }
                            historyAdapter.insertData(list2);
                            list.clear();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.orderByKey().addValueEventListener(eventListener);
        return view;
    }



}