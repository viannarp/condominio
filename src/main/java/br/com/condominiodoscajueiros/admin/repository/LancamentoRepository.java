package br.com.condominiodoscajueiros.admin.repository;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
=======
import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByCompetenciaBetween(LocalDate inicio, LocalDate fim);
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
}
