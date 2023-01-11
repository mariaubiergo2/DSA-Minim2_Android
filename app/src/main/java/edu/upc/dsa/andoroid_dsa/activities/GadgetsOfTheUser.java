package edu.upc.dsa.andoroid_dsa.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import edu.upc.dsa.andoroid_dsa.models.UserInformation;
import retrofit2.Call;

public class GadgetsOfTheUser extends AppCompatActivity implements RecycleClickViewListener{
    Api APIservice;
    String idUser;
    String name;
    private RecyclerView recyclerViewGadgets;
    private RecyclerViewAdapter adapterGadgets;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gadgets_user_profile);
        this.getUserIdFromPreviousActivity();
        this.updateLabel();
        recyclerViewGadgets = (RecyclerView) findViewById(R.id.recyclerGadgetUser);
        Log.d("DDDD", "" + recyclerViewGadgets);
        recyclerViewGadgets.setLayoutManager(new LinearLayoutManager(this));
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Gadget>> call = APIservice.purchasedGadgets(this.idUser);
        try {
            adapterGadgets = new RecyclerViewAdapter(call.execute().body(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewGadgets.setAdapter(adapterGadgets);
    }

    @Override
    public void recyclerViewListClicked(int position) {

    }
    public void getUserIdFromPreviousActivity(){
        SharedPreferences sharedPreferences = getSharedPreferences("userIdAndUsername", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("userId", null).toString();
        this.name = sharedPreferences.getString("name", null).toString();
    }
    public void updateLabel(){
        String update_title =getString(R.string.updating_title_user);
        update_title="Gadgets of "+this.name+" !";
        EditText e = (EditText) findViewById (R.id.Edit_Title_Update1);
        e.setText(update_title);
    }
    public void ReturnToProfile(View view){
        Intent intentRegister = new Intent(GadgetsOfTheUser.this, YourProfileActivity.class);
        GadgetsOfTheUser.this.startActivity(intentRegister);
    }
}
