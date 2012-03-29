package com.example.android.hello2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
//aああ
public class EverToHatenaActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.everget);
		
		//遷移元から値を受け取る
        String titleString = null;
        String contentString = null;
		Bundle extras=getIntent().getExtras();
		if (extras!=null) {
			titleString = (String)extras.getString("TITLE");
			contentString = (String)extras.getString("CONTENT");
		}
		
	    //タイトルのEditTextのidを取得し、値を設定する。
    	TextView textViewData = (TextView)findViewById(R.id.editText1);
    	textViewData.setText(titleString);
    	
    	//本文のEditTextのidを取得し、値を設定する。
    	TextView textViewData2 = (TextView)findViewById(R.id.editText2);
    	textViewData2.setText(contentString);
    	
		
	}

}
