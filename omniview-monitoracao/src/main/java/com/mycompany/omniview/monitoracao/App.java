/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.omniview.monitoracao;

import com.mycompany.omniview.monitoracao.verificacao.AutenticarLogin;

public class App {

    public static void main(String[] args) {
       

        // Usuario usuario1 = new Usuario(01, "teste@email.com", "teste");
        AutenticarLogin.CriarTabela();
        AutenticarLogin.AbrirLogin();

    }
}
