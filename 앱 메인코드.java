package com.example.real_fake;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<String> filteredItem;
    ArrayList<String> dataList;
    Double fake,real;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        dataList = new ArrayList<>();

        try {
            loadCSV();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =dataList.get(position);
                String[] itemParts = selectedItem.split(",");
                String fakeString = itemParts[1].replace("Fake ", "").replace("%", "");
                fake=Double.parseDouble(fakeString);
                String phone = "01075670611";
                if(fake>=0.45){
                   showSendSmsDialog(phone, "AI음성으로 의심됩니다");
                }else{
                    Toast.makeText(MainActivity.this,"fake 값 45%이하", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void loadCSV() throws IOException {
        InputStream inputStream = getAssets().open("last.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        boolean isFirstLine = true;

        while ((line = reader.readLine()) != null) {
            // 첫 번째 줄은 헤더이므로 스킵
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            String[] tokens = line.split(",");

            String id = tokens[0];
            fake = Double.parseDouble(tokens[1]);
            real = Double.parseDouble(tokens[2]);

            String fakeper = String.format("%.2f",fake);
            String realper = String.format("%.2f", real);


            dataList.add(id + ": Real "+realper+"%, Fake "+fakeper+"%");

        }

        reader.close();
    }


    private void showSendSmsDialog(String phoneNumber, String message) {
        new AlertDialog.Builder(this)
                .setTitle("문자 전송")
                .setMessage("AI 음성이 의심됩니다. 문자를 보내시겠습니까?")
                .setPositiveButton("확인", (dialog, which) -> sendSMS(phoneNumber, message))
                .setNegativeButton("취소", null)
                .show();
    }
    public void sendSMS(String phoneNumber, String message) {
        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "문자 전송 완료", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

}