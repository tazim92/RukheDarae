package com.returnnull.rukhedarae;

/**
 * Created by Tazim on 2/18/2016.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tazim on 12/29/2015.
 */
public class CustomListview extends ArrayAdapter<String[]> {


    String type="";

    private final Context context;
    private final String[][] locationInformations;

    CustomListview(Context context, String[][] locationInformations, String type){
        super(context,R.layout.refresh_list_view,locationInformations);
        this.context = context;
        this.locationInformations = locationInformations;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.refresh_list_view, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.textViewName);
        TextView tvAddress = (TextView) rowView.findViewById(R.id.textViewAddress);
        TextView tvContact = (TextView) rowView.findViewById(R.id.textViewContact);
        TextView tvDistance = (TextView) rowView.findViewById(R.id.textViewDistance);
        Button buttonCall = (Button) rowView.findViewById(R.id.buttonCall);
        tvName.setText(locationInformations[position][0]);
        tvAddress.setText(locationInformations[position][1]);
        tvDistance.setText(locationInformations[position][3]);

        final String contact = locationInformations[position][2];
        if(!contact.equalsIgnoreCase("null")) {
            tvContact.setText("Contact: "+contact);
            buttonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + contact));
                    context.startActivity(intent);
                }
            });
            //  buttonCall.setVisibility(View.VISIBLE);
        }else{
            tvContact.setText("");
            buttonCall.setVisibility(View.GONE);
        }

        //String url=locationInformations[position][6];

        //new DownloadImageTask(ivImage).execute(url);
/*
        if(url.equalsIgnoreCase("null"))
        {
            switch (type){
                case "hospital" : ivImage.setImageResource(R.drawable.hospital);
                    break;
                case "police" : ivImage.setImageResource(R.drawable.police);
                    break;
                case "school" : ivImage.setImageResource(R.drawable.school);
                    break;
                case "food" : ivImage.setImageResource(R.drawable.food);
                    break;
                case "fire_station" : ivImage.setImageResource(R.drawable.fire);
                    break;
                default: ivImage.setImageResource(R.drawable.others);
                    break;
            }

        }
        else{
            Picasso.with(context).load(url).into(ivImage);
        }
*/

        return rowView;
    }


}
