package com.example.dan.myapplication.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.dan.myapplication.R;
import com.example.dan.myapplication.util.PreferencesHelper;

/**
 * Created by Peppe on 26/11/2016.
 */

public class ServerSettingsDialog extends DialogFragment {
    public static final String TAG = "SERVER_SETTINGS";

    private EditText serverIp, serverPort;
    private ServerSettingsInterface listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ServerSettingsInterface) context;
        } catch (ClassCastException e) {
            Log.i(TAG, "L'activity deve implementare "+ServerSettingsInterface.class.getSimpleName());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_server_settings, null);

        serverIp = (EditText) view.findViewById(R.id.serverIp);
        serverPort = (EditText) view.findViewById(R.id.serverPort);

        builder.setView(view);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        PreferencesHelper.saveServerIp(getActivity(),serverIp.getText().toString());
                        PreferencesHelper.saveServerPort(getActivity(),Integer.parseInt(serverPort.getText().toString()));
                        listener.onSettingsSaved(serverIp.getText().toString(),Integer.parseInt(serverPort.getText().toString()));
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    public interface ServerSettingsInterface {
        void onSettingsSaved(String ip, int port);
    }
}
