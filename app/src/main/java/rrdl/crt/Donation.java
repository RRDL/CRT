package rrdl.crt;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class Donation extends Fragment {

    private FloatingActionButton send ;
    private EditText input;
    private ListView mList;
    private ArrayList<String> arrayList;
    private MyCustomAdapter mAdapter;
    private Client mClient;



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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_donation, container, false);
        send = (FloatingActionButton) v.findViewById(R.id.fab);
        input = (EditText) v.findViewById(R.id.input);
        mList = (ListView) v.findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        mAdapter = new MyCustomAdapter(this, arrayList);
        mList.setAdapter(mAdapter);

        // connect to the server
        new connectTask().execute("");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = input.getText().toString();

                //add the text in the arrayList
                arrayList.add("Me : " + message);

                //sends the message to the server
                if (mClient != null) {
                    mClient.sendMessage(message);
                }

                //refresh the list
                mAdapter.notifyDataSetChanged();
                input.setText("");
            }
        });


        return v;
    }

    public class connectTask extends AsyncTask<String,String,Client> {

        @Override
        protected Client doInBackground(String... message) {

            //we create a Client object and
            mClient = new Client(new Client.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //in the arrayList we add the messaged received from server
            arrayList.add(values[0]);
            // notify the adapter that the data set has changed. This means that new message received
            // from server was added to the list
            mAdapter.notifyDataSetChanged();
        }
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
