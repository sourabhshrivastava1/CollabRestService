/*package com.niit.collaboration.rest.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.json.Json;
import javax.json.JsonWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.*;






@ServerEndpoint(value="/serverendpointdemo",configurator=ServletAwareConfig.class)
public class ServerEndPointDemo {
	
	
	static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());
   
	//==================================================================
	
	
	
	
	//==================================================================
	
	@OnOpen
	public void handleOpen(Session userSession)
	{
		 
		
		
         chatroomUsers.add(userSession);
	}
	
	@OnMessage
	public void handleMessage(String message,Session userSession) throws IOException 
	{
		 
		//========================================================
		
		//HttpSession httpSession = (HttpSession) userSession.getUserProperties().get("httpSession");
       //  ServletContext servletContext = httpSession.getServletContext();
    	
       //  String username=(String)httpSession.getAttribute("loggedInUserID");
		
		// String username = (String) userSession.getUserProperties().get(httpSession.getAttribute("loggedInUserID"));
		
		//===========================================================
		
		
		
		String username = (String) userSession.getUserProperties().get("username");
		if(username==null){
			userSession.getUserProperties().put("username",message);
			userSession.getBasicRemote().sendText(buildJsonData("System"," you are connect " + message));
		}
		else
		{
			Iterator<Session> iterator = chatroomUsers.iterator();
			while (iterator.hasNext())iterator.next().getBasicRemote().sendText(buildJsonData(username,message));
		}
		
		
		
		
	}
	
	@OnClose
	public void handleClose(Session userSession)
	{ 
		chatroomUsers.remove(userSession);
		System.out.println("client id desconnected.............");
	}
	
	@OnError
	public void handleError(Throwable t)
	{
		 t.printStackTrace();
	}
	
    
	private String buildJsonData(String username , String message)
	{   
		
		javax.json.JsonObject jsonObject = Json.createObjectBuilder().add("message",username+":"+message).build();
		
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter))
		{
			jsonWriter.write(jsonObject);
		}
		
		
		
		 return stringWriter.toString();
	}
	
	
	
	
	class  DataInfo
	{
		Session s;
		String user;
		DataInfo(Session s,String user)
		{
			this.s=s;
			this.user=user;
		}
	}
	static Set<DataInfo> sessionlist;//=new CopyOnWriteArraySet<Session>();
	static Set<String> userlist=new HashSet<String>();
  static  Thread t;
  private  EndpointConfig config;
  static void sendToAll(final int i)
  {
t=new Thread(){
		  
		  public void run()
		  {
			
				 for(DataInfo s:sessionlist)
				 {
					 System.out.println(s.s.getId());
					 try{
					s.s.getBasicRemote().sendText(i+"is connected");
					 }
					 catch(Exception e)
					 {
						 System.out.println(e);
					 }
				 }
				 try{
				 Thread.sleep(1000);
				 }
				 catch(Exception e)
				 {
					// System.out.println(e);
				 }
				 }
		  
		  
	  };
	  t.start();
  }
  static
  {
	  sessionlist=new CopyOnWriteArraySet<DataInfo>();
	  
  }

	@OnMessage
    public void onMessage(String message, Session session) throws IOException,
            InterruptedException {
		
		
		 System.out.println(message);
		  for(DataInfo s:sessionlist)
		  {
			  
			  System.out.println("hay");
		
			  s.s.getBasicRemote().sendText(message);
			  
			  }
    
      
      
        	
    }
 
    @OnOpen
    public void onOpen(Session session,EndpointConfig c) {
    	 this.config=c;
    	

    	 HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
         ServletContext servletContext = httpSession.getServletContext();
    	
         String user=(String)httpSession.getAttribute("logged_user_id");
         userlist.add(user);
         System.out.println(userlist);
        DataInfo obj=new DataInfo(session,user);
        sessionlist.add(obj);
         for(DataInfo s:sessionlist)
         {
        	 try{
        		 
        	 s.s.getBasicRemote().sendText(userlist+" connected");
        	 }
        	 catch(Exception e)
        	 {
        		 System.out.println(e);
        	 }
        	 }
    	System.out.println("connected "+user);
    }
 
    @OnClose
    public void onClose(Session s) {
   
    	for(DataInfo obj:sessionlist)
    	{
    		if(s.getId().equals(obj.s.getId()))
    		{
    			System.out.println("math found");
    			sessionlist.remove(obj);
    			System.out.println(obj.user+" removed");
    			userlist.remove(obj.user);
    		}
    	}
    	
    	System.out.println(s.getId()+" is closed");
 
    }
	
}
*/