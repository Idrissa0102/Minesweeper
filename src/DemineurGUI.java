import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DemineurGUI extends JPanel {

    private final int NOMBRE_IMAGES = 13;
    private final int TAILLE_CASE = 50;

    private final int MASQUE_POUR_CASE = 10;
    private final int MARQUE_POUR_CASE = 10;
    private final int CASE_VIDE = 0;
    private final int CASE_MINE = 9;
    private final int CASE_MINE_MASQUE = CASE_MINE + MASQUE_POUR_CASE;
    private final int CASE_MINE_MARQUE = CASE_MINE_MASQUE + MARQUE_POUR_CASE;

    private final int DESSIN_MINE = 9;
    private final int DESSIN_MASQUE = 10;
    private final int DESSIN_MARQUE = 11;
    private final int DESSIN_MARQUE_ERRONE = 12;
    
    /*Cette partie sert à configurer le jeu*/
    private final int NBRE_MINES = 7; 
    private final int NBRE_LIGNES = 12;
    private final int NBRE_COLONNES = 12;
    /***************************************/

    private final int LONGUEUR_PLATEAU_DEMINEUR = NBRE_COLONNES * TAILLE_CASE + 1;
    private final int LARGEUR_PLATEAU_DEMINEUR = NBRE_LIGNES * TAILLE_CASE + 1;

    private int[] zone;
    private boolean partieEnCours;
    private int minesRestantes;
    private Image[] image;

    private int allCases;
    private final JLabel barreDeStatus;

    public DemineurGUI(JLabel barreDeStatus) {

        this.barreDeStatus = barreDeStatus;
        initialiserPlateau();
    }

    private void initialiserPlateau() {

        setPreferredSize(new Dimension(LONGUEUR_PLATEAU_DEMINEUR, LARGEUR_PLATEAU_DEMINEUR));

        image = new Image[NOMBRE_IMAGES];

        for (int i = 0; i < NOMBRE_IMAGES; i++) {

            var pathImage = "src/img-src/" + i + ".png";
            image[i] = (new ImageIcon(pathImage)).getImage();
        }

        addMouseListener(new MinesAdapter());
        nouvellePartie();
    }

    private void nouvellePartie() {

        int casePlateau;

        var random = new Random();
        partieEnCours = true;
        minesRestantes = NBRE_MINES;

        allCases = NBRE_LIGNES * NBRE_COLONNES;
        zone = new int[allCases];

        for (int i = 0; i < allCases; i++) {

            zone[i] = MASQUE_POUR_CASE;
        }

        barreDeStatus.setText("Nombre de mines: "+Integer.toString(minesRestantes));

        int i = 0;

        while (i < NBRE_MINES) {

            int position = (int) (allCases * random.nextDouble());

            if ((position < allCases)
                    && (zone[position] != CASE_MINE_MASQUE)) {

                int actuelle_colonne = position % NBRE_COLONNES;
                zone[position] = CASE_MINE_MASQUE;
                i++;

                if (actuelle_colonne > 0) {
                    casePlateau = position - 1 - NBRE_COLONNES;
                    if (casePlateau >= 0) {
                        if (zone[casePlateau] != CASE_MINE_MASQUE) {
                            zone[casePlateau] += 1;
                        }
                    }
                    casePlateau = position - 1;
                    if (casePlateau >= 0) {
                        if (zone[casePlateau] != CASE_MINE_MASQUE) {
                            zone[casePlateau] += 1;
                        }
                    }

                    casePlateau = position + NBRE_COLONNES - 1;
                    if (casePlateau < allCases) {
                        if (zone[casePlateau] != CASE_MINE_MASQUE) {
                            zone[casePlateau] += 1;
                        }
                    }
                }

                casePlateau = position - NBRE_COLONNES;
                if (casePlateau >= 0) {
                    if (zone[casePlateau] != CASE_MINE_MASQUE) {
                        zone[casePlateau] += 1;
                    }
                }

                casePlateau = position + NBRE_COLONNES;
                if (casePlateau < allCases) {
                    if (zone[casePlateau] != CASE_MINE_MASQUE) {
                        zone[casePlateau] += 1;
                    }
                }

                if (actuelle_colonne < (NBRE_COLONNES - 1)) {
                    casePlateau = position - NBRE_COLONNES + 1;
                    if (casePlateau >= 0) {
                        if (zone[casePlateau] != CASE_MINE_MASQUE) {
                            zone[casePlateau] += 1;
                        }
                    }
                    casePlateau = position + NBRE_COLONNES + 1;
                    if (casePlateau < allCases) {
                        if (zone[casePlateau] != CASE_MINE_MASQUE) {
                            zone[casePlateau] += 1;
                        }
                    }
                    casePlateau = position + 1;
                    if (casePlateau < allCases) {
                        if (zone[casePlateau] != CASE_MINE_MASQUE) {
                            zone[casePlateau] += 1;
                        }
                    }
                }
            }
        }
    }

    private void chercherCasesVides(int j) {

        int actuelle_colonne = j % NBRE_COLONNES;
        int casePlateau;

        if (actuelle_colonne > 0) {
            casePlateau = j - NBRE_COLONNES - 1;
            if (casePlateau >= 0) {
                if (zone[casePlateau] > CASE_MINE) {
                    zone[casePlateau] -= MASQUE_POUR_CASE;
                    if (zone[casePlateau] == CASE_VIDE) {
                        chercherCasesVides(casePlateau);
                    }
                }
            }

            casePlateau = j - 1;
            if (casePlateau >= 0) {
                if (zone[casePlateau] > CASE_MINE) {
                    zone[casePlateau] -= MASQUE_POUR_CASE;
                    if (zone[casePlateau] == CASE_VIDE) {
                        chercherCasesVides(casePlateau);
                    }
                }
            }

            casePlateau = j + NBRE_COLONNES - 1;
            if (casePlateau < allCases) {
                if (zone[casePlateau] > CASE_MINE) {
                    zone[casePlateau] -= MASQUE_POUR_CASE;
                    if (zone[casePlateau] == CASE_VIDE) {
                        chercherCasesVides(casePlateau);
                    }
                }
            }
        }

        casePlateau = j - NBRE_COLONNES;
        if (casePlateau >= 0) {
            if (zone[casePlateau] > CASE_MINE) {
                zone[casePlateau] -= MASQUE_POUR_CASE;
                if (zone[casePlateau] == CASE_VIDE) {
                    chercherCasesVides(casePlateau);
                }
            }
        }

        casePlateau = j + NBRE_COLONNES;
        if (casePlateau < allCases) {
            if (zone[casePlateau] > CASE_MINE) {
                zone[casePlateau] -= MASQUE_POUR_CASE;
                if (zone[casePlateau] == CASE_VIDE) {
                    chercherCasesVides(casePlateau);
                }
            }
        }

        if (actuelle_colonne < (NBRE_COLONNES - 1)) {
            casePlateau = j - NBRE_COLONNES + 1;
            if (casePlateau >= 0) {
                if (zone[casePlateau] > CASE_MINE) {
                    zone[casePlateau] -= MASQUE_POUR_CASE;
                    if (zone[casePlateau] == CASE_VIDE) {
                        chercherCasesVides(casePlateau);
                    }
                }
            }

            casePlateau = j + NBRE_COLONNES + 1;
            if (casePlateau < allCases) {
                if (zone[casePlateau] > CASE_MINE) {
                    zone[casePlateau] -= MASQUE_POUR_CASE;
                    if (zone[casePlateau] == CASE_VIDE) {
                        chercherCasesVides(casePlateau);
                    }
                }
            }

            casePlateau = j + 1;
            if (casePlateau < allCases) {
                if (zone[casePlateau] > CASE_MINE) {
                    zone[casePlateau] -= MASQUE_POUR_CASE;
                    if (zone[casePlateau] == CASE_VIDE) {
                        chercherCasesVides(casePlateau);
                    }
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {

        int nbreCaseNonMasquer = 0;

        for (int i = 0; i < NBRE_LIGNES; i++) {

            for (int j = 0; j < NBRE_COLONNES; j++) {

                int casePlateau = zone[(i * NBRE_COLONNES) + j];

                if (partieEnCours && casePlateau == CASE_MINE) {

                    partieEnCours = false;
                }

                if (!partieEnCours) {

                    if (casePlateau == CASE_MINE_MASQUE) {
                        casePlateau = DESSIN_MINE;
                    } else if (casePlateau == CASE_MINE_MARQUE) {
                        casePlateau = DESSIN_MARQUE;
                    } else if (casePlateau > CASE_MINE_MASQUE) {
                        casePlateau = DESSIN_MARQUE_ERRONE;
                    } else if (casePlateau > CASE_MINE) {
                        casePlateau = DESSIN_MASQUE;
                    }

                } else {

                    if (casePlateau > CASE_MINE_MASQUE) {
                        casePlateau = DESSIN_MARQUE;
                    } else if (casePlateau > CASE_MINE) {
                        casePlateau = DESSIN_MASQUE;
                        nbreCaseNonMasquer++;
                    }
                }

                g.drawImage(image[casePlateau], (j * TAILLE_CASE),
                        (i * TAILLE_CASE), this);
            }
        }

        if (nbreCaseNonMasquer == 0 && partieEnCours) {

            partieEnCours = false;
            barreDeStatus.setText("Gagné 🙂🙂🙂 !!!");

        } else if (!partieEnCours) {
            barreDeStatus.setText("Perdu ☹☹☹!!!");
        }
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cColonne = x / TAILLE_CASE;
            int cLigne = y / TAILLE_CASE;

            boolean faireRepaint = false;

            if (!partieEnCours) {

                nouvellePartie();
                repaint();
            }

            if ((x < NBRE_COLONNES * TAILLE_CASE) && (y < NBRE_LIGNES * TAILLE_CASE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (zone[(cLigne * NBRE_COLONNES) + cColonne] > CASE_MINE) {

                        faireRepaint = true;

                        if (zone[(cLigne * NBRE_COLONNES) + cColonne] <= CASE_MINE_MASQUE) {

                            if (minesRestantes > 0) {
                                zone[(cLigne * NBRE_COLONNES) + cColonne] += MARQUE_POUR_CASE;
                                minesRestantes--;
                                String msg = "Nombre de mines: "+Integer.toString(minesRestantes);
                                barreDeStatus.setText(msg);
                            } else {
                                barreDeStatus.setText("Seuil de marquage atteint !");
                            }
                        } else {

                            zone[(cLigne * NBRE_COLONNES) + cColonne] -= MARQUE_POUR_CASE;
                            minesRestantes++;
                            String msg = "Nombre de mines: "+Integer.toString(minesRestantes);
                            barreDeStatus.setText(msg);
                        }
                    }

                } else {

                    if (zone[(cLigne * NBRE_COLONNES) + cColonne] > CASE_MINE_MASQUE) {

                        return;
                    }

                    if ((zone[(cLigne * NBRE_COLONNES) + cColonne] > CASE_MINE)
                            && (zone[(cLigne * NBRE_COLONNES) + cColonne] < CASE_MINE_MARQUE)) {

                        zone[(cLigne * NBRE_COLONNES) + cColonne] -= MASQUE_POUR_CASE;
                        faireRepaint = true;

                        if (zone[(cLigne * NBRE_COLONNES) + cColonne] == CASE_MINE) {
                            partieEnCours = false;
                        }

                        if (zone[(cLigne * NBRE_COLONNES) + cColonne] == CASE_VIDE) {
                            chercherCasesVides((cLigne * NBRE_COLONNES) + cColonne);
                        }
                    }
                }

                if (faireRepaint) {
                    repaint();
                }
            }
        }
    }
}
