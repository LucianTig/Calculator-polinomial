package com.calpolinom.calculatorpolinoame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd;
    private Button buttonMinus;
    private Button buttonIntegrare;
    private Button buttonDerivare;
    private Button buttonDivide;
    private Button buttonMul;

    private Polinom pol1;
    private Polinom pol2;

    private EditText textPol1;
    private EditText textPol2;
    private TextView textResult;
    private Keyboard keyboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        keyboard=(Keyboard)findViewById(R.id.keyboard);

        buttonAdd=(Button)findViewById(R.id.buttonAdd);
        buttonMinus=(Button)findViewById(R.id.buttonMinus);
        buttonIntegrare=(Button)findViewById(R.id.buttonIntegrare);
        buttonDerivare=(Button)findViewById(R.id.buttonDerivare);
        buttonDivide=(Button)findViewById(R.id.buttonDivide);
        buttonMul=(Button)findViewById(R.id.buttonMul);


        textPol1=(EditText)findViewById(R.id.pol1);
        textPol2=(EditText)findViewById(R.id.pol2);
        textResult=(TextView)findViewById(R.id.result);

        textPol1.setRawInputType(InputType.TYPE_CLASS_TEXT);
        textPol1.setTextIsSelectable(true);

        textPol2.setRawInputType(InputType.TYPE_CLASS_TEXT);
        textPol2.setTextIsSelectable(true);

        textPol1.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = textPol1.getInputType(); // backup the input type
                textPol1.setInputType(InputType.TYPE_NULL); // disable soft input
                textPol1.onTouchEvent(event); // call native handler
                textPol1.setInputType(inType); // restore input type
                InputConnection ic=textPol1.onCreateInputConnection(new EditorInfo());
                keyboard.setInputConnection(ic);
                return true; // consume touch even
            }
        });

        textPol2.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = textPol2.getInputType(); // backup the input type
                textPol2.setInputType(InputType.TYPE_NULL); // disable soft input
                textPol2.onTouchEvent(event); // call native handler
                textPol2.setInputType(inType); // restore input type
                InputConnection ic=textPol2.onCreateInputConnection(new EditorInfo());
                keyboard.setInputConnection(ic);
                return true; // consume touch even
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pol1=getPol(textPol1.getText().toString());
                pol2=getPol(textPol2.getText().toString());

                Polinom result;
                result=pol1.adunare(pol2);
                textResult.setText(result.toString());

            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pol1=getPol(textPol1.getText().toString());
                pol2=getPol(textPol2.getText().toString());

                Polinom result;
                result=pol1.scadere(pol2);
                textResult.setText(result.toString());

            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pol1=getPol(textPol1.getText().toString());
                pol2=getPol(textPol2.getText().toString());

                Polinom result;
                result=pol1.inmultire(pol2);
                textResult.setText(result.toString());

            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pol1=getPol(textPol1.getText().toString());
                pol2=getPol(textPol2.getText().toString());

                ArrayList<Polinom> result=new ArrayList<>();
                result=pol1.impartire(pol2);
                textResult.setText(result.get(0).toString()+" remainder:"+result.get(1).toString());

            }
        });

        buttonDerivare.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pol1=getPol(textPol1.getText().toString());
                pol1.derivare();
                textResult.setText(pol1.toString());

            }
        });

        buttonIntegrare.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                pol1=getPol(textPol1.getText().toString());
                pol1.integrare();
                textResult.setText(pol1.toString());

            }
        });


    }

    private void showToast(String s){
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
    }

    public Polinom getPol(String s) {
        String pattern="([+-]?\\d*)x(\\^(\\d+))?|([+-]\\d+)|(\\d+)";


        Pattern r=Pattern.compile(pattern);
        Matcher m=r.matcher(s);

        Polinom polParsat=new Polinom();

        try {
            while(m.find()) {
                //System.out.println(m.group(1));
                //System.out.println(m.group(3));
                if(m.group(2)==null && m.group(1)!=null) {
                    if(m.group(1).equals("")!=true && m.group(1).equals("+")!=true && m.group(1).equals("-")!=true)
                        polParsat.conPol(new Monom(Integer.parseInt(m.group(1)),1));
                    else
                        polParsat.conPol(new Monom(1,1));
                }else {
                    if(m.group(2)==null && m.group(1)==null && m.group(4)!=null)
                        polParsat.conPol(new Monom(Integer.parseInt(m.group(4)),0));
                    else {
                        if(m.group(1)!=null && m.group(3)!=null)
                            if(m.group(1).equals("")!=true && m.group(1).equals("+")!=true && m.group(1).equals("-")!=true)
                                polParsat.conPol(new Monom(Integer.parseInt(m.group(1)),Integer.parseInt(m.group(3))));
                            else
                                polParsat.conPol(new Monom(1,Integer.parseInt(m.group(3))));
                        else {
                            if(m.group(5)!=null)
                                polParsat.conPol(new Monom(Integer.parseInt(m.group(5)),0));
                            else {
                                showToast("Wrong inputs!");
                                throw new Exception("Nu se respecta formatul de introducere a datelor!!");
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return polParsat;
    }

}
