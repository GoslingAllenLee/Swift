package wartaonline.chat.chatapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import wartaonline.chat.chatapp.R;
import wartaonline.chat.chatapp.models.Message;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;


public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.ViewHolder> {
    private List<Message> items;
    private Context context;

    private final static int ME_VIEW = 0;
    private final static int OTHER_VIEW = 1;

    public ChatGroupAdapter(Context activity, List<Message> items){
        this.context = activity;
        this.items = items;
    }


    public void insertData(List<Message> data){
        this.items = data;
        notifyDataSetChanged();
    }

    public  void addData(Message chat){
        this.items.add(chat);
        notifyItemInserted(items.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = 0;
        switch (viewType) {
            case ME_VIEW:
                layoutRes = R.layout.item_chat_me;
                break;
            case OTHER_VIEW:
                layoutRes = R.layout.item_chat_other;
                break;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        Message msg = items.get(position);
        switch(msg.tipe) {
            case 0:
                return ME_VIEW;
            default:
                return OTHER_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message item = items.get(position);

        holder.tvname.setText(item.name);
        holder.tvmessage.setText(item.message);
        holder.tvtanggal.setText(item.tanggal);
        Picasso.with(context).load(item.avatar).into(holder.images);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvname,  tvtanggal;
        private ImageView images;
        private EmojiconTextView tvmessage;
        //private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tvname);
            tvmessage = (EmojiconTextView) itemView.findViewById(R.id.tvmessage);
            tvtanggal = (TextView) itemView.findViewById(R.id.tvtanggal);

            images = (ImageView) itemView.findViewById(R.id.images);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(context, "isi " + items.get(getAdapterPosition()).name, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ChatGroupActivity.class);
                    intent.putExtra("room", items.get(getAdapterPosition()).message);
                    context.startActivity(intent);

                }
            });*/
        }
    }
}