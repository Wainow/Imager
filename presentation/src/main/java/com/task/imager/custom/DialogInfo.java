package com.task.imager.custom;

import android.annotation.SuppressLint;
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

import com.bumptech.glide.Glide;
import com.task.domain.Root;
import com.task.imager.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.task.imager.fragment.RandomImageFragment.TAG;

public class DialogInfo extends DialogFragment {

    public DialogInfo(){

    }

    public static DialogInfo newInstance(int height, int width, String description, Root.Urls image) {
        Log.d(TAG, "DialogInfo: newInstance: args: " + height + width + description + image.getFull());
        Bundle args = new Bundle();
        args.putInt("height", height);
        args.putInt("width", width);
        args.putString("description", description);
        args.putString("ref", image.getFull());
        args.putString("image", image.getThumb());

        DialogInfo fragment = new DialogInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_info, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(li.getContext(), R.style.Theme_AppCompat_Dialog);
        mDialogBuilder.setView(promptsView);

        TextView description = promptsView.findViewById(R.id.text_description);
        TextView width = promptsView.findViewById(R.id.text_width);
        TextView height = promptsView.findViewById(R.id.text_height);
        TextView ref = promptsView.findViewById(R.id.text_ref);
        CircleImageView mini_img = promptsView.findViewById(R.id.mini_img);

        if(getArguments() != null && getContext() != null) {
            Glide.with(getContext())
                    .load(
                            getArguments().getString("image")
                    )
                    .into(mini_img);

            if(getArguments().getString("description") == null){
                description.setVisibility(View.INVISIBLE);
            } else
                description.setText(getArguments().getString("description"));
            width.setText(
                    "Width: " +
                            getArguments().getInt("width")
                            + "px"
            );
            height.setText(
                    "Height: " +
                            getArguments().getInt("height")
                            + "px"
            );
            ref.setText(
                    "Ref: " +
                            getArguments().getString("ref")
            );
        }
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
        return  alertDialog;
    }
}
