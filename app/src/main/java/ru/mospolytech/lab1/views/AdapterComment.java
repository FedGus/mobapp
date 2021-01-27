package ru.mospolytech.lab1.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.api.ApiConfiguration;
import ru.mospolytech.lab1.api.ApiInterface;
import ru.mospolytech.lab1.classes.Images;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    Context context;
    List<SerializerComment> list;
    List<Images> listimg;

    ApiInterface api;
    CompositeDisposable disposables;

    public AdapterComment(Context context, List<SerializerComment> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SerializerComment serializerComment = list.get(position);
        listimg = new ArrayList<>();
        listimg.clear();

        holder.dateNews.setText(serializerComment.content);


        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        disposables.add(api.commentAuthor(serializerComment.id_comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((commentAuthor) -> {
                    holder.factIdText.setText(commentAuthor.name + " " + commentAuthor.surname);
                }, (error) -> {

                }));
//
//        Log.d(TAG, "onBindViewHolder: " + listimg.addAll(news.image));
        //Glide.with(context).load( comment.image+ "").into(holder.factImage);
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
