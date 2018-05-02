package fousfous.algorithm;

import fousfous.heuristique.Heuristique;
import fr.upsud.PlateauFousFous;


public class IterativeDeepening implements Algorithm {

  private Heuristique mHeuristique;
  private String mMyPlayer;
  private String mEnnemiPlayer;

  public void initPlayers(String myPlayer, String ennemiPlayer) {
    this.mMyPlayer = myPlayer;
    this.mEnnemiPlayer = ennemiPlayer;
  }

  public void setHeuristique(Heuristique heuristique) {
    this.mHeuristique = heuristique;
  }

  public String findTheBestMove(PlateauFousFous plateau) {
    return null;
  }

  public String findTheBestMove(PlateauFousFous plateau, int depth) {
    return null;
  }

}