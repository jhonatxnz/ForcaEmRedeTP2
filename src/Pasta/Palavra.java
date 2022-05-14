package Pasta;

public class Palavra extends Comunicado implements Comparable<Palavra>
{
    public String getTexto() {
        return texto;
    }
    public void setPalavra(String texto) {
        this.texto = texto;
    }
    private String texto;


    public Palavra (String texto) throws Exception
    {
        if(texto == null || texto.equals("")){
            throw new Exception("texto nulo ou então vazio.");
        }
        this.texto = texto;
        // verifica se o texto recebido é nulo ou então vazio,
        // ou seja, sem nenhum caractere, lançando exceção.
        // armazena o texto recebido em this.texto.
    }

    public int getQuantidade (char letra)
    {
        int cnt = 0;
        for(int i = 0;i < this.texto.length();i++){
            if(this.texto.charAt(i) == letra){
                cnt++;
            }
        }
        return cnt;
        // percorre o String this.texto, conta e retorna
        // quantas letras existem nele iguais a letra fornecida
    }

    public int getPosicaoDaIezimaOcorrencia (int i, char letra) throws Exception
    {
        int cnt = -1;
        for(int j = 0;j < this.texto.length();j++){
            if(this.texto.charAt(j) == letra){
                cnt++;
                if(cnt == i){
                    return j;
                }
            }
        }
        throw new Exception("Letra não encontradada");

        // se i==0, retorna a posicao em que ocorre a primeira
        // aparicao de letra fornecida em this.texto;
        // se i==1, retorna a posicao em que ocorre a segunda
        // aparicao de letra fornecida em this.texto;
        // se i==2, retorna a posicao em que ocorre a terceira
        // aparicao de letra fornecida em this.texto;
        // e assim por diante.
        // lançar excecao caso nao encontre em this.texto
        // a Iézima aparição da letra fornecida.
    }

    public int getTamanho ()
    {
        return this.texto.length();
    }

    public String toString ()
    {
        return this.texto;
    }

    public boolean equals (Object obj)
    {
        if (this==obj) return true;

        if (obj==null) return false;


        if (obj.getClass()!=Palavra.class) return false;

        Palavra palavra = (Palavra) obj;

        if (!this.texto.equals(palavra.texto)) return false;


        return true;
        // verificar se this e obj possuem o mesmo conteúdo, retornando
        // true no caso afirmativo ou false no caso negativo
    }

    public int hashCode ()
    {
        int ret=666; // um valor qualquer positivo qualquer por herdar DIRETAMENTE de Object (e NÃO EXPLICITAMENTE de outra classe)

        ret = 13*ret + (this.texto).hashCode(); // this.segundo

        //if (this.inutil!=null)
        //    ret = 11*ret + this.inutil.hashCode(); // this.inutil

        if (ret<0) ret = -ret;

        return ret;
        // calcular e retornar o hashcode de this
    }

    public int compareTo (Palavra palavra)
    {
        return this.texto.compareTo(palavra.texto);
    }


}