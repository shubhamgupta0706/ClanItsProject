package com.foxoGyan.clanits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private TextView txtForgotPass;
    private EditText login_username,login_password;
    private TextInputLayout loginUsernameInputLayout,loginPasswordInputLayout;
    Animation animShake;
    Vibrator vib;
    private Button btnSignin,btn_gotoRegpage;
     boolean doubleBackToExitPressedOnce=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtForgotPass=(TextView) findViewById(R.id.txtForgotPass);
        login_username=(EditText)findViewById(R.id.login_username);
        login_password=(EditText)findViewById(R.id.login_password);
        btnSignin=(Button)findViewById(R.id.btnSignin);
        btn_gotoRegpage=(Button)findViewById(R.id.btn_gotoRegpage);
        loginUsernameInputLayout=(TextInputLayout)findViewById(R.id.loginUsernameInputLayout);
        loginPasswordInputLayout=(TextInputLayout)findViewById(R.id.loginPasswordInputLayout);

        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);//Animation
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//Vibration

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLoginForm();
            }
        });
        btn_gotoRegpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void submitLoginForm(){
        if (!checkEmail()) {
            login_username.setAnimation(animShake);
            login_username.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        if (!checkPassword()) {
            login_password.setAnimation(animShake);
            login_password.startAnimation(animShake);
            vib.vibrate(120);
            return;
        }
        loginUsernameInputLayout.setErrorEnabled(false);
        loginPasswordInputLayout.setErrorEnabled(false);
        Toast.makeText(getApplicationContext(),"User Logged In Seccessfully!!",Toast.LENGTH_SHORT).show();
    }

    private boolean checkEmail() {
        String email = login_username.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {

            loginUsernameInputLayout.setErrorEnabled(true);
            loginUsernameInputLayout.setError(getString(R.string.err_msg_email));
            login_username.setError(getString(R.string.err_msg_required));
            requestFocus(login_username);
            return false;
        }
        loginUsernameInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {
        if (login_password.getText().toString().trim().isEmpty()) {

            loginPasswordInputLayout.setError(getString(R.string.err_msg_password));
            requestFocus(login_password);
            return false;
        }
        loginPasswordInputLayout.setErrorEnabled(false);
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 5000);
    }
}
