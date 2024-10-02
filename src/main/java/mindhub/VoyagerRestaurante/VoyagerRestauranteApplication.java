package mindhub.VoyagerRestaurante;

import mindhub.VoyagerRestaurante.models.*;
import mindhub.VoyagerRestaurante.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VoyagerRestauranteApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(VoyagerRestauranteApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AdressRepository adressRepository,
									  TableRepository tableRepository,
									  ClientTableRepository clientTableRepository,
									  ProductRepository productRepository,
									  ReviewClientProductRepository reviewRepository,
									  OrderRepository orderRepository) {
		return (args) -> {
			// ClienteLuz
			Client clientLuz = new Client("Luz", "Mieres", "luzmieres@gmail.com", passwordEncoder.encode("Luz1234."), new ArrayList<>(List.of("+54911-2345-6778", "+54911-5678-3456")));
			clientRepository.save(clientLuz);

			//DireccionLuz
			Adress adressLuz = new Adress("General Hornos", "Rafaela and Yatay", 123, TypeHome.HOUSE, "ZP1234");
			adressRepository.save(adressLuz);

			// Asignar la dirección al cliente
			clientLuz.addAdress(adressLuz);
			clientRepository.save(clientLuz); // Asegúrate de guardar el cliente nuevamente

			// ClienteMaria
			Client clientMaria = new Client("Maria", "Gonzalez", "maria.gonzalez@example.com", passwordEncoder.encode("Maria123."), new ArrayList<>(List.of("+54911-2345-6778", "+54911-5678-3456")));
			clientRepository.save(clientMaria);

			// DireccionMaria
			Adress adressMaria = new Adress("Toledo", "Irigoyen and Florencio Varela", 564, TypeHome.APARTMENT, 2, "1B", "ZP2346");
			adressRepository.save(adressMaria);

			// Asignar la dirección a Maria
			clientMaria.addAdress(adressMaria);
			clientRepository.save(clientMaria); // Asegúrate de guardar el cliente nuevamente


			// Creación de mesas
			Table table1 = new Table(true, 4, SectorType.DOWN);
			Table table2 = new Table(true, 6, SectorType.OUTSIDE);

			tableRepository.save(table1);


			tableRepository.save(table2);

			// Creación de reservas (ClientTable)
			ClientTable reservation1 = new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(2), clientLuz, table1);
			clientTableRepository.save(reservation1);
			clientLuz.addClientTable(reservation1);
			clientRepository.save(clientLuz);
			table1.addClientTable(reservation1);
			tableRepository.save(table1);
			ClientTable reservation2 = new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(3), clientMaria, table2);


			clientTableRepository.save(reservation2);

			// Creación de productos
			//Papas Fritas
			Product papasFritasCheddarBaccon = new Product("Papas fritas cheddar y  baccon", 8.99, Category.FRIED_FOOD, "Porción de papas con chedar, bacon y cebolla de verdeo");
			productRepository.save(papasFritasCheddarBaccon);

			Product papasFritasNoussete = new Product("Papas Fritas Noussete", 7.99, Category.FRIED_FOOD, "Porción de papas noussete, salsa de preferancia");
			productRepository.save(papasFritasNoussete);

			//Hamburguesas
			Product burgerDobleAmerican = new Product("Burger", 10.99, Category.HAMBURGER, "Pan integral, doble medallon de carne, queso chedar, bacon y cebolla caramelizada");
			productRepository.save(burgerDobleAmerican);

			Product burgerTripleCompleta = new Product("Burger Triple Completa", 15.45, Category.HAMBURGER, "Pan, triple medallon de carne, lechuga, tomate, cebolla, huevo, jamon y queso");
			productRepository.save(burgerTripleCompleta);

			//Bebidas
			Product jugoExprimidoNaranja = new Product("Jugo Exprimido de Naranja", 3.99, Category.DRINK, "Jugo 100% natural de naranja");
			productRepository.save(jugoExprimidoNaranja);

			Product cocaCola = new Product("CocaColaZero", 3.46, Category.DRINK, "Cocacola zero");
			productRepository.save(cocaCola);

			//Postres
			Product flanDulceLeche = new Product("Flan", 4.86, Category.DESERT, "Flan napolitano sabor dulce de leche con crema");
			productRepository.save(flanDulceLeche);

			Product rogel = new Product("Rogel", 6.65, Category.DESERT, "Masa de hojaldre, dulce de leche repostero y merengue italiano");
			productRepository.save(rogel);

			// Creación de órdenes (Order)
			Order order1 = new Order(LocalDateTime.now(), 35.99, clientLuz);
			orderRepository.save(order1);
			burgerDobleAmerican.addOrder(order1);
			jugoExprimidoNaranja.addOrder(order1);
			papasFritasNoussete.addOrder(order1);
			rogel.addOrder(order1);
			productRepository.save(burgerDobleAmerican);

			Order order2 = new Order(LocalDateTime.now(), 29.99, clientMaria);
			orderRepository.save(order2);



			// Creación de reseñas (ReviewClientProduct)
			ReviewClientProduct review1 = new ReviewClientProduct("Excelente comida", "La burger triple completa fue increíble, definitivamente la mejor que he probado.", clientMaria, papasFritasCheddarBaccon);
			reviewRepository.save(review1);

			ReviewClientProduct review2 = new ReviewClientProduct("Muy bueno", "La hamburguesa estaba jugosa y las papas fritas bien crujientes.", clientLuz, burgerDobleAmerican);
			reviewRepository.save(review2);


			System.out.println(clientLuz);
			System.out.println(clientMaria);
		};
	}
}
