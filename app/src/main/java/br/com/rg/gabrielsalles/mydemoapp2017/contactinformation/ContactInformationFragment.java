package br.com.rg.gabrielsalles.mydemoapp2017.contactinformation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.rg.gabrielsalles.mydemoapp2017.R;


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

        view.findViewById(R.id.email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri mailUri = Uri.parse("mailto:" + getResources().getString(R.string.my_email_1));
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO, mailUri);
                startActivity(mailIntent);
            }
        });

        view.findViewById(R.id.linkedIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String linkedInId = getResources().getString(R.string.my_linkedin_id);
                Intent linkedInIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@" + linkedInId));
                final PackageManager packageManager = getContext().getPackageManager();
                final List<ResolveInfo> list = packageManager.queryIntentActivities(linkedInIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list.isEmpty()) {
                    linkedInIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=" + linkedInId));
                }
                startActivity(linkedInIntent);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
