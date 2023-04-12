package hello.AllInShop.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class AddressDTO {

    @NotBlank(message = "큰도시는 반드시 입력해야 합니다.")
    private String city;
    @NotBlank(message = "나머지 주소는 반드시 입력해야 합니다.")
    private String street;
    /*@NotBlank(message = "우편번호는 주소는 반드시 입력해야 합니다.")*/
    private int zipcode;
}
