package beans;

public class Professor {
    
    private int id;
    private String nome;
    private String disciplina;
    private int idade;

    public Professor() {
    }

    public Professor(int id, String nome, String disciplina, int idade) {
        this.id = id;
        this.nome = nome;
        this.disciplina = disciplina;
        this.idade = idade;
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

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
