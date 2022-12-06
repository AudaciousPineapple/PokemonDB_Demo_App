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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICalls {

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

    public static class GetXpRateInfo extends AsyncTask<Void, Void, Void> {
        private final String urlString;
        private String xpRateResult;
        private final TextView textView;

        public GetXpRateInfo (String url, TextView textView) {
            this.urlString = url;
            this.textView = textView;
            xpRateResult = "";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection;
            BufferedReader reader;

            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String tempString;
                while ((tempString = reader.readLine()) != null)
                    xpRateResult += tempString;

                urlConnection.disconnect();
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void r) {
            super.onPostExecute(r);

            try {
                JSONObject xpObject = new JSONObject(xpRateResult);
                String textViewStr = xpObject.getJSONArray("levels").getJSONObject(99)
                        .getString("experience") + " | " + PokemonMethods.fixPokemonName(
                        xpObject.getString("name"));

                textView.setText(textViewStr);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}


