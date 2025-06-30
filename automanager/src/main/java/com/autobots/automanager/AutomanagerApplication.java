package com.autobots.automanager;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entities.Address;
import com.autobots.automanager.entities.Customer;
import com.autobots.automanager.entities.IdentityDocument;
import com.autobots.automanager.entities.PhoneNumber;
import com.autobots.automanager.repositories.AddressRepository;
import com.autobots.automanager.repositories.CustomerRepository;
import com.autobots.automanager.repositories.DocumentRepository;
import com.autobots.automanager.repositories.PhoneRepository;

@SpringBootApplication
public class AutomanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Component
	public static class Runner implements ApplicationRunner {

		@Autowired
		private CustomerRepository customerRepository;

		@Autowired
		private AddressRepository addressRepository;

		@Autowired
		private DocumentRepository documentRepository;

		@Autowired
		private PhoneRepository phoneRepository;

		@Override
		public void run(ApplicationArguments args) throws Exception {

			Calendar calendar = Calendar.getInstance();
			calendar.set(2002, Calendar.JUNE, 15);

			Customer customer = new Customer();
			customer.setName("Pedro Alcântara de Bragança e Bourbon");
			customer.setSocialName("Dom Pedro");
			customer.setBirthDate(calendar.getTime());

			PhoneNumber phone = new PhoneNumber();
			phone.setDdd("21");
			phone.setNumber("981234576");
			phone.setCustomer(customer);

			Address address = new Address();
			address.setState("Rio de Janeiro");
			address.setCity("Rio de Janeiro");
			address.setDistrict("Copacabana");
			address.setStreet("Avenida Atlântica");
			address.setNumber("1702");
			address.setPostalCode("22021001");
			address.setAdditionalInfo("Hotel Copacabana Palace");
			address.setCustomer(customer);
			customer.setAddress(address);

			IdentityDocument rg = new IdentityDocument();
			rg.setType("RG");
			rg.setNumber("1500");
			rg.setCustomer(customer);

			IdentityDocument cpf = new IdentityDocument();
			cpf.setType("CPF");
			cpf.setNumber("00000000001");
			cpf.setCustomer(customer);

			customer.getPhones().add(phone);
			customer.getDocuments().add(rg);
			customer.getDocuments().add(cpf);

			customerRepository.save(customer);

			addressRepository.save(address);
			phoneRepository.save(phone);
			documentRepository.save(rg);
			documentRepository.save(cpf);
		}
	}
}
