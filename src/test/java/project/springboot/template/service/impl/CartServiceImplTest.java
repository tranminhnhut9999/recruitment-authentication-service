//package vn.grooo.vietdang.service.impl;
//
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import vn.grooo.vietdang.entity.*;
//import vn.grooo.vietdang.entity.common.*;
//import vn.grooo.vietdang.entity.request.CustomerCartItemPostRequest;
//import vn.grooo.vietdang.entity.request.CustomerCartPostRequest;
//import vn.grooo.vietdang.entity.response.CartItemResponse;
//import vn.grooo.vietdang.repository.*;
//import vn.grooo.vietdang.util.CartItemUtil;
//import vn.grooo.vietdang.util.CartUtil;
//import vn.grooo.vietdang.util.MessageUtil;
//import vn.grooo.vietdang.util.PostUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Random;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class CartServiceImplTest {
//    @Mock
//    CartRepository cartRepository;
//    @Mock
//    CartItemRepository cartItemRepository;
//    @Mock
//    PostRepository postRepository;
//    @Mock
//    CategoryRepository categoryRepository;
//
//    @Mock
//    PromotionRepository promotionRepository;
//    @Mock
//    CartUtil cartUtil;
//    @Mock
//    PostUtil postUtil;
//    @InjectMocks
//    CartServiceImpl cartService;
//
//    @Mock
//    ContractRepository contractRepository;
//
//    CustomerCartPostRequest customerCartPostRequest;
//
//    Post requestPost;
//    Customer customer;
//    Cart cart;
//    final static Random randomGenerator = new Random();
//    final static Function<EPostType, Post> createPost = (ePostType -> {
//        Post post = new Post();
//        post.setId(randomGenerator.nextLong());
//        post.setName("Post Random Name" + post.getId());
//        PostType postType = new PostType();
//        postType.setCode(ePostType.name());
//        post.setPostType(postType);
//        PostSubPost postSubPost = new PostSubPost();
//        postSubPost.setPost(post);
//        postSubPost.setId(randomGenerator.nextLong());
//        post.setPostSubPost(postSubPost);
//        return post;
//    });
//    final static BiFunction<EPostType, Post, Post> createSubPost = ((ePostType, post) -> {
//        Post subPost = new Post();
//        subPost.setId(new Random().nextLong());
//        subPost.setName("Sub Post Random Name" + subPost.getId());
//        PostType postType = new PostType();
//        postType.setCode(ePostType.name());
//        subPost.setPostType(postType);
//        PostSubPost postSubPost = new PostSubPost();
//        postSubPost.setPost(subPost);
//        postSubPost.setId(randomGenerator.nextLong());
//        postSubPost.setParentPostSubPost(post.getPostSubPost());
//        postSubPost.setSubPostQuantity(5);
//        post.getPostSubPost().getSubPostSubPosts().add(postSubPost);
//        subPost.setPostSubPost(postSubPost);
//        return subPost;
//    });
//    final static Supplier<Category> createCategory = () ->
//    {
//        Category category = new Category();
//        category.setId(1L);
//        category.setName("Test Category");
//        return category;
//    };
//    final static BiFunction<Long, Integer, CustomerCartPostRequest> createRequest = ((id, quantity) -> {
//        CustomerCartPostRequest customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setCategoryId(1L);
//        customerCartPostRequest.setPostId(id);
//        customerCartPostRequest.setPostQuantity(quantity);
//        return customerCartPostRequest;
//    });
//    final static Function<EProductType, Product> createProduct = (productType) -> {
//        Product product = new Product();
//        product.setId(randomGenerator.nextLong());
//        product.setName("Product Name: " + randomGenerator.nextLong());
//        ProductType productType1 = new ProductType();
//        productType1.setCode(productType.name());
//        product.setType(productType1);
//        product.setCategory(createCategory.get());
//        return product;
//    };
//    final static BiFunction<Contract, Product, ContractProduct> createContractProduct = ((contract, product) -> {
//        ContractProduct contractProduct = new ContractProduct();
//        contractProduct.setContract(contract);
//        contractProduct.setProduct(product);
//        return contractProduct;
//    });
//    @Mock
//    private MessageUtil messageUtil;
//
//    @BeforeEach
//    void initData() {
//        customer = new Customer();
//        customer.setId(1L);
//        customer.setName("Grooo User");
//        customer.setCode("GROOOVN1");
//
//        cart = new Cart();
//        cart.setId(1L);
//        cart.setCustomer(customer);
//        cart.setName("Grooo_Main_Cart");
//        cart.setType(ECartType.MAIN);
//
////        List<CartItem> cartItemList = new ArrayList<>();
////
////        CartItem cartItem = new CartItem();
////        cartItem.setId(1l);
////        cartItemList.add(cartItem);
////        CartItem cartItem2 = new CartItem();
////
////        cartItem2.setId(1l);
////        cartItemList.add(cartItem2);
////
////        cartItem2.setCart(cart);
////        cartItem.setCart(cart);
////        cart.setCartItems(cartItemList);
////        Product product = createProduct.apply(EProductType.HAS_UNIT_PRICE);
////
////        Contract contract = new Contract();
////        contract.setId(1L);
////        contract.setCustomer(customer);
////        contract.getContractProducts().add(createContractProduct.apply(contract, product));
////        customer.getContracts().add(contract);
////
////        List<CartItemContract> cartItemContractList = new ArrayList<>();
////        CartItemContract cartItemContract = new CartItemContract();
////        CartItemContractKey cartItemContractKey = new CartItemContractKey();
////        cartItemContractKey.setContractId(contract.getId());
////        cartItemContractKey.setCartItemId(cartItem.getId());
////        cartItemContract.setId(cartItemContractKey);
////        cartItemContract.setCartItem(cartItem);
////        cartItemContract.setContract(contract);
////
////        cartItemContractList.add(cartItemContract);
////        cartItem.setCartItemContracts(cartItemContractList);
////        cartItemContract.setCartItem(cartItem);
////        cartItemContract.setPostQuantity(10L);
//        Mockito.when(cartUtil.getCurrentUserLoginMainCart()).thenReturn(cart);
//    }
//
//    @Test
//    void whenAddComboPost_shouldGetNotEnoughItemException() {
//        Post comboPost = new Post();
//        comboPost.setId(1L);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.COMBO.name());
//        comboPost.setPostType(postType);
//        PostSubPost postSubPost = new PostSubPost();
//        postSubPost.setId(1L);
//        postSubPost.setPost(comboPost);
//        comboPost.setPostSubPost(postSubPost);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(comboPost));
//
//        for (long i = 0; i < 3; i++) {
//            Post subPost = new Post();
//            subPost.setId(i);
//            postType = new PostType();
//            postType.setCode(EPostType.SINGLE.name());
//            subPost.setPostType(postType);
//
//            PostSubPost subPostSubPost = new PostSubPost();
//            subPostSubPost.setId(i);
//            subPostSubPost.setPost(subPost);
//            subPostSubPost.setParentPostSubPost(postSubPost);
//            postSubPost.getSubPostSubPosts().add(subPostSubPost);
//        }
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(1);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Invalid request to create combo!").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenAddPost_shouldGetAddOnlySubOptionException() {
//        Post subOptionPost = new Post();
//        subOptionPost.setId(1L);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.SUB_OPTION.name());
//        subOptionPost.setPostType(postType);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(subOptionPost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(5);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Can not buy only sub option!").isEqualTo(apiException.getMessage());
//        }
//
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetInvalidQuantity() {
//        Post singlePost = new Post();
//        singlePost.setId(1L);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        singlePost.setPostType(postType);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(0);
//
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Invalid quantity for post (must be greater than 0):1").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetQuantityOfGroupPostIsEqual_1() {
//        Post groupPost = createPost.apply(EPostType.GROUP);
//        when(postRepository.findById(any())).thenReturn(Optional.of(groupPost));
//        customerCartPostRequest = createRequest.apply(groupPost.getId(), 10);
//        for (int i = 0; i < 3; i++) {
//            Post subPost = createSubPost.apply(EPostType.SINGLE, groupPost);
//            createRequest.apply(subPost.getId(), 5);
//        }
//        try {
//            CartItemResponse cartItemResponse = cartService.addMainCartPost(customerCartPostRequest);
//            Assertions.assertThat(cartItemResponse.getPostQuantity()).isEqualTo(1);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetObligatorySubOptionException() {
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//
//        Post obligatorySubOptions = createSubPost.apply(EPostType.SUB_OPTION, singlePost);
//        obligatorySubOptions.getPostSubPost().setIsObligatory(true);
//        SubOptionType subOptionType = new SubOptionType();
//        subOptionType.setCode(ESubOptionType.COMBOBOX);
//        obligatorySubOptions.setSubOptionType(subOptionType);
//
//        for (int i = 0; i < 2; i++) {
//            obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(createSubPost.apply(EPostType.SINGLE, obligatorySubOptions).getPostSubPost());
//        }
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//        customerCartPostRequest = createRequest.apply(singlePost.getId(), 2);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Need at least obligatory sub option").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetExceedMaximumQuantityException() {
//        Post groupPost = createPost.apply(EPostType.GROUP);
//        customerCartPostRequest = createRequest.apply(groupPost.getId(), 5);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(groupPost));
//        groupPost.setCategory(createCategory.get());
////        Mockito.when(PostUtil.isGreaterThanMaxQuantity(any(), any(), any())).thenReturn(true);
//        for (long i = 0; i < 3; i++) {
//            createSubPost.apply(EPostType.SINGLE, groupPost).getPostSubPost().setMaximumQuantity(2);
//        }
//
//        customerCartPostRequest.getCustomerCartPostRequestList().add(createRequest.apply(groupPost.getPostSubPost().getSubPostSubPosts().get(0).getPost().getId(), 5));
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetRadioSubOptionException() {
//        Category category = createCategory.get();
//        when(PostUtil.checkSubOptionIsObligatory(any(), any())).thenReturn(true);
//        Post singlePost = new Post();
//        singlePost.setId(1L);
//        singlePost.setCategory(category);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        PostSubPost postSubPost = new PostSubPost();
//        postSubPost.setId(1L);
//        postSubPost.setPost(singlePost);
//        singlePost.setPostType(postType);
//        singlePost.setPostSubPost(postSubPost);
//
//        Post obligatorySubOptions = new Post();
//        obligatorySubOptions.setId(2L);
//        postType = new PostType();
//        postType.setCode(EPostType.SUB_OPTION.name());
//        obligatorySubOptions.setPostType(postType);
//        postSubPost = new PostSubPost();
//        postSubPost.setId(2L);
//        postSubPost.setPost(obligatorySubOptions);
//        postSubPost.setParentPostSubPost(singlePost.getPostSubPost());
//        obligatorySubOptions.setPostSubPost(postSubPost);
//        singlePost.getPostSubPost().getSubPostSubPosts().add(postSubPost);
//        SubOptionType subOptionType = new SubOptionType();
//        subOptionType.setCode(ESubOptionType.RADIOBOX);
//        obligatorySubOptions.setSubOptionType(subOptionType);
//        Post singleRadioPost_1 = new Post();
//        singleRadioPost_1.setId(3L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(3L);
//        postSubPost.setPost(singleRadioPost_1);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleRadioPost_1.setPostSubPost(postSubPost);
//        singleRadioPost_1.setPostType(postType);
//        singleRadioPost_1.setPostSubPost(postSubPost);
//
//        Post singleRadioPost_2 = new Post();
//        singleRadioPost_2.setId(4L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(4L);
//        postSubPost.setPost(singleRadioPost_2);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleRadioPost_2.setPostSubPost(postSubPost);
//        singleRadioPost_2.setPostType(postType);
//        singleRadioPost_2.setPostSubPost(postSubPost);
//
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleRadioPost_1.getPostSubPost());
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleRadioPost_2.getPostSubPost());
//
//
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setCategoryId(1L);
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(2);
//
//        CustomerCartPostRequest ratioSubOption = new CustomerCartPostRequest();
//        ratioSubOption.setPostId(2L);
//        ratioSubOption.setPostQuantity(1);
//
//        CustomerCartPostRequest singlePostRequest_1 = new CustomerCartPostRequest();
//        singlePostRequest_1.setPostId(3L);
//        singlePostRequest_1.setPostQuantity(1);
//        CustomerCartPostRequest singlePostRequest_2 = new CustomerCartPostRequest();
//        singlePostRequest_2.setPostId(4L);
//        singlePostRequest_2.setPostQuantity(1);
//
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_1);
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_2);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(ratioSubOption);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetComboSubOptionException() {
//        PostType postType;
//        PostSubPost postSubPost;
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//
//        Post obligatorySubOptions = createSubPost.apply(EPostType.SUB_OPTION, singlePost);
//        SubOptionType subOptionType = new SubOptionType();
//        subOptionType.setCode(ESubOptionType.COMBOBOX);
//        obligatorySubOptions.setSubOptionType(subOptionType);
//
//        Post singleComboPost_1 = new Post();
//        singleComboPost_1.setId(3L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(3L);
//        postSubPost.setPost(singleComboPost_1);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleComboPost_1.setPostSubPost(postSubPost);
//        singleComboPost_1.setPostType(postType);
//        singleComboPost_1.setPostSubPost(postSubPost);
//
//        Post singleComboPost_2 = new Post();
//        singleComboPost_2.setId(4L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(4L);
//        postSubPost.setPost(singleComboPost_2);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleComboPost_2.setPostSubPost(postSubPost);
//        singleComboPost_2.setPostType(postType);
//        singleComboPost_2.setPostSubPost(postSubPost);
//
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleComboPost_1.getPostSubPost());
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleComboPost_2.getPostSubPost());
//
//
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setCategoryId(1L);
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(2);
//
//        CustomerCartPostRequest ratioSubOption = new CustomerCartPostRequest();
//        ratioSubOption.setPostId(obligatorySubOptions.getId());
//        ratioSubOption.setPostQuantity(1);
//
//        CustomerCartPostRequest singlePostRequest_1 = new CustomerCartPostRequest();
//        singlePostRequest_1.setPostId(3L);
//        singlePostRequest_1.setPostQuantity(1);
//        CustomerCartPostRequest singlePostRequest_2 = new CustomerCartPostRequest();
//        singlePostRequest_2.setPostId(3L);
//        singlePostRequest_2.setPostQuantity(1);
//
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_1);
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_2);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(ratioSubOption);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetNoneSubPostException() {
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//        when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        singlePost.setPostSubPost(null);
//        customerCartPostRequest = createRequest.apply(singlePost.getId(), 5);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(createRequest.apply(3L, 10));
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//            Assertions.assertThat("Post dont have any sub post").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetSubPostNotFoundException() {
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//        for (int i = 0; i < 2; i++) {
//            createSubPost.apply(EPostType.SINGLE, singlePost);
//        }
//        when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        singlePost.setPostSubPost(null);
//        customerCartPostRequest = createRequest.apply(singlePost.getId(), 10);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(createRequest.apply(3L, 10));
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//            Assertions.assertThat("Post dont have any sub post").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenUpdateMainCartPosts_HaveBothPromotionAndContractException() {
////        List<CustomerCartItemPostRequest> cartProductRequestsList = new ArrayList<>();
////        CustomerCartItemPostRequest customerCartItemPostRequest = new CustomerCartItemPostRequest();
////        customerCartItemPostRequest.setCartItemId(1L);
////        customerCartItemPostRequest.setPostQuantity(10);
////        List<Long> promotionIds = new ArrayList<>();
////        promotionIds.add(1L);
////        promotionIds.add(2L);
////        promotionIds.add(3L);
//////        customerCartItemPostRequest.setPromotionIds(promotionIds);
////        customerCartItemPostRequest.setContractId(1L);
////        cartProductRequestsList.add(customerCartItemPostRequest);
////
////        when(messageUtil.getLocalMessage("Cart Item có hợp đông, ko the them promition")).thenReturn("Cart Item có hợp đông, ko the them promition");
////        ApiException apiException = assertThrows(ApiException.class, () -> cartService.updateMainCartPosts(cartProductRequestsList));
////        String message = apiException.getMessage();
////        assertEquals("Cart Item có hợp đông, ko the them promition", message);
//    }
//
//    @Test
//    void whenUpdateMainCartPosts_ContractException() {
//
//
//        List<CustomerCartItemPostRequest> cartProductRequestsList = new ArrayList<>();
//        CustomerCartItemPostRequest customerCartItemPostRequest = new CustomerCartItemPostRequest();
//        customerCartItemPostRequest.setCartItemId(1L);
//        customerCartItemPostRequest.setPostQuantity(10);
//        customerCartItemPostRequest.setContractId(1L);
//        cartProductRequestsList.add(customerCartItemPostRequest);
//
//
//        when(messageUtil.getLocalMessage("ko tim thay contract")).thenReturn("ko tim thay contract");
//
//        ApiException apiException = assertThrows(ApiException.class, () -> cartService.updateMainCartPosts(cartProductRequestsList));
//        String message = apiException.getMessage();
//        assertEquals("ko tim thay contract", message);
//
//    }
//
//    @Test
//    void whenUpdateMainCartPosts_NotFoundContractException() {
//
//        List<CustomerCartItemPostRequest> cartProductRequestsList = new ArrayList<>();
//        CustomerCartItemPostRequest customerCartItemPostRequest = new CustomerCartItemPostRequest();
//        customerCartItemPostRequest.setCartItemId(1L);
//        customerCartItemPostRequest.setPostQuantity(10);
//        customerCartItemPostRequest.setContractId(1L);
//        cartProductRequestsList.add(customerCartItemPostRequest);
//
//
//        when(messageUtil.getLocalMessage("hop dong khong co")).thenReturn("hop dong khong co");
//
//        ApiException apiException = assertThrows(ApiException.class, () -> cartService.updateMainCartPosts(cartProductRequestsList));
//        String message = apiException.getMessage();
//        assertEquals("hop dong khong co", message);
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithSingleWithOutSubOption() {
//        Product product = createProduct.apply(EProductType.HAS_UNIT_PRICE);
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//        singlePost.setProduct(product);
//        Category category = createCategory.get();
//        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(singlePost));
//        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
//        CustomerCartPostRequest request = new CustomerCartPostRequest();
//        request.setCategoryId(1L);
//        request.setPostQuantity(10);
//        request.setPostId(singlePost.getId());
//        CartItemResponse response = cartService.addMainCartPost(request);
//        Assertions.assertThat(response.getPostQuantity()).isEqualTo(request.getPostQuantity());
//        Assertions.assertThat(response.getPostId()).isEqualTo(request.getPostId());
//        Assertions.assertThat(response.getCategoryId()).isEqualTo(request.getCategoryId());
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithSingleWithSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithSingleWithPromotion() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithGroupWithOutSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithComboWithOutSubOption() {
//        Product product = createProduct.apply(EProductType.HAS_UNIT_PRICE);
//        Post comboPost = createPost.apply(EPostType.COMBO);
//        comboPost.setProduct(product);
//
//        for (int i = 0; i < 2; i++) {
//            Product subProduct = createProduct.apply(EProductType.HAS_UNIT_PRICE);
//            createSubPost.apply(EPostType.SINGLE, comboPost).setProduct(product);
//        }
//
//        Category category = createCategory.get();
//        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(comboPost));
//        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
//        CustomerCartPostRequest request = createRequest.apply(comboPost.getId(), 10);
//        for (int i = 0; i < comboPost.getPostSubPost().getSubPostSubPosts().size(); i++) {
//            PostSubPost postSubPost = comboPost.getPostSubPost().getSubPostSubPosts().get(i);
//            request.getCustomerCartPostRequestList().add(createRequest.apply(postSubPost.getPost().getId(), postSubPost.getSubPostQuantity()));
//        }
//        CartItemResponse response = cartService.addMainCartPost(request);
//        Assertions.assertThat(response.getPostQuantity()).isEqualTo(request.getPostQuantity());
//        Assertions.assertThat(response.getPostId()).isEqualTo(request.getPostId());
//        Assertions.assertThat(response.getCategoryId()).isEqualTo(request.getCategoryId());
//        for(int i= 0;i<response.getSubCartItems().size();i++){
//            CartItemResponse subCartItem = response.getSubCartItems().get(i);
//            CustomerCartPostRequest subRequest = request.getCustomerCartPostRequestList().get(i);
//            Assertions.assertThat(subCartItem.getPostQuantity()).isEqualTo(subRequest.getPostQuantity());
//            Assertions.assertThat(subCartItem.getPostId()).isEqualTo(subRequest.getPostId());
//        }
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithComboWithSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithGroupWithSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithComboWithPromotion() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithGroupWithPromotion() {
//
//    }
//}
//        cartItemContractList.add(cartItemContract);
//        cartItem.setCartItemContracts(cartItemContractList);
//        cartItemContract.setCartItem(cartItem);
//        cartItemContract.setPostQuantity(10L);
//        Mockito.when(cartUtil.getCurrentUserLoginMainCart()).thenReturn(cart);
//    }
//
//    @Test
//    void whenAddComboPost_shouldGetNotEnoughItemException() {
//        Post comboPost = new Post();
//        comboPost.setId(1L);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.COMBO.name());
//        comboPost.setPostType(postType);
//        PostSubPost postSubPost = new PostSubPost();
//        postSubPost.setId(1L);
//        postSubPost.setPost(comboPost);
//        comboPost.setPostSubPost(postSubPost);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(comboPost));
//
//        for (long i = 0; i < 3; i++) {
//            Post subPost = new Post();
//            subPost.setId(i);
//            postType = new PostType();
//            postType.setCode(EPostType.SINGLE.name());
//            subPost.setPostType(postType);
//
//            PostSubPost subPostSubPost = new PostSubPost();
//            subPostSubPost.setId(i);
//            subPostSubPost.setPost(subPost);
//            subPostSubPost.setParentPostSubPost(postSubPost);
//            postSubPost.getSubPostSubPosts().add(subPostSubPost);
//        }
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(1);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Invalid request to create combo!").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenAddPost_shouldGetAddOnlySubOptionException() {
//        Post subOptionPost = new Post();
//        subOptionPost.setId(1L);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.SUB_OPTION.name());
//        subOptionPost.setPostType(postType);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(subOptionPost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(5);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Can not buy only sub option!").isEqualTo(apiException.getMessage());
//        }
//
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetInvalidQuantity() {
//        Post singlePost = new Post();
//        singlePost.setId(1L);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        singlePost.setPostType(postType);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(0);
//
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Invalid quantity for post (must be greater than 0):1").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetQuantityOfGroupPostIsEqual_1() {
//        Post groupPost = createPost.apply(EPostType.GROUP);
//        when(postRepository.findById(any())).thenReturn(Optional.of(groupPost));
//        customerCartPostRequest = createRequest.apply(groupPost.getId(), 10);
//        for (int i = 0; i < 3; i++) {
//            Post subPost = createSubPost.apply(EPostType.SINGLE, groupPost);
//            createRequest.apply(subPost.getId(), 5);
//        }
//        try {
//            CartItemResponse cartItemResponse = cartService.addMainCartPost(customerCartPostRequest);
//            Assertions.assertThat(cartItemResponse.getPostQuantity()).isEqualTo(1);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetObligatorySubOptionException() {
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//
//        Post obligatorySubOptions = createSubPost.apply(EPostType.SUB_OPTION, singlePost);
//        obligatorySubOptions.getPostSubPost().setIsObligatory(true);
//        SubOptionType subOptionType = new SubOptionType();
//        subOptionType.setCode(ESubOptionType.COMBOBOX);
//        obligatorySubOptions.setSubOptionType(subOptionType);
//
//        for (int i = 0; i < 2; i++) {
//            obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(createSubPost.apply(EPostType.SINGLE, obligatorySubOptions).getPostSubPost());
//        }
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//        customerCartPostRequest = createRequest.apply(singlePost.getId(), 2);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            Assertions.assertThat("Need at least obligatory sub option").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetExceedMaximumQuantityException() {
//        Post groupPost = createPost.apply(EPostType.GROUP);
//        customerCartPostRequest = createRequest.apply(groupPost.getId(), 5);
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(groupPost));
//        groupPost.setCategory(createCategory.get());
////        Mockito.when(PostUtil.isGreaterThanMaxQuantity(any(), any(), any())).thenReturn(true);
//        for (long i = 0; i < 3; i++) {
//            createSubPost.apply(EPostType.SINGLE, groupPost).getPostSubPost().setMaximumQuantity(2);
//        }
//
//        customerCartPostRequest.getCustomerCartPostRequestList().add(createRequest.apply(groupPost.getPostSubPost().getSubPostSubPosts().get(0).getPost().getId(), 5));
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetRadioSubOptionException() {
//        Category category = createCategory.get();
//        when(PostUtil.checkSubOptionIsObligatory(any(), any())).thenReturn(true);
//        Post singlePost = new Post();
//        singlePost.setId(1L);
//        singlePost.setCategory(category);
//        PostType postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        PostSubPost postSubPost = new PostSubPost();
//        postSubPost.setId(1L);
//        postSubPost.setPost(singlePost);
//        singlePost.setPostType(postType);
//        singlePost.setPostSubPost(postSubPost);
//
//        Post obligatorySubOptions = new Post();
//        obligatorySubOptions.setId(2L);
//        postType = new PostType();
//        postType.setCode(EPostType.SUB_OPTION.name());
//        obligatorySubOptions.setPostType(postType);
//        postSubPost = new PostSubPost();
//        postSubPost.setId(2L);
//        postSubPost.setPost(obligatorySubOptions);
//        postSubPost.setParentPostSubPost(singlePost.getPostSubPost());
//        obligatorySubOptions.setPostSubPost(postSubPost);
//        singlePost.getPostSubPost().getSubPostSubPosts().add(postSubPost);
//        SubOptionType subOptionType = new SubOptionType();
//        subOptionType.setCode(ESubOptionType.RADIOBOX);
//        obligatorySubOptions.setSubOptionType(subOptionType);
//        Post singleRadioPost_1 = new Post();
//        singleRadioPost_1.setId(3L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(3L);
//        postSubPost.setPost(singleRadioPost_1);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleRadioPost_1.setPostSubPost(postSubPost);
//        singleRadioPost_1.setPostType(postType);
//        singleRadioPost_1.setPostSubPost(postSubPost);
//
//        Post singleRadioPost_2 = new Post();
//        singleRadioPost_2.setId(4L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(4L);
//        postSubPost.setPost(singleRadioPost_2);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleRadioPost_2.setPostSubPost(postSubPost);
//        singleRadioPost_2.setPostType(postType);
//        singleRadioPost_2.setPostSubPost(postSubPost);
//
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleRadioPost_1.getPostSubPost());
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleRadioPost_2.getPostSubPost());
//
//
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setCategoryId(1L);
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(2);
//
//        CustomerCartPostRequest ratioSubOption = new CustomerCartPostRequest();
//        ratioSubOption.setPostId(2L);
//        ratioSubOption.setPostQuantity(1);
//
//        CustomerCartPostRequest singlePostRequest_1 = new CustomerCartPostRequest();
//        singlePostRequest_1.setPostId(3L);
//        singlePostRequest_1.setPostQuantity(1);
//        CustomerCartPostRequest singlePostRequest_2 = new CustomerCartPostRequest();
//        singlePostRequest_2.setPostId(4L);
//        singlePostRequest_2.setPostQuantity(1);
//
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_1);
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_2);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(ratioSubOption);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetComboSubOptionException() {
//        PostType postType;
//        PostSubPost postSubPost;
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//
//        Post obligatorySubOptions = createSubPost.apply(EPostType.SUB_OPTION, singlePost);
//        SubOptionType subOptionType = new SubOptionType();
//        subOptionType.setCode(ESubOptionType.COMBOBOX);
//        obligatorySubOptions.setSubOptionType(subOptionType);
//
//        Post singleComboPost_1 = new Post();
//        singleComboPost_1.setId(3L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(3L);
//        postSubPost.setPost(singleComboPost_1);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleComboPost_1.setPostSubPost(postSubPost);
//        singleComboPost_1.setPostType(postType);
//        singleComboPost_1.setPostSubPost(postSubPost);
//
//        Post singleComboPost_2 = new Post();
//        singleComboPost_2.setId(4L);
//        postType = new PostType();
//        postType.setCode(EPostType.SINGLE.name());
//        postSubPost = new PostSubPost();
//        postSubPost.setId(4L);
//        postSubPost.setPost(singleComboPost_2);
//        postSubPost.setParentPostSubPost(obligatorySubOptions.getPostSubPost());
//        singleComboPost_2.setPostSubPost(postSubPost);
//        singleComboPost_2.setPostType(postType);
//        singleComboPost_2.setPostSubPost(postSubPost);
//
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleComboPost_1.getPostSubPost());
//        obligatorySubOptions.getPostSubPost().getSubPostSubPosts().add(singleComboPost_2.getPostSubPost());
//
//
//        Mockito.when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        customerCartPostRequest = new CustomerCartPostRequest();
//        customerCartPostRequest.setCategoryId(1L);
//        customerCartPostRequest.setPostId(1L);
//        customerCartPostRequest.setPostQuantity(2);
//
//        CustomerCartPostRequest ratioSubOption = new CustomerCartPostRequest();
//        ratioSubOption.setPostId(obligatorySubOptions.getId());
//        ratioSubOption.setPostQuantity(1);
//
//        CustomerCartPostRequest singlePostRequest_1 = new CustomerCartPostRequest();
//        singlePostRequest_1.setPostId(3L);
//        singlePostRequest_1.setPostQuantity(1);
//        CustomerCartPostRequest singlePostRequest_2 = new CustomerCartPostRequest();
//        singlePostRequest_2.setPostId(3L);
//        singlePostRequest_2.setPostQuantity(1);
//
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_1);
//        ratioSubOption.getCustomerCartPostRequestList().add(singlePostRequest_2);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(ratioSubOption);
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetNoneSubPostException() {
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//        when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        singlePost.setPostSubPost(null);
//        customerCartPostRequest = createRequest.apply(singlePost.getId(), 5);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(createRequest.apply(3L, 10));
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//            Assertions.assertThat("Post dont have any sub post").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenCreateCartItem_shouldGetSubPostNotFoundException() {
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//        for (int i = 0; i < 2; i++) {
//            createSubPost.apply(EPostType.SINGLE, singlePost);
//        }
//        when(postRepository.findById(any())).thenReturn(Optional.of(singlePost));
//
//        singlePost.setPostSubPost(null);
//        customerCartPostRequest = createRequest.apply(singlePost.getId(), 10);
//        customerCartPostRequest.getCustomerCartPostRequestList().add(createRequest.apply(3L, 10));
//        try {
//            cartService.addMainCartPost(customerCartPostRequest);
//        } catch (ApiException apiException) {
//            System.out.println(apiException.getMessage());
//            Assertions.assertThat("Post dont have any sub post").isEqualTo(apiException.getMessage());
//        }
//    }
//
//    @Test
//    void whenUpdateMainCartPosts_HaveBothPromotionAndContractException() {
//        List<CustomerCartItemPostRequest> cartProductRequestsList = new ArrayList<>();
//        CustomerCartItemPostRequest customerCartItemPostRequest = new CustomerCartItemPostRequest();
//        customerCartItemPostRequest.setCartItemId(1L);
//        customerCartItemPostRequest.setPostQuantity(10);
//        List<Long> promotionIds = new ArrayList<>();
//        promotionIds.add(1L);
//        promotionIds.add(2L);
//        promotionIds.add(3L);
//        customerCartItemPostRequest.setPromotionId(promotionIds.get(0));
//        customerCartItemPostRequest.setContractId(1L);
//        cartProductRequestsList.add(customerCartItemPostRequest);
//
//        when(messageUtil.getLocalMessage("Cart Item có hợp đông, ko the them promition")).thenReturn("Cart Item có hợp đông, ko the them promition");
//        ApiException apiException = assertThrows(ApiException.class, () -> cartService.updateMainCartPosts(cartProductRequestsList));
//        String message = apiException.getMessage();
//        assertEquals("Cart Item có hợp đông, ko the them promition", message);
//    }
//
//    @Test
//    void whenUpdateMainCartPosts_ContractException() {
//
//
//        List<CustomerCartItemPostRequest> cartProductRequestsList = new ArrayList<>();
//        CustomerCartItemPostRequest customerCartItemPostRequest = new CustomerCartItemPostRequest();
//        customerCartItemPostRequest.setCartItemId(1L);
//        customerCartItemPostRequest.setPostQuantity(10);
//        customerCartItemPostRequest.setContractId(1L);
//        cartProductRequestsList.add(customerCartItemPostRequest);
//
//
//        when(messageUtil.getLocalMessage("ko tim thay contract")).thenReturn("ko tim thay contract");
//
//        ApiException apiException = assertThrows(ApiException.class, () -> cartService.updateMainCartPosts(cartProductRequestsList));
//        String message = apiException.getMessage();
//        assertEquals("ko tim thay contract", message);
//
//    }
//
//    @Test
//    void whenUpdateMainCartPosts_NotFoundContractException() {
//
//        List<CustomerCartItemPostRequest> cartProductRequestsList = new ArrayList<>();
//        CustomerCartItemPostRequest customerCartItemPostRequest = new CustomerCartItemPostRequest();
//        customerCartItemPostRequest.setCartItemId(1L);
//        customerCartItemPostRequest.setPostQuantity(10);
//        customerCartItemPostRequest.setContractId(1L);
//        cartProductRequestsList.add(customerCartItemPostRequest);
//
//
//        when(messageUtil.getLocalMessage("hop dong khong co")).thenReturn("hop dong khong co");
//
//        ApiException apiException = assertThrows(ApiException.class, () -> cartService.updateMainCartPosts(cartProductRequestsList));
//        String message = apiException.getMessage();
//        assertEquals("hop dong khong co", message);
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithSingleWithOutSubOption() {
//        Product product = createProduct.apply(EProductType.HAS_UNIT_PRICE);
//        Post singlePost = createPost.apply(EPostType.SINGLE);
//        singlePost.setProduct(product);
//        Category category = createCategory.get();
//        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(singlePost));
//        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
//        CustomerCartPostRequest request = new CustomerCartPostRequest();
//        request.setCategoryId(1L);
//        request.setPostQuantity(10);
//        request.setPostId(singlePost.getId());
//        CartItemResponse response = cartService.addMainCartPost(request);
//        Assertions.assertThat(response.getPostQuantity()).isEqualTo(request.getPostQuantity());
//        Assertions.assertThat(response.getPostId()).isEqualTo(request.getPostId());
//        Assertions.assertThat(response.getCategoryId()).isEqualTo(request.getCategoryId());
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithSingleWithSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithSingleWithPromotion() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithGroupWithOutSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithComboWithOutSubOption() {
//        Product product = createProduct.apply(EProductType.HAS_UNIT_PRICE);
//        Post comboPost = createPost.apply(EPostType.COMBO);
//        comboPost.setProduct(product);
//
//        for (int i = 0; i < 2; i++) {
//            Product subProduct = createProduct.apply(EProductType.HAS_UNIT_PRICE);
//            createSubPost.apply(EPostType.SINGLE, comboPost).setProduct(product);
//        }
//
//        Category category = createCategory.get();
//        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(comboPost));
//        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(category));
//        CustomerCartPostRequest request = createRequest.apply(comboPost.getId(), 10);
//        for (int i = 0; i < comboPost.getPostSubPost().getSubPostSubPosts().size(); i++) {
//            PostSubPost postSubPost = comboPost.getPostSubPost().getSubPostSubPosts().get(i);
//            request.getCustomerCartPostRequestList().add(createRequest.apply(postSubPost.getPost().getId(), postSubPost.getSubPostQuantity()));
//        }
//        CartItemResponse response = cartService.addMainCartPost(request);
//        Assertions.assertThat(response.getPostQuantity()).isEqualTo(request.getPostQuantity());
//        Assertions.assertThat(response.getPostId()).isEqualTo(request.getPostId());
//        Assertions.assertThat(response.getCategoryId()).isEqualTo(request.getCategoryId());
//        for(int i= 0;i<response.getSubCartItems().size();i++){
//            CartItemResponse subCartItem = response.getSubCartItems().get(i);
//            CustomerCartPostRequest subRequest = request.getCustomerCartPostRequestList().get(i);
//            Assertions.assertThat(subCartItem.getPostQuantity()).isEqualTo(subRequest.getPostQuantity());
//            Assertions.assertThat(subCartItem.getPostId()).isEqualTo(subRequest.getPostId());
//        }
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithComboWithSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithGroupWithSubOption() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithComboWithPromotion() {
//
//    }
//
//    @Test
//    void whenAddCart_ShouldSuccessWithGroupWithPromotion() {
//
//    }
//}
