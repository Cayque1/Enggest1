package br.com.cayque.dev.enggest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {

    private TextView nomeusuario,emailusuario;
    private Button bt_deslogar;
    private Button bt_os;
    private Button bt_equipamento;
    private Button bt_cliente;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        getSupportActionBar().hide();
        IniciarComponentes();
        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this,FormLogin.class);
                startActivity(intent);
                finish();

            }

        });


        bt_os.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this,ActivityOs.class);
                startActivity(intent);
                finish();

            }

        });


        bt_equipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this,ActivityEquipamento.class);
                startActivity(intent);
                finish();

            }

        });


        bt_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this,ActivityCliente.class);
                startActivity(intent);
                finish();

            }

        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
               if (documentSnapshot != null){
                   nomeusuario.setText(documentSnapshot.getString("nome"));
                   emailusuario.setText(email);

               }
            }
        });




    }

    private void IniciarComponentes(){

        nomeusuario = findViewById(R.id.textnomeusuario);
        emailusuario = findViewById(R.id.textemailusuario);
        bt_deslogar = findViewById(R.id.bt_deslogar);
        bt_os = findViewById(R.id.bt_os);
        bt_equipamento = findViewById(R.id.bt_equipamentos);
        bt_cliente = findViewById(R.id.bt_cliente);
    }
}