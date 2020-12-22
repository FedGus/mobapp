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

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<ProductDetail> list;
    List<Images> listimg;

    public ListAdapter(Context context, List<ProductDetail> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.petition_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetail news = list.get(position);
        listimg = new ArrayList<>();
        listimg.clear();
        holder.factIdText.setText(news.title);
        holder.sourceView.setText("Просмотров: "+ news.timestamp);
//        holder.dateNews.setText(news.price/100 + " ₽" );
//
//        Log.d(TAG, "onBindViewHolder: " + listimg.addAll(news.image));
        Glide.with(context).load( news.image+ "").into(holder.factImage);


        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
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
            factIdText = itemView.findViewById(R.id.newsIdText);
            sourceView = itemView.findViewById(R.id.sourceView);
            dateNews = itemView.findViewById(R.id.dateNews);
            item = itemView.findViewById(R.id.item);
        }
    }
}
