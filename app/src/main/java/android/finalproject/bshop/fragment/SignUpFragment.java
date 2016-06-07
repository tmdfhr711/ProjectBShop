package android.finalproject.bshop.fragment;

import android.content.Context;
import android.content.Intent;
import android.finalproject.bshop.MainActivity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.finalproject.bshop.R;

public class SignUpFragment extends Fragment {

    private Button complete_sign_up_button;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sign_up, container, false);
        complete_sign_up_button = (Button) rootView.findViewById(R.id.complete_signup_button);
        complete_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
