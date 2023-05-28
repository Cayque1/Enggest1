package br.com.cayque.dev.enggest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityOs extends AppCompatActivity {

    private EditText editTextCliente;
    private EditText editTextNumeroSerie;
    private EditText editTextPatrimonio;
    private EditText editTextReclamacao;
    private EditText editTextServicoRealizado;
    private EditText editTextDataHora;

    private EditText editTextPesquisa;
    private Button buttonPesquisar;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os);

        editTextCliente = findViewById(R.id.editTextCliente);
        editTextNumeroSerie = findViewById(R.id.editTextNumeroSerie);
        editTextPatrimonio = findViewById(R.id.editTextPatrimonio);
        editTextReclamacao = findViewById(R.id.editTextReclamacao);
        editTextServicoRealizado = findViewById(R.id.editTextServicoRealizado);
        editTextDataHora = findViewById(R.id.editTextDataHora);

        editTextPesquisa = findViewById(R.id.editTextPesquisa);
        buttonPesquisar = findViewById(R.id.buttonPesquisar);

        firestore = FirebaseFirestore.getInstance();

        Button buttonEnviarOS = findViewById(R.id.buttonEnviarOS);
        buttonEnviarOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarOrdemServico();
            }
        });

        buttonPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisarOrdemServico();
            }
        });
    }

    private void enviarOrdemServico() {
        // Obter os valores dos campos de entrada
        String cliente = editTextCliente.getText().toString().trim();
        String numeroSerie = editTextNumeroSerie.getText().toString().trim();
        String patrimonio = editTextPatrimonio.getText().toString().trim();
        String reclamacao = editTextReclamacao.getText().toString().trim();
        String servicoRealizado = editTextServicoRealizado.getText().toString().trim();
        String dataHora = editTextDataHora.getText().toString().trim();

        // Verificar se algum campo está vazio
        if (cliente.isEmpty() || numeroSerie.isEmpty() || patrimonio.isEmpty() || reclamacao.isEmpty()
                || servicoRealizado.isEmpty() || dataHora.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar um mapa com os dados da ordem de serviço
        Map<String, Object> ordemServico = new HashMap<>();
        ordemServico.put("cliente", cliente);
        ordemServico.put("numeroSerie", numeroSerie);
        ordemServico.put("patrimonio", patrimonio);
        ordemServico.put("reclamacao", reclamacao);
        ordemServico.put("servicoRealizado", servicoRealizado);
        ordemServico.put("dataHora", dataHora);

        // Obter uma referência para a coleção "ordensServico" no Firestore
        firestore.collection("ordensServico")
                .document()
                .set(ordemServico)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ActivityOs.this, "Ordem de serviço enviada com sucesso", Toast.LENGTH_SHORT).show();
                        limparCampos();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityOs.this, "Erro ao enviar ordem de serviço", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pesquisarOrdemServico() {
        String pesquisa = editTextPesquisa.getText().toString().trim();
        // Executar a lógica de pesquisa no Firestore aqui
        Toast.makeText(ActivityOs.this, "Pesquisar: " + pesquisa, Toast.LENGTH_SHORT).show();
    }

    private void limparCampos() {
        editTextCliente.setText("");
        editTextNumeroSerie.setText("");
        editTextPatrimonio.setText("");
        editTextReclamacao.setText("");
        editTextServicoRealizado.setText("");
        editTextDataHora.setText("");
    }
}

