package com.diningroom.catalog.controllers;

import com.diningroom.catalog.dto.BrandDTO;
import com.diningroom.catalog.entitties.Brand;
import com.diningroom.catalog.services.BrandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brands")
public class BrandController {

    @Autowired
    private BrandService service;
    @GetMapping(value = "/{id}")
    public BrandDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public ResponseEntity<String> createBrand(@RequestBody BrandDTO brandDTO) {
        try {
            Brand brandCreated = service.create(brandDTO);
            return new ResponseEntity<>("Sucesso ao criar a marca: " + brandCreated.getName() + " - " + brandCreated.getId(), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar a marca: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>("Sucesso ao deletar a marca" , HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listAll")
    public List<BrandDTO> createBrand() {
        return service.findAll();
    }

    @PatchMapping(value = "/update/{brandId}")
    public ResponseEntity<String> updateBrand(@PathVariable Long brandId, @RequestBody BrandDTO brandDTO) {
        try {
            BrandDTO brandUpdatedDTO = service.updateBrand(brandId, brandDTO);
            return new ResponseEntity<>("Marca atualizada com sucesso. ID: " + brandUpdatedDTO.getId(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Erro ao atualizar a marca: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar a marca: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
