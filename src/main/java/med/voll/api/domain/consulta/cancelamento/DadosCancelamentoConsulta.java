package med.voll.api.domain.consulta.cancelamento;

import jakarta.persistence.GeneratedValue;

public record DadosCancelamentoConsulta(
        @GeneratedValue
        Long idConsulta) {
}
