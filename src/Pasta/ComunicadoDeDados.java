package Pasta;

public class ComunicadoDeDados extends Comunicado

{
    private int jogador1;
    private int jogador2;
    private int jogador3;

    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    private int jogadorDaVez;

    public int getJogador1() {
        return jogador1;
    }

    public void setJogador1(int jogador1) {
        this.jogador1 = jogador1;
    }

    public int getJogador2() {
        return jogador2;
    }

    public void setJogador2(int jogador2) {
        this.jogador2 = jogador2;
    }

    public int getJogador3() {
        return jogador3;
    }

    public void setJogador3(int jogador3) {
        this.jogador3 = jogador3;
    }


    public ComunicadoDeDados(int jogador1, int jogador2, int jogador3) {

        try {

            this.jogador1= jogador1;
            this.jogador2= jogador2;
            this.jogador3= jogador3;
            this.palavra = BancoDePalavras.getPalavraSorteada();
            this.tracinhos = new Tracinhos(palavra.getTamanho());
            this.controladorDeLetrasJaDigitadas = new ControladorDeLetrasJaDigitadas();
            this.controladorDeErros = new ControladorDeErros((int) (palavra.getTamanho() * 0.9));
        }
        catch (Exception erro){}
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

    public Tracinhos getTracinhos() {
        return tracinhos;
    }

    public void setTracinhos(Tracinhos tracinhos) {
        this.tracinhos = tracinhos;
    }

    private Palavra palavra;

    public ControladorDeErros getControladorDeErros() {
        return controladorDeErros;
    }

    public void setControladorDeErros(ControladorDeErros controladorDeErros) {
        this.controladorDeErros = controladorDeErros;
    }

    public ControladorDeLetrasJaDigitadas getControladorDeLetrasJaDigitadas() {
        return controladorDeLetrasJaDigitadas;
    }

    public void setControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas) {
        this.controladorDeLetrasJaDigitadas = controladorDeLetrasJaDigitadas;
    }

    private Tracinhos tracinhos;
    private ControladorDeErros controladorDeErros;
    private ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas;

    public ComunicadoDeDados (ComunicadoDeDados c) throws Exception // construtor de cópia
    {

        if (c==null)
            throw new Exception ("Modelo ausente");

        this.jogador1= c.jogador1;
        this.jogador2= c.jogador2;
        this.jogador3= c.jogador3;
        this.palavra = c.palavra;
        this.tracinhos = c.tracinhos;
        this.controladorDeLetrasJaDigitadas = c.controladorDeLetrasJaDigitadas;
        this.controladorDeErros = c.controladorDeErros;

        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copiar o conteúdo de t.texto para this.texto
    }
}
