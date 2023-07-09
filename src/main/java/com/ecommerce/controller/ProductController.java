package com.ecommerce.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.entity.ImageModel;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
//	@PreAuthorize("hasRole('Admin')")
	@PostMapping(value = "/addNewProduct" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Product addNewProduct( @RequestPart("product") Product product,
								@RequestPart("imageFile") MultipartFile[] file) {
		
		try {
			Set<ImageModel> uploadImage = uploadImage(file);
			product.setProductImages(uploadImage);
			Product addNewProduct = productService.addNewProduct(product,file);
			return addNewProduct;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	@GetMapping("/allProducts")
	public List<Product> getAllProduct(@RequestParam(defaultValue="0") Integer pageNumber,
			@RequestParam(defaultValue="")String searchKey
			){
		List<Product> allProduct = this.productService.getAllProduct(pageNumber,searchKey);
		System.out.println("size of result" +allProduct.size() );
		return allProduct;
	}
//	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deleteProducts/{productId}")
	public void DeleteProductById(@PathVariable Integer productId) {
		this.productService.deleteProductById(productId);
	}
	
	
	public Set<ImageModel> uploadImage(MultipartFile [] multiPartFiles)throws IOException {
		Set<ImageModel> imageModels = new HashSet<>();
		
		for (MultipartFile file : multiPartFiles) {
			ImageModel imageModel = new ImageModel(
					file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes()
					);
			imageModels.add(imageModel);
		}
		return imageModels;
		
	}
	
	@PutMapping("editProduct/{productId}")
	public void updateProduct(@RequestBody Product product,@PathVariable Integer productId) {
		
	}
	@GetMapping("/getProductDetailsById/{productId}")
	public Product getProductDetailsById(@PathVariable("productId") Integer productId) {
		Product productDetailsById = this.productService.getProductDetailsById(productId);
		return productDetailsById;
	}
	
//	@PreAuthorize("hasRole('User')")
	@GetMapping("/getProductDetails/{isSingleProductCheckout}/{productId}")
	public List<Product> getProductDetails( @PathVariable("isSingleProductCheckout")boolean isSingleProductCheckout,@PathVariable("productId") Integer productId) {
		 List<Product> productDetails = this.productService.getProductDetails(isSingleProductCheckout, productId);
		 return productDetails;
	}
}
