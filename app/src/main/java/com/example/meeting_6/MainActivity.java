package com.example.meeting_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView status;
    TextView percent;
    IncrementerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        status = findViewById(R.id.status);
        percent = findViewById(R.id.percent);

        viewModel = new ViewModelProvider(this).get(IncrementerViewModel.class);
        viewModel.getValue().observe(this, value -> {
            if(value == 100) {
                status.setText("Finished");
            } else if(value == 0) {
                status.setText("Pending");
            } else {
                status.setText("Running");
            }
            progressBar.setProgress(value);
            percent.setText(value + "%");
        });
    }

    public void start(View view) {
        viewModel.execute();
    }

    public void cancel(View view) {
        viewModel.stop();
    }
}