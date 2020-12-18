package com.task.imager.Custom;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.task.imager.R;

import static com.task.imager.Fragment.RandomImageFragment.TAG;

public class DialogInfo extends DialogFragment {
    private TextView description;
    private TextView width;
    private TextView height;
    private TextView ref;

    public DialogInfo(){

    }

    public static DialogInfo newInstance(int height, int width, String description, String ref) {
        Log.d(TAG, "DialogInfo: newInstance: args: " + height + width + description + ref);
        Bundle args = new Bundle();
        args.putInt("height", height);
        args.putInt("width", width);
        args.putString("description", description);
        args.putString("ref", ref);

        DialogInfo fragment = new DialogInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_info, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(li.getContext(), R.style.Theme_AppCompat_Dialog);
        mDialogBuilder.setView(promptsView);

        description = promptsView.findViewById(R.id.text_description);
        width = promptsView.findViewById(R.id.text_width);
        height = promptsView.findViewById(R.id.text_height);
        ref = promptsView.findViewById(R.id.text_ref);

        description.setText(getArguments().getString("description"));
        width.setText(
                "Width: " +
                String.valueOf(
                        getArguments().getInt("width")
                )
        );
        height.setText(
                "Height: " +
                String.valueOf(
                        getArguments().getInt("height")
                )
        );
        ref.setText(
                "Ref: " +
                getArguments().getString("ref")
        );

        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
        return  alertDialog;
    }
}
