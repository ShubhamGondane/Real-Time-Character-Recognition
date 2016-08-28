/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectscaling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class ProjectScaling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            BufferedReader br1 = new BufferedReader(new FileReader("data.txt"));
            
            BufferedWriter bw=new BufferedWriter(new FileWriter("out.txt"));
            
            String ss,ss1;
            double xmin,ymin,xmax,ymax,x,y;
            xmin=99999;
            ymin=99999;
            xmax=0;
            ymax=0;
            while((ss=br.readLine())!=null)
            {
                
                if(ss.contains("="))
                {
                    ymax=ymax-ymin;
                    xmax=xmax-xmin;
                    
                    while((ss1=br1.readLine())!=null)
                    {
                        if(!(ss1.contains("=")))
                        {
                            String aa[]=ss1.split(",");
                            x=Float.parseFloat(aa[0]);
                            String a[]=aa[1].split(";");
                            y=Float.parseFloat(a[0]);
                        
                        
                            x=(x-xmin)/xmax;
                            y=(y-ymin)/ymax;
                            String bb=x+","+y+"\n";
                            bw.append(bb);
                        }
                    }
                    bw.append("====\n");
                  
                    xmax=0;
                    ymax=0;
                    xmin=99999;
                    ymin=99999;
                    
                }
                else
                {
                    String aa[]=ss.split(",");
                    x=Float.parseFloat(aa[0]);
                    String a[]=aa[1].split(";");
                    y=Float.parseFloat(a[0]);
                    
                    if(x>xmax)
                    {
                        xmax=x;
                    }
                    else if(x<xmin)
                    {
                        xmin=x;
                    }
                    
                    if(y>ymax)
                    {
                        ymax=y;
                        
                    }
                    else if(y<ymin)
                    {
                        ymin=y;
                    }
                }
            }
            bw.flush();
            bw.close();
            
            System.out.println(xmax+","+ymax+","+xmin+","+ymin);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProjectScaling.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProjectScaling.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
