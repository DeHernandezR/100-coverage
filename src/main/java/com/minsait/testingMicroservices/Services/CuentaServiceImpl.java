package com.minsait.testingMicroservices.Services;

import com.minsait.testingMicroservices.Models.Banco;
import com.minsait.testingMicroservices.Models.Cuenta;
import com.minsait.testingMicroservices.Repositories.BancoRepository;
import com.minsait.testingMicroservices.Repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService{
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private BancoRepository bancoRepository;
    @Override
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta findById(Long idCuenta) {
        return cuentaRepository.findById(idCuenta).orElseThrow();
    }

    @Override
    public Integer revisarTotalTransferencias(Long idBanco) {
        Banco banco=bancoRepository.findById(idBanco).orElseThrow();
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long idCuenta) {
        return cuentaRepository.findById(idCuenta).orElseThrow().getSaldo();
    }

    @Override
    public void transferir(Long idCuentaOrigen, Long idCuentaDestino, BigDecimal monto, Long idBanco) {
        Cuenta origen=cuentaRepository.findById(idCuentaOrigen).orElseThrow();
        origen.retirar(monto);
        cuentaRepository.save(origen);

        Cuenta destino=cuentaRepository.findById(idCuentaDestino).orElseThrow();
        destino.depositar(monto);
        cuentaRepository.save(origen);

        Banco banco=bancoRepository.findById(idBanco).orElseThrow();
        int totalTransferencias=banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.save(banco);
    }

    @Override
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public boolean deleteById(Long idCuenta) {
        Optional<Cuenta> cuenta=cuentaRepository.findById(idCuenta);
        if (cuenta.isPresent()){
            cuentaRepository.deleteById(idCuenta);
            return true;
        }
        return false;
    }
}
