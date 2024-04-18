package com.nikola.spring.entities;


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_images")
public class ProductImageEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(name = "content_type",nullable = false)
    private String contentType;
    @Column(nullable = false)
    private Long size;
    @Lob
    private byte[] data;
    @OneToOne(fetch = FetchType.EAGER) //kada se koriste anotacije za vezivanje, treba koristiti joincolumn
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
