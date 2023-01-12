package edu.upc.dsa.andoroid_dsa.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.models.FAQ;

public class RecyclerViewAdapterFAQ extends RecyclerView.Adapter<RecyclerViewAdapterFAQ.ViewHolder>{
    private static RecycleClickViewListener listener;
    //private static final String URL_INT
    public List<FAQ> faqs;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView question,answer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question=(TextView)itemView.findViewById(R.id.questiontxt);
            answer=(TextView)itemView.findViewById(R.id.answertxt);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            listener.recyclerViewListClicked(this.getLayoutPosition());
        }
    }

    public RecyclerViewAdapterFAQ(List<FAQ> fs, RecycleClickViewListener listener) {
        this.faqs = fs;
        this.listener =listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_faqs,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(faqs.get(position).getQuestionFAQ());
        holder.answer.setText(faqs.get(position).getAnswerFAQ());
    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }

}
