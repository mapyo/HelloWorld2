package com.example.android.hello2;

import java.util.*;
import java.io.*;
import java.security.MessageDigest;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.THttpClient;

import com.evernote.edam.type.*;
import com.evernote.edam.userstore.*;
import com.evernote.edam.error.*;
import com.evernote.edam.userstore.Constants;
import com.evernote.edam.notestore.*;



public class Evernote {

	private static final String consumerKey = "mapyo-2633";
	private static final String consumerSecret = "7549aa3231d1f085";
	private static final String evernoteHost = "sandbox.evernote.com";
	private static final String userStoreUrl = "https://" + evernoteHost + "/edam/user";
	private static final String noteStoreUrlBase = "https://" + evernoteHost + "/edam/note/";
	private static final String userAgent = "Evernote/EDAMDemo (Java) " + 
											Constants.EDAM_VERSION_MAJOR + "." + 
											Constants.EDAM_VERSION_MINOR;
	  private UserStore.Client userStore;
	  private NoteStore.Client noteStore;
	  private String authToken;
	  private String newNoteGuid;
	  
	public String everContent[]={"","","","",""};

	  Evernote(String a, String b)
//	  throws Exception
	  {
//		    if (args.length < 2) {
//		        System.err.println("Arguments:  <username> <password>");
//		        return;}
		  String username,password;
		  username=a;
		  password=b;
		  try {
			this.intitialize(username, password);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	  }
	  
	  /**
	   * Intialize UserStore and NoteStore clients. During this step, we authenticate
	   * with the Evernote web service.
	   */
	  public boolean intitialize(String username, String password) 
	    throws Exception
	  {
	    // Set up the UserStore client. The Evernote UserStore allows you to
	    // authenticate a user and access some information about their account.
	    THttpClient userStoreTrans = new THttpClient(userStoreUrl);
	    userStoreTrans.setCustomHeader("User-Agent", userAgent);
	    TBinaryProtocol userStoreProt = new TBinaryProtocol(userStoreTrans);
	    userStore = new UserStore.Client(userStoreProt, userStoreProt);
	    
	    // Check that we can talk to the server
	    boolean versionOk = userStore.checkVersion("Evernote EDAMDemo (Java)",
	        com.evernote.edam.userstore.Constants.EDAM_VERSION_MAJOR,
	        com.evernote.edam.userstore.Constants.EDAM_VERSION_MINOR);
	    if (!versionOk) {
	      System.err.println("Incomatible EDAM client protocol version");
	      return false;
	    }

	    // Authenticate using username & password
	    AuthenticationResult authResult = null;
	    try {
	      authResult = userStore.authenticate(username, password, consumerKey, consumerSecret);
	    } catch (EDAMUserException ex) {
	      // Note that the error handling here is far more detailed than you would 
	      // provide to a real user. It is intended to give you an idea of why the 
	      // sample application isn't able to authenticate to our servers.
	      
	      // Any time that you contact us about a problem with an Evernote API, 
	      // please provide us with the exception parameter and errorcode. 
	      String parameter = ex.getParameter();
	      EDAMErrorCode errorCode = ex.getErrorCode();
	      
	      System.err.println("Authentication failed (parameter: " + parameter + " errorCode: " + errorCode + ")");
	      
	      if (errorCode == EDAMErrorCode.INVALID_AUTH) {
	        if (parameter.equals("consumerKey")) {
	          if (consumerKey.equals("en-edamtest")) {
	            System.err.println("You must replace the variables consumerKey and consumerSecret with the values you received from Evernote.");
	          } else {
	            System.err.println("Your consumer key was not accepted by " + evernoteHost);
	            System.err.println("This sample client application requires a client API key. If you requested a web service API key, you must authenticate using OAuth as shown in sample/java/oauth");
	          }
	          System.err.println("If you do not have an API Key from Evernote, you can request one from http://www.evernote.com/about/developer/api");
	        } else if (parameter.equals("username")) {
	          System.err.println("You must authenticate using a username and password from " + evernoteHost);
	          if (evernoteHost.equals("www.evernote.com") == false) {
	            System.err.println("Note that your production Evernote account will not work on " + evernoteHost + ",");
	            System.err.println("you must register for a separate test account at https://" + evernoteHost + "/Registration.action");
	          }
	        } else if (parameter.equals("password")) {
	          System.err.println("The password that you entered is incorrect");
	        }
	      }

	      return false;
	    }
	    
	    // The result of a succesful authentication is an opaque authentication token
	    // that you will use in all subsequent API calls. If you are developing a
	    // web application that authenticates using OAuth, the OAuth access token
	    // that you receive would be used as the authToken in subsquent calls.
	    authToken = authResult.getAuthenticationToken();

	    // The Evernote NoteStore allows you to accessa user's notes.    
	    // In order to access the NoteStore for a given user, you need to know the 
	    // logical "shard" that their notes are stored on. The shard ID is included 
	    // in the URL used to access the NoteStore.
	    User user = authResult.getUser();
	    String shardId = user.getShardId();
	    
	    System.out.println("Successfully authenticated as " + user.getUsername());
	    
	    // Set up the NoteStore client 
	    String noteStoreUrl = noteStoreUrlBase + shardId;
	    THttpClient noteStoreTrans = new THttpClient(noteStoreUrl);
	    noteStoreTrans.setCustomHeader("User-Agent", userAgent);
	    TBinaryProtocol noteStoreProt = new TBinaryProtocol(noteStoreTrans);
	    noteStore = new NoteStore.Client(noteStoreProt, noteStoreProt);
	    
	    return true;
	  }	  

	  /**
	   * Retrieve and display a list of the user's notes.
	   */
	String[][] listNotes()
	    throws Exception 
	  {
//		String list[][] = null;
		String list[][] ={
				{"nanimonai", ""},	//todo:後で消す
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

		// List all of the notes in the user's account
	    System.out.println("Listing all notes:");

	    // First, get a list of all notebooks
	    List<Notebook> notebooks = noteStore.listNotebooks(authToken);
	    
	    for (Notebook notebook : notebooks) {
	      System.out.println("Notebook: " + notebook.getName());
	      
	      // Next, search for the first 100 notes in this notebook, ordering by creation date
	      //ノートの最初の１０件を取得する。更新日時順に。
	      NoteFilter filter = new NoteFilter();
	      filter.setNotebookGuid(notebook.getGuid());
	      filter.setOrder(NoteSortOrder.UPDATED.getValue());	//ソートの種類
	      filter.setAscending(false);	//降順に取得する。
	      
	      NoteList noteList = noteStore.findNotes(authToken, filter, 0, 10);
	      List<Note> notes = noteList.getNotes();
	      int i=0;
	      for (Note note : notes) {
	        System.out.println(" * " + note.getTitle() + ":" + note.getGuid());
	        list[i][0]=note.getTitle();
	        list[i][1]=note.getGuid();
//	        list[i][1]=note.getContent();
	        //本文の取得（全然変な作りだけど、とりあえずすぐ作りたかった！
//	        everContent[i]=note.getContent();
	        i++;
	      }
	    }
	    //todo:test用
	    for(int i=0;i<5;i++)
	       list[i][1]=getNote(list[i][1]);
	    return list;
	  }
	  
	  // GUIDからノートを取得する。
	  public String getNote(String guid)
	  throws Exception
	  {
		  Note fullNote = noteStore.getNote(authToken, guid, true, true, false, false);
	      System.out.println("Note title " + fullNote.getTitle());
	      System.out.println("本文");
	      System.out.println(fullNote.getContent());//todo:本文のタグを覗いて表示させる。
	      return fullNote.getContent();
	  }	  
	  
}
