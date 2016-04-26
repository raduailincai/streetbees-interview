package ailincai.radu.raduailincai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ailincai.radu.raduailincai.R;

public class ComicsAdapter extends BaseAdapter {

    private final ArrayList<ComicWrapper> comics;

    public ComicsAdapter(ArrayList<ComicWrapper> comics) {
        this.comics = comics;
    }

    @Override
    public int getCount() {
        return comics.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_comic_view, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.logoView = (ImageView) convertView.findViewById(R.id.custom_comic_view_logo);
            holder.titleView = (TextView) convertView.findViewById(R.id.custom_comic_view_title);
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final ComicWrapper currentComicWrapper = comics.get(position);
        holder.titleView.setText(currentComicWrapper.getTitle());
        Picasso.with(context)
                .load(currentComicWrapper.getImageUrl())
                .into(holder.logoView);
        return convertView;
    }

    private class ViewHolder {

        TextView titleView;
        ImageView logoView;

    }
}
