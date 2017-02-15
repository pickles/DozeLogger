package io.manabu.dozelogger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class InformationDialog extends DialogFragment {

    private String title = "";
    private String message = "";
    private boolean isCancelable = false;

    private String positiveButtonLabel = "OK";
    private DialogInterface.OnClickListener positiveButtonListener;

    private boolean showNegativeButton = false;
    private String negativeButtonLabel = "Cancel";
    private DialogInterface.OnClickListener negativeButtonListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setCancelable(isCancelable)
                .setMessage(message)
                .setPositiveButton(positiveButtonLabel, positiveButtonListener);

        if (showNegativeButton) {
            builder.setNegativeButton(negativeButtonLabel, negativeButtonListener);
        }

        return builder.create();
    }

    public boolean getIsCancelable() {
        return isCancelable;
    }

    public InformationDialog setIsCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public InformationDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getNegativeButtonLabel() {
        return negativeButtonLabel;
    }

    public InformationDialog setNegativeButtonLabel(String negativeButtonLabel) {
        this.negativeButtonLabel = negativeButtonLabel;
        return this;
    }

    public DialogInterface.OnClickListener getNegativeButtonListener() {
        return negativeButtonListener;
    }

    public InformationDialog setNegativeButtonListener(DialogInterface.OnClickListener negativeButtonListener) {
        this.negativeButtonListener = negativeButtonListener;
        return this;
    }

    public String getPositiveButtonLabel() {
        return positiveButtonLabel;
    }

    public InformationDialog setPositiveButtonLabel(String positiveButtonLabel) {
        this.positiveButtonLabel = positiveButtonLabel;
        return this;
    }

    public DialogInterface.OnClickListener getPositiveButtonListener() {
        return positiveButtonListener;
    }

    public InformationDialog setPositiveButtonListener(DialogInterface.OnClickListener positiveButtonListener) {
        this.positiveButtonListener = positiveButtonListener;
        return this;
    }

    public boolean isShowNegativeButton() {
        return showNegativeButton;
    }

    public InformationDialog setShowNegativeButton(boolean showNegativeButton) {
        this.showNegativeButton = showNegativeButton;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public InformationDialog setTitle(String title) {
        this.title = title;
        return this;
    }
}
