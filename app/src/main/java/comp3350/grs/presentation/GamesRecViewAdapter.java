package comp3350.grs.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import comp3350.grs.R;
import comp3350.grs.objects.Game;


// Adapter class for BrowseGames class
public class GamesRecViewAdapter extends RecyclerView.Adapter<GamesRecViewAdapter.ViewHolder> {


    private ArrayList<Game> games = new ArrayList<>() ;

    private Context context ;

    public GamesRecViewAdapter(Context context) {
        this.context = context ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_list_item, parent, false) ;
        ViewHolder holder = new ViewHolder(view) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GamesRecViewAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(games.get(position).getName());
        holder.txtPrice.setText("$" + String.valueOf(games.get(position).getPrice()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, games.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(games.get(position).getImageURL())
                .into(holder.image) ;
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtPrice ;
        private CardView parent ;
        private ImageView image ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName) ;
            parent = itemView.findViewById(R.id.parent) ;
            txtPrice = itemView.findViewById(R.id.txtPrice) ;

            image = itemView.findViewById(R.id.image) ;
        }
    }
}
