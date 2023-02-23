package com.minsait.testingMicroservices.Models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class TransferirDTO {
    private long idCuentaOrigen;
    private long idCuentaDestino;
    private long idBanco;
    private BigDecimal monto;
}
