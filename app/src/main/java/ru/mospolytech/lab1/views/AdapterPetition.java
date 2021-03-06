package ru.mospolytech.lab1.views;

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
import ru.mospolytech.lab1.R;
import ru.mospolytech.lab1.api.ApiConfiguration;
import ru.mospolytech.lab1.api.ApiInterface;
import ru.mospolytech.lab1.classes.Images;

public class AdapterPetition extends RecyclerView.Adapter<AdapterPetition.ViewHolder> {

    Context context;
    List<SerializerPetitionDetail> list;
    List<Images> listimg;
    ApiInterface api;
    private CompositeDisposable disposables;

    public AdapterPetition(Context context, List<SerializerPetitionDetail> list){
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
        SerializerPetitionDetail petition = list.get(position);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        holder.petitionIdText.setText(petition.title);
        String dtStart = petition.timestamp;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dtStart);
            holder.sourceView.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.datePetition.setText(petition.address);

        Glide.with(context).load( "http://comfortable-city.std-709.ist.mospolytech.ru/api/photo/" + petition.filename).into(holder.petitionImage);

        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityPetition.class);
            intent.putExtra("id_petition", petition.id_petition);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView petitionImage;
        TextView petitionIdText;
        TextView datePetition;
        TextView sourceView;
        LinearLayout item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petitionImage = itemView.findViewById(R.id.petitionImage);
            petitionIdText = itemView.findViewById(R.id.commentAuthor);
            sourceView = itemView.findViewById(R.id.timeStamp);
            datePetition = itemView.findViewById(R.id.commentContent);
            item = itemView.findViewById(R.id.item);
        }
    }
}
