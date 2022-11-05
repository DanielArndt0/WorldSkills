package com.app.speedrun;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.speedrun.databinding.ActivityCadastroBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;

public class Cadastro extends AppCompatActivity {

    ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        listeners();
        camCheckBoxes();
        corCheckBoxes();
        cordCheckBoxes();
    }

    private void camCheckBoxes() {
        new CheckboxGroup(Arrays.asList(
                binding.CamDom,
                binding.CamSeg,
                binding.CamTer,
                binding.CamQua,
                binding.CamQui,
                binding.CamSex,
                binding.CamSab
        ), binding.CamMaster.isChecked());
        binding.CamTextBox.setEnabled(binding.CamMaster.isChecked());
    }

    private void corCheckBoxes() {
        new CheckboxGroup(Arrays.asList(
                binding.CorDom,
                binding.CorSeg,
                binding.CorTer,
                binding.CorQua,
                binding.CorQui,
                binding.CorSex,
                binding.CorSab
        ), binding.CorMaster.isChecked());
        binding.CorTextBox.setEnabled(binding.CorMaster.isChecked());
    }

    private void cordCheckBoxes() {
        new CheckboxGroup(Arrays.asList(
                binding.CordDom,
                binding.CordSeg,
                binding.CordTer,
                binding.CordQua,
                binding.CordQui,
                binding.CordSex,
                binding.CordSab
        ), binding.CordMaster.isChecked());
        binding.CordTextBox.setEnabled(binding.CordMaster.isChecked());
    }

    private void listeners() {
        binding.CorMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corCheckBoxes();
            }
        });

        binding.CamMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camCheckBoxes();
            }
        });

        binding.CordMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cordCheckBoxes();
            }
        });


        binding.DateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment date = new DatePicker(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = Calendar.getInstance();
                        c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                        binding.DateText.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
                    }
                }, Calendar.getInstance());
                date.show(getSupportFragmentManager(), "");
            }
        });

        setTimer(binding.CordHor);
        setTimer(binding.CorHor);
        setTimer(binding.CamHor);

        binding.SaveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                DAO dao = new DAO(getApplicationContext(), "app", "access");
                FieldCheck fieldCheck = new FieldCheck();
                Period between = TimeUtil.between(binding.DateText.getText().toString(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "dd/MM/yyyy");

                if (fieldCheck.execute(Arrays.asList(
                        new Check(binding.PesoBox, binding.PesoText.getText().toString().isEmpty(), "Campo obrigatório!"),
                        new Check(binding.AlturaBox, binding.AlturaText.getText().toString().isEmpty(), "Campo obrigatório!"),
                        new Check(binding.DateBox, binding.DateText.getText().toString().isEmpty() || between.getYears() < 18, "Campo obrigatório!\n- Usuário precisa ser maior de idade")
                ))) {
                    dao.set("normalLog", "true");
                    Toast.makeText(Cadastro.this, "Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                }
                fieldCheck.clear();
            }
        });

        binding.AlturaText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    imc();
                } catch (Exception ignore) {

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.PesoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    imc();
                } catch (Exception ignore) {

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setTimer(TextInputEditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment time = new TimerPicker(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar c = Calendar.getInstance();
                        c.set(0, 0, 0, timePicker.getHour(), timePicker.getMinute());
                        editText.setText(new SimpleDateFormat("HH:mm").format(c.getTime()));
                    }
                }, Calendar.getInstance());
                time.show(getSupportFragmentManager(), "");
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void imc() {
        double peso = Double.parseDouble(binding.PesoText.getText().toString());
        double altura = Double.parseDouble(binding.AlturaText.getText().toString());
        double imc = peso / (altura * altura);

        if (imc < 18.5) {
            binding.imcText.setText(String.format("Seu IMC é de: %.2f, o que indica MAGREZA!", imc));
        } else if (imc > 18.5 && imc < 24.9) {
            binding.imcText.setText(String.format("Seu IMC é de: %.2f, o que indica NORMAL!", imc));
        } else if (imc > 25.0 && imc < 29.9) {
            binding.imcText.setText(String.format("Seu IMC é de: %.2f, o que indica SOBREPESO!", imc));
        } else if (imc > 30.0 && imc < 39.9) {
            binding.imcText.setText(String.format("Seu IMC é de: %.2f, o que indica OBESIDADE!", imc));
        } else if (imc >= 40.0) {
            binding.imcText.setText(String.format("Seu IMC é de: %.2f, o que indica OBESIDADE GRAVE!", imc));
        }
    }


}