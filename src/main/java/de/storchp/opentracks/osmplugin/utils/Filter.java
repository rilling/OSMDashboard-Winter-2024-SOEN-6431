package de.storchp.opentracks.osmplugin.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Filter {
    public static void applyFilter(Context context) {
        final String[] filterOptions = {};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Filter")
                .setItems(filterOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedOption = filterOptions[which];
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
