package Pasta;

public class ComunicadoDePalavra extends Comunicado{
    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

    Palavra palavra = null;
}
