package com.diningroom.catalog.controllers;


import com.diningroom.catalog.dto.ProductDTO;
import com.diningroom.catalog.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO productCreatedDTO = service.create(productDTO);
            return new ResponseEntity<>("Sucesso ao criar produto: " + productCreatedDTO.getName() + " - " + productCreatedDTO.getId(), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar produto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>("Sucesso ao deletar produto" , HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listAll")
    public List<ProductDTO> listAllProduct() {
        return service.findAll();
    }

    @PatchMapping(value = "/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO productUpdatedDTO = service.updateProduct(productId, productDTO);
            return new ResponseEntity<>("Produto atualizado com sucesso. ID: " + productUpdatedDTO.getId(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Erro ao atualizar produto: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar produto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listByBrand/{brandId}")
    public ResponseEntity<List<ProductDTO>> listProductsByBrand(@PathVariable Long brandId) {
        List<ProductDTO> productsDTO = service.listProductsByBrand(brandId);
        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }
}
