package abbalove.chat.chatapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import abbalove.chat.chatapp.ChatActivity;
import abbalove.chat.chatapp.R;
import abbalove.chat.chatapp.models.User;
import abbalove.chat.chatapp.utils.PrefUtils;



public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private List<User> items;
    private Context context;
    private DatabaseReference mref;
    public Boolean isCreator;


    public MemberAdapter(List<User> users, Context context,DatabaseReference mref, Boolean isCreator){
        this.items = users;
        this.context = context;
        this.mref = mref;
        this.isCreator = isCreator;
    }

    public void insertData(List<User> data){
        this.items = data;
        notifyDataSetChanged();
    }


    //tempelin data
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }

    //set data
    @Override
    public void onBindViewHolder(MemberAdapter.ViewHolder holder, int position) {
        User user = items.get(position);
        holder.tvusername.setText(user.name);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvusername;


        public ViewHolder(View itemView) {
            super(itemView);

            tvusername = (TextView) itemView.findViewById(R.id.tvusername);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showActionDialog(items.get(getAdapterPosition()).uid,items.get(getAdapterPosition()).name);
                }
            });
        }
    }

    public void showActionDialog(final String uid, final String name){

        //validasi action member

        User user = PrefUtils.getCurrentUser(context);

        List<String> action = new ArrayList<>();
        if(!user.uid.equals(uid)){
            //si pembuat grup bisa accept dan remove tetapi di luar itu hanya chat
            if(isCreator){
                action.add("Accept");
                action.add("Remove");
            }
            action.add("Chat");
        }

        final CharSequence[] arrays = action.toArray(new String[action.size()]);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        dialogbuilder.setTitle("Action : ");

        dialogbuilder.setItems(arrays, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedText = arrays[which].toString();
                // jika di klik accept
                if(selectedText.equals("Accept")){
                    mref.child(uid).setValue(true);
                }
                if (selectedText.equals("Remove")){
                    mref.child(uid).removeValue();
                }

                if (selectedText.equals("Chat")){

                    // jika di klik chat
                    Intent intent = new Intent(context, ChatActivity.class);
                    // kirim data
                    intent.putExtra("uid",uid);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                }

            }
        });

        AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.show();
    }

}
