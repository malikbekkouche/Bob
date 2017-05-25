package com.example.malik.bob.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by malik on 25-05-2017.
 */

public class User extends RealmObject {

    @PrimaryKey
    private String name;
    private String password;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public User() {
    }

    public User(String name, String password) {

        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
