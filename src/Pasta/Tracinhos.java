package Pasta;

public class Tracinhos extends Comunicado implements Cloneable

{
    private char texto [];

    public Tracinhos (int qtd) throws Exception
    {
        if (qtd  < 0) //se qtd for negativo lançamos uma exceção
        {
            throw new Exception("Caracteres negativos inválidos.");
        }

        this.texto = new char[qtd]; //colocamos texto com tamanho igual qtd
        for(int i = 0; i < this.texto.length; i++ ){ //percorre o tamanho do texto e preenche com tracinho
            this.texto[i] = '_';        //texto na posição [i]  recebe o underline
        }
        // verifica se qtd não é positiva, lançando uma exceção.
        // instancia this.texto com um vetor com tamanho igual qtd.
        // preenche this.texto com underlines (_).
    }

    public void revele (int posicao, char letra) throws Exception
    {
        if(posicao < 0 || posicao >= this.texto.length){
            throw new Exception("posição negativa ou igual ou maior que o tamanho do texto");
        }
        this.texto[posicao] = letra; //texto indexado de posição recebe a letra que foi passada

        // verifica se posicao é negativa ou então igual ou maior
        // do que this.texto.length, lançando uma exceção.
        // armazena a letra fornecida na posicao tambem fornecida
        // do vetor this.texto
    }

    public boolean isAindaComTracinhos ()
    {
        for(int i = 0;i < this.texto.length;i++){
            if(this.texto[i] == '_'){
                return true;
            }
        }
        return false;
        // percorre o vetor de char this.texto e verifica
        // se o mesmo ainda contem algum underline ou se ja
        // foram todos substituidos por letras; retornar true
        // caso ainda reste algum underline, ou false caso
        // contrario
    }

    public String toString ()
    {
        String ret = "";
        for(int i = 0;i < this.texto.length;i++){
            ret += this.texto[i];
            if (i!= this.texto.length-1){
                ret += "";
            }
        }
        return ret;

        // retorna um String com TODOS os caracteres que há
        // no vetor this.texto, intercalados com espaços em
        // branco
    }

    public boolean equals (Object obj)
    {
        if (this==obj) return true;

        if (obj==null) return false;


        if (obj.getClass()!=Tracinhos.class) return false;

        Tracinhos tracinhos = (Tracinhos) obj;

        for(int i = 0;i < this.texto.length;i++){
            if (this.texto[i]!=tracinhos.texto[i]){ //se texto na posicao [i] for diferente de tracinhos na posicao [i] retornamos falso pois não é igual
                return false;
            }
        }
        return true;
        //if (this==obj) return true;
        //
        //if (obj==null) return false;

        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo
    }

    public int hashCode ()
    {
        int ret=666;

        for(int i = 0;i < this.texto.length;i++) {
            ret = ret * 13 +  Character.valueOf(this.texto[i]).hashCode(); // pegando um caracter do texto e transformando o caracter na classe dele e pegando hashcode]
            //ret = ret * 13 +  new Character(this.texto[i]).hashCode();
        }
        if (ret<0) ret = -ret;

        return ret;
        // calcular e retornar o hashcode de this
    }

    public Tracinhos (Tracinhos t) throws Exception // construtor de cópia
    {

        if (t==null)
            throw new Exception ("Modelo ausente");

        this.texto = new char[t.texto.length];

        for(int i = 0;i < this.texto.length;i++){
            this.texto[i] = t.texto[i];

        }
        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copiar o conteúdo de t.texto para this.texto
    }

    public Object clone ()
    {
        Tracinhos ret=null;

        try
        {
            ret = new Tracinhos (this);
        }
        catch (Exception erro)
        {} // ignorando Exception, pois sei que não ocorrera

        return ret;
        // retornar uma copia de this

    }
}
