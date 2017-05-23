package com.example.android.whitecaps;

/**
 * Created by Pramod on 05/05/17.
 */
public interface LoginView {
    String getUsername();

    void showUsernameError(int resId);

    String getPassword();

    void showPasswordError(int resId);

    void startMainActivity();

    void showLoginError(int resId);
}
