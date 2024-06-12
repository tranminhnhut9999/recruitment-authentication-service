package project.springboot.template.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import project.springboot.template.util.MessageUtil;
import vn.grooo.vietdang.entity.*;
import vn.grooo.vietdang.s1_repository.S1GroupProductRepository;
import vn.grooo.vietdang.s1_repository.S1GroupRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private DisplayGroupRepository displayGroupRepository;

    @Mock
    private PostTypeRepository postTypeRepository;

    @Mock
    private ProductTypeRepository productTypeRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private PostUtil postUtil;

    @Mock
    private SubOptionTypeRepository subOptionTypeRepository;

    @Mock
    private S1GroupRepository s1GroupRepository;

    @Mock
    private S1GroupProductRepository s1GroupProductRepository;

    @Mock
    private RequestUtil requestUtil;

    @Mock
    private PostSubPostRepository postSubPostRepository;

    @Mock
    private PostDisplayGroupRepository postDisplayGroupRepository;

    @Mock
    private PromotionPostRepository promotionPostRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    public void initData() {
        // init post type
        PostType singlePostType = new PostType();
        singlePostType.setId(1L);
        singlePostType.setCode(EPostType.SINGLE.name());
        singlePostType.setName("Bài viết đơn");
        when(postTypeRepository.findByCode(EPostType.SINGLE.name())).thenReturn(Optional.of(singlePostType));

        PostType comboPostType = new PostType();
        comboPostType.setId(2L);
        comboPostType.setCode(EPostType.COMBO.name());
        comboPostType.setName("Bài viết combo");
        when(postTypeRepository.findByCode(EPostType.COMBO.name())).thenReturn(Optional.of(comboPostType));

        PostType groupPostType = new PostType();
        groupPostType.setId(3L);
        groupPostType.setCode(EPostType.GROUP.name());
        groupPostType.setName("Bài viết group");
        when(postTypeRepository.findByCode(EPostType.GROUP.name())).thenReturn(Optional.of(groupPostType));


        // init image
        Image image = new Image();
        image.setId(1L);
        image.setUrl("URL");
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        Image image1 = new Image();
        image1.setId(2L);
        image1.setUrl("URL 1");
        when(imageRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(image, image1));

        // init display group
        DisplayGroup displayGroup = new DisplayGroup();
        displayGroup.setId(1L);
        when(displayGroupRepository.findById(1L)).thenReturn(Optional.of(displayGroup));

        DisplayGroup displayGroup1 = new DisplayGroup();
        displayGroup1.setId(2L);
        when(displayGroupRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(displayGroup, displayGroup1));

        // init category
        Category category = new Category();
        category.setId(1L);
        category.setName("Category name");
        displayGroup.setIsMain(true);
        category.setDisplayGroups(Collections.singletonList(displayGroup));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // product type
        ProductType hasUnitPriceType = new ProductType();
        hasUnitPriceType.setId(1L);
        hasUnitPriceType.setCode(EProductType.HAS_UNIT_PRICE.name());
        when(productTypeRepository.findByCode(EProductType.HAS_UNIT_PRICE.name())).thenReturn(Optional.of(hasUnitPriceType));

        ProductType noUnitPriceType = new ProductType();
        noUnitPriceType.setId(2L);
        noUnitPriceType.setCode(EProductType.NO_UNIT_PRICE.name());
        when(productTypeRepository.findByCode(EProductType.NO_UNIT_PRICE.name())).thenReturn(Optional.of(noUnitPriceType));

        // init sub option
        SubOptionType subOptionType = new SubOptionType();
        subOptionType.setCode(ESubOptionType.COMBOBOX);
        subOptionType.setId(1L);
        when(subOptionTypeRepository.findById(1L)).thenReturn(Optional.of(subOptionType));


        SubOptionType subOptionType1 = new SubOptionType();
        subOptionType1.setCode(ESubOptionType.RADIOBOX);
        subOptionType1.setId(2L);
        when(subOptionTypeRepository.findById(2L)).thenReturn(Optional.of(subOptionType1));

        // init product
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setCode("product1");
        product.setCategory(categoryRepository.findById(1L).orElse(null));
        product.setType(productTypeRepository.findByCode(EProductType.HAS_UNIT_PRICE.name()).orElse(null));
        product.setUnitPrice(BigDecimal.valueOf(10_000_000));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    }

    @Test
    public void shouldConvertPostRequestToPostSuccessWithSinglePost() {

        String postName = "This is test post";

        PostRequest request = new PostRequest();
        request.setName(postName);
        request.setPostType(EPostType.SINGLE);
        request.setCategoryId(1L);
        request.setAvatarImageId(1L);
        request.setPostStatus(EPostStatus.ALIVE);
        request.setDisplayGroupIds(Arrays.asList(1L, 2L));

        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setId(1L);
        imageRequest.setNumericalOrder(2);

        ImageRequest imageRequest1 = new ImageRequest();
        imageRequest1.setId(2L);
        imageRequest1.setNumericalOrder(1);

        request.setImages(Arrays.asList(imageRequest, imageRequest1));

        request.setAvatarImageId(1L);

        request.setProductType(EProductType.HAS_UNIT_PRICE);
        request.setIsVisible(false);
        request.setProductId(1L);
        request.setIsMainPost(false);

        Post post = postService.convertPostRequestToOriginalPost(request);

        assertEquals(post.getName(), request.getName());
        assertEquals(post.getProductType().getCode(), EProductType.HAS_UNIT_PRICE.name());
        assertEquals(post.getPostImages().size(), 2);
        assertEquals(post.getCategory().getId(), 1L);
        assertEquals(post.getPostDisplayGroups().size(), 2);
        assertEquals(post.getAvatarImage().getId(), 1L);
        assertEquals(post.getPostType().getCode(), EPostType.SINGLE.name());
        assertNull(post.getPostSubPost());
    }

    @Test
    public void shouldConvertPostRequestToPostSuccessWithComboPost() {

        String postName = "This is test post";

        PostRequest request = new PostRequest();
        request.setName(postName);
        request.setPostType(EPostType.COMBO);
        request.setCategoryId(1L);
        request.setAvatarImageId(1L);
        request.setPostStatus(EPostStatus.ALIVE);
        request.setDisplayGroupIds(Arrays.asList(1L, 2L));

        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setId(1L);
        imageRequest.setNumericalOrder(2);

        ImageRequest imageRequest1 = new ImageRequest();
        imageRequest1.setId(2L);
        imageRequest1.setNumericalOrder(1);

        request.setImages(Arrays.asList(imageRequest, imageRequest1));

        request.setAvatarImageId(1L);

        request.setProductType(EProductType.HAS_UNIT_PRICE);
        request.setIsVisible(false);
        request.setProductId(1L);
        request.setIsMainPost(false);

        Post post = postService.convertPostRequestToOriginalPost(request);

        assertEquals(post.getName(), request.getName());
        assertEquals(post.getProductType().getCode(), EProductType.HAS_UNIT_PRICE.name());
        assertEquals(post.getPostImages().size(), 2);
        assertEquals(post.getCategory().getId(), 1L);
        assertEquals(post.getPostDisplayGroups().size(), 2);
        assertEquals(post.getAvatarImage().getId(), 1L);
        assertEquals(post.getPostType().getCode(), EPostType.COMBO.name());
        assertNull(post.getPostSubPost());
    }


}
