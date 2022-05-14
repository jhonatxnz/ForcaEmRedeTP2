package Pasta;

public class ControladorDeLetrasJaDigitadas extends  Comunicado implements Cloneable
{
    private String letrasJaDigitadas;

    public ControladorDeLetrasJaDigitadas ()
    {
        this.letrasJaDigitadas = "";
        // torna this.letrasJaDigitadas igual ao String vazio
    }

    public boolean isJaDigitada (char letra)
    {
        for (int i = 0;i < letrasJaDigitadas.length();i++){ //percorre o string letrasJaDigitadas
            if(this.letrasJaDigitadas.charAt(i) == letra){
                return true; //possui a letra fornecida
            }
        }
        return false;
        // percorrer o String this.letrasJaDigitadas e verificar se ele
        // possui a letra fornecida, retornando true em caso afirmativo
        // ou false em caso negativo
    }

    public void registre (char letra) throws Exception
    {
        if(isJaDigitada(letra)){ //verifica se a letra ja foi digitada
            throw new Exception("Letra ja foi digitada");
        }
        else{
          this.letrasJaDigitadas += letra;

        }

        // verifica se a letra fornecida ja foi digitada (pode usar
        // o método this.isJaDigitada, para isso), lancando uma exceção
        // em caso afirmativo.
        // concatena a letra fornecida a this.letrasJaDigitadas.
        // concatena a letra fornecida a this.letrasJaDigitadas.
    }
    @Override
    public String toString ()
    {
        String ret ="";
        for(int i = 0;i < this.letrasJaDigitadas.length();i++){
            if(i == this.letrasJaDigitadas.length()-1){
                ret +=this.letrasJaDigitadas.charAt(i);
            }
            else
                ret +=this.letrasJaDigitadas.charAt(i) + ",";
        }
        return ret;
        // retorna um String com TODAS as letras presentes em
        // this.letrasJaDigitadas separadas por vírgula (,).
    }

    public boolean equals (Object obj)
    {
        if (this==obj) return true;

        if (obj==null) return false;


        if (obj.getClass()!=Palavra.class) return false;

        ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas = (ControladorDeLetrasJaDigitadas) obj;

        if (this.letrasJaDigitadas.equals(controladorDeLetrasJaDigitadas.letrasJaDigitadas)) return false;


        return true;
        // verificar se this e obj são iguais
    }

    public int hashCode ()
    {
        int ret=666; // um valor qualquer positivo qualquer por herdar DIRETAMENTE de Object (e NÃO EXPLICITAMENTE de outra classe)

        ret = 13*ret +   (this.letrasJaDigitadas).hashCode();

        //if (this.inutil!=null)
        //    ret = 11*ret + this.inutil.hashCode(); // this.inutil

        if (ret<0) ret = -ret;

        return ret;
        // calcular e retornar o hashcode de this
    }

    public ControladorDeLetrasJaDigitadas(ControladorDeLetrasJaDigitadas controladorDeLetrasJaDigitadas)
            throws Exception // construtor de cópia
    {
        if (controladorDeLetrasJaDigitadas==null)
            throw new Exception ("Modelo ausente");

        this.letrasJaDigitadas = controladorDeLetrasJaDigitadas.letrasJaDigitadas;

        // copiar c.letrasJaDigitadas em this.letrasJaDigitadas
    }
    public Object clone ()
    {
        ControladorDeLetrasJaDigitadas ret=null;

        try
        {
            ret = new ControladorDeLetrasJaDigitadas (this);
        }
        catch (Exception erro)
        {} // ignorando Exception, pois sei que não ocorrera

        return ret;
        // criar uma cópia do this com o construtor de cópia e retornar
    }
}
