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

public class UserMatchAdapter extends RecyclerView.Adapter<UserMatchAdapter.MyHolder>{

    Context context;
    List<GroupModel> bookingModels;

    public UserMatchAdapter(Context context, List<GroupModel> requestModelList) {
        this.context = context;
        this.bookingModels = requestModelList;
    }

    @NonNull
    @Override
    public UserMatchAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_post_3, parent , false);
        return new UserMatchAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMatchAdapter.MyHolder holder, int position) {

        String name = bookingModels.get(position).getMaSportName();
        String status = bookingModels.get(position).getMaStatus();
        String time = bookingModels.get(position).getMaTime();

        holder.showName.setText(name);
        holder.showStatus.setText(status);
        holder.showDate.setText(time);

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
                    Intent UserViewMatchDetailsActivity = new Intent(context, UserViewMatchDetailsActivity.class);
                    int position = getAdapterPosition();

                    UserViewMatchDetailsActivity.putExtra("MaTimestamp",bookingModels.get(position).getMaTimestamp());
                    UserViewMatchDetailsActivity.putExtra("MaUid",bookingModels.get(position).getMaUid());

                    UserViewMatchDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(UserViewMatchDetailsActivity);

                }
            });

        }
    }

}


