package com.example.agendabotucatu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText edNome, edEmail, edSenha;
    private Usuario usuario;
    private static final String TAG = "EmailSenha";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        edNome = findViewById(R.id.edNome);
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
    }

    public void criarUsuario(View view) {
        if (edEmail.getText().toString().isEmpty() || edSenha.getText().toString().isEmpty()){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(edEmail.getText().toString(), edSenha.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");

                        String userId = mAuth.getCurrentUser().getUid();

                        usuario = new Usuario(edNome.getText().toString(), edEmail.getText().toString(), userId);

                        db.collection("usuarios").document(userId)
                                .set(usuario)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
                                    Toast.makeText(CadastroActivity.this, "Usuário criado com sucesso!",
                                            Toast.LENGTH_SHORT).show();

                                    edNome.setText("");
                                    edEmail.setText("");
                                    edSenha.setText("");
                                    abrirNavegacao();
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("FIRESTORE", "Error writing document", e);
                                    Toast.makeText(CadastroActivity.this, "Falha ao salvar dados do usuário.",
                                            Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CadastroActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void abrirNavegacao() {
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    public void voltarLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}