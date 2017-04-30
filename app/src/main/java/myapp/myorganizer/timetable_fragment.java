package myapp.myorganizer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;


public class timetable_fragment extends Fragment {
    //daysOfWeek day[] = new daysOfWeek[7];
    Timetable tt;
    TextView text;
    todo td;
    //String days[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    int j = 0, k;
    Button btn;
    ListView ttListView;
    Fragment fragment;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private String[] mDrawerMenu;
    //LinearLayout rlayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timetable, container,
                false);
        //setContentView(R.layout.activity_main);
        //Intent in1 = getIntent();
        LinearLayout rlayout = (LinearLayout) rootView.findViewById(R.id.rlayout);
        ttListView = (ListView) rootView.findViewById(R.id.listView);
        text = (TextView) rootView.findViewById(R.id.textView);
        mDrawerList = (ListView) rootView.findViewById(R.id.navigation_drawer);
        mDrawerMenu = getResources().getStringArray(R.array.drawer_menu);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ArrayList<String> ttItems = new ArrayList<String>();
        final ArrayAdapter<String> aa;
        aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ttItems);
        ttListView.setAdapter(aa);
        tt = new Timetable();
        tt.addDay();
        //readLecturesfromFile();
        tt.lectures();
        Calendar calendar = Calendar.getInstance();
        int getday = calendar.get(Calendar.DAY_OF_WEEK);
        k = getday - 1;
        tt.day[getday - 1].display(ttItems);
        text.setText("Time table for " + tt.day[getday - 1].day);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        aa.notifyDataSetChanged();
        ttListView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            //ttListView.setOnTouchListener(new OnSwipeTouchListener(Timetable.this) {
            @Override
            public void onSwipeBottom() {
                if (k == 0) {
                    k = 6;
                } else {
                    k--;
                }
                ttItems.clear();
                tt.day[k].display(ttItems);
                aa.notifyDataSetChanged();
                text.setText("Time table for " + tt.day[k].day);
                System.out.println("Inside");
                super.onSwipeBottom();
            }

            @Override
            public void onSwipeTop() {
                if (k == 6) {
                    k = 0;
                } else {
                    k++;
                }
                ttItems.clear();
                tt.day[k].display(ttItems);
                aa.notifyDataSetChanged();
                text.setText("Time table for " + tt.day[k].day);
                System.out.println("Inside");
                super.onSwipeTop();
            }

            @Override
            public void onSwipeLeft() {
                fragment = new todo();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(1, true);
                    //setTitle(mDrawerMenu[1]);
                    getActivity().setTitle("To Do List");
                }
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                fragment = new contact();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(2, true);
                    getActivity().setTitle("My Contacts");
                }
                super.onSwipeRight();
            }
        });

    }
    /*@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }*/

}

