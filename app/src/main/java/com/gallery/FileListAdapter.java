package com.gallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gallery.bean.FileCountBean;

import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private List<FileCountBean> mData;
    private Context mContext;

    public FileListAdapter(Context context, List<FileCountBean> files) {
        mData = files;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.file_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.fileName.setText(mData.get(position).getName());
        holder.count.setText(mData.get(position).getCount() + "");
        holder.file_path.setText(mData.get(position).getPath());
        holder.fileItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ThreadShowActivity.class);
                intent.putExtra("path", mData.get(position).getPath());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView fileName, file_path;
        TextView count;
        View fileItem;

        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_item_tv);
            count = itemView.findViewById(R.id.file_item_tv_count);
            fileItem = itemView.findViewById(R.id.file_item);
            file_path = itemView.findViewById(R.id.file_path);
        }
    }

}
