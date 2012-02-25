package com.example.android.hello2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//�^�C�g����guid��z�񂩂�Ƃ��ăR�s�[���Ă�B
//todo�F�{����z��̕ϐ�����Ď擾����B
public class HelloWorld2Activity extends Activity {

	public Evernote demo;
	
    public String everList[][]={
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""},
			{"", ""}
    };
    
    public HelloWorld2Activity(){
		Evernote demo = new Evernote("mapyo_dev", "12345678");
	    try {
	    	everList=demo.listNotes();
		} catch (Exception e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
    }

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Evernote���^�C�g���̎擾
        
        //���X�g�̒ǉ�
        String[] items ={everList[0][0],everList[1][0],everList[2][0],everList[3][0],everList[4][0],everList[5][0]};
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this,
        		android.R.layout.simple_list_item_1,
        		items
        		);
        
        ListView listView = (ListView) findViewById(R.id.listView1);
        // �A�_�v�^�[��ݒ肵�܂�
        listView.setAdapter(adapter);

        // ���X�g�r���[�̃A�C�e�����N���b�N���ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // �N���b�N���ꂽ�A�C�e�����擾���܂�
                String item = (String) listView.getItemAtPosition(position);
                System.out.println(item);

                //position�͉��Ԗڂ̃��X�g����Ԃ��B
                String test=everList[position][0];
                Toast.makeText(HelloWorld2Activity.this, test, Toast.LENGTH_LONG).show();
                //��ʂ�J�ڂ�����
//              @Override
              Intent intent = new Intent();
              //�J�ڐ�ɒl��n��
              intent.putExtra("TITLE", everList[position][0]);
//              intent.putExtra("CONTENT", "test");
                  System.out.println(everList[position][1]);
  				intent.putExtra("CONTENT", everList[position][1]);
			
              intent.setClassName(
                      "com.example.android.hello2",
                      "com.example.android.hello2.EverToHatenaActivity");
              startActivity(intent);
            }
        });
        
        // ���X�g�r���[�̃A�C�e�����I�����ꂽ���ɌĂяo�����R�[���o�b�N���X�i�[��o�^���܂�
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // �I�����ꂽ�A�C�e�����擾���܂�
                String item = (String) listView.getSelectedItem();
//                Toast.makeText(HelloWorld2Activity.this, item, Toast.LENGTH_LONG).show();
                
                
                
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    
    }
}