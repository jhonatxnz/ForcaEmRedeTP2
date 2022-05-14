package Pasta;

import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private ComunicadoDeDados dadosDaForca;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;
    //variaveis or objetos
    private int                 qtdJogador = 0;
    private int jogadorDaVez = -1;


    public SupervisoraDeConexao
            (Socket conexao, ArrayList<Parceiro> usuarios)
            throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor =
                    new ObjectOutputStream(
                            this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor=
                    new ObjectInputStream(
                            this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }

        try
        {
            this.usuario =
                    new Parceiro (this.conexao,
                            receptor,
                            transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try
        {
            synchronized (this.usuarios) //aguarda todods os usuarios entrar
            {
                this.usuario.setId(this.usuarios.size()); //calcula quantidade de usuario que sera guardado no arraylist usuarios
                this.usuarios.add (this.usuario); //adiciona usuarios no arraylist

                if(this.usuarios.size() % 3 == 0){ //se o resto da divisao dos usuarios por 3 for igual a
                    //0 significa que foi formado um grupo de tres jogadores

                    qtdJogador = this.usuarios.size(); //a variável qtdJogador recebe o tamanho da arraylist


                    int jogador1 = qtdJogador-3; //para ele ficar na posição 0 do vetor pq ele foi o primeiro a entrar
                    int jogador2 = qtdJogador-2; //para ele ficar na posição 1 do vetor pq ele foi o segundo a entrar
                    int jogador3 = qtdJogador-1; //para ele ficar na posição 2 do vetor pq ele foi o terceiro a entrar

                    dadosDaForca = new ComunicadoDeDados(jogador1, jogador2, jogador3); //instanciamos o objeto dadosDaForca do tipo ComunicadoDeDados
                    // passando como parâmetro os jogadores

                    //os usuários receberão o objeto dadosDaForca que herda da classe ComunicadoDeDados
                    this.usuarios.get(qtdJogador-1).receba(dadosDaForca);
                    this.usuarios.get(qtdJogador-2).receba(dadosDaForca);
                    this.usuarios.get(qtdJogador-3).receba(dadosDaForca);

                    this.usuarios.get(qtdJogador-3).receba(new ComunicadoComecar(dadosDaForca,0)); //o usuario na posicao qtdJogador-3(jogador1) recebe um comunicadoComeçar, os dadosDaForca atualizados e a posição 0 do vetor
                    this.usuarios.get(qtdJogador-2).receba(new ComunicadoComecar(dadosDaForca,1)); //o usuario na posicao qtdJogador-2(jogador2) recebe um comunicadoComeçar, os dadosDaForca atualizados e a posição 1 do vetor
                    this.usuarios.get(qtdJogador-1).receba(new ComunicadoComecar(dadosDaForca,2)); //o usuario na posicao qtdJogador-1(jogador3) recebe um comunicadoComeçar, os dadosDaForca atualizados e a posição 2 do vetor

                    usuarios.get(0).receba(new ComunicadoDaVez(0,this.usuarios.size(),dadosDaForca)); //o usuário da posição 0 do vetor recebe um
                    // novo ComunicadoDaVez, nos colocamos o this.usuários.size()
                    //para que seja controlado a quantidade de jogadores que esta
                    //em jogo e os dadosDaForca atualizado
                }
            }

            for(;;) //forever
            {
                Comunicado comunicado = this.usuario.envie(); // o objeto comunicado da classe Comunicado sera enviado ao servidor

                if (comunicado==null)
                    return;
                    //-------------------------------------------LETRA-------------------------------------------------------//
                else if (comunicado instanceof PedidoDeLetra)// se o servidor receber um comunicado de PedidoDeLetra
                {
                    PedidoDeLetra pedidoDeLetra = (PedidoDeLetra)comunicado;//variavel pedido de letra recebe o comunicado que
                    // esta na classe pedido de letra

                    dadosDaForca = ((PedidoDeLetra)comunicado).getComunicadoDeDados(); //dadosDaForca recebera o comunicado de pedido de letra
                    // e o que esta contido no ComunicadoDeDados

                    int winner = this.usuarios.indexOf(usuario); //a variavel winner do tipo int recebera o valor que esta
                    // contido na arraylist usuarios do
                    //indice usuario passado como parametro

                    if (dadosDaForca.getControladorDeLetrasJaDigitadas().isJaDigitada (pedidoDeLetra.getLetra())) //se caso a letra ja tenha sido digitada
                    {
                        usuario.receba(new ComunicadoDeLetraJaDigitada()); //o usuario recebera um comunicado sendo informado de que aquela letra ja foi digitada

                    }
                    else
                    {
                        dadosDaForca.getControladorDeLetrasJaDigitadas().registre (pedidoDeLetra.getLetra());//a letra sera registrada no controlador
                        // de letras

                        int qtd = dadosDaForca.getPalavra().getQuantidade (pedidoDeLetra.getLetra()); //variavel qtd soma a quantidade de letra
                        // que tem na palavra

                        if (qtd==0) //caso o usuario digite uma letra e a quantidade nela na palavra seja 0
                        {
                            dadosDaForca.getControladorDeErros().registreUmErro (); //um erro sera contabilizado,
                            usuario.receba(new ComunicadoDeLetraInexistente()); //o usuario recebera um novo comunicado de letra inexistente
                        }
                        else // e caso a quantidade nao seja igual a 0
                        {
                            for (int i=0; i<qtd; i++) //sera feita uma contagem de quantos caracteres da letra que usuario escolheu existe na palavra
                            {
                                int posicao = dadosDaForca.getPalavra().getPosicaoDaIezimaOcorrencia (i,pedidoDeLetra.getLetra()); //a variavel posicao faz a
                                // verificacao da posicao da letra escolhida na palavra

                                dadosDaForca.getTracinhos().revele (posicao, pedidoDeLetra.getLetra()); //e aqui é revelado a letra e a posicao dela na palavra
                            }
                            System.out.println (); //print vazio para dar espaço no console
                            //////////////////////////////////////////////////////////////
                            if(!dadosDaForca.getTracinhos().isAindaComTracinhos()){ //se caso a palavra nao retorne mais tracinhos
                                usuario.receba(new ComunicadoDeVitoria()); //o usuário recebera um comunicado de vitoria
                                switch (winner){ //aqui fizemos um switch com algumas condicoes
                                    case 0: //se caso o jogador da posição vetorial 0 receba o comunicado de vitoria
                                        this.usuarios.get(1).receba(new ComunicadoDeDerrota()); //o usuario da posicao 1 do vetor recebe um comunicado de derrota
                                        this.usuarios.get(2).receba(new ComunicadoDeDerrota()); //o usuario da posicao 2 do vetor recebe um comunicado de derrota
                                        break;
                                    case 1: //caso o jogador 1 da posição vetorial receba o comunicado de vitoria
                                        this.usuarios.get(0).receba(new ComunicadoDeDerrota()); //o usuario da posicao 0 do vetor recebe um comunicado de derrota
                                        this.usuarios.get(2).receba(new ComunicadoDeDerrota()); //o usuario da posicao 2 do vetor recebe um comunicado de derrota
                                        break;
                                    case 2:// caso o jogador 2 da posicao vetorial receba o comunicado de vitoria
                                        this.usuarios.get(0).receba(new ComunicadoDeDerrota()); //o usuario da posicao 0 do vetor recebe um comunicado de derrota
                                        this.usuarios.get(1).receba(new ComunicadoDeDerrota()); //o usuario da posicao 1 do vetor recebe um comunicddo de derrota
                                        break;
                                }
                            }
                            else { //e caso a palavra ainda tenha tracinhos
                                usuario.receba(new ComunicadoDeAcerto()); //o jogador recebera apenas um comunicado que ele acertou a letra
                            }
                        }
                    }
                }
                //-------------------------------------------PALAVRA-------------------------------------------------------//

                else  if(comunicado instanceof PedidoDePalavra){  // agora, caso o usuario digite 2 e escolha advinhar qual é a palavra
                    PedidoDePalavra pedidoDePalavra = (PedidoDePalavra) comunicado; //variavel pedido de palavra recebe o comunicado que
                    // esta na classe PedidoDePalavra

                    dadosDaForca = ((PedidoDePalavra)comunicado).getComunicadoDeDados(); //antes de enviar o pedido é necessario atualizar os dados da forca
                    int winner = this.usuarios.indexOf(usuario);//variavel recebera o valor do indice que recebe a sequencia dos jogadores

                    if(pedidoDePalavra.getPalavra().toUpperCase().equals(dadosDaForca.getPalavra().toString())){ //se caso a palavra seja igual a que
                        // esta guardada no banco de palavras
                        usuario.receba(new ComunicadoDeVitoria()); //o usuario recebe um comunicado de vitoria
                        switch (winner){ //aqui temos o mesmo esquema da letra
                            case 0: // se caso o usuario da posicao vetorial 0 receber o comunicado de vitoria
                                this.usuarios.get(1).receba(new ComunicadoDeDerrota()); //o usuario da posicao 1 do vetor recebe um comunicado de derrota
                                this.usuarios.get(2).receba(new ComunicadoDeDerrota()); //e o usuario da posicao 2 do vetor recebe um comunicado de derrota
                                break;
                            case 1: // se caso o usuario da posicao vetorial 1 receber um comunicado de vitoria
                                this.usuarios.get(0).receba(new ComunicadoDeDerrota()); //o usuario da posicao 0 do vetor recebe um comunicado de derrota
                                this.usuarios.get(2).receba(new ComunicadoDeDerrota()); // e o usuario da posicao 2 do vetor recebe um comunicaddo de derrota
                                break;
                            case 2: //e se caso o jogador da posicao vetorial 2 receba um comunicado de vitoria
                                this.usuarios.get(0).receba(new ComunicadoDeDerrota()); //o jogador da posicao 0 do vetor recebe um comunicado de derrota
                                this.usuarios.get(1).receba(new ComunicadoDeDerrota()); //e o jogador da posicao 1 do vetor recebe um comunicado de derrota
                                break;
                        }
                    }
                    else{ //e se caso o usuario erre a palavra
                        usuario.receba(new ComunicadoDeDerrota()); //ele recebera um comunicado de derrota e sera retirado do jogo

                        if(this.usuarios.size() == 2){ //neste caso se 2 jogadores receberem um comunicado de derrota
                            this.usuarios.get(1).receba(new ComunicadoDeVitoria()); //o jogador que restar recebera um comunicado de vitória
                        }
                    }
                }

                else if(comunicado instanceof ComunicadoDeDados) {
                    dadosDaForca = (ComunicadoDeDados)comunicado; //Typecast, o comunicado vira o comunicadoDeDados

                }
                else if(comunicado instanceof PedidoDeDados) {

                    usuario.receba((ComunicadoDeDados) dadosDaForca);//fizemos uma conversao e o usuario recebe um com. de dados
                }

                else if(comunicado instanceof ComunicadoDaVez){ //ou se caso seja a vez de um outro jogador e
                    // ele tenha que receber um comunicado da vez

                    dadosDaForca = new ComunicadoDeDados(((ComunicadoDaVez) comunicado).getComunicadoDeDados());//dadosDaForca recebe
                    // comunicadoDeDados com a vezDeQuem e os dados
                    ComunicadoDaVez comunicadoDaVez = ((ComunicadoDaVez) comunicado); //typecast
                    this.usuario.receba(dadosDaForca); // usuario recebe os dados da forca
                    comunicadoDaVez.setQtdJogadores(this.usuarios.size());//atualiza a posição dos jogadores
                    comunicadoDaVez.setJogadorDaVez(); //atualiza qual é o jogador da vez
                    usuarios.get(comunicadoDaVez.getJogadorDaVez()).receba(comunicadoDaVez);//o usuario recebera ComunicadoDaVez

                }

                else if (comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove (this.usuario);
                    }
                    this.usuario.adeus();
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor   .close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}