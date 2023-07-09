package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	public List<Product> getAllProduct(Integer pageNumber,String searchKey){
		Pageable pageable = PageRequest.of(pageNumber, 4);
		if(searchKey.equals("")) {
			Page<Product> productPageable = this.productDao.findAll(pageable);
			List<Product> products = productPageable.getContent();
			return products;
		}else {
			return productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
			
		}
		
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
	
	public List<Product> getProductDetails(boolean isSingleCheckout,Integer productId) {
		if(isSingleCheckout) {
//			We are going to buy single product 
			List<Product> list = new ArrayList<>();
			Product product = productDao.findById(productId).get();
			list.add(product);
			return list;
		}else {
//			we are going to checkout entire cart
		}
		return new ArrayList<>();
	}
}
