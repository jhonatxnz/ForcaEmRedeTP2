package Pasta;

public class ComunicadoDaVez extends Comunicado{
    private int jogadorDaVez;
    private int qtdJogadores;
    private ComunicadoDeDados comunicadoDeDados;

    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez() {
        this.jogadorDaVez++;
        if(qtdJogadores == this.jogadorDaVez){
            jogadorDaVez = 0;
        }
    }

    public ComunicadoDaVez(int jogadorDaVez,int qtdJogadores, ComunicadoDeDados c) {
        this.jogadorDaVez = jogadorDaVez;
        this.qtdJogadores = qtdJogadores;
        this.comunicadoDeDados = c;
    }
    public void setQtdJogadores(int qtdJogadores){
        int diferenca = this.qtdJogadores-qtdJogadores;
        if(this.jogadorDaVez > 0)
            this.jogadorDaVez -= diferenca;
        this.qtdJogadores = qtdJogadores;
    }
    public ComunicadoDeDados getComunicadoDeDados(){
        return this.comunicadoDeDados;

    }
}