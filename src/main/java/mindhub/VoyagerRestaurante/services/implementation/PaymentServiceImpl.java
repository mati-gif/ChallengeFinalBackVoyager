package mindhub.VoyagerRestaurante.services.implementation;

import mindhub.VoyagerRestaurante.dtos.CardDetailsDTO;
import mindhub.VoyagerRestaurante.dtos.PaymentRequestDTO;
import mindhub.VoyagerRestaurante.dtos.PaymentResponseDTO;
import mindhub.VoyagerRestaurante.models.Client;
import mindhub.VoyagerRestaurante.models.Order;
import mindhub.VoyagerRestaurante.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RestTemplate restTemplate; //Api que permite hacer peticiones de backend a backend

    private String homebankingUrl = "https://homebanking-back-luz-mieres-c55-mh.onrender.com/api/payments"; // URL del sistema de homebanking

    @Override
    public PaymentResponseDTO initiatePayment(Order order, CardDetailsDTO cardDetailsDTO) {
        // Crear un objeto con los detalles del pago
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setAmount(order.getTotalAmount());
        paymentRequest.setCardDetailsDTO(cardDetailsDTO); // Pasar los detalles de la tarjeta

        try {
            // Enviar la solicitud de pago al sistema de homebanking
            ResponseEntity<PaymentResponseDTO> response = restTemplate.postForEntity(
                    homebankingUrl + "/pay-order", paymentRequest, PaymentResponseDTO.class);

            // Verificar la respuesta y devolver el resultado
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();

            // Retornar un PaymentResponseDTO en caso de error
            PaymentResponseDTO paymentResponse = new PaymentResponseDTO();
            paymentResponse.setSuccess(false);
            paymentResponse.setMessage("Payment failed due to an error.");
            return paymentResponse;
        }
    }
}


