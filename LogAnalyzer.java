
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         // complete constructor
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         // complete method
         FileResource fr = new FileResource(filename);
         for(String line : fr.lines())
         {
            LogEntry logs = WebLogParser.parseEntry(line);
            records.add(logs);
         }
         
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     public int countUniqueIPs()
     {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le:records)
        {
         String ipAddr = le.getIpAddress();
         if ( !uniqueIPs.contains(ipAddr))
         {
             uniqueIPs.add(ipAddr);
            }
        }
        return uniqueIPs.size();
      }
      
      
      public void printAllHigherThanNum(int num)
      {
        
        for (LogEntry le:records)
        {
         int statuscode = le.getStatusCode();
         if (statuscode > num)
         {
         System.out.println(le);   
         }
        }
        
      }
     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
     ArrayList<String> ipdaterange = new ArrayList<String>();
     ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le:records)
        {
            String str = le.getAccessTime().toString();
            String ipAddr = le.getIpAddress();
            System.out.println("test "+str);
            
            if(str.substring(4,10).contains(someday) && !uniqueIPs.contains(ipAddr))
            {
                ipdaterange.add(le.getIpAddress());
                 uniqueIPs.add(ipAddr);
            }
            
            
       
            
        }
        
        return ipdaterange;
     }
     public int countUniqueIPsInRange(int low, int high)
     {
          ArrayList<String> uniqueIPs = new ArrayList<String>();
          int count = 0 ;
        for (LogEntry le:records)
        {
         int statuscode = le.getStatusCode();
         String ipAddr = le.getIpAddress();
         if ( !uniqueIPs.contains(ipAddr) && statuscode >= low && statuscode <= high)
         {
             uniqueIPs.add(ipAddr);
             count++;
         }
        }
        return count;
     }
     public HashMap<String, Integer> countVisitsPerIP()
     {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (LogEntry le:records)
        {
        String ip = le.getIpAddress();
        if (! counts.containsKey(ip))
        {
          counts.put(ip,1);
        }
        else
        {
            counts.put(ip,counts.get(ip)+1);
        }
        
        }
        return counts;
     }
     public int mostNumberVisitsByIP(HashMap<String, Integer> ip)
     {
         int max = 0;
        for(String s : ip.keySet())
        {
          int count = ip.get(s);
          
          if(count > max)
          {
            max = count;  
          }
        }
        return max;
     }
     public  ArrayList<String> iPsMostVisits(HashMap<String, Integer> ip)
     {
         ArrayList<String> IPs = new ArrayList<String>();
         int max = mostNumberVisitsByIP(ip);
         for(String s : ip.keySet())
        {
          int count = ip.get(s);
          
          if(count == max)
          {
            IPs.add(s); 
          }
        }
        return IPs;
        
     }
     public HashMap<String, ArrayList<String>> iPsForDays()
     {
        
          HashMap<String, ArrayList<String>> datevsip = new HashMap<String, ArrayList<String>>();
        for (LogEntry le:records)
        {
        String str = le.getAccessTime().toString().substring(4,10);
        String ipAddr = le.getIpAddress();
    
            ArrayList<String> IPs = new ArrayList<String>();
            if(!datevsip.containsKey(str))
            {
                
                IPs.add(ipAddr);
                datevsip.put(str,IPs);
                 
            }
            else
            {
                IPs =datevsip.get(str);
                IPs.add(ipAddr);
               datevsip.put(str,IPs);
            }
        
        
    }
        return datevsip;
        
     }
     
     
     public ArrayList<String>  dayWithMostIPVisits(HashMap<String, ArrayList<String>> ip)
     {
          int max = 0;
          ArrayList<String> date = new ArrayList<String>();
        for(String s : ip.keySet())
        {
          int count = ip.get(s).size();
          
          if(count > max)
          {
            max = count;  
          }
        }
        for(String s : ip.keySet())
        {
           
          
          if(ip.get(s).size() == max)
          {
            date.add(s);
          }
        }
        
        return date;
        
        
     }
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> dayvsip ,String day)
     {
         HashMap<String, Integer> countip = new HashMap<String, Integer>();
          ArrayList<String> ips = new ArrayList<String>();
        for (String s : dayvsip.keySet())
        {
           if(s.contains(day))
           {
              ips = dayvsip.get(s);
           }
        }
        for (String s : ips)
        {
          if(!countip.containsKey(s))
          {countip.put(s,1);}
          else
          {
              countip.put(s,countip.get(s)+1);
          }
        }
        ArrayList<String>  IPS = iPsMostVisits(countip);
        return IPS;
     }
}
