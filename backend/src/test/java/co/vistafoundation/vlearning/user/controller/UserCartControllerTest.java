package co.vistafoundation.vlearning.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.model.UserCart;
import co.vistafoundation.vlearning.user.repository.UserCartRepository;
import co.vistafoundation.vlearning.user.service.UserCartService;

@SpringBootTest
class UserCartControllerTest {

	@Autowired
	private UserCartService userCartService;

	@MockBean
	UserCartRepository userCartRepository;

	@InjectMocks
	private UserCartController UserCartController;

	UserCart userCart = new UserCart();

	List<UserCart> userCartList = new ArrayList<UserCart>();

	@BeforeEach
	public void setUp() {
		// Initializing Cart Items for one item-cart

		userCart.setProductName("Batch of 1 Student, Maths- Class 10");
		userCart.setProductCategory("Academic");
		userCart.setPurchaseLevel("Product");
		userCart.setPurchaseType("New");
		userCart.setSubscriptionType("Monthly");
		userCart.setPurchaseAmount(2500f);
		userCart.setIdStudent(1L);
		userCart.setIdBatch(1L);
		userCart.setIdProduct(1L);
		userCart.setIdProductGroup(1L);
		userCart.setUserSurId(1L);

		UserCart userCartOutput = new UserCart();
		userCartOutput.setProductName("Batch of 1 Student, Maths- Class 10");
		userCartOutput.setProductCategory("Academic");
		userCartOutput.setPurchaseLevel("Product");
		userCartOutput.setPurchaseType("New");
		userCartOutput.setSubscriptionType("Monthly");
		userCartOutput.setPurchaseAmount(2500f);
		userCartOutput.setIdStudent(1L);
		userCartOutput.setIdBatch(1L);
		userCartOutput.setIdProduct(1L);
		userCartOutput.setIdProductGroup(1L);
		userCartOutput.setIdUserCart(1L);
		userCartOutput.setUserSurId(1L);

		Mockito.when(userCartRepository.save(userCart)).thenReturn(userCartOutput);

		List<UserCart> userCartListOutput = new ArrayList<UserCart>();

		UserCart userCart1 = new UserCart();
		userCart1.setProductName("Batch of 1 Student, Maths- Class 10");
		userCart1.setProductCategory("Academic");
		userCart1.setPurchaseLevel("Product");
		userCart1.setPurchaseType("New");
		userCart1.setSubscriptionType("Monthly");
		userCart1.setPurchaseAmount(3000f);
		userCart1.setIdStudent(1L);
		userCart1.setIdBatch(1L);
		userCart1.setIdProduct(1L);
		userCart1.setIdProductGroup(1L);
		userCart1.setUserSurId(1L);

		UserCart userCart2 = new UserCart();
		userCart2.setProductName("Batch of 1 Student, Maths- Class 9");
		userCart2.setProductCategory("Academic");
		userCart2.setPurchaseLevel("Product");
		userCart2.setPurchaseType("New");
		userCart2.setSubscriptionType("Yearly");
		userCart2.setPurchaseAmount(30000f);
		userCart2.setIdBatch(1L);
		userCart2.setIdProduct(2L);
		userCart2.setIdProductGroup(1L);
		userCart2.setIdStudent(1L);
		userCart2.setUserSurId(1L);

		userCartList.add(userCart1);
		userCartList.add(userCart2);

		UserCart userCart1Output = new UserCart();
		userCart1Output.setProductName("Batch of 1 Student, Maths- Class 10");
		userCart1Output.setProductCategory("Academic");
		userCart1Output.setPurchaseLevel("Product");
		userCart1Output.setPurchaseType("New");
		userCart1Output.setSubscriptionType("Monthly");
		userCart1Output.setPurchaseAmount(3000f);
		userCart1Output.setIdStudent(1L);
		userCart1Output.setIdBatch(1L);
		userCart1Output.setIdProduct(1L);
		userCart1Output.setIdProductGroup(1L);
		userCart1Output.setUserSurId(1L);
		userCart1Output.setIdUserCart(2L);

		UserCart userCart2Output = new UserCart();
		userCart2Output.setProductName("Batch of 1 Student, Maths- Class 9");
		userCart2Output.setProductCategory("Academic");
		userCart2Output.setPurchaseLevel("Product");
		userCart2Output.setPurchaseType("New");
		userCart2Output.setSubscriptionType("Yearly");
		userCart2Output.setPurchaseAmount(30000f);
		userCart2Output.setIdBatch(1L);
		userCart2Output.setIdProduct(2L);
		userCart2Output.setIdProductGroup(1L);
		userCart2Output.setIdStudent(1L);
		userCart2Output.setUserSurId(1L);
		userCart2Output.setIdUserCart(3L);

		userCartListOutput.add(userCart1Output);
		userCartListOutput.add(userCart2Output);

		Mockito.when(userCartRepository.saveAll(userCartList)).thenReturn(userCartListOutput);

		// for delete All functions
		List<Long> idUserCarts = new ArrayList<Long>();
		idUserCarts.add(2L);
		idUserCarts.add(3L);

		Mockito.when(userCartRepository.findByIdUserCartIn(idUserCarts)).thenReturn(userCartListOutput)
				.thenReturn(null);

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void createCartTest() throws Exception {

		Document document = userCartService.createCart(userCart);
		UserCart uc = (UserCart) document.getData();
		assertEquals(userCart.getIdBatch(), uc.getIdBatch());
		assertEquals(userCart.getIdProduct(), uc.getIdProduct());
		assertEquals(userCart.getIdProductGroup(), uc.getIdProductGroup());
		assertEquals(userCart.getIdStudent(), uc.getIdStudent());
		assertEquals(1L, uc.getIdUserCart());
		assertEquals(userCart.getUserSurId(), uc.getUserSurId());
		assertEquals(userCart.getProductCategory(), uc.getProductCategory());
		assertEquals(userCart.getProductName(), uc.getProductName());
		assertEquals(userCart.getPurchaseLevel(), uc.getPurchaseLevel());
		assertEquals(userCart.getPurchaseType(), uc.getPurchaseType());
		assertEquals(userCart.getSubscriptionType(), uc.getSubscriptionType());
		assertEquals(userCart.getPurchaseAmount(), uc.getPurchaseAmount());

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void createCartItemsTest() throws Exception {

		System.out.println(userCartList.get(0).getIdBatch());
		Document document = userCartService.createCartItems(userCartList);
		List<UserCart> uCartList = (List<UserCart>) document.getData();

		System.out.println(uCartList.get(0).getIdBatch());
		assertEquals(userCartList.size(), uCartList.size());
		assertEquals(userCartList.get(0).getIdBatch(), uCartList.get(0).getIdBatch());
		assertEquals(userCartList.get(0).getIdProduct(), uCartList.get(0).getIdProduct());
		assertEquals(userCartList.get(0).getIdProductGroup(), uCartList.get(0).getIdProductGroup());
		assertEquals(userCartList.get(0).getIdStudent(), uCartList.get(0).getIdStudent());
//		assertEquals(2L, uCartList.get(0).getIdUserCart());
		assertEquals(userCartList.get(0).getUserSurId(), uCartList.get(0).getUserSurId());
		assertEquals(userCartList.get(0).getProductCategory(), uCartList.get(0).getProductCategory());
		assertEquals(userCartList.get(0).getProductName(), uCartList.get(0).getProductName());
		assertEquals(userCartList.get(0).getPurchaseLevel(), uCartList.get(0).getPurchaseLevel());
		assertEquals(userCartList.get(0).getPurchaseType(), uCartList.get(0).getPurchaseType());
		assertEquals(userCartList.get(0).getSubscriptionType(), uCartList.get(0).getSubscriptionType());
		assertEquals(userCartList.get(0).getPurchaseAmount(), uCartList.get(0).getPurchaseAmount());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void updateCartTest() throws Exception {

		UserCart userCart = new UserCart();

		userCart.setProductName("Batch of 5 Students, Maths- Class 10");
		userCart.setProductCategory("Academic");
		userCart.setPurchaseLevel("Product");
		userCart.setPurchaseType("New");
		userCart.setPurchaseAmount(11000f);
		userCart.setSubscriptionType("quaterly");
		userCart.setIdBatch(1L);
		userCart.setIdProduct(3L);
		userCart.setIdProductGroup(1L);
		userCart.setIdStudent(1L);
		userCart.setIdUserCart(1L);
		userCart.setUserSurId(1L);

		UserCart updatedCart = new UserCart();

		updatedCart.setProductName("Batch of 5 Students, Maths- Class 10");
		updatedCart.setProductCategory("Academic");
		updatedCart.setPurchaseLevel("Product");
		updatedCart.setPurchaseType("New");
		updatedCart.setPurchaseAmount(3000f);
		updatedCart.setSubscriptionType("Monthly");
		updatedCart.setIdBatch(1L);
		updatedCart.setIdProduct(3L);
		updatedCart.setIdProductGroup(1L);
		updatedCart.setIdStudent(1L);
		updatedCart.setIdUserCart(1L);
		updatedCart.setUserSurId(1L);

		Mockito.when(userCartRepository.findByIdUserCart(1L)).thenReturn(userCart);

		Mockito.when(userCartRepository.save(userCart)).thenReturn(updatedCart);

		Document document = userCartService.updateCart(userCart);
		
		UserCart uc = (UserCart) document.getData();

		assertEquals(1L, uc.getIdBatch());
		assertEquals(3L, uc.getIdProduct());
		assertEquals(1L, uc.getIdProductGroup());
		assertEquals(1L, uc.getIdStudent());
		assertEquals(1L, uc.getIdUserCart());
		assertEquals(1L, uc.getUserSurId());
		assertEquals("Academic", uc.getProductCategory());
		assertEquals("Batch of 5 Students, Maths- Class 10", uc.getProductName());
		assertEquals("Product", uc.getPurchaseLevel());
		assertEquals("New", uc.getPurchaseType());
		assertEquals("Monthly", uc.getSubscriptionType());
		assertEquals(3000f, uc.getPurchaseAmount());
	}

	@SuppressWarnings({ "rawtypes" })
	@Test
	public void deleteCartItemTest() throws Exception {

		UserCart userCartOutput = new UserCart();
		userCartOutput.setProductName("Batch of 1 Student, Maths- Class 10");
		userCartOutput.setProductCategory("Academic");
		userCartOutput.setPurchaseLevel("Product");
		userCartOutput.setPurchaseType("New");
		userCartOutput.setSubscriptionType("Monthly");
		userCartOutput.setPurchaseAmount(2500f);
		userCartOutput.setIdStudent(1L);
		userCartOutput.setIdBatch(1L);
		userCartOutput.setIdProduct(1L);
		userCartOutput.setIdProductGroup(1L);
		userCartOutput.setIdUserCart(1L);
		userCartOutput.setUserSurId(1L);

		Long idUserCart = 1L;

		Mockito.when(userCartRepository.findByIdUserCart(idUserCart)).thenReturn(userCartOutput).thenReturn(null);

		Document doc = userCartService.deleteCartItem(idUserCart);

		assertEquals(null, doc.getData());
		assertEquals("Cart item deleted successfully!", doc.getMessage());
		assertEquals(200, doc.getStatusCode());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void deleteAllCartItemsTest() throws Exception {
		List<Long> idUserCarts = new ArrayList<Long>();
		idUserCarts.add(2L);
		idUserCarts.add(3L);

		Document doc = userCartService.deleteAllCartItems(idUserCarts);

		assertEquals(null, doc.getData());
		assertEquals("All cart items deleted successfully!", doc.getMessage());
		assertEquals(200, doc.getStatusCode());

	}

}
