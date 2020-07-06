package com.miapp.tabmenuapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.miapp.tabmenuapp.DataBase.Contact;
import com.miapp.tabmenuapp.Fragment.AFragment;
import com.miapp.tabmenuapp.Fragment.CFragment;
import com.miapp.tabmenuapp.Fragment.DFragment;
import com.miapp.tabmenuapp.R;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    ArrayList<Contact> contacts;
    Context context;
    ContactViewHolder holder;

    public ContactAdapter(ArrayList<Contact> contacts, Context context){
        this.contacts = contacts;
        this.context = context;
        holder = new ContactViewHolder();
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contacts.indexOf(contacts.get(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.contacto_row_layout, null);
            holder.contactId = convertView.findViewById(R.id.contactId);
            holder.nombre = convertView.findViewById(R.id.contactName);
            holder.numero = convertView.findViewById(R.id.contactNumber);
            holder.favorito = convertView.findViewById(R.id.contactFavorite);
            holder.contenedor = convertView.findViewById(R.id.contenedor_row_contact);

        }
        holder.contactId.setText(""+contacts.get(position).getMId());
        holder.nombre.setText(""+contacts.get(position).getContactName());
        holder.numero.setText(""+contacts.get(position).getContactNumber());
        if(contacts.get(position).getContactoFavorito().equals("si")){
            holder.favorito.setChecked(true);
            holder.favorito.setClickable(false);
            Log.d("Funciono chequeo", "Si, detecto favorito");
        }else{
            holder.favorito.setChecked(false);
            holder.favorito.setClickable(false);
            Log.d("Funciono chequeo", "NONONO, no detecto favorito");
        }
        holder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();


                Bundle b = new Bundle();
                b.putString("id", ""+contacts.get(position).getMId());
                b.putString("nombre", ""+contacts.get(position).getContactName());
                b.putString("numero", ""+contacts.get(position).getContactNumber());
                b.putString("favorito", ""+contacts.get(position).getContactoFavorito());
                //b.putArrayList
                DFragment dFragment = new DFragment();
                dFragment.setArguments(b);


                fragmentManager.beginTransaction()
                        .replace(R.id.principalito, dFragment)
                        .addToBackStack("formularioEdit")
                        .commit();
            }
        });

        return convertView;
    }
}

class ContactViewHolder{
    public TextView contactId,
    nombre,
    numero;
    public CheckBox favorito;
    public LinearLayout contenedor;
}
