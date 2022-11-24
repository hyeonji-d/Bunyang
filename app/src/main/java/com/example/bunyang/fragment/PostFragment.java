package com.example.bunyang.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bunyang.data.Dog;
import com.example.bunyang.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class PostFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private DatabaseReference mDatabaseRef; // 실시단 데이터베이스


    ImageView photo_view2;
    TextView post_lead;
    Button back;

    private TextView post_id;
    private TextView post_email;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String a;
    private String mParam1;
    private String mParam2;
    ImageView load;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bunyang");

        Dog dog = (Dog) getArguments().getSerializable("dog");
        String data = "photo/" + dog.getId();

        Log.e("test", "" + data);
        // Inflate the layout for this fragment

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://projectk-5e444.appspot.com/");
        StorageReference storageRef = storage.getReference();

        storageRef.child(data).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override

            public void onSuccess(Uri uri) {

                // Inflate the layout for this fragment

                photo_view2 = (ImageView) v.findViewById(R.id.photo_view2);

                Glide.with(getActivity()).load(uri).into(photo_view2);
                post_lead = (TextView) v.findViewById(R.id.post_lead);
                back = (Button) v.findViewById(R.id.back);

                post_lead.setText(dog.getSpin1() + " / " + dog.getContent() );

                post_id = (TextView) v.findViewById(R.id.post_id);
                post_id.setText("작성자 : " + getArguments().getString("name"));

                post_email = (TextView) v.findViewById(R.id.post_email);
                post_email.setText("이메일 : " + getArguments().getString("emailId"));

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }

                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getActivity().getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

}