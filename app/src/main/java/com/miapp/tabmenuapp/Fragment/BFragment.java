package com.miapp.tabmenuapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.miapp.tabmenuapp.Adapter.FavoriteAdapter;
import com.miapp.tabmenuapp.ContactDaoImplement;
import com.miapp.tabmenuapp.DataBase.Contact;
import com.miapp.tabmenuapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends Fragment {
    ContactDaoImplement contactDaoImplement;
    ArrayList<Contact> contacts;
    ListView lvFavorites;
    public BFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Obtener datos de Base de Datos
        contactDaoImplement = ContactDaoImplement.get(getView().getContext());
        contacts = contactDaoImplement.getContacts();
        lvFavorites = getView().findViewById(R.id.lv_favoritos);


        if(contacts.size() > 0){
            Log.d("EstadoBase: ", "Hay info en la base de datos");
            Bundle bundle = new Bundle();

            //put your ArrayList data in bundle
            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(contacts, getView().getContext());
            lvFavorites.setAdapter(favoriteAdapter);
        }else{
            Log.d("EstadoBase: ", "No hay info en la base de datos");
        }
    }
}
