package araclar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.scheduling.annotation.Scheduled;

public class ZamanlanmisGorevler {
    @Scheduled(cron="0 00 08 * * ? ") // "Saniye Dakika Saat Gün Ay(1-31) haftaninGünü(MON-FRI)" 
//    @Scheduled(fixedDelay = 5000) //Görev Bittikten 5 saniye sonra yeni görev
    //@Scheduled(fixedRate = 5000) //Görev başladıktan 5 saniye sonra yeni görev
    public void zamanla() throws IOException
    {	
    	try{
    		URL myURL = new URL("http://localhost:9086/murakib/buguneokumalariekle");
//    		myURL.openConnection();
    		
    	    BufferedReader in = new BufferedReader(new InputStreamReader(myURL.openStream()));
            /*String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);*/
            in.close();
    	}catch (MalformedURLException e) { 
//           System.out.println("mal"+e);
        } 
        catch (IOException e) {   
//        	System.out.println("io"+e);
        }
//        List memurListesi=null;
//        Sms sms=new Sms();
//        sms.setMemuraGonder(false);
//        Genel.smsGonder(null, sms, new Sube());
    }
}
