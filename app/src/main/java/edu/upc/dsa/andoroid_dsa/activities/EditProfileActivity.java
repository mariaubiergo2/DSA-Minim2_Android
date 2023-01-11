package edu.upc.dsa.andoroid_dsa.activities;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Credentials;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import edu.upc.dsa.andoroid_dsa.models.User;
import edu.upc.dsa.andoroid_dsa.models.UserId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity{
    public String idUser;
    public String username;
    public String surname;
    public String birthday;
    public String email;
    public String password;
    public String coins;

    public String new_idUser;
    public String new_username;
    public String new_surname;
    public String new_birthday;
    public String new_email;
    public String new_password;
    public String new_coins;

    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_main);
        this.getVariables();
        this.updateLabels();
        this.getUserIdFromDashboard();

        //recyclerViewGadgets.setAdapter(adapterGadgets);

    }
    public void getVariables() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        this.username = sharedPreferences.getString("username", null).toString();
        this.surname = sharedPreferences.getString("surname", null).toString();
        this.birthday = sharedPreferences.getString("birthday", null).toString();
        this.email = sharedPreferences.getString("email", null).toString();
        this.password = sharedPreferences.getString("password", null).toString();
        this.coins=sharedPreferences.getString("coins",null).toString();
    }
    public void updateLabels(){
        String updateUsername =getString(R.string.updating_username);
        updateUsername=this.username;
        EditText editorUsername = (EditText) findViewById (R.id.user_name);
        editorUsername.setText(updateUsername);

        String updateSurname =getString(R.string.updating_surname);
        updateSurname=this.surname;
        EditText editorSurname = (EditText) findViewById (R.id.sur_name);
        editorSurname.setText(updateSurname);

        String updateBirthday =getString(R.string.updating_birthday);
        updateBirthday=this.birthday;
        EditText editorBirthday = (EditText) findViewById (R.id.birth_day);
        editorBirthday.setText(updateBirthday);
        String updateEmail =getString(R.string.updating_email);
        updateEmail=this.email;
        EditText editorEmail = (EditText) findViewById (R.id.e_mail);
        editorEmail.setText(updateEmail);
        String updatePassword =getString(R.string.updating_password);
        updatePassword=this.password;
        EditText editorPassword = (EditText) findViewById (R.id.pass_word);
        editorPassword.setText(updatePassword);

    }
    public void Return(View view){
        Intent intentRegister = new Intent(EditProfileActivity.this, DashBoardActivity.class);
        EditProfileActivity.this.startActivity(intentRegister);
    }
    public void getUserIdFromDashboard(){
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("userId", null).toString();
    }
    public void editUser(View view){
        APIservice = RetrofitClient.getInstance().getMyApi();

        TextInputEditText un = findViewById(R.id.user_name);
        this.new_username = un.getText().toString();

        TextInputEditText us = findViewById(R.id.sur_name);
        this.new_surname = us.getText().toString();

        TextInputEditText bb = findViewById(R.id.birth_day);
        this.new_birthday = bb.getText().toString();

        TextInputEditText ee = findViewById(R.id.e_mail);
        this.new_email = ee.getText().toString();

        TextInputEditText pp = findViewById(R.id.pass_word);
        this.new_password = pp.getText().toString();

        User newuser = new User(this.new_username, this.new_surname, this.new_birthday, this.new_email, this.new_password, parseInt(this.coins));

        Call<Void> call = APIservice.updateUser(newuser, this.idUser);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 201:
                        Intent intentRegister = new Intent(EditProfileActivity.this, YourProfileActivity.class);
                        EditProfileActivity.this.startActivity(intentRegister);
                        Toast.makeText(EditProfileActivity.this,"Correctly changed", Toast.LENGTH_SHORT).show();
                        break;

                    case 401:
                        Intent intentRegister2 = new Intent(EditProfileActivity.this, YourProfileActivity.class);
                        EditProfileActivity.this.startActivity(intentRegister2);
                        Toast.makeText(EditProfileActivity.this,"Unavailable", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(EditProfileActivity.this,"Error 404", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar snakyfail = Snackbar.make(view, "NETWORK FAILURE", 3000);
                snakyfail.show();
            }
        });

        //Toast.makeText(this,"PICO ACCEPT", Toast.LENGTH_SHORT).show();
        //Intent intentRegister = new Intent(EditProfileActivity.this, YourProfileActivity.class);
        //EditProfileActivity.this.startActivity(intentRegister);
    }
}
