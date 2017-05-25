package com.example.malik.bob.DB;

import android.util.Log;

import com.example.malik.bob.Objects.Moment;
import com.example.malik.bob.Objects.User;

import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by malik on 25-05-2017.
 */

public class UserDB {
    private static Realm realm;
    private static UserDB sUserDB; //singleton

    public static UserDB get() {
        if (sUserDB == null) {
            realm = Realm.getDefaultInstance();

            sUserDB= new UserDB();
        }
        return sUserDB;
    }

    private UserDB(){}

    public boolean authenticate(User user){
        final User fuser= user;
        final AtomicBoolean retour=new AtomicBoolean();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<User> rows=realm.where(User.class).equalTo("name", fuser.getName()).findAll();
                if (rows.size()>0 && rows.get(0).getPassword().equals(fuser.getPassword()))
                    retour.set(true);
                else
                    retour.set(false);
            }});
        return retour.get();
    }

    public boolean addUser(User user){
        final User fuser= user;
        final AtomicBoolean retour=new AtomicBoolean();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<User> rows=realm.where(User.class).equalTo("name", fuser.getName()).findAll();
                if (rows.size()>0)
                    retour.set(false);
                else{
                    retour.set(true);
                    realm.copyToRealm(fuser);
                }
            }});
        return retour.get();
    }


}
