package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsultas {
    
    @Autowired
    private PacienteRepository repository;
    
    public void validar(DadosAgendamentoConsulta dados) {
        
        var pacienteAtivo = repository.findAtivoById(dados.idPaciente());
        
        if (!pacienteAtivo) {
            throw new ValidacaoException("Paciente inativo");
        }
    }
}
