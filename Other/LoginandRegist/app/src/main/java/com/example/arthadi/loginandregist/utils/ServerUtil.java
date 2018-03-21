package com.example.arthadi.loginandregist.utils;

import android.content.Context;
import android.util.Log;

import com.example.arthadi.loginandregist.R;
import com.example.arthadi.loginandregist.models.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Yohanes Himawan K on 7/11/2017.
 */

public class ServerUtil {
    public interface ResponseListener{
        public void onFailure(String message);
        public void onResponse(boolean success, String message);
    }

    public static void deleteTrx(final Context context, Transaction transaction, final ResponseListener listener){
        OkHttpClient client = new OkHttpClient();
        MultipartBody responesBody = new MultipartBody.Builder()
                .setType( MultipartBody.FORM)
                .addFormDataPart("id",Integer.toString(transaction.id))
                .build();

        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url)+ "transaction/delete")
                .method("POST",RequestBody.create(null, new byte[0]))
                .post(responesBody)
                .build();

        PopupUtil.showLoading(context,"","Please wait ...");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                PopupUtil.dismissDialog();
                //PopupUtil.showMsg(context, "Error saving transaction", PopupUtil.SHORT);
                if(listener !=null){
                    listener.onFailure(e.getMessage());
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();
                String body = response.body().string();
                Log.d("ServerUtil", "Response" + body);

                try{
                    JSONObject jsonObject = new JSONObject(body);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    // PopupUtil.showMsg(context,message, PopupUtil.SHORT);

                    if (listener !=null){
                        listener.onResponse(success,message);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
    }

    public static void saveTrx (final Context context, Transaction transaction, final ResponseListener listener){
        if (transaction.subCategory == null){
            transaction.subCategory = "";
        }
        if (transaction.note == null){
            transaction.note = "";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(transaction.transactionDate);

        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType( MultipartBody.FORM)
                .addFormDataPart("user_id","1")
                .addFormDataPart("cat",transaction.category)
                .addFormDataPart("sub_cat", transaction.subCategory)
                .addFormDataPart("amount",transaction.amount + "")
                .addFormDataPart("type",transaction.type)
                .addFormDataPart("note", transaction.note)
                .addFormDataPart("created_at", DateUtil.formatMySql(calendar.getTime()));


        if (transaction.id > 0){
            builder.addFormDataPart("id",Integer.toString(transaction.id));
        }

        Request request = new Request.Builder()
                .url(context.getString(R.string.base_url)+ "transaction/insert")
                .method("POST",RequestBody.create(null, new byte[0]))
                .post(builder.build())
                .build();

        PopupUtil.showLoading(context,"","Please wait ...");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                PopupUtil.dismissDialog();
                //PopupUtil.showMsg(context, "Error saving transaction", PopupUtil.SHORT);
                if(listener !=null){
                    listener.onFailure(e.getMessage());
                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();
                String body = response.body().string();
                Log.d("ServerUtil", "Response" + body);

                try{
                    JSONObject jsonObject = new JSONObject(body);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                   // PopupUtil.showMsg(context,message, PopupUtil.SHORT);

                    if (listener !=null){
                        listener.onResponse(success,message);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }
}
