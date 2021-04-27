package com.example.singlekey;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    PassGenerationService passGenerationService;
    MasterKeyService keyService;

    public void copyPassword(){
        //TODO copy Password to Clipboard
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton copyButton = findViewById(R.id.fab);
        final EditText input = findViewById(R.id.et_service);
        final TextView password = findViewById(R.id.tv_password);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                CharSequence input = textView.getText();
                passGenerationService.setMasterPass(keyService.getMasterkey());
                password.setText(passGenerationService.generatePassword(input));
                return false;
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyPassword();
                Snackbar.make(view, R.string.snackbar_copied, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snackbar_copied, null).show();
            }
        });

        passGenerationService = new PassGenerationService("SALT");
        passGenerationService.setMasterPass("Baum"); // TODO
        keyService = new MasterKeyService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}