package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroPaciente(String  nome, String  email, DadosEndereco endereco, String cpf, String rg) {
}
