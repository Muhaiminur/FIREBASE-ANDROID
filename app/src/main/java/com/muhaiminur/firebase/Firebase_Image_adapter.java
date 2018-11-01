package com.muhaiminur.firebase;

import android.content.Context;
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

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
        }
    }
}
