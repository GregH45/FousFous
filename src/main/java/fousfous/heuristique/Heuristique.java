package fousfous.heuristique;

import fr.upsud.PlateauFousFous;

public interface Heuristique {
  
  public int getWeight(PlateauFousFous plateau, String joueur);

}