package com.example.joquempo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private BancoHelper bancoHelper;
    private int pontosUsuario, pontosMaquina;
    private TextView textResultado;
    private TextView textPlacar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bancoHelper = new BancoHelper(this);

        textResultado = findViewById(R.id.textResultado);
        textPlacar = findViewById(R.id.textPlacar);

        // Carrega o placar do banco
        int[] placar = bancoHelper.obterPlacar();
        pontosUsuario = placar[0];
        pontosMaquina = placar[1];

        atualizarPlacarUI();
    }

    public void selecionadoPedra(View view) {
        opcaoSelecionada("pedra");
    }

    public void selecionadoPapel(View view) {
        opcaoSelecionada("papel");
    }

    public void selecionadoTesoura(View view) {
        opcaoSelecionada("tesoura");
    }

    public void opcaoSelecionada(String opcaoSelecionada) {
        ImageView imageResultado = findViewById(R.id.imageResultado);

        String[] opcoes = {"pedra", "papel", "tesoura"};
        String opcaoApp = opcoes[new Random().nextInt(3)];

        switch (opcaoApp) {
            case "pedra":
                imageResultado.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imageResultado.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imageResultado.setImageResource(R.drawable.tesoura);
                break;
        }

        if ((opcaoApp.equals("tesoura") && opcaoSelecionada.equals("papel")) ||
                (opcaoApp.equals("papel") && opcaoSelecionada.equals("pedra")) ||
                (opcaoApp.equals("pedra") && opcaoSelecionada.equals("tesoura"))) {

            textResultado.setText("Você perdeu!");
            pontosMaquina++;

        } else if ((opcaoSelecionada.equals("tesoura") && opcaoApp.equals("papel")) ||
                (opcaoSelecionada.equals("papel") && opcaoApp.equals("pedra")) ||
                (opcaoSelecionada.equals("pedra") && opcaoApp.equals("tesoura"))) {

            textResultado.setText("Você Ganhou!");
            pontosUsuario++;

        } else {
            textResultado.setText("Empatou!");
        }

        bancoHelper.atualizarPlacar(pontosUsuario, pontosMaquina);
        atualizarPlacarUI();
    }

    private void atualizarPlacarUI() {
        textPlacar.setText("Você: " + pontosUsuario + " | Máquina: " + pontosMaquina);
    }

    // 🆕 Método para zerar o placar
    public void zerarPlacar(View view) {
        pontosUsuario = 0;
        pontosMaquina = 0;
        bancoHelper.atualizarPlacar(pontosUsuario, pontosMaquina);
        atualizarPlacarUI();
        textResultado.setText("Placar zerado. Jogue novamente!");
    }
}
