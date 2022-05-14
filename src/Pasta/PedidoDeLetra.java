package Pasta;

public class PedidoDeLetra extends Comunicado
{
    private char letra;
    private ComunicadoDeDados comunicadoDeDados;

    public ComunicadoDeDados getComunicadoDeDados() {
        return comunicadoDeDados;
    }

    public void setComunicadoDeDados(ComunicadoDeDados comunicadoDeDados) {
        this.comunicadoDeDados = comunicadoDeDados;
    }


    public PedidoDeLetra(char letra, ComunicadoDeDados c)
    {
        this.letra = letra;
        this.comunicadoDeDados = c;
    }

    public char getLetra ()
    {
        return this.letra;
    }


    public String toString ()
    {
        return (""+this.letra);
    }
}