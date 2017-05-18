package com.example.malik.bob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView date,name,light,temp,moist;
    MomentsDB momentsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent=getIntent();
        //Bundle bundle=intent.getExtras();
        //Moment moment= (Moment) bundle.getSerializable("moment");
        long id=(long)intent.getIntExtra("moment",0);

        momentsDB=MomentsDB.get();
        Moment moment=momentsDB.getMomentById(id);

        date=(TextView)findViewById(R.id.date);
        name=(TextView)findViewById(R.id.person);
        light=(TextView)findViewById(R.id.moisture);
        temp=(TextView)findViewById(R.id.light);
        moist=(TextView)findViewById(R.id.temperature);

        date.setText(moment.getDate());
        name.setText(moment.getName());
        light.setText(moment.getLight()+"");
        temp.setText(moment.getTemperature()+"");
        moist.setText(moment.getMoisture());



    }
}
