package ua.edu.sumdu.fragmentcommunicatetoast;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

public class SimpleFragment extends Fragment {
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    private int mRadioButtonChoice = NONE;

    private static final String CHOICE = "choice";

    OnFragmentInteractionListener mListener;

    public SimpleFragment() {
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice, String result);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context + getResources().getString(R.string.exception_message));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        if (getArguments() != null && getArguments().containsKey(CHOICE)) {
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            if (mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {
                    View radioButton = radioGroup.findViewById(checkedId);
                    int index = radioGroup.indexOfChild(radioButton);
                    TextView textView = rootView.findViewById(R.id.fragment_header);
                    switch (index) {
                        case YES:
                            textView.setText(R.string.yes_message);
                            mRadioButtonChoice = YES;
                            mListener.onRadioButtonChoice(YES, getString(R.string.yes_result));
                            break;
                        case NO:
                            textView.setText(R.string.no_message);
                            mRadioButtonChoice = NO;
                            mListener.onRadioButtonChoice(NO, getString(R.string.no_result));
                            break;
                        default:
                            mRadioButtonChoice = NONE;
                            mListener.onRadioButtonChoice(NONE, getString(R.string.none_result));
                            break;
                    }
                });
        return rootView;
    }

    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }
}
