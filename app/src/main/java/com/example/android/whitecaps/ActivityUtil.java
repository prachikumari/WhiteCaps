package com.example.android.whitecaps;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Pramod on 05/05/17.
 */

class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity() {
        context.startActivity(new Intent(context, Teacher_Dashboard.class));
    }
}
