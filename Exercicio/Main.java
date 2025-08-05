/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercicio;

public class Main {
    public static void main(String[] args) {
        
        CartaoCreditoPagamento cc = new CartaoCreditoPagamento("Cartao de Credito");
        cc.processaPagamento(150.0);
        cc.mostraPagamento();

        PIXPagamento pix = new PIXPagamento("PIX");
        pix.processaPagamento(200.0);
        pix.mostraPagamento();

        PayPalPagamento paypal = new PayPalPagamento("PayPal");
        paypal.processaPagamento(300.0);
        paypal.mostraPagamento();
    }
}

