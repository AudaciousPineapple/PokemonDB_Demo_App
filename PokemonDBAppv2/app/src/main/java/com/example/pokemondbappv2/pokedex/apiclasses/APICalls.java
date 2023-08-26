package com.example.pokemondbappv2.pokedex.apiclasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokemondbappv2.PokemonMethods;
import com.example.pokemondbappv2.pokedex.databaseclasses.PokemonEntryG1;
import com.example.pokemondbappv2.pokedex.databaseclasses.bArrayConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICalls {

    public interface OnTaskCompleted{
        void OnTaskCompleted();
    }

    public static class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private final String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();

                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }

    public static class SpriteLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private OnTaskCompleted listener;
        private final String url;
        private ImageView imageView;
        private final boolean isFirstSprite;
        PokemonEntryG1 pokemon;
        private String toDb;

        public SpriteLoadTask(String url, ImageView imageView, boolean isFirstSprite,
                              PokemonEntryG1 pokemon, OnTaskCompleted listener) {
            this.url = url;
            this.imageView = imageView;
            this.isFirstSprite = isFirstSprite;
            this.pokemon = pokemon;
            this.listener = listener;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();

                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap result = BitmapFactory.decodeStream(input);
                connection.disconnect();
                input.close();

                toDb = bArrayConverter.bitmapToByte(result);

                /*
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = result.copy(result.getConfig(), result.isMutable());
                Log.d("**TESTING**" , String.valueOf(bitmap.getByteCount()));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                toDb = stream.toByteArray();
                 */

                return result;
            } catch (IOException e) {
                Log.e("**TESTING**", "doInBackground not completed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);

            /*
            Log.d("**TESTING**" , String.valueOf(toDb.length));
            String str = "";
            for (int i = 0; i < toDb.length; i++) {
                str += toDb[i];
            }
            Log.d("**TESTING**" , str);
             */

            if (isFirstSprite)
                pokemon.setSprite1(toDb);
            else {
                pokemon.setSprite2(toDb);
                listener.OnTaskCompleted();
            }
        }
    }
}


