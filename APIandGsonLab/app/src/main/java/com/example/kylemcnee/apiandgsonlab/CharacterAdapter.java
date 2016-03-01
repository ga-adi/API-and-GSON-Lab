package com.example.kylemcnee.apiandgsonlab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
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

        GetImageAsyncTask task = new GetImageAsyncTask(holder);
        task.execute(imagePath + "." + imageExtension);
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

    private class GetImageAsyncTask extends AsyncTask<String, Void, Drawable> {
        private ViewHolder mViewHolder;

        public GetImageAsyncTask(ViewHolder viewHolder) {
            mViewHolder = viewHolder;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                InputStream content = (InputStream) url.getContent();
                return Drawable.createFromStream(content, "src");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            mViewHolder.mImage.setImageDrawable(drawable);
            mViewHolder.mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}
