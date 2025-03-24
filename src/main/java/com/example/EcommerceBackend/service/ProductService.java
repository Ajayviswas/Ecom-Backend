package com.example.EcommerceBackend.service;


import com.example.EcommerceBackend.model.Product;
import com.example.EcommerceBackend.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;


    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }


    @Cacheable(value = "products",key = "#id")
    public Product getProductById(int id){
        return productRepo.findById(id).orElse(null);
    }

    @CachePut(value = "products" , key ="#product.id")
    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }

    @CachePut(value = "products" , key ="#product.id")
    public Product updateProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);
    }

    @CacheEvict(value = "products" , key="#id")
    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }
}
