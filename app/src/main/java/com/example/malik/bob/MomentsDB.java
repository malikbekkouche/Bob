package com.example.malik.bob;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by malik on 26-04-2017.
 */

public class MomentsDB {

    private static Realm realm;
    private static MomentsDB sMomentDB; //singleton
    private static MainActivity ma;

    public static MomentsDB get() {
        if (sMomentDB == null) {
            realm = Realm.getDefaultInstance();
            RealmChangeListener rcl = new RealmChangeListener() {
                @Override
                public void onChange(Object element) {
                    ma.setUpUI();
                }
            };
            realm.addChangeListener(rcl);
            sMomentDB= new MomentsDB();
        }
        return sMomentDB;
    }

    private MomentsDB(){
       /* addMoment(new Moment(new Date().toString(),"Josh",244,17,16));
        addMoment((new Moment(new Date().toString(),"Danny",250,145,666)));
        addMoment(new Moment(new Date().toString(),"Thomas",74,31,9));*/
    }


    public long addMoment(Moment moment) {
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
        return fmoment.getId();

    }

    public OrderedRealmCollection<Moment> getThingsDB() {
        OrderedRealmCollection<Moment> l= realm.where(Moment.class).findAll();
        return l;
    }

    public OrderedRealmCollection<Moment> getThingsDBReversed() {
        OrderedRealmCollection<Moment> list= realm.where(Moment.class).findAll().sort("date", Sort.DESCENDING);
        return list;
    }

    public Moment getMomentById(long id){
        RealmResults<Moment> rows=realm.where(Moment.class).equalTo("id", id).findAll();
        return rows.get(0);
    }

    public long getIdByDate(Moment m){
        RealmResults<Moment> rows=realm.where(Moment.class).equalTo("date", m.getDate()).findAll();
        return rows.get(0).getId();
    }

    public static MainActivity getMa() {
        return ma;
    }

    public static void setMa(MainActivity ma) {
        MomentsDB.ma = ma;
    }

    public void deleteAll(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Moment> rows=realm.where(Moment.class).findAll();
                int i=0;
                while (i<rows.size()) {
                    rows.get(i).deleteFromRealm();
                    i++;
                }
            }});
    }
}
