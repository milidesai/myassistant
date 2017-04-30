package myapp.myorganizer;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerMenu;
    int openingpage = 0,currentpage,flag = 0;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mDrawerMenu = getResources().getStringArray(R.array.drawer_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerMenu));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO,ActionBar.DISPLAY_USE_LOGO|ActionBar.DISPLAY_SHOW_HOME);
        //getActionBar().setDisplayUseLogoEnabled(true);
        //getActionBar().setIcon(R.drawable.ic_menu_black_24dp);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#bea4ec")));

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            setPreferences();
            if(flag == 0)
              selectItem(setPreferences());
            if(flag == 1)
            {
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                String page = sharedPrefs.getString("Last_page", "-1");
                selectItem(Integer.parseInt(page));
                create_toast("page= "+page);
            }
        }
        /*mDrawerLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            //ttListView.setOnTouchListener(new OnSwipeTouchListener(Timetable.this) {
            @Override
            public void onSwipeLeft() {
                if (currentpage == 2)
                    currentpage = 0;
                else
                    currentpage++;
                selectItem(currentpage);
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                if (currentpage == 0)
                    currentpage = 2;
                else
                    currentpage--;
                selectItem(currentpage);
                super.onSwipeRight();
            }
        });*/

        //selectItem(setPreferences());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent in = new Intent(this,pref.class);
                startActivity(in);
                // create intent to perform web search for this planet
                //Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                //intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                //if (intent.resolveActivity(getPackageManager()) != null) {
                //    startActivity(intent);
                //} else {
                //    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                //}
                return true;
                //break;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and do action based on position
        Fragment fragment = null;
        switch (position) {
            case 0: fragment = new timetable_fragment ();
                currentpage = 0;
                setCurrentPage();
                //create_toast("selectItem called");
                break;
            case 1: fragment = new todo ();
                currentpage = 1;
                setCurrentPage();
                //create_toast("selectItem called");
                break;
            case 2: fragment = new longer_to_do();
                currentpage = 1;
                setCurrentPage();
                break;
            case 3: fragment = new contact();
                currentpage = 2;
                setCurrentPage();
                //create_toast("selectItem called");
                break;
            case 4: Intent in = new Intent(this,pref.class);
                    startActivity(in);
                    break;
            case 5: fragment = new about();
                    break;
            case 6: fragment = new exit();
                    break;

            default:break;
        }
        if (fragment != null) {
            Bundle args = new Bundle();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mDrawerMenu[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    public int setPreferences () {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String page = sharedPrefs.getString("OpenType", "-1");
        if (page.equalsIgnoreCase("1"))
            openingpage = 0;
        if (page.equalsIgnoreCase("2"))
            openingpage = 1;
        if(page.equalsIgnoreCase("3"))
            openingpage = 2;
        if(page.equalsIgnoreCase("4"))
            flag = 1;
        System.out.println("opening page " + page + " " + openingpage);
        return openingpage;
    }
    public void setCurrentPage()
    {
        //String n = "";
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPrefs.getString("Last_page","-1");
        //create_toast(sharedPrefs.getString("Last_page","-1"));
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("Last_page",Integer.toString(currentpage));
        editor.commit();
        //create_toast(sharedPrefs.getString("Last_page","-1"));
        //SharedPreferences settings = getSharedPreferences(n,MODE_PRIVATE);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    void create_toast(String text)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(),text, duration);
        toast.show();
    }
    //Placeholder for timetable
    public static class exit extends Fragment {

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.timetable, container,
                    false);
            return rootView;
        }

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            new AlertDialog.Builder(getActivity()).setTitle("Exit").setMessage("Do you really want to exit?")
                    .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    AppExit();
                }
            }).setNegativeButton(android.R.string.no,null).show();

        }
        public void AppExit()
        {

            getActivity().finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }
    }

}
