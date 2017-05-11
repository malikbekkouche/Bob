package com.example.malik.bob;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by malik on 26-04-2017.
 */

public class MomentsDB {

    private static Realm realm;
    private static MomentsDB sMomentDB; //singleton

    public static MomentsDB get() {
        if (sMomentDB == null) {
            realm = Realm.getDefaultInstance();
            sMomentDB= new MomentsDB();
        }
        return sMomentDB;
    }

    private MomentsDB(){
       /* addMoment(new Moment(new Date().toString(),"Josh",244,17,16));
        addMoment((new Moment(new Date().toString(),"Danny",250,145,666)));
        addMoment(new Moment(new Date().toString(),"Thomas",74,31,9));*/
    }


    public void addMoment(Moment moment) {
        final Moment fmoment= moment;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long nextID;
                try {
                    nextID = (long)(realm.where(Moment.class).max("id"))+1;
                } catch (NullPointerException e) {
                    nextID = 1;
                }
                fmoment.setId(nextID);
                realm.copyToRealm(fmoment);
                Log.d("here :",fmoment.getName());
            }});

    }

    public OrderedRealmCollection<Moment> getThingsDB() {
        OrderedRealmCollection<Moment> l= realm.where(Moment.class).findAll();
        return l;
    }

    public OrderedRealmCollection<Moment> getThingsDBReversed() {
        OrderedRealmCollection<Moment> list= realm.where(Moment.class).findAll().sort("date", Sort.DESCENDING);
        return list;
    }

}
