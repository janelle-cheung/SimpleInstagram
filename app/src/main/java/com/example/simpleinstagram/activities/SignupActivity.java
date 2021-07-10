package com.example.simpleinstagram.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simpleinstagram.databinding.ActivitySignupBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.etUsername.getText().toString();
                String password1 = binding.etPassword.getText().toString();
                String password2 = binding.etReenterPassword.getText().toString();
                if (!password1.equals(password2)) {
                    Toast.makeText(SignupActivity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password1);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignupActivity.this, "Account sign-up successful", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SignupActivity.this, "Issue with sign-up", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });
    }
}