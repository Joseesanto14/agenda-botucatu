package com.example.agendabotucatu.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.models.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadEventoActivity extends AppCompatActivity {

    private static final String IMAGEM_PADRAO =
            "https://pixabay.com/pt/photos/evento-festa-eventos-noite-de-festa-3005668/";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private EditText edTitulo, edDescricao, edLocal, edData, edHora,edCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_evento);

        // Inicializar views
        edTitulo = findViewById(R.id.edTitulo);
        edDescricao = findViewById(R.id.edDescricao);
        edLocal = findViewById(R.id.edLocal);
        edData = findViewById(R.id.edData);
        edHora = findViewById(R.id.edHora);
        edCategoria = findViewById(R.id.edCategoria);

    }

    public void voltarFeed(View view){
        finish();
    }

    public void salvarEvento(View view) {

        // ✔ Validar campos obrigatórios
        String titulo = edTitulo.getText().toString().trim();
        String descricao = edDescricao.getText().toString().trim();
        String local = edLocal.getText().toString().trim();
        String data = edData.getText().toString().trim();
        String hora = edHora.getText().toString().trim();
        String categoria = edCategoria.getText().toString().trim();

        if (titulo.isEmpty() || descricao.isEmpty() || local.isEmpty() ||
                data.isEmpty() || hora.isEmpty() || categoria.isEmpty()) {

            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Combinar data + hora
        String dataHora = data + " - " + hora;

        // ID do usuário logado
        String idUsuarioCriador = auth.getCurrentUser() != null ?
                auth.getCurrentUser().getUid() :
                "desconhecido";

        Evento novoEvento = new Evento(titulo, descricao, dataHora, local, categoria, idUsuarioCriador, IMAGEM_PADRAO,0);

        // Salvar no Firestore
        db.collection("eventos")
                .add(novoEvento)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Evento salvo!", Toast.LENGTH_SHORT).show();
                    finish();  // volta para o Feed
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Erro ao cadastrar: " + e.getMessage(),
                                Toast.LENGTH_LONG).show()
                );
    }
}