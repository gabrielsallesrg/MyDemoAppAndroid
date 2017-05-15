package br.com.rg.gabrielsalles.mydemoapp2017.randomuser.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.PaintCompat;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.rg.gabrielsalles.mydemoapp2017.R;

public class RandomUserFragmentAbout extends Fragment {

    private OnFragmentInteractionListener mListener;

    public RandomUserFragmentAbout() {
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
        View v = inflater.inflate(R.layout.random_user_fragment_about, container, false);

        ((TextView) v.findViewById(R.id.randomuser_text)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) v.findViewById(R.id.retrofit_text)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) v.findViewById(R.id.greendao_text)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) v.findViewById(R.id.glide_text)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) v.findViewById(R.id.recyclerviewanimators_text)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) v.findViewById(R.id.stetho_text)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) v.findViewById(R.id.databinding_text)).setMovementMethod(LinkMovementMethod.getInstance());

        return v;
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
