package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoDisponivel implements ValidadorAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoIndisponivel = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoIndisponivel) {
            throw new ValidacaoException("Médico não está disponível neste horário");

        }
    }

}
