package Pasta;

public class ControladorDeErros extends Comunicado implements Cloneable
{
    private int qtdMax, qtdErr=0;

    public ControladorDeErros (int qtdMax) throws Exception
    {
        if (qtdMax < 0)
        {
            throw new Exception("Caracteres negativos inválidos!");
        }
        this.qtdMax = qtdMax;
        // verifica se qtdMax fornecida não é positiva, lançando
        // uma exceção.
        // armazena qtdMax fornecida em this.qtdMax.
    }

    public void registreUmErro () throws Exception
    {
        if(this.qtdErr == this.qtdMax){
            throw new Exception("Quantidade máxima de erros!");
        }
        this.qtdErr++;
        // verifica se this.qtdErr ja é igual a this.qtdMax,
        // lançando excecao em caso positivo ou
        // incrementando this.qtdErr em caso negativo
    }

    public boolean isAtingidoMaximoDeErros  ()
    {
        if (this.qtdErr == this.qtdMax){
            return true;
        }
        return false;
        // returna true se this.qtdErr for igual a this.qtdMax,
        // ou então false, caso contrario.
    }

    public String toString ()
    {
        return this.qtdErr + "/" + this.qtdMax;
    }

    public boolean equals (Object obj)
    {
        if (this==obj) return true;

        if (obj==null) return false;

        if (obj.getClass()!=ControladorDeErros.class) return false;

        ControladorDeErros controladorDeErros = (ControladorDeErros) obj;

        if (this.qtdMax!=controladorDeErros.qtdMax) return false;
        if (this.qtdErr!=controladorDeErros.qtdErr) return false;


        return true;
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo

    }

    public int hashCode ()
    {
        int ret=666; // um valor qualquer positivo qualquer por herdar DIRETAMENTE de Object (e NÃO EXPLICITAMENTE de outra classe)

        ret = 13*ret +  Integer.valueOf(this.qtdMax).hashCode(); // this.segundo
        ret =  3*ret + Integer.valueOf(this.qtdErr).hashCode(); // this.minuto


        if (ret<0) ret = -ret;

        return ret;
        // calcular e retornar o hashcode de this
    }

    public ControladorDeErros (ControladorDeErros c) throws Exception // construtor de cópia
    {
        if (c==null)
            throw new Exception ("Modelo ausente");

        this.qtdMax = c.qtdMax;
        this.qtdErr = c.qtdErr;

        // copiar c.qtdMax e c.qtdErr, respectivamente em, this.qtdMax e this.qtdErr
    }

    public Object clone ()
    {
        ControladorDeErros ret=null;

        try
        {
            ret = new ControladorDeErros (this);
        }
        catch (Exception erro)
        {} // ignorando Exception, pois sei que não ocorrera

        return ret;
        // retornar uma cópia de this
    }
}