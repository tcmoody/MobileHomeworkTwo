package com.lclark.homeworktwo;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * Created by prog on 2/22/16.
 */
public class PokemonDetail extends AppCompatActivity{

    public static final String TAG = PokemonDetail.class.getSimpleName();
    public static final String POKEMON_ARG = "ArgPokemon";
    private Pokemon mPokemon;
    private NetworkAsyncTask mAsync;
    TextView mHp, mAttack, mDefense, mSpeed, mSpecAttack, mSpecDefense, mBaseExp;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail);
        mPokemon = getIntent().getParcelableExtra(POKEMON_ARG);

        TextView id = (TextView) findViewById(R.id.pokemon_id_textview_pokemon_detail);
        TextView height = (TextView) findViewById(R.id.pokemon_height_textview_pokemon_detail);
        TextView weight = (TextView) findViewById(R.id.pokemon_weight_textview_pokemon_detail);
        ImageView pokeImage = (ImageView) findViewById(R.id.pokemon_imageview_pokemon_detail);
        mHp = (TextView) findViewById(R.id.pokemon_details_health);
        mAttack = (TextView) findViewById(R.id.pokemon_details_attack);
        mDefense = (TextView) findViewById(R.id.pokemon_details_defense);
        mSpeed = (TextView) findViewById(R.id.pokemon_details_speed);
        mSpecAttack = (TextView) findViewById(R.id.pokemon_details_special_attack);
        mSpecDefense = (TextView) findViewById(R.id.pokemon_details_special_defense);
        mBaseExp = (TextView) findViewById(R.id.pokemon_details_base_experience);
        mProgress = (ProgressBar) findViewById(R.id.pokemon_details_progress_bar);
        mProgress.setIndeterminate(true);

        Picasso.with(this).load(mPokemon.getImageUrl()).fit().centerInside().into(pokeImage);
        id.setText(getString(R.string.pokemon_ID,mPokemon.getId()));
        height.setText(getString(R.string.pokemon_height, mPokemon.getHeight()));
        weight.setText(getString(R.string.pokemon_weight, mPokemon.getWeight()));

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(mPokemon.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(!mPokemon.isUpdated()) {
            Log.d(TAG, "great failure");
            if (networkInfo == null || !networkInfo.isConnected()) {
                Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            } else {
                mAsync = new NetworkAsyncTask(mPokemon, this);
                mAsync.execute(mPokemon.getId());
            }

            updatePokemon();
        }else{
            Log.d(TAG, "great success");
            mHp.setVisibility(View.VISIBLE);
            mAttack.setVisibility(View.VISIBLE);
            mDefense.setVisibility(View.VISIBLE);
            mSpeed.setVisibility(View.VISIBLE);
            mSpecAttack.setVisibility(View.VISIBLE);
            mSpecDefense.setVisibility(View.VISIBLE);
            mBaseExp.setVisibility(View.VISIBLE);
        }
    }

    public void updatePokemon(){
        mHp.setText(getString(R.string.pokemon_health, mPokemon.getmHp()));
        mAttack.setText(getString(R.string.pokemon_attack, mPokemon.getmAttack()));
        mDefense.setText(getString(R.string.pokemon_defense, mPokemon.getmDefense()));
        mSpeed.setText(getString(R.string.pokemon_speed, mPokemon.getmSpeed()));
        mSpecAttack.setText(getString(R.string.pokemon_spec_attack, mPokemon.getmSpecAttack()));
        mSpecDefense.setText(getString(R.string.pokemon_spec_defense, mPokemon.getmDefense()));
        mBaseExp.setText(getString(R.string.pokemon_base_exp, mPokemon.getmBaseExp()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                killActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        killActivity();
    }

    private void killActivity() {
        if(mAsync != null && !mAsync.isCancelled()){
            mAsync.cancel(true);
        }
        Intent intent = new Intent();
        intent.putExtra(POKEMON_ARG, mPokemon);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
