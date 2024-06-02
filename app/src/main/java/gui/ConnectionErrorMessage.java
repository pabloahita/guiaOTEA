package gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.fundacionmiradas.indicatorsevaluation.R;

public class ConnectionErrorMessage {

    public static void showMsg(Context context){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.con_err_title)
                .setMessage(R.string.con_err)
                .setIcon(android.R.drawable.stat_notify_error).create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Este bloque se ejecuta cuando el diálogo es cerrado de cualquier forma (botón, fuera del diálogo, etc.)
                Intent intent = new Intent(context, gui.MainActivity.class);
                context.startActivity(intent);
            }
        });

        dialog.show();
    }
}
