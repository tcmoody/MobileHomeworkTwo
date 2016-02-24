package com.lclark.homeworktwo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by prog on 2/21/16.
 */
public class PokemonRecyclerAdapter extends RecyclerView.Adapter<PokemonRecyclerAdapter.PokemonViewHolder>{

    private final ArrayList<Pokemon> mPokemons;
    private final OnPokemonClickListener mListener;

    public interface OnPokemonClickListener{
        void OnPokemonClick(Pokemon pokemon);
    }

    public PokemonRecyclerAdapter(ArrayList<Pokemon> pokemons, OnPokemonClickListener pokemonClickListener) {
        mPokemons = pokemons;
        mListener=pokemonClickListener;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View row = layoutInflater.inflate(R.layout.pokemon_row_info, parent, false);
        return new PokemonViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final PokemonViewHolder holder, final int position) {
        Pokemon pokemon = mPokemons.get(position);
        holder.name.setText(pokemon.getName());
        holder.id.setText(pokemon.getId());

        Picasso.with(holder.pokePicture.getContext()).load(pokemon.getImageUrl()).into(holder.pokePicture);

        Context pokeContext = holder.name.getContext();
        holder.height.setText(pokeContext.getString(R.string.pokemon_height, pokemon.getHeight()));
        holder.weight.setText(pokeContext.getString(R.string.pokemon_weight, pokemon.getWeight()));
        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.OnPokemonClick(mPokemons.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPokemons.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder{

        TextView name, id, height, weight;
        ImageView pokePicture;
        View fullView;

        public PokemonViewHolder(View itemView) {
            super(itemView);
            fullView=itemView;
            pokePicture=(ImageView) itemView.findViewById(R.id.pokemon_imageview);
            name = (TextView) itemView.findViewById(R.id.pokemon_name_textview);
            id = (TextView) itemView.findViewById(R.id.pokemon_id_textview);
            height = (TextView) itemView.findViewById(R.id.pokemon_height_textview);
            weight = (TextView) itemView.findViewById(R.id.pokemon_weight_textview);
        }
    }
}
