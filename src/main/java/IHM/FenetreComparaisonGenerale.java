package IHM;

import Graphe.ConfigListe.Exceptions.ExceptionAjListeGraphe;
import Graphe.CreationGraphe;
import org.graphstream.graph.Graph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.Color.black;

public class FenetreComparaisonGenerale extends JFrame {
        FenetrePrincipale fenetrePrincipale;
        JFrame jFrame = new JFrame ();
        private String cheminFile;

        CreationGraphe creationGraphe;

        JPanel graph1;

        JPanel graph2;

        JPanel graph3;

        JPanel constrPartieVisuel;

        JLabel background = new JLabel ();

        JPanel noeudsVoisins;

        String lieu1;

        String lieu2;
        Graph getGraph1;

        Graph getGraph2;

    String lieuOuvert;
    int nbVilleMax;
    String lieuGastronomique;
    int nbRestaurantMax;
    String lieuCulturel;
    int nbLoisirMax;

    int nbVilleLieu1;
    int nbVilleLieu2;
    int nbRestaurantLieu1;
    int nbRestaurantLieu2;
    int nbLoisirLieu1;
    int nbLoisirLieu2;

    JLabel medaille1Lieu1;
        JLabel medaille2Lieu1;
        JLabel medaille3Lieu1;

        JLabel medaille1Lieu2;

        JLabel medaille2Lieu2;

        JLabel medaille3Lieu2;

        public FenetreComparaisonGenerale (String newCheminFile, FenetrePrincipale newFenetrePrincipale) throws IOException, ExceptionAjListeGraphe {
            super ();
            cheminFile = newCheminFile;
            fenetrePrincipale = newFenetrePrincipale;
            creationGraphe = new CreationGraphe (cheminFile);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            ImageIcon imageFond = new ImageIcon("src/main/resources/Graph_Plan.png");
            jFrame.setMinimumSize(new Dimension(1650, 1080));
            jFrame.setExtendedState(this.MAXIMIZED_BOTH);
            jFrame.getContentPane().setLayout(new GridLayout(1, 1));
            Image iconGraph = Toolkit.getDefaultToolkit().getImage ("src/main/resources/GraphIcone.png");
            jFrame.setIconImage (iconGraph);
            jFrame.setJMenuBar (jMenuBar ());
            JLabel background = new JLabel(imageFond);
            background.setLayout (new FlowLayout ());
            jFrame.add (background);
            constrPartieVisuel = constrPartieVisuel ();
            background.add (constrPartieVisuel);
            background.add (informationsGraphe ());
            jFrame.setVisible(true);
        }

        public JPanel constrPartieVisuel () throws IOException, ExceptionAjListeGraphe {
            JPanel p = new JPanel ();
            p.setLayout (new BorderLayout());
            comparaisonGenerale ();
            graph1 = constrFenVisuel1 ();
            graph2 = constrFenVisuel2 ();
            graph3 = constrFenVisuel3 ();
            p.add (graph1, BorderLayout.WEST);
            p.add (graph2,  BorderLayout.CENTER);
            p.add (graph3,  BorderLayout.EAST);
            p.setOpaque (false);
            return p;
        }

        public JPanel basPanel () {
            JPanel p = new JPanel ();
            p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
            p.add (boutonsDeVisualisation ());
            p.add (Box.createRigidArea(new Dimension(0, 60)));
            p.add (noeudsVoisins);
            p.setOpaque (false);
            return p;
        }

        public JPanel informationsGraphe () throws IOException, ExceptionAjListeGraphe {
            JPanel p = new JPanel ();
            p.setLayout (new BorderLayout());

            p.add (constrInformationsSurLeGraphe1 (), BorderLayout.WEST);
            p.add (constrInformationsSurLeGraphe2 (), BorderLayout.CENTER);
            p.add (constrInformationsSurLeGraphe3 (), BorderLayout.EAST);
            p.setOpaque (false);

            return p;
        }

        public JPanel constrFenVisuel1 () throws IOException, ExceptionAjListeGraphe {
            System.setProperty("org.graphstream.ui", "swing");

            graph1 = creationGraphe.creerGraphe2DistanceTailleRéduit (lieuOuvert);

            graph1.setOpaque (false);

            return graph1;
        }

        public JPanel constrFenVisuel2 () throws IOException, ExceptionAjListeGraphe {
            System.setProperty("org.graphstream.ui", "swing");

            graph2 = creationGraphe.creerGraphe2DistanceTailleRéduit (lieuGastronomique);

            graph2.setOpaque (false);

            return graph2;
        }

    public JPanel constrFenVisuel3 () throws IOException, ExceptionAjListeGraphe {
        System.setProperty("org.graphstream.ui", "swing");

        graph3 = creationGraphe.creerGraphe2DistanceTailleRéduit (lieuCulturel);

        graph3.setOpaque (false);

        return graph3;
    }

    public void comparaisonGenerale () {
        lieuOuvert = "";
        nbVilleMax = 0;
        lieuGastronomique = "";
        nbRestaurantMax = 0;
        lieuCulturel = "";
        nbLoisirMax = 0;

        for (String lieu : creationGraphe.getNoeud(CreationGraphe.getGraphe())) {
            Graph getGraph = creationGraphe.getGraphVoisins1Et2Distance(lieu);
            int nbVille1 = creationGraphe.getNbVille (getGraph);
            int nbRestaurant1 = creationGraphe.getNbRestaurant (getGraph);
            int nbLoisir1 = creationGraphe.getNbVille (getGraph);
            switch (creationGraphe.getClasse (getGraph, lieu)) {
                case "Ville":
                    nbVille1 = nbVille1 - 1;
                    break;
                case "Restaurant":
                    nbRestaurant1 = nbRestaurant1 - 1;
                    break;
                case "Loisir":
                    nbLoisir1 = nbLoisir1 - 1;
                    break;
            }
            if (nbVille1 > nbVilleMax) {
                nbVilleMax = nbVille1;
                lieuOuvert = lieu;
            }
            if (nbRestaurant1 > nbRestaurantMax) {
                nbRestaurantMax = nbRestaurant1;
                lieuGastronomique = lieu;
            }
            if (nbLoisir1 > nbLoisirMax) {
                nbLoisirMax = nbLoisir1;
                lieuCulturel = lieu;
            }
        }
    }

        public JPanel constrInformationsSurLeGraphe1 () {
            JPanel p = new JPanel ();
            p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
            JLabel informationsGraphe = new JLabel ("Lieu ouvert :  " + lieuOuvert);
            p.add (informationsGraphe);
            JPanel p1 = new JPanel ();
            p1.setLayout (new FlowLayout());
            medaille1Lieu1 = new JLabel (" / : ouvert ");
            medaille2Lieu1 = new JLabel ("/ : gastrnomique ");
            medaille3Lieu1 = new JLabel (" / : culturel ");
            p1.add (medaille1Lieu1);
            p1.add (medaille2Lieu1);
            p1.add (medaille3Lieu1);
            p1.setOpaque (false);
            p.add (p1);
            medaille1Lieu1.setFont (new Font ("Arial", Font.BOLD, 10));
            medaille1Lieu1.setForeground(Color.WHITE);
            medaille2Lieu1.setFont (new Font ("Arial", Font.BOLD, 10));
            medaille2Lieu1.setForeground(Color.WHITE);
            medaille3Lieu1.setFont (new Font ("Arial", Font.BOLD, 10));
            medaille3Lieu1.setForeground(Color.WHITE);
            informationsGraphe.setFont (new Font ("Arial", Font.BOLD, 20));
            informationsGraphe.setForeground(Color.WHITE);
            Icon iconMedaille1 = new ImageIcon ("src/main/resources/medailleLieu.png");
            medaille1Lieu1.setIcon(iconMedaille1);
            p.add (new JLabel (" "));
            Icon iconVille = new ImageIcon ("src/main/resources/lieuVilleIcone.png");
            getGraph1 = creationGraphe.getGraphVoisins1Et2Distance (lieuOuvert);
            nbVilleLieu1 = creationGraphe.getNbVille (getGraph1);
            nbRestaurantLieu1 = creationGraphe.getNbRestaurant (getGraph1);
            nbLoisirLieu1 = creationGraphe.getNbLoisir (getGraph1);
            switch (creationGraphe.getClasse (getGraph1, lieuOuvert)) {
                case "Ville":
                    nbVilleLieu1 = nbVilleLieu1 - 1;
                    break;
                case "Restaurant":
                    nbRestaurantLieu1 = nbRestaurantLieu1 - 1;
                    break;
                case "Loisir":
                    nbLoisirLieu1 = nbLoisirLieu1 - 1;
                    break;
            }
            JLabel nombreVilles = new JLabel ("Nombre de villes reliées ");
            p.add (nombreVilles);
            nombreVilles.setIcon (iconVille);
            nombreVilles.setFont (new Font ("Arial", Font.BOLD, 15));
            nombreVilles.setForeground(Color.WHITE);
            JLabel nbVilles = new JLabel (String.valueOf(nbVilleLieu1));
            p.add (nbVilles);
            nbVilles.setForeground(Color.WHITE);
            Icon iconRestaurant = new ImageIcon ("src/main/resources/lieuRestaurantIcone.png");
            JLabel nombreRestaurants = new JLabel ("Nombre de restaurants reliés ");
            p.add (nombreRestaurants);
            nombreRestaurants.setIcon (iconRestaurant);
            nombreRestaurants.setFont (new Font ("Arial", Font.BOLD, 15));
            nombreRestaurants.setForeground(Color.WHITE);
            JLabel nbRestaurants = new JLabel (String.valueOf(nbRestaurantLieu1));
            p.add (nbRestaurants);
            nbRestaurants.setForeground(Color.WHITE);
            Icon iconLoisir = new ImageIcon ("src/main/resources/lieuLoisirIcone.png");
            JLabel nombreLoisirs = new JLabel ("Nombre de loisirs reliés ");
            p.add (nombreLoisirs);
            nombreLoisirs.setIcon (iconLoisir);
            nombreLoisirs.setFont (new Font ("Arial", Font.BOLD, 15));
            nombreLoisirs.setForeground(Color.WHITE);
            JLabel nbLoisirs = new JLabel (String.valueOf(nbLoisirLieu1));
            p.add (nbLoisirs);
            nbLoisirs.setForeground(Color.WHITE);
            p.setOpaque (false);
            return p;
        }

        public JPanel constrInformationsSurLeGraphe2 () {
            JPanel p = new JPanel ();
            p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
            JLabel informationsGraphe = new JLabel ("Lieu gastronomique : " + lieuGastronomique);
            p.add (informationsGraphe);
            JPanel p1 = new JPanel ();
            p1.setLayout (new FlowLayout());
            medaille1Lieu2 = new JLabel (" / : ouvert ");
            medaille2Lieu2 = new JLabel (" / : gastronomique ");
            medaille3Lieu2 = new JLabel (" / : culturel ");
            p1.add (medaille1Lieu2);
            p1.add (medaille2Lieu2);
            p1.add (medaille3Lieu2);
            Icon iconMedaille1 = new ImageIcon ("src/main/resources/medailleLieu.png");
            medaille2Lieu2.setIcon(iconMedaille1);
            p1.setOpaque (false);
            p.add (p1);
            medaille1Lieu2.setFont (new Font ("Arial", Font.BOLD, 10));
            medaille1Lieu2.setForeground(Color.WHITE);
            medaille2Lieu2.setFont (new Font ("Arial", Font.BOLD, 10));
            medaille2Lieu2.setForeground(Color.WHITE);
            medaille3Lieu2.setFont (new Font ("Arial", Font.BOLD, 10));
            medaille3Lieu2.setForeground(Color.WHITE);
            informationsGraphe.setFont (new Font ("Arial", Font.BOLD, 20));
            informationsGraphe.setForeground(Color.WHITE);
            p.add (new JLabel (" "));
            Icon iconVille = new ImageIcon ("src/main/resources/lieuVilleIcone.png");
            getGraph2 = creationGraphe.getGraphVoisins1Et2Distance (lieuGastronomique);
            nbVilleLieu2 = creationGraphe.getNbVille (getGraph2);
            nbRestaurantLieu2 = creationGraphe.getNbRestaurant (getGraph2);
            nbLoisirLieu2 = creationGraphe.getNbVille (getGraph2);
            switch (creationGraphe.getClasse (getGraph2, lieuGastronomique)) {
                case "Ville":
                    nbVilleLieu2 = nbVilleLieu2 - 1;
                    break;
                case "Restaurant":
                    nbRestaurantLieu2 = nbRestaurantLieu2 - 1;
                    break;
                case "Loisir":
                    nbLoisirLieu2 = nbLoisirLieu2 - 1;
                    break;
            }
            JLabel nombreVilles = new JLabel ("Nombre de villes reliées ");
            p.add (nombreVilles);
            nombreVilles.setIcon (iconVille);
            nombreVilles.setFont (new Font ("Arial", Font.BOLD, 15));
            nombreVilles.setForeground(Color.WHITE);
            JLabel nbVilles = new JLabel (String.valueOf(nbVilleLieu2));
            p.add (nbVilles);
            nbVilles.setForeground(Color.WHITE);
            Icon iconRestaurant = new ImageIcon ("src/main/resources/lieuRestaurantIcone.png");
            JLabel nombreRestaurants = new JLabel ("Nombre de restaurants reliés ");
            p.add (nombreRestaurants);
            nombreRestaurants.setIcon (iconRestaurant);
            nombreRestaurants.setFont (new Font ("Arial", Font.BOLD, 15));
            nombreRestaurants.setForeground(Color.WHITE);
            JLabel nbRestaurants = new JLabel (String.valueOf(nbRestaurantLieu2));
            p.add (nbRestaurants);
            nbRestaurants.setForeground(Color.WHITE);
            Icon iconLoisir = new ImageIcon ("src/main/resources/lieuLoisirIcone.png");
            JLabel nombreLoisirs = new JLabel ("Nombre de loisirs reliés ");
            p.add (nombreLoisirs);
            nombreLoisirs.setIcon (iconLoisir);
            nombreLoisirs.setFont (new Font ("Arial", Font.BOLD, 15));
            nombreLoisirs.setForeground(Color.WHITE);
            JLabel nbLoisirs = new JLabel (String.valueOf(nbLoisirLieu2));
            p.add (nbLoisirs);
            nbLoisirs.setForeground(Color.WHITE);
            p.setOpaque (false);
            return p;
        }

    public JPanel constrInformationsSurLeGraphe3 () {
        JPanel p = new JPanel ();
        p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel informationsGraphe = new JLabel ("Lieu culturel : " + lieuCulturel);
        p.add (informationsGraphe);
        JPanel p1 = new JPanel ();
        p1.setLayout (new FlowLayout());
        medaille1Lieu2 = new JLabel (" / : ouvert ");
        medaille2Lieu2 = new JLabel (" / : gastronomique ");
        medaille3Lieu2 = new JLabel (" / : culturel ");
        p1.add (medaille1Lieu2);
        p1.add (medaille2Lieu2);
        p1.add (medaille3Lieu2);
        Icon iconMedaille1 = new ImageIcon ("src/main/resources/medailleLieu.png");
        medaille3Lieu2.setIcon(iconMedaille1);
        p1.setOpaque (false);
        p.add (p1);
        medaille1Lieu2.setFont (new Font ("Arial", Font.BOLD, 10));
        medaille1Lieu2.setForeground(Color.WHITE);
        medaille2Lieu2.setFont (new Font ("Arial", Font.BOLD, 10));
        medaille2Lieu2.setForeground(Color.WHITE);
        medaille3Lieu2.setFont (new Font ("Arial", Font.BOLD, 10));
        medaille3Lieu2.setForeground(Color.WHITE);
        informationsGraphe.setFont (new Font ("Arial", Font.BOLD, 20));
        informationsGraphe.setForeground(Color.WHITE);
        p.add (new JLabel (" "));
        Icon iconVille = new ImageIcon ("src/main/resources/lieuVilleIcone.png");
        getGraph2 = creationGraphe.getGraphVoisins1Et2Distance (lieuCulturel);
        nbVilleLieu2 = creationGraphe.getNbVille (getGraph2);
        nbRestaurantLieu2 = creationGraphe.getNbRestaurant (getGraph2);
        nbLoisirLieu2 = creationGraphe.getNbVille (getGraph2);
        switch (creationGraphe.getClasse (getGraph2, lieuCulturel)) {
            case "Ville":
                nbVilleLieu2 = nbVilleLieu2 - 1;
                break;
            case "Restaurant":
                nbRestaurantLieu2 = nbRestaurantLieu2 - 1;
                break;
            case "Loisir":
                nbLoisirLieu2 = nbLoisirLieu2 - 1;
                break;
        }
        JLabel nombreVilles = new JLabel ("Nombre de villes reliées ");
        p.add (nombreVilles);
        nombreVilles.setIcon (iconVille);
        nombreVilles.setFont (new Font ("Arial", Font.BOLD, 15));
        nombreVilles.setForeground(Color.WHITE);
        JLabel nbVilles = new JLabel (String.valueOf(nbVilleLieu2));
        p.add (nbVilles);
        nbVilles.setForeground(Color.WHITE);
        Icon iconRestaurant = new ImageIcon ("src/main/resources/lieuRestaurantIcone.png");
        JLabel nombreRestaurants = new JLabel ("Nombre de restaurants reliés ");
        p.add (nombreRestaurants);
        nombreRestaurants.setIcon (iconRestaurant);
        nombreRestaurants.setFont (new Font ("Arial", Font.BOLD, 15));
        nombreRestaurants.setForeground(Color.WHITE);
        JLabel nbRestaurants = new JLabel (String.valueOf(nbRestaurantLieu2));
        p.add (nbRestaurants);
        nbRestaurants.setForeground(Color.WHITE);
        Icon iconLoisir = new ImageIcon ("src/main/resources/lieuLoisirIcone.png");
        JLabel nombreLoisirs = new JLabel ("Nombre de loisirs reliés ");
        p.add (nombreLoisirs);
        nombreLoisirs.setIcon (iconLoisir);
        nombreLoisirs.setFont (new Font ("Arial", Font.BOLD, 15));
        nombreLoisirs.setForeground(Color.WHITE);
        JLabel nbLoisirs = new JLabel (String.valueOf(nbLoisirLieu2));
        p.add (nbLoisirs);
        nbLoisirs.setForeground(Color.WHITE);
        p.setOpaque (false);
        return p;
    }


        public JPanel legende () {
            JPanel p = new JPanel ();
            p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
            Icon legendeRecherche = new ImageIcon("src/main/resources/mapPinSmall.png");
            JLabel recherche = new JLabel("Lieu départ : " + lieu1);
            JLabel recherche2 = new JLabel("Lieu recherché : " + lieu1);
            p.add(recherche);
            p.add (recherche2);
            recherche.setFont(new Font("Arial", Font.BOLD, 15));
            recherche.setForeground(Color.WHITE);
            recherche.setIcon(legendeRecherche);

            recherche2.setFont(new Font("Arial", Font.BOLD, 15));
            recherche2.setForeground(Color.WHITE);
            recherche2.setIcon(legendeRecherche);

            Icon legendeVille = new ImageIcon("src/main/resources/legendeVille.png");
            JLabel ville = new JLabel("Ville ");
            p.add(ville);
            ville.setFont(new Font("Arial", Font.BOLD, 15));
            ville.setForeground(Color.WHITE);
            ville.setIcon(legendeVille);

            Icon legendeRestaurant = new ImageIcon("src/main/resources/legendeRestaurant.png");
            JLabel restaurant = new JLabel("Restaurants ");
            p.add(restaurant);
            restaurant.setFont(new Font("Arial", Font.BOLD, 15));
            restaurant.setForeground(Color.WHITE);
            restaurant.setIcon(legendeRestaurant);

            Icon legendeLoisir = new ImageIcon("src/main/resources/legendeLoisir.png");
            JLabel loisir = new JLabel("Loisir ");
            p.add(loisir);
            loisir.setFont(new Font("Arial", Font.BOLD, 15));
            loisir.setForeground(Color.WHITE);
            loisir.setIcon(legendeLoisir);

            Icon lgendeNationale = new ImageIcon ("src/main/resources/legendeNationale.png");
            JLabel nationale = new JLabel ("Nationale ");
            p.add (nationale);
            nationale.setFont (new Font ("Arial", Font.BOLD, 15));
            nationale.setForeground(Color.WHITE);
            nationale.setIcon (lgendeNationale);
            Icon legendeDepartementale = new ImageIcon ("src/main/resources/legendeDepartementale.png");
            JLabel departementale = new JLabel ("Departementale ");
            p.add (departementale);
            departementale.setFont (new Font ("Arial", Font.BOLD, 15));
            departementale.setForeground(Color.WHITE);
            departementale.setIcon (legendeDepartementale);
            Icon legendeAutoroute = new ImageIcon ("src/main/resources/legendeAutoroute.png");
            JLabel autoroute = new JLabel ("Autoroute ");
            p.add (autoroute);
            autoroute.setFont (new Font ("Arial", Font.BOLD, 15));
            autoroute.setForeground(Color.WHITE);
            autoroute.setIcon (legendeAutoroute);
            p.setOpaque (false);
            return p;
        }

        public JPanel boutonsDeVisualisation () {
            JPanel p = new JPanel ();
            p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
            JButton revenirAuGraphePrincipal = new JButton ("Revenir au graphe principal ");
            p.add (revenirAuGraphePrincipal);
            revenirAuGraphePrincipal.addActionListener (event -> {
                try {
                    FenetreGraphe fenetreGraphe = new FenetreGraphe (cheminFile, fenetrePrincipale);
                    jFrame.setVisible (false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ExceptionAjListeGraphe e) {
                    throw new RuntimeException(e);
                }
            });
            p.setOpaque (false);
            return p;
        }

        public JMenuBar jMenuBar () {
            JMenuBar jMenuBar = new JMenuBar ();
            jMenuBar.setBackground (black);
            jMenuBar.add (jMenuFichier ());
            jMenuBar.add (jMenuAPropos ());
            jMenuBar.add (jMenuAffichage ());
            jMenuBar.add (jMenuChercher ());
            return jMenuBar;
        }

        public JMenu jMenuFichier () {
            JMenu fichier = new JMenu ("Fichier ");
            Icon nouveauIcon = new ImageIcon ("src/main/resources/nouveau.png");
            ImageIcon iconNouveauRedim = new ImageIcon(((ImageIcon) nouveauIcon).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem nouveau = new JMenuItem ("nouveau ", iconNouveauRedim);
            Icon iconOuverture = new ImageIcon ("src/main/resources/ouverture.png");
            ImageIcon iconOuvrirRedim = new ImageIcon(((ImageIcon) iconOuverture).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem ouvrir = new JMenuItem ("ouvrir ", iconOuvrirRedim);
            Icon enregistrerIcon = new ImageIcon ("src/main/resources/enregistrer.png");
            ImageIcon iconEnregistrerRedim = new ImageIcon(((ImageIcon) enregistrerIcon).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
            JMenuItem enregistrer = new JMenuItem ("enregistrer ", iconEnregistrerRedim);
            JMenuItem enregistrerSous = new JMenuItem ("enregistrer-sous ");
            Icon fermerIcon = new ImageIcon ("src/main/resources/fermer.png");
            ImageIcon iconFermerRedim = new ImageIcon(((ImageIcon) fermerIcon).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem fermer = new JMenuItem ("fermer ", iconFermerRedim);
            fichier.add (nouveau);
            nouveau.addActionListener (event -> {
                FenetreCreer fenetreCreer = new FenetreCreer (fenetrePrincipale);
                jFrame.setVisible (false);
            });
            fichier.add (ouvrir);
            ouvrir.addActionListener (event -> {
                JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                choose.setDialogTitle("selectionnez votre graphe ");
                choose.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("graphe au format csv ou txt ", "csv", "txt");
                choose.addChoosableFileFilter(filter);
                int res = choose.showOpenDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    try {
                        FenetreGraphe fenetreGraphe = new FenetreGraphe(choose.getSelectedFile().getPath(), fenetrePrincipale);
                        jFrame.setVisible (false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ExceptionAjListeGraphe e) {
                        throw new RuntimeException(e);
                    }
                }});
            fichier.add (enregistrer);
            fichier.add (enregistrerSous);
            fichier.add (new JSeparator ());
            fichier.add (fermer);
            fermer.addActionListener (event -> {
                fenetrePrincipale.getJFrame ().setVisible (true);
                jFrame.dispose ();
            });

            fichier.setForeground(Color.WHITE);
            return fichier;
        }

        public JMenu jMenuAffichage () {
            JMenu affichage = new JMenu ("Affichage ");
            Icon mapIcone = new ImageIcon ("src/main/resources/mapIcone.png");
            ImageIcon iconMapRedim = new ImageIcon(((ImageIcon) mapIcone).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem graphePrincipal = new JMenuItem ("graphe principal ", iconMapRedim);
            Icon voisinIcone = new ImageIcon ("src/main/resources/voisinIcone.jpg");
            ImageIcon iconVoisinRedim = new ImageIcon(((ImageIcon) voisinIcone).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenu grapheVoisin = new JMenu ("graphe des voisins d'un lieu ");
            grapheVoisin.setIcon (iconVoisinRedim);
            JMenuItem choixNoeudVoisin = new JMenuItem ("Allez à la fenètre dédiée ");
            affichage.add (graphePrincipal);
            graphePrincipal.addActionListener (event -> {
                try {
                    FenetreGraphe fenetreGraphe = new FenetreGraphe (cheminFile, fenetrePrincipale);
                    jFrame.setVisible (false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ExceptionAjListeGraphe e) {
                    throw new RuntimeException(e);
                }
            });
            affichage.add (grapheVoisin);
            grapheVoisin.add (choixNoeudVoisin);
            choixNoeudVoisin.addActionListener (event -> {
                ArrayList<String> graphNode = creationGraphe.getNoeud (creationGraphe.getGraphe ());
                ArrayList<Integer> distance = new ArrayList <Integer> ();
                distance.add (1);
                distance.add (2);
                JComboBox listegraphNode = new JComboBox (graphNode.toArray ());
                JComboBox choixDistance = new JComboBox (distance.toArray ());
                JLabel distanceExplications = new JLabel ("A ... distance ");
                JButton visualiser = new JButton ("visualiser ");
                visualiser.addActionListener (event1 -> {
                    try {
                        new FenetreGraphVoisin ("src/main/resources/graphe.csv", fenetrePrincipale, listegraphNode.getSelectedItem ().toString (), (Integer) choixDistance.getSelectedItem ());
                        jFrame.setVisible (false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ExceptionAjListeGraphe e) {
                        throw new RuntimeException(e);
                    }

                    validate();
                });

                Object[] options = new Object[]{};
                JOptionPane fenetreGraphVoisins = new JOptionPane("Veuillez selectionner le lieu dont vous voulez connaitre les voisins directes ",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null, options, null);

                fenetreGraphVoisins.add(listegraphNode);
                fenetreGraphVoisins.add (distanceExplications);
                fenetreGraphVoisins.add (choixDistance);
                fenetreGraphVoisins.add (visualiser);

                JDialog diag = new JDialog();
                diag.getContentPane().add(fenetreGraphVoisins);
                diag.pack();
                diag.setVisible(true);
            });

            affichage.setForeground(Color.WHITE);
            return affichage;
        }

        public JMenu jMenuChercher () {
            JMenu chercher = new JMenu ("Chercher lieu à proximité ");
            Icon villeIcone = new ImageIcon ("src/main/resources/lieuVilleIcone.png");
            ImageIcon iconVilleRedim = new ImageIcon(((ImageIcon) villeIcone).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem chercherVille = new JMenuItem ("Ville ", iconVilleRedim);
            Icon restaurantIcone = new ImageIcon ("src/main/resources/lieuRestaurantIcone.png");
            ImageIcon iconRestaurantRedim = new ImageIcon(((ImageIcon) restaurantIcone).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem chercherRestaurant = new JMenuItem ("Restaurant ", iconRestaurantRedim);
            Icon loisirIcone = new ImageIcon ("src/main/resources/lieuLoisirIcone.png");
            ImageIcon iconLoisirRedim = new ImageIcon(((ImageIcon) loisirIcone).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
            JMenuItem chercherLoisir = new JMenuItem ("Restaurant ", iconLoisirRedim);
            chercher.add (chercherVille);
            chercher.add (chercherRestaurant);
            chercher.add (chercherLoisir);
            chercherVille.addActionListener (event -> {
                JLabel aProximiteDe = new JLabel ("a proximité de : ");
                JLabel aDistanceDe = new JLabel ("a distance de : ");
                String [] aDistance = {"1"};
                ArrayList <String> graphNode = creationGraphe.getNoeud (creationGraphe.getGraphe ());
                JComboBox listegraphNode = new JComboBox (graphNode.toArray ());
                JComboBox distance = new JComboBox (aDistance);
                JButton visualiser = new JButton ("visualiser ");
                visualiser.addActionListener (event1 -> {
                    try {
                        new IHM.FenetreGrapheChercher("src/main/resources/graphe.csv", fenetrePrincipale, listegraphNode.getSelectedItem ().toString (), "Ville");
                        jFrame.setVisible (false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ExceptionAjListeGraphe e) {
                        throw new RuntimeException(e);
                    }

                    validate();
                });
                Object[] options = new Object[]{};
                JOptionPane fenetreGraphVoisins = new JOptionPane("Veuillez selectionner le lieu dont vous voulez connaitre les voisins directes ",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null, options, null);

                fenetreGraphVoisins.add (aProximiteDe);
                fenetreGraphVoisins.add(listegraphNode);
                fenetreGraphVoisins.add (aDistanceDe);
                fenetreGraphVoisins.add (distance);
                fenetreGraphVoisins.add (visualiser);

                JDialog diag = new JDialog();
                diag.getContentPane().add(fenetreGraphVoisins);
                diag.pack();
                diag.setVisible(true);
            });
            chercherRestaurant.addActionListener (event -> {
                JLabel aProximiteDe = new JLabel ("a proximité de : ");
                JLabel aDistanceDe = new JLabel ("a distance de : ");
                String [] aDistance = {"1"};
                ArrayList <String> graphNode = creationGraphe.getNoeud (creationGraphe.getGraphe ());
                JComboBox listegraphNode = new JComboBox (graphNode.toArray ());
                JComboBox distance = new JComboBox (aDistance);
                JButton visualiser = new JButton ("visualiser ");
                visualiser.addActionListener (event1 -> {
                    try {
                        new IHM.FenetreGrapheChercher("src/main/resources/graphe.csv", fenetrePrincipale, listegraphNode.getSelectedItem ().toString (), "Restaurant");
                        jFrame.setVisible (false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ExceptionAjListeGraphe e) {
                        throw new RuntimeException(e);
                    }

                    validate();
                });
                Object[] options = new Object[]{};
                JOptionPane fenetreGraphVoisins = new JOptionPane("Veuillez selectionner le lieu dont vous voulez connaitre les voisins directes ",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null, options, null);

                fenetreGraphVoisins.add (aProximiteDe);
                fenetreGraphVoisins.add(listegraphNode);
                fenetreGraphVoisins.add (aDistanceDe);
                fenetreGraphVoisins.add (distance);
                fenetreGraphVoisins.add (visualiser);

                JDialog diag = new JDialog();
                diag.getContentPane().add(fenetreGraphVoisins);
                diag.pack();
                diag.setVisible(true);
            });
            chercherLoisir.addActionListener (event -> {
                JLabel aProximiteDe = new JLabel ("a proximité de : ");
                JLabel aDistanceDe = new JLabel ("a distance de : ");
                String [] aDistance = {"1"};
                ArrayList <String> graphNode = creationGraphe.getNoeud (creationGraphe.getGraphe ());
                JComboBox listegraphNode = new JComboBox (graphNode.toArray ());
                JComboBox distance = new JComboBox (aDistance);
                JButton visualiser = new JButton ("visualiser ");
                visualiser.addActionListener (event1 -> {
                    try {
                        new IHM.FenetreGrapheChercher("src/main/resources/graphe.csv", fenetrePrincipale, listegraphNode.getSelectedItem ().toString (), "Loisir");
                        jFrame.setVisible (false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ExceptionAjListeGraphe e) {
                        throw new RuntimeException(e);
                    }

                    validate();
                });

                Object[] options = new Object[]{};
                JOptionPane fenetreGraphVoisins = new JOptionPane("Veuillez selectionner le lieu dont vous voulez connaitre les voisins directes ",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null, options, null);

                fenetreGraphVoisins.add (aProximiteDe);
                fenetreGraphVoisins.add(listegraphNode);
                fenetreGraphVoisins.add (aDistanceDe);
                fenetreGraphVoisins.add (distance);
                fenetreGraphVoisins.add (visualiser);

                JDialog diag = new JDialog();
                diag.getContentPane().add(fenetreGraphVoisins);
                diag.pack();
                diag.setVisible(true);
            });

            chercher.setForeground(Color.WHITE);
            return chercher;
        }

        public JPanel noeudsVoisins () throws IOException, ExceptionAjListeGraphe {
            JPanel p = new JPanel ();
            p.setLayout (new BoxLayout(p, BoxLayout.Y_AXIS));
            ArrayList <String> graphNode = creationGraphe.getNoeud (creationGraphe.getGraphe ());
            JLabel titreVisualiserNoeudsVoisins = new JLabel ("Visualiser les noeuds voisins directes d'un noeud ");
            JLabel explicationsNoeudsVoisins = new JLabel ("Commencez par choisir le noeud dont vous souhaitez afficher les voisins directes ");
            titreVisualiserNoeudsVoisins.setForeground(Color.WHITE);
            explicationsNoeudsVoisins.setForeground(Color.WHITE);
            JComboBox listegraphNode = new JComboBox (graphNode.toArray ());
            p.add (titreVisualiserNoeudsVoisins);
            p.add (explicationsNoeudsVoisins);
            p.add (listegraphNode);
            listegraphNode.setFont (new Font("Times New Roman", Font.BOLD, 15));
            listegraphNode.setBackground (black);
            listegraphNode.setForeground(Color.WHITE);
            JButton visualiser = new JButton ("visualiser ");
            p.add (visualiser);
            visualiser.addActionListener (event -> {
                try {
                    new FenetreGraphVoisin ("src/main/resources/graphe.csv", fenetrePrincipale, listegraphNode.getSelectedItem ().toString (), 0);
                    jFrame.setVisible (false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ExceptionAjListeGraphe e) {
                    throw new RuntimeException(e);
                }
                validate();
            });
            p.setOpaque (false);
            return p;
        }

        public JMenu jMenuAPropos () {
            JMenu APropos = new JMenu ("A propos ");
            APropos.setForeground(Color.WHITE);
            return APropos;
        }
    }
