
public class Couche {

	private Neurone[] neurones;
	private int nbNeurones;
	
	public Couche(int nbNeu, int nbEntrees){
		nbNeurones = nbNeu;
		neurones = new Neurone[nbNeurones];
		for(int i = 0; i<nbNeurones; i++){
			Neurone neurone = new Neurone (nbEntrees);
			neurones[i]=neurone;
		}
	}
}
