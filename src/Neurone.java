
public class Neurone {
	public int nbEntrees;
	public double[] poids;
	public double[] entrees;
	public double sortie;
	private double activation;
	public double gradient;
	double coefSigmo = 2.0;
	
	public Neurone(int nbE){
		nbEntrees = nbE;
		entrees = new double[nbEntrees];
		poids = new double[nbEntrees];
	}
	
	public void setDonneesEntree(double[] donnees){
		int k = 0;
		for(int i=0; i<nbEntrees; i++){
			entrees[i]=donnees[k];
			k++;
		}
	}
	
	public void calculerSortie(){
		activation = 0.0;
		for(int i=0; i<nbEntrees; i++){
			activation += entrees[i]*poids[i];
		}
		double buffer = Math.exp(coefSigmo*activation);
		sortie = (buffer-1)/(buffer+1);
	}
	
	public void initPoids(double intervalle){
		for(int i=0; i<nbEntrees; i++){
			poids[i]=Math.random()*intervalle*2-intervalle;
		}
	}
}
