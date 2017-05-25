package com.example.malik.bob;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malik.bob.Objects.Moment;

import io.realm.OrderedRealmCollection;

/**
 * Created by malik on 26-04-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public OrderedRealmCollection<Moment> list;
    public Context mContext;
    private final View.OnClickListener mOnClickListener = new TimeClickListener();
    private final View.OnLongClickListener mOnLongClickListener = new DetailsClickListener();
    public MyAdapter(OrderedRealmCollection<Moment> a,Context c){
        list=a;
        mContext=c;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li=LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.moment_item,parent,false);

        view.setOnClickListener(mOnClickListener);
        view.setOnLongClickListener(mOnLongClickListener);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        Moment m=list.get(position);
        Log.d("there",m.getName());
        int color=0;
        if(list.get(position).getMoisture()>300)
            color= ContextCompat.getColor(mContext,R.color.bleu) ;
        else
            color= ContextCompat.getColor(mContext,R.color.rouge) ;
        holder.color=(color);
        holder.itemView.setBackgroundColor(color);
        holder.id.setText(m.getName());
        holder.light.setText(""+m.getLight());
        holder.temp.setText(""+m.getTemperature());
        holder.moist.setText(""+m.getMoisture());
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        private TextView id,temp,light,moist;
        public int color;
        public MyViewHolder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.id);
            light=(TextView)itemView.findViewById(R.id.light);
            temp=(TextView)itemView.findViewById(R.id.temp);
            moist=(TextView)itemView.findViewById(R.id.moist);

        }
    }

    public class TimeClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            MainActivity ma=(MainActivity)mContext;
            int itemPosition =ma.rv.getChildLayoutPosition(view);
            Moment item = list.get(itemPosition);

            LayoutInflater inflater = ma.getLayoutInflater();
           // View secondView = inflater.inflate(R.layout.image_toast,
             //       (ViewGroup) ma.findViewById(R.id.linear));

            Toast toast = new Toast(ma);
            ImageView imageView=new ImageView(ma);
            imageView.setImageBitmap(ma.byteToBitmap(item.getPhoto()));
            toast.setView(imageView);
            toast.show();

           /* toast.setView(secondView);
            ImageView imageView=(ImageView)ma.findViewById(R.id.imageToast);
            imageView.setImageBitmap(ma.byteToBitmap(item.getPhoto()));
            TextView textView=(TextView)ma.findViewById(R.id.timestamp);
            textView.setText(item.getDate());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();*/

            //Bitmap bm = ma.byteToBitmap(item.getPhoto());
            //Toast.makeText(mContext, bm.getHeight()+"x "+bm.getWidth()+"", Toast.LENGTH_LONG).show();


        }

    }

    public class DetailsClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            MainActivity ma=(MainActivity)mContext;
            int itemPosition =ma.rv.getChildLayoutPosition(v);
            Moment item = list.get(itemPosition);
            Toast.makeText(ma,item.getDate(),Toast.LENGTH_SHORT).show();
            //*Bundle b=new Bundle();

            return true;
        }
    }
}
