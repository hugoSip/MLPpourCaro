
public class Neurone {
	private int nbEntrees;
	private double[] poids;
	private double[] entrees;
	public double sortie;
	private double activation;
	public double gradient;
	
	public Neurone(int nbE){
		nbEntrees = nbE;
		entrees = new double[nbEntrees];
		poids = new double[nbEntrees];
	}
	
	public void setDonneesEntree(int[] donnees){
		int k = 0;
		for(int i=0; i<nbEntrees; i++){
			entrees[i]=donnees[k];
			k++;
		}
		sortie=donnees[k];
		
	}
	
	public void passeAvant(){
		activation = 0.0;
		for(int i=0; i<nbEntrees; i++){
			activation += entrees[i]*poids[i];
		}
		sortie = 1/1+Math.exp(-activation);
	}
	
	public void initPoids(double intervalle){
		for(int i=0; i<nbEntrees; i++){
			poids[i]=Math.random()*intervalle*2-intervalle;
		}
	}
}
