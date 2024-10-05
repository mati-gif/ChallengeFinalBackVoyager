
package mindhub.VoyagerRestaurante.services.implementation;

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
    private RestTemplate restTemplate;

    private String homebankingUrl = "http://localhost:8080/api/payments"; // URL del sistema de homebanking

    @Override
    public boolean initiatePayment(Order order, Client client) {
        // Crear un objeto con los detalles del pago
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setAmount(order.getTotalAmount());

        try {
            // Enviar la solicitud de pago al sistema de homebanking
            ResponseEntity<PaymentResponseDTO> response = restTemplate.postForEntity(
                    homebankingUrl + "/pay-order", paymentRequest, PaymentResponseDTO.class);

            // Verificar la respuesta
            return response.getBody().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Si ocurre algún error, retornar false
        }
    }
}