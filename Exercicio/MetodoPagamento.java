/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercicio;

/**
 *
 * @author laboratorio
 */
abstract class MetodoPagamento {
    
    public String nomeMetodo ;
    
    public String idPagamento;

public MetodoPagamento(String nomeMetodo) {
    this.nomeMetodo = nomeMetodo;
    this.idPagamento = nomeMetodo + "23123";
    System.out.println("idPagamento gerado : " + idPagamento);
}
   
    public void processaPagamento(double valor){
         System.out.println("Pagamento processado ! valor igual a : " +valor );
    }
    
    public void mostraPagamento(){
        System.out.println("nome do metodo : "+nomeMetodo);
        System.out.println("id do Pagamento : " +idPagamento);
    }
    
    
}
