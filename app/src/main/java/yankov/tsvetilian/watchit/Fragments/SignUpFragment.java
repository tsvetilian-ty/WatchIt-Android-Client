package yankov.tsvetilian.watchit.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yankov.tsvetilian.watchit.NetworkContracts.NetworkRequestContract;
import yankov.tsvetilian.watchit.R;
import yankov.tsvetilian.watchit.Utilities.FragmentEvent;
import yankov.tsvetilian.watchit.Utilities.FragmentEventListener;
import yankov.tsvetilian.watchit.Utilities.Validator;
import yankov.tsvetilian.watchit.ViewModels.UserViewModel;


public class SignUpFragment extends Fragment {


    private FragmentEventListener eventEmitter;
    private View view;
    private UserViewModel viewModel;
    private EditText watchItServer;
    private EditText name;
    private EditText emailTextView;
    private EditText password;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        eventEmitter = ((FragmentEventListener) context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        bindView();
        return view;
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        watchItServer = view.findViewById(R.id.server_input);
        name = view.findViewById(R.id.name_input);
        emailTextView = view.findViewById(R.id.email_input);
        password = view.findViewById(R.id.password_input);

        final MaterialButton submit = view.findViewById(R.id.sign_up);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit.setClickable(false);
                if (!Validator.isValidServer(watchItServer.getText())) {
                    watchItServer.setError("Invalid URL!");
                    submit.setClickable(true);
                } else if (!Validator.isValidName(name.getText())) {
                    name.setError("Invalid name it should be longer than 4 symbols!");
                    submit.setClickable(true);
                } else if (!Validator.isValidEmail(emailTextView.getText())) {
                    emailTextView.setError("Invalid email address!");
                    submit.setClickable(true);
                } else if (!Validator.isValidPassword(password.getText())) {
                    password.setError("Invalid password enter 6 or more symbols!");
                    submit.setClickable(true);
                } else {
                    viewModel.userSignUp(watchItServer.getText(), name.getText(), emailTextView.getText(), password.getText(),
                            new NetworkRequestContract() {
                                @Override
                                public void onSuccess(@NonNull String statusMessage) {
                                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onError(@NonNull String statusMessage) {
                                    submit.setClickable(true);
                                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

        TextView signInLink = view.findViewById(R.id.signin_link);

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventEmitter.onFragmentEvent(FragmentEvent.SIGN_IN_CLICK);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
        eventEmitter = null;
    }
}
