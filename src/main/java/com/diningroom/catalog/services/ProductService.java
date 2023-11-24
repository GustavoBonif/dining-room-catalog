package com.diningroom.catalog.services;

import com.diningroom.catalog.dto.ProductDTO;
import com.diningroom.catalog.entitties.Brand;
import com.diningroom.catalog.entitties.Product;
import com.diningroom.catalog.feingclients.WarehouseFeignClients;
import com.diningroom.catalog.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final WarehouseFeignClients warehouseFeignClients;

    @Autowired
    public ProductService(ProductRepository productRepository, BrandService brandService, WarehouseFeignClients warehouseFeignClients) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.warehouseFeignClients = warehouseFeignClients;
    }

    @Transactional
    public ProductDTO findById(Long id) {
        Product entity = productRepository.findById(id).get();
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        this.checkEmptyRequiredFields(productDTO);

        Brand brand = brandService.findRepositoryById(productDTO.getBrand_id());

        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setBrand(brand);

        if(productDTO.getDescription() != null || !productDTO.getName().isEmpty()) {
            newProduct.setDescription(productDTO.getDescription());
        }

        Product productUpdated = productRepository.save(newProduct);
        ProductDTO productUpdatedDTO = new ProductDTO(productUpdated);

        try{
            warehouseFeignClients.createFromProduct(productUpdated.getId());
        }catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return productUpdatedDTO;
    }

    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("O produto não existe.");
        }
        Product productToUpdate = productRepository.findById(productId).get();

        BeanUtils.copyProperties(productDTO, productToUpdate, this.getNullPropertyNames(productDTO));

        Product productUpdated = productRepository.save(productToUpdate);

        return new ProductDTO(productUpdated);
    }

    @Transactional
    public List<ProductDTO> findAll() {
        List<Product> brands = productRepository.findAll();

        return brands.stream()
                .map(this::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        ProductDTO productDTO = this.findById(id);
        productRepository.deleteById(productDTO.getId());
    }

    @Transactional
    public List<ProductDTO> listProductsByBrand(Long brandId) {

        Brand brand = brandService.findRepositoryById(brandId);
        List<Product> brands = productRepository.findByBrand(brand);

        return brands.stream()
                .map(this::productToProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Product findEntityById(Long id) {
        return productRepository.findById(id).get();
    }

    @Transactional
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    private void checkEmptyRequiredFields(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("O campo 'nome' deve ser preenchido.");
        }

        if (productDTO.getPrice() == null || productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O campo 'preço' é obrigatório e deve ser maior que zero.");
        }

        if (productDTO.getBrand_id() == null || productDTO.getBrand_id() <= 0) {
            throw new IllegalArgumentException("A marca é obrigatória e deve ser válida.");
        }
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private ProductDTO productToProductDTO(Product product) {
        return new ProductDTO(product);
    }

}