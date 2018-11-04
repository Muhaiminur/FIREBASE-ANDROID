package com.muhaiminur.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class Firebase_Image_adapter extends RecyclerView.Adapter<Firebase_Image_adapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";
    private ArrayList<Image> image = new ArrayList<>();
    private Context mContext;
    public Firebase_Image_adapter(Context context, ArrayList<Image> i) {
        image=i;
        mContext = context;
    }

    @Override
    public Firebase_Image_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.normal_list_item, parent, false);
        return new Firebase_Image_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Firebase_Image_adapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(image.get(position).getImage_url())
                .apply(requestOptions)
                .into(holder.image);

        holder.name.setText(image.get(position).getImage_description());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + image.get(position).getImage_description());
                Toast.makeText(mContext, image.get(position).getImage_description(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.count.setText(image.get(position).getImage_count()+"+");
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                new Send_data_task(image.get(position).getId()).execute("my string parameter");
                /*int n=Integer.parseInt(image.get(position).getImage_count())+1;
                mDatabase.setValue(n+"");*/
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView count;
        LikeButton likeButton;
        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
            this.count=itemView.findViewById(R.id.love_result);
            this.likeButton=itemView.findViewById(R.id.love_button);
        }
    }

    private class Send_data_task extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog4;

        // Runs in UI before background thread is called
        String sendData;

        Send_data_task(String s){
            sendData=s;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Do something like display a progress bar
            progressDialog4 = ProgressDialog.show(mContext,"Sending Report","Please Wait!");

            progressDialog4.setCanceledOnTouchOutside(false);
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            try {

                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Image_Array").child(sendData).child("love_count");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        image.clear();
                        //Image totalLikes = dataSnapshot.getValue(Image.class);
                        String totalLikes = dataSnapshot.getValue(String.class);
                        int n=Integer.parseInt(totalLikes)+1;
                        //Image totalLikes =(Image) dataSnapshot.getValue();
                        mDatabase.setValue(n+"");
                        Log.d(TAG,n+"");
                        //notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }catch (Exception e){
                Log.d("Paisi send data 2",e.toString());
            }

            return "this string is passed to onPostExecute";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Do things like hide the progress bar or change a TextView
            progressDialog4.dismiss();
        }
    }
}
