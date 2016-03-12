import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Pretraitement {
	
	private int dataLineLength;
    private double[][] outputDataDouble;
    private int nbLine;

	public Pretraitement(String file){
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    nbLine = 0;
		    while ((line = br.readLine()) != null) {
		    	nbLine++;
		    }
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

		    String line;
		    String[] outputDataString;
		    int i = 0;
		    while ((line = br.readLine()) != null) {
		    	if(i == 0){
		    		dataLineLength = line.split(",").length;
		    		outputDataString = new String[dataLineLength];
		    		outputDataDouble = new double[nbLine][dataLineLength];
		    	}
		    	outputDataString = line.split(",");
		    	for(int j = 0; j < outputDataString.length; j++){
		    		outputDataDouble[i][j] = Double.parseDouble(outputDataString[j]);
		    	}
		    	i++;
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public double[][] getDataTable(){
		return outputDataDouble;
	}
	
	public int getDataLength(){
		return nbLine;
	}
	
	public int getDataLineLength(){
		return dataLineLength;
	}
}
