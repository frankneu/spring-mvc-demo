package com.github.frankneu.spring.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyModelAndViewImpl extends MyModelAndView {

	private String perfix;
	
	public MyModelAndViewImpl(String viewName) {
		super(viewName);
		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
        path=path.replace('/', '\\');   
        path=path.replace("file:", "");  
        path=path.replace("classes\\", "");  
        path=path.substring(1);  
        perfix = path;		
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> model) {
		FileInputStream fis = null;
		String filename = this.getView();
        List<String> htmlList = new ArrayList<String>();
        try {
            fis = new FileInputStream(new File(perfix + filename));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));

            String line = null;
            while ((line = reader.readLine()) != null) {
            	if(line.indexOf("$") ==0 && model.containsKey(line.substring(1, line.length()))){
            		htmlList.add(model.get(line.substring(1, line.length())).toString());
            	}else{
            		htmlList.add(line);
            	}
            }
            reader.close();
            ;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
     	if (resp == null ){
     			throw new RuntimeException("request null£¡");
     	}
     	StringBuilder sb = new StringBuilder();
     	for(String str : htmlList)
     		sb.append(str+"</br>");
		resp.setCharacterEncoding("UTF-8");
     	resp.setContentLength(sb.length());
		try {
			PrintWriter out = resp.getWriter();
			out.print(sb);
	     	out.flush();
	     	out.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}     	    	
	}
}
