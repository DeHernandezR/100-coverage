package com.minsait.testingMicroservices.Controllers;

import com.minsait.testingMicroservices.Models.Cuenta;
import com.minsait.testingMicroservices.Models.TransferirDTO;
import com.minsait.testingMicroservices.Services.CuentaService;
import com.minsait.testingMicroservices.exceptions.DineroInsuficienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequestMapping("/api/v1/cuentas")
@Slf4j
@RestController
//@Controller **estos dos o solo rest controller
//@ResponseBody
public class CuentaController{
    @Autowired
    private CuentaService service;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Cuenta> findAll(){
        log.info("Running list method");
        return service.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable Long id){
        try {
            Cuenta cuenta=service.findById(id);
            return ResponseEntity.ok(cuenta);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**@GetMapping("/borrar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteById(@PathVariable Long id){
       if (service.deleteById(id))
            return "Eliminado";
     return "No existe ese id";

    }*/

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta guardar (@RequestBody Cuenta cuenta) {
        return service.save(cuenta);
    }


    //actualizar
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cuenta> actualizar (@PathVariable Long id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaActualizada = service.findById(id);
            cuentaActualizada.setSaldo(cuenta.getSaldo());
            cuentaActualizada.setPersona(cuenta.getPersona());
            return new ResponseEntity<>(service.save(cuentaActualizada), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

     @DeleteMapping("/{id}")
     public ResponseEntity<?> delete (@PathVariable Long id) {
     if (service.deleteById(id))
        return ResponseEntity.noContent().build();
     return ResponseEntity.notFound().build();
     }

    @PostMapping
    public ResponseEntity<?> transferir (@RequestBody TransferirDTO dto){
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("peticion",dto);
        try {
            service.transferir(dto.getIdCuentaOrigen(), dto.getIdCuentaDestino(), dto.getMonto(), dto.getIdBanco());
            response.put("status","OK");
            response.put("mensaje","Transferencia realizada con exito");
        }catch (DineroInsuficienteException exception){
            response.put("status","OK");
            response.put("mensaje", exception.getMessage());
        }catch (NoSuchElementException exception){
            response.put("status","Not Found");
            response.put("mensaje","not found");
        }
        return ResponseEntity.ok(response);
    }
}
