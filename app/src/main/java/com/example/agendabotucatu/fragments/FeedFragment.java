package com.example.agendabotucatu.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.adapters.EventoAdapter;
import com.example.agendabotucatu.models.Evento;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    RecyclerView recyclerEventos;
    EventoAdapter adapter;
    List<Evento> listaEventos = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
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
        // Infla o layout para este fragmento e o armazena em uma variável.
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerEventos = view.findViewById(R.id.recyclerEventos);
        recyclerEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new EventoAdapter(getContext(), listaEventos);
        recyclerEventos.setAdapter(adapter);

        carregarEventos();

        // Retorna a view configurada no final do métdo.
        return view;
    }

    private void carregarEventos() {
        CollectionReference eventosRef = db.collection("eventos");

        eventosRef.get().addOnSuccessListener(query -> {
            listaEventos.clear();
            for (var doc : query) {
                Evento e = doc.toObject(Evento.class);
                e.setIdEvento(doc.getId()); // para abrir detalhes depois
                listaEventos.add(e);
            }
            adapter.notifyDataSetChanged();
        });
    }
}