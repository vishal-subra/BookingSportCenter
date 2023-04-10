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

public class UserGroupRequestAdapter extends RecyclerView.Adapter<UserGroupRequestAdapter.MyHolder>{

    Context context;
    List<RequestGroupModel> bookingModels;

    public UserGroupRequestAdapter(Context context, List<RequestGroupModel> requestModelList) {
        this.context = context;
        this.bookingModels = requestModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_post_4, parent , false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String name = bookingModels.get(position).getReqSportName();
        String status = bookingModels.get(position).getReqStatus();
        String time = bookingModels.get(position).getReqTime();
        String uName = bookingModels.get(position).getReqAskedName();
        String place = bookingModels.get(position).getReqCourt();

        holder.showName.setText(name);
        holder.showStatus.setText(status);
        holder.showDate.setText(time);
        holder.showPlace.setText(place);
        holder.showUserName.setText(uName);

    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView showName,showStatus,showDate,showUserName,showPlace;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            showName =itemView.findViewById(R.id.ShowName);
            showStatus =itemView.findViewById(R.id.ShowStatus);
            showDate = itemView.findViewById(R.id.ShowDate);
            showUserName = itemView.findViewById(R.id.ShowUserName);
            showPlace = itemView.findViewById(R.id.ShowPlace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent UserViewRequestDetailsActivity = new Intent(context, UserViewRequestDetailsActivity.class);
                    int position = getAdapterPosition();

                    UserViewRequestDetailsActivity.putExtra("ReqTimestamp",bookingModels.get(position).getReqTimestamp());
                    UserViewRequestDetailsActivity.putExtra("ReqAskedUid",bookingModels.get(position).getReqAskedUid());

                    UserViewRequestDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(UserViewRequestDetailsActivity);

                }
            });

        }
    }

}


