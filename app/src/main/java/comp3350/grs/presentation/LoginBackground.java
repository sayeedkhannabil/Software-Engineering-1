package comp3350.grs.presentation;


// a fragment, the background(and buttons) when user enter the app
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import comp3350.grs.R;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginBackground#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginBackground extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public LoginBackground() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    public static LoginBackground newInstance(String param1, String param2) {
        LoginBackground fragment = new LoginBackground();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_background,
                container, false);

        TextView signin=(TextView) view.findViewById(R.id.textView4);
        Utilities.setOnTouchEffect(signin);
        //after clicking "signin" button
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });

        //after clicking "signup" button
        Button signup=(Button) view.findViewById(R.id.button);
        Utilities.setOnTouchEffect(signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Signup.class);
                startActivity(intent);
            }
        });

        //after clicked "continue as guest", take the user to game gallery
        Button ctnAsGst=(Button) view.findViewById(R.id.button2);
        Utilities.setOnTouchEffect(ctnAsGst);
        ctnAsGst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AccessUsers.setActiveUser(new Guest());//set active user as
                } catch (IncorrectFormat incorrectFormat) {
                    incorrectFormat.printStackTrace();
                }
                // guest
                Intent intent=new Intent(getActivity(), GalleryOrForum.class);
                startActivity(intent);
            }
        });
        return view;
    }
}