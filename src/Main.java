
public class Main {
	
	static int nbNeuronesCoucheCachee = 5;
	static int nbCouchesCachees = 3;
	static int nbIterations = 40;
	static double initPoids = 0.3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Pretraitement pr = new Pretraitement("data/glass.data");
		//Pretraitement pr = new Pretraitement("data/gl.gen");
		//=> étudier le fichier .gen pour reproduire le comportement;
		pr.bordel();
		pr.calculerEntreesSorties();
		pr.supprimerColonne(0);
		pr.normaliser();
		double[][] data = pr.getDataTable();
		double[][] sorties = pr.getSorties();
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				System.out.print(data[i][j] + " | ");
			}
			System.out.println("");
		}
		System.out.println("");
		Reseau reseau = new Reseau(nbCouchesCachees,nbNeuronesCoucheCachee,data,sorties);
		for(int i =0; i<nbIterations; i++){
			reseau.apprendre();
		}
		System.out.println("apprentissage fini");
		//double[]sortie = reseau.trouverClasse(data[data.length-1]);
		int[]sortie;
		double[]sortieDesiree;
		for(int i =5; i>0; i--){
			sortie = reseau.trouverClasse(data[data.length-i]);
			sortieDesiree = sorties[sorties.length-i];
			System.out.print("Désiré : ");
			for(int j = 0; j < sortieDesiree.length; j++){
				System.out.print((int)sortieDesiree[j] + " | ");
			}
			System.out.println("");
			System.out.print("Trouvé : ");
			for(int j = 0; j < sortie.length; j++){
				System.out.print(sortie[j] + " | ");
			}
			System.out.println("");
			System.out.println("");
		}
	}

}
