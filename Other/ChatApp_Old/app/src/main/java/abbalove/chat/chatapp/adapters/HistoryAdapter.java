package abbalove.chat.chatapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import abbalove.chat.chatapp.ChatActivity;
import abbalove.chat.chatapp.R;
import abbalove.chat.chatapp.models.Message;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Message> items;
    private Context context;


    public HistoryAdapter(Context activity, List<Message> items){
        this.context = activity;
        this.items = items;
    }


    public void insertData(List<Message> data){
        this.items = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message item = items.get(position);
        holder.tvroomname.setText(item.name);
        holder.tvcreatedby.setText(item.message);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvroomname, tvcreatedby;

        public ViewHolder(View itemView) {
            super(itemView);
            tvroomname = (TextView) itemView.findViewById(R.id.tvroomname);
            tvcreatedby = (TextView) itemView.findViewById(R.id.tvcreatedby);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("uid", items.get(getAdapterPosition()).uid);
                    intent.putExtra("name", items.get(getAdapterPosition()).name);
                    context.startActivity(intent);

                }
            });

        }
    }
}