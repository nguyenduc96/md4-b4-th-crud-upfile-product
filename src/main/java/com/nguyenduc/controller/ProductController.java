package com.nguyenduc.controller;

import com.nguyenduc.model.Product;
import com.nguyenduc.model.ProductForm;
import com.nguyenduc.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping
    public ModelAndView showAllList(@RequestParam(name = "q", required = false) String name) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Product> products;
        if (name == null || name.equals("")) {
            products=productService.findAll();
        } else {
            products = productService.findByName(name);
            if (products.size() == 0) {
                modelAndView.addObject("message", "Not found!!!!");
            }
        }
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        return new ModelAndView("create", "productForm", new ProductForm());
    }

    @PostMapping("/create")
    public ModelAndView createProduct(@ModelAttribute ProductForm productForm) {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = new Product(
                productForm.getId(),
                productForm.getName(),
                productForm.getDescription(),
                productForm.getPrice(),
                fileName
        );
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("message", "Created new product successfully !");
        return modelAndView;
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute ProductForm productForm) {
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = new Product(
                productForm.getId(),
                productForm.getName(),
                productForm.getDescription(),
                productForm.getPrice(),
                fileName
        );
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showFormEdit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.findById(id);
        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId());
        productForm.setDescription(product.getDescription());
        productForm.setName(product.getName());
        productForm.setPrice(product.getPrice());
        modelAndView.addObject("productForm", productForm);
        return modelAndView;
    }

    @GetMapping("/{id}/detail")
    public ModelAndView productInfo(@PathVariable("id") Long id) {
        return new ModelAndView("view", "product", productService.findById(id));
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/product";
    }

}
