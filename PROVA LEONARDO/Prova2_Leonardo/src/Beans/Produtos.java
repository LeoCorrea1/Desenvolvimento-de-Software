package Beans;

public class Produtos {
    
    int id;
    String nome;
    Float Preco;
    int saldo;

    public Produtos(int id, String nome, Float Preco, int saldo) {
        this.id = id;
        this.nome = nome;
        this.Preco = Preco;
        this.saldo = saldo;
    }

    public Produtos(){
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPreco() {
        return Preco;
    }

    public void setPreco(Float Preco) {
        this.Preco = Preco;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    
}
