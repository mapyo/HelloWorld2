package com.example.android.hello2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
//a����
public class EverToHatenaActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.everget);
		
		//�J�ڌ�����l���󂯎��
        String titleString = null;
        String contentString = null;
		Bundle extras=getIntent().getExtras();
		if (extras!=null) {
			titleString = (String)extras.getString("TITLE");
			contentString = (String)extras.getString("CONTENT");
		}
		
	    //�^�C�g����EditText��id���擾���A�l��ݒ肷��B
    	TextView textViewData = (TextView)findViewById(R.id.editText1);
    	textViewData.setText(titleString);
    	
    	//�{����EditText��id���擾���A�l��ݒ肷��B
    	TextView textViewData2 = (TextView)findViewById(R.id.editText2);
    	textViewData2.setText(contentString);
    	
		
	}

}
