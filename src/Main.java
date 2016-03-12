
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Pretraitement pr = new Pretraitement("data/glass.data");
		double[][] data = pr.getDataTable();
		
		for(int i = 0; i < data.length; i++){
			for(int j = 0; j < data[0].length; j++){
				System.out.print(data[i][j] + " | ");
			}
			System.out.println("");
		}
	}

}
