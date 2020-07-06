package com.miapp.tabmenuapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.miapp.tabmenuapp.Adapter.ContactAdapter;
import com.miapp.tabmenuapp.Adapter.FavoriteAdapter;
import com.miapp.tabmenuapp.ContactDaoImplement;
import com.miapp.tabmenuapp.DataBase.Contact;
import com.miapp.tabmenuapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CFragment extends Fragment {
    ListView lvContacts;
    ListView lvFavorites;

    public CFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        if(bundle != null){
           String activityName = bundle.getString("activity");
           String name = bundle.getString("name");
           Log.d(activityName, name);
        }else{
            Log.d("CFragment","No hay Datos");
        }

        return inflater.inflate(R.layout.fragment_c, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button cancelar = getView().findViewById(R.id.cancelar_edt);
        lvContacts = getActivity().findViewById(R.id.lv_contactos);
        lvFavorites = getActivity().findViewById(R.id.lv_favoritos);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        Button aceptar = getView().findViewById(R.id.cerrar_ventana_config);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 final EditText nombre = getView().findViewById(R.id.nombre_form);
                 final EditText numero = getView().findViewById(R.id.numero_form);
                 final CheckBox checkBox = getView().findViewById(R.id.favCheckBox);
                 String strBox;
                 if(checkBox.isChecked()){
                     strBox = "si";
                 }else{
                    strBox =  "no";
                 }
                 String strNombre = nombre.getText().toString();
                 String strNumero = numero.getText().toString();
                 Log.d("nombre", ""+strNombre);
                 Log.d("numero", ""+strNumero);
                 Log.d("box", ""+strBox);

                 //Crear contact
                 Contact contact = new Contact();
                 contact.setContactName(strNombre);
                 contact.setContactNumber(strNumero);
                 contact.setContactoFavorito(strBox);

                 //Agregar a base de datos
                ContactDaoImplement contactDaoImplement = ContactDaoImplement.get(getView().getContext());


                //Actualizar ListaContactos
                contactDaoImplement.addContact(contact);
                ArrayList<Contact> nuevaList = contactDaoImplement.getContacts();

                ContactAdapter contactAdapter = new ContactAdapter(nuevaList, getActivity());
                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(nuevaList, getActivity());

                lvContacts.setAdapter(contactAdapter);
                lvFavorites.setAdapter(favoriteAdapter);

                Toast.makeText(getActivity(), "Contacto creado exitosamente", Toast.LENGTH_LONG).show();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle b = new Bundle();
        b.putString("newData", "CFragment");
        b.putInt("year", 2020);
        //b.putArrayList
        AFragment aFragment = new AFragment();
        aFragment.setArguments(b);

        /*FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenido, aFragment)
                .commit();*/
    }
}
