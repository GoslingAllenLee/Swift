package abbalove.chat.chatapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import abbalove.chat.chatapp.ChatGroupActivity;
import abbalove.chat.chatapp.R;
import abbalove.chat.chatapp.WorldDetailPostActivity;
import abbalove.chat.chatapp.models.Post;
import abbalove.chat.chatapp.models.PostKey;
import abbalove.chat.chatapp.models.User;

import static android.content.ContentValues.TAG;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private RecyclerView recyclerView;
    private TimelineAdapter timelineAdapter;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private User userData;

    private StorageReference storageReference;

    private List<Post> items;
    private List<String> itemkey;
    private Context context;

    private Uri mimageUri = null;

//    public interface OnItemClickListener {
//        void onItemClick(View v, int position);
//    }
//
//    private final OnItemClickListener listener;


//    public TimelineAdapter(Context activity, List<Post> items, OnItemClickListener listener){
//        this.context = activity;
//        this.items = items;
//        this.listener = listener;
//    }

    public TimelineAdapter(Context activity, List<Post> items, List<String> itemkey){
        this.context = activity;
        this.items = items;
        this.itemkey = itemkey;
    }

    public void insertData(List<Post> data, List<String> datakey){
        this.items = data;
        this.itemkey = datakey;
        Collections.reverse(this.items);
        Collections.reverse(this.itemkey);

        //Log.d(TAG, "datanya: "+datakey.get(1).key);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_room, parent, false);
//        final ViewHolder vh = new ViewHolder(v);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onItemClick(v, vh.getPosition());
//            }
//        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post item = items.get(position);
        //PostKey itemkey2 = itemkey.get(position);
        holder.txtkey.setText(this.itemkey.get(position));
        holder.tvroomname.setText(item.title);
        holder.tvcreatedby.setText(item.content);

        Picasso.with(context).load(item.image).into(holder.imgview);
        holder.itemView.setTag(item);
    }

//    @Override
//    public int getItemCount() {
//        return itemkey.size();
//    }

    @Override
    public int getItemCount() {
        return itemkey.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvroomname, tvcreatedby, txtkey;
        private ImageView imgview;

        public ViewHolder(View itemView) {
            super(itemView);
            tvroomname = (TextView) itemView.findViewById(R.id.tvroomname);
            tvcreatedby = (TextView) itemView.findViewById(R.id.tvcreatedby);
            txtkey = (TextView) itemView.findViewById(R.id.txtkey);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WorldDetailPostActivity.class);
                    intent.putExtra("title", items.get(getAdapterPosition()).title);
                    intent.putExtra("content", items.get(getAdapterPosition()).content);
                    intent.putExtra("image", items.get(getAdapterPosition()).image);
                    intent.putExtra("key", itemkey.get(getAdapterPosition()));
                    context.startActivity(intent);


                    TextView x = (TextView) view.findViewById(R.id.txtkey);
                    String your_text = x.getText().toString();



                    Log.d(TAG, "onClick: " + itemkey.get(getAdapterPosition()));
                    //Log.d(TAG, "onClick: " + itemkey.get(getAdapterPosition()).key);
                    Log.d(TAG, "clicked position2:" + items.get(getAdapterPosition()).title);
                }
            });

        }

    }

}