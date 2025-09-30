package conexao;

import DAO.AlunosDAO;
import beans.Aluno;
import DAO.ProfessorDAO;
import beans.Professor;

public class Principal {
    public static void main(String[] args) {
        // Inserindo um Aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Leonardo Correa");
        aluno.setCurso("Engenharia");
        aluno.setIdade(20);
        AlunosDAO alunosDAO = new AlunosDAO();
        alunosDAO.inserir(aluno);
        
        // Inserindo um Professor
        Professor professor = new Professor();
        professor.setNome("Maria Silva");
        professor.setDisciplina("Matematica");
        professor.setIdade(35);
        ProfessorDAO professorDAO = new ProfessorDAO();
        professorDAO.inserir(professor);
        
        System.out.println("Aluno e Professor inseridos com sucesso!");
    }
}
