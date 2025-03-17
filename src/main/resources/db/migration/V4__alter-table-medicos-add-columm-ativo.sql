alter table medicos add ativo tinyint;
update medicos set ativo = 1 where ativo is null;
