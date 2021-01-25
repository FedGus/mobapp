package ru.mospolytech.lab1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<ProductDetail> list;
    List<Images> listimg;
    ApiInterface api;
    private CompositeDisposable disposables;

    public ListAdapter(Context context, List<ProductDetail> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_petition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetail news = list.get(position);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        holder.factIdText.setText(news.title);
        String dtStart = news.timestamp;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dtStart);
            holder.sourceView.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.dateNews.setText(news.address);

        Glide.with(context).load( "http://comfortable-city.std-709.ist.mospolytech.ru/api/photo/" + news.filename).into(holder.factImage);

       
//        Log.d(TAG, "onBindViewHolder: " + listimg.addAll(news.image));



        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, PetitionActivity.class);
            intent.putExtra("id_petition", news.id_petition);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView factImage;
        TextView factIdText;
        TextView dateNews;
        TextView sourceView;
        LinearLayout item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            factImage = itemView.findViewById(R.id.newsImage);
            factIdText = itemView.findViewById(R.id.commentAuthor);
            sourceView = itemView.findViewById(R.id.timeStamp);
            dateNews = itemView.findViewById(R.id.commentContent);
            item = itemView.findViewById(R.id.item);
        }
    }
}
