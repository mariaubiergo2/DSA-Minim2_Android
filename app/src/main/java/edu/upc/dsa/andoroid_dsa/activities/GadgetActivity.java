package edu.upc.dsa.andoroid_dsa.activities;

import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.SHARED_PREFS;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT1;
import static edu.upc.dsa.andoroid_dsa.activities.LogInActivity.TEXT2;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.Gadget;
import retrofit2.Call;

public class GadgetActivity extends AppCompatActivity implements RecycleClickViewListener {

    //TableLayout tableLayout;

    Api APIservice;

    Button logout;

    private RecyclerView recyclerViewGadgets;
    private RecyclerViewAdapter adapterGadgets;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gadget_shop_activity);
        this.getUserIdFromDashboard();

        //recyclerViewGadgets= new RecyclerView(this);
        recyclerViewGadgets=(RecyclerView)findViewById(R.id.recyclerGadget);
        Log.d("DDDD", ""+recyclerViewGadgets);
        recyclerViewGadgets.setLayoutManager(new LinearLayoutManager(this));
        //tableLayout = findViewById(R.id.tableLayout);
        //logout =findViewById(R.id.logOutBtn);
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<List<Gadget>> call = APIservice.getGadgets();
        try {
            adapterGadgets = new RecyclerViewAdapter(call.execute().body(), this);
            //buildTable(call);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recyclerViewGadgets.setAdapter(adapterGadgets);
    }
    public void btnClicked(View view) throws IOException {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putString(TEXT1, "");
        editor.putString(TEXT2, "");
        editor.apply();
        Intent intent = new Intent(GadgetActivity.this, MainActivity.class);
        GadgetActivity.this.startActivity(intent);
    }

    public void returnFunction(View view){
        Intent intentRegister = new Intent(GadgetActivity.this, DashBoardActivity.class);
        GadgetActivity.this.startActivity(intentRegister);
    }

    @Override
    public void recyclerViewListClicked(int position) {
        Gadget gadget=adapterGadgets.gadgets.get(position);
        Intent intent=new Intent(GadgetActivity.this,PurchaseActivity.class);
        saveVariables(gadget, this.userId);
        GadgetActivity.this.startActivity(intent);

    }
    public void saveVariables(Gadget gadgetClicked, String userId) {
        SharedPreferences sharedPreferences= getSharedPreferences("gadgetItem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("idGadget", gadgetClicked.getIdGadget());
        editor.putString("descriptionGadget", gadgetClicked.getDescription());
        editor.putString("costGadget",String.valueOf(gadgetClicked.getCost()));
        editor.putString("idUser", userId);
        editor.apply();
    }
    public void getUserIdFromDashboard(){
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        this.userId = sharedPreferences.getString("userId",null).toString();
    }

    public void goBackToDashBoardActivity(View view) {
        Intent intent=new Intent(GadgetActivity.this, DashBoardActivity.class);
        startActivity(intent);
    }
}