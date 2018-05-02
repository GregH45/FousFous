package fousfous.algorithm;

import fousfous.heuristique.Heuristique;
import fr.upsud.PlateauFousFous;


public class MiniMax implements Algorithm {

  private Heuristique mHeuristique;
  private String mMyPlayer;
  private String mEnnemiPlayer;
  private int mDepth = 2;

  public void initPlayers(String myPlayer, String ennemiPlayer) {
    this.mMyPlayer = myPlayer;
    this.mEnnemiPlayer = ennemiPlayer;
  }

  public void setHeuristique(Heuristique heuristique) {
    this.mHeuristique = heuristique;
  }

  public String findTheBestMove(PlateauFousFous plateau) {
    return findTheBestMove(plateau, this.mDepth);
  }

  public String findTheBestMove(PlateauFousFous plateau, int depth) {
    String[] moves = plateau.mouvementsPossibles(this.mMyPlayer);
    String res = null;
    int max = Integer.MIN_VALUE;
    int min;

    if (null != moves) {
      for(String move : moves) {
        PlateauFousFous copy = PlateauFousFous.copy(plateau);

        min = minMax(copy, depth);

        if (min > 8000) {
          return move;
        }
        else if (min > max) {
          max = min;
          res = move;
        }
      }
    }
  
    return res;
  }

  private int minMax(PlateauFousFous plateau, int depth) {
	  if(plateau.finDePartie() || depth == 0) {
	    return this.mHeuristique.getWeight(plateau, this.mEnnemiPlayer);
	  } else {
	    String[] moves =  plateau.mouvementsPossibles(this.mMyPlayer);
      int min = Integer.MAX_VALUE;

	    if (moves != null) {
  			for(String move : moves) {
			    PlateauFousFous copy = PlateauFousFous.copy(plateau);

			    copy.play(move, mMyPlayer);
				  min = Math.min(min, maxMin(copy, depth-1));
  			}
	    }

		  return min;
	  }
  }
  
  private int maxMin(PlateauFousFous plateau, int depth) {
		if (plateau.finDePartie() || depth == 0) {
			return this.mHeuristique.getWeight(plateau, this.mEnnemiPlayer);
		} else {
			String[] moves = plateau.mouvementsPossibles(this.mEnnemiPlayer);
      int max = Integer.MIN_VALUE;

			if (moves != null) {
				for(String move : moves) {
					PlateauFousFous copy = PlateauFousFous.copy(plateau);

					copy.play(move, mEnnemiPlayer);
					
					max = Math.max(max, minMax(copy, depth-1));
				}
			}

			return max;
		}
	}
  

}