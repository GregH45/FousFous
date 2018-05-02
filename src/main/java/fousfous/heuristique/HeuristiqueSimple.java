package fousfous.heuristique;

import fr.upsud.PlateauFousFous;
import fr.upsud.Player;

public class HeuristiqueSimple implements Heuristique {
  
  public int getWeight(PlateauFousFous plateau, String joueur) {
	int[][] tmp = plateau.getAllCasesOf(joueur);
	int[][] tmp2 = plateau.getAllCasesOf(joueur == Player.FULL_REPRESENTATION[1] ? Player.FULL_REPRESENTATION[2] : Player.FULL_REPRESENTATION[1]);

    int mesCases = tmp  != null ?  tmp.length : 0;
    int sesCases = tmp2 != null ? tmp2.length : 0;

    return mesCases - sesCases;
  }

}