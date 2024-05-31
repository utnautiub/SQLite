package com.example.btt_sqlite_vinfast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btt_sqlite_vinfast.model.User;
import com.example.btt_sqlite_vinfast.repository.UserRepository;


public class SignupActivity extends AppCompatActivity {
    EditText edtTen, edtTenDangNhap, edtMatKhau, edtXacNhanMatKhau;
    Button btnDangKy;

    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_signup); // Sửa lại đúng tên layout

        edtTen = findViewById(R.id.edtTen);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);

        userRepository = new UserRepository(this); // Khởi tạo UserRepository

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        String name = edtTen.getText().toString().trim();
        String username = edtTenDangNhap.getText().toString().trim();
        String password = edtMatKhau.getText().toString().trim();
        String rePassword = edtXacNhanMatKhau.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(rePassword)) {
            Toast.makeText(SignupActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sử dụng UserRepository để đăng ký người dùng
        User newUser = new User(0, username, password, name, "user");
        userRepository.addUser(newUser);
        Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


}

