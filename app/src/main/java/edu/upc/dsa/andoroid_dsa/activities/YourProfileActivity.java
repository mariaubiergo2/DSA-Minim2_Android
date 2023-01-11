package edu.upc.dsa.andoroid_dsa.activities;

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

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import retrofit2.Call;

public class YourProfileActivity extends AppCompatActivity{
    public String idUser;
    public String username;
    public String surname;
    public String birthday;
    public String email;
    public String password;
    public String coins;
    public List<Gadget> gadgetsOfTheUser;
    private RecyclerView recyclerViewGadgets;
    private RecyclerViewAdapter adapterGadgets;
    Api APIservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_profile_main);
        this.getVariables();
        this.updateLabels();
        this.getUserIdFromDashboard();
        //recyclerViewGadgets=(RecyclerView)findViewById(R.id.recyclerGadgetYourProfile);
        //Log.d("DDDD", ""+recyclerViewGadgets);
        //recyclerViewGadgets.setLayoutManager(new LinearLayoutManager(this));
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Gadget>> call = APIservice.purchasedGadgets(this.idUser);

        try {
            this.gadgetsOfTheUser=call.execute().body();
            //adapterGadgets = new RecyclerViewAdapter(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String updateTitle =getString(R.string.updating_title);
        updateTitle=this.username+" !";
        EditText editorTitle = (EditText) findViewById (R.id.title_profile);
        editorTitle.setText(updateTitle);
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
        String update_coins= getString(R.string.updating_coins);
        update_coins=this.coins;
        EditText editor_coins = (EditText) findViewById(R.id.co_ins);
        editor_coins.setText(update_coins);
    }
    public void Return(View view){
        Intent intentRegister = new Intent(YourProfileActivity.this, DashBoardActivity.class);
        YourProfileActivity.this.startActivity(intentRegister);
    }
    public void getUserIdFromDashboard(){
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("userId", null).toString();
    }
    public void openGadgetsOfUser(View view){
        if(this.gadgetsOfTheUser==null){
            Toast.makeText(YourProfileActivity.this,"You have not bought any gadgets, go to the shop!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent =new Intent(YourProfileActivity.this, GadgetsOfTheUser.class);
        this.saveUserIdAndName(this.idUser,this.username);
        this.startActivity(intent);
    }
    public void saveUserIdAndName(String userId, String username){
        SharedPreferences sharedPreferences= getSharedPreferences("userIdAndUsername", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.putString("name",username);
        Log.i("SAVING: ",userId);
        Log.i("SAVING: ",username);
        editor.apply();
    }
    public void openEditUser(View view){
        Intent intent =new Intent(YourProfileActivity.this, EditProfileActivity.class);
        this.saveUserIdAndName(this.idUser,this.username);
        this.startActivity(intent);
    }
    public void saveUserInfo(String userId, String username){
        SharedPreferences sharedPreferences= getSharedPreferences("userIdAndUsername", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("userId", userId);
        editor.putString("name",username);
        Log.i("SAVING: ",userId);
        Log.i("SAVING: ",username);
        editor.apply();
    }
}
