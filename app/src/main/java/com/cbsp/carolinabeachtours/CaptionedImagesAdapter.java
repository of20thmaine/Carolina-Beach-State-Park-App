package com.cbsp.carolinabeachtours;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private List<Location> locations;
    private Listener listener;

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

    CaptionedImagesAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    void setListener(Listener listener) {
        this.listener = listener;
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

        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(),
                locations.get(position).getImageId());

        imageView.setImageDrawable(drawable);
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
