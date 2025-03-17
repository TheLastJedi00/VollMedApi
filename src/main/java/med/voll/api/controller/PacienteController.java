package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")

public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        //var pacientes armazena novo paciente
        var paciente = new Paciente(dados);
        //Save paciente in DB
        repository.save(paciente);
        //Cria Uri
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();
        //201 Created
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> Listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        //page by ativos
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        //200 OK
        return ResponseEntity.ok(page);
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        //get by ID
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        //200 OK
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        //Buscar id
        var paciente = repository.getReferenceById(id);
        //204 Delete
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        //Buscar id
        var paciente = repository.getReferenceById(id);
        //205 Get paciente by ID
        return ResponseEntity.noContent().build();
    }
}