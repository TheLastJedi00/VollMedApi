# VollMedApi
Implementação da funcionalidade de agendamento de consultas:
Adicionada a lista de validadores na classe AgendaDeConsultas.
Alterado o método cancelar para chamar os validadores e validar o cancelamento.

Criação da interface e classe de validação:
Criada a interface ValidadorCancelamentoDeConsulta e a classe ValidadorHorarioAntecedencia para validar a antecedência do cancelamento.

Resolução de conflitos de nomes:
Renomeados os componentes para evitar conflitos de nomes entre as classes de validação de agendamento e cancelamento.

Ajustes nas consultas:
Alterado o método existsByMedicoIdAndData na interface ConsultaRepository para considerar consultas canceladas.
Atualizada a query no método escolherMedicoAleatorioLivreNaData na interface MedicoRepository para desconsiderar consultas canceladas.
