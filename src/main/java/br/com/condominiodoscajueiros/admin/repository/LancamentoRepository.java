package br.com.condominiodoscajueiros.admin.repository;

import br.com.condominiodoscajueiros.admin.domain.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByCompetenciaBetween(LocalDate inicio, LocalDate fim);
}
