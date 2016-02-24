package com.lclark.homeworktwo;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prog on 2/23/16.
 */
public class NetworkAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = NetworkAsyncTask.class.getSimpleName();
    private Pokemon mPokemon;
    private PokemonDetail mPokemonDetail;
    public NetworkAsyncTask(Pokemon pokemon, PokemonDetail pokemonDetail){
        mPokemon = pokemon;
        mPokemonDetail = pokemonDetail;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        StringBuilder responseBuilder = new StringBuilder();
        JSONObject jsonObject = null;
        if(strings == null){
            return null;
        }

        String id = strings[0];

        try {
            URL url = new URL("http://pokeapi.co/api/v2/pokemon/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            if(isCancelled()){
                return null;
            }
            while((line = reader.readLine()) != null){
                responseBuilder.append(line);
                if(isCancelled()){
                    return null;
                }
            }
            jsonObject = new JSONObject(responseBuilder.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        mPokemonDetail.mHp.setVisibility(View.INVISIBLE);
        mPokemonDetail.mAttack.setVisibility(View.INVISIBLE);
        mPokemonDetail.mDefense.setVisibility(View.INVISIBLE);
        mPokemonDetail.mSpeed.setVisibility(View.INVISIBLE);
        mPokemonDetail.mSpecAttack.setVisibility(View.INVISIBLE);
        mPokemonDetail.mSpecDefense.setVisibility(View.INVISIBLE);
        mPokemonDetail.mBaseExp.setVisibility(View.INVISIBLE);
        mPokemonDetail.mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCancelled(){
        super.onCancelled();
        mPokemonDetail.mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostExecute(JSONObject json){
        super.onPostExecute(json);
        if (json == null){
            Log.e(TAG, "Resulting JSON is null");
        }else{
            try {
                
                mPokemon.setmBaseExp(json.getString("base_experience"));
                JSONArray pokeArray = json.getJSONArray("stats");
                
                for (int i = 0; i < pokeArray.length(); i++) {
                    
                    JSONObject stat = pokeArray.getJSONObject(i);
                    String baseStat = stat.getString("base_stat");

                    switch(i){
                        case 0:
                            mPokemon.setmSpeed(baseStat);
                            break;
                        case 1:
                            mPokemon.setmSpecDef(baseStat);
                            break;
                        case 2:
                            mPokemon.setmSpecAttack(baseStat);
                            break;
                        case 3:
                            mPokemon.setmDefense(baseStat);
                            break;
                        case 4:
                            mPokemon.setmAttack(baseStat);
                            break;
                        case 5:
                            mPokemon.setmHp(baseStat);
                            break;
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPokemonDetail.updatePokemon();
            mPokemonDetail.mProgress.setVisibility(View.INVISIBLE);
            mPokemonDetail.mHp.setVisibility(View.VISIBLE);
            mPokemonDetail.mAttack.setVisibility(View.VISIBLE);
            mPokemonDetail.mDefense.setVisibility(View.VISIBLE);
            mPokemonDetail.mSpeed.setVisibility(View.VISIBLE);
            mPokemonDetail.mSpecAttack.setVisibility(View.VISIBLE);
            mPokemonDetail.mSpecDefense.setVisibility(View.VISIBLE);
            mPokemonDetail.mBaseExp.setVisibility(View.VISIBLE);
            mPokemon.setUpdated(true);

        }
    }
}
