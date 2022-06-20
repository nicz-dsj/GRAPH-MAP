package Graphe.ConfigLieu;

public class Loisir extends Lieu {
    public Loisir() {
        super();
    }

    public Loisir(String nomLieu) {
        super(nomLieu);
    }

    @Override
    public String toString() {
        return "Nom : " + super.getNomLieu() + ", Type : Loisir";
    }

    @Override
    public int compareTo(Object o) {
        Lieu o1 = (Lieu) o;
        return getNomLieu ().compareTo (o1.getNomLieu ());
    }
}
