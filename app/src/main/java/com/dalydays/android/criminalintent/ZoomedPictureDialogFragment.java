package com.dalydays.android.criminalintent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class ZoomedPictureDialogFragment extends DialogFragment {

    private static final String LOG_TAG = "ZoomedPictureDebug";

    private static final String ARG_FILE = "file";
    private ImageView mZoomedPhotoView;

    public static ZoomedPictureDialogFragment newInstance(File imageFile) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE, imageFile);

        ZoomedPictureDialogFragment fragment = new ZoomedPictureDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        File imageFile = (File) getArguments().getSerializable(ARG_FILE);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_zoomed_image, null);

        mZoomedPhotoView = v.findViewById(R.id.dialog_zoomed_image);

        if (imageFile == null || !imageFile.exists()) {
            mZoomedPhotoView.setImageDrawable(null);
        } else {
            Log.d(LOG_TAG, "width: " + mZoomedPhotoView.getWidth());
            Bitmap bitmap = PictureUtils.getScaledBitmap(imageFile.getPath(), mZoomedPhotoView.getWidth(), mZoomedPhotoView.getHeight());
            mZoomedPhotoView.setImageBitmap(bitmap);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.zoomed_photo_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
    }
}
