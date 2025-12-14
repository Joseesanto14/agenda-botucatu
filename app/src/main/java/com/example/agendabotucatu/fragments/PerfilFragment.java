package com.example.agendabotucatu.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.activities.CadEventoActivity;
import com.example.agendabotucatu.activities.LoginActivity;
import com.example.agendabotucatu.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    RecyclerView recyclerEventos;
    FirebaseUser user;
    FirebaseFirestore db;
    Usuario usuario;
    EditText edEmail, edTipoConta;
    TextView txtNomeUsuario;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edEmail = view.findViewById(R.id.edEmail);
        edTipoConta = view.findViewById(R.id.edTipoConta);
        txtNomeUsuario = view.findViewById(R.id.txtNomeUsuario);
        recyclerEventos = view.findViewById(R.id.recyclerEventos);

        db = FirebaseFirestore.getInstance();

        buscarInfos();

        view.findViewById(R.id.btnSair).setOnClickListener(v -> sairConta());

        view.findViewById(R.id.btnCadastrarEvento).setOnClickListener(v -> abrirCadastroEvento());

    }
    private void abrirCadastroEvento(){
        Intent intent = new Intent(getActivity(), CadEventoActivity.class);
        startActivity(intent);
    }

    private void sairConta(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void buscarInfos(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String id = user.getUid();

            db.collection("usuarios").document(id).get()
                    .addOnSuccessListener(document -> {
                        if(document.exists()){
                            // Cria o objeto Usuario com base nos dados do Firestore
                            usuario = new Usuario(document.getString("nome"), document.getString("email"), document.getString("id"));
                            usuario.setTipo(document.getString("tipo"));
                            usuario.setImagem(document.getString("imagem"));

                            txtNomeUsuario.setText(usuario.getNome());
                            edEmail.setText(usuario.getEmail());
                            edTipoConta.setText(usuario.getTipo());
                        }
                    });
        } else {
            // Redireciona para a tela de login se não houver usuário logado
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }
}