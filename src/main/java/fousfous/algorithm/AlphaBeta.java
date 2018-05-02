package fousfous.algorithm;

import fousfous.heuristique.Heuristique;
import fr.upsud.PlateauFousFous;


public class AlphaBeta implements Algorithm {

  private Heuristique mHeuristique;
  private String mMyPlayer;
  private String mEnnemiPlayer;
  private int mDepth = 1000;

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
    
    if (moves != null) {
      String res = null;
      int a = Integer.MIN_VALUE;
      int b = Integer.MAX_VALUE;

      for (String coup : moves) {
        PlateauFousFous copy = PlateauFousFous.copy(plateau);

        copy.play(coup, this.mMyPlayer);

        int max = maxMin(copy, depth, a, b);

        if (a < max) {
          b = max;
          res = coup;
        }
      }

      return res;
    }

    return null;
  }
  
  private int maxMin(PlateauFousFous plateau, int depth, int a, int b) {
		if (plateau.finDePartie() || depth == 0) {
			return this.mHeuristique.getWeight(plateau, this.mEnnemiPlayer);
		} else {
      String[] moves = plateau.mouvementsPossibles(this.mEnnemiPlayer);

      if (moves != null) {
  			for (String move : moves) {
  				PlateauFousFous copy = PlateauFousFous.copy(plateau);

  				copy.play(move, this.mEnnemiPlayer);

  				a = Math.max(a, minMax(copy, depth-1, a, b));

  				if (a >= b) {
  					return b;
  				}
  			}
      }

			return a;
		}
	}
	
	private int minMax(PlateauFousFous plateau, int depth, int a, int b) {
		if (plateau.finDePartie() || depth == 0) {
			return this.mHeuristique.getWeight(plateau, this.mMyPlayer);
		} else {
      String[] moves = plateau.mouvementsPossibles(this.mMyPlayer);

      if (moves != null) {
  			for (String move : moves) {
  				PlateauFousFous copy = PlateauFousFous.copy(plateau);

  				copy.play(move, this.mMyPlayer);

  				b = Math.min(b, maxMin(copy, depth-1, a, b));

  				if (a >= b) {
  					return a;
  				}
  			}
      }

			return b;
		}
	}

}