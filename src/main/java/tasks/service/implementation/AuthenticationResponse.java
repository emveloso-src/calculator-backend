package tasks.service.implementation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
}
