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
//			clientRepository.save(clientLuz);
			clientRepository.save(clientLuz); // Asegúrate de guardar el cliente nuevamente
//			adressRepository.save(adressLuz);


			//DireccionLuz
			Adress adressLuz = new Adress("General Hornos", "Rafaela and Yatay", 123, TypeHome.HOUSE, "ZP1234");
//			clientLuz.addAddress(adressLuz);
			adressRepository.save(adressLuz);

			// ClienteMaria
			Client clientMaria = new Client("Maria", "Gonzalez", "maria.gonzalez@example.com", passwordEncoder.encode("Maria123."), new ArrayList<>(List.of("+54911-2345-6778", "+54911-5678-3456")));
			clientRepository.save(clientMaria);

			//Direcciones
			Adress adress1Maria = new Adress("General Hornos", "Rafaela and Yatay", 123, TypeHome.HOUSE, "ZP1234");

			Adress adress2Maria = new Adress("Toledo", "Irigoyen and Florencio Varela", 564, TypeHome.APARTMENT, 2, "1B", "ZP2346");
//			clientMaria.addAddress(adress1Maria);
//			clientMaria.addAddress(adress2Maria);

			adressRepository.save(adress1Maria);
			adressRepository.save(adress2Maria);

			Order order1 = new Order(LocalDateTime.now(), 500, OrderType.DELIVERY);
			order1.setAdress(adressLuz);
			clientLuz.addOrder(order1);
			orderRepository.save(order1);

			// Asignar la dirección a Maria
//			clientMaria.addAdress(adressMaria);
//			clientRepository.save(clientMaria); // Asegúrate de guardar el cliente nuevamente



			// Creación de mesas
            //15 mesas sector planta baja
            Table table1 = new Table(true, 2, SectorType.GROUND_FLOOR, 1);
            Table table2 = new Table(true, 4, SectorType.GROUND_FLOOR, 2);
            Table table3 = new Table(true, 6, SectorType.GROUND_FLOOR, 3);
            Table table4 = new Table(true, 8, SectorType.GROUND_FLOOR, 4);
            Table table5 = new Table(true, 3, SectorType.GROUND_FLOOR, 5);
            Table table6 = new Table(true, 5, SectorType.GROUND_FLOOR, 6);
            Table table7 = new Table(true, 7, SectorType.GROUND_FLOOR, 7);
            Table table8 = new Table(true, 2, SectorType.GROUND_FLOOR, 8);
            Table table9 = new Table(true, 4, SectorType.GROUND_FLOOR, 9);
            Table table10 = new Table(true, 6, SectorType.GROUND_FLOOR, 10);
            Table table11 = new Table(true, 8, SectorType.GROUND_FLOOR, 11);
            Table table12 = new Table(true, 3, SectorType.GROUND_FLOOR, 12);
            Table table13 = new Table(true, 5, SectorType.GROUND_FLOOR, 13);
            Table table14 = new Table(true, 7, SectorType.GROUND_FLOOR, 14);
            Table table15 = new Table(true, 2, SectorType.GROUND_FLOOR, 15);

            //9 meses sector exterior
            Table table16 = new Table(true, 2, SectorType.OUTDOOR, 16);
            Table table17 = new Table(true, 4, SectorType.OUTDOOR, 17);
            Table table18 = new Table(true, 6, SectorType.OUTDOOR, 18);
            Table table19 = new Table(true, 8, SectorType.OUTDOOR, 19);
            Table table20 = new Table(true, 3, SectorType.OUTDOOR, 20);
            Table table21 = new Table(true, 5, SectorType.OUTDOOR, 21);
            Table table22 = new Table(true, 7, SectorType.OUTDOOR, 22);
            Table table23 = new Table(true, 2, SectorType.OUTDOOR, 23);
			Table table24 = new Table(true, 4, SectorType.OUTDOOR, 24);

			//7 mesas sector primer piso
			Table table25 = new Table(true, 2, SectorType.FIRST_FLOOR, 25);
			Table table26 = new Table(true, 4, SectorType.FIRST_FLOOR, 26);
			Table table27 = new Table(true, 6, SectorType.FIRST_FLOOR, 27);
			Table table28 = new Table(true, 8, SectorType.FIRST_FLOOR, 28);
			Table table29 = new Table(true, 3, SectorType.FIRST_FLOOR, 29);
			Table table30 = new Table(true, 5, SectorType.FIRST_FLOOR, 30);
			Table table31 = new Table(true, 7, SectorType.FIRST_FLOOR, 31);
			tableRepository.saveAll(List.of(table1, table2, table3, table4,table5,
					table6, table7, table8, table9, table10, table11, table12, table13,
					table14, table15, table16, table17, table18, table19, table20,
					table21, table22, table23, table24, table25, table26, table27,
					table28, table29, table30, table31));



			tableRepository.save(table2);

			// Creación de reservas (ClientTable)
			ClientTable reservation1 = new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(1), clientLuz, table1);
			clientTableRepository.save(reservation1);
//			clientLuz.addClientTable(reservation1);
			clientRepository.save(clientLuz);
			//table1.addClientTable(reservation1);
			tableRepository.save(table1);
			clientTableRepository.save(reservation1);

			ClientTable reservation2 = new ClientTable(LocalDateTime.now(), LocalDateTime.now().plusHours(1), clientMaria, table2);
			clientTableRepository.save(reservation2);
//			clientMaria.addClientTable(reservation2);
			clientRepository.save(clientMaria);
			//table2.addClientTable(reservation2);
			tableRepository.save(table2);
			clientTableRepository.save(reservation2);


			// Creación de productos
			// Hamburguesas
			Product product1 = new Product("Oklahoma", 8.99, Category.BURGER, "Juicy burger with caramelized onions and cheddar cheese.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727749502/oklahoma1_kyqed5.png");
			Product product2 = new Product("American", 7.49, Category.BURGER, "Classic American burger with lettuce, tomato, and mayonnaise.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727749543/american1_haeqpf.png");
			Product product3 = new Product("Voyager", 9.99, Category.BURGER, "Gourmet burger with avocado, bacon, and special sauce.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727749532/cheeseBuger1_trckvo.png");
			Product product4 = new Product("Cheeseburger", 10.49, Category.BURGER, "Delicious burger with double cheese and fresh onion.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727749479/voyager1_wqem9z.png");
			Product product5 = new Product("Veggie Burger", 8.99, Category.BURGER, "Vegetarian burger with chickpeas and avocado sauce.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727723685/big-sandwich-hamburger-burger-with-beef-red-onion-tomato-fried-bacon_lthzrk.jpg");

			// Frituras
			Product product6 = new Product("Cheddar Fries", 5.99, Category.FRYING, "Crispy French fries topped with melted cheddar cheese and crispy bacon.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753875/papasChedarPS_xbpzmi.png");
			Product product7 = new Product("Fries", 2.99, Category.FRYING, "Crispy golden French fries.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753876/papas1PS_kh6rye.png");
			Product product8 = new Product("Onion Fries", 3.49, Category.FRYING, "Crispy fried onion strips.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753876/friesOnionPS_ziqavq.png");

			// Bebidas
			Product product9 = new Product("Coca Bottle", 1.99, Category.DRINK, "Classic bottle cola soda.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727757754/cocaBotellaPS_bhhhao.png");
			Product product10 = new Product("Coca Can", 1.49, Category.DRINK, "Classic can cola soda.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727757755/cocaLataPS_zrwjyx.png");
			Product product11 = new Product("Fanta Bottle", 1.99, Category.DRINK, "Orange flavored soda in a bottle.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727757754/fantaBotellaPS_exinu7.png");
			Product product12 = new Product("Fanta Can", 1.49, Category.DRINK, "Orange flavored soda in a can.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727757753/lataFantaPS_abr5jv.png");

			// Postres
			Product product13 = new Product("Chocolate Cupcake", 2.99, Category.DESSERT, "Rich chocolate cupcake with creamy frosting.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753895/postre1PS_cka425.png");
			Product product14 = new Product("Strawberry Cupcake", 2.99, Category.DESSERT, "Light and fluffy cupcake with strawberry frosting.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753897/postre3PS_u9fzfd.png");
			Product product15 = new Product("Chocolate Cake Slice", 4.99, Category.DESSERT, "A slice of rich chocolate cake with layers of creamy frosting.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753898/postre2PS_bfbigw.png");
			Product product16 = new Product("Strawberry Cake Slice", 4.99, Category.DESSERT, "A slice of light and fruity strawberry cake with whipped cream.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753896/postre5PS_nychtd.png");
			Product product17 = new Product("Strawberry Pie", 5.49, Category.DESSERT, "Delicious strawberry pie with a flaky crust.", "https://res.cloudinary.com/dhojn5eon/image/upload/v1727753896/postre4PS_u6i2ja.png");

			// Guardar productos en la base de datos
			productRepository.saveAll(List.of(
					// Hamburguesas
					product1, product2, product3, product4, product6,

					// Frituras
					product5, product11, product12,

					// Bebidas
					product7, product8, product9, product10,

					// Postres
					product13, product14, product15, product16, product17
			));
			System.out.println(clientLuz);
			System.out.println(clientMaria);
		};
	}
}
