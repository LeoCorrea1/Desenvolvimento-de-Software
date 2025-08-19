/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author laboratorio
 */
public class Pessoa {
    public String Nome;
    public String sexo;
    public String Idioma;

    public Pessoa(String Nome, String sexo, String Idioma) {
        this.Nome = Nome;
        this.sexo = sexo;
        this.Idioma = Idioma;
    }

    public Object[] obterDados(){
        return new Object[] {Nome,sexo,Idioma};
    }
    
    
    
}
