package Pasta;

import java.net.*;
import java.io.*;

public class Cliente3 {
    public static final String HOST_PADRAO = "localhost";
    public static final int PORTA_PADRAO = 3000;

    public static void main(String[] args) throws Exception {
        if (args.length > 2) {
            System.err.println("Uso esperado: java Pasta.Cliente [HOST [PORTA]]\n");
            return;
        }

        Socket conexao = null;
        try {
            String host = Cliente.HOST_PADRAO;
            int porta = Cliente.PORTA_PADRAO;

            if (args.length > 0)
                host = args[0];

            if (args.length == 2)
                porta = Integer.parseInt(args[1]);

            conexao = new Socket(host, porta);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectOutputStream transmissor = null;
        try {
            transmissor =
                    new ObjectOutputStream(
                            conexao.getOutputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor = null;
        try {
            receptor =
                    new ObjectInputStream(
                            conexao.getInputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor = null;
        try {
            servidor =
                    new Parceiro(conexao, receptor, transmissor);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        TratadoraDeComunicadoDeDesligamento tratadoraDeComunicadoDeDesligamento = null;
        try {
            tratadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
        } catch (Exception erro) {
        } // sei que servidor foi instanciado

        tratadoraDeComunicadoDeDesligamento.start();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ComunicadoDeDados dadosDaForca = null;      //Instancio classes que vou usar globalmente
        ComunicadoDaVez vezDeQuem = null;
        Comunicado comunicado = null;

        boolean perdeu = false;                     //Boolean perdeu, caso o cliente perca a variável fica true

        try {
            System.out.println("Esperando 3 jogadores se conectar...");

            do {
                comunicado = (Comunicado) servidor.espie();

            }
            while (!(comunicado instanceof ComunicadoDeDados));    //Servidor fica espiando para ver se não tem um comunicadoDeDados
            comunicado = servidor.envie();                         //Envia comunicado do servidor
            dadosDaForca = (ComunicadoDeDados) comunicado;         //Typecast
            System.out.println(dadosDaForca.getPalavra());         //Printando a palavra só para ter certeza


        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }

        try {

            do {
                comunicado = (Comunicado) servidor.espie();

            }
            while (!(comunicado instanceof ComunicadoComecar));                      //Servidor fica espiando para ver se não tem um ComunicadoComecar
            comunicado = servidor.envie();                                           //Envia comunicado do servidor
            ComunicadoComecar comunicadoComecar = (ComunicadoComecar) comunicado;    //Typecast o comunicado vira o comunicadoComecar

            System.out.println("Você é o jogador n° " + comunicadoComecar.getPos()); //pegamos a posição em que o usuário entra e printamos

            //vezDeQuem = new ComunicadoDaVez(comunicadoComecar.getPos(),dadosDaForca);
        } catch (Exception erro) {
            System.out.println(erro.getMessage());
        }

        do {                                                        //Enquanto o usuário não perdeu ele fica nesse do/while

            comunicado = null;
            do {
                comunicado = (Comunicado) servidor.espie();

            }
            while (!(comunicado instanceof ComunicadoDaVez ) &&     //Servidor fica  espiando para ver se não tem um ComunicadoDaVez
                    !(comunicado instanceof ComunicadoDeVitoria) &&  //E um ComunicadoDeVitoria
                    !(comunicado instanceof ComunicadoDeDerrota));   //E um ComunicadoDeDerrota

            if(comunicado instanceof ComunicadoDaVez){              //Se tiver um comunicado de ComunicadoDaVez
                vezDeQuem = (ComunicadoDaVez) servidor.envie();     //Enviamos do servidor de quem é a vez
                dadosDaForca = vezDeQuem.getComunicadoDeDados();    //O usuário que está em sua vez recebe os dados (Ex:tracinhos, letrasJaDigitadas etc)

            }
            else if(comunicado instanceof ComunicadoDeVitoria){     //Se tiver um comunicado de ComunicadoDeVitoria exibimos as devidas mensagens
                System.out.println("Você ganhou!!!");
                System.out.println("A palavra realmente era..." + dadosDaForca.getPalavra());
                try
                {
                    servidor.receba(new PedidoParaSair());          //O servidor recebe um pedido de saida, pois teve um ganhador e o jogo está encerrado
                }
                catch (Exception error){}
                System.exit(0);                               //Tiramos o cliente
            }
            else{                                                   //Se não for um comunicadoDeVitoria e sim um de derrota exibimos as devidas mensagens
                System.err.println("Você perdeu!!!");
                System.out.println("A palavra era..." + dadosDaForca.getPalavra());
                try
                {
                    servidor.receba(new PedidoParaSair());          //O servidor recebe um pedido de saida, pois teve um perdedor e ele precisa ser retirado de jogo
                }
                catch (Exception error){}
                System.exit(0);                               //Tiramos o cliente
            }

            System.out.println("Palavra...:" + dadosDaForca.getTracinhos());      //Prints sobre o jogo
            System.out.println("Digitadas.:" + dadosDaForca.getControladorDeLetrasJaDigitadas());

            System.out.println("Você deseja dizer a letra ou chutar a palavra?" + //Printamos para o usuário se ele quer dizer um letra ou chutar a palavra
                    " 1 - letra e 2 - palavra");
            byte resposta = Teclado.getUmByte();    //Pegamos a resposta do usuário

            if(resposta == 1){                      //Se o usuário digitou 1 ele pode dizer aletra
                System.out.println("Ótimo, diga a letra");
                try
                {
                    System.out.print   ("Qual letra? ");
                    char letra = Character.toUpperCase (Teclado.getUmChar()); //Pegamos a letra que esse usuário informou

                    servidor.receba(new PedidoDeLetra(letra,dadosDaForca));   //Servidor recebe um novo pedidoDeLetra com a letra que ele passou por parãmetro
                    comunicado = null;                                        //E os dadosDaForca atualizados

                    do {
                        comunicado = (Comunicado) servidor.espie();

                    }
                    while ( !(comunicado instanceof ComunicadoDeLetraJaDigitada ) &&  //Servidor fica espiando para ver se não tem um ComunicadoDeLetraJaDigitada
                            !(comunicado instanceof ComunicadoDeLetraInexistente) &&  //E um ComunicadoDeLetraInexistente
                            !(comunicado instanceof ComunicadoDeAcerto) &&            //E um ComunicadoDeAcerto
                            !(comunicado instanceof ComunicadoDeVitoria));            //E um ComunicadoDeVitoria

                    comunicado = servidor.envie();                                    //Envia comunicado do servidor

                    if(comunicado instanceof ComunicadoDeLetraJaDigitada){            //Se tiver um comunicado de ComunicadoDeLetraJaDigitada
                        System.out.println("Letra ja foi digitada");                  //Printamos que a letra ja foi digitada
                    }
                    else if (comunicado instanceof ComunicadoDeLetraInexistente){     //Ou se tiver um comunicado de ComunicadoDeLetraInexistente
                        System.err.println("Letra inexistente");                      //Printamos que a letra não existe

                    }else if (comunicado instanceof ComunicadoDeAcerto){              //Ou se tiver um comunicado de ComunicadoDeAcerto
                        System.out.println("Você acertou");                           //Printamos um acerto

                    }else if (comunicado instanceof ComunicadoDeVitoria){             //Ou se tiver um comunicado de ComunicadoDeVitoria
                        System.out.println("Você ganhou");
                        System.out.println("A palavra realmente era..." + dadosDaForca.getPalavra());
                        try
                        {
                            servidor.receba(new PedidoParaSair());                    //O servidor recebe um pedido de saida pois teve um ganhador e o jogo está encerrado
                        }
                        catch (Exception error){}
                        System.exit(0);
                    }
                    servidor.receba(vezDeQuem);
                }
                catch (Exception erro) {System.err.println (erro.getMessage());}
            }

            else if(resposta == 2){                                         //Caso ele opte por chutar a palavra
                System.out.println("Ótimo, diga a palavra");

                String chute = Teclado.getUmString();                       //Pegamos a palavra que esse usuário informou
                servidor.receba(new PedidoDePalavra(chute,dadosDaForca));   //Servidor recebe um novo pedidoDePalavra com a palavra que ele passou por parâmetro
                //E os dadosDaForca atualizados
                do {
                    comunicado = (Comunicado) servidor.espie();

                }
                while (!(comunicado instanceof ComunicadoDeVitoria ) &&    //Servidor fica espiando para ver se não tem um ComunicadoDeLetraJaDigitada
                        !(comunicado instanceof ComunicadoDeDerrota));     //E um ComunicadoDeDerrota

                if(comunicado instanceof ComunicadoDeVitoria){             //Se tiver um comunicado de ComunicadoDeVitoria exibimos as devidas mensagens
                    System.out.println("Você ganhou");
                    System.out.println("A palavra realmente era..." + dadosDaForca.getPalavra());
                    try
                    {
                        servidor.receba(new PedidoParaSair());             //O servidor recebe um pedido de saida pois teve um ganhador e o jogo está encerrado
                    }
                    catch (Exception error){}
                    System.exit(0);                                  //Tiramos o cliente
                }
                else if(comunicado instanceof ComunicadoDeDerrota){        //Se não for um comunicadoDeVitoria e sim um de derrota exibimos as devidas mensagens
                    servidor.receba(vezDeQuem);
                    System.err.println("Você Perdeu");
                    System.out.println("A palavra era..." + dadosDaForca.getPalavra());
                    try
                    {
                        servidor.receba(new PedidoParaSair());             //O servidor recebe um pedido de saida pois teve um perdedor e ele precisa ser retirado de jogo
                    }
                    catch (Exception error){}
                    System.exit(0);                                  //Tiramos o cliente
                }
            }
            else{                                                          //Se o número que o usuario digitou não for 1 e nem dois
                System.err.println("Número inválido");
            }

            //Mandamos os dadosDaForca atualizados para o usuário para ele ver as letrasJaDigitadas e os tracinhos preenchidos ou não
            do {
                comunicado = (Comunicado) servidor.espie();

            }
            while (!(comunicado instanceof ComunicadoDeDados));
            comunicado = servidor.envie();
            dadosDaForca = (ComunicadoDeDados) comunicado;
            servidor.receba(dadosDaForca);

            System.out.println("Palavra...:" + dadosDaForca.getTracinhos());
            System.out.println("Digitadas.:" + dadosDaForca.getControladorDeLetrasJaDigitadas());
        }while(!perdeu);
    }
}