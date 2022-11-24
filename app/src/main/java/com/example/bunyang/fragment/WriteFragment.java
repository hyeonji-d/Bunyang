package com.example.bunyang.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bunyang.data.Dog;
import com.example.bunyang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class WriteFragment extends Fragment {

    Uri imageUri;

    ImageButton write, photo;
    ImageView photoView;
    ArrayAdapter<CharSequence> dog1, dog2, dog3, dog4, dog5, dog6;

    Spinner spin1, spin2, spin3, spin4, spin5, spin6;
    EditText inputEdit;
    Context c;
    WallpaperManager iv_review_image;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시단 데이터베이스
    private FirebaseStorage mstorageRef; // 실시단 데이터베이스

    //private final StorageReference reference = FirebaseStorage.

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private int id;
    private Fragment homeFragment;


    public WriteFragment() {
        // Required empty public constructor
    }

    public static WriteFragment newInstance(String param1, String param2) {
        WriteFragment fragment = new WriteFragment();
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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("bunyang");
        mstorageRef = FirebaseStorage.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_write, container, false);

        write = (ImageButton) v.findViewById(R.id.write);
        photo = (ImageButton) v.findViewById(R.id.photo);
        photoView = (ImageView) v.findViewById(R.id.photo_view);
        c = container.getContext();

        spin1 = (Spinner) v.findViewById(R.id.spinner);
        spin2 = (Spinner) v.findViewById(R.id.spinner2);
        spin3 = (Spinner) v.findViewById(R.id.spinner3);
        spin4 = (Spinner) v.findViewById(R.id.spinner4);
        spin5 = (Spinner) v.findViewById(R.id.spinner5);
        spin6 = (Spinner) v.findViewById(R.id.spinner6);

        inputEdit = (EditText) v.findViewById(R.id.input_edit);

        dog1 = ArrayAdapter.createFromResource(getActivity(), R.array.종류, R.layout.spinner);
        dog1.setDropDownViewResource(R.layout.spinner);
        spin1.setAdapter(dog1);

        dog2 = ArrayAdapter.createFromResource(getActivity(), R.array.지역, R.layout.spinner);
        dog2.setDropDownViewResource(R.layout.spinner);
        spin2.setAdapter(dog2);

        dog3 = ArrayAdapter.createFromResource(getActivity(), R.array.성별, R.layout.spinner);
        dog3.setDropDownViewResource(R.layout.spinner);
        spin3.setAdapter(dog3);

        dog4 = ArrayAdapter.createFromResource(getActivity(), R.array.나이, R.layout.spinner);
        dog4.setDropDownViewResource(R.layout.spinner);
        spin4.setAdapter(dog4);

        dog5 = ArrayAdapter.createFromResource(getActivity(), R.array.견종, R.layout.spinner);
        dog5.setDropDownViewResource(R.layout.spinner);
        spin5.setAdapter(dog5);

        dog6 = ArrayAdapter.createFromResource(getActivity(), R.array.크기, R.layout.spinner);
        dog6.setDropDownViewResource(R.layout.spinner);
        spin6.setAdapter(dog6);


        mDatabaseRef.child("Dog").orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Dog dog = item.getValue(Dog.class);
                    id = dog.getId() + 1;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homefragment = new HomeFragment();
                Dog dog = new Dog();

                dog.setId(id);

                String stoUrl =  "photo/" + dog.getId();

                String imgUrl = "gs://projectk-5e444.appspot.com/"+ stoUrl;

                dog.setSpin1("" + spin1.getSelectedItem());
                dog.setSpin2("" + spin2.getSelectedItem());
                dog.setSpin3("" + spin3.getSelectedItem());
                dog.setSpin4("" + spin4.getSelectedItem());
                dog.setSpin5("" + spin5.getSelectedItem());
                dog.setSpin6("" + spin6.getSelectedItem());
                dog.setContent("" + inputEdit.getText().toString());
                dog.setImageUrl("" + imgUrl);
                //dog.setImageUrl("" + );
                mDatabaseRef.child("Dog").child("" + id).setValue(dog);

                StorageReference storageRef = mstorageRef.getReference();
                StorageReference riversRef = storageRef.child(stoUrl);
                UploadTask uploadTask = riversRef.putFile(imageUri);

                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelay, homefragment).commitAllowingStateLoss();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getData() != null) {
//                    Uri imageUri = data.getData();
                    imageUri = data.getData();
                    photoView.setImageURI(imageUri);

//                    StorageReference storageRef = mstorageRef.getReference();
//                    StorageReference riversRef = storageRef.child("photo/1.png");
//                     UploadTask uploadTask = riversRef.putFile(imageUri);
                }
            }
        }
    }

    private void uploadToFirebase(Uri uri) {


    }
}