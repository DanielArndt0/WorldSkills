package com.app.speedrun;

import com.google.android.material.textfield.TextInputLayout;

public class Check {
    private final TextInputLayout textBox;
    private final boolean isTrue;
    private final String message;

    public Check(TextInputLayout textBox, boolean isTrue, String message) {
        this.textBox = textBox;
        this.isTrue = isTrue;
        this.message = message;
    }

    public TextInputLayout getTextBox() {
        return textBox;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public String getMessage() {
        return message;
    }
}
