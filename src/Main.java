
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Pretraitement pr = new Pretraitement("data/glass.data");
		pr.bordel();
		double[][] data = pr.getDataTable();
		
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				System.out.print(data[i][j] + " | ");
			}
			System.out.println("");
		}
		System.out.println("");
		Reseau reseau = new Reseau(3,10,data);
		for(int i =0; i<2000; i++){
			reseau.apprendre();
		}
		double[]sortie = reseau.trouverClasse(data[data.length-1]);
		for(int i = 0; i < sortie.length; i++){
			System.out.print(sortie[i] + " | ");
		}
	}

}
