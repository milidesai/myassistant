package myapp.myorganizer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amit Desai on 06-03-2016.
 */
public class contact extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    int i =0,id1,flag=0;
    ListView myfav;
    ArrayAdapter<String> aa2;
    final ArrayList<String> favcontacts = new ArrayList<String>();
    final ArrayList<String> names = new ArrayList<String>();
    final ArrayList<String> phone = new ArrayList<String>();
    ImageButton add_contacts,delete_no,update,call;
    EditText contactText, contactNoText;
    public static final int DIALOG_FRAGMENT = 1;
    dialogbox dialogFragment;
    Fragment fragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts, container,
                false);
        add_contacts = (ImageButton) rootView.findViewById(R.id.add_contact);
        delete_no = (ImageButton) rootView.findViewById(R.id.delete_contact);
        update = (ImageButton) rootView.findViewById(R.id.update_contact);
        call = (ImageButton) rootView.findViewById(R.id.call_button);
        myfav = (ListView) rootView.findViewById(R.id.contact_list);
        contactText = (EditText) rootView.findViewById(R.id.editText2);
        contactNoText = (EditText) rootView.findViewById(R.id.editText3);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //j = x = 0;

        aa2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, favcontacts);
        myfav.setAdapter(aa2);
        readList();
        aa2.notifyDataSetChanged();
        add_contacts.setOnClickListener(this);
        delete_no.setOnClickListener(this);
        update.setOnClickListener(this);
        call.setOnClickListener(this);
        myfav.setOnItemClickListener(this);
        myfav.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {

            @Override
            public void onSwipeLeft() {
                fragment = new timetable_fragment();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(1, true);
                    //setTitle(mDrawerMenu[1]);
                    getActivity().setTitle("Timetable");
                }
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                fragment = new todo();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(2, true);
                    getActivity().setTitle("To Do List");
                }
                super.onSwipeRight();
            }
        });

    }


    @Override
    public void onClick(View v) {

           Bundle args;

           switch(v.getId())
           {
               case R.id.add_contact:
                   FragmentManager fm = getFragmentManager();
                   dialogFragment = new dialogbox ();
                   args = new Bundle ();
                   args.clear();
                   args.putString("name", "");
                   args.putString("phone", "");
                   dialogFragment.setArguments(args);
                   dialogFragment.setTargetFragment(this, DIALOG_FRAGMENT);
                   dialogFragment.show(fm, "Sample Fragment");
                   //System.out.println("Dialog " + dialogFragment.str);
                   display();
                   break;
               case R.id.delete_contact:
                   favcontacts.remove(id1);
                   i--;
                   writeList();
                   aa2.notifyDataSetChanged();
                   flag = 0;
                   display();
                   break;
               case R.id.update_contact:
                   FragmentManager fm1 = getFragmentManager();
                   dialogFragment = new dialogbox ();
                   args = new Bundle ();
                   args.clear();
                   args.putString("name", names.get(id1));
                   args.putString("phone", phone.get(id1));
                   dialogFragment.setArguments(args);
                   dialogFragment.setTargetFragment(this, DIALOG_FRAGMENT);
                   dialogFragment.show(fm1, "Sample Fragment");
                   //System.out.println("Dialog " + dialogFragment.str);
                   display();
                   break;
               case R.id.call_button:
                   Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                   phoneIntent.setData(Uri.parse("tel:"+phone.get(id1)));
                   startActivity(phoneIntent);
                   flag = 0;
                   break;
               default:break;
           }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DIALOG_FRAGMENT:
                //if(flag == 1)
                //{

                //}
                if (resultCode == Activity.RESULT_OK) {
                    if (dialogFragment != null) {
                        System.out.println("Dialog " + dialogFragment.str);
                        if(flag==0)
                        {
                            favcontacts.add(i, dialogFragment.str);
                            names.add(i, dialogFragment.name.getText().toString());
                            phone.add(i, dialogFragment.cell_no.getText().toString());
                            System.out.println(names.get(i));
                            System.out.println(phone.get(i));
                            i++;
                            writeList();
                            aa2.notifyDataSetChanged();
                        }
                        else
                        {
                            favcontacts.set(id1,dialogFragment.str);
                            names.set(id1, dialogFragment.name.getText().toString());
                            phone.set(id1,dialogFragment.cell_no.getText().toString());
                            writeList();
                            aa2.notifyDataSetChanged();
                            System.out.println(names.get(id1));
                            flag = 0;
                        }
                    }

                } else if (resultCode == Activity.RESULT_CANCELED){
                    // After Cancel code.
                }

                break;
        }
    }

    public void readList()
    {
        String fileName = "/sdcard/contacts.txt";
        String line;
        int point;
        try {
            boolean contactsFound = false;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);
            create_toast("Reading contacts file ");
            while ((line = br.readLine()) != null)
               {
                contactsFound = true;
                System.out.println ("Reading contact " + line);
                favcontacts.add(i, line);
                point=line.indexOf(":");
                System.out.println(point);
                names.add(i,line.substring(0,point));
                phone.add(i,line.substring(point+2));
                i++;
               }
            if (br != null) {
                br.close();
                if (!contactsFound)
                    writeList();
            }
            }
        catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Error Reading File " + fileName);
                e.printStackTrace();
            }
    }
    void create_toast(String text)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity(),text, duration);
        toast.show();
    }
    void writeList()
    {
        int k =0;
        String fileName = "/sdcard/contacts.txt";
        String line;

        try {
            FileWriter filewrite = new FileWriter(fileName);
            BufferedWriter br  = new BufferedWriter(filewrite);
            while(k<i) {
                line = favcontacts.get(k);
                System.out.println (line);
                if (line != null) {
                    System.out.println ("Writing contact :" + line);
                    filewrite.write(line);
                    filewrite.write("\n");
                }
                k++;
            }
            br.close();

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            //text.setText("Error Reading File " + fileName);
            e.printStackTrace();
        }
        //System.out.println("Reading file over");


    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //view.setSelected(true);
        id1 = (int) id;
        //item  = (EditText) getActivity().findViewById(R.id.editText);
        //item.setText(todoItems.get((int) id));
        flag = 1;
    }
    void display()
    {
        int k=0;
        while(k<i) {
            System.out.println(names.get(k));
            System.out.println(phone.get(k));
            k++;
        }
    }
}
