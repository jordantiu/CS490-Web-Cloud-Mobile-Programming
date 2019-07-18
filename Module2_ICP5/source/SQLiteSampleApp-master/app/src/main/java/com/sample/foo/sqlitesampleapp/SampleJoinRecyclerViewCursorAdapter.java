package com.sample.foo.sqlitesampleapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.foo.sqlitesampleapp.databinding.EmployeeListItemBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by obaro on 26/09/2016.
 */

public class SampleJoinRecyclerViewCursorAdapter extends RecyclerView.Adapter<SampleJoinRecyclerViewCursorAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;

    public SampleJoinRecyclerViewCursorAdapter(Context context, Cursor cursor) {

        mContext = context;
        mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EmployeeListItemBinding itemBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBinding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        mCursor.moveToPosition(pos);

                        String id = mCursor.getString(mCursor.getColumnIndex(SampleDBContract.Employee.EMPLOYEE_ID));
                        Intent intent = new Intent(mContext, EmployeeModifyActivity.class);
                        intent.putExtra("id", id);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        public void bindCursor(Cursor cursor) {
            itemBinding.firstnameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_FIRSTNAME)
            ));
            itemBinding.lastnameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_LASTNAME)
            ));
            itemBinding.jobDescLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION)
            ));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH)));
            itemBinding.dobLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

            itemBinding.nameLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_NAME)
            ));
            itemBinding.descLabel.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_DESCRIPTION)
            ));

            calendar.setTimeInMillis(cursor.getLong(
                    cursor.getColumnIndexOrThrow(SampleDBContract.Employer.COLUMN_FOUNDED_DATE)));
            itemBinding.foundedLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.employee_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}