package com.adventbiztech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adventbiztech.listener.PopupListener;
import com.adventbiztech.pojo.Player;
import com.adventbiztech.sportzinteractive_sat.R;

import java.util.ArrayList;

public class PLayerAdapter extends RecyclerView.Adapter<PLayerAdapter.PlayerViewHolder> {
    ArrayList<Player> arrayList;
    Context context;
    private static PopupListener popupListener;

    public PLayerAdapter(ArrayList<Player> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PLayerAdapter.PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_layou_player, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PLayerAdapter.PlayerViewHolder holder, int position) {
        Player player = arrayList.get(position);
        holder.tvPlayerName.setText(player.getP_name());
        holder.tvSrNo.setText(player.getSr_no());
        if (player.isIs_caption()){
            holder.ivCap.setVisibility(View.VISIBLE);
        }else{
            holder.ivCap.setVisibility(View.GONE);
        }
        if (player.isIs_wicket_keeper()){
            holder.ivWK.setVisibility(View.VISIBLE);
        }else{
            holder.ivWK.setVisibility(View.GONE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupListener!=null){
                    popupListener.click("Player Name : "+player.getP_name()+"\n\n"+
                            "Batting Style : "+player.getBatting_style()+"\n"+
                            "Bowling Style : "+player.getBowling_style());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView tvPlayerName,tvSrNo;
        ImageView ivCap,ivWK;
        CardView cardView;
        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            tvSrNo = itemView.findViewById(R.id.textViewPlayerNo);
            ivCap = itemView.findViewById(R.id.imageViewCap);
            ivWK = itemView.findViewById(R.id.imageViewWK);
            cardView = itemView.findViewById(R.id.cardViewPlayer);
        }
    }
    public  static void bindListener(PopupListener Listener){
        popupListener=Listener;
    }
}
