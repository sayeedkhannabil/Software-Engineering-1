package comp3350.grs.presentation;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
// CLASS: Utilities...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// a group of useful functions
//-----------------------------------------
public class Utilities {

    //create an alert dialog which has one button to cancel the dialog
    public static AlertDialog createAlertDialog(String message, Context context){
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        return alert11;
    }
}
