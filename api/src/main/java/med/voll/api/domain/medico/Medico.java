package med.voll.api.domain.medico;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    private boolean ativo;
    //entidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String crm;
    private String telefone;
    //lista de especialidades
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;
    //Construtor da classe Controller
    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        //contrutor na classe endereço
        this.endereco = new Endereco(dados.endereco());
        this.especialidade = dados.especialidade();
        this.telefone = dados.telefone();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {

        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
