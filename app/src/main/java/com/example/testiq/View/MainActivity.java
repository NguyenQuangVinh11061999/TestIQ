package com.example.testiq.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testiq.Model.Info;
import com.example.testiq.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    TextView edtEmail, edtPass;
    Button btnDangNhap, btnThoat;
    public Info info = null;
    SharedPreferences sharedPreferences;
    CheckBox chkCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        info = new Info();
        sharedPreferences = getSharedPreferences("LuuTT", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "");
        String passWord = sharedPreferences.getString("password", "");
        edtEmail.setText(userName);
        edtPass.setText(passWord);

        Boolean isCheck = sharedPreferences.getBoolean("check", false);
        chkCheck.setChecked(isCheck);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtEmail.getText().toString();
                String passWord = edtPass.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                info.setPassWord(edtPass.getText().toString());
                info.setUser(edtEmail.getText().toString());
                String user = info.getUser().toString();
                String pass = info.getPassWord().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Bạn cần điền đầy đủ thông tin !!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (check_ki_tu_dac_biet() == true && check_do_dai() == true && check_chu_so() == true) {
                        if (chkCheck.isChecked()) {
                            //Luu thong tin vao file Xml

                            editor.putString("username", userName);
                            editor.putString("password", passWord);
                            editor.putBoolean("check", chkCheck.isChecked()); // hoac true

                            editor.commit();

                        } else {
                            //Bo luu thong tin ( xoa du lieu )
                            editor.remove("username");
                            editor.remove("password");
                            editor.remove("check");
                            editor.commit();
                        }

                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void mapping() {
        chkCheck = findViewById(R.id.chkCheck);
        btnThoat = findViewById(R.id.btnThoat);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }

    public boolean check_ki_tu_dac_biet() {
        String password = info.getPassWord();
        int dem = 0;
        char[] mang = {'@', '!', '#', '%', '&', '-', '_', '+', '=', '?'};

        for (int i = 0; i < mang.length; i++) {
            for (int j = 0; j < password.length(); j++) {
                if (mang[i] == password.charAt(j)) {
                    dem++;
                }
            }
        }

        if (dem == 1)
            return true;
        else
            return false;
    }

    public boolean check_do_dai() {
        String password = info.getPassWord();
        if (password.length() > 6)
            return true;
        else
            return false;
    }

    public boolean check_chu_so() {
        String password = info.getPassWord();
        int dem = 0;
        for (int i = 0; i < password.length(); i++) {
            String kitu = String.valueOf(password.charAt(i));
            int j = 0;
            while (j <= 9) {
                if (kitu.equals("" + j + ""))
                    dem++;
                j++;
            }

        }

        if (dem > 0)
            return true;
        return false;
    }


}