package rrdl.crt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BlankFragment extends Fragment implements View.OnClickListener {

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onClick(View v){
        switch(v.getId()){
            case R.id.fp1:
                (new intentHandler()).start(this.getActivity(),step1.class);
                break;
            case R.id.fp2:
                (new intentHandler()).start(this.getActivity(),asphyxiation.class);
                break;
            case R.id.fp3:
                (new intentHandler()).start(this.getActivity(),Bleeding.class);
                break;
            case R.id.fp4:
                (new intentHandler()).start(this.getActivity(),unconsciousness.class);
                break;
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    interface OnFragmentInteractionListener {
    }

}
