package com.minsait.testingMicroservices.Repositories;

import com.minsait.testingMicroservices.Models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Long> {
}
