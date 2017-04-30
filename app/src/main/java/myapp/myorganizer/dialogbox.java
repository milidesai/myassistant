package myapp.myorganizer;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Amit Desai on 07-03-2016.
 */
public class dialogbox extends DialogFragment implements View.OnClickListener {
        String str;
        Button doneButton;
        EditText name,cell_no;
        View rootView;
        String nm, phone;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.popup, container, false);
            getDialog().setTitle("Add Contact");
            doneButton = (Button) rootView.findViewById(R.id.add);
            name = (EditText) rootView.findViewById(R.id.editText2);
            cell_no = (EditText) rootView.findViewById(R.id.editText3);
            nm = getArguments().getString("name");
            phone = getArguments().getString("phone");
            name.setText(nm);
            cell_no.setText(phone);

            doneButton.setOnClickListener(this);
            return rootView;
        }

        /*public void onActivityCreated(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           //doneButton.setOnClickListener(this);
        }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add : str = name.getText().toString() +": "+cell_no.getText().toString();
                            System.out.println(str);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                            dismiss();
                            break;
            default: break;
        }
    }
    void writeList(String str)
    {
        int k =0;
        //String date;
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        //String fileName = baseDir + File.separator + "timetable.txt";
        String fileName = "/sdcard/contacts.txt";
        //String fileName2 = "/sdcard/myhellotest.txt";
        //InputStream inputStream = getResources().openRawResource(R.raw.timetable);

        //text.setText("Reading File '" + fileName + "'");
        String line;
        int dayno;

        try {
            // line = br.readLine();
            FileWriter filewrite = new FileWriter(fileName);
            BufferedWriter br  = new BufferedWriter(filewrite);


            //text.setText("File Found " + fileName);


                line = str;
                //System.out.println (line);
                filewrite.write(line);
                filewrite.write("\n");




        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            //text.setText("Error Reading File " + fileName);
            e.printStackTrace();
        }
        //System.out.println("Reading file over");


    }
}

