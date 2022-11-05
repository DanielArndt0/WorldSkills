package com.app.speedrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.app.speedrun.databinding.ActivityLoginBinding;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    int errorCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        listeners();
    }

    private void listeners() {
        binding.logLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUser = binding.logUserText.getText().toString();
                String sPassword = binding.logPasswordText.getText().toString();

                DAO dao = new DAO(getApplicationContext(), "app", sUser);
                FieldCheck fieldCheck = new FieldCheck();
                if (fieldCheck.execute(Arrays.asList(
                        new Check(binding.logUserBox, !sUser.equals(dao.get("user")) || sUser.isEmpty(), "Usuário não encontrado"),
                        new Check(binding.logPasswordBox, !sPassword.equals(dao.get("password")), "Senha incorreta")
                ))) {
                    DAO dao1 = new DAO(getApplicationContext(), "app", "access");
                    dao1.set("logged", sUser);

                    if (!Boolean.parseBoolean(dao1.get("normalLog"))) {
                        startActivity(new Intent(Login.this, Cadastro.class));
                    } else {
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }
                    finish();

                } else {
                    errorCounter++;
                    checkErrors(3, 30000);
                }
                fieldCheck.clear();
            }
        });

        binding.logSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
                finish();
            }
        });
    }

    private void checkErrors(int errorCompare, long millis) {
        Toast.makeText(this, "Usuário/Senha inválidos!", Toast.LENGTH_SHORT).show();
        if (errorCounter == errorCompare) {
            Toast.makeText(this, "Login bloqueado: aguarde 30s!", Toast.LENGTH_SHORT).show();
            errorCounter = 0;
            enabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enabled(true);
                }
            }, millis);
        }
    }

    private void enabled(boolean status) {
        binding.logUserBox.setEnabled(status);
        binding.logPasswordBox.setEnabled(status);
        binding.logLoginButton.setEnabled(status);
        binding.logSignupButton.setEnabled(status);
    }
}