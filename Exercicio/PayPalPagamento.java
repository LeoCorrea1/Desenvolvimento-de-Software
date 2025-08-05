/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercicio;

/**
 *
 * @author laboratorio
 */
public class PayPalPagamento extends MetodoPagamento{
    
    public String nomePag = "PAY PAL";
    public double valor;

    public PayPalPagamento(String nomeMetodo) {
        super(nomeMetodo);
    }
    
     @Override
     public void processaPagamento(double valor){
         System.out.println("Pagamento processado ! valor igual a : " +valor );
    }
    
    @Override
    public void mostraPagamento(){
        System.out.println("nome do metodo : "+nomePag);
        System.out.println("id do Pagamento : " +idPagamento);
    }
    
}
