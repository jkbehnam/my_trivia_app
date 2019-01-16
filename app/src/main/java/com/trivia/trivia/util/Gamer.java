package com.trivia.trivia.util;

public class Gamer {
    private String name;
    private String phone_number;
    private String reg_time;
    private String password;
    public Gamer(String _name, String _phone_number, String password, String _reg_time ){
        setName(_name);
        setPhone_number(_phone_number);
        setPassword(password);
        setReg_time(_reg_time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
