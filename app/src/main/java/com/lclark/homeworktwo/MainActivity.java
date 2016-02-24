package com.lclark.homeworktwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PokemonRecyclerAdapter.OnPokemonClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mPokemonRecyclerView;
    public static final int CODE_POKEMON = 0;
    private Pokedex pokedex;
    private ArrayList<Pokemon> mPokemons;
    PokemonRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Pokedex");

        mPokemonRecyclerView = (RecyclerView) findViewById(R.id.main_activity_recyclerView);
        mPokemonRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        pokedex = new Pokedex();
        mPokemons = pokedex.getPokemons();
        recyclerAdapter = new PokemonRecyclerAdapter(mPokemons, this);

        mPokemonRecyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void OnPokemonClick(Pokemon pokemon) {
        Intent intent = new Intent(MainActivity.this, PokemonDetail.class);
        intent.putExtra(PokemonDetail.POKEMON_ARG, pokemon);
        startActivityForResult(intent, CODE_POKEMON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Pokemon pokemon = data.getParcelableExtra(PokemonDetail.POKEMON_ARG);
        mPokemons.set(mPokemons.indexOf(pokemon), pokemon);
        recyclerAdapter.notifyItemChanged(mPokemons.indexOf(pokemon));
    }
}
