package edu.upc.dsa.andoroid_dsa.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upc.dsa.andoroid_dsa.Api;
import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.RetrofitClient;
import edu.upc.dsa.andoroid_dsa.models.UserInformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseActivity extends AppCompatActivity {
    Api APIservice;
    String idUser;
    String idGadget;
    String costGadget;
    String descriptionGadget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_activity);
        this.getVariables();
        this.updateLabels();
    }
    public void getVariables() {
        SharedPreferences sharedPreferences = getSharedPreferences("gadgetItem", Context.MODE_PRIVATE);
        this.idUser = sharedPreferences.getString("idUser", null).toString();
        this.idGadget = sharedPreferences.getString("idGadget", null).toString();
        this.descriptionGadget = sharedPreferences.getString("descriptionGadget", null).toString();
        this.costGadget = sharedPreferences.getString("costGadget", null).toString();
    }
    public void buyGadget(View view){
        APIservice = RetrofitClient.getInstance().getMyApi();
        Call<Void> call =APIservice.buyAGadget(this.idGadget,this.idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 201:
                        Toast.makeText(PurchaseActivity.this,"Purchase done successfully! Go back and buy more :)", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(PurchaseActivity.this,"Not enough money, play and get more!", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(PurchaseActivity.this,"The gadget does not exist anymore!", Toast.LENGTH_SHORT).show();
                        break;
                    case 409:
                        Toast.makeText(PurchaseActivity.this,"The identification is not correctly done!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PurchaseActivity.this,"NETWORK FAILURE :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateLabels(){
        String updateTitle =getString(R.string.updating_title_gadget);
        updateTitle=this.descriptionGadget+" !";
        EditText editorTitle = (EditText) findViewById (R.id.title_profile_gadget);
        editorTitle.setText(updateTitle);
        String updateIdentifier =getString(R.string.updating_identifier);
        updateIdentifier=this.idGadget;
        EditText editorIdentifier = (EditText) findViewById (R.id.id_gadget);
        editorIdentifier.setText(updateIdentifier);
        String updateDescription =getString(R.string.updating_description);
        updateDescription=this.descriptionGadget;
        EditText editorDescription = (EditText) findViewById (R.id.description_gadget);
        editorDescription.setText(updateDescription);
        String updateCost =getString(R.string.updating_cost);
        updateCost=this.costGadget;
        EditText editorCost = (EditText) findViewById (R.id.cost_Gadget);
        editorCost.setText(updateCost);
    }
    public void goBackToGadgetActivity(View view) {
        Intent intent = new Intent(PurchaseActivity.this, GadgetActivity.class);
        startActivity(intent);
    }
}
