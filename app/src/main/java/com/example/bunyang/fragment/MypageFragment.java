package com.example.bunyang.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bunyang.data.DataUtil;
import com.example.bunyang.R;

public class MypageFragment extends Fragment {

    Button photo_click;
    ImageView profile;

    private TextView myEmail;
    private TextView myName;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MypageFragment() {
    }

    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        profile = v.findViewById(R.id.profile);
        photo_click = v.findViewById(R.id.photo_click);

        myName = (TextView) v.findViewById(R.id.myname);
        myName.setText("이름 : " + getArguments().getString("name"));

        myEmail = (TextView) v.findViewById(R.id.myemail);
        myEmail.setText("이메일 : " + getArguments().getString("emailId"));


        photo_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);

            }
        });

        if (DataUtil.imageUri != null) {
            profile.setImageURI(DataUtil.imageUri);
        }

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    profile.setImageURI(imageUri);
                    DataUtil.imageUri = imageUri;
                }
            }
        }
    }
}