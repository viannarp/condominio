package br.com.condominiodoscajueiros.admin.repository;

import br.com.condominiodoscajueiros.admin.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByLancamentoIdOrderByDataPagamentoAsc(Long lancamentoId);

    @Query("select coalesce(sum(p.valorPago), 0) from Pagamento p where p.lancamento.id = :lancamentoId")
    BigDecimal somarPagamentosPorLancamento(@Param("lancamentoId") Long lancamentoId);
>>>>>>> 9065793 (Implementa melhorias de segurança, CRUD completo, PDF e relatórios)
}
