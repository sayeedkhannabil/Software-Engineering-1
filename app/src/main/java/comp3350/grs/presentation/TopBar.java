package comp3350.grs.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import comp3350.grs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopBar#newInstance} factory method to
 * create an instance of this fragment.
 */
//the top bar fragment
public class TopBar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView back_to_prev;
    private TextView top_bar_text;
    private ImageView user_button;

    public TopBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment top_bar.
     */
    // TODO: Rename and change types and number of parameters
    public static TopBar newInstance(String param1, String param2) {
        TopBar fragment = new TopBar();
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
        View view= inflater.inflate(R.layout.fragment_top_bar, container,
                false);
        back_to_prev=view.findViewById(R.id.back_to_prev);
        top_bar_text=view.findViewById(R.id.top_bar_text);
        user_button=view.findViewById(R.id.user_button);

        setBack_to_prev();
        setUser_button();
        return view;
    }


    private void setBack_to_prev(){
        Utilities.setOnTouchEffect(back_to_prev);
        //when press the back arrow, return to the previous activity
        back_to_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void setUser_button(){
        Utilities.setOnTouchEffect(user_button);
        user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),UserPage.class);
                startActivity(intent);
            }
        });
    }

    public void setTop_bar_text(String text){
        top_bar_text.setText(text);
    }
}