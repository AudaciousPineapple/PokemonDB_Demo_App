package com.example.pokemondbappv2.pokedex.databaseclasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class bArrayConverter {

    public static String bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] b = stream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap byteToBitmap(String image) {
        byte[] b = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}
