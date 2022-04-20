/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.omniview.monitoracao.usuario;

import com.github.britooo.looca.api.core.Looca;
import com.mycompany.omniview.monitoracao.Connection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author mariana.cazzoto
 */
public class RecursosComputador {

    private String processador;
    private Integer bitMaquina;
    private String sistemaOperacional;

    private Integer arquiteturaSis;
    private Double memoriaRam;
    private Double memoriaRamTotal;
    private List processos;
    private Integer quantidadeDiscos;
    private Double cpuTotal;

    Looca looca = new Looca();

    public void informacoesDoSistemaAtual() {
        Connection config = new Connection();
        JdbcTemplate con = new JdbcTemplate(config.getDatasource());

        //Pega o nome do processador
        processador = looca.getProcessador().getNome();

        //Pega quantos bit a maquina tem
        bitMaquina = looca.getSistema().getArquitetura();

        //Pega o sistema operacional da maquina
        sistemaOperacional = looca.getSistema().getSistemaOperacional();

        //Arquitetura do processador
        arquiteturaSis = looca.getSistema().getArquitetura();
        //Memoria ram convertida de bytes para Gigas
        //(RAM EM USO)
        Long memoriaRamByte = looca.getMemoria().getTotal().longValue();
        memoriaRamTotal = memoriaRamByte / 1073741824.0;

        quantidadeDiscos = looca.getGrupoDeDiscos().getQuantidadeDeDiscos();

        //Insert na tabela maquina
        con.update("INSERT INTO MAQUINA (tipo,sistemaOperacional,"
                + "ramTotal,arquitetura,processador,disco,Fk_EstMaq)"
                + "VALUES (null,?,?,?,?,?, 1)",
                sistemaOperacional, memoriaRamTotal,
                arquiteturaSis, processador, quantidadeDiscos);

    }

    public void informacaomemoria() {
        //Pega as informações da memoria a cada 5 segundos
        while (true) {

            // try = tentar , se der erro vai pro catch
            // thread = tarefas 
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TesteRecursos.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(looca.getMemoria());
        }
    }

    public void inserirHostName() {
        Connection config = new Connection();
        JdbcTemplate con = new JdbcTemplate(config.getDatasource());

        try {
            String Inet = InetAddress.getLocalHost().getHostName();
            con.update("UPDATE MAQUINA SET HOSTNAME  = ? WHERE ID = 100", Inet);

        } catch (UnknownHostException ex) {
            Logger.getLogger(RecursosComputador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
     InetAddress.getLocalHost().getHostName()
                    
                    } catch (UnknownHostException ex) {
            Logger.getLogger(AutenticarLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
 insertHostName(){
        InetAddress.getLocalHost().getHostName()
     */
    @Override
    public String toString() {
        return String.format("Processador: %s \n"
                + "Total de bits: %d \n"
                + "Sistema Operacional: %s \n"
                + " \n"
                + "Arquitetura do sistema: x%d "
                + "\n"
                + "-------Memória-------"
                + "\n %.2f", processador, bitMaquina,
                sistemaOperacional, arquiteturaSis, memoriaRamTotal);
    }

}
