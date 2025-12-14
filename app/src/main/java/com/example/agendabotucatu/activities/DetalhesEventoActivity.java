package com.example.agendabotucatu.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.agendabotucatu.R;
import com.example.agendabotucatu.models.Evento;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DetalhesEventoActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView imgEvento, imgFotoAutor;
    private TextView txtNomeAutor, txtTituloEvento, txtLocal, txtData, txtCategoria, txtDescricao;
    private ImageButton btFavoritar;
    private String eventoId = "";
    private String localEvento = "";
    private String tituloEvento = "";
    private String autorId = "";
    private Button btVerMapa, btCompartilhar, btVoltar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        eventoId = getIntent().getStringExtra("eventoId");

        inicializarViews();

        // Buscar dados do evento
        carregarEvento();

        btVoltar.setOnClickListener(v -> finish());

        btCompartilhar.setOnClickListener(v -> compartilharEvento());

        btVerMapa.setOnClickListener(v -> abrirNoMapa());

        btFavoritar.setOnClickListener(v -> marcarComoFavorito());

    }


    public void voltarFeed(View view){
        finish();
    }

    private void inicializarViews() {
        imgEvento = findViewById(R.id.imgEvento);
        imgFotoAutor = findViewById(R.id.imgFotoAutor);
        txtNomeAutor = findViewById(R.id.txtNomeAutor);
        txtTituloEvento = findViewById(R.id.txtTituloEvento);
        txtLocal = findViewById(R.id.txtLocal);
        txtData = findViewById(R.id.txtData);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtDescricao = findViewById(R.id.txtDescricao);
        btFavoritar = findViewById(R.id.btFavoritar);
        btVerMapa      = findViewById(R.id.btVerMapa);
        btCompartilhar = findViewById(R.id.btCompartilhar);
        btVoltar       = findViewById(R.id.btVoltar);
    }

    private void carregarEvento() {
        db.collection("eventos")
                .document(eventoId)
                .get()
                .addOnSuccessListener(doc -> {

                    if (!doc.exists()) return;

                    // Puxa direto do Firestore
                    String titulo     = doc.getString("titulo");
                    String local      = doc.getString("local");
                    String data       = doc.getString("data");
                    String categoria  = doc.getString("categoria");
                    String descricao  = doc.getString("descricao");
                    String imagemUrl  = doc.getString("imagemUrl");
                    autorId           = doc.getString("idUsuarioCriador");

                    tituloEvento = titulo;
                    localEvento = local;

                    txtTituloEvento.setText(titulo);
                    txtLocal.setText("Local: " + local);
                    txtData.setText("Data: " + data);
                    txtCategoria.setText("Categoria: " + categoria);
                    txtDescricao.setText(descricao);

//                    if (imagemUrl != null && !imagemUrl.isEmpty()) {
//                        Glide.with(this).load(imagemUrl).into(imgEvento);
//                    }

                    carregarAutor(autorId);
                });
    }


    private void carregarAutor(String autorUid) {
        db.collection("usuarios")
                .document(autorUid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) return;

                    String nomeAutor = doc.getString("nome");
//                    String fotoAutor = doc.getString("imagemPerfil");

                    txtNomeAutor.setText(nomeAutor);

//                    if (fotoAutor != null && !fotoAutor.isEmpty()) {
//                        Glide.with(this)
//                                .load(fotoAutor)
//                                .placeholder(R.drawable.ic_user)
//                                .into(imgFotoAutor);
//                    }
                });
    }

    public void abrirNoMapa() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(localEvento));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void compartilharEvento() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String texto = "Evento: " + tituloEvento +
                "\nLocal: " + localEvento +
                "\nVeja na Agenda Botucatu!";

        intent.putExtra(Intent.EXTRA_TEXT, texto);
        startActivity(Intent.createChooser(intent, "Compartilhar evento via"));
    }

    public void marcarComoFavorito() {
        btFavoritar.setImageResource(R.drawable.ic_favoritos);



        // todo: Aqui você vai adicionar depois a lógica real:
        //
        // db.collection("usuarios")
        //   .document(usuarioId)
        //   .collection("favoritos")
        //   .document(eventoId)
        //   .set(...)
        //
        // OU
        //
        // db.collection("eventos")
        //   .document(eventoId)
        //   .update("favoritadosPor", FieldValue.arrayUnion(usuarioId))
    }

}