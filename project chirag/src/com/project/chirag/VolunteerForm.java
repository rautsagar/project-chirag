package com.project.chirag;





import com.project.chirag.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class VolunteerForm extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volunteerform);

	}



	public void sendEmail(View v) {
		final EditText nameField = (EditText) findViewById(R.id.Name);
		String name = nameField.getText().toString();

		boolean validate = true;

		String gender = "";

		final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.radioGender);
		final int selectedId = genderRadioGroup.getCheckedRadioButtonId();
		final RadioButton radioGenderButton = (RadioButton) findViewById(selectedId);
		if (radioGenderButton.getText() != null) {
			gender = radioGenderButton.getText().toString();
		} else {
			validate = false;
		}

		final EditText emailField = (EditText) findViewById(R.id.Email);
		String email = emailField.getText().toString();

		final EditText addressField = (EditText) findViewById(R.id.Address);
		String address = addressField.getText().toString();

		final CheckBox emailAlertsCheckbox = (CheckBox) findViewById(R.id.emailAlerts);
		boolean emailAlerts = emailAlertsCheckbox.isChecked();

		if (name.length() < 3)
			validate = false;

		int atPos = email.indexOf("@");
		int dotPos = email.lastIndexOf(".");

		if (email == "" || atPos == 0 || dotPos < atPos
				|| dotPos + 1 >= email.length() || dotPos + 5 <= email.length()
				|| atPos == -1)
			validate = false;

		if (!validate) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Validation error");

			// set dialog message
			alertDialogBuilder
					.setMessage("Please enter proper values for all fields !!")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		} else {

			String message = "";
			message += "\n Name :            " + name;
			message += "\n Gender :          " + gender;
			message += "\n Email :           " + email;
			message += "\n Address :         " + address;
			message += "\n Email Alerts :    " + emailAlerts;

			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

			String[] recipients = new String[] { "sujeet1412@gmail.com" };

			emailIntent
					.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);

			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Project Chirag Volunteer Registration");

			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

			emailIntent.setType("text/plain");

			startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			finish();
		}
	}
}