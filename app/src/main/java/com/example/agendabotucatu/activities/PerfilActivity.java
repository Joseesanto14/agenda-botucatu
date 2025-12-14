package com.example.agendabotucatu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseFirestore db;
    Usuario usuario;
    EditText edEmail, edTipoConta;
    TextView txtNomeUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edEmail = findViewById(R.id.edEmail);
        edTipoConta = findViewById(R.id.edTipoConta);
        txtNomeUsuario = findViewById(R.id.txtNomeUsuario);

        // Inicializa o FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        // Chamada para buscar as informações do usuário do Firestore
        buscarInfos();

    }

    public void sairConta(View view){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void buscarInfos(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) { // Verifica se há um usuário logado
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
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}