package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;

public interface PaymentService {
    boolean initiatePayment(Order order, Client client);
}