package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import businesslogic.Landmark;
import facade.Facade;

public class LandmarkPrediction implements Runnable {

    private final InputStream inputStream;

    public LandmarkPrediction(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private BufferedReader getBufferedReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public void run() {
        BufferedReader br = getBufferedReader(inputStream);
        BufferedReader stdInput = getBufferedReader(inputStream);
        String line = null;


		try {
			while ((line = stdInput.readLine()) != null) {
			
				String[] tab = line.split(" ");
				Boolean b;
				if(tab[2] == "true"){
					b = true;
				}
				else
				{
					b = false;
				}
				float t =  Float.parseFloat(tab[1]);

				Landmark l = new Landmark(Float.parseFloat(tab[0]), Cadre2.imHEIGHT - t, b);
			
				Affichage.ListLandmark.add(l);
			   
			        System.out.println(line);
			    
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
