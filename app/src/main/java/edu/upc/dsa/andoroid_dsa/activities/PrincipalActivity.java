package edu.upc.dsa.andoroid_dsa.activities;

import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.SHARED_PREFS;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT1;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity {

    Api APIservice;

    Intent intent;
    private String text1;
    private String text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_main);
        this.loadData();

        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                if(!Objects.equals(text1, "") && !Objects.equals(text2, "")) {
                    intent = new Intent(PrincipalActivity.this, DashBoardActivity.class);
                } else {
                    intent = new Intent(PrincipalActivity.this, LogInActivity.class);
                }
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(t,5000);
    }
    public void setLanguage(View view){
        if (intent!=null) {
            switch (view.getId()) {
                case R.id.buttonEN:
                    setLocale("en");
                    startActivity(intent);
                    break;
                case R.id.buttonZH:
                    setLocale("zh");
                    startActivity(intent);
                    break;
            }
        }
    }

    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration, metrics);
        onConfigurationChanged(configuration);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfiguration) {
        super.onConfigurationChanged(newConfiguration);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text1 = sharedPreferences.getString( TEXT1,"" );
        text2 = sharedPreferences.getString( TEXT2,"" );
    }



}