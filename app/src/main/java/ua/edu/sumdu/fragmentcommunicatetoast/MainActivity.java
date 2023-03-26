package ua.edu.sumdu.fragmentcommunicatetoast;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {

    private Button mButton;
    private boolean isFragmentDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String STATE_CHOICE = "user_choice";

    private int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            mRadioButtonChoice = savedInstanceState.getInt(STATE_CHOICE);
            if (isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
        }

        mButton.setOnClickListener(view -> {
            if (!isFragmentDisplayed) {
                displayFragment();
            } else {
                closeFragment();
            }
        });
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction
                .add(R.id.fragment_container, simpleFragment)
                .addToBackStack(null)
                .commit();

        mButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    @Override
    public void onRadioButtonChoice(int choice, String result) {
        mRadioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + result, LENGTH_SHORT).show();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        savedInstanceState.putInt(STATE_CHOICE, mRadioButtonChoice);
        super.onSaveInstanceState(savedInstanceState);
    }
}