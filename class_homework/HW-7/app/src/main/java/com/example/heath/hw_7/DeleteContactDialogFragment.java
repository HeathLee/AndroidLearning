package com.example.heath.hw_7;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by heath on 15-12-7.
 */
public class DeleteContactDialogFragment extends DialogFragment {

    public interface DeleteDialogListener {
        void onConfirmDelete(Contact contact);
    }

    DeleteDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        final Contact contact = (Contact)arguments.getSerializable("contact");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("删除联系人")
                .setPositiveButton("删除", new DialogInterface.OnClickListener
                        () {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onConfirmDelete(contact);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener
                        () {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DeleteDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must " +
                    "implement DeleteDialogListener");
        }
    }
}
