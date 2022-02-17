package com.example.calculatrice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button bt0,btpoint,btegale,btdiv,bt1,bt2,bt3,btfois,bt4,bt5,bt6,btmoins,bt7,bt8,bt9,btplus;
    TextView tv;
    double f,s,res;
    String op,a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt0=(Button)findViewById(R.id.bt0);
        btpoint=(Button)findViewById(R.id.btpoint);
        btegale=(Button)findViewById(R.id.btegale);
        btdiv=(Button)findViewById(R.id.btdiv);
        bt1=(Button)findViewById(R.id.bt1);
        bt2=(Button)findViewById(R.id.bt2);
        bt3=(Button)findViewById(R.id.bt3);
        btfois=(Button)findViewById(R.id.btfois);
        bt4=(Button)findViewById(R.id.bt4);
        bt5=(Button)findViewById(R.id.bt5);
        bt6=(Button)findViewById(R.id.bt6);
        btmoins=(Button)findViewById(R.id.btmoins);
        bt7=(Button)findViewById(R.id.bt7);
        bt8=(Button)findViewById(R.id.bt8);
        bt9=(Button)findViewById(R.id.bt9);
        btplus=(Button)findViewById(R.id.btplus);
        tv=(TextView)findViewById(R.id.tv);

    }
    public void met_0(View view){
        String input=tv.getText()+"0";
        tv.setText(input);
    }
    public void met_1(View view){
        String input=tv.getText()+"1";
        tv.setText(input);
    }
    public void met_2(View view){
        String input=tv.getText()+"2";
        tv.setText(input);
    }
    public void met_3(View view){
        String input=tv.getText()+"3";
        tv.setText(input);
    }
    public void met_4(View view){
        String input=tv.getText()+"4";
        tv.setText(input);
    }
    public void met_5(View view){
        String input=tv.getText()+"5";
        tv.setText(input);
    }
    public void met_6(View view){
        String input=tv.getText()+"6";
        tv.setText(input);
    }
    public void met_7(View view){
        String input=tv.getText()+"7";
        tv.setText(input);
    }
    public void met_8(View view){
        String input=tv.getText()+"8";
        tv.setText(input);
    }
    public void met_9(View view){
        String input=tv.getText()+"9";
        tv.setText(input);
    }
    public void met_point(View view){
        String input=tv.getText()+".";
        tv.setText(input);
    }
    public void met_plus(View view){
        f=Double.parseDouble(tv.getText().toString());
        tv.setText("");
        op="+";
    }
    public void met_div(View view){
        f=Double.parseDouble(tv.getText().toString());
        tv.setText("");
        op="/";
    }
    public void met_moins(View view){
        f=Double.parseDouble(tv.getText().toString());
        tv.setText("");
        op="-";
    }
    public void met_fois(View view){
        f=Double.parseDouble(tv.getText().toString());
        tv.setText("");
        op="*";
    }
    public void met_egale(View view){

        s=Double.parseDouble(tv.getText().toString());
        switch (op){
            case "+":
                res=f+s;
                tv.setText(""+res);
                break;
            case "-":
                res=f-s;
                tv.setText(""+res);
                break;
            case "*":
                res=f*s;
                tv.setText(""+res);
                break;
            case "/":
                res=f/s;
                tv.setText(""+res);
                break;
        }



    }
}
