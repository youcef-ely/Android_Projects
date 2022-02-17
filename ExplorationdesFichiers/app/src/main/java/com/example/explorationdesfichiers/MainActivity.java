package com.example.explorationdesfichiers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<File> liste_fichiers = new ArrayList<File>();

    public static File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

    public static ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                liste_fichiers.add(file);
            }
        }
        return liste_fichiers;
    }

    public void load(File root) {
        liste_fichiers.clear();
        MainActivity.root = root;
        liste_fichiers = MainActivity.getfile(root);

        TextView racine = (TextView) findViewById(R.id.racine);

        racine.setText(root.getAbsolutePath());
        LinearLayout files = (LinearLayout) findViewById(R.id.fichiers);

        for (int i = 0; i < liste_fichiers.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.fichier_design, null);
            CardView card1 = (CardView) view.findViewById(R.id.carte1);
            TextView fileNameLine = (TextView) view.findViewById(R.id.nom_fichier);
            fileNameLine.setText(liste_fichiers.get(i).getName());
            if (liste_fichiers.get(i).isDirectory()) {
                card1.setCardBackgroundColor(Color.parseColor("#E5E1E0"));
                card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File root1 = new File(new StringBuilder().append(root.getAbsolutePath()).append("/").append(fileNameLine.getText().toString()).toString());
                        files.removeAllViews();
                        load(root1);
                    }
                });
            }
            files.addView(view);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }

        Button backBtn = (Button) findViewById(R.id.retour);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root = root.getParentFile();
                load(root);
            }
        });

        load(root);
    }
}