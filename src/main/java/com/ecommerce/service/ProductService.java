package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Product;




@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	
	public Product addNewProduct(Product product, MultipartFile[] file) {
		Product newProduct = productDao.save(product);
		
		return newProduct;
		
	}
	
	public List<Product> getAllProduct() {
		List<Product> products= this.productDao.findAll();
		return products;
	}
	
	public void deleteProductById(Integer productId) {
		 this.productDao.deleteById(productId);
	}
	
	public void updateProduct(Integer productId,Product product) {
		Optional<Product> DbProduct = this.productDao.findById(productId);
	
	}
	public Product getProductDetailsById(Integer productId) {
		Product product = productDao.findById(productId).get();
		return product;
	}
}
