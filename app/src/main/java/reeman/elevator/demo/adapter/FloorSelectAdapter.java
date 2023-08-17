package reeman.elevator.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import reeman.elevator.demo.R;
import reeman.elevator.demo.model.resources.Buildings;


public class FloorSelectAdapter extends RecyclerView.Adapter<FloorSelectAdapter.BuildingSelectViewHolder> {

    private final List<String> dataList = new ArrayList<>();

    public void setDataList(List<String> newList) {
        this.dataList.clear();
        this.dataList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BuildingSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_building_select, parent, false);
        return new BuildingSelectViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(String floors);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingSelectViewHolder holder, int position) {
        holder.textView.setText(dataList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(dataList.get(holder.getBindingAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class BuildingSelectViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public BuildingSelectViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_building_select);
        }
    }
}
