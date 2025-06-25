package com.example.geims_navigation_2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.geims_navigation_2.DetailActivity;
import com.example.geims_navigation_2.Domain.DoctorsModel;
import com.example.geims_navigation_2.databinding.ViewholderTopDoctorBinding;

import java.util.List;

public class TopDoctorAdapter extends RecyclerView.Adapter<TopDoctorAdapter.Viewholder> {
    private final List<DoctorsModel> items;
    private Context context;

    public TopDoctorAdapter(List<DoctorsModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public TopDoctorAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderTopDoctorBinding binding = ViewholderTopDoctorBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
        );
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopDoctorAdapter.Viewholder holder, int position) {
        DoctorsModel doctorsModel = items.get(position);
        holder.binding.nameTxt.setText(doctorsModel.getName());
        holder.binding.specialTxt.setText(doctorsModel.getSpecial());
        holder.binding.ratingTxt.setText(doctorsModel.getRating()+" ");
        holder.binding.patientsText.setText(doctorsModel.getExpriense()+" years");

        Glide.with(holder.itemView.getContext())
                .load(doctorsModel.getPicture())
                .apply(new RequestOptions().transform(new CenterCrop()))
                .into(holder.binding.img);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("object", doctorsModel);
                context.startActivity(intent);
                Log.d("Adapter", "Clicked on: " + doctorsModel.getName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private final ViewholderTopDoctorBinding binding;

        public Viewholder(ViewholderTopDoctorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}





















