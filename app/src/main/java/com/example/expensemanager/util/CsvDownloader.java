package com.example.expensemanager.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CsvDownloader {

    private final Context context;

    public CsvDownloader(Context context) {
        this.context = context;
    }

    public void downloadCsv(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast("Download failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    showToast("Server error: " + response.code());
                    return;
                }

                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!downloadsDir.exists()) downloadsDir.mkdirs();

                File csvFile = new File(downloadsDir, "expenses.csv");

                try (FileOutputStream fos = new FileOutputStream(csvFile)) {
                    if (response.body() != null) {
                        fos.write(response.body().bytes());
                        showToast("CSV saved to Downloads/expenses.csv");
                    }
                }
            }
        });
    }

    private void showToast(String message) {
        android.os.Handler handler = new android.os.Handler(context.getMainLooper());
        handler.post(() -> Toast.makeText(context, message, Toast.LENGTH_LONG).show());
    }
}
