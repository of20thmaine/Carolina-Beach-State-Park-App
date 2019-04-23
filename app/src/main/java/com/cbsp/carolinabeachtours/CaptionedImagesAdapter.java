package com.cbsp.carolinabeachtours;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private List<Location> locations;
    private Context ctx;
    private Listener listener;
    private final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    interface Listener {
       void onClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    CaptionedImagesAdapter(List<Location> locations, Context ctx) {
        this.locations = locations;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Lets me hot swap new data into recycler view.
     * @param newData: List of locations.
     */
    void swapDataSet(List<Location> newData){
        this.locations = newData;
        notifyDataSetChanged();
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);

        StorageReference image = mStorageRef.child(locations.get(position).getImageFile());

        GlideApp.with(ctx)
                .load(image)
                .into(imageView);

        imageView.setContentDescription(locations.get(position).getName());

        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(locations.get(position).getName());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

}
