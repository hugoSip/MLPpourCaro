
public class Couche {

	private Neurone[] neurones;
	public int nbNeurones;
	private int nbEntrees;
	
	public Couche(int nbNeu, int nbEnt){
		nbNeurones = nbNeu;
		nbEntrees = nbEnt;
		neurones = new Neurone[nbNeurones];
		for(int i = 0; i<nbNeurones; i++){
			Neurone neurone = new Neurone (nbEntrees);
			neurones[i]=neurone;
		}
	}
	
	public void initialisation(double intervalle){
		for(int i = 0; i<nbNeurones; i++){
			neurones[i].initPoids(intervalle);
		}
	}
	
	public void aller(double[] donnees){
		for(int i = 0; i<nbNeurones; i++){
			neurones[i].setDonneesEntree(donnees);
			neurones[i].calculerSortie();
		}
	}
	
	public Neurone getNeurone(int i){
		return neurones[i];
	}
}
