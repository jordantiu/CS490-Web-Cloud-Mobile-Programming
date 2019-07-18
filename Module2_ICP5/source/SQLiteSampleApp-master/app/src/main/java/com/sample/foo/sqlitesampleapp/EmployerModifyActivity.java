package com.sample.foo.sqlitesampleapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sample.foo.sqlitesampleapp.databinding.ActivityEmployerModifyBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployerModifyActivity extends AppCompatActivity {

    private static final String TAG = "EmployerModifyActivity";
    private ActivityEmployerModifyBinding binding;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employer_modify);

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();

        String query = "SELECT * from " + SampleDBContract.Employer.TABLE_NAME
                + " WHERE " + SampleDBContract.Employer._ID + " = ?";

        id = getIntent().getStringExtra("id");
        Cursor cursor = database.rawQuery(query , new String[] {id});

        cursor.moveToFirst();

        binding.nameEditText.setText(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employer.COLUMN_NAME)));
        binding.descEditText.setText(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employer.COLUMN_DESCRIPTION)));

        Date date = new Date(Long.valueOf(cursor.getString(cursor.getColumnIndex(SampleDBContract.Employer.COLUMN_FOUNDED_DATE))));
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateFormatted = formatter.format(date);

        binding.foundedEditText.setText(dateFormatted);
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

        database.delete(SampleDBContract.Employer.TABLE_NAME,
                SampleDBContract.Employer._ID + " = ?", new String[] {id});

        Toast.makeText(this, "Deleted item " + id, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void update() {
        String name = binding.nameEditText.getText().toString();
        String desc = binding.descEditText.getText().toString();
        long date = 0;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    binding.foundedEditText.getText().toString()));
            date = calendar.getTimeInMillis();
        }
        catch (Exception e) {}

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SampleDBContract.Employer.COLUMN_NAME, name);
        contentValues.put(SampleDBContract.Employer.COLUMN_DESCRIPTION, desc);
        contentValues.put(SampleDBContract.Employer.COLUMN_FOUNDED_DATE, date);

        Integer result = database.update(SampleDBContract.Employer.TABLE_NAME, contentValues,
                SampleDBContract.Employer._ID + " = ?", new String[] {id});

        Toast.makeText(this, "Updated item " + id, Toast.LENGTH_SHORT).show();
        finish();
    }
}
