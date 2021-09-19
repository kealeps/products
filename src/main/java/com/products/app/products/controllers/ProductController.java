package com.products.app.products.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.products.app.productscommons.models.entity.Product;
import com.products.app.products.models.service.IProductService;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private Environment environment;
    /*
    @Value("${server.port}")
    private Integer port;
    */
    
    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    public List<Product> list(){
        return productService.findAll().stream().map(p ->{
            p.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            //p.setPort(port);
            return p;
        }).collect(Collectors.toList());
    }

    @GetMapping("/detail/{id}")
    public Product detail(@PathVariable Long id) throws InterruptedException{
        if(id.equals(10L)){
            throw new IllegalStateException("Producto No encontrado");
        }
        if(id.equals(7L)){
            TimeUnit.SECONDS.sleep(5L);
        }
        Product product =  productService.findById(id);
        product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        //product.setPort(port);
       /* try {
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } */
        return product;
    }
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product){
        return productService.save(product);
    }
    @PutMapping("/save/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product product, @PathVariable Long id){
        Product prod = productService.findById(id);
        prod.setName(product.getName());
        prod.setPrice(product.getPrice());
        return productService.save(prod);
    }
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }
}
