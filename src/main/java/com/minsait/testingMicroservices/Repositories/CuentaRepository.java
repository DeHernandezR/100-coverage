package com.minsait.testingMicroservices.Repositories;

import com.minsait.testingMicroservices.Models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
