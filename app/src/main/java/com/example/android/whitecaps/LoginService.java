package com.example.android.whitecaps;

/**
 * Created by Harshita on 05/05/17.
 */

class LoginService {
    public boolean login(String username, String password) {
        return "tamal@gmail.com".equals(username) && "tamal123".equals(password);
    }
}
