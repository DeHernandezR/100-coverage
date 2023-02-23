package com.minsait.testingMicroservices.Services;

import com.minsait.testingMicroservices.Models.Cuenta;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


public interface CuentaService {
    List<Cuenta> findAll();
    Cuenta findById(Long idCuenta);
    Integer revisarTotalTransferencias(Long idBanco);
    BigDecimal revisarSaldo(Long idCuenta);
    void transferir(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto, Long idBanco);
    Cuenta save(Cuenta cuenta);
    boolean deleteById(Long idCuenta);
}
