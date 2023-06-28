package com.cognizant.subscription.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cognizant.subscription.client.AuthClient;
import com.cognizant.subscription.client.DrugClient;
import com.cognizant.subscription.client.RefillClient;
import com.cognizant.subscription.model.AuthResponse;
import com.cognizant.subscription.model.DrugDetails;
import com.cognizant.subscription.model.DrugLocationDetails;
import com.cognizant.subscription.model.MemberPrescription;
import com.cognizant.subscription.model.MemberSubscription;
import com.cognizant.subscription.model.ResponseForSuccess;
import com.cognizant.subscription.repository.MemberPrescriptionRepository;
import com.cognizant.subscription.repository.MemberSubscriptionRepository;
import com.cognizant.subscription.service.SubscriptionService;


@AutoConfigureMockMvc
@SpringBootTest
public class SubscriptionControllerTests {

	@InjectMocks
	private SubscriptionController subscriptionController;

	@Mock
	private SubscriptionService subscriptionService;

	@MockBean
	MemberPrescriptionRepository memberPrescriptionRepository;

	@MockBean
	MemberSubscriptionRepository memberSubscriptionRepository;

	@MockBean
	private AuthClient authClient;

	@MockBean
	private DrugClient drugClient;

	@MockBean
	private RefillClient refillClient;

	@Autowired
	private MockMvc mockMvc;

	List<DrugLocationDetails> list = new ArrayList<DrugLocationDetails>();

	@Test
	void subscribeTest() throws Exception {

		list.add(new DrugLocationDetails("xy", "Chennai", 30, null));
		DrugDetails expected = new DrugDetails("xy", "Paracetamol", "Azeem", new Date(), new Date(), list);

		MemberPrescription memberPrescription = new MemberPrescription(12001L, "harshit", "Chennai", "6754",
				"MediBuddy", LocalDate.now(), "2times", 3, "Paracetamol", "Gautam", 3);
		MemberSubscription memberSubscription = new MemberSubscription(1L, memberPrescription.getId(),
				memberPrescription.getMemberId(), memberPrescription.getDate(), memberPrescription.getQuantity(),
				memberPrescription.getDrugName(), memberPrescription.getCourseDuration(),
				memberPrescription.getMemberLocation(), "active");

		AuthResponse authResponse = new AuthResponse("harshit", "harshit", true);

		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		ResponseEntity<Object> res = new ResponseEntity<>(new ResponseForSuccess("Refill Done Successfully"),
				HttpStatus.OK);
		ResponseEntity<String> subscriptionResponse = new ResponseEntity<String>(
				"You have successfully subscribed to " + memberPrescription.getDrugName(), HttpStatus.OK);

		when(authClient.getValidity("Bearer Token")).thenReturn(response);

		when(drugClient.getDrugByName("Bearer Token", "Paracetamol")).thenReturn(expected);

		when(drugClient.updateQuantity("Bearer Token", "Paracetamol", "Chennai", 3)).thenReturn(res);

		when(memberPrescriptionRepository.save(any(MemberPrescription.class))).thenReturn(memberPrescription);
		
		when(memberSubscriptionRepository.save(any(MemberSubscription.class))).thenReturn(memberSubscription);
		
		when(subscriptionService.subscribe("Bearer Token", memberPrescription)).thenReturn(subscriptionResponse);

		MvcResult result = mockMvc.perform(post("/api/subscribe").header("Authorization", "Bearer Token")

				.contentType(MediaType.APPLICATION_JSON)
				.content(
						"{ \"courseDuration\": 1, \"date\": \"2021-02-02\", \"doctorDetails\": \"Gautam\", \"dosage\": \"2times\", \"drugName\": \"Paracetamol\", \"id\": 1, \"insuranceProvider\": \"MediBuddy\", \"memberId\": \"harshit\", \"memberLocation\": \"Chennai\", \"policyNumber\": \"6754\", \"quantity\": 3}")

				.accept(MediaType.APPLICATION_JSON))

				.andReturn();

		assertEquals(subscriptionResponse.getBody(), result.getResponse().getContentAsString());
	}


	@Test
	void getAllSubscriptionsTest() throws Exception {

		MemberPrescription memberPrescription = new MemberPrescription(12001L, "harshit", "Pune", "6754", "MediBuddy",
				LocalDate.now(), "2times", 3, "Paracetamol", "Gautam", 3);
		MemberPrescription memberPrescriptions = new MemberPrescription(12001L, "harshit", "Chennai", "6754",
				"MediBuddy", LocalDate.now(), "3times", 4, "Crocin", "Ayush", 4);
		MemberSubscription memberSubscription = new MemberSubscription(1L, memberPrescription.getId(),
				memberPrescription.getMemberId(), memberPrescription.getDate(), memberPrescription.getQuantity(),
				memberPrescription.getDrugName(), memberPrescription.getCourseDuration(),
				memberPrescription.getMemberLocation(), "active");
		MemberSubscription memberSubscriptions = new MemberSubscription(2L, memberPrescriptions.getId(),
				memberPrescriptions.getMemberId(), memberPrescriptions.getDate(), memberPrescriptions.getQuantity(),
				memberPrescriptions.getDrugName(), memberPrescriptions.getCourseDuration(),
				memberPrescriptions.getMemberLocation(), "active");
		List<MemberSubscription> list = new ArrayList<>();
		list.add(memberSubscription);
		list.add(memberSubscriptions);
		AuthResponse authResponse = new AuthResponse("harshit", "harshit", true);
		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

		when(authClient.getValidity("Bearer Token")).thenReturn(response);
		when(memberSubscriptionRepository.findByMemberId("harshit")).thenReturn(list);
		when(subscriptionService.getAllSubscription("Bearer Token", "harshit")).thenReturn(list);

		String expected = Long.toString(list.get(0).getSubscriptionId());

		expected = expected + Long.toString(list.get(1).getSubscriptionId());

		MvcResult result = mockMvc
				.perform(get("/api/getAllSubscriptions/harshit").header("Authorization", "Bearer Token")).andReturn();

		String actual = result.getResponse().getContentAsString().substring(19, 20);
		actual = actual + result.getResponse().getContentAsString().substring(205, 206);

		assertEquals(expected, actual);

	}

	@Test
	void getDrugBySubscriptionTest() throws Exception {

		AuthResponse authResponse = new AuthResponse("harshit", "harshit", true);

		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

		MemberPrescription memberPrescription = new MemberPrescription(12001L, "harshit", "Pune", "6754", "MediBuddy",
				LocalDate.now(), "2times", 3, "Paracetamol", "Gautam", 3);

		MemberSubscription memberSubscription = new MemberSubscription(1L, memberPrescription.getId(),
				memberPrescription.getMemberId(), memberPrescription.getDate(), memberPrescription.getQuantity(),
				memberPrescription.getDrugName(), memberPrescription.getCourseDuration(),
				memberPrescription.getMemberLocation(), "active");

		List<MemberSubscription> list = new ArrayList<MemberSubscription>();
		list.add(memberSubscription);

		Optional<MemberSubscription> optional = Optional.of(memberSubscription);

		when(authClient.getValidity("Bearer Token")).thenReturn(response);

		when(subscriptionService.getDrugBySubscription("Bearer Token", 1L)).thenReturn("Paracetamol");

		when(subscriptionController.getDrugBySubscription("Bearer Token", 1L)).thenReturn("Paracetamol");

		when(memberSubscriptionRepository.findById(1L)).thenReturn(optional);

		MvcResult result = mockMvc.perform(get("/api/getdrugbysubscription/1").header("Authorization", "Bearer Token"))
				.andReturn();

		assertEquals(optional.get().getDrugName(), result.getResponse().getContentAsString());

	}

	@Test
	void getSubscriptionTest() throws Exception {

		AuthResponse authResponse = new AuthResponse("harshit", "harshit", true);

		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);

		MemberPrescription memberPrescription = new MemberPrescription(12001L, "harshit", "Pune", "6754", "MediBuddy",
				LocalDate.now(), "2times", 3, "Paracetamol", "Gautam", 3);

		MemberSubscription memberSubscription = new MemberSubscription(1L, memberPrescription.getId(),
				memberPrescription.getMemberId(), memberPrescription.getDate(), memberPrescription.getQuantity(),
				memberPrescription.getDrugName(), memberPrescription.getCourseDuration(),
				memberPrescription.getMemberLocation(), "active");

		Optional<MemberSubscription> optional = Optional.of(memberSubscription);
		ResponseEntity<MemberSubscription> responseEntity = new ResponseEntity<>(optional.get(), HttpStatus.OK);
		when(authClient.getValidity("Bearer Token")).thenReturn(response);
		when(subscriptionService.getSubscription("Bearer Token", 1L)).thenReturn(responseEntity);
		when(memberSubscriptionRepository.findById(1L)).thenReturn(optional);
		when(subscriptionController.getSubscription("Bearer Token", 1L)).thenReturn(responseEntity);

		MvcResult result = mockMvc.perform(get("/api/getsubscription/1").header("Authorization", "Bearer Token"))
				.andReturn();

		String actual = result.getResponse().getContentAsString().substring(18, 19);

		assertEquals(Long.toString(optional.get().getSubscriptionId()), actual);

	}

	@Test
	void unsubscribeTest() throws Exception {
		MemberPrescription memberPrescription = new MemberPrescription(1L, "harshit", "Pune", "6754", "MediBuddy",
				LocalDate.now(), "2", 3, "Paracetamol", "Gautam", 3);

		MemberSubscription memberSubscription = new MemberSubscription(1L, memberPrescription.getId(),
				memberPrescription.getMemberId(), memberPrescription.getDate(), memberPrescription.getQuantity(),
				memberPrescription.getDrugName(), memberPrescription.getCourseDuration(),
				memberPrescription.getMemberLocation(), "active");
		AuthResponse authResponse = new AuthResponse("harshit", "harshit", true);
		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("You have succesfully Unsubscribed",
				HttpStatus.OK);
		String expected = "You have to clear your payment dues first.";

		when(authClient.getValidity("Bearer Token")).thenReturn(response);
		when(refillClient.getRefillPaymentDues("Bearer Token", 1L)).thenReturn(false);
		when(subscriptionService.unsubscribe("Bearer Token", "harshit", 1L)).thenReturn(responseEntity);
		MvcResult result = mockMvc.perform(post("/api/unsubscribe/").header("Authorization", "Bearer Token").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"memberId\": \"harshit\", \"subscriptionId\": \"1\"}")

				.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		System.out.println("************************************************"+result.getResponse().getContentAsString());
		String actual = result.getResponse().getContentAsString();

		assertEquals(expected, actual);
	}

	@Test
	void unsubscribeRefillDueTest() throws Exception {
		MemberPrescription memberPrescription = new MemberPrescription(12001L, "harshit", "Pune", "6754", "MediBuddy",
				LocalDate.now(), "2times", 3, "Paracetamol", "Gautam", 3);

		MemberSubscription memberSubscription = new MemberSubscription(1L, memberPrescription.getId(),
				memberPrescription.getMemberId(), memberPrescription.getDate(), memberPrescription.getQuantity(),
				memberPrescription.getDrugName(), memberPrescription.getCourseDuration(),
				memberPrescription.getMemberLocation(), "active");
		AuthResponse authResponse = new AuthResponse("harshit", "harshit", true);
		ResponseEntity<AuthResponse> response = new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("You have to clear your payment dues first.",
				HttpStatus.OK);
		String expected = "You have succesfully Unsubscribed";

		when(authClient.getValidity("Bearer Token")).thenReturn(response);
		when(refillClient.getRefillPaymentDues("Bearer Token", 1L)).thenReturn(true);
		when(subscriptionService.unsubscribe("Bearer Token", "harshit", 1L)).thenReturn(responseEntity);
		MvcResult result = mockMvc.perform(post("/api/unsubscribe/").header("Authorization", "Bearer Token").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"memberId\": \"harshit\", \"subscriptionId\": \"1\"}")

				.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String actual = result.getResponse().getContentAsString();

		assertEquals(expected, actual);
	}

}
