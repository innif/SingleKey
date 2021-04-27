package com.example.singlekey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MasterKeyService {
    String masterkey;
    Context context;

    private void applyPassword(String input, boolean remember){
        //TODO check input
        masterkey = input;
        if(remember){
            SharedPreferences.Editor editor = context.getSharedPreferences("app-storage", Context.MODE_PRIVATE).edit();
            editor.putString("masterpass", input);
            editor.apply();
        }
    }

    private void startKeyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.enter_masterkey_dialog, null);
        final EditText passfield = dialogView.findViewById(R.id.dialog_masterpass);
        final CheckBox remeber = dialogView.findViewById(R.id.dialog_rememberPass);
        builder.setTitle(R.string.masterkey_dialog_title);
        builder.setMessage(R.string.masterkey_dialog_text);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                applyPassword(passfield.getText().toString(), remeber.isSelected());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO cancel
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public MasterKeyService(Context context){
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("app-storage", Context.MODE_PRIVATE);
        masterkey = sharedPreferences.getString("masterpass", "");

        if ( masterkey == null || masterkey.equals("")){
            startKeyDialog();
        }
    }

    public String getMasterkey() {
        return masterkey;
    }
}
