package com.example.a79875.todaynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.MesureWidgetHeight;
import com.example.a79875.todaynews.enity.Title;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MsgContentRecyclerViewAdapter extends RecyclerView.Adapter<MsgContentRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Title> titleList;
    private LayoutInflater layoutInflater;
    private OnItemClickListenter onItemClickListenter;

    public MsgContentRecyclerViewAdapter(Context context, List<Title> titleList) {
        this.context = context;
        this.titleList = titleList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        Title title = titleList.get(position);
        if (title.getTitle().equals("header") &&
                title.getImageUrl() == null
                && title.getUri() == null) {
            return 1;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == 2) {
            view = layoutInflater.inflate(R.layout.msg_content_recycler_view_item, viewGroup, false);
        } else {
            view = layoutInflater.inflate(R.layout.toast_layout, viewGroup, false);
        }
        return new ViewHolder(view,i);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Title title = titleList.get(i);
        if (getItemViewType(i) == 1){
            viewHolder.tipContent.setText(title.getDescription());
        }else {
            viewHolder.tvNewTitle.setText(title.getTitle());
            Glide.with(context).load(title.getImageUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .thumbnail(0.1f)
                    .into(viewHolder.ivNewPic);
            viewHolder.tvNewDescrip.setText(title.getDescription());
            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int[] locations = new int[2];
                    v.getLocationOnScreen(locations);// 当前点击位置位于屏幕的y坐标
                    onItemClickListenter.onItemDelete(i, viewHolder.ivDelete, locations[1]);
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListenter.onItemClick(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivNewPic;
        private ImageView ivDelete;
        private TextView tvNewTitle;
        private TextView tvNewDescrip;
        private View view;

        private TextView tipContent;

        public ViewHolder(@NonNull View itemView,int i) {
            super(itemView);
            this.view = itemView;
            if (i == 2) {
                this.ivDelete = view.findViewById(R.id.iv_delete_news);
                this.ivNewPic = view.findViewById(R.id.title_pic);
                this.tvNewTitle = view.findViewById(R.id.title_text);
                this.tvNewDescrip = view.findViewById(R.id.descr_text);
            }else {
                this.tipContent = view.findViewById(R.id.toast_content);
            }
        }
    }

    public void setOnItemClickListenter(OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }

    public interface OnItemClickListenter {
        void onItemClick(int position);

        void onItemDelete(int position, View view, int currentY);
    }
}
