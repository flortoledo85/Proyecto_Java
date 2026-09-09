package com.talentotech;

public class Cliente {
    private String user;
    private String email;
    
    public Cliente(String user, String email) {
        setUser(user);
        setMail(email);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if (user != null && !user.trim().isEmpty()) {
            this.user = user;
        }
    }

     public String getMail() {
        return email;
    }

    public void setMail(String email) {

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (email != null && email.matches(regex)) {
            this.email = email;
        }
    }
}
