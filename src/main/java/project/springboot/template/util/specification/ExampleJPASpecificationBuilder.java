package project.springboot.template.util.specification;

public class ExampleJPASpecificationBuilder {

//    public static CartItemSpecificationBuilder specifications() {
//        return new CartItemSpecificationBuilder();
//    }
//
//    private final List<Specification<CartItem>> specifications = new ArrayList<>();
//
//    public CartItemSpecificationBuilder byPostEqual(Post post) {
//        if (post == null) {
//            return this;
//        }
//        specifications.add((root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get(CartItem_.POST), post)
//        );
//        return this;
//    }
//
//    public CartItemSpecificationBuilder byCartEqual(Cart cart) {
//        if (cart == null) {
//            return this;
//        }
//        specifications.add((root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get(CartItem_.cart), cart)
//        );
//        return this;
//    }
//
//    public Specification<CartItem> build() {
//        return specifications.stream()
//                .filter(Objects::nonNull)
//                .reduce(all(), Specification::and);
//    }
//
//    private Specification<CartItem> all() {
//        return Specification.where(null);
//    }

}
