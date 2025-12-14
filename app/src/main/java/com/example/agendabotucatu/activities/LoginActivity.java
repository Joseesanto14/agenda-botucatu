package com.example.agendabotucatu.activities;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agendabotucatu.R;
import com.example.agendabotucatu.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "EmailSenha";
    private FirebaseAuth mAuth;
    private EditText edEmail, edSenha;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            abrirNavegacao(currentUser);
        }
    }

    private void abrirNavegacao(FirebaseUser user){
        Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginUsuario(View view){
        if (edEmail.getText().toString().isEmpty() || edSenha.getText().toString().isEmpty()){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(edEmail.getText().toString(),edSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"sigInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            edEmail.setText("");
                            edSenha.setText("");
                            abrirNavegacao(user);
                        } else {
                            Log.w(TAG, "sigInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Falha na autenticação!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "signInWithEmail:failure", e.getCause());
                        Toast.makeText(LoginActivity.this, "Falha na autenticação!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void abrirCadastro(View view){
        Intent intent = new Intent(getApplicationContext(),CadastroActivity.class);
        startActivity(intent);
        finish();
    }
}