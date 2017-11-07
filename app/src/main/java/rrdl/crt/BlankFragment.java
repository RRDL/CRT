package rrdl.crt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BlankFragment extends Fragment implements View.OnClickListener {



    private OnFragmentInteractionListener mListener;


    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void start(Class e){
        Intent i = new Intent(getContext(),e);
        startActivity(i);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.fp1:
                start(step1.class);
                break;
            case R.id.fp2:
                start(asphyxiation.class);
                break;
            case R.id.fp3:
                start(Bleeding.class);
                break;
            case R.id.fp4:
                start(unconsciousness.class);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_blank, container, false);

        Button btn1=(Button)view.findViewById(R.id.fp1);
        btn1.setOnClickListener(this);
        Button btn2=(Button)view.findViewById(R.id.fp2);
        btn2.setOnClickListener(this);
        Button btn3=(Button)view.findViewById(R.id.fp3);
        btn3.setOnClickListener(this);
        Button btn4=(Button)view.findViewById(R.id.fp4);
        btn4.setOnClickListener(this);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }

}
