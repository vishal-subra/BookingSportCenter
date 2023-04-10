package com.example.sportcenterv2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserBookingAdapter extends RecyclerView.Adapter<UserBookingAdapter.MyHolder>{

    Context context;
    List<BookingModel> bookingModels;

    public UserBookingAdapter(Context context, List<BookingModel> requestModelList) {
        this.context = context;
        this.bookingModels = requestModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_post, parent , false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String name = bookingModels.get(position).getDoName();
        String status = bookingModels.get(position).getDoStatus();
        String date = bookingModels.get(position).getDoDate();

        holder.showName.setText(name);
        holder.showStatus.setText(status);
        holder.showDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView showName,showStatus,showDate;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            showName =itemView.findViewById(R.id.ShowName);
            showStatus =itemView.findViewById(R.id.ShowStatus);
            showDate = itemView.findViewById(R.id.ShowDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent UserViewBookingDetailsActivity = new Intent(context,UserViewBookingDetailsActivity.class);
                    int position = getAdapterPosition();

                    UserViewBookingDetailsActivity.putExtra("DoTimestamp",bookingModels.get(position).getDoTimestamp());
                    UserViewBookingDetailsActivity.putExtra("DoUid",bookingModels.get(position).getDoUid());

                    UserViewBookingDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(UserViewBookingDetailsActivity);

                }
            });

        }
    }

}


