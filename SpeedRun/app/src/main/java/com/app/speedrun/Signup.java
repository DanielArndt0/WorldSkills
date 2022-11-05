package com.app.speedrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.speedrun.databinding.ActivitySignupBinding;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();

    }

    private void init() {
        setSupportActionBar(binding.toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        listenters();
    }

    private void listenters() {

        binding.sigSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sName = binding.sigNameText.getText().toString();
                String sEmail = binding.sigEmailText.getText().toString();
                String sUser = binding.sigUserText.getText().toString();
                String sPassword = binding.sigPasswordText.getText().toString();
                String sRepassword = binding.sigRepasswordText.getText().toString();


                DAO dao = new DAO(getApplicationContext(), "app", sUser);
                FieldCheck fieldCheck = new FieldCheck();
                if (fieldCheck.execute(Arrays.asList(
                        new Check(binding.sigNameBox, !fieldCheck.constraint(sName, "[a-zA-Z0-9_.] [a-zA-Z0-9_.]"), "Nome deve conter sobrenome"),
                        new Check(binding.sigEmailBox, !fieldCheck.constraint(sEmail, "^[a-zA-Z_.][a-zA-Z0-9_.]{5,}@[a-zA-Z0-9_.]{3,}[.com|.com.br]$"), "Email inválido"),
                        new Check(binding.sigUserBox, !fieldCheck.constraint(sUser, "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z_.][A-Za-z0-9_.]{5,}$") || sUser.equals(dao.get("user")), "Usuário inválido"),
                        new Check(binding.sigPasswordBox, !fieldCheck.constraint(sPassword, "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[0-9])[A-Za-z0-9_.]{7,}$"), "Senha inválida"),
                        new Check(binding.sigRepasswordBox, !sRepassword.equals(sPassword), "Este campo deve ser igual a senha")
                ))) {
                    dao.set("user", sUser);
                    dao.set("name", sName);
                    dao.set("email", sEmail);
                    dao.set("password", sPassword);
                    Toast.makeText(Signup.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this, Login.class));
                }

                fieldCheck.clear();
            }
        });


        binding.sigCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlert();
            }
        });
    }

    private void buildAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Alerta")
                .setMessage("Deseja realmente sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Signup.this, Login.class));
                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            buildAlert();
        }
        return super.onOptionsItemSelected(item);
    }
}