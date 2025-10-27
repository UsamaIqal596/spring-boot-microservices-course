package com.sivalabs.bookstore.catalog.domain.product;

import com.sivalabs.bookstore.catalog.ApplicationProperties;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    private  final ApplicationProperties applicationProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
        this.productRepository = productRepository;
        this.applicationProperties = applicationProperties;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        //why we didi because jpa start page number from 0 not 1
        pageNo = pageNo <=1? 0 : pageNo - 1;

        //Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);
        // var all = productRepository.findAll(pageable);
        // final Iterable<?> productPage = productRepository.findAll(pageable);
        // or we can use below
                        Page<ProductEntity> productsPage = productRepository.findAll(pageable);
       final Page<Product> pageOfProductEntityToProductObject = productsPage.map(ProductMapper::toProduct);

        final PagedResult<Product> productPagedResult= new PagedResult<>(
                pageOfProductEntityToProductObject.getContent(),
                pageOfProductEntityToProductObject.getTotalElements(),
                                pageOfProductEntityToProductObject.getTotalPages(),
                                pageOfProductEntityToProductObject.getNumber() + 1 ,
                                pageOfProductEntityToProductObject.isFirst(),
                                pageOfProductEntityToProductObject.isLast(),
                                pageOfProductEntityToProductObject.hasNext(),
                pageOfProductEntityToProductObject.hasPrevious()
        );
        return productPagedResult;
    }


    public Optional<Product> getProductByCode(String code) {
        final Optional<ProductEntity> byCode = productRepository.findByCode(code);
//        final Optional<Product> product = byCode.map(ProductMapper::toProduct); thsi can be used and its better is to use this
        final Optional<Product> product = byCode.map(productEntity -> ProductMapper.toProduct(productEntity));
        return product;
    }

}
