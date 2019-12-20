package com.example.ismthebuilder.LogIn.LogIn;

public class LogInModel
{
    String email, password, level;

    public LogInModel() {
    }

    public LogInModel(String email, String password, String level) {
        this.email = email;
        this.password = password;
        this.level = level;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLevel() {
        return level;
    }
}
