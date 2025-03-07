package med.voll.api.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/medicos")

public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        //Salvar(novo médicos(atributos))
        var medico = new Medico(dados);
        repository.save(medico);
        //Uri bilder cria URI
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        //201 Created
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> Listar(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao){
        //Paginação de ativos
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        //200 OK
        return ResponseEntity.ok(page);

    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        //Bucar ID
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        //200 OK
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        //Buscar ID
        var medico = repository.getReferenceById(id);
        medico.excluir();
        //204 Delete
        return ResponseEntity.noContent().build();
    }
}
