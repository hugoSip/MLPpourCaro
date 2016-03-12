
public class Neurone {
	private int nbEntrees;
	private double[] poids;
	private double[] entrees;
	private double sortie;
	private double activation;
	private double gradient;
	
	public Neurone(int nbE, int nbS){
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
		sortie = fonction(activation);
	}
	
	public void setGradient(double g){
		gradient = g;
		
	}
	
	public void initPoids(double intervalle){
		for(int i=0; i<nbEntrees; i++){
			poids[i]=Math.random()*intervalle*2-intervalle;
		}
	}
}
