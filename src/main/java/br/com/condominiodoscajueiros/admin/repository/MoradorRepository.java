package br.com.condominiodoscajueiros.admin.repository;

import br.com.condominiodoscajueiros.admin.domain.Morador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoradorRepository extends JpaRepository<Morador, Long> {
}
