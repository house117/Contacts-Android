package com.miapp.tabmenuapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.miapp.tabmenuapp.DataBase.Contact;
import com.miapp.tabmenuapp.Fragment.AboutFragment;
import com.miapp.tabmenuapp.Fragment.CFragment;
import com.miapp.tabmenuapp.Fragment.ConfigurationFragment;
import com.miapp.tabmenuapp.Receiver.PhoneReceiver;
import com.miapp.tabmenuapp.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permiso llamadas
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)){

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }

        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            Log.d("MAINACTIVITY", "SIN PERMISO READ_PHONE_STATE/READ_CALL_LOG");
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_CALL_LOG}, 100);
        }

        Intent intent = new Intent(MainActivity.this, PhoneReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 0,
                intent, 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                FragmentManager fragmentManager = getSupportFragmentManager();
                CFragment cfragment = new CFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.principalito, cfragment)
                        .addToBackStack("formularioAdd")
                        .commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Toast.makeText(getApplicationContext(), "Configuración Menu", Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            ConfigurationFragment configurationFragment = new ConfigurationFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.principalito, configurationFragment)
                    .addToBackStack("configFrg")
                    .commit();
            return true;
        }
        if(id == R.id.about){
            Toast.makeText(getApplicationContext(), "About Menu", Toast.LENGTH_LONG).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            AboutFragment aboutFragment = new AboutFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.principalito, aboutFragment)
                    .addToBackStack("aboutFrg")
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}