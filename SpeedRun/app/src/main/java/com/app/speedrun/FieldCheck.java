package com.app.speedrun;

import java.util.List;
import java.util.regex.Pattern;

public class FieldCheck {
    private List<Check> checkList;

    public boolean execute(List<Check> checkList) {
        this.checkList = checkList;
        boolean fValid = true;
        for (Check check : checkList) {
            if (check.isTrue()) {
                check.getTextBox().setError(check.getMessage());
                fValid = false;
            }
        }
        return fValid;
    }

    public void clear() {
        if (checkList != null) {
            for (Check check : checkList) {
                if (!check.isTrue())
                    check.getTextBox().setErrorEnabled(false);
            }
        }
    }

    public boolean constraint(String str, String constraint) {
        return Pattern.compile(constraint).matcher(str).find();
    }
}
