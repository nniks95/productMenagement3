package com.nikola.spring.converter;
import com.nikola.spring.dto.*;
import com.nikola.spring.entities.*;
import com.nikola.spring.repositories.*;
import com.sun.jdi.DoubleValue;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TempConverter {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private OrderAddressRepository orderAddressRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    private DecimalFormat decForm = new DecimalFormat("0.00");

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CategoryDto entityToDto (CategoryEntity category){

        CategoryDto returnValue = mapper.map(category, CategoryDto.class);

        Optional<List<ProductEntity>> productsOptional = Optional.ofNullable(category.getProducts());
        List<Integer> productIds = new ArrayList<>();
        if(!productsOptional.isEmpty()){
            productsOptional.get().forEach((productEntity)->{
                productIds.add(productEntity.getId());
            });
        }
        returnValue.setProductsIds(productIds);
        return returnValue;
    }




    public CategoryEntity dtoToEntity(CategoryDto category){

        CategoryEntity returnValue = mapper.map(category, CategoryEntity.class);

        Optional<List<Integer>> productsIdsOptional = Optional.ofNullable(category.getProductsIds());

        List<ProductEntity> products = new ArrayList<>();

        if(!productsIdsOptional.isEmpty()){
            productsIdsOptional.get().forEach((productId)->{
                ProductEntity product = productRepository.findById(productId).orElse(null);
                //koristi se repository da se povuce entitet po id-u
                if(product != null){
                    products.add(product);
                }
            });
        }
        returnValue.setProducts(products);
        return returnValue;
    }
    public ProductDto entityToDto (ProductEntity product){
        ProductDto returnValue = mapper.map(product, ProductDto.class);

        Optional<CategoryEntity> categoryOptional = Optional.ofNullable(product.getCategory());
        //pravi se optional konfiktno polje iz productEtity-ja
        if(categoryOptional.isPresent()){
            Integer categoryId = categoryOptional.get().getId();
            returnValue.setCategoryId(categoryId);
        }


        Optional<Timestamp> createTimeOptional = Optional.ofNullable(product.getCreateTime());
        if(createTimeOptional.isPresent()){
            LocalDateTime createTime = createTimeOptional.get().toLocalDateTime();
            String createTimeStr = createTime.format(dateTimeFormatter);
            returnValue.setCreateTime(createTimeStr);
        }

        Optional<Timestamp> updateTimeOptional = Optional.ofNullable(product.getUpdateTime());
        if(updateTimeOptional.isPresent()){
            LocalDateTime updateTime = updateTimeOptional.get().toLocalDateTime();
            String updateTimeStr = updateTime.format(dateTimeFormatter);
            returnValue.setUpdateTime(updateTimeStr);
        }

        return returnValue;
    }

    public ProductEntity dtoToEntity(ProductDto product){

        ProductEntity returnValue = mapper.map(product, ProductEntity.class);

        Optional<Integer> categoryIdOptional = Optional.ofNullable(product.getCategoryId());
        if(categoryIdOptional.isPresent()){
            Integer categoryId = categoryIdOptional.get();
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
            if(categoryEntity != null){
                returnValue.setCategory(categoryEntity);
            }

        }
        return returnValue;
    }

    public ProductImageEntity dtoToEntity(ProductImageDto productImage){
        ProductImageEntity returnValue = mapper.map(productImage, ProductImageEntity.class);

        Optional<Integer> productIdOptional = Optional.ofNullable(productImage.getProduct_id());
        if(productIdOptional.isPresent()){
            Integer productId = productIdOptional.get();
            ProductEntity productEntity = productRepository.findById(productId).orElse(null);
            if(productEntity != null){
                returnValue.setProduct(productEntity);
            }
        }
        return returnValue;
    }

    public ProductImageDto entityToDto(ProductImageEntity productImage){
        ProductImageDto returnValue = mapper.map(productImage, ProductImageDto.class);

        Optional<ProductEntity> productEntityOptional = Optional.ofNullable(productImage.getProduct());
        if(productEntityOptional.isPresent()){
            returnValue.setProduct_id(productEntityOptional.get().getId());
        }
        return returnValue;
    }

    public UserDto entityToDto(UserEntity userEntity){
        UserDto returnValue = mapper.map(userEntity, UserDto.class);

        Optional<List<RoleEntity>> rolesOptional = Optional.ofNullable(userEntity.getRoles());
        List<Integer> rolesIds = new ArrayList<>();
        if(rolesOptional.isPresent()){
            for(RoleEntity roleEntity: rolesOptional.get()){
                rolesIds.add(roleEntity.getId());
            }
        }
        returnValue.setRolesIds(rolesIds);
        Optional<Byte> enabledOptional = Optional.ofNullable(userEntity.getEnabled());
        if(enabledOptional.isPresent()){
            returnValue.setEnabled(enabledOptional.get().shortValue());
            //pretvaranje byta u short zbog baga na model mapperu
        }
        return returnValue;
    }
    public UserEntity dtoToEntity(UserDto userDto){
        UserEntity returnValue = mapper.map(userDto, UserEntity.class);
        Optional<Short> enabledOptional = Optional.ofNullable(userDto.getEnabled());
        if(enabledOptional.isPresent()){
            returnValue.setEnabled(enabledOptional.get().byteValue());
        }
        Optional<List<Integer>> rolesIdsOptional = Optional.ofNullable(userDto.getRolesIds());
        List<RoleEntity> roleEntities = new ArrayList<>();
        if(rolesIdsOptional.isPresent()){
            for(Integer roleId:rolesIdsOptional.get()){
                RoleEntity role = roleRepository.findById(roleId).orElse(null);
                if(role != null){
                    roleEntities.add(role);
                }
            }
        }
        returnValue.setRoles(roleEntities);
        return returnValue;
    }

    public RoleEntity dtoToEntity(RoleDto roleDto) {
        RoleEntity returnValue = mapper.map(roleDto, RoleEntity.class);
        Optional<List<Integer>> userIdsOptional = Optional.of(roleDto.getUsersIds());
        List<UserEntity> userEntities = new ArrayList<>();
        if (userIdsOptional.isPresent()) {
            for (Integer userId : userIdsOptional.get()) {
                UserEntity user = userRepository.findById(userId).orElse(null);
                if (user != null) {
                    userEntities.add(user);
                }
            }
        }
        returnValue.setUsers(userEntities);
        return returnValue;
    }

    public RoleDto entityToDto(RoleEntity roleEntity){
        RoleDto returnValue = mapper.map(roleEntity,RoleDto.class);

        Optional<List<UserEntity>> userEntitiesOptional = Optional.ofNullable(roleEntity.getUsers());
        List<Integer> userIds = new ArrayList<>();
        if(userEntitiesOptional.isPresent()){
            for(UserEntity user:userEntitiesOptional.get()){
                userIds.add(user.getId());
            }
        }
        returnValue.setUsersIds(userIds);
        return returnValue;
    }

    public CustomerDto entityToDto(CustomerEntity customerEntity){
        CustomerDto returnValue = mapper.map(customerEntity, CustomerDto.class);

        Optional<ShippingAddressEntity> shippingAddressOptional = Optional.ofNullable(customerEntity.getShippingAddress());
        if(shippingAddressOptional.isPresent()){
            returnValue.setShippingAddressId(shippingAddressOptional.get().getId());
        }
        Optional<UserEntity> userOptional = Optional.ofNullable(customerEntity.getUser());
        if(userOptional.isPresent()){
            returnValue.setUserId(userOptional.get().getId());
        }
        Optional<CartEntity> cartOptional = Optional.ofNullable(customerEntity.getCart());
        if(cartOptional.isPresent()){
            returnValue.setCartId(cartOptional.get().getId());
        }
        return returnValue;
    }
    public CustomerEntity dtoToEntity(CustomerDto customerDto){
        CustomerEntity returnValue = mapper.map(customerDto, CustomerEntity.class);

        Optional<Integer> shippingAddressIdOptional = Optional.ofNullable(customerDto.getShippingAddressId());
        if(shippingAddressIdOptional.isPresent()){
            Integer shippingAddressId = shippingAddressIdOptional.get();
            returnValue.setShippingAddress(shippingAddressRepository.findById(shippingAddressId).orElse(null));
        }

        Optional<Integer> userIdOptional = Optional.ofNullable(customerDto.getUserId());
        if(userIdOptional.isPresent()){
            returnValue.setUser(userRepository.findById(userIdOptional.get()).orElse(null));
        }

        Optional<Integer> cartIdOptional  = Optional.ofNullable(customerDto.getCartId());
        if(cartIdOptional.isPresent()){
            returnValue.setCart(cartRepository.findById(cartIdOptional.get()).orElse(null));
        }
        return  returnValue;
    }

    public ShippingAddressEntity dtoToEntity(ShippingAddressDto shippingAddressDto){
        ShippingAddressEntity returnValue = mapper.map(shippingAddressDto, ShippingAddressEntity.class);

        Optional<Integer> customerIdOptional = Optional.ofNullable(shippingAddressDto.getCustomerId());
        if(customerIdOptional.isPresent()){
            returnValue.setCustomer(customerRepository.findById(customerIdOptional.get()).orElse(null));
        }
        return  returnValue;
    }

    public  ShippingAddressDto entityToDto(ShippingAddressEntity shippingAddressEntity){
        ShippingAddressDto returnValue = mapper.map(shippingAddressEntity, ShippingAddressDto.class);

        Optional<CustomerEntity> customerOptional = Optional.ofNullable(shippingAddressEntity.getCustomer());
        if(customerOptional.isPresent()){
            returnValue.setCustomerId(customerOptional.get().getId());
        }
        return  returnValue;
    }

    public CartEntity dtoToEntity(CartDto cartDto){
        CartEntity returnValue = mapper.map(cartDto,CartEntity.class);

        Optional<Integer> customerIdOptional = Optional.ofNullable(cartDto.getCustomerId());
        if(customerIdOptional.isPresent()){
            returnValue.setCustomer(customerRepository.findById(customerIdOptional.get()).orElse(null));
        }
        Optional<List<Integer>> cartItemsIdsOptional = Optional.ofNullable(cartDto.getCartItemsIds());
        List<CartItemEntity> cartItems = new ArrayList<>();
        if(cartItemsIdsOptional.isPresent()){
            for(Integer cartItemId:cartItemsIdsOptional.get()){
                CartItemEntity item = cartItemRepository.findById(cartItemId).orElse(null);
                if(item != null){
                    cartItems.add(item);
                }
            }
        }
        returnValue.setCartItems(cartItems);
        Double price = Double.valueOf(decForm.format(returnValue.getCartPrice()));
        returnValue.setCartPrice(price);
        return  returnValue;
    }

    public CartDto entityToDto(CartEntity cartEntity){
        CartDto returnValue = mapper.map(cartEntity, CartDto.class);

        Optional<CustomerEntity> customerOptional = Optional.ofNullable(cartEntity.getCustomer());
        if(customerOptional.isPresent()){
            returnValue.setCustomerId(customerOptional.get().getId());
        }
        Optional<List<CartItemEntity>> cartItemsOptional = Optional.ofNullable(cartEntity.getCartItems());
        List<Integer> cartItemsIds = new ArrayList<>();
        if(cartItemsOptional.isPresent()){
            for(CartItemEntity cartItem:cartItemsOptional.get()){
                Integer itemId = cartItem.getId();
                cartItemsIds.add(itemId);
            }
        }
        returnValue.setCartItemsIds(cartItemsIds);
        Double price = Double.valueOf(decForm.format(returnValue.getCartPrice()));
        returnValue.setCartPrice(price);
        return returnValue;
    }


    public CartItemEntity dtoToEntity(CartItemDto cartItemDto){
        CartItemEntity returnValue = mapper.map(cartItemDto,CartItemEntity.class);
        Optional<Integer> cartIdOptional = Optional.ofNullable(cartItemDto.getCart_id());
        if (cartIdOptional.isPresent()){
            Integer cartId = cartItemDto.getCart_id();
            returnValue.setCart(cartRepository.findById(cartId).orElse(null));
        }
        Optional<Integer> productIdOptional = Optional.ofNullable(cartItemDto.getProduct_id());
        if(productIdOptional.isPresent()){
            returnValue.setProduct(productRepository.findById(productIdOptional.get()).orElse(null));
        }
        return returnValue;
    }
    public CartItemDto entityToDto(CartItemEntity cartItemEntity){
        CartItemDto returnValue = mapper.map(cartItemEntity, CartItemDto.class);
        Optional<CartEntity> cartEntityOptional = Optional.ofNullable(cartItemEntity.getCart());
        if(cartEntityOptional.isPresent()){
            returnValue.setCart_id(cartEntityOptional.get().getId());
        }
        Optional<ProductEntity> productEntityOptional = Optional.ofNullable(cartItemEntity.getProduct());
        if(productEntityOptional.isPresent()){
            returnValue.setProduct_id(productEntityOptional.get().getId());
        }
        return returnValue;
    }

    public OrderDto entityToDto(OrderEntity orderEntity){
        OrderDto returnValue = mapper.map(orderEntity,OrderDto.class);
        Optional<CartEntity> cartEntityOptional= Optional.ofNullable(orderEntity.getCart());
        if(cartEntityOptional.isPresent()){
            returnValue.setCartId(cartEntityOptional.get().getId());
        }
        Optional<Timestamp> createTimeOptional = Optional.ofNullable(orderEntity.getCreateTime());
        if(createTimeOptional.isPresent()){
            LocalDateTime createTime = createTimeOptional.get().toLocalDateTime();
            String createTimeStr = createTime.format(dateTimeFormatter);
            returnValue.setCreateTime(createTimeStr);
        }
        Optional<OrderAddressEntity> orderAddressEntityOptional = Optional.ofNullable(orderEntity.getOrderAddress());
        if(orderAddressEntityOptional.isPresent()){
            returnValue.setOrderAddressId(orderAddressEntityOptional.get().getId());
        }
        Optional <List<OrderItemEntity>> orderItemsOptional = Optional.ofNullable(orderEntity.getOrderItems());
        List<Integer> orderItemsIds = new ArrayList<>();
        if(orderItemsOptional.isPresent()){
            for(OrderItemEntity orderItem : orderItemsOptional.get()){
                orderItemsIds.add(orderItem.getId());
            }
        }
        returnValue.setOrderItemsIds(orderItemsIds);
        return returnValue;
    }

    public OrderEntity dtoToEntity(OrderDto orderDto){
        OrderEntity returnValue = mapper.map(orderDto,OrderEntity.class);
        Optional<Integer> cartIdOptional = Optional.ofNullable(orderDto.getCartId());
        if(cartIdOptional.isPresent()){
            CartEntity cart = cartRepository.findById(cartIdOptional.get()).orElse(null);
            if(cart != null){
                returnValue.setCart(cart);
            }
        }
        Optional<Integer> orderAddressIdOptional = Optional.ofNullable(orderDto.getOrderAddressId());
        if(orderAddressIdOptional.isPresent()){
            OrderAddressEntity orderAddress = orderAddressRepository.findById(orderAddressIdOptional.get()).orElse(null);
            if(orderAddress != null){
                returnValue.setOrderAddress(orderAddress);
            }
        }
        Optional<List<Integer>> orderItemsIdsOptional = Optional.ofNullable(orderDto.getOrderItemsIds());
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        if(orderItemsIdsOptional.isPresent()){
            for(Integer orderItemId : orderItemsIdsOptional.get()){
                OrderItemEntity orderItem = orderItemRepository.findById(orderItemId).orElse(null);
                if(orderItem != null){
                    orderItemEntities.add(orderItem);
                }
            }
        }
        returnValue.setOrderItems(orderItemEntities);
        return returnValue;
    }
    public OrderItemDto entityToDto(OrderItemEntity orderItemEntity){
        OrderItemDto returnValue = mapper.map(orderItemEntity,OrderItemDto.class);
        Optional<OrderEntity> orderEntityOptional = Optional.ofNullable(orderItemEntity.getOrder());
        if(orderEntityOptional.isPresent()){
            returnValue.setOrderId(orderEntityOptional.get().getId());
        }
        return returnValue;
    }
    public OrderItemEntity dtoToEntity(OrderItemDto orderItemDto){
        OrderItemEntity returnValue = mapper.map(orderItemDto,OrderItemEntity.class);
        Optional<Integer> orderIdOptional = Optional.ofNullable(orderItemDto.getOrderId());
        if(orderIdOptional.isPresent()){
            OrderEntity order = orderRepository.findById(orderIdOptional.get()).orElse(null);
            if(order != null){
                returnValue.setOrder(order);
            }
        }
        return returnValue;
    }

    public OrderAddressDto entityToDto(OrderAddressEntity orderAddressEntity){
        OrderAddressDto returnValue = mapper.map(orderAddressEntity,OrderAddressDto.class);
        Optional<OrderEntity> orderEntityOptional = Optional.ofNullable(orderAddressEntity.getOrderEntity());
        if(orderEntityOptional.isPresent()){
            returnValue.setOrderId(orderEntityOptional.get().getId());
        }
        return returnValue;
    }
    public OrderAddressEntity dtoToEntity(OrderAddressDto orderAddressDto){
        OrderAddressEntity returnValue = mapper.map(orderAddressDto,OrderAddressEntity.class);
        Optional<Integer> orderIdOptional = Optional.ofNullable(orderAddressDto.getOrderId());
        if(orderIdOptional.isPresent()){
            OrderEntity order = orderRepository.findById(orderIdOptional.get()).orElse(null);
            if(order != null){
                returnValue.setOrderEntity(order);
            }
        }
        return returnValue;
    }

    public OrderAddressDto shippingAddressToOrderAddress(ShippingAddressDto shippingAddressDto){
        OrderAddressDto returnValue = mapper.map(shippingAddressDto, OrderAddressDto.class);
        System.out.println(returnValue.toString());

        returnValue.setId(null);
        System.out.println(returnValue.toString());
        return returnValue;
    }

    public OrderItemDto cartItemToOrderItem(CartItemDto cartItemDto){
        OrderItemDto returnValue = new OrderItemDto();
        Optional<Integer> productIdOptional = Optional.ofNullable(cartItemDto.getProduct_id());
        if(productIdOptional.isPresent()){
            Integer productId = productIdOptional.get();
            ProductEntity productEntity = productRepository.findById(productId).orElse(null);
            if(productEntity != null){
                String productName = productEntity.getBrand()+ " " + productEntity.getModel();
                Double productPrice = productEntity.getPrice();
                returnValue.setProductName(productName);
                returnValue.setProductPrice(productPrice);
            }
        }
        Optional<Integer> quantityOptional = Optional.ofNullable(cartItemDto.getQuantity());
        if(quantityOptional.isPresent()){
            Integer quantity = quantityOptional.get();
            returnValue.setProductQuantity(quantity);
        }

        Double price = returnValue.getProductPrice()* returnValue.getProductQuantity();
        returnValue.setPrice(price);
        return returnValue;
    }









}
