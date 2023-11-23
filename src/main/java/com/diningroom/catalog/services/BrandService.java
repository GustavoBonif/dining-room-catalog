package com.diningroom.catalog.services;

import com.diningroom.catalog.dto.BrandDTO;
import com.diningroom.catalog.entitties.Brand;
import com.diningroom.catalog.repositories.BrandRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repository;

    @Transactional
    public BrandDTO findById(Long id) {
        Brand entity = repository.findById(id).get();
        BrandDTO dto = new BrandDTO(entity);
        return dto;
    }

    @Transactional
    public Brand create(BrandDTO brandDTO) {
        if(this.checkEmptyFields(brandDTO)) {
            throw new IllegalArgumentException("Nem todos os campos foram preenchidos.");
        }

        Brand newBrand = new Brand();
        newBrand.setName(brandDTO.getName());
        return repository.save(newBrand);
    }

    @Transactional
    public void delete(Long id) {
        BrandDTO brandDTO = this.findById(id);
        repository.deleteById(brandDTO.getId());
    }

    @Transactional
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) {

        if(this.checkEmptyFields(brandDTO)) {
            throw new IllegalArgumentException("Nem todos os campos foram preenchidos.");
        }

        Brand brandToUpdate = repository.findById(brandId).get();

        // Copia as propriedades não nulas de brandDTO para brandToUpdate
        BeanUtils.copyProperties(brandDTO, brandToUpdate, "id");

        Brand brandUpdated = repository.save(brandToUpdate);

        BrandDTO brandUpdatedDTO = new BrandDTO(brandUpdated);
        return brandUpdatedDTO;
    }

    @Transactional
    public List<BrandDTO> findAll() {
        List<Brand> brands = repository.findAll();

        return brands.stream()
                .map(this::brandToBrandDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Brand findRepositoryById (Long id) {
        return repository.findById(id).get();
    }

    private BrandDTO brandToBrandDTO(Brand brand) {
        return new BrandDTO(brand);
    }

    private Boolean checkEmptyFields(BrandDTO brandDTO) {
        return brandDTO.getName() == null || brandDTO.getName().isEmpty();
    }
}
