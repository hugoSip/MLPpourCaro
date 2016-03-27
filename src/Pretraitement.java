import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pretraitement {
	
	private int dataLineLength;
    private double[][] inputDataDouble;
    private double[][] sorties;
    private int nbExemples;

	public Pretraitement(String file){
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    nbExemples = 0;
		    while ((line = br.readLine()) != null) {
		    	nbExemples++;
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
		    		inputDataDouble = new double[nbExemples][dataLineLength];
		    	}
		    	outputDataString = line.split(",");
		    	for(int j = 0; j < outputDataString.length; j++){
		    		inputDataDouble[i][j] = Double.parseDouble(outputDataString[j]);
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
	
	public double[][] getData(){
		return this.inputDataDouble;
	}
	
	public double[][] getSorties(){
		return this.sorties;
	}
	
	public void calculerEntreesSorties(){
		int nbSorties = 0;
		List <Double>listeSortie = new ArrayList<Double>();
		for(int i = 0; i<nbExemples; i++){
			if(!listeSortie.contains(inputDataDouble[i][inputDataDouble[0].length-1])){
				listeSortie.add(inputDataDouble[i][inputDataDouble[0].length-1]);
				nbSorties++;
			}
		}
		sorties = new double[nbExemples][nbSorties];
		for(int i = 0; i<nbExemples; i++){
			for(int j = 0; j<nbSorties; j++){
				sorties[i][j] = -1.0;
			}
			for(int k = 0; k < nbSorties; k++){
				if(listeSortie.get(k) == inputDataDouble[i][inputDataDouble[0].length-1]){
					sorties[i][k] = 1.0;
				}
			}
		}
		supprimerColonne(inputDataDouble[0].length - 1);
	}
	
	public void supprimerColonne(int del){
		double[][] newDataDouble = new double[nbExemples][dataLineLength-1];
		
		for(int i = 0; i < inputDataDouble.length; i++){
			for(int j = 0; j < inputDataDouble[0].length; j++){
				if(j < del){
					newDataDouble[i][j] = inputDataDouble[i][j];
				}
				else if(j > del){
					newDataDouble[i][j-1] = inputDataDouble[i][j];
				}
			}
		}
		dataLineLength--;
		inputDataDouble = new double[nbExemples][dataLineLength];
		inputDataDouble = newDataDouble;
	}
	
	public void bordel(){
		for(int i = 0; i < 10*nbExemples; i++){
			int l1 = (int)(Math.random()*nbExemples);
			int l2 = (int)(Math.random()*nbExemples);
			this.swapLine(l1, l2);
		}
	}
	
	public void swapLine(int l1, int l2){
		double[] lineTampon = new double[dataLineLength];
		for(int i = 0; i < inputDataDouble[l1].length; i++){
			lineTampon[i] = inputDataDouble[l1][i];
		}
		for(int i = 0; i < inputDataDouble[l1].length; i++){
			inputDataDouble[l1][i] = inputDataDouble[l2][i];
		}
		for(int i = 0; i < inputDataDouble[l1].length; i++){
			inputDataDouble[l2][i] = lineTampon[i];
		}
	}

	public double[][] getDataTable(){
		return inputDataDouble;
	}
	
	public int getNbExemples(){
		return nbExemples;
	}
	
	public int getNbEntrees(){
		return inputDataDouble[0].length;
	}
	
	@Deprecated
	public void centrerReduire(){
		for(int i = 0; i<inputDataDouble[0].length; i++){
			double moyenne;
			double ecartType;
			double total = 0.0;
			double totalDesCarres = 0.0;
			double moyenneDesCarres;
			for(int j = 0; j<inputDataDouble.length; j++){
				total+=inputDataDouble[j][i];
				totalDesCarres+=Math.pow(inputDataDouble[j][i], 2.0);
			}
			moyenne = total/((double)(nbExemples));
			moyenneDesCarres = totalDesCarres / ((double)(nbExemples));
			ecartType = Math.sqrt(moyenneDesCarres - Math.pow(moyenne, 2.0));
			for(int j = 0; j<inputDataDouble.length; j++){
				inputDataDouble[j][i] = (inputDataDouble[j][i] - moyenne)/ecartType;
			}
		}
	}
	
	public void normaliser(){
		for(int i = 0; i<inputDataDouble[0].length; i++){
			double min = 10000000.0;
			double max = -10000000.0;
			for(int j = 0; j<inputDataDouble.length; j++){
				if(inputDataDouble[j][i]<min){
					min = inputDataDouble[j][i];
				}
				if(inputDataDouble[j][i]>max){
					max = inputDataDouble[j][i];
				}
			}
			for(int j = 0; j<inputDataDouble.length; j++){
				inputDataDouble[j][i] = 2.0 * ((inputDataDouble[j][i]-min)/(max-min)) -1.0;
			}
		}
	}
}
