package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    //entidade
    private String logradouro;
    private String  bairro;
    private String  cep;
    private String cidade;
    private String uf;
    private String complemento;
    private String numero;
    //contrutor na classe controller
    public Endereco(DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.bairro = dados.bairro();
        this.cep = dados.cep();
        this.cidade = dados.cidade();
        this.uf = dados.uf();
        this.complemento = dados.complemento();
        this.numero = dados.numero();


    }

    public void atualizarInformacoes(DadosEndereco dados) {
        if(dados.logradouro() != null){
            this.logradouro = dados.logradouro();
        }
        if(dados.bairro() != null){
            this.bairro = dados.logradouro();
        }
        if(dados.cep() != null){
            this.cep = dados.logradouro();
        }
        if(dados.cidade() != null){
            this.cidade = dados.logradouro();
        }
        if(dados.uf() != null){
            this.uf = dados.logradouro();
        }
        if(dados.complemento() != null){
            this.complemento = dados.logradouro();
        }
        if(dados.numero() != null){
            this.numero = dados.logradouro();
        }
    }
}
