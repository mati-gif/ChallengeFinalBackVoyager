package mindhub.VoyagerRestaurante.services;

import mindhub.VoyagerRestaurante.dtos.CardDetailsDTO;
import mindhub.VoyagerRestaurante.dtos.PaymentResponseDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;

public interface PaymentService {
    PaymentResponseDTO initiatePayment(Order order, CardDetailsDTO cardDetailsDTO);
}

