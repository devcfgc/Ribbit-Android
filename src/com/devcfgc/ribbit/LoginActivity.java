package com.devcfgc.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class LoginActivity extends Activity {

	protected EditText mUserName;
	protected EditText mPassword;
	protected Button mLoginButton;

	protected TextView mSignUpTextView;
	protected TextView mForgotPassTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this method must be called before setContentView and give us a progress indicator to on or off
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);

		mSignUpTextView = (TextView) findViewById(R.id.signUpText);
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		mForgotPassTextView = (TextView) findViewById(R.id.forgotpassText);
		mForgotPassTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				final EditText emailInput = new EditText(LoginActivity.this);
				emailInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
				
				AlertDialog.Builder alertForgotPass = new AlertDialog.Builder(LoginActivity.this);
				alertForgotPass.setMessage(R.string.forgot_password_message)
						.setTitle(R.string.forgot_password_title)
						.setView(emailInput)
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String emailValue = emailInput.getText().toString();
								ParseUser.requestPasswordResetInBackground(emailValue, new RequestPasswordResetCallback() {
									
									@Override
									public void done(ParseException e) {
										if (e == null) {
									      // An email was successfully sent with reset instructions. and get back to login page
									    } else {
									    	AlertDialog.Builder builderAux = new AlertDialog.Builder(LoginActivity.this);
									    	builderAux.setMessage(e.getMessage())
													.setTitle(R.string.forgot_password_error_email_title)
													.setPositiveButton(android.R.string.ok, null);
											AlertDialog dialogAux = builderAux.create();
											dialogAux.show();
									    }
									}
								});
							}
						});
				AlertDialog dialogForgotPass = alertForgotPass.create();
				dialogForgotPass.show();
			}
		});

		mUserName = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mLoginButton = (Button) findViewById(R.id.loginButton);

		mLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = mUserName.getText().toString();
				String password = mPassword.getText().toString();

				username = username.trim();
				password = password.trim();

				if (username.isEmpty() || password.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setMessage(R.string.login_error_message)
							.setTitle(R.string.login_error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					//Login
					setProgressBarIndeterminateVisibility(true);
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						@Override
						public void done(ParseUser user, ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							if (e == null) {
								//Success
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							} else {
								AlertDialog.Builder builder = new AlertDialog.Builder(
										LoginActivity.this);
								builder.setMessage(e.getMessage())
										.setTitle(R.string.login_error_title)
										.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
