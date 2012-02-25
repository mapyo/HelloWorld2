package com.example.android.hello2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//タイトルとguidを配列からとってコピーしてる。
//todo：本文を配列の変数作って取得する。
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
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Evernoteよりタイトルの取得
        
        //リストの追加
        String[] items ={everList[0][0],everList[1][0],everList[2][0],everList[3][0],everList[4][0],everList[5][0]};
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this,
        		android.R.layout.simple_list_item_1,
        		items
        		);
        
        ListView listView = (ListView) findViewById(R.id.listView1);
        // アダプターを設定します
        listView.setAdapter(adapter);

        // リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                String item = (String) listView.getItemAtPosition(position);
                System.out.println(item);

                //positionは何番目のリストかを返す。
                String test=everList[position][0];
                Toast.makeText(HelloWorld2Activity.this, test, Toast.LENGTH_LONG).show();
                //画面を遷移させる
//              @Override
              Intent intent = new Intent();
              //遷移先に値を渡す
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
        
        // リストビューのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // 選択されたアイテムを取得します
                String item = (String) listView.getSelectedItem();
//                Toast.makeText(HelloWorld2Activity.this, item, Toast.LENGTH_LONG).show();
                
                
                
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    
    }
}