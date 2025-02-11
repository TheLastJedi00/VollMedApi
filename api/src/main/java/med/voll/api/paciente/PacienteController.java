package med.voll.api.paciente;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")

public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional

    public void paciente(@RequestBody @Valid DadosCadastroPaciente dados){
        repository.save(new Paciente(dados));
    }
}
