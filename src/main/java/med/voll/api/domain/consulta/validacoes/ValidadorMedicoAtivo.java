package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas {

    @Autowired
    private MedicoRepository repository;

    public ValidadorMedicoAtivo(MedicoRepository repository) {
        this.repository = repository;

    }

    public void validar(DadosAgendamentoConsulta dados) {
        //optional
        if (dados.idMedico()==null) {
            return;
        }        
        var medicoAtivo = repository.findAtivoById(dados.idMedico());
        
        if (!medicoAtivo) {
            throw new ValidacaoException("Médico não está ativo");
        }
    }
    
}
