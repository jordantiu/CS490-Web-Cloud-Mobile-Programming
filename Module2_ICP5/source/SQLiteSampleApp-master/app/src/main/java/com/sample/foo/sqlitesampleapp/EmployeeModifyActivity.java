package com.sample.foo.sqlitesampleapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sample.foo.sqlitesampleapp.databinding.ActivityEmployeeModBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeModifyActivity extends AppCompatActivity {

    private static final String TAG = "EmployeeModifyActivity";
    private ActivityEmployeeModBinding binding;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_mod);

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();

        String[] queryCols = new String[]{"_id", SampleDBContract.Employer.COLUMN_NAME};
        String[] adapterCols = new String[]{SampleDBContract.Employer.COLUMN_NAME};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        Cursor employerCursor = database.query(
                SampleDBContract.Employer.TABLE_NAME,     // The table to query
                queryCols,                                // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item, employerCursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.employerSpinner.setAdapter(cursorAdapter);

        id = getIntent().getStringExtra("id");

        Cursor cursor = database.rawQuery(SampleDBContract.SELECT_EMPLOYEE_BY_ID, new String[] {id});

        cursor.moveToFirst();

        binding.firstnameEditText.setText(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employee.COLUMN_FIRSTNAME)));
        binding.lastnameEditText.setText(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employee.COLUMN_LASTNAME)));

        binding.jobDescEditText.setText(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION)));

        Date date = new Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH))));
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormatted = formatter.format(date);
        binding.dobEditText.setText(dateFormatted);

        date = new Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employee.COLUMN_EMPLOYED_DATE))));
        dateFormatted = formatter.format(date);
        binding.employedEditText.setText(dateFormatted);

        int employerID = Integer.valueOf(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employee.COLUMN_EMPLOYER_ID)));

        for (int i = 0; i < binding.employerSpinner.getCount(); i++ ) {
            if (((Cursor)binding.employerSpinner.getItemAtPosition(i)).getInt(0) == employerID) {
                binding.employerSpinner.setSelection(i);
            }
        }
        cursor.close();

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void delete() {
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getWritableDatabase();

        database.delete(SampleDBContract.Employee.TABLE_NAME,
                SampleDBContract.Employee.EMPLOYEE_ID + " = ?", new String[] {id});

        Toast.makeText(this, "Deleted item " + id, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void update() {
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SampleDBContract.Employee.COLUMN_FIRSTNAME, binding.firstnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_LASTNAME, binding.lastnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION, binding.jobDescEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_EMPLOYER_ID,
                ((Cursor)binding.employerSpinner.getSelectedItem()).getInt(0));

        Log.d("getINT", ((Cursor)binding.employerSpinner.getSelectedItem()).getInt(0) + "");
        Log.d("getColumnName", ((Cursor)binding.employerSpinner.getSelectedItem()).getColumnName(0));

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    binding.dobEditText.getText().toString()));
            long date = calendar.getTimeInMillis();
            values.put(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH, date);

            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    binding.employedEditText.getText().toString()));
            date = calendar.getTimeInMillis();
            values.put(SampleDBContract.Employee.COLUMN_EMPLOYED_DATE, date);
        }
        catch (Exception e) {
            Log.e(TAG, "Error", e);
            Toast.makeText(this, "Date is in the wrong format", Toast.LENGTH_LONG).show();
            return;
        }

        Integer result = database.update(SampleDBContract.Employee.TABLE_NAME, values,
                SampleDBContract.Employee.EMPLOYEE_ID + " = ?", new String[] {id});

        Toast.makeText(this, "Updated item " + id, Toast.LENGTH_SHORT).show();
        finish();
    }
}
