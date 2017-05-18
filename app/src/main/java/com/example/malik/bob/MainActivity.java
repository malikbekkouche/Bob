package com.example.malik.bob;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class MainActivity extends AppCompatActivity implements SyncUser.Callback{
    RecyclerView rv;
    MomentsDB momentsDB;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton;
    MyAdapter adapter;
    EditText name;

    // NEVER put username/password in a real app !!!!!
    private static final String USERNAME= "marha@itu.dk";
    private static final String PASSWORD= "mmad#5marha";

    private static final String HOST_ITU= "130.226.142.162";
    private static final String HOST= HOST_ITU;
    private static final String DBNAME= "BobDB";
    private static final String INITIALS= "MM";

    //Url
    public static final String AUTH_URL= "http://" + HOST + ":9080/auth";
    public static final String REALM_URL= "realm://" + HOST + ":9080/~/" + DBNAME + INITIALS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRealmSync();
        momentsDB.setMa(this);
        //only when needed
        //momentsDB.deleteAll();





    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            final getBobData gbd = new getBobData();
            gbd.execute();
            Chirp c = null;
            try {
                c = gbd.get();

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                byte[] photo=BitmapToByte(imageBitmap);
                Random r=new Random();

                Moment m = new Moment(new Date().toString(), name.getText().toString(), c.getMoist(), c.getLight(), c.getTemp());
                m.setPhoto(photo);
                long id=momentsDB.addMoment(m);
                m.setId(id);
                adapter.notifyDataSetChanged();
            } catch (InterruptedException e) {
                Toast.makeText(this, "Couldn't connect to Bob. :(", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
    }

    public byte[] BitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public Bitmap byteToBitmap(byte[] bite){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bite, 0, bite.length);
        return bitmap;
    }

    private void setUpRealmSync() {
        if (SyncUser.currentUser() == null) {
            SyncCredentials myCredentials = SyncCredentials.usernamePassword(USERNAME, PASSWORD, false);
            SyncUser.loginAsync(myCredentials, AUTH_URL, this);
        } else {
            //User already logged in
            setupSync(SyncUser.currentUser());
        }
    }

    @Override
    public void onSuccess(SyncUser user) {  setupSync(user);  }

    @Override
    public void onError(ObjectServerError error) {
       // setUpUI();
        Toast.makeText(this, "Failed to login - Using local database only", Toast.LENGTH_LONG).show();
    }

    private void setupSync(SyncUser user) {
        SyncConfiguration defaultConfig = new SyncConfiguration.Builder(user, REALM_URL).build();
        Realm.setDefaultConfiguration(defaultConfig);
        setUpUI();
        Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show();
    }

    public void setUpUI(){
        name=(EditText)findViewById(R.id.your_name);
        mImageButton=(ImageButton)findViewById(R.id.photo);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        momentsDB=MomentsDB.get();

        rv = (RecyclerView) findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(momentsDB.getThingsDBReversed(),this);

        rv.setAdapter(adapter);

    }



}
