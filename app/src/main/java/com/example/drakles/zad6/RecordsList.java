package com.example.drakles.zad6;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordsList extends AppCompatActivity {
    private MediaPlayer mp = new MediaPlayer();
    private List<String> myList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    private static final String MEDIA_PATH = MainActivity.folder.getAbsolutePath() + "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.mylist);
        final File file = MainActivity.folder;
        for (File aList : file.listFiles()) {
            myList.add(aList.getName());
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                playSong(MEDIA_PATH + myList.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File song = new File(MEDIA_PATH + myList.get(position));
                boolean success = song.delete();
                if (success) {
                    adapter.remove(myList.get(position));
                    Toast.makeText(RecordsList.this, "Nagranie usunieto",
                            Toast.LENGTH_LONG).show();
                }
                return success;
            }
        });
    }

    private void playSong(String songPath) {
        try {
            mp.reset();
            mp.setDataSource(songPath);
            mp.prepare();
            mp.start();
            Toast.makeText(RecordsList.this, "Odtwarzanie",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
    }
}

