package com.example.kylemcnee.apiandgsonlab;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by charlie on 3/1/16.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MarvelCharacter> mData;

    public CharacterAdapter(Context context, ArrayList<MarvelCharacter> characters) {
        mContext = context;
        mData = characters;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MarvelCharacter character = mData.get(position);
        holder.mName.setText(character.getName());

        String imagePath = character.getThumbnail().getPath();
        String imageExtension = character.getThumbnail().getExtension();

        Uri uri = Uri.parse(imagePath + imageExtension);
        holder.mImage.setImageURI(uri);
        holder.mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.character_image);
            mName = (TextView) itemView.findViewById(R.id.character_name);
        }
    }
}
