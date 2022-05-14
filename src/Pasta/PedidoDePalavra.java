package Pasta;

public class PedidoDePalavra extends Comunicado {
    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    private int jogadorDaVez;
    private String palavra ="";
    private ComunicadoDeDados comunicadoDeDados;

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }
    public PedidoDePalavra(String palavra, ComunicadoDeDados c)
    {
        this.palavra = palavra;
        this.comunicadoDeDados = c;
        //this.jogadorDaVez = jogadorDaVez;
    }
}
