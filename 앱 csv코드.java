package com.example.real_fake;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public CSVParser() {
        //빈 생성자
    }

    public List<Item> getItemList(Context context) throws IOException{
        InputStream inputStream = context.getResources().openRawResource(R.raw.item);
        BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = "";
        List<Item> itemList = new ArrayList<>();

        while ((line = reader.readLine())!=null){
            String[] tokens = line.split(",");
            Item item = new Item();
            item.setNum(tokens[0]);
            item.setFake(Float.parseFloat(tokens[1]));
            item.setReal(Float.parseFloat(tokens[2]));

            itemList.add(item);
        }

        reader.close();
        inputStream.close();

        return itemList;
    }

    public List<Item> getItemList(Context context, Uri uri) throws IOException{
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line = ",";
        List<Item> itemList = new ArrayList<>();

        while ((line = reader.readLine())!=null){
            String[] tokens = line.split(",");
            Item item = new Item();
            item.setNum(tokens[0]);
            item.setFake(Float.parseFloat(tokens[1]));
            item.setReal(Float.parseFloat(tokens[2]));

            itemList.add(item);
        }

        reader.close();
        inputStream.close();

        return itemList;
    }
}
