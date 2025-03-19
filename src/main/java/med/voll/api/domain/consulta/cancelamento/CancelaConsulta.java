package med.voll.api.domain.consulta.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.validacoes.StatusConsulta;

@Service
public class CancelaConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;

    public CancelaConsulta(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new ValidacaoException("Consulta já cancelada!");
        }
        if (consulta.getId() != dados.idConsulta()) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

}
