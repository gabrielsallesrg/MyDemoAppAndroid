package br.com.rg.gabrielsalles.mydemoapp2017;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ContactInformationFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public ContactInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.contact_information_fragment, container, false);
        return view;
    }

    public void sendEmailToDev(View v) {
        Uri mailUri = Uri.parse("mailto:" + getResources().getString(R.string.my_email_1));
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
        startActivity(mailIntent);
    }

    public void accessDevLinkedin(View v) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
