package fousfous.players;

import fousfous.IJoueur;
import fr.upsud.PlateauFousFous;
import fr.upsud.Player;

import java.util.Random;


public class JoueurAleatoire implements IJoueur {

  private PlateauFousFous mPlateau;
  private int mMyColour;
  private String mMyFullRepresentation;
  private String mAdvFullRepresentation;


  public void initJoueur(int mycolour) {
    this.mPlateau = new PlateauFousFous();
    this.mMyColour = mycolour;
    this.mMyFullRepresentation = Player.FULL_REPRESENTATION[this.mMyColour == 1 ? Player.NOIR : Player.BLANC];
    this.mAdvFullRepresentation = Player.FULL_REPRESENTATION[this.mMyColour == 1 ? Player.BLANC : Player.NOIR];
  }


  public int getNumJoueur() {
    return this.mMyColour;
  }


  public String choixMouvement() {
    String[] moves = this.mPlateau.mouvementsPossibles(this.mMyFullRepresentation);
    
    if (moves != null && moves.length > 0) {
      // Random IA
      Random randomGen = new Random();
      int rand = randomGen.nextInt(moves.length);
      String move = moves[rand];

      this.mPlateau.play(move, this.mMyFullRepresentation);

      return move;
    }

    return "PASSE";
  }


  public void declareLeVainqueur(int colour) {
    if (colour == this.mMyColour) {
      System.out.println("Winner!!");
    } else {
      System.out.println("Looser");
    }
  }


  public void mouvementEnnemi(String coup) {
    this.mPlateau.play(coup, this.mAdvFullRepresentation);
  }


  public String binoName() {
    return "Grégoire Harba - Thomas Le Sciellour";
  }

}