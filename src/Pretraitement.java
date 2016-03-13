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
	
	public void deleteColumn(int del){
		double[][] newDataDouble = new double[nbLine][dataLineLength-1];
		
		for(int i = 0; i < outputDataDouble.length; i++){
			for(int j = 0; j < outputDataDouble[0].length; j++){
				if(j < del){
					newDataDouble[i][j] = outputDataDouble[i][j];
				}
				else if(j > del){
					newDataDouble[i][j-1] = outputDataDouble[i][j];
				}
			}
		}
		dataLineLength--;
		outputDataDouble = new double[nbLine][dataLineLength];
		outputDataDouble = newDataDouble;
	}
	
	public void bordel(){
		for(int i = 0; i < 10*nbLine; i++){
			int l1 = (int)(Math.random()*nbLine);
			int l2 = (int)(Math.random()*nbLine);
			this.swapLine(l1, l2);
		}
	}
	
	public void swapLine(int l1, int l2){
		double[] lineTampon = new double[dataLineLength];
		for(int i = 0; i < outputDataDouble[l1].length; i++){
			lineTampon[i] = outputDataDouble[l1][i];
		}
		for(int i = 0; i < outputDataDouble[l1].length; i++){
			outputDataDouble[l1][i] = outputDataDouble[l2][i];
		}
		for(int i = 0; i < outputDataDouble[l1].length; i++){
			outputDataDouble[l2][i] = lineTampon[i];
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
