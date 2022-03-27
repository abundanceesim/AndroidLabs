package com.cst2335.esim0001;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    TextView messageText;
    TextView idText;
    CheckBox checkbox;
    Button hideButton;
    String message;
    long id;
    boolean isSend;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        //return inflater.inflate(R.layout.fragment_details, container, false);

        // Inflate the layout for this fragment and store it in a variable.
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        messageText = (TextView)view.findViewById(R.id.MessageHere_lab6);
        idText = (TextView) view.findViewById(R.id.IDText_lab6);
        checkbox = (CheckBox) view.findViewById(R.id.checkbox_lab6);


        if(savedInstanceState == null){
            // Get back arguments that were passed into the Bundle
            if(getArguments() != null) {
                message = getArguments().getString("message", "");
                id = getArguments().getLong("id", 0);
                isSend = getArguments().getBoolean("isSent", false);

                messageText.setText(message.toString());
                idText.setText("ID=" + Long.toString(id));
                if(isSend == true){
                    checkbox.setChecked(true);
                }
            }
        }

        hideButton = (Button)view.findViewById(R.id.hideButton_lab6);

        hideButton.setOnClickListener( click -> {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(this);// remove Fragment
            ft.commit();
        });

        return view;
    }
}