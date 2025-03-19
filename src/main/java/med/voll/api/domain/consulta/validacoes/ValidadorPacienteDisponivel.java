package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteDisponivel {

    @Autowired
    private ConsultaRepository consultaRepository;

    public ValidadorPacienteDisponivel(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }
    
    public void validar(DadosAgendamentoConsulta dados) {
        var primeiroHorario = dados.data().withHour(8);
        var ultimoHorario = dados.data().withHour(18);
        var pacienteIndisponivel = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
    
        if (pacienteIndisponivel) {
            throw new ValidacaoException("Paciente já tem consulta agenadeada para a data");
        }
        
    }
}