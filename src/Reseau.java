import java.util.ArrayList;
import java.util.List;

public class Reseau {

	private Couche[] couches;
	private int nbCouches;
	private int nbSorties;
	private int nbEntrees;
	private int nbExemples;
	private int nbNeuronesParCoucheCachee;
	private double[][] entrees;
	private double[] sorties;
	double coefDerivee = 1.0;
	double coefApprentissage = 0.1;
	
	public Reseau (int nbCouche, int nbNeuronesParCouche, double[][] donnees){
		nbCouches = nbCouche-1;
		couches =  new Couche[nbCouches];
		nbNeuronesParCoucheCachee = nbNeuronesParCouche;
		nbEntrees = donnees[0].length-1;
		nbExemples = donnees.length;
		entrees = new double[nbExemples][nbEntrees];
		sorties = new double[nbExemples];
		diviserEntreesSorties(donnees);
		trouverNbSorties();
		remplirCouches();
	}
	
	public void trouverNbSorties(){
		nbSorties = 0;
		List <Double>listeSortie = new ArrayList<Double>();
		for(int i = 0; i<nbExemples; i++){
			if(!listeSortie.contains(sorties[i])){
				listeSortie.add(sorties[i]);
				nbSorties++;
			}
		}
	}
	
	public void apprendre(){
		for(int i =0; i<nbExemples; i++){
			aller(entrees[i]);
			calculerGradientErreurSortie(i);
			retour();
			miseAJourPoids();
		}
	}
	
	public double [] trouverClasse(double [] exemple){
		aller(exemple);
		double sortie[] = new double[nbSorties];
		for(int i =0; i< nbSorties; i++){
			sortie[i]=couches[nbCouches-1].getNeurone(i).sortie;
		}
		return sortie;
	}
	
	public void diviserEntreesSorties(double[][] donnees){
		for(int i =0; i<nbExemples;i++){
			for(int j=0; j<nbEntrees; j++){
				entrees[i][j]=donnees[i][j];
			}
			sorties[i]=donnees[i][nbEntrees];
		}
	}
	
	public void remplirCouches(){
		int nbEntree=nbEntrees;
		for(int i = 0; i<nbCouches; i++){
			if(i == nbCouches - 1){
				nbNeuronesParCoucheCachee = nbSorties;
			}
			Couche couche = new Couche(nbNeuronesParCoucheCachee, nbEntree);
			couches[i]= couche;
			nbEntree = nbNeuronesParCoucheCachee;
		}
	}
	
	public void calculerGradientErreurSortie(int idExemple){
		double deriveeSigmoide;
		for(int i=0; i<nbSorties; i++){
			Neurone neurone = couches[nbCouches-1].getNeurone(i);
			deriveeSigmoide = deriveeSigmoide(neurone.sortie);
			neurone.gradient = 2 * deriveeSigmoide * (sorties[idExemple]-neurone.sortie);
		}
	}
	
	public double deriveeSigmoide(double fa){
		return (coefDerivee * (1.0+fa) * (1.0-fa));
	}
	
	public void aller(double[] exemple){
		int nbEntree;
		couches[0].aller(exemple);
		for(int i = 1;i<nbCouches;i++){
			nbEntree = couches[i-1].nbNeurones;
			double[] entree = new double[nbEntree];
			for(int j = 0; j<nbEntree; j++){
				entree[j]=couches[i-1].getNeurone(j).sortie;
			}
			couches[i].aller(entree);
		}
	}
	
	public void retour(){
		double deriveeSigmoide;
		double somme;
		for(int i = nbCouches-2; i>=0;i--){
			for(int j =0; j< couches[i].nbNeurones;j++){
				deriveeSigmoide = deriveeSigmoide(couches[i].getNeurone(j).sortie);
				somme = 0.0;
				for(int k = 0; k<couches[i+1].nbNeurones; k++){
					somme += couches[i+1].getNeurone(k).poids[j]*couches[i+1].getNeurone(k).gradient;
				}
				couches[i].getNeurone(j).gradient = deriveeSigmoide*somme;
			}
		}
	}
	
	public void miseAJourPoids(){
		for(int i = 0; i<nbCouches; i++){
			for(int j = 0; j<couches[i].nbNeurones; j++){
				for(int k = 0; k<couches[i].getNeurone(j).nbEntrees; k++){
					Neurone neurone = couches[i].getNeurone(j);
					neurone.poids[k]+=coefApprentissage*neurone.gradient*neurone.entrees[k];
				}
			}
		}
	}
}
