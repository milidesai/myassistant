package myapp.myorganizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.method.KeyListener;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
 * Created by Amit Desai on 04-02-2016.
 */
public class todo extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnKeyListener {
    ImageButton updatebutton,next;
    String str1;
    ImageButton ref,addbutton,delbutton,later,movetolater;
    int j,id1,flag =0,x;
    ListView todolistView;
    ArrayAdapter<String> aa1;
    final ArrayList<String> todoItems = new ArrayList<String>();
    final ArrayList<String> nextItems = new ArrayList<String>();
    EditText item;
    Fragment fragment;
    //Boolean click=false;
   // todo td = new todo();
    longer_to_do ltd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todolist, container,
                false);

        addbutton = (ImageButton) rootView.findViewById(R.id.button);
        todolistView = (ListView) rootView.findViewById(R.id.listView2);
        delbutton = (ImageButton) rootView.findViewById(R.id.delete_button);
        updatebutton = (ImageButton) rootView.findViewById(R.id.up_button);
        next = (ImageButton) rootView.findViewById(R.id.tomm_button);
        ref = (ImageButton) rootView.findViewById(R.id.refresh);
        item  = (EditText) rootView.findViewById(R.id.editText);
        later = (ImageButton) rootView.findViewById(R.id.later);
        ltd = new longer_to_do();
        movetolater = (ImageButton) rootView.findViewById(R.id.movetolater);
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        j = x = 0;
        aa1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, todoItems);
        todolistView.setAdapter(aa1);
        readList();
        ltd.readList();
        addbutton.setOnClickListener(this);
        aa1.notifyDataSetChanged();
        todolistView.setOnItemClickListener(this);
        delbutton.setEnabled(false);
        delbutton.setOnClickListener(this);
        updatebutton.setEnabled(false);
        updatebutton.setOnClickListener(this);
        next.setOnClickListener(this);
        ref.setOnClickListener(this);
        item.setOnKeyListener(this);
        later.setOnClickListener(this);
        movetolater.setOnClickListener(this);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        todolistView.setOnTouchListener(new OnSwipeTouchListener(getActivity()){

            @Override
            public void onSwipeLeft() {
                fragment = new contact();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(1, true);
                    //setTitle(mDrawerMenu[1]);
                    getActivity().setTitle("My Contacts");
                }
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeRight() {
                fragment = new timetable_fragment();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(2, true);
                    getActivity().setTitle("Timetable");
                    MainActivity main = new MainActivity();
                    main.mDrawerList.setItemChecked(2,true);
                }
                super.onSwipeRight();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("On Resuming");
        //onKey();
        //todolistView.refreshDrawableState();
        aa1.notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        String str;
        int i;
        str = item.getText().toString();
        //System.out.println("click");
        switch (v.getId())
        {
            case R.id.button :
                if((str != null) && (!str.trim().isEmpty())) {
                    todoItems.add(j, str);
                    j++;
                    //Context context = getApplicationContext();
                    create_toast("Added item");
                }
                break;
            case R.id.delete_button:
                todoItems.remove(id1);
                //System.out.println(id1);
                j--;
                create_toast("Removed item");
                //writeList();
                //aa1.notifyDataSetChanged();
               // item.setText(" ");
                break;
            case R.id.up_button:
                todoItems.set(id1, str);
                //System.out.println(id1);
                flag =0;
                create_toast("Updated item");
                break;
            case R.id.tomm_button:
                  str1 = todoItems.get(id1);
                  todoItems.remove(id1);
                  j--;
                  nextItems.add(str1);
                  x++;
                  create_toast("Item moved to tommorrow");
                  break;
            case R.id.refresh:
                 i = 0;
                 while(i < x)
                 {
                     //System.out.println(x);
                     todoItems.add(nextItems.remove(0));
                     i++;
                     j++;
                 }
                 x = 0;
                 create_toast("item brought back to today");
                 break;
            case R.id.later:
                 String str2 = todoItems.get(id1);
                 todoItems.remove(id1);
                 j--;
                 create_toast("adding item "+ ltd.j + " to later");
                 ltd.latertodoItems.add(ltd.j,str2);
                 ltd.j++;
                 ltd.writeList();
                 break;
            case R.id.movetolater:
                fragment = new longer_to_do();
                if (fragment != null) {
                    Bundle args = new Bundle();
                    // Insert the fragment by replacing any existing fragment
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    //mDrawerList.setItemChecked(2, true);
                    getActivity().setTitle("To do Later");

                }
                    break;
            default:
                break;

        }
        writeList();
        aa1.notifyDataSetChanged();
        delbutton.setEnabled(false);
        updatebutton.setEnabled(false);
        item.setText(" ");
    }
    public void readList()
    {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        //String fileName = baseDir + File.separator + "timetable.txt";
        String fileName = "/sdcard/todo.txt";
        //String fileName2 = "/sdcard/myhellotest.txt";
        //InputStream inputStream = getResources().openRawResource(R.raw.timetable);

        //text.setText("Reading File '" + fileName + "'");
        String line,d1,d2;
        int dayno;

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader br  = new BufferedReader(fileReader);
            Date d = new Date();
            boolean today = true;
            d2 =  "Date: "+ d;
            //text.setText("File Found " + fileName);
            //System.out.println (line);
            line = br.readLine();
            //System.out.println (d.toString().substring(8, 11));
            //System.out.println (line.substring(14,16));
            //System.out.println(line);

            if((line !=null)){
                String partline = line.substring(14,16);
                String partdate = d.toString().substring(8,10);
                //System.out.println (partline);
                //System.out.println (partdate);
               //d1 = "Date: " + (Integer.parseInt(line.substring(7))+1);
               if(partline.equalsIgnoreCase(partdate)) {
                  //line = " ";
                  //System.out.println("inside");
                  //System.out.println(line);
                  line = br.readLine();
                  while (line != null) {
                      //System.out.println(line.substring(0,4));
                      if ((line.length() >= 4) && (line.substring(0, 4).equalsIgnoreCase("Date")))
                          today = false;
                      if (today) {
                          todoItems.add(j, line);
                          j++;
                      }
                      else {
                          if ((line.length() >= 4) && (!line.substring(0, 4).equalsIgnoreCase("Date"))) {
                              nextItems.add(x, line);
                              x++;
                          }
                      }
                      line = br.readLine();
                      //System.out.println (line);
                  }

              }
              else
              {
                 // System.out.println("in else " + j);
                  while((line = br.readLine())!= null)
                  {
                      if ((line.length() >= 4) && (!line.substring(0, 4).equalsIgnoreCase("Date"))) {
                          //System.out.println("Adding line: " + line + " at " + j);
                          todoItems.add(j, line);
                          j++;
                      }
                  }
              }
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
    void create_toast(String text)
    {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity(),text, duration);
        toast.show();
    }
    void writeList()
    {
            int k =0;
            //String date;
            String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            //String fileName = baseDir + File.separator + "timetable.txt";
            String fileName = "/sdcard/todo.txt";
            //String fileName2 = "/sdcard/myhellotest.txt";
            //InputStream inputStream = getResources().openRawResource(R.raw.timetable);

            //text.setText("Reading File '" + fileName + "'");
            String line;
            int dayno;

            try {
                // line = br.readLine();
                FileWriter filewrite = new FileWriter(fileName);
                BufferedWriter br  = new BufferedWriter(filewrite);
                Date dt = new Date();

                //text.setText("File Found " + fileName);
                filewrite.write("Date: "+dt);
                filewrite.write("\n");
                while(k<j) {
                    line = todoItems.get(k);
                    //System.out.println (line);
                    filewrite.write(line);
                    filewrite.write("\n");
                    k++;

                }
                Calendar cd = Calendar.getInstance();
                cd.add(Calendar.DATE,1);
                //System.out.println(Calendar.DATE);
                filewrite.write("Date: "+cd.get(Calendar.DATE));
                line = "Date: "+cd.get(Calendar.DATE);
                System.out.println(line);
                filewrite.write("\n");
                k =0;
                while(k<x)
                {
                    line = nextItems.get(k);
                    //System.out.println (line);
                    filewrite.write(line);
                    filewrite.write("\n");
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //view.setSelected(true);
        id1 = (int)id;
        item  = (EditText) getActivity().findViewById(R.id.editText);
        item.setText(todoItems.get((int) id));
        flag = 1;
        updatebutton.setEnabled(true);
        delbutton.setEnabled(true);

    }
    /*public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if(key == KeyEvent.KEYCODE_ENTER)
        {
            todoItems.set(id1,item.getText().toString());
        }
    }*/


    //@Override
    /*public boolean onDrag(View v, DragEvent event) {
        todolistView.change
        return false;
    }*/
    public void onEnter()
    {
        String str;
        str = item.getText().toString();
        if((str != null) && (!str.trim().isEmpty())&&(flag == 0)) {
            todoItems.add(j, str);
            j++;
            //Context context = getApplicationContext();
            create_toast("Added item");
        }
        else
        {
            todoItems.set(id1, str);
            flag =0;
            create_toast("Updated item");
        }
        writeList();
        aa1.notifyDataSetChanged();
        item.setText(" ");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
              switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                         onEnter();
                         return true;
                     default:
                         break;
                    }
        }
        return false;
    }
}
