package mindhub.VoyagerRestaurante.dtos;

import java.util.List;

public record RegisterDTO(String firstName, String lastName, String email, String password, List<String> phoneNumbers) { }


