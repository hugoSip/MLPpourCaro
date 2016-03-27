import java.util.ArrayList;
import java.util.List;

public class Reseau {

	public Couche[] couches;
	private int nbCouches;
	private int nbSorties;
	private int nbEntrees;
	private int nbExemples;
	private int nbNeuronesParCoucheCachee;
	private double[][] entrees;
	private double[][] sorties;
	double coefDerivee = 0.3;
	double coefApprentissage = 0.1;
	
	public Reseau (int nbCouche, int nbNeuronesParCouche, double[][] entre, double[][] sort){
		nbCouches = nbCouche-1;
		couches =  new Couche[nbCouches];
		nbNeuronesParCoucheCachee = nbNeuronesParCouche;
		nbEntrees = entre[0].length;
		nbExemples = entre.length;
		nbSorties = sort[0].length;
		entrees = entre;
		sorties = sort;
		remplirCouches();
	}
	
	public void apprendre(){
		for(int i =0; i<nbExemples; i++){
			aller(entrees[i]);
			calculerGradientErreurSortie(i);
			retour();
			miseAJourPoids();
		}
	}
	
	public int [] trouverClasse(double [] exemple){
		aller(exemple);
		int sortie[] = new int[nbSorties];
		int k = -1;
		double max = -2.0;
		for(int i =0; i< nbSorties; i++){
			if(couches[nbCouches-1].getNeurone(i).sortie>max){
				max = couches[nbCouches-1].getNeurone(i).sortie;
				k = i;
			}
		}
		for(int i =0; i< nbSorties; i++){
			sortie[i]=-1;
		}
		sortie[k]=1;
		return sortie;
	}
	
	public double [] trouverClasse2(double [] exemple){
		aller(exemple);
		double sortie[] = new double[nbSorties];
		for(int i =0; i< nbSorties; i++){
			sortie[i]=couches[nbCouches-1].getNeurone(i).sortie;
			}
		return sortie;
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
			couches[i].initialisation(0.3);
		}
	}
	
	public void calculerGradientErreurSortie(int idExemple){
		double deriveeSigmoide;
		for(int i=0; i<nbSorties; i++){
			Neurone neurone = couches[nbCouches-1].getNeurone(i);
			deriveeSigmoide = deriveeSigmoide(neurone.sortie);
			neurone.gradient = 2 * deriveeSigmoide * (sorties[idExemple][i]-neurone.sortie);
		}
	}
	
	public double deriveeSigmoide(double fa){
		//return (coefDerivee * (1.0+fa) * (1.0-fa));
		return (Math.exp(-fa)/Math.pow((1.0+Math.exp(-fa)),2.0));
	}
	
	public void aller(double[] exemple){
		int nbEntree;
		couches[0].aller(exemple);
		for(int i = 1;i<nbCouches-1;i++){
			nbEntree = couches[i-1].nbNeurones;
			double[] entree = new double[nbEntree];
			for(int j = 0; j<nbEntree; j++){
				entree[j]=couches[i-1].getNeurone(j).sortie;
			}
			couches[i].aller(entree);
		}
		
		double[] entree = new double[couches[nbCouches-2].nbNeurones];
		for(int i = 0; i<couches[nbCouches-2].nbNeurones; i++){
			entree[i]=couches[nbCouches-2].getNeurone(i).sortie;
		}
		for(int i = 0; i<couches[nbCouches-1].nbNeurones; i++){
			couches[nbCouches-1].getNeurone(i).setDonneesEntree(entree);
			couches[nbCouches-1].getNeurone(i).activation = 0.0;
			for(int k = 0; k<entree.length; k++){
				couches[nbCouches-1].getNeurone(i).activation += couches[nbCouches-1].getNeurone(i).entrees[k]*couches[nbCouches-1].getNeurone(i).poids[k];
			}
			couches[nbCouches-1].getNeurone(i).sortie=couches[nbCouches-1].getNeurone(i).activation;
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
