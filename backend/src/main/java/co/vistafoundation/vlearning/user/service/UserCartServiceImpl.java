/**
 * 
 */
package co.vistafoundation.vlearning.user.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.vistafoundation.vlearning.auth.model.User;
import co.vistafoundation.vlearning.auth.repository.UserRepository;
import co.vistafoundation.vlearning.auth.security.UserPrincipal;
import co.vistafoundation.vlearning.batch.model.Batch;
import co.vistafoundation.vlearning.batch.repository.BatchRepository;
import co.vistafoundation.vlearning.batch.repository.SpecialOfferProductRepository;
import co.vistafoundation.vlearning.batch.service.BatchService;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.exception.AppException;
import co.vistafoundation.vlearning.product.model.Product;
import co.vistafoundation.vlearning.product.repository.ProductRepository;
import co.vistafoundation.vlearning.specialoffer.model.SpecialOffer;
import co.vistafoundation.vlearning.specialoffer.repository.SpecialOfferRepository;
import co.vistafoundation.vlearning.subscription.dto.PromoCodeDTO;
import co.vistafoundation.vlearning.subscription.model.StudentSubscription;
import co.vistafoundation.vlearning.subscription.repository.StudentSubscriptionRepository;
import co.vistafoundation.vlearning.user.dto.BatchPromoCodeDTO;
import co.vistafoundation.vlearning.user.dto.UserCartDTO;
import co.vistafoundation.vlearning.user.dto.UserCartResponseDTO;
import co.vistafoundation.vlearning.user.model.UserCart;
import co.vistafoundation.vlearning.user.repository.UserCartRepository;

/**
 * @author vk
 *
 */
@Service
public class UserCartServiceImpl implements UserCartService {

	@Autowired
	UserCartRepository userCartRepository;

	@Autowired
	StudentSubscriptionRepository studentSubscriptionRepository;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SpecialOfferRepository specialOfferRepository;
	
	@Autowired
	SpecialOfferProductRepository specialOfferProductRepository;
	
	@Autowired
	BatchService batchService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	
	
	public Document<?> createCart(UserCart userCart) {
		Document<UserCart> document = new Document<>();
		
		try {
			
		User loggedInUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
		}

		if (loggedInUser == null)
			throw new AppException("Invalid User");

		if (!userCart.getUserSurId().equals(loggedInUser.getUserSurId())) {
			document.setData(null);
			document.setMessage("User dosent have access to add this item.");
			document.setStatusCode(HttpStatus.FORBIDDEN.value());
			return document;

		}
		
		if (userCart.getProductCategory().equals("BATCH")) {
			StudentSubscription studentSubscription = studentSubscriptionRepository
					.findByIdBatchAndActiveFlagAndIdStudent(userCart.getIdBatch(), true, userCart.getIdStudent());
			if (studentSubscription != null) {
				document.setData(null);
				document.setMessage("You have already active subsctiption with "+userCart.getProductName());						
				document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				return document;
			}
		}
		userCart = userCartRepository.save(userCart);
		if (userCart != null) {
			document.setData(userCart);
			document.setMessage("Product item added to cart successfully");
			document.setStatusCode(201);
		} else {
			document.setData(null);
			document.setMessage("Error while Product item adding to cart ");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		
		}
		catch(Exception e) 
		{
	    document.setData(null);
		document.setMessage(e.getLocalizedMessage());
		document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
		}
		
		return document;
	}

	@Override
	public Document<?> createCartItems(List<UserCart> userCart) {
		Document<List<UserCartResponseDTO>> document = new Document<>();
		try {
			
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			for( UserCart u : userCart) {
				
				if (!u.getUserSurId().equals(loggedInUser.getUserSurId())) {
					document.setData(null);
					document.setMessage("User dosent have access to add this item to cart.");
					document.setStatusCode(HttpStatus.FORBIDDEN.value());
					return document;

				}
			};
			
			
		List<UserCartResponseDTO> ucrDTOs = new ArrayList<>();
		for (UserCart cartItem : userCart) {
			if (cartItem.getProductCategory().equals("BATCH")) {
				StudentSubscription studentSubscription = studentSubscriptionRepository
						.findByIdBatchAndActiveFlagAndIdStudent(cartItem.getIdBatch(), true, cartItem.getIdStudent());
				if (studentSubscription != null) {
					document.setData(null);
					if (userCart.size()>1) {
						document.setMessage("You have already active subsctiption with selected batches");
					}else {
						document.setMessage("You have already active subsctiption with "+cartItem.getProductName());						
					}
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return document;
				}
			}
		}
		List<UserCart> list = userCartRepository.saveAll(userCart);
		if (!list.isEmpty()) {
			list.forEach(item -> {
				if (item.getIdBatch() != null) {
					UserCartResponseDTO uResponseDTO = new UserCartResponseDTO();
					BeanUtils.copyProperties(item, uResponseDTO);
					Batch batch = batchRepository.findByIdBatch(item.getIdBatch());
					if (batch != null) {
						uResponseDTO.setBatch(batch);
					}
					ucrDTOs.add(uResponseDTO);
				}
			});
			document.setData(ucrDTOs);
			document.setMessage("Product item added to cart successfully");
			document.setStatusCode(201);
		} else {
			document.setData(null);
			document.setMessage("Error while product item adding to cart ");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		
		}
		catch(Exception e) 
		{
	    document.setData(null);
		document.setMessage(e.getLocalizedMessage());
		document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
		}
		
		return document;
		
	}
	
	@Override
	public Document<?> cartItems(List<UserCart> userCart) {
		
		Document<List<UserCartResponseDTO>> document = new Document<>();
		
		try {
			
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			for( UserCart u : userCart) {
				
				if (!u.getUserSurId().equals(loggedInUser.getUserSurId())) {
					document.setData(null);
					document.setMessage("User dosent have access to add this item to cart.");
					document.setStatusCode(HttpStatus.FORBIDDEN.value());
					return document;

				}
			};
			
		
		List<UserCartResponseDTO> ucrDTOs = new ArrayList<>();
		for (UserCart cartItem : userCart) {
			if (cartItem.getProductCategory().equals("BATCH")) {
				StudentSubscription studentSubscription = studentSubscriptionRepository
						.findByIdBatchAndActiveFlagAndIdStudent(cartItem.getIdBatch(), true, cartItem.getIdStudent());
				if (studentSubscription != null) {
					document.setData(null);
					if (userCart.size()>1) {
						document.setMessage("You have already active subsctiption with selected batches");
					}else {
						document.setMessage("You have already active subsctiption with "+cartItem.getProductName());						
					}
					document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
					return document;
				}
			}
		}
		if (!userCart.isEmpty()) {
			userCart.forEach(item -> {
				if (item.getIdBatch() != null) {
					UserCartResponseDTO uResponseDTO = new UserCartResponseDTO();
					BeanUtils.copyProperties(item, uResponseDTO);
					Batch batch = batchRepository.findByIdBatch(item.getIdBatch());
					if (batch != null) {
						uResponseDTO.setBatch(batch);
					}
					ucrDTOs.add(uResponseDTO);
				}
			});
			document.setData(ucrDTOs);
			document.setMessage("Product items fetching successfully");
			document.setStatusCode(201);
		} else {
			document.setData(null);
			document.setMessage("Error while fetching product items");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		
		}
		catch(Exception e) 
		{
	    document.setData(null);
		document.setMessage(e.getLocalizedMessage());
		document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
		}
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document updateCart(UserCart userCart) {
		Document document = new Document();
		
		try {
			
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");

			if (!userCart.getUserSurId().equals(loggedInUser.getUserSurId())) {
				document.setData(null);
				document.setMessage("User dosent have access to add this item.");
				document.setStatusCode(HttpStatus.FORBIDDEN.value());
				return document;

			}
			
		UserCart existingUserCart = userCartRepository.findByIdUserCart(userCart.getIdUserCart());
		if (existingUserCart == null)
			throw new AppException("cart item details not found!");
		existingUserCart.setCreatedBy(existingUserCart.getCreatedBy());
		existingUserCart.setCreatedAt(existingUserCart.getCreatedAt());
		existingUserCart.setIdBatch(userCart.getIdBatch());
		existingUserCart.setIdProduct(userCart.getIdProduct());
		existingUserCart.setIdProductGroup(userCart.getIdProductGroup());
		existingUserCart.setIdStudent(userCart.getIdStudent());
		existingUserCart.setProductName(userCart.getProductName());
		existingUserCart.setPurchaseAmount(userCart.getPurchaseAmount());
		existingUserCart.setPurchaseLevel(userCart.getPurchaseLevel());
		existingUserCart.setPurchaseType(userCart.getPurchaseType());
		existingUserCart.setSubscriptionType(userCart.getSubscriptionType());
		existingUserCart.setUserSurId(userCart.getUserSurId());
		UserCart response = userCartRepository.save(existingUserCart);
		if (response != null) {
			document.setData(response);
			document.setMessage("Product item updated to cart successfully");
			document.setStatusCode(HttpStatus.OK.value());
			
		} else {
			document.setData(null);
			document.setMessage("Error while updating product item");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
		}
		
		}
		
		catch(Exception e) 
		{
	    document.setData(null);
		document.setMessage(e.getLocalizedMessage());
		document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
		}
		
		return document;
	}

	@Override

	public Document<?> findAllByUser(Long userSurId) {
		Document<List<UserCartResponseDTO>> document = new Document<>();
		List<UserCartResponseDTO> ucrDTOs = new ArrayList<>();

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         UserPrincipal userPrincipal = null;
       
         if (!(authentication instanceof AnonymousAuthenticationToken)) {
             userPrincipal = (UserPrincipal) authentication.getPrincipal();
         }
         if (userPrincipal == null)
             throw new AppException("Invalid User");

		List<UserCart> list = userCartRepository.findByUserSurId(userPrincipal.getUserSurId());
		list.forEach(item -> {
			if (item.getIdBatch() != null) {
				UserCartResponseDTO uResponseDTO = new UserCartResponseDTO();
				BeanUtils.copyProperties(item, uResponseDTO);
				Batch batch = batchRepository.findByIdBatch(item.getIdBatch());
				if (batch != null) {
					uResponseDTO.setBatch(batch);
				}
				ucrDTOs.add(uResponseDTO);
			}
		});
		if (ucrDTOs.size() != 0) {
			document.setData(ucrDTOs);
			document.setMessage("Cart data fetched successfully");
			document.setStatusCode(HttpStatus.OK.value());
			return document;
		} else if (ucrDTOs.size() == 0) {
			document.setData(null);
			document.setMessage("Cart data not found");
			document.setStatusCode(HttpStatus.NOT_FOUND.value());
			return document;
		} else {
			document.setData(null);
			document.setMessage("Error while fetching cart data");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return document;
		}
	}

	@Override
	public Document<?> findAllByUser2(Long userSurId) {
		
		Document<UserCartDTO> document = new Document<>();
		
		try {
			UserCartDTO cartDto = new UserCartDTO();

			List<UserCartResponseDTO> ucrDTOs = new ArrayList<>();

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	         UserPrincipal userPrincipal = null;
	       
	         if (!(authentication instanceof AnonymousAuthenticationToken)) {
	             userPrincipal = (UserPrincipal) authentication.getPrincipal();
	         }
	         if (userPrincipal == null)
	             throw new AppException("Invalid User");

			List<UserCart> cartList = userCartRepository.findByUserSurId(userPrincipal.getUserSurId());
			if (cartList.isEmpty())
				throw new AppException("No Items In Cart");
			cartList.forEach(item -> {
				if (item.getIdBatch() != null) {
					UserCartResponseDTO uResponseDTO = new UserCartResponseDTO();
					BeanUtils.copyProperties(item, uResponseDTO);
					Batch batch = batchRepository.findByIdBatch(item.getIdBatch());
					if (batch != null) {
						uResponseDTO.setBatch(batch);
					}
					ucrDTOs.add(uResponseDTO);
				}
			});
			boolean checkout = false;
			List<UserCart> duplicates = new ArrayList<>();
			Set<UserCart> set = new HashSet<>();
			set.addAll(cartList);
			for (UserCart item : cartList) {
				if (set.contains(item)) {
					set.remove(item);
				} else {
					duplicates.add(item);
				}
			}
			if (duplicates.size() > 0)
				checkout = false;
			else
				checkout = true;
			cartDto.setCartList(ucrDTOs);
			cartDto.setDuplicateCartList(duplicates);
			cartDto.setAllowToCheckout(checkout);
			document.setData(cartDto);
			document.setMessage("Request Successful.");
			document.setStatusCode(200);
		} catch (Exception exp) {
			document.setData(null);
			document.setMessage(exp.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return document;
	}
/**
 * 
 * Updated by Sajini S
 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document deleteCartItem(Long idUserCart) {
		Document document = new Document();
		
		try {
		
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
		UserCart userCart = userCartRepository.findByIdUserCartAndUserSurId(idUserCart,loggedInUser.getUserSurId());
		
		if (userCart == null) {
			document.setData(null);
			document.setMessage("cart item details not found!");
			document.setStatusCode(404);
			return document;
		}
		
		Batch batch = batchRepository.findByIdBatch(userCart.getIdBatch());
		
		if(batch.getIdBatchGroup()!=null) {
			List<UserCart> ucList = userCartRepository.findByUniqueIdBatch();
			

			userCartRepository.deleteInBatch(ucList);
			
		}else {
			userCartRepository.delete(userCart);
		}
		
		
		
		document.setData(null);
		document.setMessage("Cart item deleted successfully!");
		document.setStatusCode(200);

		
		}
		catch (Exception exp) {
			document.setData(null);
			document.setMessage(exp.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document deleteAllCartItems(List<Long> idUserCarts) {
		
		Document document = new Document();
		
		try {
		User loggedInUser = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
		}

		if (loggedInUser == null)
			throw new AppException("Invalid User");
		
		List<UserCart> deleteList = userCartRepository.findByIdUserCartInAndUserSurId(idUserCarts,loggedInUser.getUserSurId());
		if (deleteList.size() == 0) {
			document.setData(null);
			document.setMessage("cart item details not found!");
			document.setStatusCode(404);
			return document;
		}
		userCartRepository.deleteInBatch(deleteList);
		document.setData(null);
		document.setMessage("All cart items deleted successfully!");
		document.setStatusCode(200);
		return document;
		
		}
		
		catch (Exception exp) {
			document.setData(null);
			document.setMessage(exp.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return document;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Document updateBatchIfCheckoutCancel(List<UserCartResponseDTO> userCarts) {
		Document document = new Document();
		
		try {
			
		List<UserCartResponseDTO> list = new ArrayList();
		if (userCarts.size() != 0) {
			
			User loggedInUser = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (!(authentication instanceof AnonymousAuthenticationToken)) {
				UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
				loggedInUser = userRepository.findByUserSurId(userPrincipal.getUserSurId());
			}

			if (loggedInUser == null)
				throw new AppException("Invalid User");
			
			for(UserCartResponseDTO u : userCarts) {
				
				if(!u.getUserSurId().equals(loggedInUser.getUserSurId()))
					throw new AppException("Invalid user info found in user cart item.");	
			};
			
			for (UserCartResponseDTO userCartResponseDTO : userCarts) {
				Batch batch = batchRepository.findByIdBatch(userCartResponseDTO.getIdBatch());
				if (batch!=null) {
					if (batch.getUpdatedBy().equals(userCartResponseDTO.getUserSurId())) {						
						batch.setPaymentStatus(null);
						batch = batchRepository.save(batch);
					}
					userCartResponseDTO.setBatch(batch);
					list.add(userCartResponseDTO);
				}
			}
			document.setData(list);
			document.setMessage("batch payment status updated successful");
			document.setStatusCode(HttpStatus.OK.value());
		} else {
			document.setData(null);
			document.setMessage("Error while batch payment status updation");
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return document;
		
		}
		
		catch (Exception exp) {
			document.setData(null);
			document.setMessage(exp.getLocalizedMessage());
			document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
		return document;
	}

	@Override
    public Document<PromoCodeDTO> applyPromoCode(String promoCode) {
        Document<PromoCodeDTO> document = new Document<>();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal userPrincipal = null;
            List<UserCartResponseDTO> promoAppliedList = new ArrayList<>();
            List<UserCartResponseDTO> nonPromoList = new ArrayList<>();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                userPrincipal = (UserPrincipal) authentication.getPrincipal();
            }
            if (userPrincipal == null)
                throw new AppException("Invalid User");
            SpecialOffer so = specialOfferRepository.findByCouponCodeAndActiveFlagAndSpecialOfferEndDateGreaterThanEqual(promoCode, true,LocalDate.now());
            if (so == null)
                throw new AppException("Invalid Promocode");
            List<UserCart> userCartList = userCartRepository.findByUserSurId(userPrincipal.getUserSurId());
            
            List<Long> idBatches = new ArrayList<>();
            
            // <Long> idProducts = new ArrayList<>();
            if (userCartList.isEmpty())
                throw new AppException("User Cart is Empty.");
            
            List<UserCartResponseDTO> userCartListDto = new ArrayList<>();
            for (UserCart userCart : userCartList) {
            	UserCartResponseDTO rcrDTO = new UserCartResponseDTO();
            	BeanUtils.copyProperties(userCart, rcrDTO);
            	userCartListDto.add(rcrDTO);
			}
            idBatches = userCartList.stream().map(UserCart::getIdBatch).distinct().collect(Collectors.toList());
            
            //added by vk, for checking batch timing conflicts in cart page while applying promo code
            Document<Boolean> responseBatchConflict=batchService.checkConflictsOnBatch(idBatches);
            if (responseBatchConflict.getStatusCode()==409) {
            	document.setData(null);
                document.setMessage(responseBatchConflict.getMessage());
                document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
            
            //idProducts = userCartList.stream().map(UserCart::getIdProduct).distinct().collect(Collectors.toList());
            List<BatchPromoCodeDTO> bpclist = specialOfferRepository.findByPromocodeByIdBatchs(idBatches);
            
            if (bpclist.isEmpty()) {
                throw new AppException("These batches doesn't contain any special offer");
            }
   
            //order by monthly value
            bpclist.sort(Comparator.comparing(BatchPromoCodeDTO::getMonthlySubcrAmt));
            
            Long finalPrice = 0l;
            Long actualPrice = 0l;
            /**
             * Iterating UserCart items 
             * for summing the value of total amount
             */
            for (UserCart uc : userCartList) {
                Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                if (product == null)
                    throw new AppException(" Invalid id product found in user cart item.");
                Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH") ? product.getMonthlySubcrAmt()
                        : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt() : 0L);
                actualPrice += itemNetPrice;
            }
            
            userCartList.sort(Comparator.comparing(UserCart::getPurchaseAmount));
            switch (promoCode) {
            // comparing value of variable against each case
            case "6For2000":
            { // check for duplicates available
                List<StudentSubscription> ss = studentSubscriptionRepository
                        .findByUserSurIdAndIdSpecialOfferAndSpecialOfferFlagAndActiveFlag(userPrincipal.getUserSurId(),
                                so.getIdSpecialOffer(), true,true);
                // check whether user already used the coupon code
                if (!ss.isEmpty())
                    throw new AppException("The code you entered has already been redeemed");
                
                // if user cart contains duplicate items , throw error 
                if (userCartList.size() != idBatches.size())
                    throw new AppException("There is Duplicate items available in your cart , You cannot apply this promo code.");
                int count = 0;
                List<Long> finalIdProducts = new ArrayList<>();
                
                // get final distinct id products  
                finalIdProducts = bpclist.stream().map(BatchPromoCodeDTO::getIdProduct).distinct().collect(Collectors.toList());
                
//              System.out.println("cart size "+userCartList.size());
//              System.out.println("unique batch "+finalIdProducts.size());
                
                
                if (userCartList.size() != finalIdProducts.size() )
                    
                {    // object will be helpful during the duplicate id product available in cart 
                     List<Long> tempIdProductList = new ArrayList<>();
                     
                     // check idProduct as minmum 6 unique id products 
                    if (finalIdProducts.size()>=6) 
                    {
                        
                        for (UserCartResponseDTO uc : userCartListDto) 
                        {
                            //idProduct already available in tempIdProductList 
                            if (tempIdProductList.contains(uc.getIdProduct())) 
                            {  // adding the product to nonPromolist since , this id product promo code is already applied
                            	uc.setIdSpecialOffer(null);
                                nonPromoList.add(uc);
                                Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                                if (product == null)
                                    throw new AppException(" Invalid id product found in user cart item.");
                                Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH")
                                        ? product.getMonthlySubcrAmt()
                                        : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                                : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
                                                        : 0L);
                                finalPrice += itemNetPrice;
                                
                            }
                            else 
                            { 
                                List<BatchPromoCodeDTO> ucList = bpclist.stream().filter(u -> u.getIdBatch().equals(uc.getIdBatch()) )
                            .collect(Collectors.toList());
                                
                               // add idProduct to tempIdProductList
                                tempIdProductList.add(uc.getIdProduct());
                                BatchPromoCodeDTO bpc = ucList.get(0);
                                
                                //check the promo is applicable for and batch of 5
                                if ((bpc.getCouponCode().equalsIgnoreCase("6For2000")
                                        && !bpc.getProductLineCode().equalsIgnoreCase("BATCH_5")))
                                {
                                	uc.setIdSpecialOffer(null);
                                    nonPromoList.add(uc);
                                    Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                                    if (product == null)
                                        throw new AppException(" Invalid id product found in user cart item.");
                                    Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH")
                                            ? product.getMonthlySubcrAmt()
                                            : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                                    : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
                                                            : 0L);
                                    finalPrice += itemNetPrice;
                                }
                                if (bpc.getCouponCode().equalsIgnoreCase("6For2000")
                                        && bpc.getProductLineCode().equalsIgnoreCase("BATCH_5")) 
                                {
                                    
                                    if (uc.getSubscriptionType().equals("MONTH"))
                                        count++;
                                    
                                    // if item count is more then 6 , it will be treated as non promo items 
                                    if (count > 6) {
                                        Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                                        if (product == null)
                                            throw new AppException(" Invalid id product found in user cart item.");
                                        Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH")
                                                ? product.getMonthlySubcrAmt()
                                                : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                                        : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
                                                                : 0L);
                                        finalPrice += itemNetPrice;
                                        uc.setIdSpecialOffer(null);
                                        nonPromoList.add(uc);
                                    } else {
                                    	uc.setIdSpecialOffer(so.getIdSpecialOffer());
                                        promoAppliedList.add(uc);
                                    }
                                    
                                    
                                }
                                
                            }
                        }
                        finalPrice += 2000l; // adding default value for the promo-code '6FOR2000'
                        PromoCodeDTO promoCodeDTO = new PromoCodeDTO();
                        promoCodeDTO.setAppliedPromoCode(promoCode);
                        promoCodeDTO.setPromoAppliedList(promoAppliedList);
                        promoCodeDTO.setNonPromoList(nonPromoList);
                        promoCodeDTO.setTotalAmount(finalPrice);
                        promoCodeDTO.setActualAmount(actualPrice);
                        promoCodeDTO.setPromoCodeValue(2000L);
                        promoCodeDTO.setNonPromoItemsAmount(finalPrice - 2000l);
                        document.setData(promoCodeDTO);
                        document.setMessage("Promo code applied successfully!");
                        document.setStatusCode(HttpStatus.OK.value());  
                        
                    }
                    else 
                    {  //unique product having less then 6 items will not applicable for this promo code. 
                        
                        for (UserCartResponseDTO uc : userCartListDto) {
                            Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                            if (product == null)
                                throw new AppException(" Invalid id product found in user cart item.");
                            Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH") ? product.getMonthlySubcrAmt()
                                    : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                            : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt() : 0L);
                            finalPrice += itemNetPrice;
                            uc.setIdSpecialOffer(null);
                            nonPromoList.add(uc);
                        }
                        
                        PromoCodeDTO promoCodeDTO = new PromoCodeDTO();
                        promoCodeDTO.setAppliedPromoCode(promoCode);
                        promoCodeDTO.setPromoAppliedList(promoAppliedList);
                        promoCodeDTO.setNonPromoList(nonPromoList);
                        promoCodeDTO.setTotalAmount(finalPrice);
                        promoCodeDTO.setActualAmount(actualPrice);
                        promoCodeDTO.setPromoCodeValue(0L);
                        promoCodeDTO.setNonPromoItemsAmount(finalPrice);
                        document.setData(promoCodeDTO);
                        document.setMessage("This PromoCode is not applicpable for selected combination course.");
                        document.setStatusCode(HttpStatus.OK.value());
                        
                    }
                    
                    
                }
                else 
                {
                    for (BatchPromoCodeDTO b : bpclist) {
                        if ((b.getCouponCode().equalsIgnoreCase("6For2000")
                                && !b.getProductLineCode().equalsIgnoreCase("BATCH_5"))) {
                            List<UserCartResponseDTO> ucList = userCartListDto.stream().filter(u -> u.getIdBatch().equals(b.getIdBatch()) )
                                    .collect(Collectors.toList());
                            UserCartResponseDTO uc = ucList.get(0);
                            // Check Count for batch 5 for promo monthly basis.
                            uc.setIdSpecialOffer(null);
                            nonPromoList.add(uc);
                            Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                            if (product == null)
                                throw new AppException(" Invalid id product found in user cart item.");
                            Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH")
                                    ? product.getMonthlySubcrAmt()
                                    : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                            : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
                                                    : 0L);
                            finalPrice += itemNetPrice;
                        }
                        if (b.getCouponCode().equalsIgnoreCase("6For2000")
                                && b.getProductLineCode().equalsIgnoreCase("BATCH_5")) {
                            List<UserCartResponseDTO> ucList = userCartListDto.stream().filter(u -> u.getIdBatch().equals(b.getIdBatch()))
                                    .collect(Collectors.toList());
                            UserCartResponseDTO uc = ucList.get(0);
                            if (uc.getSubscriptionType().equals("MONTH"))
                                count++;
                            if (count > 6) {
                                Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                                if (product == null)
                                    throw new AppException(" Invalid id product found in user cart item.");
                                Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH")
                                        ? product.getMonthlySubcrAmt()
                                        : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                                : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt()
                                                        : 0L);
                                finalPrice += itemNetPrice;
                                uc.setIdSpecialOffer(null);
                                nonPromoList.add(uc);
                            } else {
                            	uc.setIdSpecialOffer(so.getIdSpecialOffer());
                                promoAppliedList.add(uc);
                            }
                        }
                    }
                    
                    if (count < 6) {
                        throw new AppException("This is promo code only applicable for 6 unique Course for Batch of 5 on monthly subscription.");
                    }
                    // Adding promo total value
                    finalPrice += 2000L;
                    
                    PromoCodeDTO promoCodeDTO = new PromoCodeDTO();
                    promoCodeDTO.setAppliedPromoCode(promoCode);
                    promoCodeDTO.setPromoAppliedList(promoAppliedList);
                    promoCodeDTO.setNonPromoList(nonPromoList);
                    promoCodeDTO.setTotalAmount(finalPrice);
                    promoCodeDTO.setActualAmount(actualPrice);
                    promoCodeDTO.setPromoCodeValue(2000L);
                    promoCodeDTO.setNonPromoItemsAmount(finalPrice - 2000L);
                    document.setData(promoCodeDTO);
                    document.setMessage("promo code applied successfully!");
                    document.setStatusCode(HttpStatus.OK.value());
                    
                }
                
        
                break;
                
            }
            default: {
                for (UserCartResponseDTO uc : userCartListDto) {
                    Product product = productRepository.findByIdProductAndActiveFlag(uc.getIdProduct(),Boolean.TRUE);
                    if (product == null)
                        throw new AppException(" Invalid id product found in user cart item.");
                    Long itemNetPrice = (long) (uc.getSubscriptionType().equals("MONTH") ? product.getMonthlySubcrAmt()
                            : uc.getSubscriptionType().equals("QUARTER") ? product.getQtrSubscrAmt()
                                    : uc.getSubscriptionType().equals("ANNUAL") ? product.getAnnualSubscrAmt() : 0L);
                    finalPrice += itemNetPrice;
                    uc.setIdSpecialOffer(null);
                    nonPromoList.add(uc);
                }
                
                PromoCodeDTO promoCodeDTO = new PromoCodeDTO();
                promoCodeDTO.setAppliedPromoCode(promoCode);
                promoCodeDTO.setPromoAppliedList(promoAppliedList);
                promoCodeDTO.setNonPromoList(nonPromoList);
                promoCodeDTO.setTotalAmount(finalPrice);
                promoCodeDTO.setActualAmount(actualPrice);
                promoCodeDTO.setPromoCodeValue(0L);
                promoCodeDTO.setNonPromoItemsAmount(finalPrice);
                document.setData(promoCodeDTO);
                document.setMessage("Promo code you entered seems to invalid, please enter correct code.");
                document.setStatusCode(HttpStatus.BAD_REQUEST.value());
            }
            }
        } catch (Exception exp) {
            document.setData(null);
            document.setMessage(exp.getLocalizedMessage());
            document.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return document;
    }

}
