package com.example.bookings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private List<Bookings> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView emailTextView;
        private final TextView dateTextView;
        private final TextView timeTextView;
        private final CheckBox selectCheckBox;

        public ViewHolder(View view) {
            super(view);
            emailTextView = view.findViewById(R.id.emailTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            timeTextView = view.findViewById(R.id.timeTextView);
            selectCheckBox = view.findViewById(R.id.selectCheckBox);
        }

        public void bindData(Bookings item) {
            emailTextView.setText(item.getEmail());
            dateTextView.setText(item.getDate());
            timeTextView.setText(item.getTime());
            selectCheckBox.setChecked(false); // Assuming you might want a dynamic state later
        }
    }

    public BookingsAdapter(List<Bookings> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bookings, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
