package Pasta;

public class BancoDePalavras
{
    private static String[] palavras =
            {
                    "JAVA",
                    "CLASSE",
                    "OBJETO",
                    "INSTANCIA",
                    "PUBLICO",
                    "PRIVATIVO",
                    "CARICATURA",
                    "CONSTRUTOR",
                    "SOCIEDADE",
                    "PERFECCIONISMO",
                    "ALEGRIA",
                    "PRAZER",
                    "EXCELENTE",
                    "DISPONIBILIDADE",
                    "GASTRONOMICO",
                    "INCOERENTE",
                    "DISCIPLINA",
                    "NOSTALGIA",
                    "INCOMPATIBILIDADE",
                    "INFRAESTRUTURA",
                    "DIGNIDADE",
                    "PERSPECTIVA",
                    "JUSTIFICATIVO",
                    "BAGUNCEIRO",
                    "BALANCEAMENTO",
                    "LEALDADE",
                    "INCOMPRESSIBILIDADE",
                    "MADRUGADA",
                    "MALABARISMO",
                    "NASCIMENTO",
                    "ACAMPAMENTO",
                    "ABASTECIMENTO",
                    "IMPRESSIONANTE",
                    "CONSTRANGIMENTO",
                    "COMPANHEIRISMO",
                    "PARALELEPIPEDO",
                    "CONSTANTEMENTE",
                    "CARICATURA",
                    "JABUTICABEIRA",
                    "VIOLINO",
                    "BATERISTA",
                    "VENEZUELA",
                    "FELICIDADE",
                    "COMPORTAMENTO",
                    "BORBOLETA",
                    "BAUNILHA"
            };

    public static Palavra getPalavraSorteada ()
    {
        Palavra palavra = null;

        try
        {
            palavra =
                    new Palavra (BancoDePalavras.palavras[
                            (int)(Math.random() * BancoDePalavras.palavras.length)]);
        }
        catch (Exception e)
        {}

        return palavra;
    }
}
