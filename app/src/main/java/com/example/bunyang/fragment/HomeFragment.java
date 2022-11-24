package com.example.bunyang.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bunyang.R;
import com.example.bunyang.data.Data;
import com.example.bunyang.data.DataAdapter;
import com.example.bunyang.data.Dog;
import com.example.bunyang.fragment.PostFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;


    ArrayAdapter<CharSequence> dog1, dog2, dog3, dog4, dog5, dog6;

    Spinner spin1, spin2, spin3, spin4, spin5, spin6;

    Button btn;

    ListView listView2;

    ImageButton point;

    Context c;

    ArrayList<Dog> dataList;

    DataAdapter dataAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabaseRef; // 실시단 데이터베이스

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        dataList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        c = container.getContext();

        spin1 = (Spinner) v.findViewById(R.id.spinner);
        spin2 = (Spinner) v.findViewById(R.id.spinner2);
        spin3 = (Spinner) v.findViewById(R.id.spinner3);
        spin4 = (Spinner) v.findViewById(R.id.spinner4);
        spin5 = (Spinner) v.findViewById(R.id.spinner5);
        spin6 = (Spinner) v.findViewById(R.id.spinner6);
        btn = (Button) v.findViewById(R.id.btn1);
        point = (ImageButton) v.findViewById(R.id.point);

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.clear();
                mDatabaseRef.child("Dog").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("snap", "" + snapshot);
                        for (DataSnapshot item : snapshot.getChildren()) {
                            Dog dog = item.getValue(Dog.class);
                            if (!spin1.getSelectedItem().equals("경력")) {
                                if (!dog.getSpin1().equals("" + spin1.getSelectedItem())) {
                                    continue;
                                }
                            }
                            if (!spin2.getSelectedItem().equals("지역")) {
                                if (!dog.getSpin2().equals("" + spin2.getSelectedItem())) {
                                    continue;
                                }
                            }
                            if (!spin3.getSelectedItem().equals("성별")) {
                                if (!dog.getSpin3().equals("" + spin3.getSelectedItem())) {
                                    continue;
                                }
                            }
                            if (!spin4.getSelectedItem().equals("나이")) {
                                if (!dog.getSpin4().equals("" + spin4.getSelectedItem())) {
                                    continue;
                                }
                            }
                            if (!spin5.getSelectedItem().equals("견종")) {
                                if (!dog.getSpin5().equals("" + spin5.getSelectedItem())) {
                                    continue;
                                }
                            }
                            if (!spin6.getSelectedItem().equals("크기")) {
                                if (!dog.getSpin6().equals("" + spin6.getSelectedItem())) {
                                    continue;
                                }
                            }
                            dataList.add(dog);


//                            Fragment AdapterFragment = new PostFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("dog", dog);
//                            bundle.putString("id", getActivity().getIntent().getStringExtra("id"));

//                            AdapterFragment.setArguments(bundle);


                        }
                        dataAdapter.setDogs(dataList);
                        dataAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        this.InitializeData();

        ListView listView = (ListView) v.findViewById(R.id.listview);
        dataAdapter = new DataAdapter(getActivity());

        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //Bundle bundle = new Bundle();
                //bundle.putString("name",edit.getText().toString());
//                Toast.makeText(c.getApplicationContext(),
//                        dataAdapter.getItem(position).getSpin1(),
//                        Toast.LENGTH_LONG).show();
                Fragment postFragment = new PostFragment();
                Bundle bundle = new Bundle();
                Dog dog = dataAdapter.getItem(position);

                bundle.putSerializable("dog", dog);
                postFragment.setArguments(bundle);
                bundle.putString("name", getActivity().getIntent().getStringExtra("name"));
                bundle.putString("emailId", getActivity().getIntent().getStringExtra("emailId"));
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.framelay, postFragment).commitAllowingStateLoss();
            }
        });


        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelay, new WriteFragment()).commitAllowingStateLoss();

            }
        });


        return v;

    }

    public void InitializeData() {
//        DataList = new ArrayList<Data>();
        Data data = new Data();
//        data.setDogName();

//        DataList.add(new Data(R.drawable.dog, "강아지"));
//        DataList.add(new Data(R.drawable.dog, "강아지2"));
//        DataList.add(new Data(R.drawable.dog, "강아지3"));
    }
}