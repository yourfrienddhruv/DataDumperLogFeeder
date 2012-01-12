
//import java.io.PipedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;

import java.io.BufferedReader;
import org.apache.log4j.Logger;

public class LineFeeder {

	/** @Author Dhruv Gohil. Tested on SOLARIS-10. dhruv.gohil@elitecore.com & yourfrienddhruv(at) g y h r.com
         *  @TODO TECHINCAL : Memory Leak Test, Memory consumption pattern
         *        FUNCTIONAL : Log rollover Test
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException,InterruptedException {
		// TODO Auto-generated method stub
		String processErrorLoggerCategroyName="processErrorLogger";
		String processOutputLoggerCategroyName="processOutputLogger";
		String lineFeederSelfLoggerCategroyName="lineFeederSelfLogger";
		
			System.out.println("	Finding Logger with processErrorLogger:" + processErrorLoggerCategroyName);		
			Logger processErrorLogger =  Logger.getLogger(processErrorLoggerCategroyName);
			System.out.println("	Found processErrorLogger Log LEVEL	  :" + processErrorLogger.getLevel());
			
			System.out.println("	Finding Logger with processOutputLogger:" + processOutputLoggerCategroyName);		
			Logger processOutputLogger =  Logger.getLogger(processOutputLoggerCategroyName);
			System.out.println("	Found processOutputLogger Log LEVEL	   :" + processOutputLogger.getLevel());
			
			System.out.println("	Finding Logger with lineFeederSelfLogger:" + lineFeederSelfLoggerCategroyName);		
			Logger lineFeederSelfLogger =  Logger.getLogger(lineFeederSelfLoggerCategroyName);
			System.out.println("	Found lineFeederSelfLogger Log LEVEL	:" + lineFeederSelfLogger.getLevel());
		
			//logger.info("Auth Response for Username=\"25218961@mtnl\"  NAS-Identifier=\"HW-JKP-PJKP-BASA\"  NAS-Port-Id=\"slot=2;subslot=1;port=0;vlanid=1218;vlanid2=2685;\"  : ACCESS_REJECT  [ REASON : Account is not active ]"+i);
		if(args.length < 1){
			System.out.println("USAGE   : java " + LineFeeder.class.getName() + " <OS script of whose output needs to be pipelined to log>");
			System.out.println("Example : java " + LineFeeder.class.getName() + " ./myScript.sh");
			System.exit(-1);
		}
		//String[] cmd = {"/bin/bash","-c", args[0]};
		String[] cmd = {args[0]};
		lineFeederSelfLogger.info("	PipeLining output of Process :" + cmd);	
		Process proc = Runtime.getRuntime().exec(cmd);
		lineFeederSelfLogger.info("	Started Processes as :"+ proc );
		lineFeederSelfLogger.info("	Outputting lines to Log LEVEL: INFO");
		
		// any error message?
        StreamGobbler errorGobbler = new  StreamGobbler(proc.getErrorStream(), "ERROR",processErrorLogger);            
        
        // any output?
        StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT",processOutputLogger);
            
        // kick them off
        errorGobbler.start();
        outputGobbler.start();
                                
        // any error???
        int exitVal = proc.waitFor();
        lineFeederSelfLogger.warn("Process ExitValue: " + exitVal + "Exisitng The Main Programm.");
	}

}

class StreamGobbler extends Thread{
    InputStream is;
    String type;
    //OutputStream os;
    Logger outputlogger;   
    StreamGobbler(InputStream is, String type, Logger logger)    {
        this.is = is;
        this.type = type;
        this.outputlogger = logger;
    }
    
    public void run()    {
        try{     
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for(String line=null;(line = br.readLine()) != null;){
                outputlogger.info(line);
                // System.out.println(type + ">" + line);    
            }
        } catch (IOException ioe){
           ioe.printStackTrace();  
        }
    }
}
