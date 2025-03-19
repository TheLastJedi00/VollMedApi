package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorAntecedencia implements ValidadorAgendamentoConsultas {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var dataAtual = LocalDateTime.now();
        var diferencaMinutos = Duration.between(dataAtual, dataConsulta).toMinutes();

        if (diferencaMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com no mínimo 30 minutos de antecedência");
        }
    }
}
