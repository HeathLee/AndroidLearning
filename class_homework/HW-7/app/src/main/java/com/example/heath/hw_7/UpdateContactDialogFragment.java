package com.example.heath.hw_7;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by heath on 15-12-8.
 */
public class UpdateContactDialogFragment extends DialogFragment {

    public interface UpdateDialogListener {
        void onConfirmUpdate(Contact contact, int position);
    }

    UpdateDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UpdateDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must " +
                    "implement UpdateDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.detail, null);
        final EditText sidEditText = (EditText) view.findViewById(R.id.sid);
        final EditText nameEditText = (EditText) view.findViewById(R.id.name);
        final EditText phoneEditText = (EditText) view.findViewById(R.id.phone);

        sidEditText.setText(args.getString("sid"));
        nameEditText.setText(args.getString("name"));
        phoneEditText.setText(args.getString("phone"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("更改联系人")
                .setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sid = sidEditText.getText().toString();
                        String name = nameEditText.getText().toString();
                        String phone = phoneEditText.getText().toString();
                        if (TextUtils.isEmpty(sid) || TextUtils.isEmpty(name) ||
                                TextUtils.isEmpty(phone)) {
                            Toast.makeText(getActivity(), "请填写完整信息", Toast
                                    .LENGTH_SHORT).show();
                        } else {
                            mListener.onConfirmUpdate(new Contact(sid, name,
                                    phone), args.getInt("position"));
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
        return builder.create();
    }
}
