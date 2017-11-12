package rrdl.crt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Donation extends Fragment {

    private FloatingActionButton send ;
    private EditText input;
    private ListView listmsg;


    public Donation() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static Donation newInstance() {
        Donation fragment = new Donation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_donation, container, false);
        send = (FloatingActionButton) v.findViewById(R.id.fab);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"clik",Toast.LENGTH_LONG).show();
            }
        });
        input = (EditText) v.findViewById(R.id.input);
        listmsg = (ListView) v.findViewById(R.id.list);



        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
