package com.example.database.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.database.R;

public class AddEdit_LoadActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.database.EXTRA_ID";

    public static final String EXTRA_TITLE = "com.example.database.EXTRA_TITLE";
    public static final String EXTRA_DESC = "com.example.database.EXTRA_DESC";
    public static final String EXTRA_PRIO = "com.example.database.EXTRA_PRIO";
    private EditText edit_title;
    private EditText edit_desc;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__load);

        edit_desc = findViewById(R.id.edit_desc);
        edit_title = findViewById(R.id.edit_title);

        numberPicker = findViewById(R.id.numberpicker_Priority);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Update Note");
            edit_desc.setText(intent.getStringExtra(EXTRA_DESC));
            edit_title.setText(intent.getStringExtra(EXTRA_TITLE));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIO,1));
        } else
            setTitle("Add Note");

    }

    private void saveNode() {
        String title = edit_title.getText().toString();
        String desc = edit_desc.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Enter the title and desc", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_PRIO, priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id != -1)
        {
            data.putExtra(EXTRA_ID,id);
            Log.d("Update",""+id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_node_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
