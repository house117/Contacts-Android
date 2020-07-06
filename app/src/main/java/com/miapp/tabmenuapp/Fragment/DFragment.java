package com.miapp.tabmenuapp.Fragment;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
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
public class DFragment extends Fragment {
    ListView lvContacts;
    ListView lvFavorites;
    String id;
    String nombre;
    String numero;
    String favorito;
    public DFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null){
             id = bundle.getString("id");
             nombre = bundle.getString("nombre");
             numero = bundle.getString("numero");
             favorito = bundle.getString("favorito");
            int year = bundle.getInt("year");
            Log.d("RecibioFragD", "id: "+id);
            Log.d("RecibioFragD", "nombre: "+nombre);
            Log.d("RecibioFragD", "numero: "+numero);
            Log.d("RecibioFragD", "fav: "+favorito);
        }else{
            Log.d("DFragment","No hay Datos");
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_d, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button cancelar = getView().findViewById(R.id.cancelar_edt);
        Button aceptar = getView().findViewById(R.id.cerrar_ventana_config);
        Button telefono = getView().findViewById(R.id.btn_call_edt);

        lvContacts = getActivity().findViewById(R.id.lv_contactos);
        lvFavorites = getActivity().findViewById(R.id.lv_favoritos);

        TextView id_edit = getView().findViewById(R.id.id_edt);
        EditText nombre_edit = getView().findViewById(R.id.nombre_form_edt);
        EditText numero_edit = getView().findViewById(R.id.numero_form_edt);
        CheckBox fav_edit = getView().findViewById(R.id.favCheckBox_edt);

        id_edit.setText(""+id);
        //id_edit.setVisibility(View.INVISIBLE);
        nombre_edit.setText(""+nombre);
        numero_edit.setText(""+numero);

        if(favorito.equals("si")){
            fav_edit.setChecked(true);
        }else{
            fav_edit.setChecked(false);
        }

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView id = getView().findViewById(R.id.id_edt);
                final EditText nombre = getView().findViewById(R.id.nombre_form_edt);
                final EditText numero = getView().findViewById(R.id.numero_form_edt);
                final CheckBox checkBox = getView().findViewById(R.id.favCheckBox_edt);
                String strBox;
                if(checkBox.isChecked()){
                    strBox = "si";
                }else{
                    strBox =  "no";
                }
                String strNombre = nombre.getText().toString();
                String strNumero = numero.getText().toString();
                String strId = id.getText().toString();
                Log.d("idEd", ""+strId);
                Log.d("nombreEd", ""+strNombre);
                Log.d("numeroEd", ""+strNumero);
                Log.d("boxEd", ""+strBox);

                //Crear contact
                Contact contact = new Contact();
                contact.setMId(strId);
                contact.setContactName(strNombre);
                contact.setContactNumber(strNumero);
                contact.setContactoFavorito(strBox);

                //Agregar a base de datos
                ContactDaoImplement contactDaoImplement = ContactDaoImplement.get(getView().getContext());
                contactDaoImplement.updateContact(contact);

                //Actualizar ListaContactos

                ArrayList<Contact> nuevaList = contactDaoImplement.getContacts();

                ContactAdapter contactAdapter = new ContactAdapter(nuevaList, getActivity());
                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(nuevaList, getActivity());
                lvContacts.setAdapter(contactAdapter);
                lvFavorites.setAdapter(favoriteAdapter);
                Toast.makeText(getActivity(), "Contacto actualizado", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText numero = getView().findViewById(R.id.numero_form_edt);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+numero.getText().toString()));
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
