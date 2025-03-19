package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import java.time.DayOfWeek;
import org.springframework.stereotype.Component;

@Component
public class ValidadorHorario implements ValidadorAgendamentoConsultas {
    public void validar(DadosAgendamentoConsulta dados){
        // Lógica para validar horário
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var sabado = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
        var cedoDemais = dataConsulta.getHour() < 7;
        var tardeDemais = dataConsulta.getHour() > 18;

        if (tardeDemais || cedoDemais || domingo || sabado) {
            throw new ValidacaoException("Horário escolhido está fora do expediente");
        }
    }
}
