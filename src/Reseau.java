
public class Reseau {

	private Couche[] couches;
	private int nbCouches;
	
	public Reseau (int nbCou, int nbNeuroneParCouche, int nbEnt, int nbSor){
		nbCouches = nbCou;
		couches =  new Couche[nbCou];
		int nbNeurones = nbNeuroneParCouche;
		int nbEntrees = nbEnt;
		for(int i = 0; i<nbCouches; i++){
			if(i == nbCouches - 1){
				nbNeurones = nbSor;
			}
			Couche couche = new Couche(nbNeurones, nbEntrees);
			couches[i]= couche;
			nbEntrees = nbNeurones;
		}
	}
	
	
	
	
	
	void gradsor(int numex)  //calcul des gradients d'erreur, sur la couche de sortie
	{
	  int i;
	  double dsig;  //derivee de la sigmoide

	  for (i=0;i<dimsor;i++)
	    { dsig=deriv(res.couche[res.nbcou-1].neurone[i].s);
	      res.couche[res.nbcou-1].neurone[i].y=2*dsig*(app.sd[numex][i]-res.couche[res.nbcou-1].neurone[i].s);
	    }
	}

	void retour()  //passe arriere : calcul des gradients retropropages, dans les couches cachees
	{
	  int i,m,k;
	  double dsig;
	  double somm;

	  for (k=res.nbcou-2;k>0;k--)
	    for (i=0;i<res.couche[k].nbneu;i++)
	      { dsig=deriv(res.couche[k].neurone[i].s);
		somm=0.0;
		for(m=0;m<res.couche[k+1].nbneu;m++)
		  somm+=res.couche[k+1].neurone[m].w[i]*res.couche[k+1].neurone[m].y;
		res.couche[k].neurone[i].y=dsig*somm;
	      }
	}

	void modifw()  //mise a jour des poids du reseau (toutes les couches)
	{
	  int i,j,k;

	  for (k=1;k<res.nbcou;k++)
	    for (i=0;i<res.couche[k].nbneu;i++)
	      for (j=0;j<res.couche[k].neurone[i].nbent;j++)
		res.couche[k].neurone[i].w[j]+=alpha*res.couche[k].neurone[i].y*res.couche[k].neurone[i].x[j];
	}
}
