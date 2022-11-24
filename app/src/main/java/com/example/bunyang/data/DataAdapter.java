package com.example.bunyang.data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bunyang.R;
import com.example.bunyang.fragment.HomeFragment;
import com.example.bunyang.fragment.PostFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {




    private DatabaseReference mDatabaseRef; // 실시단 데이터베이스
    private FirebaseStorage mstorageRef; // 실시단 데이터베이스

    private int id;

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Dog> dogs;

    public DataAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        dogs = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return dogs.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Dog getItem(int position) {
        return dogs.get(position);
    }


    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {

        View view = mLayoutInflater.inflate(R.layout.listview, null);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bunyang");
        mstorageRef = FirebaseStorage.getInstance();

        Fragment fragment = new HomeFragment();
        int rkawk;
        rkawk = dogs.get(position).getId();
        String data = "photo/" + rkawk;

        Log.e("dog.getId()", "" + rkawk);
        // Inflate the layout for this fragment

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://projectk-5e444.appspot.com/");
        StorageReference storageRef = storage.getReference();

        storageRef.child(data).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override

            public void onSuccess(Uri uri) {

                // Inflate the layout for this fragment
                //Fragment postFragment = new PostFragment();
                //getArguments().getString("id");
                ImageView imageView = (ImageView) view.findViewById(R.id.dogphoto);
                TextView dogName = (TextView) view.findViewById(R.id.dogname);
                TextView dogArea = (TextView) view.findViewById(R.id.dogarea);
                TextView dogGender = (TextView) view.findViewById(R.id.doggender);
                TextView dogAge = (TextView) view.findViewById(R.id.dogage);
                TextView dogType = (TextView) view.findViewById(R.id.dogtype);
                TextView dogSize = (TextView) view.findViewById(R.id.dogsize);

                Glide.with(view.getContext()).load(uri).into(imageView);
                //imageView.setImageResource(R.drawable.dog);
                dogName.setText(dogs.get(position).getSpin1());
                dogArea.setText(dogs.get(position).getSpin2());
                dogGender.setText(dogs.get(position).getSpin3());
                dogAge.setText(dogs.get(position).getSpin4());
                dogType.setText(dogs.get(position).getSpin5());
                dogSize.setText(dogs.get(position).getSpin6());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
            }
        });



        return view;
    }

    public ArrayList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<Dog> dogs) {
        this.dogs = dogs;
    }
}
