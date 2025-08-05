/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercicio2;

/**
 *
 * @author laboratorio
 */
public class Main implements ICalculadora{
    
    public static void main(String[] args) {
        
    }

    @Override
    public double somar(double n1, double n2) {
        return n1 + n2;
       
    }

    @Override
    public double subtrair(double n1, double n2) {
       return n1 - n2;
    }

    @Override
    public double multiplicar(double n1, double n2) {
        return n1 * n2;
    }

    @Override
    public double dividir(double n1, double n2) {
         return n1 / n2;
    }

 
    
}
