package br.com.condominiodoscajueiros.admin.repository;

import br.com.condominiodoscajueiros.admin.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
